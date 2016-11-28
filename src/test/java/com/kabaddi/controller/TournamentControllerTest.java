package com.kabaddi.controller;

import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.kabaddi.entities.Match;
import com.kabaddi.entities.Team;
import com.kabaddi.entities.Tournament;
import com.kabaddi.entities.Venue;
import com.kabaddi.repository.TournamentRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class TournamentControllerTest {
	@Mock
	private TournamentRepository tournamentRepository;
	@Mock
	private MessageSource messageSource;
	@InjectMocks
	private TournamentController tournamentControllerTest;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(tournamentControllerTest).build();

	}

	@Test
	public void testListTournaments() throws Exception {
		this.mockMvc
				.perform(get("/tournaments").header("Accept-Language", "en_us")
						.accept(MediaType.parseMediaType("application/json")))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"));

	}

	@Test
	public void testListTournamentNames() throws Exception {
		Tournament tournament = new Tournament();
		tournament.setName("pro-kabaddi");
		Team pune = new Team("Pune");
		Venue venuePune = new Venue();
		venuePune.setVenueLocation("Pune");
		venuePune.setVenueName("Pune");
		pune.setHomeGround(venuePune);

		Team mumbai = new Team("Mumbai");
		Venue venueMumbai = new Venue();
		venueMumbai.setVenueLocation("Mumbai");
		venueMumbai.setVenueName("Mumbai");
		mumbai.setHomeGround(venueMumbai);

		Match match = new Match(pune, mumbai);
		List<Match> matches = new ArrayList<>();
		matches.add(match);
		tournament.setMatches(matches);
		Tournament kabaddi = new Tournament();
		kabaddi.setName("pro-kabaddi-2016");
		List<Tournament> tournaments = new ArrayList<>();
		tournaments.add(tournament);
		tournaments.add(kabaddi);

		Mockito.when(tournamentRepository.findAll()).thenReturn(tournaments);

		this.mockMvc
				.perform(get("/tournaments/names").header("Accept-Language", "en_us")
						.accept(MediaType.parseMediaType("application/json")))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"));

	}

	@Test
	public void testListTournamentsByName() throws Exception {

		Tournament tournament = new Tournament();
		tournament.setName("pro-kabaddi");
		Team pune = new Team("Pune");
		Venue venuePune = new Venue();
		venuePune.setVenueLocation("Pune");
		venuePune.setVenueName("Pune");
		pune.setHomeGround(venuePune);

		Team mumbai = new Team("Mumbai");
		Venue venueMumbai = new Venue();
		venueMumbai.setVenueLocation("Mumbai");
		venueMumbai.setVenueName("Mumbai");
		mumbai.setHomeGround(venueMumbai);

		Match match = new Match(pune, mumbai);
		List<Match> matches = new ArrayList<>();
		matches.add(match);
		tournament.setMatches(matches);
		Mockito.when(tournamentRepository.findByName(any(String.class))).thenReturn(tournament);

		this.mockMvc
				.perform(get("/tournaments/pro-kabaddi").header("Accept-Language", "en_us")
						.accept(MediaType.parseMediaType("application/json")))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"));
	}
	@Test
	public void testListTournamentMatchesByNameTeamName() throws Exception {

		Tournament tournament = new Tournament();
		tournament.setName("pro-kabaddi");
		Team pune = new Team("Pune");
		Venue venuePune = new Venue();
		venuePune.setVenueLocation("Pune");
		venuePune.setVenueName("Pune");
		pune.setHomeGround(venuePune);

		Team mumbai = new Team("Mumbai");
		Venue venueMumbai = new Venue();
		venueMumbai.setVenueLocation("Mumbai");
		venueMumbai.setVenueName("Mumbai");
		mumbai.setHomeGround(venueMumbai);

		Match match = new Match(pune, mumbai);
		List<Match> matches = new ArrayList<>();
		matches.add(match);
		tournament.setMatches(matches);
		Mockito.when(tournamentRepository.findByName(any(String.class))).thenReturn(tournament);

		this.mockMvc
				.perform(get("/tournaments/pro-kabaddi/Pune").header("Accept-Language", "en_us")
						.accept(MediaType.parseMediaType("application/json")))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"));
	}
	

}
