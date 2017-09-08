package de.florianmarsch.fussballmanager.db.entity.matchday;

import javax.persistence.Entity;
import javax.persistence.Table;

import de.florianmarsch.fussballmanager.db.entity.AbstractEntity;

@Table
@Entity
public class Matchday extends AbstractEntity implements Comparable<Matchday>{

	private Integer number;
	private Boolean processed = Boolean.FALSE;
	private String results;
	private String modus;
	private Boolean divisionFinals = Boolean.FALSE;
	
	public String getModus() {
		return modus;
	}
	public void setModus(String modus) {
		this.modus = modus;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Boolean getProcessed() {
		return processed;
	}
	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}
	public String getResults() {
		return results;
	}
	public void setResults(String results) {
		this.results = results;
	}
	
	public String getStatus() {
		if(processed){
			return modus + " - Inactive, Matchday in the past";
		}else{
			return modus + " - Still active or Matchday in the future";
		}
		
	}
	public Boolean getDivisionFinals() {
		return divisionFinals;
	}
	public void setDivisionFinals(Boolean divisionFinals) {
		this.divisionFinals = divisionFinals;
	}
	@Override
	public int compareTo(Matchday other) {
		return getNumber().compareTo(other.getNumber());
	}
	
}
