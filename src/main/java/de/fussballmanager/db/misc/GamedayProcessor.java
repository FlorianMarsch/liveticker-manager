package de.fussballmanager.db.misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public ProcessingResult process(Integer number){
		ProcessingResult processingResult = new ProcessingResult();
		
		Matchday currentMatchday =getMatchday(number);
		processingResult.setMatchday(currentMatchday);
		
		List<Match> currentMatches = getMatches(currentMatchday);
		processingResult.setMatches(currentMatches);
		
		List<ProcessedEvent> events = goalResolver.getGoals(number);
		processingResult.setEvents(events);
		for (ProcessedEvent processedEvent : events) {
			addEventToMatch(processedEvent,currentMatches );
		}
		
		List<Match> matchesUntil = getMatchesUntil(currentMatchday);
		matchesUntil.addAll(currentMatches);
		List<AllTimeTable> table = getTable(matchesUntil, currentMatchday);
		Collections.sort(table);
		processingResult.setTable(table);
		
		return processingResult;
		
	}

	private List<AllTimeTable> getTable(List<Match> matchesUntil, Matchday currentMatchday) {
		Map<Trainer, AllTimeTable> allTimeTableMap = new HashMap<Trainer, AllTimeTable>();
		
		for (Match match : matchesUntil) {
			Trainer trainer = match.getHome();
			AllTimeTable tableEntry = allTimeTableMap.get(trainer);
			if(tableEntry == null){
				tableEntry = new AllTimeTable();
				tableEntry.setTrainer(trainer);
				tableEntry.setMatchday(currentMatchday);
				allTimeTableMap.put(trainer, tableEntry);
			}
			
			Integer goals = match.getHomeGoals();
			Integer goalsAgainst =match.getGuestGoals();
			Integer points = match.getHomePoints();
			tableEntry.addGoals(goals);
			tableEntry.addGoalsAgainst(goalsAgainst);
			tableEntry.addPoints(points);
		}
		for (Match match : matchesUntil) {
			Trainer trainer = match.getGuest();
			AllTimeTable tableEntry = allTimeTableMap.get(trainer);
			if(tableEntry == null){
				tableEntry = new AllTimeTable();
				tableEntry.setTrainer(trainer);
				tableEntry.setMatchday(currentMatchday);
				allTimeTableMap.put(trainer, tableEntry);
			}
			
			Integer goals = match.getGuestGoals();
			Integer goalsAgainst =match.getHomeGoals();
			Integer points = match.getGuestPoints();
			tableEntry.addGoals(goals);
			tableEntry.addGoalsAgainst(goalsAgainst);
			tableEntry.addPoints(points);
		}
		
		return new ArrayList<AllTimeTable>(allTimeTableMap.values());
	}

	private void addEventToMatch(ProcessedEvent processedEvent,
			List<Match> currentMatches) {
		String trainer = processedEvent.getTrainer();
		for (Match match : currentMatches) {
			if(match.getHome().getName().equals(trainer)
					|| match.getGuest().getName().equals(trainer)){
				addEventToMatch(processedEvent, match);
				return;
			}
		}
		
	}

	private void addEventToMatch(ProcessedEvent processedEvent, Match match) {
		String trainer = processedEvent.getTrainer();
		Boolean isHome = match.getHome().getName().equals(trainer);
		Boolean isGoal = processedEvent.getEvent().equals("Goal")||processedEvent.getEvent().equals("Penalty");
		Integer homeGoals = match.getHomeGoals();
		Integer guestGoals = match.getGuestGoals();
		
		if(isHome && isGoal){
			match.setHomeGoals(homeGoals +1);
		}
		if(!isHome && isGoal){
			match.setGuestGoals(guestGoals +1);
		}
		if(isHome && !isGoal){
			match.setGuestGoals(guestGoals +1);
		}
		if(!isHome && !isGoal){
			match.setHomeGoals(homeGoals +1);
		}
	}

	private List<Match> getMatchesUntil(Matchday currentMatchday) {
		List<Match> currentMatches = new ArrayList<Match>();
		
		List<Match> all = matchService.getAll();
		for (Match match : all) {
			if(match.getMatchday().getNumber() < currentMatchday.getNumber()){
				currentMatches.add(match);
			}
		}
		return currentMatches;
	}
	
	private List<Match> getMatches(Matchday currentMatchday) {
		List<Match> currentMatches = new ArrayList<Match>();
		
		List<Match> all = matchService.getAll();
		for (Match match : all) {
			if(match.getMatchday().equals(currentMatchday)){
				currentMatches.add(match);
			}
		}
		return currentMatches;
	}

	private Matchday getMatchday(Integer number) {
		List<Matchday> allMatchdays = matchdayService.getAll();
		for (Matchday matchday : allMatchdays) {
			if(matchday.getNumber().equals(number)){
				return matchday;
			}
		}
		return null;
	}
	
	
}
