package de.florianmarsch.fussballmanager.live;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class GameDayFinder {

	private String gamedayUrl = "http://feedmonster.iliga.de/feeds/il/de/competitions/1/1271/matchdaysOverview.json";

	public String getCurrentGameDay() {
		try {
			String content = loadFile(gamedayUrl);
			JSONArray days = new JSONObject(content).getJSONArray("matchdays");

			for (int i = 0; i < days.length(); i++) {
				JSONObject tempDay = days.getJSONObject(i);
				if (tempDay.getBoolean("isCurrentMatchday")) {
					JSONArray kickoffs = tempDay.getJSONArray("kickoffs");
					for (int j = 0; j < kickoffs.length(); j++) {
						String date = kickoffs.getString(j);
						if (isInRange(date, new Date())) {
							return tempDay.getString("name").split(". ")[0];
						}
					}
				}
			}

			return null;
		} catch (Exception e) {
			return null;
		}
	}

	private boolean isInRange(String date, Date date2) throws ParseException {

		String calendarPart = date.split("T")[0];
		String timePart = date.split("T")[1].split("Z")[0];

		Date calendarPartDate = new SimpleDateFormat("yy-MM-dd")
				.parse(calendarPart);
		Date timePartDate = new SimpleDateFormat("HH:mm:ss").parse(timePart);
		
		long sum = calendarPartDate.getTime() + timePartDate.getTime();

		Date sumDate = new Date(sum);

		Calendar cal = Calendar.getInstance();
		cal.setTime(sumDate);
		cal.add(Calendar.HOUR_OF_DAY, 3);
		Date startDate = cal.getTime();

		cal.setTime(startDate);
		cal.add(Calendar.HOUR_OF_DAY, 3);
		Date endDate = cal.getTime();
System.out.println("startDate" + startDate);
System.out.println("endDate" + endDate);
		
		if (date2.after(startDate) && date2.before(endDate)) {
			return true;
		}

		return false;
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

}
