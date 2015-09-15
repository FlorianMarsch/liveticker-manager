package de.fussballmanager.db.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;

import de.fussballmanager.db.AccessLayerException;
import de.fussballmanager.db.entity.tick.Tick;
import de.fussballmanager.db.jpa.EmFactory;

public class TickServiceEMHandler extends TickServiceBusiness{

	public List<Tick> getTicks(){
		EntityTransaction transaction = null;
		try {
			em = EmFactory.getEntityManager();
			transaction = em.getTransaction();
			transaction.begin();
			List<Tick> resultList = super.getTicks();
			transaction.commit();
			return new ArrayList<Tick>(resultList);
		} catch (Exception e) {
			if(transaction != null && transaction.isActive()){
				transaction.rollback();
			}
			throw new AccessLayerException("A DB Problem occured", e);
		}
	}
	
	public void save(Tick aTick){
		EntityTransaction transaction = null;
		try {
			em = EmFactory.getEntityManager();
			transaction = em.getTransaction();
			transaction.begin();
			super.save(aTick);
			transaction.commit();
		} catch (Exception e) {
			if(transaction != null && transaction.isActive()){
				transaction.rollback();
			}
			throw new AccessLayerException("A DB Problem occured", e);
		}
	}
	
}
