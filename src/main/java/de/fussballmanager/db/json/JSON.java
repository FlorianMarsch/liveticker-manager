package de.fussballmanager.db.json;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
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
		return describe;
	}
	
	
}
