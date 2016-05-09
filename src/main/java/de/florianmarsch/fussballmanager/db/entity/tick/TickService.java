package de.florianmarsch.fussballmanager.db.entity.tick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;
import de.florianmarsch.fussballmanager.db.entity.tick.QTick;
import de.florianmarsch.fussballmanager.db.service.AbstractService;

public class TickService extends AbstractService<Tick> {

	public TickService() {
		super(QTick.tick);
	}

	@Override
	public Tick getNewInstance() {
		return new Tick();
	}
	
	public List<Tick> getAllByMatchday(Matchday aMatchday){
		List<Tick> response = new ArrayList<>();
		List<Tick> all = getAll();
		for (Tick tick : all) {
			if(tick.getMatchdayNumber() == aMatchday.getNumber()){
				response.add(tick);
			}
		}
		return response;
	}

	@Override
	public Map<Object, Object> getCachedMap() {
		return new HashMap<>();
	}
}
