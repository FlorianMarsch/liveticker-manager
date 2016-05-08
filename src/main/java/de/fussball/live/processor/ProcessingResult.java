package de.fussball.live.processor;

import java.util.List;
import java.util.Map;

import de.fussball.live.ticker.Event;
import de.fussballmanager.db.entity.allTimeTable.AllTimeTable;
import de.fussballmanager.db.entity.division.Division;
import de.fussballmanager.db.entity.match.Match;
import de.fussballmanager.db.entity.matchday.Matchday;

public class ProcessingResult {

	private Matchday matchday;
	private List<Match> matches;
	private List<Event> events;
	private List<AllTimeTable> table;
	private Map<Division, List<AllTimeTable>> divisionalTables;
	
	public Matchday getMatchday() {
		return matchday;
	}
	public void setMatchday(Matchday matchday) {
		this.matchday = matchday;
	}
	public List<Match> getMatches() {
		return matches;
	}
	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	public List<AllTimeTable> getTable() {
		return table;
	}
	public void setTable(List<AllTimeTable> table) {
		this.table = table;
	}
	public Map<Division, List<AllTimeTable>> getDivisionalTables() {
		return divisionalTables;
	}
	public void setDivisionalTables(Map<Division, List<AllTimeTable>> divisionalTables) {
		this.divisionalTables = divisionalTables;
	}
	
	
	
	
}
