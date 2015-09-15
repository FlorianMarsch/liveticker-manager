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

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
import de.fussballmanager.db.entity.tick.Tick;
import de.fussballmanager.db.service.TickService;

public class Main {

	public static void main(String[] args) {

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

		exception(RuntimeException.class, (e, request, response) -> {
			response.status(500);
			response.body("Ein Fehler ist aufgetreten. ");
		});

		get("/file", (req, res) -> {
			Map<String, Object> attributes = new HashMap<>();
			try {

				File createTempFile = File.createTempFile("common-io", "");
				String path = createTempFile.getAbsolutePath();

				String fileName = path + "/../cache/" ;
				File tempFile = new File(fileName);
				tempFile = new File(tempFile.getCanonicalPath());
				tempFile.mkdir();
				tempFile = new File(tempFile.getCanonicalPath() + "/test.data");
				tempFile.setWritable(Boolean.TRUE);
				tempFile.createNewFile();

				FileUtils.writeByteArrayToFile(tempFile, new Date().toString().getBytes(), true);
				String readFileToString = FileUtils.readFileToString(tempFile);
				List<String> resultList = new ArrayList<String>();
				resultList.add(tempFile.getCanonicalPath());
				resultList.add(readFileToString);
				attributes.put("results", resultList );
				return new ModelAndView(attributes, "db.ftl");
			} catch (Exception e) {
				e.printStackTrace();
				attributes.put("message", "There was an error: " + e);
				return new ModelAndView(attributes, "error.ftl");
			} finally {
			}
		}, new FreeMarkerEngine());

		get("/db",
				(req, res) -> {
					Map<String, Object> attributes = new HashMap<>();
					try {

						TickService tempTickService = new TickService();
						tempTickService.save(new Tick());
						List<Tick> resultList = tempTickService.getTicks();
						for (Tick tempTick : resultList) {
							tempTickService.save(tempTick);
						}
						
						attributes.put("results", resultList );
						return new ModelAndView(attributes, "db.ftl");
					} catch (Exception e) {
						attributes.put("message", "There was an error: " + e);
						return new ModelAndView(attributes, "error.ftl");
					} finally {
					}
				}, new FreeMarkerEngine());

	}

}
