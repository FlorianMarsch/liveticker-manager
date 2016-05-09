package de.florianmarsch.fussballmanager.live.ticker;

import java.util.HashSet;
import java.util.Set;

import de.florianmarsch.fussballmanager.db.entity.player.PlayerService;
import de.florianmarsch.fussballmanager.db.entity.player.QPlayer;

public class PlayernameMatcher {

	PlayerService ps = new PlayerService();
	Set<String> keySet = ps.getAllOrderedInMap(
			QPlayer.player.normalizedName).keySet();

	public String getMatchedName(String name) {
		System.out.println("check " + name);
		
		PlayerName remoteName = new PlayerName(name);
		
		Set<PlayerName> playersToCheck = new HashSet<PlayerName>();
		for (String originName : keySet) {
			PlayerName localName =  new PlayerName(originName);
			if(localName.isSameFamily(remoteName)){
				playersToCheck.add(localName);
			}
		}
		
		System.out.println("alternatives : " + playersToCheck);
		if (playersToCheck.isEmpty()) {
			return null;
		}
		PlayerName thePlayerName = filter(playersToCheck, remoteName);
		if(thePlayerName != null ){
			return thePlayerName.toString();
		}else{
			return null;
		}
	}

	private PlayerName filter(Set<PlayerName> playersToCheck, PlayerName remote) {
		PlayerName tempReturn = null;
		for (PlayerName playerToCheck : playersToCheck) {
			if (playerToCheck.hasAbbriviatedName()) {
				String abbreviation = playerToCheck.getFirstName();
				if (remote.getFirstName().startsWith(abbreviation)) {
					return playerToCheck;
				}
			} else {
				if(playersToCheck.size() == 1){
					return playersToCheck.iterator().next();
				}else{
					// found Ma. Lehmann + Lehmann
					// return playersToCheck;
				}
			}
		}
		return tempReturn;
	}

}
