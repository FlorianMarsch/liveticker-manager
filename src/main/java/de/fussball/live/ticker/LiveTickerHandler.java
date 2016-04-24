package de.fussball.live.ticker;

import java.util.ArrayList;
import java.util.List;

import de.fussball.live.ticker.event.EventResolver;
import de.fussballmanager.db.entity.matchday.Matchday;
import de.fussballmanager.db.entity.tick.Tick;

public class LiveTickerHandler {

	EventResolver eventResolver = new EventResolver();
	PlayernameMatcher nameMatcher = new PlayernameMatcher();
	
	public LiveTickerHandler(){
	}
	
	public List<Tick> getResolvedLiveTickerEvents(Matchday aMatchday){
		List<Tick> tempReturn = new ArrayList<Tick>();
		
		List<Tick> events = eventResolver.getLiveTickerEvents(aMatchday);
		
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
