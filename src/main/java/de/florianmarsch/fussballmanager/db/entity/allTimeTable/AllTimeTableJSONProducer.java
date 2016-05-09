package de.florianmarsch.fussballmanager.db.entity.allTimeTable;

import de.florianmarsch.fussballmanager.db.json.AbstractJSONProducer;

public class AllTimeTableJSONProducer extends AbstractJSONProducer<AllTimeTable>{

	public AllTimeTableJSONProducer() {
		super(new AllTimeTableService());
	}

}
