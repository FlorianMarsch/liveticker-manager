package de.fussballmanager.scheduler.axis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultHolder {
	
	private static Map<Long, List<Object>> results = new HashMap<Long, List<Object>>();

	public static List<Object> getResult(Long token) {
		if(results.containsKey(token)){
			return results.get(token);
		}else{
			results.put(token, new ArrayList<Object>());
			return getResult(token);
		}
		
	}

	public static void removeResult(Long token) {
		if(results.containsKey(token)){
			results.remove(token);
		}
		return;
	}

}
