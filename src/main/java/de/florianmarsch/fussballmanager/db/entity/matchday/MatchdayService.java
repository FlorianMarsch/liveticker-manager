package de.florianmarsch.fussballmanager.db.entity.matchday;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import de.florianmarsch.fussballmanager.db.entity.matchday.QMatchday;
import de.florianmarsch.fussballmanager.db.service.AbstractService;

public class MatchdayService extends AbstractService<Matchday> {

	public MatchdayService() {
		super(QMatchday.matchday);
	}

	public Map<Integer, Matchday> getMatchdaysByNumber() {
		return super.getAllOrderedInMap(QMatchday.matchday.number);
	}

	@Override
	public Matchday getNewInstance() {
		return new Matchday();
	}

	public Matchday getNextMatchday(Matchday currentMatchday) {
		List<Matchday> all = getAll();

		Collections.sort(all, new Comparator<Matchday>() {

			@Override
			public int compare(Matchday o1, Matchday o2) {
				return o1.getNumber().compareTo(o2.getNumber());
			}
		});
		Integer number = currentMatchday.getNumber();
		Matchday startingAt = null;
		for (Matchday gameDay : all) {
			if (startingAt == null && gameDay.getNumber() > number
					&& gameDay.getModus().equals(currentMatchday.getModus())) {
				startingAt = gameDay;
			}
		}
		return startingAt;
	}

	public Matchday get(Integer number) {
		return getAllOrderedInMap(QMatchday.matchday.number).get(number);
	}
}
