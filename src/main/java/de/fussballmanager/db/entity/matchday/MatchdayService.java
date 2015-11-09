package de.fussballmanager.db.entity.matchday;

import java.util.Map;

import de.fussballmanager.db.service.AbstractService;

public class MatchdayService extends AbstractService<Matchday> {

	public MatchdayService() {
		super(QMatchday.matchday);
	}

	public Map<Integer,Matchday> getMatchdaysByNumber() {
		return super.getAllOrderedInMap(QMatchday.matchday.number);
	}
	
	@Override
	public Matchday getNewInstance() {
		return new Matchday();
	}

}
