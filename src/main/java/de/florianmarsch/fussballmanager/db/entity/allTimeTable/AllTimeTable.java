package de.florianmarsch.fussballmanager.db.entity.allTimeTable;

import javax.persistence.Entity;
import javax.persistence.Table;

import de.florianmarsch.fussballmanager.db.entity.AbstractEntity;
import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;
import de.florianmarsch.fussballmanager.db.entity.trainer.Trainer;

@Table
@Entity
public class AllTimeTable extends AbstractEntity implements Comparable<AllTimeTable> {

	Matchday matchday;
	Trainer trainer;
	Integer goals = 0;
	Integer goalsAgainst = 0;
	Integer points = 0;
	Integer won = 0;
	Integer draw = 0;
	Integer loose = 0;

	@javax.persistence.Transient
	Integer better;

	@javax.persistence.Transient
	Integer betterPoints;

	public Matchday getMatchday() {
		return matchday;
	}

	public void setMatchday(Matchday matchday) {
		this.matchday = matchday;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	public Integer getGoals() {
		return goals;
	}

	public void setGoals(Integer goals) {
		this.goals = goals;
	}

	public Integer getGoalsAgainst() {
		return goalsAgainst;
	}

	public void setGoalsAgainst(Integer goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getWon() {
		return won;
	}

	public void setWon(Integer won) {
		this.won = won;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getLoose() {
		return loose;
	}

	public void setLoose(Integer loose) {
		this.loose = loose;
	}

	@Override
	public int compareTo(AllTimeTable o) {
		int macthdayCompare = matchday.getNumber().compareTo(o.getMatchday().getNumber());
		if (macthdayCompare != 0) {
			return macthdayCompare;
		}
		int pointsCompare = points.compareTo(o.getPoints());
		if (pointsCompare != 0) {
			return pointsCompare * -1;
		}
		int goalCompare = goals.compareTo(o.getGoals());
		if (goalCompare != 0) {
			return goalCompare * -1;
		}
		int goalAginstCompare = goalsAgainst.compareTo(o.getGoalsAgainst());
		if (goalAginstCompare != 0) {
			return goalAginstCompare;
		}
		return 0;
	}

	public void addGoals(Integer someGoals) {
		goals = goals + someGoals;

	}

	public void addGoalsAgainst(Integer someGoals) {
		goalsAgainst = goalsAgainst + someGoals;

	}

	public void addPoints(Integer somePoints) {
		switch (somePoints) {
		case 3:
			incrementWins();
			break;
		case 1:
			incrementDraw();
			break;
		case 0:
			incrementLoose();
			break;
		default:
			break;
		}

	}

	public void incrementWins() {
		points = points + 3;
		won = won + 1;

	}

	public void incrementDraw() {
		points = points + 1;
		draw = draw + 1;

	}

	public void incrementLoose() {
		points = points + 0;
		loose = loose + 1;

	}

	public Integer getBetter() {
		return better;
	}

	public void setBetter(Integer better) {
		this.better = better;
	}

	public Integer getBetterPoints() {
		return betterPoints;
	}

	public void setBetterPoints(Integer betterPoints) {
		this.betterPoints = betterPoints;
	}

}
