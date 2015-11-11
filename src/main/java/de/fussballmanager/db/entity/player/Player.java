package de.fussballmanager.db.entity.player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.fussballmanager.db.entity.AbstractEntity;
import de.fussballmanager.db.entity.club.Club;

@Table
@Entity
public class Player extends AbstractEntity {

	@Column
	@NotNull
	private String name;

	@Column
	private String normalizedName;

	@Column
	@NotNull
	private Integer externID;

	@Column
	private Integer price;

	@ManyToOne
	private Club club;

	@Column
	private Integer points = 0;

	@Column
	private Integer goals = 0;

	@Column
	private Integer owngoals = 0;

	@Column
	private Integer red = 0;

	@Column
	private Integer yellow = 0;

	@Column
	private Integer yellowRed = 0;

	@Column
	private String status;

	@Column
	private String position;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNormalizedName() {
		return normalizedName;
	}

	public void setNormalizedName(String normalizedName) {
		this.normalizedName = normalizedName;
	}

	public Integer getExternID() {
		return externID;
	}

	public void setExternID(Integer aExternID) {
		this.externID = aExternID;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getGoals() {
		return goals;
	}

	public void setGoals(Integer goals) {
		this.goals = goals;
	}

	public Integer getOwngoals() {
		return owngoals;
	}

	public void setOwngoals(Integer owngoals) {
		this.owngoals = owngoals;
	}

	public Integer getRed() {
		return red;
	}

	public void setRed(Integer red) {
		this.red = red;
	}

	public Integer getYellow() {
		return yellow;
	}

	public void setYellow(Integer yellow) {
		this.yellow = yellow;
	}

	public Integer getYellowRed() {
		return yellowRed;
	}

	public void setYellowRed(Integer yellowRed) {
		this.yellowRed = yellowRed;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDisplayName(){
		if(getNormalizedName() != null){
			return getNormalizedName();
		}else{
			return getName();
		}
	}

	@Override
	public String getDisplayValue() {
		return getDisplayName();
	}
	
	
}
