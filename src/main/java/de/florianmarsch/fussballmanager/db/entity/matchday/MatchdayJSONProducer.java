package de.florianmarsch.fussballmanager.db.entity.matchday;

import de.florianmarsch.fussballmanager.db.json.AbstractJSONProducer;

public class MatchdayJSONProducer extends AbstractJSONProducer<Matchday>{

	public MatchdayJSONProducer() {
		super(new MatchdayService());
	}

}
