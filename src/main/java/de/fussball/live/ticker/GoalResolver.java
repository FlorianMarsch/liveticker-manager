package de.fussball.live.ticker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.fussballmanager.db.entity.lineup.ClassicKaderFactory;
import de.fussballmanager.db.entity.match.Match;
import de.fussballmanager.db.entity.match.MatchService;
import de.fussballmanager.db.entity.matchday.Matchday;
import de.fussballmanager.db.entity.tick.Tick;
import de.fussballmanager.db.entity.trainer.Trainer;

public class GoalResolver {

	public List<Event> getGoals(Matchday aMatchday) {
		List<Event> returnEvents = new ArrayList<Event>();
		LiveTickerHandler liveTicker = new LiveTickerHandler();
		ClassicKaderFactory ckf = new ClassicKaderFactory();

		List<Match> matches = new MatchService().getAllByMatchday(aMatchday);

		List<Tick> resolvedEvents = liveTicker
				.getResolvedLiveTickerEvents(aMatchday);

		for (Tick tempEvent : resolvedEvents) {
			Map<Trainer, Set<String>> allPlayer = ckf.getAll(aMatchday);
			try {
				for (Trainer trainer : allPlayer.keySet()) {
					Set<String> team = allPlayer.get(trainer);

					if (team.contains(tempEvent.getResolvedName())) {

						Match match = getMatch(matches, trainer);
						
						Event processedEvent = new Event();
						processedEvent.setEvent(tempEvent.getEvent());
						processedEvent.setId(tempEvent.getId());
						processedEvent.setName(tempEvent.getName());
						processedEvent.setResolved(tempEvent.getResolvedName());
						processedEvent.setTrainer(trainer);
						processedEvent.setMatch(match);
						processedEvent.addEventToMatch();
						returnEvents.add(processedEvent);

					}

				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return returnEvents;
	}

	
	public Match getMatch(List<Match> matches, Trainer trainer){
		Match current = null;
		for (Match tempMatch : matches) {
			if (tempMatch.getHome().equals(trainer)
					|| tempMatch.getGuest().equals(trainer)) {
				current = tempMatch;
			}
		}
		return current;
	}
	
}
