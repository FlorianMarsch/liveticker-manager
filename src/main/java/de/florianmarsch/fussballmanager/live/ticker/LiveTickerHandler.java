package de.florianmarsch.fussballmanager.live.ticker;

import java.util.ArrayList;
import java.util.List;

import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;
import de.florianmarsch.fussballmanager.db.entity.tick.Tick;
import de.florianmarsch.fussballmanager.db.entity.tick.TickService;

public class LiveTickerHandler {

	TickService ts = new TickService();
	PlayernameMatcher nameMatcher = new PlayernameMatcher();
	
	public LiveTickerHandler(){
	}
	
	public List<Tick> getResolvedLiveTickerEvents(Matchday aMatchday){
		List<Tick> tempReturn = new ArrayList<Tick>();
		
		List<Tick> events = ts.getAllByMatchday(aMatchday);
		
		for (Tick event : events) {
			String name = event.getName();
			String resolvedName = nameMatcher.getMatchedName(name);
			if(resolvedName != null){
				event.setResolvedName(resolvedName);
				tempReturn.add(event);
			}
		}
		return tempReturn;
	}
	
}
