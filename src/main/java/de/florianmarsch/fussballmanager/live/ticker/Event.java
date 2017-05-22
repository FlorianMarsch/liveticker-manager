package de.florianmarsch.fussballmanager.live.ticker;

import org.json.JSONException;
import org.json.JSONObject;

import de.florianmarsch.fussballmanager.db.entity.match.Match;
import de.florianmarsch.fussballmanager.db.entity.trainer.Trainer;

public class Event {
	private String id;
	private String name;
	private String event;
	
	private Trainer trainer;
	private Match match;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", event=" + event + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

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
			tempJsonPlayer.put("name", getName());
			tempJsonPlayer.put("type", getEvent());
			tempJsonPlayer.put("owner", trainer.getName());
			tempJsonPlayer.put("hashTag", "#"+ trainer.getHashTag());
			tempJsonPlayer.put("gameHashTag", match.getHashTag());
			tempJsonPlayer.put("result", match.getHomeGoals()+":"+match.getGuestGoals());
			tempJsonPlayer.put("bye", match.isByeGame());
			tempJsonPlayer.put("fake", match.isFake());
			tempJsonPlayer.put("match", match.getId());
			
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
