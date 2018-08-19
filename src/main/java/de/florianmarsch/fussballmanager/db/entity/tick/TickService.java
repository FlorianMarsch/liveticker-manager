package de.florianmarsch.fussballmanager.db.entity.tick;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;

public class TickService {

	public TickService() {
	}

	public List<Tick> getAllByMatchday(Matchday aMatchday) {
		List<Tick> response = new ArrayList<>();

		String season = System.getenv("SEASON");
		
		try {
			String gamedayUrl = "http://node-comunio-system-api.herokuapp.com/api/result/"+season+"/" + aMatchday.getNumber();
			InputStream is = (InputStream) new URL(gamedayUrl).getContent();
			String content = IOUtils.toString(is, "UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			List<Tick> parsed = mapper.readValue(content,new TypeReference<List<Tick>>(){});
			response.addAll(parsed);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}
