package de.florianmarsch.fussballmanager.db.entity.tick;

import de.florianmarsch.fussballmanager.db.json.AbstractJSONProducer;

public class TickJSONProducer extends AbstractJSONProducer<Tick>{

	public TickJSONProducer() {
		super(new TickService());
	}

}
