package de.florianmarsch.fussballmanager.db.entity.divisionalTable;

import java.util.List;

import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;
import de.florianmarsch.fussballmanager.db.entity.matchday.MatchdayService;
import de.florianmarsch.fussballmanager.db.json.AbstractJSONProducer;
import de.florianmarsch.fussballmanager.db.service.AbstractService;
import spark.Request;
import spark.Spark;

public class DivisionalTableJSONProducer extends AbstractJSONProducer<DivisionalTable>{

	private DivisionalTableService service;
	private MatchdayService matchdayService ;
	
	public DivisionalTableJSONProducer() {
		super(new DivisionalTableService());
	}
	
	@Override
	protected void register(AbstractService<DivisionalTable> aAbstractService, String aRoot) {
		super.register(aAbstractService, "standings/division");
	}

	
	@Override
	public void registerCustom() {
		
		service = (DivisionalTableService) getHandler().getService();
		matchdayService = new MatchdayService();
		
		
		Spark.get("/" + root + "/matchday/:number", (request, response) -> {
			
			Matchday matchday = getMatchday(request);

			List<DivisionalTable> found = service.getAllByMatchday(matchday);
			return toJson(found);
		});
	}


	private Matchday getMatchday(Request request) {
		Integer matchdayNumber = Integer.valueOf(request.params("number"));
		Matchday matchday = matchdayService.get(matchdayNumber);
		return matchday;
	}
	
}
