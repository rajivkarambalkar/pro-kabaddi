package com.kabaddi.schedular;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.kabaddi.entities.Match;
import com.kabaddi.entities.Team;
import com.kabaddi.entities.Venue;

public class DoubleRoundRobinSchedulerTest {
	@Test
	public void scheduleMatches() {
		Team pune = new Team("Pune");
		Team mumbai = new Team("Mumbai");
		Team kolkata = new Team("Kolkata");
		Team banglore = new Team("Banglore");

		Venue venuePune = new Venue();
		venuePune.setVenueLocation("Pune");
		venuePune.setVenueName("Pune");
		pune.setHomeGround(venuePune);

		Venue venueMumbai = new Venue();
		venueMumbai.setVenueLocation("Mumbai");
		venueMumbai.setVenueName("Mumbai");
		mumbai.setHomeGround(venueMumbai);

		Venue venueKolkata = new Venue();
		venueKolkata.setVenueLocation("Kolkata");
		venueKolkata.setVenueName("Kolkata");
		kolkata.setHomeGround(venueKolkata);

		Venue venueBanglore = new Venue();
		venueBanglore.setVenueLocation("Banglore");
		venueBanglore.setVenueName("Banglore");
		banglore.setHomeGround(venueBanglore);

		List<Team> teams = new ArrayList<>();
		teams.add(pune);
		teams.add(banglore);
		teams.add(mumbai);
		teams.add(kolkata);
		LocalDate startDate = LocalDate.now();
		DoubleRoundRobinScheduler doubleRoundRobinScheduler = new DoubleRoundRobinScheduler();
		List<Match> matches = doubleRoundRobinScheduler.scheduleMatches(teams,startDate);
		//for 4 teams with round robin , there will be 6 matches
		assertThat(matches.size()).isEqualTo(6);
		
		teams.remove(kolkata);
		
		 matches = doubleRoundRobinScheduler.scheduleMatches(teams,startDate);
		//for 3 teams with round robin , there will be 6 matches
		assertThat(matches.size()).isEqualTo(2);
		

	}

}
