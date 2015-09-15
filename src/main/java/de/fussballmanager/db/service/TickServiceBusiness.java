package de.fussballmanager.db.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.mysema.query.jpa.impl.JPAQuery;

import de.fussballmanager.db.entity.tick.QTick;
import de.fussballmanager.db.entity.tick.Tick;
import de.fussballmanager.db.jpa.QueryBuilder;

public class TickServiceBusiness {

	EntityManager em;

	public List<Tick> getTicks() {
		new QueryBuilder(em, QTick.tick);
		JPAQuery selectQuery = new QueryBuilder(em, QTick.tick).select(null);
		List<Tick> resultList = selectQuery.list(QTick.tick);
		return resultList;
	}

	public void save(Tick aTick) {
		if (aTick.getPersistend()) {
			em.merge(aTick);
		} else {
			aTick.setLastChangedTime(System.currentTimeMillis());
			aTick.setPersistend(Boolean.TRUE);
			em.persist(aTick);
		}
	}

}
