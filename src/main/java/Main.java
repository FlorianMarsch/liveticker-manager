import static spark.SparkBase.port;

import java.util.Collections;
import java.util.List;

import de.florianmarsch.fussballmanager.db.entity.division.DivisionJSONProducer;
import de.florianmarsch.fussballmanager.db.entity.lineup.LineUpJSONProducer;
import de.florianmarsch.fussballmanager.db.entity.match.MatchJSONProducer;
import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;
import de.florianmarsch.fussballmanager.db.entity.matchday.MatchdayJSONProducer;
import de.florianmarsch.fussballmanager.db.entity.matchday.MatchdayService;
import de.florianmarsch.fussballmanager.db.entity.trainer.TrainerJSONProducer;
import de.florianmarsch.fussballmanager.db.json.BindContext;
import spark.Request;

public class Main {

	public static void main(String[] args) {
		Long startTime = System.currentTimeMillis();
		System.out.println("Server started at " + startTime);

		port(Integer.valueOf(System.getenv("PORT")));

		registerErrorHandler(new ErrorHandler());
	
		

		BindContext ctx = new BindContext();

		new TrainerJSONProducer().bindServices(ctx);
		new MatchdayJSONProducer().bindServices(ctx);
		new MatchJSONProducer().bindServices(ctx);
		new LineUpJSONProducer().bindServices(ctx);
		new DivisionJSONProducer().bindServices(ctx);

	}	
		
	static Matchday getLiveGameDay() {
		List<Matchday> all = new MatchdayService().getAll();
		Collections.sort(all);
		Matchday aMatchday = null;
		for (Matchday tempMatchday : all) {
			if (!tempMatchday.getProcessed()) {
				aMatchday = tempMatchday;
				break;
			}
		}
		return aMatchday;
	}

	private static void registerErrorHandler(ErrorHandler errorHandler) {
		errorHandler.register();
	}

	private static Matchday getMatchday(Request request, String param) {
		Integer currentGameDay = Integer.valueOf(request.params(param));
		Matchday aMatchday = new MatchdayService().getMatchdaysByNumber().get(Integer.valueOf(currentGameDay));
		return aMatchday;
	}

}
