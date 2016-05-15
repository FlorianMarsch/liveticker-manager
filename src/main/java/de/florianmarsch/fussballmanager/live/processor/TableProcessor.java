package de.florianmarsch.fussballmanager.live.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.florianmarsch.fussballmanager.db.entity.allTimeTable.AllTimeTable;
import de.florianmarsch.fussballmanager.db.entity.match.Match;
import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;
import de.florianmarsch.fussballmanager.db.entity.trainer.Trainer;

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

		ArrayList<AllTimeTable> response = new ArrayList<AllTimeTable>(allTimeTableMap.values());
		Collections.sort(response, new Comparator<AllTimeTable>() {

			@Override
			public int compare(AllTimeTable o1, AllTimeTable o2) {
				int compareTo = o2.getPoints().compareTo(o1.getPoints());
				if(compareTo != 0){
					return compareTo;
				}
				compareTo = o2.getGoals().compareTo(o1.getGoals());
				if(compareTo != 0){
					return compareTo;
				}
				return o1.getGoalsAgainst().compareTo(o2.getGoalsAgainst());
			}
		});
		
		return response;
	}

}
