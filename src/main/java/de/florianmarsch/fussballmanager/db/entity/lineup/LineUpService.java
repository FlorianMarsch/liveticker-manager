package de.florianmarsch.fussballmanager.db.entity.lineup;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.florianmarsch.fussballmanager.db.entity.trainer.Trainer;
import de.florianmarsch.fussballmanager.db.service.AbstractService;

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
			String urlString = trainer.getKaderUrl();
			Set<String> players = getPlayer(urlString);
			String excludeString = trainer.getExcludeKaderUrl();
			Set<String> exclude = getPlayer(excludeString);
			players.removeAll(exclude);
			return players;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("abbruch", e);
		}
	}

	Set<String> getPlayer(String urlString)
			throws IOException, MalformedURLException, JsonParseException, JsonMappingException {
		InputStream is = (InputStream) new URL(urlString).getContent();
		String content = IOUtils.toString(is, "UTF-8");
		ObjectMapper mapper = new ObjectMapper();
		List<String> parsed = mapper.readValue(content, new TypeReference<List<String>>() {
		});
		return new HashSet<>(parsed);
	}

}
