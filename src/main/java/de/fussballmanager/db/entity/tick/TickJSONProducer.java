package de.fussballmanager.db.entity.tick;

import de.fussballmanager.db.json.AbstractJSONProducer;

public class TickJSONProducer extends AbstractJSONProducer<Tick>{

	public TickJSONProducer() {
		super(new TickService());
	}

}
