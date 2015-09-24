package de.fussball.live.ticker.event;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class TickerResolver {

	public TickerResolver(){
		
	}
	
	public String getLiveTicker(String gameday) {
		Integer id = Integer.valueOf(gameday) + 5662927;
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
}
