package de.florianmarsch.fussballmanager.db.entity.division;

import de.florianmarsch.fussballmanager.db.entity.division.QDivision;
import de.florianmarsch.fussballmanager.db.service.AbstractService;

public class DivisionService extends AbstractService<Division> {

	public DivisionService() {
		super(QDivision.division);
	}

	@Override
	public Division getNewInstance() {
		return new Division();
	}

}
