package de.florianmarsch.fussballmanager.db.entity.division;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import de.florianmarsch.fussballmanager.db.entity.AbstractEntity;
import de.florianmarsch.fussballmanager.db.entity.trainer.Trainer;

@Table
@Entity
public class Division extends AbstractEntity implements Comparable<Division> {

	private String name;
	private String description;
	private List<Trainer> trainers = new ArrayList<Trainer>();
	
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

	@Override
	public int compareTo(Division o) {
		return getName().compareTo(o.getName());
	}

}
