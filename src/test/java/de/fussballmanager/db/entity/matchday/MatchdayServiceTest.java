package de.fussballmanager.db.entity.matchday;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class MatchdayServiceTest {

	@Spy
	MatchdayService sut;

	List<Matchday> all = new ArrayList<>();

	@Before
	public void setUp() {
		all = new ArrayList<>();
		Matchday temp = new Matchday();
		temp.setNumber(1);
		temp.setModus("Division");
		all.add(temp);
		temp = new Matchday();
		temp.setNumber(2);
		temp.setModus("CL");
		all.add(temp);
		temp = new Matchday();
		temp.setNumber(3);
		temp.setModus("Division");
		all.add(temp);
		
	}

	@Test
	public void testGetNextMatchday() {
		Mockito.doReturn(all).when(sut).getAll();
		Matchday current = new Matchday();
		current = new Matchday();
		current.setNumber(1);
		current.setModus("Division");
		
		Matchday nextMatchday = sut.getNextMatchday(current);
		
		Assert.assertTrue(nextMatchday.getNumber().equals(3));
	}

}
