package de.fussballmanager.db.entity.match;

import de.fussballmanager.db.json.AbstractJSONProducer;

public class MatchJSONProducer extends AbstractJSONProducer<Match>{

	public MatchJSONProducer() {
		super(new MatchService());
	}

}
