package de.fussballmanager.db.entity.allTimeTable;

import javax.persistence.Entity;
import javax.persistence.Table;

import de.fussballmanager.db.entity.AbstractEntity;
import de.fussballmanager.db.entity.matchday.Matchday;
import de.fussballmanager.db.entity.trainer.Trainer;

@Table
@Entity
public class AllTimeTable extends AbstractEntity {

	Matchday matchday;
	Trainer trainer;
	Integer goals;
	Integer goalsAgainst;
	Integer points;
	
	@Override
	public String getDisplayValue() {
		
		return matchday.getDisplayValue()+"-"+trainer.getDisplayValue()+" "+points;
	}

	
	
}
