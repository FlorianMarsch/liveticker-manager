package de.florianmarsch.fussballmanager.live.ticker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.florianmarsch.fussballmanager.db.entity.lineup.ClassicKaderFactory;
import de.florianmarsch.fussballmanager.db.entity.match.Match;
import de.florianmarsch.fussballmanager.db.entity.match.MatchService;
import de.florianmarsch.fussballmanager.db.entity.matchday.Matchday;
import de.florianmarsch.fussballmanager.db.entity.tick.Tick;
import de.florianmarsch.fussballmanager.db.entity.tick.TickService;
import de.florianmarsch.fussballmanager.db.entity.trainer.Trainer;

public class GoalResolver {

	public List<Event> getGoals(Matchday aMatchday) {
		List<Event> returnEvents = new ArrayList<Event>();
		TickService liveTicker = new TickService();
		ClassicKaderFactory ckf = new ClassicKaderFactory();

		List<Match> matches = new MatchService().getAllByMatchday(aMatchday);

		List<Tick> ticks = liveTicker.getAllByMatchday(aMatchday);

		Map<Trainer, Set<String>> allPlayer = null;
		for (Tick tick : ticks) {
			if (allPlayer == null) {
				// Load them only if there are goals to process
				allPlayer = ckf.getAll(aMatchday);
			}
			try {
				for (Trainer trainer : allPlayer.keySet()) {
					Match match = getMatch(matches, trainer);
					if (match != null) {
						// Trainer musst be at a gameday. may happen on semi
						// finals

						Set<String> team = new HashSet<String>(allPlayer.get(trainer));

						String name = tick.getName();
						System.out.println("check " + name + " in "+trainer+" :" + team + " ? " + team.contains(name));
						if (team.contains(name)) {

							Event event = new Event();
							event.setEvent(tick.getEvent());
							event.setId(tick.getId());
							event.setName(name);
							event.setTrainer(trainer);
							event.setMatch(match);
							event.addEventToMatch();
							returnEvents.add(event);
						}
					}

				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return returnEvents;
	}

	boolean containsPart(Set<String> team, String name) {
		String[] split = name.split(" ");
		String attempt = split[split.length - 1];

		for (String player : team) {
			if (player.contains(attempt)) {
				return true;
			}
		}
		return false;
	}

	public Match getMatch(List<Match> matches, Trainer trainer) {
		Match current = null;
		for (Match tempMatch : matches) {
			if (tempMatch.getHome().equals(trainer) || tempMatch.getGuest().equals(trainer)) {
				current = tempMatch;
			}
		}
		return current;
	}

}
