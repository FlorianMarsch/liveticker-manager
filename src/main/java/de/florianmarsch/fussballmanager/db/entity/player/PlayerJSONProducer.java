package de.florianmarsch.fussballmanager.db.entity.player;

import de.florianmarsch.fussballmanager.db.json.AbstractJSONProducer;

public class PlayerJSONProducer extends AbstractJSONProducer<Player>{

	public PlayerJSONProducer() {
		super(new PlayerService());
	}

}
