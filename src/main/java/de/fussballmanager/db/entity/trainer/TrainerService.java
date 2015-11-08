package de.fussballmanager.db.entity.trainer;

import de.fussballmanager.db.service.AbstractService;

public class TrainerService extends AbstractService<Trainer> {

	public TrainerService() {
		super(QTrainer.trainer);
	}

}
