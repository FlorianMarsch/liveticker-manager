package de.florianmarsch.fussballmanager.live.processor;

import java.util.List;

import de.florianmarsch.fussballmanager.db.entity.match.Match;
import de.florianmarsch.fussballmanager.db.entity.match.MatchService;
import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;
import de.florianmarsch.fussballmanager.db.entity.matchday.MatchdayService;
import de.florianmarsch.fussballmanager.db.entity.trainer.Trainer;

public class MatchCreator {

	MatchdayService matchdayService = new MatchdayService();
	MatchService matchService = new MatchService();
	
	
	public void createDivisionalMatches(List<Trainer> trainers, Matchday currentMatchday){
		Trainer a = trainers.get(0);
		Trainer b = trainers.get(1);
		Trainer c = trainers.get(2);
		Trainer d = trainers.get(3);
		
		Matchday startingAt = getMatchdayService().getNextMatchday(currentMatchday);
		if(startingAt == null){
			//last Game reached
			return;
		}
		
		Matchday first = getMatchdayService().get(startingAt.getNumber());
		Matchday second = getMatchdayService().get(startingAt.getNumber()+1);
		Matchday third= getMatchdayService().get(startingAt.getNumber()+2);
		
		generateMatches(first, a,b,c,d);
		generateMatches(second, a,c,b,d);
		generateMatches(third, a,d,c,b);
	}

	
	void generateMatches(Matchday aMatchday, Trainer a, Trainer b, Trainer c, Trainer d) {
		createMatch(aMatchday, a,b);
		createMatch(aMatchday, c,d);
	}

	void createMatch(Matchday aMatchday, Trainer aHome, Trainer aGuest) {
		Match match = new Match();
		match.setHome(aHome);
		match.setGuest(aGuest);
		match.setMatchday(aMatchday);
		
		match.setHomeGoals(Double.valueOf(Math.random()*5).intValue());
		match.setGuestGoals(Double.valueOf(Math.random()*5).intValue());
		getMatchService().save(match);
	}


	MatchService getMatchService() {
		return matchService;
	}
	
	MatchdayService getMatchdayService() {
		return matchdayService;
	}
	
}
