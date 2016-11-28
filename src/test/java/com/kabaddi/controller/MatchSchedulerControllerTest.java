package com.kabaddi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kabaddi.entities.Team;
import com.kabaddi.entities.Venue;
import com.kabaddi.repository.TeamRepository;
import com.kabaddi.repository.TournamentRepository;
import com.kabaddi.schedular.RoundRobinScheduler;

@RunWith(SpringJUnit4ClassRunner.class)

public class MatchSchedulerControllerTest {
	@Mock
	private TeamRepository teamRepository;
	@Mock
	private TournamentRepository tournamentRepository;
	@Mock
	private RoundRobinScheduler roundRobinScheduler;
	@Mock
	private MessageSource messageSource;

	private MockMvc mockMvc;

	@InjectMocks
	private MatchSchedulerController matchSchedulerControllerTest;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(matchSchedulerControllerTest).build();

	}

	@Test
	public void testGenerateSchedule() throws Exception {
		Team pune = new Team("Pune");
		Venue venuePune = new Venue();
		venuePune.setVenueLocation("Pune");
		venuePune.setVenueName("Pune");
		pune.setHomeGround(venuePune);
		ObjectMapper mapper = new ObjectMapper();

		String jsonStringRequest = mapper.writeValueAsString(pune);

		this.mockMvc.perform(
				post("/pro-kabaddi/generateSchedule?teamNames=U MUMBA&teamNames=PUNERI PALTAN&teamNames=BENGALURU BULLS&teamNames=DABANG DELHI K.C.&tournamentName=pro-kabaddi-3&tournamentStartDate=2017-01-25")
						.header("Accept-Language", "en_us").contentType(MediaType.APPLICATION_JSON)
						.content(jsonStringRequest))
				.andExpect(status().isOk());

	}
}
