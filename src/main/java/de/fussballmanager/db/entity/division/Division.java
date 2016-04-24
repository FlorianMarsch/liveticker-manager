package de.fussballmanager.db.entity.division;

import javax.persistence.Entity;
import javax.persistence.Table;

import de.fussballmanager.db.entity.AbstractEntity;

@Table
@Entity
public class Division extends AbstractEntity {

	private String name;
	private String description;
	
	@Override
	public String getDisplayValue() {
		return "Division "+name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}



}
