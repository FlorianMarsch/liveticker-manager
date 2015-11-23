package de.fussballmanager.db.misc;

import org.json.JSONException;
import org.json.JSONObject;

import de.fussball.live.ticker.event.Event;
import de.fussballmanager.db.entity.match.Match;
import de.fussballmanager.db.entity.trainer.Trainer;

public class ProcessedEvent extends Event {

	private Trainer trainer;
	private Match match;
	public Trainer getTrainer() {
		return trainer;
	}
	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}
	public Match getMatch() {
		return match;
	}
	public void setMatch(Match match) {
		this.match = match;
	}

	public JSONObject toJSONObject(){
		try {
			JSONObject tempJsonPlayer = new JSONObject();
			tempJsonPlayer.put("id", getId());
			tempJsonPlayer.put("name", getResolved());
			tempJsonPlayer.put("type", getEvent());
			tempJsonPlayer.put("owner", trainer.getName());
			tempJsonPlayer.put("hashTag", "#"+ trainer.getHashTag());
			tempJsonPlayer.put("gameHashTag", match.getDisplayValue());
			tempJsonPlayer.put("result", match.getHomeGoals()+":"+match.getGuestGoals());
			tempJsonPlayer.put("bye", match.isByeGame());
			tempJsonPlayer.put("fake", match.isFake());
			
			return tempJsonPlayer;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	
	
	public void addEventToMatch() {
		Boolean isHome = match.getHome().equals(trainer);
		Boolean isGoal = getEvent().equals("Goal")||getEvent().equals("Penalty");
		Integer homeGoals = match.getHomeGoals();
		Integer guestGoals = match.getGuestGoals();
		
		if(isHome && isGoal){
			match.setHomeGoals(homeGoals +1);
		}
		if(!isHome && isGoal){
			match.setGuestGoals(guestGoals +1);
		}
		if(isHome && !isGoal){
			match.setGuestGoals(guestGoals +1);
		}
		if(!isHome && !isGoal){
			match.setHomeGoals(homeGoals +1);
		}
	}
	
}
