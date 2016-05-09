package de.fussball.live.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import de.fussball.live.ticker.Event;
import de.fussball.live.ticker.GoalResolver;
import de.fussballmanager.db.entity.allTimeTable.AllTimeTable;
import de.fussballmanager.db.entity.division.Division;
import de.fussballmanager.db.entity.match.Match;
import de.fussballmanager.db.entity.match.MatchService;
import de.fussballmanager.db.entity.matchday.Matchday;
import de.fussballmanager.db.entity.matchday.MatchdayService;
import de.fussballmanager.db.entity.trainer.Trainer;

public class GamedayProcessor {

	MatchdayService matchdayService = new MatchdayService();
	MatchService matchService = new MatchService();
	GoalResolver goalResolver = new GoalResolver();
	TableProcessor tableProcessor = new TableProcessor();
	DivisionProcessor divisionProcessor = new DivisionProcessor();
	MatchCreator matchCreator = new MatchCreator();

	public ProcessingResult process(Matchday currentMatchday) {
		return process(currentMatchday, Boolean.FALSE);
	}

	public ProcessingResult process(Matchday currentMatchday, Boolean saveProcessing) {
		ProcessingResult processingResult = new ProcessingResult();

		processingResult.setMatchday(currentMatchday);
		if (currentMatchday.getProcessed()) {
			throw new IllegalArgumentException("matchday already processed");
		}
		if (saveProcessing) {
			currentMatchday.setProcessed(Boolean.TRUE);
			matchdayService.save(currentMatchday);
		}

		List<Match> currentMatches = getMatches(currentMatchday);
		processingResult.setMatches(currentMatches);

		List<Event> events = goalResolver.getGoals(currentMatchday);
		for (Event processedEvent : events) {
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

		List<Match> matchesUntil = matchService.getMatchesUntil(currentMatchday);
		matchesUntil.addAll(currentMatches);
		List<AllTimeTable> table = tableProcessor.getTable(matchesUntil, currentMatchday);
		Collections.sort(table);
		processingResult.setTable(table);

		Map<Division, List<AllTimeTable>> divisionalTables;
		divisionalTables = divisionProcessor.getDivisionalTables(currentMatchday);
		processingResult.setDivisionalTables(divisionalTables);

		if (currentMatchday.getDivisionFinals() && saveProcessing) {
			List<Division> newDivisions = divisionProcessor.processDownswing(divisionalTables);
			for (Division division : newDivisions) {
				List<Trainer> trainers = division.getTrainers();
				if (trainers.size() == 4) {

					matchCreator.createDivisionalMatches(trainers, currentMatchday);

				} else {
					throw new IllegalArgumentException(division.getName());
				}
			}
		}

		return processingResult;

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
		allHappendMatchesUntil.addAll(matchService.getMatchesUntil(aMatchday));

		List<AllTimeTable> table = tableProcessor.getTable(allHappendMatchesUntil, aMatchday);
		Collections.sort(table);
		processingResult.setTable(table);

		processingResult.setEvents(new ArrayList<Event>());

		return processingResult;
	}

}
