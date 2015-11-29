package de.fussballmanager.db.misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.fussballmanager.db.entity.allTimeTable.AllTimeTable;
import de.fussballmanager.db.entity.match.Match;
import de.fussballmanager.db.entity.match.MatchService;
import de.fussballmanager.db.entity.matchday.Matchday;
import de.fussballmanager.db.entity.matchday.MatchdayService;
import de.fussballmanager.db.entity.trainer.Trainer;

public class GamedayProcessor {

	MatchdayService matchdayService = new MatchdayService();
	MatchService matchService = new MatchService();

	GoalResolver goalResolver = new GoalResolver();

	public ProcessingResult process(Matchday currentMatchday) {
		return process(currentMatchday, Boolean.FALSE);
	}

	public ProcessingResult process(Matchday currentMatchday,
			Boolean saveProcessing) {
		ProcessingResult processingResult = new ProcessingResult();

		processingResult.setMatchday(currentMatchday);
		if (currentMatchday.getProcessed()) {
			throw new RuntimeException("matchday already processed");
		}
		if (saveProcessing) {
			currentMatchday.setProcessed(Boolean.TRUE);
			matchdayService.save(currentMatchday);
		}

		List<Match> currentMatches = getMatches(currentMatchday);
		processingResult.setMatches(currentMatches);

		List<ProcessedEvent> events = goalResolver.getGoals(currentMatchday);
		for (ProcessedEvent processedEvent : events) {
			Integer pos = currentMatches.indexOf(processedEvent.getMatch());
			Match theMatch = currentMatches.get(pos);
			processedEvent.setMatch(theMatch);
			processedEvent.addEventToMatch();
		}
		processingResult.setEvents(events);
		if (saveProcessing) {
			for (Match match : currentMatches) {
				matchService.save(match);
			}
		}

		List<Match> matchesUntil = getMatchesUntil(currentMatchday);
		matchesUntil.addAll(currentMatches);
		List<AllTimeTable> table = getTable(matchesUntil, currentMatchday);
		Collections.sort(table);
		processingResult.setTable(table);

		return processingResult;

	}

	private List<AllTimeTable> getTable(List<Match> matchesUntil,
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

	private List<Match> getMatchesUntil(Matchday currentMatchday) {
		List<Match> currentMatches = new ArrayList<Match>();

		List<Match> all = matchService.getAll();
		for (Match match : all) {
			if (match.getMatchday().getNumber() < currentMatchday.getNumber()) {
				if (match.getMatchday().getModus()
						.equals(currentMatchday.getModus())) {
					currentMatches.add(match);
				}
			}
		}
		return currentMatches;
	}

	private List<Match> getMatches(Matchday currentMatchday) {
		List<Match> currentMatches = new ArrayList<Match>();

		List<Match> all = matchService.getAll();
		for (Match match : all) {
			if (match.getMatchday().equals(currentMatchday)) {
				currentMatches.add(match);
			}
		}
		return currentMatches;
	}

	public ProcessingResult review(Matchday aMatchday) {
		ProcessingResult processingResult = new ProcessingResult();
		processingResult.setMatchday(aMatchday);

		List<Match> currentMatches = getMatches(aMatchday);
		processingResult.setMatches(currentMatches);

		List<Match> allHappendMatchesUntil = new ArrayList<Match>();
		allHappendMatchesUntil.addAll(currentMatches);
		allHappendMatchesUntil.addAll(getMatchesUntil(aMatchday));

		List<AllTimeTable> table = getTable(allHappendMatchesUntil, aMatchday);
		Collections.sort(table);
		processingResult.setTable(table);

		processingResult.setEvents(new ArrayList<ProcessedEvent>());

		return processingResult;
	}

}
