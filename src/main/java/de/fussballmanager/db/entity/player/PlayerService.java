package de.fussballmanager.db.entity.player;

import java.util.Map;

import de.fussballmanager.db.service.AbstractService;

public class PlayerService extends AbstractService<Player> {

	public PlayerService() {
		super(QPlayer.player);
	}

	public Map<Integer, Player> getPlayersByExternID() {
		return super.getAllOrderedInMap(QPlayer.player.externID);
	}

	@Override
	public Player getNewInstance() {
		return new Player();
	}

}
