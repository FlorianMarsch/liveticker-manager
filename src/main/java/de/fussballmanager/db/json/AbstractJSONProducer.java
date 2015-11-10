package de.fussballmanager.db.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.base.Stopwatch;

import spark.ModelAndView;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;
import de.fussballmanager.db.entity.AbstractEntity;
import de.fussballmanager.db.service.AbstractService;

public abstract class AbstractJSONProducer<E extends AbstractEntity> {

	private String root;
	private AbstractService<E> service;

	public AbstractJSONProducer(AbstractService<E> aAbstractService,
			String aRoot) {
		root = aRoot;
		System.out.println("Register root : " + root);
		service = aAbstractService;
	}

	public void registerServices() {
		registerGetAll();
		registerGetById();
		registerGetAttributeById();
		registerSave();
	}

	private void registerSave() {
		Spark.put("/" + root + "/:id", (request, response) -> {
			String id = request.params(":id");
			List<E> found = get(id);
			E entity;
			if (found.isEmpty()) {
				entity = service.getNewInstance();
			} else {
				entity = found.get(0);
			}
			String bodyString = request.body();
			JSONObject body = null;
			try {
				body = new JSONObject(bodyString);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Iterator keys = body.keys();
				while (keys.hasNext()) {
					String key = keys.next().toString();
					if (isAssignable(key)) {
						Object value = body.get(key);
						BeanUtils.setProperty(entity, key, value);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			service.save(entity);
			found = get(entity.getId());
			JSONArray data = JSON.getJsonArray(found);
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data.toString());
			return new ModelAndView(attributes, "json.ftl");
		}, new FreeMarkerEngine());

	}

	private void registerGetAttributeById() {
		Spark.get(
				"/" + root + "/:id/:property",
				(request, response) -> {
					String id = request.params(":id");
					String property = request.params(":property");
					List<E> found = get(id);
					String data = null;
					try {
						data = BeanUtils.getProperty(found.iterator().next(),
								property);
					} catch (Exception e) {
						e.printStackTrace();
					}
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
			String id = request.params(":id");
			List<E> found = get(id);
			JSONArray data = JSON.getJsonArray(found);
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data.toString());
			stopwatch.stop();
			System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));
			return new ModelAndView(attributes, "json.ftl");
		}, new FreeMarkerEngine());
	}

	private List<E> get(String id) {
		List<E> found = new ArrayList<E>(1);
		E temp = service.getAllAsMap().get(id);
		if (temp!=null) {
			found.add(temp);
		}
		return found;
	}

	private void registerGetAll() {
		Spark.get("/" + root, (request, response) -> {
			List<E> all = service.getAll();
			JSONArray data = JSON.getJsonArray(all);

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data.toString());
			return new ModelAndView(attributes, "json.ftl");
		}, new FreeMarkerEngine());
	}

	private boolean isAssignable(String key) {
		return !(key.equals("id") || key.equals("class")
				|| key.equals("schemaName") || key.equals("persistend"));
	}

}
