package de.florianmarsch.fussballmanager.db.entity.trainer;

import de.florianmarsch.fussballmanager.db.json.AbstractJSONProducer;

public class TrainerJSONProducer extends AbstractJSONProducer<Trainer>{

	public TrainerJSONProducer() {
		super(new TrainerService());
	}

}
