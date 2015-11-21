package de.fussballmanager.db.misc;

import java.util.List;

import de.fussballmanager.db.entity.allTimeTable.AllTimeTable;
import de.fussballmanager.db.entity.match.Match;
import de.fussballmanager.db.entity.matchday.Matchday;

public class ProcessingResult {

	private Matchday matchday;
	private List<Match> matches;
	private List<ProcessedEvent> events;
	private List<AllTimeTable> table;
	
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
	public List<ProcessedEvent> getEvents() {
		return events;
	}
	public void setEvents(List<ProcessedEvent> events) {
		this.events = events;
	}
	public List<AllTimeTable> getTable() {
		return table;
	}
	public void setTable(List<AllTimeTable> table) {
		this.table = table;
	}
	
	
	
	
}
