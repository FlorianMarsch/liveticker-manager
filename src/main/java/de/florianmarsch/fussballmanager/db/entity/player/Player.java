package de.florianmarsch.fussballmanager.db.entity.player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.florianmarsch.fussballmanager.db.entity.AbstractEntity;
import de.florianmarsch.fussballmanager.db.entity.club.Club;

@Table
@Entity
public class Player extends AbstractEntity {

	private String comunio;
	private String feedmonster;

	private String name;
	private String abbreviationName;
	private String position;

	public String getComunio() {
		return comunio;
	}

	public void setComunio(String comunio) {
		this.comunio = comunio;
	}

	public String getFeedmonster() {
		return feedmonster;
	}

	public void setFeedmonster(String feedmonster) {
		this.feedmonster = feedmonster;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviationName() {
		return abbreviationName;
	}

	public void setAbbreviationName(String abbreviationName) {
		this.abbreviationName = abbreviationName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public String getDisplayValue() {
		return getName();
	}

}
