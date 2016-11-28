package com.kabaddi.schedular;

import java.time.LocalDate;
import java.util.List;

import com.kabaddi.entities.Match;
import com.kabaddi.entities.Team;

public interface RoundRobinScheduler {
	List<Match> scheduleMatches(List<Team> teams, LocalDate startDate);
}
