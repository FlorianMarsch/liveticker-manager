package de.florianmarsch.fussballmanager.db.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;

import de.florianmarsch.fussballmanager.db.entity.AbstractEntity;
import de.florianmarsch.fussballmanager.db.entity.allTimeTable.AllTimeTable;
import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;
import de.florianmarsch.fussballmanager.db.service.AbstractService;
import spark.Route;
import spark.Spark;
import spark.route.HttpMethod;

public abstract class AbstractJSONProducer<E extends AbstractEntity> {

	protected String root;
	private RequestHandler<E> handler;
	private Gson gson;

	public AbstractJSONProducer(AbstractService<E> aAbstractService) {
		Type genericSuperclass = this.getClass().getGenericSuperclass();
		Type x = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
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

	protected void register(AbstractService<E> aAbstractService, String aRoot) {
		root = aRoot.toLowerCase();
		if (!root.contains("/")) {
			if (root.endsWith("h")) {
				root = root + "es";
			} else {
				root = root + "s";
			}
		}
		root = "api/" + root;
		handler = new RequestHandler<E>(aAbstractService);
	}

	@Deprecated
	public AbstractJSONProducer(AbstractService<E> aAbstractService, String aRoot) {
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
		delete("/" + root + "/:id", (request, response) -> {

			List<E> found = getHandler().get(request);
			getHandler().delete(found.get(0));

			return toJson(null);
		});

	}

	public RequestHandler<E> getHandler() {
		return handler;
	}

	private void registerGetSchema() {
		get("/" + root + "/" + "schema/", (request, response) -> {

			Map<String, String> types = getHandler().getSchema();

			return toJson(types);
		});

	}

	private void registerSave() {
		put("/" + root + "/:id", (request, response) -> {

			List<E> found = getHandler().save(request);
			return toJson(found);
		});

	}

	private void registerGetById() {
		get("/" + root + "/:id", (request, response) -> {

			List<E> found = getHandler().get(request);
			return gson.toJson(found);
		});
	}

	private void registerGetAll() {
		get("/" + root, (request, response) -> {
			List<E> all = getHandler().getAll();
			return toJson(all);
		});
	}

	protected void get(String path, Route route) {
		System.out.println("register get : " + path);
		Spark.get(path, getWrapped(route));
	}

	protected void put(String path, Route route) {
		System.out.println("register put : " + path);
		Spark.put(path, getWrapped(route));
	}

	protected void post(String path, Route route) {
		System.out.println("register post : " + path);
		Spark.post(path, getWrapped(route));
	}

	protected void delete(String path, Route route) {
		System.out.println("register delete : " + path);
		Spark.delete(path, getWrapped(route));
	}
	
	private Route getWrapped(Route route) {
		return (request, response) -> {
			return route.handle(request, response);
		};
	}

	public String toJson(Object notAJson) {
		if(notAJson instanceof String) {
			return notAJson.toString();
		}
		return gson.toJson(notAJson);
	}

	public void registerCustom() {

	}

}
