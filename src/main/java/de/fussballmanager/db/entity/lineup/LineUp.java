package de.fussballmanager.db.entity.lineup;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.fussballmanager.db.entity.AbstractEntity;
import de.fussballmanager.db.entity.matchday.Matchday;
import de.fussballmanager.db.entity.trainer.Trainer;

@Table
@Entity
public class LineUp extends AbstractEntity {

	private Matchday matchday;
	private Trainer trainer;
	
	@ElementCollection
	private Set<String> players = new HashSet<String>();
	
	
	
	public Matchday getMatchday() {
		return matchday;
	}



	public void setMatchday(Matchday matchday) {
		this.matchday = matchday;
	}



	public Trainer getTrainer() {
		return trainer;
	}



	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}



	public Set<String> getPlayers() {
		return players;
	}



	public void setPlayers(Set<String> players) {
		this.players = players;
	}



	@Override
	public String getDisplayValue() {
		return getId();
	}

}
