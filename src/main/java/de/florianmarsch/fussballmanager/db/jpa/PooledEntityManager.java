package de.florianmarsch.fussballmanager.db.jpa;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

public class PooledEntityManager implements EntityManager {

	private EntityManager wraped;
	public PooledEntityManager(EntityManager aEntityManager) {
		wraped = aEntityManager;
	}
	
	EntityManager getWraped(){
		return wraped;
	}
	
	void clearWraped(){
		wraped = null;
	}
	
	@Override
	public void persist(Object entity) {
		wraped.persist(entity);
	}

	@Override
	public <T> T merge(T entity) {
		return wraped.merge(entity);
	}

	@Override
	public void remove(Object entity) {
wraped.remove(entity);
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey) {
		return wraped.find(entityClass, primaryKey);
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey,
			Map<String, Object> properties) {
		return wraped.find(entityClass, primaryKey, properties);
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey,
			LockModeType lockMode) {
		return wraped.find(entityClass, primaryKey, lockMode);
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey,
			LockModeType lockMode, Map<String, Object> properties) {
		return wraped.find(entityClass, primaryKey, lockMode, properties);
	}

	@Override
	public <T> T getReference(Class<T> entityClass, Object primaryKey) {
		return wraped.getReference(entityClass, primaryKey);
	}

	@Override
	public void flush() {
		wraped.flush();
	}

	@Override
	public void setFlushMode(FlushModeType flushMode) {
		wraped.setFlushMode(flushMode);
	}

	@Override
	public FlushModeType getFlushMode() {
		return wraped.getFlushMode();
	}

	@Override
	public void lock(Object entity, LockModeType lockMode) {
		wraped.lock(entity, lockMode);
	}

	@Override
	public void lock(Object entity, LockModeType lockMode,
			Map<String, Object> properties) {
		wraped.lock(entity, lockMode, properties);
	}

	@Override
	public void refresh(Object entity) {
		wraped.refresh(entity);
	}

	@Override
	public void refresh(Object entity, Map<String, Object> properties) {
		wraped.refresh(entity, properties);
	}

	@Override
	public void refresh(Object entity, LockModeType lockMode) {
		wraped.refresh(entity, lockMode);
	}

	@Override
	public void refresh(Object entity, LockModeType lockMode,
			Map<String, Object> properties) {
		wraped.refresh(entity, properties);
	}

	@Override
	public void clear() {
		wraped.clear();
	}

	@Override
	public void detach(Object entity) {
		wraped.detach(entity);
	}

	@Override
	public boolean contains(Object entity) {
		return wraped.contains(entity);
	}

	@Override
	public LockModeType getLockMode(Object entity) {
		return wraped.getLockMode(entity);
	}

	@Override
	public void setProperty(String propertyName, Object value) {
		wraped.setProperty(propertyName, value);
	}

	@Override
	public Map<String, Object> getProperties() {
		return wraped.getProperties();
	}

	@Override
	public Query createQuery(String qlString) {
		return wraped.createQuery(qlString);
	}

	@Override
	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
		return wraped.createQuery(criteriaQuery);
	}

	@Override
	public Query createQuery(CriteriaUpdate updateQuery) {
		return wraped.createQuery(updateQuery);
	}

	@Override
	public Query createQuery(CriteriaDelete deleteQuery) {
		return wraped.createQuery(deleteQuery);
	}

	@Override
	public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
		return wraped.createQuery(qlString, resultClass);
	}

	@Override
	public Query createNamedQuery(String name) {
		return wraped.createNamedQuery(name);
	}

	@Override
	public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
		return wraped.createNamedQuery(name, resultClass);
	}

	@Override
	public Query createNativeQuery(String sqlString) {
		return wraped.createNativeQuery(sqlString);
	}

	@Override
	public Query createNativeQuery(String sqlString, Class resultClass) {
		return wraped.createNativeQuery(sqlString, resultClass);
	}

	@Override
	public Query createNativeQuery(String sqlString, String resultSetMapping) {
		return wraped.createNativeQuery(sqlString, resultSetMapping);
	}

	@Override
	public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
		return wraped.createNamedStoredProcedureQuery(name);
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
		return wraped.createStoredProcedureQuery(procedureName);
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(
			String procedureName, Class... resultClasses) {
		return wraped.createStoredProcedureQuery(procedureName, resultClasses);
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(
			String procedureName, String... resultSetMappings) {
		return wraped.createStoredProcedureQuery(procedureName, resultSetMappings);
	}

	@Override
	public void joinTransaction() {
		wraped.joinTransaction();

	}

	@Override
	public boolean isJoinedToTransaction() {
		return wraped.isJoinedToTransaction();
	}

	@Override
	public <T> T unwrap(Class<T> cls) {
		return wraped.unwrap(cls);
	}

	@Override
	public Object getDelegate() {
		return wraped.getDelegate();
	}

	@Override
	public void close() {
//		wraped.close();
		throw new IllegalArgumentException("Should be done by Pool");
	}

	@Override
	public boolean isOpen() {
		return wraped.isOpen();
	}

	@Override
	public EntityTransaction getTransaction() {
		return wraped.getTransaction();
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return wraped.getEntityManagerFactory();
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return wraped.getCriteriaBuilder();
	}

	@Override
	public Metamodel getMetamodel() {
		return wraped.getMetamodel();
	}

	@Override
	public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
		return wraped.createEntityGraph(rootType);
	}

	@Override
	public EntityGraph<?> createEntityGraph(String graphName) {
		return wraped.createEntityGraph(graphName);
	}

	@Override
	public EntityGraph<?> getEntityGraph(String graphName) {
		return wraped.getEntityGraph(graphName);
	}

	@Override
	public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
		return wraped.getEntityGraphs(entityClass);
	}

}
