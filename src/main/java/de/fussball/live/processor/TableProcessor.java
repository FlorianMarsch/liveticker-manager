package de.fussball.live.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fussballmanager.db.entity.allTimeTable.AllTimeTable;
import de.fussballmanager.db.entity.match.Match;
import de.fussballmanager.db.entity.matchday.Matchday;
import de.fussballmanager.db.entity.trainer.Trainer;

public class TableProcessor {

	
	public List<AllTimeTable> getTable(List<Match> matchesUntil,
			Matchday currentMatchday) {
		Map<Trainer, AllTimeTable> allTimeTableMap = new HashMap<Trainer, AllTimeTable>();

		for (Match match : matchesUntil) {
			if (!match.isFake()) {
				Trainer trainer = match.getHome();
				AllTimeTable tableEntry = allTimeTableMap.get(trainer);
				if (tableEntry == null) {
					tableEntry = new AllTimeTable();
					tableEntry.setTrainer(trainer);
					tableEntry.setMatchday(currentMatchday);
					allTimeTableMap.put(trainer, tableEntry);
				}
				Integer goals = match.getHomeGoals();
				Integer goalsAgainst = match.getGuestGoals();
				Integer points = match.getHomePoints();
				if (match.isByeGame()) {
					tableEntry.addGoals(goals);
				} else {
					tableEntry.addGoals(goals);
					tableEntry.addGoalsAgainst(goalsAgainst);
					tableEntry.addPoints(points);
				}
			}
		}
		for (Match match : matchesUntil) {
			if (!match.isFake()) {
				Trainer trainer = match.getGuest();
				AllTimeTable tableEntry = allTimeTableMap.get(trainer);
				if (tableEntry == null) {
					tableEntry = new AllTimeTable();
					tableEntry.setTrainer(trainer);
					tableEntry.setMatchday(currentMatchday);
					allTimeTableMap.put(trainer, tableEntry);
				}

				Integer goals = match.getGuestGoals();
				Integer goalsAgainst = match.getHomeGoals();
				Integer points = match.getGuestPoints();
				if (match.isByeGame()) {
					tableEntry.addGoals(goals);
				} else {
					tableEntry.addGoals(goals);
					tableEntry.addGoalsAgainst(goalsAgainst);
					tableEntry.addPoints(points);
				}
			}
		}

		return new ArrayList<AllTimeTable>(allTimeTableMap.values());
	}

}
