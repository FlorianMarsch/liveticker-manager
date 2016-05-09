package de.florianmarsch.fussballmanager.db.entity.division;

import de.florianmarsch.fussballmanager.db.json.AbstractJSONProducer;

public class DivisionJSONProducer extends AbstractJSONProducer<Division>{

	public DivisionJSONProducer() {
		super(new DivisionService());
	}

}
