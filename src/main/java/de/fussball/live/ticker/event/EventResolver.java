package de.fussball.live.ticker.event;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.fussballmanager.db.entity.matchday.Matchday;
import de.fussballmanager.db.entity.tick.Tick;

public class EventResolver {
	
	public List<Tick> getLiveTickerEvents(Matchday aMatchday){
		String content = getLiveTickerText(aMatchday);
		return getLiveTickerEvents(content);
	}
	
	public String getLiveTickerText(Matchday aMatchday) {
		Integer id = aMatchday.getNumber() + 5662927;
		String content = loadFile("http://feedmonster.iliga.de/feeds/il/de/competitions/1/1271/matchdays/"
				+ id + ".json");
		return content;
	}
	

	private String loadFile(String url) {

		StringBuffer tempReturn = new StringBuffer();
		try {
			URL u = new URL(url);
			InputStream is = u.openStream();
			DataInputStream dis = new DataInputStream(new BufferedInputStream(
					is));
			String s;

			while ((s = dis.readLine()) != null) {
				tempReturn.append(s);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempReturn.toString();
	}
	

	public List<Tick> getLiveTickerEvents(String content) {
		
		
		List<Tick> eventList = new ArrayList<Tick>();
		try {
			JSONObject json = new JSONObject(content);
			JSONArray kickoffs = json.getJSONArray("kickoffs");

			for (int k = 0; k < kickoffs.length(); k++) {
				JSONObject element = kickoffs.getJSONObject(k);
				JSONArray groups = element.getJSONArray("groups");
				for (int gr = 0; gr < groups.length(); gr++) {
					JSONObject group = groups.getJSONObject(gr);
					JSONArray matches = group.getJSONArray("matches");
					for (int m = 0; m < matches.length(); m++) {
						JSONObject match = matches.getJSONObject(m);
						JSONArray goals = match.getJSONArray("goals");
						for (int go = 0; go < goals.length(); go++) {
							JSONObject goal = goals.getJSONObject(go);
							String type = goal.getString("type");
							String eventId = goal.getString("eventId");
							String player = goal.getJSONObject("player")
									.getString("name");

							String norm = Normalizer.normalize(player, Normalizer.Form.NFD);
							norm = norm.replaceAll("[^\\p{ASCII}]", "");
							
							Tick e = new Tick();
							e.setExternId(eventId);
							e.setEvent(type);
							e.setName(norm);
							eventList.add(e);

						}
					}
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("Abbruch", e);
		}
		return eventList;
	}
}
