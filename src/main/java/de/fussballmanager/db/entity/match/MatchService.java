package de.fussballmanager.db.entity.match;

import java.util.ArrayList;
import java.util.List;

import de.fussballmanager.db.entity.matchday.Matchday;
import de.fussballmanager.db.service.AbstractService;

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

}
