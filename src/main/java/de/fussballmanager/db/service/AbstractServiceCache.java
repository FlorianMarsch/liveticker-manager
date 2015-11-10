package de.fussballmanager.db.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysema.query.types.Path;

import de.fussballmanager.db.AccessLayerException;
import de.fussballmanager.db.entity.AbstractEntity;

public abstract class  AbstractServiceCache<E extends AbstractEntity> extends AbstractServiceEMHandler<E>{
	
	private static final Integer GET_ALL = "getAll".hashCode();
	Map<Object, Object> cachedMap = new HashMap<Object, Object>();
	
	public List<E> getAll(){
		try {
			List<E> resultList = null;
			if(cachedMap.containsKey(GET_ALL)){
				resultList = (List<E>) cachedMap.get(GET_ALL);
			}else{
				resultList = super.getAll();
				cachedMap.put(GET_ALL, resultList);
			}
//			return new ArrayList<E>(resultList);
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccessLayerException("A Cache Problem occured", e);
		}
	}
	
	public <K> Map<K,E> getAllOrderedInMap(Path<K> aKey) {
		try {
			Map<K,E> resultList = null;
			if(cachedMap.containsKey(aKey)){
				resultList = (Map<K,E>) cachedMap.get(aKey);
			}else{
				resultList = super.getAllOrderedInMap(aKey);
				cachedMap.put(aKey, resultList);
			}
			return resultList;
//			return new HashMap<K, E>(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccessLayerException("A Cache Problem occured", e);
		}
	}
	
	
	public void save(E aAbstractEntity){
		try {
			super.save(aAbstractEntity);
			cachedMap.clear();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccessLayerException("A Cache Problem occured", e);
		}
	}
	
}
