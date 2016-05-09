package de.florianmarsch.fussballmanager.db.entity.club;

import de.florianmarsch.fussballmanager.db.json.AbstractJSONProducer;

public class ClubJSONProducer extends AbstractJSONProducer<Club>{

	public ClubJSONProducer() {
		
		
		
		super(new ClubService());
	}

}
