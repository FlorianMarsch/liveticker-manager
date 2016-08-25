package de.florianmarsch.fussballmanager.db.entity.lineup;

import java.io.InputStream;
import java.net.URL;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.florianmarsch.fussballmanager.db.entity.trainer.Trainer;
import de.florianmarsch.fussballmanager.db.service.AbstractService;
import de.florianmarsch.fussballmanager.db.entity.lineup.QLineUp;
import de.florianmarsch.fussballmanager.db.entity.player.Player;
import de.florianmarsch.fussballmanager.db.entity.player.PlayerService;
import de.florianmarsch.fussballmanager.db.entity.player.QPlayer;

public class LineUpService extends AbstractService<LineUp> {

	public LineUpService() {
		super(QLineUp.lineUp);
	}

	@Override
	public LineUp getNewInstance() {
		return new LineUp();
	}

	public Set<String> reciveCurrentLineUp(Trainer trainer) {
		try {
			String html = null;
			String urlString = trainer.getUrl();
			InputStream is = (InputStream) new URL(urlString).getContent();
			html = IOUtils.toString(is, "UTF-8");

			JSONArray lineUps = new JSONObject(html).getJSONArray("data");

			Set<String> teamList = new HashSet<String>();
			for (int i = 0; i < lineUps.length(); i++) {
				JSONObject player = lineUps.getJSONObject(i);

				if (player.has("match")) {
					String name = player.getJSONObject("match").getString("name");
					teamList.add(name);
				}

			}
			System.out.println(teamList);
			return teamList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("abbruch", e);
		}
	}

}
