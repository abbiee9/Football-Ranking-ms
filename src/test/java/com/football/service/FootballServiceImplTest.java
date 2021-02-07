package com.football.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.football.exception.FootballNotFoundException;
import com.football.model.Football;
import com.football.model.FootballResponse;
import com.football.repository.FootballRepository;

@RunWith(MockitoJUnitRunner.class)
public class FootballServiceImplTest {

	@InjectMocks
	private FootballServiceImpl footballServiceImpl;

	@Mock
	private FootballRepository repo;

	List<Football> lists;

	Football football;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		lists = new ArrayList<>();
		football = new Football();
		football.setCountryName("India");
		football.setLeagueName("ISL");
		football.setTeamName("Up test");
		football.setOverallPosition(1);
		lists.add(football);
	}

	@Test
	public void getFootballsTest() {
		String filter = "country";
		String value = "India";
		when(repo.findAll()).thenReturn(lists);
		List<Football> footballs = footballServiceImpl.getFootballs(filter, value);
		assertNotNull(footballs);
		assertEquals(1, footballs.size());
		assertEquals(1, footballs.get(0).getOverallPosition().intValue());
		assertEquals("ISL", footballs.get(0).getLeagueName());
	}

	@Test
	public void getFootballsTest1() {
		String filter = "league";
		String value = "Isl";
		when(repo.findAll()).thenReturn(lists);
		List<Football> footballs = footballServiceImpl.getFootballs(filter, value);
		assertNotNull(footballs);
		assertEquals(1, footballs.size());
		assertEquals(1, footballs.get(0).getOverallPosition().intValue());
		assertEquals("ISL", footballs.get(0).getLeagueName());
	}

	@Test
	public void getFootballsTest2() {
		String filter = "team";
		String value = "UP test";
		when(repo.findAll()).thenReturn(lists);
		List<Football> footballs = footballServiceImpl.getFootballs(filter, value);
		assertNotNull(footballs);
		assertEquals(1, footballs.size());
		assertEquals(1, footballs.get(0).getOverallPosition().intValue());
		assertEquals("ISL", footballs.get(0).getLeagueName());
	}

	@Test(expected = FootballNotFoundException.class)
	public void getFootballsTestException() {
		String filter = "team1";
		String value = "US test";
		when(repo.findAll()).thenReturn(lists);
		footballServiceImpl.getFootballs(filter, value);

	}

	@Test
	public void getFootballsTestException1() {
		String filter = "team1";
		String value = "US test";
		when(repo.findAll()).thenReturn(lists);
		try {
			footballServiceImpl.getFootballs(filter, value);
		} catch (FootballNotFoundException e) {
			assertEquals("Incorrect filter applied", e.getMessage());
		}

	}

	@Test
	public void getFootballTest() {
		when(repo.findByFootballId(Mockito.anyLong())).thenReturn(football);
		Football football1 = footballServiceImpl.getFootball(1l);
		assertNotNull(football1);
		assertEquals(1, football1.getOverallPosition().intValue());
		assertEquals("ISL", football1.getLeagueName());
	}

	@Test(expected = FootballNotFoundException.class)
	public void getFootballTestException() {
		when(repo.findByFootballId(Mockito.anyLong())).thenReturn(null);
		footballServiceImpl.getFootball(1l);

	}

	@Test
	public void getFootballTestException1() {
		try {
			when(repo.findByFootballId(Mockito.anyLong())).thenReturn(null);
			footballServiceImpl.getFootball(1l);
		} catch (FootballNotFoundException e) {
			assertEquals("Invalid Football id!", e.getMessage());
		}

	}

	@Test
	public void addFootballTest() {
		// doNothing().when(repo.save(Mockito.any(Football.class)));
		FootballResponse addfootball = footballServiceImpl.addfootball(football);
		assertEquals("Successful", addfootball.getStatus());
		assertEquals("Football added successfully", addfootball.getMessage());
	}

	@Test
	public void updateFootballTest() {
		// doNothing().when(repo.save(Mockito.any(Football.class)));
		FootballResponse addfootball = footballServiceImpl.addfootball(football);
		assertEquals("Successful", addfootball.getStatus());
		assertEquals("Football added successfully", addfootball.getMessage());
	}

	@Test
	public void deleteFootballTest() {
		when(repo.findByFootballId(Mockito.anyLong())).thenReturn(football);
		FootballResponse addfootball = footballServiceImpl.deleteFootball(1l);
		assertEquals("Successful", addfootball.getStatus());
		assertEquals("Football deleted successfully", addfootball.getMessage());
	}

	@Test(expected = FootballNotFoundException.class)
	public void deleteFootballTestException() {
		when(repo.findByFootballId(Mockito.anyLong())).thenReturn(null);
		footballServiceImpl.deleteFootball(1l);
	}

	@Test
	public void deleteFootballTestException1() {
		try {
			when(repo.findByFootballId(Mockito.anyLong())).thenReturn(null);
			footballServiceImpl.deleteFootball(1l);
		} catch (FootballNotFoundException e) {
			assertEquals("No record exist with provided id", e.getMessage());
		}
	}

}
