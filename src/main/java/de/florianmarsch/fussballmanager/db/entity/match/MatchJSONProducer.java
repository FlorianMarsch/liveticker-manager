package de.florianmarsch.fussballmanager.db.entity.match;

import java.util.List;

import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;
import de.florianmarsch.fussballmanager.db.entity.matchday.MatchdayService;
import de.florianmarsch.fussballmanager.db.json.AbstractJSONProducer;
import spark.Request;

public class MatchJSONProducer extends AbstractJSONProducer<Match> {

	private MatchService service;
	private MatchdayService matchdayService;

	public MatchJSONProducer() {
		super(new MatchService());
	}

	@Override
	public void registerCustom() {

		service = (MatchService) getHandler().getService();
		matchdayService = new MatchdayService();

		get("/" + root + "/matchday/:number", (request, response) -> {

			Matchday matchday = getMatchday(request);

			List<Match> found = service.getAllByMatchday(matchday);
			return toJson(found);
		});
	}

	private Matchday getMatchday(Request request) {
		Integer matchdayNumber = Integer.valueOf(request.params("number"));
		Matchday matchday = matchdayService.get(matchdayNumber);
		return matchday;
	}
}
