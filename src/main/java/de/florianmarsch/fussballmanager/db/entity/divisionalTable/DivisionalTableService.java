package de.florianmarsch.fussballmanager.db.entity.divisionalTable;

import java.util.ArrayList;
import java.util.List;

import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;
import de.florianmarsch.fussballmanager.db.service.AbstractService;

public class DivisionalTableService extends AbstractService<DivisionalTable> {

	public DivisionalTableService() {
		super(QDivisionalTable.divisionalTable);
	}

	@Override
	public DivisionalTable getNewInstance() {
		return new DivisionalTable();
	}

	public List<DivisionalTable> getAllByMatchday(Matchday aMatchday) {
		ArrayList<DivisionalTable> arrayList = new ArrayList<DivisionalTable>();
		List<DivisionalTable> all = getAll();
		for (DivisionalTable standing : all) {
			if(standing.getMatchday() != null && standing.getMatchday().equals(aMatchday)){
				arrayList.add(standing);
			}
		}
		
		return arrayList;
	}

}
