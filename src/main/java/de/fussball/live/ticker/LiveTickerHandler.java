package de.fussball.live.ticker;

import java.util.ArrayList;
import java.util.List;

import de.fussball.live.ticker.event.Event;
import de.fussball.live.ticker.event.EventResolver;
import de.fussball.live.ticker.event.TickerResolver;

public class LiveTickerHandler {

	EventResolver eventResolver = new EventResolver();
	TickerResolver tickerResolver = new TickerResolver();
	PlayernameMatcher nameMatcher = new PlayernameMatcher();
	
	public LiveTickerHandler(){
	}
	
	public List<Event> getResolvedLiveTickerEvents(String gameday){
		List<Event> tempReturn = new ArrayList<Event>();
		
		String tempLive = tickerResolver.getLiveTicker(gameday);
		List<Event> events = eventResolver.getResolvedLiveTickerEvents(tempLive);
		
		for (Event event : events) {
			String name = event.getName();
			String resolvedName = nameMatcher.getMatchedName(name);
			if(resolvedName != null){
				event.setResolved(resolvedName);
				tempReturn.add(event);
			}
		}
		return tempReturn;
	}
	
}
