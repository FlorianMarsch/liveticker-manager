package de.fussballmanager.db.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysema.query.types.Path;

import de.fussballmanager.db.AccessLayerException;
import de.fussballmanager.db.entity.AbstractEntity;

public abstract class  AbstractServiceCache<E extends AbstractEntity> extends AbstractServiceEMHandler<E>{
	
	private static final Integer GET_ALL = "getAll".hashCode();
	private Map<Object, Object> cachedMap = new HashMap<Object, Object>();
	
	public List<E> getAll(){
		try {
			List<E> resultList = null;
			if(getCachedMap().containsKey(GET_ALL)){
				resultList = (List<E>) getCachedMap().get(GET_ALL);
			}else{
				resultList = super.getAll();
				getCachedMap().put(GET_ALL, resultList);
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
			if(getCachedMap().containsKey(aKey)){
				resultList = (Map<K,E>) getCachedMap().get(aKey);
			}else{
				resultList = super.getAllOrderedInMap(aKey);
				getCachedMap().put(aKey, resultList);
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
			getCachedMap().clear();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccessLayerException("A Cache Problem occured", e);
		}
	}
	
	public void delete(E aAbstractEntity){
		try {
			super.delete(aAbstractEntity);
			getCachedMap().clear();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccessLayerException("A Cache Problem occured", e);
		}
	}
	
	
	public Map<Object, Object> getCachedMap() {
		return cachedMap;
	}
	
}
