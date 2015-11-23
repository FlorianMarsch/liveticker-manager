package de.fussballmanager.db.entity.matchday;

import javax.persistence.Entity;
import javax.persistence.Table;

import de.fussballmanager.db.entity.AbstractEntity;

@Table
@Entity
public class Matchday extends AbstractEntity{

	private Integer number;
	private Boolean processed = Boolean.FALSE;
	private String results;
	
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
	
	@Override
	public String getDisplayValue() {
		return String.valueOf(number);
	}
	
	public String getStatus() {
		if(processed){
			return "Inactive, Matchday in the past";
		}else{
			return "Still active or Matchday in the future";
		}
		
	}
	
}
