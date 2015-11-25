import static spark.Spark.get;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.freemarker.FreeMarkerEngine;
import de.fussballmanager.db.entity.club.ClubJSONProducer;
import de.fussballmanager.db.entity.matchday.Matchday;
import de.fussballmanager.db.entity.matchday.MatchdayJSONProducer;
import de.fussballmanager.db.entity.matchday.MatchdayService;
import de.fussballmanager.db.entity.player.PlayerJSONProducer;
import de.fussballmanager.db.entity.trainer.TrainerJSONProducer;
import de.fussballmanager.db.json.BindContext;
import de.fussballmanager.db.misc.GamedayProcessor;
import de.fussballmanager.db.misc.GoalResolver;
import de.fussballmanager.db.misc.ProcessedEvent;
import de.fussballmanager.db.misc.ProcessingResult;

public class Main {

	public static void main(String[] args) {
		Long startTime = System.currentTimeMillis();
		System.out.println("Server started at "+startTime);

		port(Integer.valueOf(System.getenv("PORT")));
		staticFileLocation("/public");

		registerErrorHandler(new ErrorHandler());

		get("/the.appcache", (request, response) -> {
			response.type("text/cache-manifest");

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", startTime);
			return new ModelAndView(attributes, "offlineManifest.ftl");
		}, new FreeMarkerEngine());

		get("/live/:id", (request, response) -> {
			String param = ":id";
			Matchday aMatchday = getMatchday(request, param);
			JSONArray data = new JSONArray();

			GoalResolver gr = new GoalResolver();
			List<ProcessedEvent> resolvedEvents = gr.getGoals(aMatchday);

			for (ProcessedEvent tempEvent : resolvedEvents) {
				try {
					data.put(data.length(), tempEvent.toJSONObject());
				} catch (Exception e1) {
					throw new RuntimeException(e1);
				}
			}

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data.toString());
			return new ModelAndView(attributes, "json.ftl");
		}, new FreeMarkerEngine());

		BindContext ctx = new BindContext();

		new ClubJSONProducer().bindServices(ctx);
		new TrainerJSONProducer().bindServices(ctx);
		new MatchdayJSONProducer().bindServices(ctx);
		new PlayerJSONProducer().bindServices(ctx);

		get("/overview/:id", (request, response) -> {
			String param = ":id";
			Matchday aMatchday = getMatchday(request, param);
			GamedayProcessor gp = new GamedayProcessor();
			ProcessingResult process = null;
			if (aMatchday.getProcessed()) {
				process = gp.review(aMatchday);
			} else {
				process = gp.process(aMatchday);
			}

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("matchday", process.getMatchday());
			attributes.put("events", process.getEvents());
			attributes.put("results", process.getMatches());
			attributes.put("allTimeTable", process.getTable());

			return new ModelAndView(attributes, "overview.ftl");
		}, new FreeMarkerEngine());
		get("/process/:id", (request, response) -> {
			String param = ":id";
			Matchday aMatchday = getMatchday(request, param);

			GamedayProcessor gp = new GamedayProcessor();
			ProcessingResult process = gp.process(aMatchday, Boolean.TRUE);

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("matchday", process.getMatchday());
			attributes.put("events", process.getEvents());
			attributes.put("results", process.getMatches());
			attributes.put("allTimeTable", process.getTable());

			return new ModelAndView(attributes, "overview.ftl");
		}, new FreeMarkerEngine());

	}

	private static void registerErrorHandler(ErrorHandler errorHandler) {
		errorHandler.register();
	}

	private static Matchday getMatchday(Request request, String param) {
		Integer currentGameDay = Integer.valueOf(request.params(param));
		Matchday aMatchday = new MatchdayService().getMatchdaysByNumber().get(
				Integer.valueOf(currentGameDay));
		return aMatchday;
	}

}
