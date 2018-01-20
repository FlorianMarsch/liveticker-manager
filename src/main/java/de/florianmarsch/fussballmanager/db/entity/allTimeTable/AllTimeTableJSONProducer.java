package de.florianmarsch.fussballmanager.db.entity.allTimeTable;

import java.util.List;

import de.florianmarsch.fussballmanager.db.entity.match.Match;
import de.florianmarsch.fussballmanager.db.entity.match.MatchService;
import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;
import de.florianmarsch.fussballmanager.db.entity.matchday.MatchdayService;
import de.florianmarsch.fussballmanager.db.json.AbstractJSONProducer;
import de.florianmarsch.fussballmanager.db.service.AbstractService;
import spark.Request;
import spark.Spark;

public class AllTimeTableJSONProducer extends AbstractJSONProducer<AllTimeTable>{

	private AllTimeTableService service;
	private MatchdayService matchdayService ;
	
	public AllTimeTableJSONProducer() {
		super(new AllTimeTableService());
	}
	
	@Override
	protected void register(AbstractService<AllTimeTable> aAbstractService, String aRoot) {
		super.register(aAbstractService, "standing");
	}

	
	@Override
	public void registerCustom() {
		
		service = (AllTimeTableService) getHandler().getService();
		matchdayService = new MatchdayService();
		
		
		Spark.get("/" + root + "/matchday/:number", (request, response) -> {
			
			Matchday matchday = getMatchday(request);

			List<AllTimeTable> found = service.getAllByMatchday(matchday);
			return toJson(found);
		});
	}


	private Matchday getMatchday(Request request) {
		Integer matchdayNumber = Integer.valueOf(request.params("number"));
		Matchday matchday = matchdayService.get(matchdayNumber);
		return matchday;
	}
	
}
