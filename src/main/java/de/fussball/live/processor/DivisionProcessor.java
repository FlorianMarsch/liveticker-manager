package de.fussball.live.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.fussballmanager.db.entity.allTimeTable.AllTimeTable;
import de.fussballmanager.db.entity.division.Division;
import de.fussballmanager.db.entity.division.DivisionService;
import de.fussballmanager.db.entity.match.Match;
import de.fussballmanager.db.entity.match.MatchService;
import de.fussballmanager.db.entity.matchday.Matchday;
import de.fussballmanager.db.entity.trainer.Trainer;

public class DivisionProcessor {

	DivisionService divisionService = new DivisionService();
	MatchService matchService = new MatchService();

	public Map<Division, List<AllTimeTable>> getDivisionalTables(Matchday currentMatchday){
		List<Match> divisonalMatches = matchService.getDivisonalMatchesUntil(currentMatchday);
		return getDivisionalTables(currentMatchday, divisonalMatches);
	}
	
	public Map<Division, List<AllTimeTable>> getFinalDivisionalTables(Matchday currentMatchday){
		if(!currentMatchday.getDivisionFinals()){
			throw new IllegalArgumentException();
		}
		List<Match> divisonalMatches = matchService.getDivisonalMatchesUntil(currentMatchday);
		divisonalMatches.addAll(matchService.getAllByMatchday(currentMatchday));
		return getDivisionalTables(currentMatchday, divisonalMatches);
	}

	private Map<Division, List<AllTimeTable>> getDivisionalTables(Matchday currentMatchday,
			List<Match> divisonalMatches) {
		Map<Division, List<AllTimeTable>> returnMap = new HashMap<>();
		List<Division> all = divisionService.getAll();
		for (Division division : all) {
			List<Trainer> trainers = division.getTrainers();
			List<AllTimeTable> tableMap = getTable(trainers, divisonalMatches, currentMatchday);
			returnMap.put(division, tableMap);
		}
		return returnMap;
	}

	private List< AllTimeTable> getTable(List<Trainer> trainers, List<Match> divisonalMatches, Matchday currentMatchday) {
		Set<Match> affectedMatches = new HashSet<>();
		for (Match match : divisonalMatches) {
			for (Trainer trainer : trainers) {
				if(match.getHome().equals(trainer) || match.getGuest().equals(trainer)){
					affectedMatches.add(match);
				}
			}
		}
		
		return new TableProcessor().getTable(new ArrayList<>(affectedMatches), currentMatchday);
	}
}
