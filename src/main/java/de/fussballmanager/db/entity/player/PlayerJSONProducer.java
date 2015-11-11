package de.fussballmanager.db.entity.player;

import de.fussballmanager.db.json.AbstractJSONProducer;

public class PlayerJSONProducer extends AbstractJSONProducer<Player>{

	public PlayerJSONProducer() {
		super(new PlayerService());
	}

}
