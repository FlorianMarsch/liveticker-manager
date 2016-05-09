package de.fussball.live.processor;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import de.fussballmanager.db.entity.match.MatchService;
import de.fussballmanager.db.entity.matchday.Matchday;
import de.fussballmanager.db.entity.matchday.MatchdayService;
import de.fussballmanager.db.entity.trainer.Trainer;

@RunWith(MockitoJUnitRunner.class)
public class MatchCreatorTest {

	@Spy
	MatchCreator sut;

	@Mock
	MatchdayService matchdayService;

	@Mock
	MatchService matchService;

	Matchday currentMatchday;
	Matchday first;
	Matchday second;
	Matchday third;
	Trainer a;
	Trainer b;
	Trainer c;
	Trainer d;

	@Before
	public void setUp() {
		Mockito.doReturn(matchdayService).when(sut).getMatchdayService();
		Mockito.doReturn(matchService).when(sut).getMatchService();
		currentMatchday = new Matchday();
		first = new Matchday();
		first.setNumber(1);
		second = new Matchday();
		third = new Matchday();
		a = new Trainer();
		b = new Trainer();
		c = new Trainer();
		d = new Trainer();
	}

	@Test
	public void testCreateDivisionalMatches() {
		Mockito.doNothing().when(sut).generateMatches(first, a, b, c, d);
		Mockito.doNothing().when(sut).generateMatches(second, a, c, b, d);
		Mockito.doNothing().when(sut).generateMatches(third, a, d, c, b);

		Mockito.doReturn(first).when(matchdayService).getNextMatchday(currentMatchday);
		Mockito.doReturn(first).when(matchdayService).get(1);
		Mockito.doReturn(second).when(matchdayService).get(2);
		Mockito.doReturn(third).when(matchdayService).get(3);

		List<Trainer> trainers = new ArrayList<>();
		trainers.add(a);
		trainers.add(b);
		trainers.add(c);
		trainers.add(d);
		sut.createDivisionalMatches(trainers, currentMatchday);

		Mockito.verify(sut, Mockito.times(1)).generateMatches(first, a, b, c, d);
		Mockito.verify(sut, Mockito.times(1)).generateMatches(second, a, c, b, d);
		Mockito.verify(sut, Mockito.times(1)).generateMatches(third, a, d, c, b);

	}

}
