package de.fussball.kader;

import java.io.InputStream;
import java.net.URL;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ClassicKaderFactory {

	public static Map<String, String> cached = new HashMap<String, String>();

	public Set<String> get(String id) {
		try {
			String html = null;
			if (!cached.containsKey(id)) {
				String urlString = "http://www.comunio.de/playerInfo.phtml?pid="
						+ id;
				InputStream is = (InputStream) new URL(urlString).getContent();
				html = IOUtils.toString(is, "UTF-8");

				html = Normalizer.normalize(html, Normalizer.Form.NFD);
				html = html.replaceAll("[^\\p{ASCII}]", "");
				cached.put(id, html);
			}else{
				html = cached.get(id);
			}

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
				teamList.add(norm.trim());
			}
			System.out.println(teamList);
			return teamList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("abbruch", e);
		}
	}
	
	public Map<String,Set<String>> getAll(){
		Map<String,Set<String>>returnMap = new HashMap<String,Set<String>>();
		
		returnMap.put("FCEtepetete", get("9414334"));
		returnMap.put("TorpedoEric", get("9128310"));
		returnMap.put("CheaterP", get("9958945"));
		returnMap.put("JuventusFlorin", get("9105942"));
		returnMap.put("ZenitTill", get("9198836"));
		returnMap.put("SpartaTobyIO", get("9206452"));
		returnMap.put("FcPhoenix", get("9369292"));
		returnMap.put("FCTor", get("9462897"));
		returnMap.put("HerrmanniacAnna", get("9311676"));
		returnMap.put("LokomotiveKaiopolis", get("10526925"));
		returnMap.put("Annika", get("10958878"));
		returnMap.put("Nadine", get("10956588"));
		
		return returnMap;
		
	}

}
