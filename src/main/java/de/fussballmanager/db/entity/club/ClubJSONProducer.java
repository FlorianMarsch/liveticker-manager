package de.fussballmanager.db.entity.club;

import de.fussballmanager.db.json.AbstractJSONProducer;

public class ClubJSONProducer extends AbstractJSONProducer<Club>{

	public ClubJSONProducer() {
		super(new ClubService(), "club");
	}

}
