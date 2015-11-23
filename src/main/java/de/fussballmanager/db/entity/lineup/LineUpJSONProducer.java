package de.fussballmanager.db.entity.lineup;

import de.fussballmanager.db.json.AbstractJSONProducer;

public class LineUpJSONProducer extends AbstractJSONProducer<LineUp>{

	public LineUpJSONProducer() {
		
		
		
		super(new LineUpService());
	}

}
