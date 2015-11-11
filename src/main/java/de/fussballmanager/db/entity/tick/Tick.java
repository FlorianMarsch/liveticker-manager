package de.fussballmanager.db.entity.tick;

import javax.persistence.Entity;
import javax.persistence.Table;

import de.fussballmanager.db.entity.AbstractEntity;

@Table
@Entity
public class Tick extends AbstractEntity {

	@Override
	public String getDisplayValue() {
		return getId();
	}

}
