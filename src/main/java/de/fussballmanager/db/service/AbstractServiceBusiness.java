package de.fussballmanager.db.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.EntityPathBase;

import de.fussballmanager.db.entity.AbstractEntity;
import de.fussballmanager.db.jpa.QueryBuilder;

public abstract class AbstractServiceBusiness<E extends AbstractEntity> {

	EntityManager em;
	EntityPathBase<E> source;

	public List<E> getAll() {
		JPAQuery selectQuery = new QueryBuilder(em, source).select(null);
		List<E> resultList = (List<E>) selectQuery.list(source);
		return resultList;
	}

	public <K> Map<K, E> getAllOrderedInMap(Path<K> aKey) {
		JPAQuery selectQuery = new QueryBuilder(em, source).select(null);
		Map<K, E> resultMap = (Map<K, E>) selectQuery.map(aKey, source);
		return resultMap;
	}

	public void save(E aEntity) {
		aEntity.setLastChangedTime(System.currentTimeMillis());
		if (aEntity.getPersistend()) {
			em.merge(aEntity);
		} else {
			aEntity.setPersistend(Boolean.TRUE);
			em.persist(aEntity);
		}
	}

	public void delete(E aEntity) {
		JPAQuery selectQuery = new QueryBuilder(em, source).select(null);
		List<E> all = (List<E>) selectQuery.list(source);
		for (E e : all) {
			if(e.equals(aEntity)){
				em.refresh(e);
				em.remove(e);
			}
		}
	}

}
