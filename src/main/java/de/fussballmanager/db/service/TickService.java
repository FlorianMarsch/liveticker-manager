package de.fussballmanager.db.service;

import java.util.List;

import de.fussballmanager.db.AccessLayerException;
import de.fussballmanager.db.entity.tick.Tick;

public class TickService extends TickServiceCache {

	public List<Tick> getTicks() {
		try {
			return super.getTicks();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccessLayerException("A Call Problem occured", e);
		}
	}

	public void save(Tick aTick) {
		try {
			super.save(aTick);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccessLayerException("A Call Problem occured", e);
		}
	}

}
