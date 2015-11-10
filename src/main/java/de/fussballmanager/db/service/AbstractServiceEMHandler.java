package de.fussballmanager.db.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.mysema.query.types.Path;

import de.fussballmanager.db.AccessLayerException;
import de.fussballmanager.db.entity.AbstractEntity;
import de.fussballmanager.db.jpa.EmPool;

public abstract class AbstractServiceEMHandler<E extends AbstractEntity> extends AbstractServiceBusiness<E>{
	
	public List<E> getAll(){
		EntityTransaction transaction = null;
		try {
			em = getInitialized(em);
			transaction = em.getTransaction();
			transaction.begin();
			List<E> resultList = super.getAll();
			transaction.commit();
			em = remove(em);
			return new ArrayList<E>(resultList);
		} catch (Exception e) {
			if(transaction != null && transaction.isActive()){
				transaction.rollback();
			}
			em = destroy(em);
			e.printStackTrace();
			throw new AccessLayerException("A DB Problem occured", e);
		}
	}

	public <K> Map<K,E> getAllOrderedInMap(Path<K> aKey) {
		EntityTransaction transaction = null;
		try {
			em = getInitialized(em);
			transaction = em.getTransaction();
			transaction.begin();
			Map<K,E> resultList = super.getAllOrderedInMap(aKey) ;
			transaction.commit();
			em = remove(em);
			return new HashMap<K, E>(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			if(transaction != null && transaction.isActive()){
				transaction.rollback();
			}
			em = destroy(em);
			e.printStackTrace();
			throw new AccessLayerException("A DB Problem occured", e);
		}
	}
	
	public void save(E aAbstractEntity){
		EntityTransaction transaction = null;
		try {
			em = getInitialized(em);
			transaction = em.getTransaction();
			transaction.begin();
			super.save(aAbstractEntity);
			transaction.commit();
			em = remove(em);
		} catch (Exception e) {
			if(transaction != null && transaction.isActive()){
				transaction.rollback();
			}
			em = destroy(em);
			e.printStackTrace();
			throw new AccessLayerException("A DB Problem occured", e);
		}
	}
	
	private EntityManager getInitialized(EntityManager aEntityManager) {
		if(aEntityManager == null){
			return EmPool.instance.get();
		}
		return aEntityManager;
	}
	
	private EntityManager destroy(EntityManager aEntityManager) {
		EmPool.instance.remove(aEntityManager);
		return null;
	}
	
	private EntityManager remove(EntityManager aEntityManager) {
		EmPool.instance.put(aEntityManager);
		return null;
	}

	
}
