import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
import de.fussball.kader.ClassicKaderFactory;
import de.fussball.live.ticker.LiveTickerHandler;
import de.fussball.live.ticker.event.Event;
import de.fussballmanager.db.entity.club.ClubJSONProducer;
import de.fussballmanager.db.entity.match.Match;
import de.fussballmanager.db.entity.match.MatchService;
import de.fussballmanager.db.entity.matchday.Matchday;
import de.fussballmanager.db.entity.matchday.MatchdayJSONProducer;
import de.fussballmanager.db.entity.matchday.MatchdayService;
import de.fussballmanager.db.entity.player.Player;
import de.fussballmanager.db.entity.player.PlayerJSONProducer;
import de.fussballmanager.db.entity.player.PlayerService;
import de.fussballmanager.db.entity.tick.Tick;
import de.fussballmanager.db.entity.tick.TickService;
import de.fussballmanager.db.entity.trainer.QTrainer;
import de.fussballmanager.db.entity.trainer.Trainer;
import de.fussballmanager.db.entity.trainer.TrainerJSONProducer;
import de.fussballmanager.db.entity.trainer.TrainerService;
import de.fussballmanager.db.json.BindContext;
import de.fussballmanager.db.misc.GamedayProcessor;
import de.fussballmanager.db.misc.ProcessingResult;
import de.fussballmanager.db.service.AbstractService;
import de.fussballmanager.scheduler.Bootstrap;

public class Main {

	public static void main(String[] args) {

		System.out.println("Server started");

		port(Integer.valueOf(System.getenv("PORT")));
		staticFileLocation("/public");

		get("/intern/hello", (req, res) -> "Intern Hello World");
		get("/hello", (req, res) -> "Hello World");

		post("/loginaction",
				(request, response) -> {
					String body = request.body();
					if (body == null || body.isEmpty()) {
						throw new RuntimeException("body is null or empty");
					}
					String name = "?";
					try {
						JSONArray bodyJson = new JSONArray(body);
						for (int i = 0; i < bodyJson.length(); i++) {
							JSONObject tempSubmit = bodyJson.getJSONObject(i);
							if (tempSubmit.has("name")
									&& tempSubmit.getString("name") != null) {
								name = tempSubmit.getString("name");
							}
						}
					} catch (Exception e) {
						throw new RuntimeException(
								"JSON could not be interpreted.", e);
					}
					Map<String, Object> attributes = new HashMap<>();
					attributes.put("url", "hello");

					return new ModelAndView(attributes, "redirect.ftl");
				}, new FreeMarkerEngine());

		get("/", (request, response) -> {

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("message", "Hello World!");
			request.session(true);
			return new ModelAndView(attributes, "login.ftl");
		}, new FreeMarkerEngine());

		get("/live/:gameday",
				(request, response) -> {

					LiveTickerHandler liveTicker = new LiveTickerHandler();
					String currentGameDay = request.params(":gameday");
					JSONArray data = new JSONArray();
					if (currentGameDay != null) {

						List<Event> resolvedEvents = liveTicker
								.getResolvedLiveTickerEvents(currentGameDay);

						for (Event tempEvent : resolvedEvents) {
							try {
								JSONObject tempJsonPlayer = new JSONObject();
								tempJsonPlayer.put("id", tempEvent.getId());
								tempJsonPlayer.put("name",
										tempEvent.getResolved());
								tempJsonPlayer.put("event",
										tempEvent.getEvent());
								data.put(data.length(), tempJsonPlayer);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}

					}
					Map<String, Object> attributes = new HashMap<>();
					attributes.put("data", data.toString());
					request.session(true);
					return new ModelAndView(attributes, "json.ftl");
				}, new FreeMarkerEngine());

		get("/resolvedgoals/:gameday",
				(request, response) -> {

					String currentGameDay = request.params(":gameday");
					JSONArray data = new JSONArray();
					if (currentGameDay != null) {
						LiveTickerHandler liveTicker = new LiveTickerHandler();
						ClassicKaderFactory ckf = new ClassicKaderFactory();

						Map<String, Set<String>> allPlayer = ckf.getAll();

						List<Event> resolvedEvents = liveTicker
								.getResolvedLiveTickerEvents(currentGameDay);

						
						Matchday aMatchday = new MatchdayService().getMatchdaysByNumber().get(Integer.valueOf(currentGameDay));
						Map<String, Trainer> allTrainer = new TrainerService().getAllOrderedInMap(QTrainer.trainer.name);
						List<Match> matches = new MatchService().getAllByMatchday(aMatchday);
						for (Event tempEvent : resolvedEvents) {
							try {
								for (String trainer : allPlayer.keySet()) {
									Set<String> team = allPlayer.get(trainer);

									if (team.contains(tempEvent.getResolved())) {
										
										
										Trainer trainerObj = allTrainer.get(trainer);
										Match current = null;
										for (Match tempMatch : matches) {
											if(tempMatch.getHome().equals(trainerObj) || tempMatch.getGuest().equals(trainerObj)){
												current = tempMatch;
											}
										}
										String gameHashTag = "#"+current.getHome().getHashTag()+"vs"+current.getGuest().getHashTag();
										
										JSONObject tempJsonPlayer = new JSONObject();
										tempJsonPlayer.put("id",
												tempEvent.getId());
										tempJsonPlayer.put("name",
												tempEvent.getResolved());
										tempJsonPlayer.put("type",
												tempEvent.getEvent());
										tempJsonPlayer.put("owner", trainer);
										tempJsonPlayer.put("hashTag", "#"+trainerObj.getHashTag());
										tempJsonPlayer.put("gameHashTag", gameHashTag);
										data.put(data.length(), tempJsonPlayer);
									}

								}

							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}

					}
					Map<String, Object> attributes = new HashMap<>();
					attributes.put("data", data.toString());
					request.session(true);
					return new ModelAndView(attributes, "json.ftl");
				}, new FreeMarkerEngine());

		get("/kader/:id", (request, response) -> {

			String id = request.params(":id");
			JSONArray data = new JSONArray();
			if (id != null) {
				ClassicKaderFactory ckf = new ClassicKaderFactory();
				Set<String> set = ckf.get(id);
				for (String value : set) {
					data.put(value);
				}
			}
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data.toString());
			request.session(true);
			return new ModelAndView(attributes, "json.ftl");
		}, new FreeMarkerEngine());

		get("/all", (request, response) -> {

			PlayerService playerservice = new PlayerService();

			List<Player> all = playerservice.getAll();
			JSONArray data = new JSONArray();

			for (Player tempPlayer : all) {
				try {
					JSONObject tempJsonPlayer = new JSONObject();
					tempJsonPlayer.put("id", tempPlayer.getId());
					tempJsonPlayer.put("extern", tempPlayer.getExternID());
					tempJsonPlayer.put("name", tempPlayer.getDisplayName());
					tempJsonPlayer.put("price", tempPlayer.getPrice());
					tempJsonPlayer.put("position", tempPlayer.getPosition());
					tempJsonPlayer.put("points", tempPlayer.getPoints());
					data.put(data.length(), tempJsonPlayer);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			Map<String, Object> attributes = new HashMap<>();
			attributes.put("data", data.toString());
			request.session(true);
			return new ModelAndView(attributes, "json.ftl");
		}, new FreeMarkerEngine());

		exception(RuntimeException.class, (e, request, response) -> {
			response.status(500);
			response.body("Ein Fehler ist aufgetreten. ");
		});

		get("/file",
				(req, res) -> {
					Map<String, Object> attributes = new HashMap<>();
					try {

						File createTempFile = File.createTempFile("common-io",
								"");
						String path = createTempFile.getAbsolutePath();

						String fileName = path + "/../cache/";
						File tempFile = new File(fileName);
						tempFile = new File(tempFile.getCanonicalPath());
						tempFile.mkdir();
						tempFile = new File(tempFile.getCanonicalPath()
								+ "/test.data");
						tempFile.setWritable(Boolean.TRUE);
						tempFile.createNewFile();

						FileUtils.writeByteArrayToFile(tempFile, new Date()
								.toString().getBytes(), true);
						String readFileToString = FileUtils
								.readFileToString(tempFile);
						List<String> resultList = new ArrayList<String>();
						resultList.add(tempFile.getCanonicalPath());
						resultList.add(readFileToString);
						attributes.put("results", resultList);
						return new ModelAndView(attributes, "db.ftl");
					} catch (Exception e) {
						e.printStackTrace();
						attributes.put("message", "There was an error: " + e);
						return new ModelAndView(attributes, "error.ftl");
					} finally {
					}
				}, new FreeMarkerEngine());

		get("/db", (req, res) -> {
			Map<String, Object> attributes = new HashMap<>();
			try {
				new Bootstrap().init();
				AbstractService<Tick> tempTickService = new TickService();
				List<Tick> resultList = tempTickService.getAll();

				attributes.put("results", resultList);
				return new ModelAndView(attributes, "db.ftl");
			} catch (Exception e) {
				attributes.put("message", "There was an error: " + e);
				return new ModelAndView(attributes, "error.ftl");
			} finally {
			}
		}, new FreeMarkerEngine());

		
		BindContext ctx = new BindContext();
		
		new ClubJSONProducer().bindServices(ctx);
		new TrainerJSONProducer().bindServices(ctx);
		new MatchdayJSONProducer().bindServices(ctx);
		new PlayerJSONProducer().bindServices(ctx);
		
		
		get("/overview/:id", (request, response) -> {
			Integer id = Integer.valueOf(request.params(":id"));
			GamedayProcessor gp = new GamedayProcessor();
			ProcessingResult process = gp.process(id);
			
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("matchday", process.getMatchday());
			attributes.put("events", process.getEvents());
			attributes.put("results", process.getMatches());
			attributes.put("allTimeTable", process.getTable());
			
			return new ModelAndView(attributes, "overview.ftl");
		}, new FreeMarkerEngine());
		get("/process/:id", (request, response) -> {
			Integer id = Integer.valueOf(request.params(":id"));
			GamedayProcessor gp = new GamedayProcessor();
			ProcessingResult process = gp.process(id, Boolean.TRUE);
			
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("matchday", process.getMatchday());
			attributes.put("events", process.getEvents());
			attributes.put("results", process.getMatches());
			attributes.put("allTimeTable", process.getTable());
			
			return new ModelAndView(attributes, "overview.ftl");
		}, new FreeMarkerEngine());
		
	}

}
