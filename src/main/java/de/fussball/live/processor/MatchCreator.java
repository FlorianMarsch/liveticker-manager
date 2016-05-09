package de.fussball.live.processor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import de.fussballmanager.db.entity.match.Match;
import de.fussballmanager.db.entity.match.MatchService;
import de.fussballmanager.db.entity.matchday.Matchday;
import de.fussballmanager.db.entity.matchday.MatchdayService;
import de.fussballmanager.db.entity.matchday.QMatchday;
import de.fussballmanager.db.entity.trainer.Trainer;

public class MatchCreator {

	MatchdayService matchdayService = new MatchdayService();
	MatchService matchService = new MatchService();
	
	
	public void createDivisionalMatches(List<Trainer> trainers, Matchday currentMatchday){
		Collections.shuffle(trainers);
		Trainer a = trainers.get(0);
		Trainer b = trainers.get(1);
		Trainer c = trainers.get(2);
		Trainer d = trainers.get(3);
		
		Integer number = currentMatchday.getNumber();
		List< Matchday> all = matchdayService.getAll();
		
		Collections.sort(all,new Comparator<Matchday>() {

			@Override
			public int compare(Matchday o1, Matchday o2) {
				// TODO Auto-generated method stub
				return o1.getNumber().compareTo(o2.getNumber());
			}
		});
		
		Matchday startingAt = null;
		for (Matchday gameDay : all) {
			if(startingAt == null && gameDay.getNumber() > number && gameDay.getModus().equals(currentMatchday.getModus())){
				startingAt = gameDay;
			}
		}
		
		Map<Integer, Matchday> allOrderedInMap = matchdayService.getAllOrderedInMap(QMatchday.matchday.number);
		Matchday first = allOrderedInMap.get(startingAt.getNumber());
		Matchday second = allOrderedInMap.get(startingAt.getNumber()+1);
		Matchday third= allOrderedInMap.get(startingAt.getNumber()+2);
		
		generateMatches(first, a,b,c,d);
		generateMatches(second, a,c,b,d);
		generateMatches(third, a,d,c,b);
	}


	private void generateMatches(Matchday aMatchday, Trainer a, Trainer b, Trainer c, Trainer d) {
		createMatch(aMatchday, a,b);
		createMatch(aMatchday, b,c);
	}


	private void createMatch(Matchday aMatchday, Trainer aHome, Trainer aGuest) {
		Match match = new Match();
		match.setHome(aHome);
		match.setGuest(aGuest);
		match.setMatchday(aMatchday);
		matchService.save(match);
	}
	
}
