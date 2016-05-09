package de.florianmarsch.fussballmanager.db.entity.club;

import java.util.Map;

import de.florianmarsch.fussballmanager.db.entity.club.QClub;
import de.florianmarsch.fussballmanager.db.service.AbstractService;

public class ClubService extends AbstractService<Club> {

	public ClubService() {
		super(QClub.club);
	}

	public Map<Integer,Club> getClubsByExternID() {
		return super.getAllOrderedInMap(QClub.club.externID);
	}

	@Override
	public Club getNewInstance() {
		return new Club();
	}

}
