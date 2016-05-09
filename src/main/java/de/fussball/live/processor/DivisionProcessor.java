package de.fussball.live.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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

	public List<Division> processDownswing(Map<Division, List<AllTimeTable>> divisionalTables) {

		List<Division> order = new ArrayList<>(divisionalTables.keySet());
		Collections.sort(order,new Comparator<Division>() {

			@Override
			public int compare(Division division1, Division division2) {
				return division1.getName().compareTo(division2.getName());
			}
		});
		Iterator<Division> iterator = order.iterator();
		Trainer relegater = null;
		Division last = null;
		while (iterator.hasNext()) {
			Division division = iterator.next();
			List<AllTimeTable> table = divisionalTables.get(division);
			Collections.sort(table);
			
			if(relegater!=null){
				division.getTrainers().add(relegater);
				last.getTrainers().remove(relegater);
				relegater = null;
			}
			
			
			Trainer tempClimber = table.get(0).getTrainer();
			relegater = table.get(table.size()).getTrainer();
			if(last != null){
				last.getTrainers().add(tempClimber);
				division.getTrainers().remove(tempClimber);
			}
			
			last = division;
		}
		for (Division division : order) {
			divisionService.save(division);
		}
		return order;
	}
}
