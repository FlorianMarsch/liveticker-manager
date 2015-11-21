package de.fussballmanager.db.misc;

import de.fussball.live.ticker.event.Event;

public class ProcessedEvent extends Event{

	private String trainer ;

	public String getTrainer() {
		return trainer;
	}

	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}
	
}
