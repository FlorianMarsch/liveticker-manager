package de.florianmarsch.fussballmanager.db.entity.player;

import de.florianmarsch.fussballmanager.db.service.AbstractService;

public class PlayerService extends AbstractService<Player> {

	public PlayerService() {
		super(QPlayer.player);
	}

	@Override
	public Player getNewInstance() {
		return new Player();
	}

}
