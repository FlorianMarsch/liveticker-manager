package de.fussballmanager.db.entity.match;

import java.util.Map;

import de.fussballmanager.db.service.AbstractService;

public class MatchService extends AbstractService<Match> {

	public MatchService() {
		super(QMatch.match);
	}

	public Map<Integer,Match> getMatchdaysByNumber() {
		return super.getAllOrderedInMap(QMatch.match.matchday.number);
	}
	
	@Override
	public Match getNewInstance() {
		return new Match();
	}

}
