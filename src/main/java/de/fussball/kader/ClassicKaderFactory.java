package de.fussball.kader;

import java.io.InputStream;
import java.net.URL;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ClassicKaderFactory {


	public Set<String> get(String id) {
		try {

				String urlString = "http://www.comunio.de/playerInfo.phtml?pid="
						+ id ;
				InputStream is = (InputStream) new URL(urlString).getContent();
				String html = IOUtils.toString(is, "UTF-8");
				
				html = Normalizer.normalize(html, Normalizer.Form.NFD);
				html = html.replaceAll("[^\\p{ASCII}]", "");
				
			Document doc = Jsoup.parse(html);
			Elements lines = doc.select(".name_cont");
			
			Set<String> teamList = new HashSet<String>();
			for (int i = 0; i < lines.size(); i++) {
				Element line = lines.get(i);
				String tempName = line.html();
				tempName = StringEscapeUtils.unescapeHtml(tempName);
				String norm = Normalizer.normalize(tempName, Normalizer.Form.NFD);
				norm = norm.replaceAll("[^\\p{ASCII}]", "");
				teamList .add(norm.trim());
			}
			System.out.println(teamList);
			return teamList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("abbruch", e);
		}
	}

	

}
