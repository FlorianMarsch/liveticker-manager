package de.florianmarsch.fussballmanager.db.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import de.florianmarsch.fussballmanager.db.entity.AbstractEntity;
import de.florianmarsch.fussballmanager.db.service.AbstractService;
import spark.Spark;

public abstract class AbstractJSONProducer<E extends AbstractEntity> {

	protected String root;
	private RequestHandler<E> handler;
	private Gson gson;
	
	public AbstractJSONProducer(AbstractService<E> aAbstractService) {
		Type genericSuperclass = this.getClass().getGenericSuperclass();
		Type x = ((ParameterizedType) genericSuperclass)
				.getActualTypeArguments()[0];
		Class<?> forName = null;
		try {
			forName = Class.forName(x.getTypeName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gson = new Gson();
		register(aAbstractService, forName.getSimpleName());
	}
	
	private void register(AbstractService<E> aAbstractService, String aRoot) {
		root = aRoot.toLowerCase();
		if(root.endsWith("h")) {
			root = root +"es";
		}else {
			root = root+"s";
		}
		System.out.println("Register root : " + root);
		handler = new RequestHandler<E>(aAbstractService);
	}

	@Deprecated
	public AbstractJSONProducer(AbstractService<E> aAbstractService,
			String aRoot) {
		register(aAbstractService, aRoot);
	}

	public void bindServices(BindContext aBindContext) {
		aBindContext.bind(root, getHandler());
		
		registerGetAll();
		registerGetById();
		registerGetSchema();
		registerSave();
		registerDelete();
		registerCustom();
	}

	private void registerDelete() {
		Spark.delete("/" + root + "/:id", (request, response) -> {


			List<E> found = getHandler().get(request);
			getHandler().delete(found.get(0));
		
			return toJson(null);
		});
		
	}

	public RequestHandler<E> getHandler() {
		return handler;
	}

	private void registerGetSchema() {
		Spark.get("/" + root + "/" + "schema/", (request, response) -> {

		
			Map<String, String> types = getHandler().getSchema();

			return toJson(types);
		});

	}

	private void registerSave() {
		Spark.put("/" + root + "/:id", (request, response) -> {

			List<E> found = getHandler().save(request);
			return toJson(found);
		});

	}


	private void registerGetById() {
		Spark.get("/" + root + "/:id", (request, response) -> {

		

			List<E> found = getHandler().get(request);
			return gson.toJson(found);
		});
	}

	private void registerGetAll() {
		Spark.get("/" + root, (request, response) -> {
			List<E> all = getHandler().getAll();
			return toJson(all);
		});
	}
	

	public String toJson(Object notAJson) {
		return gson.toJson(notAJson);
	}
	
	public void registerCustom() {
		
	}

}
