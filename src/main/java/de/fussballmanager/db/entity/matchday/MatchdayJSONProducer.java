package de.fussballmanager.db.entity.matchday;

import de.fussballmanager.db.json.AbstractJSONProducer;

public class MatchdayJSONProducer extends AbstractJSONProducer<Matchday>{

	public MatchdayJSONProducer() {
		super(new MatchdayService(), "matchday");
	}

}
