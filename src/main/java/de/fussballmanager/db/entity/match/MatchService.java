package de.fussballmanager.db.entity.match;

import de.fussballmanager.db.service.AbstractService;

public class MatchService extends AbstractService<Match> {

	public MatchService() {
		super(QMatch.match);
	}

	@Override
	public Match getNewInstance() {
		return new Match();
	}

}
