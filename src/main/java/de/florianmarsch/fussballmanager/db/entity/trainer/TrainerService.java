package de.florianmarsch.fussballmanager.db.entity.trainer;

import de.florianmarsch.fussballmanager.db.entity.trainer.QTrainer;
import de.florianmarsch.fussballmanager.db.service.AbstractService;

public class TrainerService extends AbstractService<Trainer> {

	public TrainerService() {
		super(QTrainer.trainer);
	}

	@Override
	public Trainer getNewInstance() {
		return new Trainer();
	}

}
