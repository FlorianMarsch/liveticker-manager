package de.fussballmanager.db.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import spark.ModelAndView;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.common.base.Stopwatch;

import de.fussballmanager.db.entity.AbstractEntity;
import de.fussballmanager.db.service.AbstractService;

public abstract class AbstractJSONProducer<E extends AbstractEntity> {

	protected String root;
	private RequestHandler<E> handler;
	
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
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", new JSONArray());
			return new ModelAndView(attributes, "json.ftl");
		}, new FreeMarkerEngine());
		
	}

	private void registerGetSchema() {
		Spark.get("/" + root + "/" + "schema/", (request, response) -> {

			Stopwatch stopwatch = Stopwatch.createStarted();

			Map<String, String> types = handler.getSchema();

			JSONObject data = new JSONObject(types);
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data.toString());
			stopwatch.stop();
			System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));
			return new ModelAndView(attributes, "json.ftl");
		}, new FreeMarkerEngine());

	}

	private void registerSave() {
		Spark.put("/" + root + "/:id", (request, response) -> {

			List<E> found = handler.save(request);
			JSONArray data = JSON.getJsonArray(found);
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data.toString());
			return new ModelAndView(attributes, "json.ftl");
		}, new FreeMarkerEngine());

	}

	private void registerGetAttributeById() {
		Spark.get("/" + root + "/:id/:property", (request, response) -> {

			String data = handler.getAttributeValue(request);

			Map<String, Object> attributes = new HashMap<>();
			JSONArray value = new JSONArray();
			value.put(data);
			attributes.put("data", value);
			return new ModelAndView(attributes, "json.ftl");
		}, new FreeMarkerEngine());
	}

	private void registerGetById() {
		Spark.get("/" + root + "/:id", (request, response) -> {

			Stopwatch stopwatch = Stopwatch.createStarted();

			List<E> found = handler.get(request);
			JSONArray data = JSON.getJsonArray(found);
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data.toString());
			stopwatch.stop();
			System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));
			return new ModelAndView(attributes, "json.ftl");
		}, new FreeMarkerEngine());
	}

	private void registerGetAll() {
		Spark.get("/" + root, (request, response) -> {
			List<E> all = handler.getAll();
			JSONArray data = JSON.getJsonArray(all);

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data.toString());
			return new ModelAndView(attributes, "json.ftl");
		}, new FreeMarkerEngine());
	}

}
