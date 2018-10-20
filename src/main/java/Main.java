import static spark.Spark.get;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import de.florianmarsch.fussballmanager.db.entity.division.DivisionJSONProducer;
import de.florianmarsch.fussballmanager.db.entity.lineup.LineUpJSONProducer;
import de.florianmarsch.fussballmanager.db.entity.match.Match;
import de.florianmarsch.fussballmanager.db.entity.match.MatchJSONProducer;
import de.florianmarsch.fussballmanager.db.entity.match.MatchService;
import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;
import de.florianmarsch.fussballmanager.db.entity.matchday.MatchdayJSONProducer;
import de.florianmarsch.fussballmanager.db.entity.matchday.MatchdayService;
import de.florianmarsch.fussballmanager.db.entity.trainer.TrainerJSONProducer;
import de.florianmarsch.fussballmanager.db.json.BindContext;
import de.florianmarsch.fussballmanager.live.processor.DivisionProcessor;
import de.florianmarsch.fussballmanager.live.processor.GamedayProcessor;
import de.florianmarsch.fussballmanager.live.processor.ProcessingResult;
import de.florianmarsch.fussballmanager.live.ticker.Event;
import de.florianmarsch.fussballmanager.live.ticker.GoalResolver;
import spark.ModelAndView;
import spark.Request;
import spark.template.freemarker.FreeMarkerEngine;

public class Main {

	public static void main(String[] args) {
		Long startTime = System.currentTimeMillis();
		System.out.println("Server started at " + startTime);

		port(Integer.valueOf(System.getenv("PORT")));
		staticFileLocation("/public");

		registerErrorHandler(new ErrorHandler());

		get("/the.appcache", (request, response) -> {
			response.type("text/cache-manifest");

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", startTime);
			return new ModelAndView(attributes, "offlineManifest.ftl");
		} , new FreeMarkerEngine());

		get("/live/:id", (request, response) -> {
			String param = ":id";
			Matchday aMatchday = getMatchday(request, param);
			JSONArray data = new JSONArray();

			GoalResolver gr = new GoalResolver();
			List<Event> resolvedEvents = gr.getGoals(aMatchday);

			for (Event tempEvent : resolvedEvents) {
				try {
					data.put(data.length(), tempEvent.toJSONObject());
				} catch (Exception e1) {
					throw new RuntimeException(e1);
				}
			}

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data.toString());
			return new ModelAndView(attributes, "json.ftl");
		} , new FreeMarkerEngine());

		BindContext ctx = new BindContext();

		new TrainerJSONProducer().bindServices(ctx);
		new MatchdayJSONProducer().bindServices(ctx);
		new MatchJSONProducer().bindServices(ctx);
		new LineUpJSONProducer().bindServices(ctx);
		new DivisionJSONProducer().bindServices(ctx);
		
		get("/leaderboard", (request, response) -> {
			Matchday aMatchday = getLiveGameDay();
			
			
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
			attributes.put("divisionalTables", process.getDivisionalTables().entrySet());

			return new ModelAndView(attributes, "leaderboard.ftl");
		} , new FreeMarkerEngine());
		
		
		get("/overview", (request, response) -> {
			Matchday aMatchday = getLiveGameDay();
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
			attributes.put("divisionalTables", process.getDivisionalTables().entrySet());

			return new ModelAndView(attributes, "overview.ftl");
		} , new FreeMarkerEngine());
		
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
			attributes.put("divisionalTables", process.getDivisionalTables().entrySet());

			return new ModelAndView(attributes, "overview.ftl");
		} , new FreeMarkerEngine());
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
			attributes.put("divisionalTables", process.getDivisionalTables().entrySet());

			return new ModelAndView(attributes, "overview.ftl");
		} , new FreeMarkerEngine());

		get("/view/:id", (request, response) -> {
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

			return new ModelAndView(attributes, "live.ftl");
		} , new FreeMarkerEngine());
		
		
		get("/view", (request, response) -> {
			Matchday aMatchday = getLiveGameDay();
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

			return new ModelAndView(attributes, "live.ftl");
		} , new FreeMarkerEngine());

		get("/screen/:id", (request, response) -> {
			String param = ":id";

			String id = request.params(param);
			Match match = new MatchService().getAllAsMap().get(id);
			Matchday aMatchday = match.getMatchday();

			GamedayProcessor gp = new GamedayProcessor();
			ProcessingResult process = null;
			if (aMatchday.getProcessed()) {
				process = gp.review(aMatchday);
			} else {
				process = gp.process(aMatchday);
			}

			
			
			Map<String, Object> attributes = new HashMap<>();

			List<Match> matches = process.getMatches();
			Match currentMatch = null;
			for (Match temp : matches) {
				if (temp.getId().equals(id)) {
					attributes.put("x", temp);
					currentMatch = temp;
				}
			}

			List<Event> events = process.getEvents();
			List<Event> currentEvents = new ArrayList<Event> ();
			for (Event e : events) {
				if (e.getMatch().getId().equals(id)) {
					
					currentEvents.add(e);
				}
			}
			attributes.put("events", currentEvents);
			
			
			return new ModelAndView(attributes, "screen.ftl");
		} , new FreeMarkerEngine());

	}

	static Matchday getLiveGameDay() {
		List<Matchday> all = new MatchdayService().getAll();
		Collections.sort(all);
		Matchday aMatchday = null;
		for (Matchday tempMatchday : all) {
			if(!tempMatchday.getProcessed()){
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
