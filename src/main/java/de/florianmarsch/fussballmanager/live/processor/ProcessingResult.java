package de.florianmarsch.fussballmanager.live.processor;

import java.util.List;
import java.util.Map;

import de.florianmarsch.fussballmanager.db.entity.allTimeTable.AllTimeTable;
import de.florianmarsch.fussballmanager.db.entity.division.Division;
import de.florianmarsch.fussballmanager.db.entity.match.Match;
import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;
import de.florianmarsch.fussballmanager.db.entity.trainer.Trainer;
import de.florianmarsch.fussballmanager.live.ticker.Event;

public class ProcessingResult {

	private Matchday matchday;
	private List<Match> matches;
	private List<Event> events;
	private List<AllTimeTable> tableDayBefore;
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
	public List<AllTimeTable> getTableDayBefore() {
		return tableDayBefore;
	}
	public void setTableDayBefore(List<AllTimeTable> tableDayBefore) {
		this.tableDayBefore = tableDayBefore;
	}
	
	
	public void applyTableDayBefore(List<AllTimeTable> tableDayBefore){
		if(tableDayBefore == null || tableDayBefore.isEmpty()){
			return;
		}
		
		for (AllTimeTable allTimeTable : table) {
			Trainer trainer = allTimeTable.getTrainer();
			int position = table.indexOf(allTimeTable);
			
			AllTimeTable tableBefore = null;
			for (AllTimeTable allTimeTableBefore : tableDayBefore) {
				if(allTimeTableBefore.getTrainer().equals(trainer)){
					tableBefore = allTimeTableBefore;
				}
			}
			int positionBefore = tableDayBefore.indexOf(tableBefore);
			int delta = positionBefore - position;
			allTimeTable.setBetter(delta);
		}
	}
	
}
