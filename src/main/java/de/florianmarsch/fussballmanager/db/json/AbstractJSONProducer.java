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
		root = aRoot;
		System.out.println("Register root : " + root);
		handler = new RequestHandler<E>(aAbstractService);
	}

	@Deprecated
	public AbstractJSONProducer(AbstractService<E> aAbstractService,
			String aRoot) {
		register(aAbstractService, aRoot);
	}

	public void bindServices(BindContext aBindContext) {
		aBindContext.bind(root, handler);
		
		registerGetAll();
		registerGetById();
		registerGetAttributeById();
		registerGetSchema();
		registerSave();
		registerDelete();
	}

	private void registerDelete() {
		Spark.delete("/" + root + "/:id", (request, response) -> {


			List<E> found = handler.get(request);
			handler.delete(found.get(0));
		
			return gson.toJson(null);
		});
		
	}

	private void registerGetSchema() {
		Spark.get("/" + root + "/" + "schema/", (request, response) -> {

		
			Map<String, String> types = handler.getSchema();

			return gson.toJson(types);
		});

	}

	private void registerSave() {
		Spark.put("/" + root + "/:id", (request, response) -> {

			List<E> found = handler.save(request);
			return gson.toJson(found);
		});

	}

	private void registerGetAttributeById() {
		Spark.get("/" + root + "/:id/:property", (request, response) -> {

			String data = handler.getAttributeValue(request);

			return gson.toJson(data);
		});
	}

	private void registerGetById() {
		Spark.get("/" + root + "/:id", (request, response) -> {

		

			List<E> found = handler.get(request);
			return gson.toJson(found);
		});
	}

	private void registerGetAll() {
		Spark.get("/" + root, (request, response) -> {
			List<E> all = handler.getAll();
			return gson.toJson(all);
		});
	}

}
