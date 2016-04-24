package de.fussballmanager.db.entity.tick;

import javax.persistence.Entity;
import javax.persistence.Table;

import de.fussballmanager.db.entity.AbstractEntity;

@Table
@Entity
public class Tick extends AbstractEntity {

	private int matchdayNumber;
	private String event;
	private String externId;
	private String name;
	private String resolvedName;
	
	
	
	public String getEvent() {
		return event;
	}



	public void setEvent(String event) {
		this.event = event;
	}



	public String getExternId() {
		return externId;
	}



	public void setExternId(String externId) {
		this.externId = externId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getResolvedName() {
		return resolvedName;
	}



	public void setResolvedName(String resolvedName) {
		this.resolvedName = resolvedName;
	}



	@Override
	public String getDisplayValue() {
		return getId();
	}



	public int getMatchdayNumber() {
		return matchdayNumber;
	}



	public void setMatchdayNumber(int matchdayNumber) {
		this.matchdayNumber = matchdayNumber;
	}

}
