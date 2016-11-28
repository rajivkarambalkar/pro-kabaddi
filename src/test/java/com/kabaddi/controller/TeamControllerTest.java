package com.kabaddi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

@RunWith(SpringJUnit4ClassRunner.class)
public class TeamControllerTest {
	@Mock
	private TeamRepository teamRepository;
	@Mock
	private MessageSource messageSource;

	@InjectMocks
	private TeamController teamControllerTest;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(teamControllerTest).build();

	}


	@Test
	public void testaddTeam() throws Exception {
		Team pune = new Team("Pune");
		Venue venuePune = new Venue();
		venuePune.setVenueLocation("Pune");
		venuePune.setVenueName("Pune");
		pune.setHomeGround(venuePune);
		ObjectMapper mapper = new ObjectMapper();

		String jsonStringRequest = mapper.writeValueAsString(pune);
		this.mockMvc
				.perform(post("/teams/addTeam").header("Accept-Language", "en_us")
						.contentType(MediaType.APPLICATION_JSON).content(jsonStringRequest))
				.andExpect(status().isOk());

	}
	
	@Test
	public void testListTeams() throws Exception {
		this.mockMvc
				.perform(get("/teams").header("Accept-Language", "en_us")
						.accept(MediaType.parseMediaType("application/json")))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"));

	}
}
