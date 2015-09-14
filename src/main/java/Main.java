import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.net.URI;
import java.net.URISyntaxException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.json.JSONArray;
import org.json.JSONObject;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import spark.Session;
import static spark.Spark.get;

import com.heroku.sdk.jdbc.DatabaseUrl;

import de.fussballmanager.db.jpa.EmFactory;

public class Main {

	public static void main(String[] args) {

		port(Integer.valueOf(System.getenv("PORT")));
		staticFileLocation("/public");


		
		
		get("/intern/hello", (req, res) -> "Intern Hello World");
		get("/hello", (req, res) -> "Hello World");

		post("/loginaction", (request, response) -> {
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
				throw new RuntimeException("JSON could not be interpreted.", e);
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
		
		
		
		get("/db",
				(req, res) -> {
					Map<String, Object> attributes = new HashMap<>();
					try {

						EntityManager em = EmFactory.getEntityManager();
						em.getTransaction().begin();
						
						Query createQuery = em.createNativeQuery("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
						createQuery.executeUpdate();
						
						Query insertQuery = em.createNativeQuery("INSERT INTO ticks VALUES (now())");
						insertQuery.executeUpdate();
						
						Query selectQuery = em.createNativeQuery("SELECT tick FROM ticks");
						List resultList = selectQuery.getResultList();
						em.getTransaction().commit();
						ArrayList<String> output = new ArrayList<String>();
//						while (rs.next()) {
//							output.add("Read from DB: "
//									+ rs.getTimestamp("tick"));
//						}

						attributes.put("results", resultList);
						return new ModelAndView(attributes, "db.ftl");
					} catch (Exception e) {
						attributes.put("message", "There was an error: " + e);
						return new ModelAndView(attributes, "error.ftl");
					} finally {
					}
				}, new FreeMarkerEngine());

	}

}
