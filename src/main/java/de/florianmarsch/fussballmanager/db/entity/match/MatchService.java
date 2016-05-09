package de.florianmarsch.fussballmanager.db.entity.match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;
import de.florianmarsch.fussballmanager.db.service.AbstractService;
import de.florianmarsch.fussballmanager.db.entity.match.QMatch;

public class MatchService extends AbstractService<Match> {

	public MatchService() {
		super(QMatch.match);
	}

	@Override
	public Match getNewInstance() {
		return new Match();
	}
	
	public List<Match> getAllByMatchday(Matchday aMatchday){
		
		ArrayList<Match> arrayList = new ArrayList<Match>();
		List<Match> all = getAll();
		for (Match match : all) {
			if(match.getMatchday().equals(aMatchday)){
				arrayList.add(match);
			}
		}
		
		return arrayList;
	}
	
	public List<Match> getMatchesUntil(Matchday currentMatchday) {
		List<Match> currentMatches = new ArrayList<Match>();

		for (Match match : getAll()) {
			if (match.getMatchday().getNumber() < currentMatchday.getNumber()) {
				if (match.getMatchday().getModus()
						.equals(currentMatchday.getModus())) {
					currentMatches.add(match);
				}
			}
		}
		return currentMatches;
	}
	
	public List<Match> getDivisonalMatchesUntil(Matchday currentMatchday) {
		List<Match> currentMatches = new ArrayList<Match>();
		List<Match> matchesUntil = getMatchesUntil(currentMatchday);
		
		Collections.sort(matchesUntil, new Comparator<Match>() {

			@Override
			public int compare(Match match1, Match match2) {
				return match1.getMatchday().getNumber().compareTo(match2.getMatchday().getNumber());
			}
		});
		
		for (Match match : matchesUntil) {
			currentMatches.add(match);
			if(match.getMatchday().getDivisionFinals()){
				currentMatches.clear();
			}
		}

		return currentMatches;
	}
	

}
