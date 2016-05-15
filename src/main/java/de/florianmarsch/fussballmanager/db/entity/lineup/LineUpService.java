package de.florianmarsch.fussballmanager.db.entity.lineup;

import java.io.InputStream;
import java.net.URL;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
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

	PlayerService ps = new PlayerService();
	Map<String, Player> players = ps.getAllOrderedInMap(
			QPlayer.player.name);
	
	public LineUpService() {
		super(QLineUp.lineUp);
	}

	@Override
	public LineUp getNewInstance() {
		return new LineUp();
	}
	
	public Set<String> reciveCurrentLineUp(Trainer trainer){
		try {
			String html = null;
			String urlString = trainer.getUrl();
			InputStream is = (InputStream) new URL(urlString).getContent();
			html = IOUtils.toString(is, "UTF-8");

			html = Normalizer.normalize(html, Normalizer.Form.NFD);
			html = html.replaceAll("[^\\p{ASCII}]", "");

			Document doc = Jsoup.parse(html);
			Elements lines = doc.select(".name_cont");

			Set<String> teamList = new HashSet<String>();
			for (int i = 0; i < lines.size(); i++) {
				Element line = lines.get(i);
				String tempName = line.html();
				tempName = StringEscapeUtils.unescapeHtml(tempName);
				String norm = Normalizer.normalize(tempName,
						Normalizer.Form.NFD);
				norm = norm.replaceAll("[^\\p{ASCII}]", "");
				String trim = norm.trim();
				
				String name = getMatchedName(trim);
				if(name != null){
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
	
	public String getMatchedName(String name) {
		System.out.println("check " + name);
		boolean contains = players.containsKey(name);
		if(contains){
			return players.get(name).getAbbreviationName();
		}else{
			return null;
		}
	}
	
}
