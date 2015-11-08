package de.fussballmanager.db.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONArray;
import org.json.JSONObject;

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
		registerSave();
	}

	private void registerSave() {
		Spark.put("/"+root+"/:id", (request, response) -> {
			List<E> all = service.getAll();
			String id = request.params(":id");
			List<E> found = get(id);
			E entity;
			if(found.isEmpty()){
				entity = service.getNewInstance();
			}else{
				entity = found.get(0);
			}
			String bodyString =request.body();
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
					Object value = body.get(key);
					BeanUtils.setProperty(entity, key, value);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			service.save(entity);
			found = get(entity.getId());
			JSONArray data = getJsonArray(found);
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data.toString());
			return new ModelAndView(attributes, "json.ftl");
		}, new FreeMarkerEngine());
		
	}

	private void registerGetById() {
		Spark.get("/" + root + "/:id", (request, response) -> {
			String id = request.params(":id");
			List<E> found = get(id);
			JSONArray data = getJsonArray(found);
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data.toString());
			return new ModelAndView(attributes, "json.ftl");
		}, new FreeMarkerEngine());
	}

	private List<E> get(String id) {
		List<E> all = service.getAll();
		List<E> found = new ArrayList<E>();
		for (E tempEntity : all) {
			if (tempEntity.getId().equals(id)) {
				found.add(tempEntity);
			}
		}
		return found;
	}

	private void registerGetAll() {
		Spark.get("/" + root, (request, response) -> {
			List<E> all = service.getAll();
			JSONArray data = getJsonArray(all);

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data.toString());
			return new ModelAndView(attributes, "json.ftl");
		}, new FreeMarkerEngine());
	}

	private JSONArray getJsonArray(List<E> all) {
		JSONArray data = new JSONArray();

		for (E tempEntity : all) {
			try {
				JSONObject tempJsonPlayer = new JSONObject();

				Map<String, String> describtion = BeanUtils
						.describe(tempEntity);
				for (String key : describtion.keySet()) {
					tempJsonPlayer.put(key, describtion.get(key));
				}
				data.put(data.length(), tempJsonPlayer);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return data;
	}

}
