package de.florianmarsch.fussballmanager.db.entity.lineup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;
import de.florianmarsch.fussballmanager.db.entity.trainer.Trainer;
import de.florianmarsch.fussballmanager.db.entity.trainer.TrainerService;

public class ClassicKaderFactory {
	LineUpService lineUpService = new LineUpService();

	public Set<String> get(Trainer trainer, Matchday currentMatchday) {
		if(trainer.getFake()){
			return new HashSet<String>();
		}
		
		List<LineUp> all = lineUpService.getAll();
		for (LineUp lineUp : all) {
			if(lineUp.getTrainer().equals(trainer) && lineUp.getMatchday().equals(currentMatchday)){
				return lineUp.getPlayers();
			}
		}
		Set<String> recivedLineUp = lineUpService.reciveCurrentLineUp(trainer);
		LineUp lineUp = new LineUp();
		lineUp.setTrainer(trainer);
		lineUp.setMatchday(currentMatchday);
		lineUp.setPlayers(recivedLineUp);
		lineUpService.save(lineUp);
		return recivedLineUp;
	}

	public Map<Trainer, Set<String>> getAll(Matchday currentMatchday) {
		Map<Trainer, Set<String>> returnMap = new HashMap<Trainer, Set<String>>();

		TrainerService ts = new TrainerService();
		List<Trainer> all = ts.getAll();

		for (Trainer trainer : all) {
			returnMap.put(trainer, get(trainer, currentMatchday));
		}
		return returnMap;

	}

}
