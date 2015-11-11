package de.fussballmanager.db.json;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import de.fussballmanager.db.entity.AbstractEntity;

public class JSON {

	public static <E extends AbstractEntity> JSONArray getJsonArray(List<E> all) {
		JSONArray data = new JSONArray();

		for (E tempEntity : all) {
			try {

				Map<String, String> describtion = describe(tempEntity);
				
				data.put(data.length(), new JSONObject(describtion));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return data;
	}
	
	public static Map<String, String> describe(AbstractEntity aObject)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Map<String, String> describe = BeanUtils.describe(aObject);
		describe.remove("class");
		describe.remove("schemaName");
		describe.remove("persistend");
		describe.remove("displayValue");
		describe.remove("lastChangedTime");
		describe.remove("creationTime");
		return describe;
	}
	
	public static Map<String, String> describeTypes(AbstractEntity aObject)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		
		Map<String, String> describeTypes = new HashMap<String, String>();
		Map<String, String> describe = describe(aObject);
		for (String key : describe.keySet()) {
			String type = coerceType(key, aObject);
			if(type != null){
				describeTypes.put(key, type);
			}
		}
		
		return describeTypes;
	}

	public static String coerceType(String key, AbstractEntity aObject) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Class checkClass =PropertyUtils.getPropertyType(aObject, key);
		if(Number.class.isAssignableFrom(checkClass)){
			return "Number";
		}
		if(String.class.isAssignableFrom(checkClass)){
			return "String";
		}
		if(List.class.isAssignableFrom(checkClass)){
			return "Array";
		}
		if(Boolean.class.isAssignableFrom(checkClass)){
			return "Boolean";
		}
		if(AbstractEntity.class.isAssignableFrom(checkClass)){
			return checkClass.getSimpleName();
		}
		return null;
	}
	
	
}
