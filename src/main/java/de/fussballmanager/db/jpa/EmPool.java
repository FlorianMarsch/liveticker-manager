package de.fussballmanager.db.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import de.fussballmanager.db.AccessLayerException;

public final class EmPool {

	public static final EmPool instance = new EmPool();
	private EmFactory emFactory = new EmFactory();
	private Set<EntityManager> managed = new HashSet<EntityManager>();
	private Set<EntityManager> delivered = new HashSet<EntityManager>();

	private EmPool() {
	}

	public EntityManager get() {
		synchronized (this) {
			if (isFull()) {
				throw new AccessLayerException(
						"No Connection can be delivered", null);
			}
			EntityManager next;
			if (isEmpty()) {
				next = emFactory.produceEntityManager();
			} else {
				next = getEffectivePool().iterator().next();
			}
			delivered.add(next);
			return wrap(next);
		}
	}

	public void put(EntityManager aEntityManager) {
		synchronized (this) {
			PooledEntityManager pooled = (PooledEntityManager)aEntityManager;
			EntityManager inner = unwrap(aEntityManager);
			if (delivered.contains(inner)) {
				delivered.remove(inner);
				pooled.clearWraped();
			} else {
				String message = "EntityManager not delivered by the ConnectionPool";
				throw new AccessLayerException(message,
						new IllegalArgumentException(message));
			}
		}
	}

	public void remove(EntityManager aEntityManager) {
		synchronized (this) {
			PooledEntityManager pooled = (PooledEntityManager)aEntityManager;
			EntityManager inner = unwrap(aEntityManager);
			if (delivered.contains(inner)) {
				inner.close();
				pooled.clearWraped();
				delivered.remove(inner);
				managed.remove(inner);
			} else {
				String message = "EntityManager not delivered by the ConnectionPool";
				throw new AccessLayerException(message,
						new IllegalArgumentException(message));
			}
		}
	}

	private EntityManager wrap(EntityManager aEntityManager) {
		return new PooledEntityManager(aEntityManager);
	}

	private EntityManager unwrap(EntityManager aEntityManager) {
		return ((PooledEntityManager) aEntityManager).getWraped();
	}

	private boolean isFull() {
		return delivered.size() >= 10;
	}

	private boolean isEmpty() {
		Set<EntityManager> pool = getEffectivePool();
		return pool.isEmpty();
	}

	private Set<EntityManager> getEffectivePool() {
		HashSet<EntityManager> hashSet = new HashSet<EntityManager>(managed);
		hashSet.removeAll(delivered);
		return hashSet;
	}
}
