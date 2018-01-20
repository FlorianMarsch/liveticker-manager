package de.florianmarsch.fussballmanager.db.json;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONObject;

import de.florianmarsch.fussballmanager.db.entity.AbstractEntity;
import de.florianmarsch.fussballmanager.db.service.AbstractService;
import spark.Request;

public class RequestHandler<E extends AbstractEntity> implements PropertyResolver{
	private AbstractService<E> service;
	private BindContext binding;

	public RequestHandler(AbstractService<E> aAbstractService) {
		service = aAbstractService;
	}

	public Map<String, String> getSchema() {
		E temp = getService().getNewInstance();
		Map<String, String> types = null;
		try {
			types = JSON.describeTypes(temp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return types;
	}

	public List<E> save(Request request) {
		String id = request.params(":id");
		List<E> found = get(id);
		E entity;
		if (found.isEmpty()) {
			entity = getService().getNewInstance();
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
					setProperty(entity, key, value);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getService().save(entity);
		return get(entity.getId());

	}

	public AbstractService<E> getService() {
		return service;
	}

	private void setProperty(E entity, String key, Object value)
			throws IllegalAccessException, InvocationTargetException {
		
		Map<String, String> schema = getSchema();
		String type = schema.get(key);
		PropertyResolver handler = binding.getHandler(type);
		Object resolved = handler.resolve(value);
		
		BeanUtils.setProperty(entity, key, resolved);
	}

	public String getAttributeValue(Request request) {
		String id = request.params(":id");
		String property = request.params(":property");
		List<E> found = get(id);
		String data = null;
		try {
			data = BeanUtils.getProperty(found.iterator().next(), property);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public List<E> get(Request request) {
		String id = request.params(":id");
		return get(id);
	}

	public List<E> get(String id) {
		List<E> found = new ArrayList<E>(1);
		E temp = getService().getAllAsMap().get(id);
		if (temp != null) {
			found.add(temp);
		}
		return found;
	}
	
	public void delete(E aEntity) {
		getService().delete(aEntity);
	}
	

	public List<E> getAll() {
		List<E> all = getService().getAll();
		return all;
	}

	public boolean isAssignable(String key) {
		return !(key.equals("id") || key.equals("class")
				|| key.equals("schemaName") || key.equals("persistend"));
	}

	public void setBinding(BindContext binding) {
		this.binding = binding;
	}

	@Override
	public Object resolve(Object aValue) {
		return getService().getAllAsMap().get(aValue);
	}

}
