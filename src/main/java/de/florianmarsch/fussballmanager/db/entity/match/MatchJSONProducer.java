package de.florianmarsch.fussballmanager.db.entity.match;

import de.florianmarsch.fussballmanager.db.json.AbstractJSONProducer;

public class MatchJSONProducer extends AbstractJSONProducer<Match>{

	public MatchJSONProducer() {
		super(new MatchService());
	}

}
