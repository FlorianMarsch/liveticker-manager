package de.fussballmanager.db.entity.match;

import javax.persistence.Entity;
import javax.persistence.Table;

import de.fussballmanager.db.entity.AbstractEntity;
import de.fussballmanager.db.entity.matchday.Matchday;
import de.fussballmanager.db.entity.trainer.Trainer;

@Table
@Entity
public class Match extends AbstractEntity {

	private Matchday matchday;
	private Trainer guest;
	private Trainer home;
	private Integer guestGoals = 0;
	private Integer homeGoals = 0;

	
	@Override
	public String getDisplayValue() {
		return "#"+home.getHashTag()+"vs"+guest.getHashTag();
	}
	
	public Matchday getMatchday() {
		return matchday;
	}

	public void setMatchday(Matchday matchday) {
		this.matchday = matchday;
	}

	public Trainer getGuest() {
		return guest;
	}

	public void setGuest(Trainer guest) {
		this.guest = guest;
	}

	public Trainer getHome() {
		return home;
	}

	public void setHome(Trainer home) {
		this.home = home;
	}

	public Integer getGuestGoals() {
		return guestGoals;
	}

	public void setGuestGoals(Integer guestGoals) {
		this.guestGoals = guestGoals;
	}

	public Integer getHomeGoals() {
		return homeGoals;
	}

	public void setHomeGoals(Integer homeGoals) {
		this.homeGoals = homeGoals;
	}

	public Integer getHomePoints(){
		if(homeGoals > guestGoals){
			return 3;
		}else if(homeGoals < guestGoals){
			return 0;
		}else{
			return 1;
		}
	}
	
	public Integer getGuestPoints(){
		if(homeGoals < guestGoals){
			return 3;
		}else if(homeGoals > guestGoals){
			return 0;
		}else{
			return 1;
		}
	}
	
}
