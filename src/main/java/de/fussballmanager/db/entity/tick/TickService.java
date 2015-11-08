package de.fussballmanager.db.entity.tick;

import de.fussballmanager.db.service.AbstractService;

public class TickService extends AbstractService<Tick> {

	public TickService() {
		super(QTick.tick);
	}

	@Override
	public Tick getNewInstance() {
		return new Tick();
	}

}
