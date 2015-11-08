package de.fussballmanager.db.entity.trainer;

import de.fussballmanager.db.json.AbstractJSONProducer;

public class TrainerJSONProducer extends AbstractJSONProducer<Trainer>{

	public TrainerJSONProducer() {
		super(new TrainerService(), "trainer");
	}

}
