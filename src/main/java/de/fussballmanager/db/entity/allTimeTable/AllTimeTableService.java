package de.fussballmanager.db.entity.allTimeTable;

import de.fussballmanager.db.service.AbstractService;

public class AllTimeTableService extends AbstractService<AllTimeTable> {

	public AllTimeTableService() {
		super(QAllTimeTable.allTimeTable);
	}

	@Override
	public AllTimeTable getNewInstance() {
		return new AllTimeTable();
	}

}
