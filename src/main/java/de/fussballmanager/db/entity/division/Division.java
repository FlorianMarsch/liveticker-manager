package de.fussballmanager.db.entity.division;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import de.fussballmanager.db.entity.AbstractEntity;
import de.fussballmanager.db.entity.trainer.Trainer;

@Table
@Entity
public class Division extends AbstractEntity {

	private String name;
	private String description;
	private List<Trainer> trainers;
	
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

	public List<Trainer> getTrainers() {
		return trainers;
	}

	public void setTrainers(List<Trainer> trainers) {
		this.trainers = trainers;
	}

}
