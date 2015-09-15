import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.persistence.tools.file.FileUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import spark.Session;
import static spark.Spark.get;

import com.heroku.sdk.jdbc.DatabaseUrl;
import com.mysema.query.jpa.impl.JPAQuery;

import de.fussballmanager.db.entity.tick.QTick;
import de.fussballmanager.db.entity.tick.Tick;
import de.fussballmanager.db.jpa.EmFactory;

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

						EntityManager em = EmFactory.getEntityManager();
						em.getTransaction().begin();

						em.persist(new Tick());
						

						JPAQuery selectQuery = new JPAQuery(em).from(QTick.tick);
					
						List<Tick> resultList = selectQuery.list(QTick.tick);
						em.getTransaction().commit();

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
