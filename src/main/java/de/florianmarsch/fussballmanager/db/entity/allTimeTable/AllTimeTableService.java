package de.florianmarsch.fussballmanager.db.entity.allTimeTable;

import java.util.ArrayList;
import java.util.List;

import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;
import de.florianmarsch.fussballmanager.db.service.AbstractService;

public class AllTimeTableService extends AbstractService<AllTimeTable> {

	public AllTimeTableService() {
		super(QAllTimeTable.allTimeTable);
	}

	@Override
	public AllTimeTable getNewInstance() {
		return new AllTimeTable();
	}

	public List<AllTimeTable> getAllByMatchday(Matchday aMatchday) {
		ArrayList<AllTimeTable> arrayList = new ArrayList<AllTimeTable>();
		List<AllTimeTable> all = getAll();
		for (AllTimeTable standing : all) {
			if(standing.getMatchday() != null && standing.getMatchday().equals(aMatchday)){
				arrayList.add(standing);
			}
		}
		
		return arrayList;
	}

}
