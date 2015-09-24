package de.fussball.live.ticker.event;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventResolver {

	public List<Event> getResolvedLiveTickerEvents(String content) {
		
		
		List<Event> eventList = new ArrayList<Event>();
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
							
							Event e = new Event();
							e.setId(eventId);
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
