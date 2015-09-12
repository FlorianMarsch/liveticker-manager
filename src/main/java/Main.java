import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONObject;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import static spark.Spark.get;

import com.heroku.sdk.jdbc.DatabaseUrl;

public class Main {

	public static void main(String[] args) {

		Map<String,String> session = new HashMap<String, String>();
		
		port(Integer.valueOf(System.getenv("PORT")));
		staticFileLocation("/public");

		get("/hello", (req, res) -> "Hello World");

		post("/loginaction", (request, response) -> {
			String body = request.body();
			if (body == null || body.isEmpty()) {
				throw new IllegalArgumentException("body is null or empty");
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
				throw new RuntimeException("JSON could not be interpreted.", e);
			}
			session.put(request.ip(), name);
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("url", "hello");

			return new ModelAndView(attributes, "redirect.ftl");
		}, new FreeMarkerEngine());

		get("/", (request, response) -> {
			
			String name = session.get(request.ip());
			if(name == null){
				name="World";
			}
			
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("message", "Hello "+name+"!");

			return new ModelAndView(attributes, "login.ftl");
		}, new FreeMarkerEngine());

		get("/db",
				(req, res) -> {
					Connection connection = null;
					Map<String, Object> attributes = new HashMap<>();
					try {
						connection = DatabaseUrl.extract().getConnection();

						Statement stmt = connection.createStatement();
						stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
						stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
						ResultSet rs = stmt
								.executeQuery("SELECT tick FROM ticks");

						ArrayList<String> output = new ArrayList<String>();
						while (rs.next()) {
							output.add("Read from DB: "
									+ rs.getTimestamp("tick"));
						}

						attributes.put("results", output);
						return new ModelAndView(attributes, "db.ftl");
					} catch (Exception e) {
						attributes.put("message", "There was an error: " + e);
						return new ModelAndView(attributes, "error.ftl");
					} finally {
						if (connection != null)
							try {
								connection.close();
							} catch (SQLException e) {
							}
					}
				}, new FreeMarkerEngine());

	}

}
