package de.fussballmanager.db.entity.division;

import de.fussballmanager.db.json.AbstractJSONProducer;

public class DivisionJSONProducer extends AbstractJSONProducer<Division>{

	public DivisionJSONProducer() {
		super(new DivisionService());
	}

}
