package de.fussballmanager.db.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fussballmanager.db.AccessLayerException;
import de.fussballmanager.db.entity.tick.Tick;

class TickServiceCache extends TickServiceEMHandler{

	private static final Integer GET_ALL = "getAll".hashCode();
	Map<Integer, Object> cachedMap = new HashMap<Integer, Object>();
	
	public List<Tick> getTicks(){
		try {
			List<Tick> resultList = null;
			if(cachedMap.containsKey(GET_ALL)){
				resultList = (List<Tick>) cachedMap.get(GET_ALL);
			}else{
				resultList = super.getTicks();
				cachedMap.put(GET_ALL, resultList);
			}
			return new ArrayList<Tick>(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccessLayerException("A Cache Problem occured", e);
		}
	}
	
	public void save(Tick aTick){
		try {
			super.save(aTick);
			cachedMap.clear();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccessLayerException("A Cache Problem occured", e);
		}
	}
	
}
