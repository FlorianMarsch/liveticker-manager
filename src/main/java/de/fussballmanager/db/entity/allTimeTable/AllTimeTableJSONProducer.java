package de.fussballmanager.db.entity.allTimeTable;

import de.fussballmanager.db.json.AbstractJSONProducer;

public class AllTimeTableJSONProducer extends AbstractJSONProducer<AllTimeTable>{

	public AllTimeTableJSONProducer() {
		super(new AllTimeTableService());
	}

}
