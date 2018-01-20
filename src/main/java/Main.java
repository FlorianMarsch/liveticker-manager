import static spark.SparkBase.port;

import de.florianmarsch.fussballmanager.db.entity.allTimeTable.AllTimeTableJSONProducer;
import de.florianmarsch.fussballmanager.db.entity.division.DivisionJSONProducer;
import de.florianmarsch.fussballmanager.db.entity.divisionalTable.DivisionalTableJSONProducer;
import de.florianmarsch.fussballmanager.db.entity.lineup.LineUpJSONProducer;
import de.florianmarsch.fussballmanager.db.entity.match.MatchJSONProducer;
import de.florianmarsch.fussballmanager.db.entity.matchday.MatchdayJSONProducer;
import de.florianmarsch.fussballmanager.db.entity.trainer.TrainerJSONProducer;
import de.florianmarsch.fussballmanager.db.json.BindContext;

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
		new AllTimeTableJSONProducer().bindServices(ctx);
		new DivisionalTableJSONProducer().bindServices(ctx);

	}	
		

	private static void registerErrorHandler(ErrorHandler errorHandler) {
		errorHandler.register();
	}

}
