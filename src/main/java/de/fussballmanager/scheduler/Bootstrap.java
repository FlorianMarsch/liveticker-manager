package de.fussballmanager.scheduler;

import java.text.Normalizer;
import java.util.List;
import java.util.Map;

import de.fussballmanager.db.entity.club.Club;
import de.fussballmanager.db.entity.club.ClubService;
import de.fussballmanager.db.entity.player.Player;
import de.fussballmanager.db.entity.player.PlayerService;
import de.fussballmanager.scheduler.axis.PlayerItem;
import de.fussballmanager.scheduler.axis.service.PlayerItemWebservice;


public class Bootstrap {

	ClubService clubService = new ClubService();
	PlayerService playerService = new PlayerService();
	PlayerItemWebservice webService = new PlayerItemWebservice();

	public void init() {
		Club tempClub = new Club();
		tempClub.setName("FC Bayern München");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(1);
		clubService.save(tempClub);

		tempClub = new Club();
		tempClub.setName("VfL Wolfsburg");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(12);
		clubService.save(tempClub);

		tempClub = new Club();
		tempClub.setName("Borussia M'gladbach");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(3);
		clubService.save(tempClub);

		tempClub = new Club();
		tempClub.setName("Bayer 04 Leverkusen");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(8);
		clubService.save(tempClub);

		tempClub = new Club();
		tempClub.setName("FC Schalke 04");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(10);
		clubService.save(tempClub);

		tempClub = new Club();
		tempClub.setName("FC Augsburg");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(68);
		clubService.save(tempClub);

		tempClub = new Club();
		tempClub.setName("1899 Hoffenheim");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(62);
		clubService.save(tempClub);

		tempClub = new Club();
		tempClub.setName("Borussia Dortmund");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(5);
		clubService.save(tempClub);

		tempClub = new Club();
		tempClub.setName("SV Werder Bremen");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(6);
		clubService.save(tempClub);

		tempClub = new Club();
		tempClub.setName("1. FSV Mainz 05");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(18);
		clubService.save(tempClub);

		tempClub = new Club();
		tempClub.setName("Eintracht Frankfurt");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(9);
		clubService.save(tempClub);

		tempClub = new Club();
		tempClub.setName("1. FC Köln");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(13);
		clubService.save(tempClub);

		tempClub = new Club();
		tempClub.setName("Hertha BSC");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(7);
		clubService.save(tempClub);

//		tempClub = new Club();
//		tempClub.setName("SC Freiburg");
//		tempClub.setLogoUrl("logo");
//		tempClub.setExternID(21);
//		clubService.save(tempClub);

		tempClub = new Club();
		tempClub.setName("Hannover 96");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(17);
		clubService.save(tempClub);

		tempClub = new Club();
		tempClub.setName("Hamburger SV");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(4);
		clubService.save(tempClub);

//		tempClub = new Club();
//		tempClub.setName("SC Paderborn");
//		tempClub.setLogoUrl("logo");
//		tempClub.setExternID(81);
//		clubService.save(tempClub);

		tempClub = new Club();
		tempClub.setName("VfB Stuttgart");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(14);
		clubService.save(tempClub);
		
		tempClub = new Club();
		tempClub.setName("SV Darmstadt 98");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(89);
		clubService.save(tempClub);
		
		tempClub = new Club();
		tempClub.setName("FC Ingolstadt 04");
		tempClub.setLogoUrl("logo");
		tempClub.setExternID(90);
		clubService.save(tempClub);

		List<PlayerItem> allPlayerItems = webService.getAllPlayers();
		Map<Integer, Player> playersByComunioID = playerService
				.getPlayersByExternID();
		for (PlayerItem playerItem : allPlayerItems) {
			Player tempPlayer;
			if (playersByComunioID.containsKey(playerItem.getId())) {
				tempPlayer = playersByComunioID.get(playerItem.getId());
			} else {
				tempPlayer = new Player();
			}
			tempPlayer.setExternID(playerItem.getId());
			tempPlayer.setName(playerItem.getName());
			
			String norm = Normalizer.normalize(playerItem.getName(), Normalizer.Form.NFD);
			norm = norm.replaceAll("[^\\p{ASCII}]", "");
			
			if(!norm.equals(playerItem.getName())){
				tempPlayer.setNormalizedName(norm);
			}
			tempPlayer.setPrice(playerItem.getQuote());
			tempPlayer.setPoints(playerItem.getPoints());
			tempPlayer.setPosition(playerItem.getPosition());
			playerService.save(tempPlayer);
		}

	}
}
