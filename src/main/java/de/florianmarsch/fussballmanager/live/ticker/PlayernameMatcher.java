package de.florianmarsch.fussballmanager.live.ticker;

import java.util.Map;

import de.florianmarsch.fussballmanager.db.entity.player.Player;
import de.florianmarsch.fussballmanager.db.entity.player.PlayerService;
import de.florianmarsch.fussballmanager.db.entity.player.QPlayer;

public class PlayernameMatcher {

	PlayerService ps = new PlayerService();
	Map<String, Player> players = ps.getAllOrderedInMap(
			QPlayer.player.name);

	public String getMatchedName(String name) {
		System.out.println("check " + name);
		boolean contains = players.containsKey(name);
		if(contains){
			return players.get(name).getAbbreviationName();
		}else{
			return null;
		}
	}
}
