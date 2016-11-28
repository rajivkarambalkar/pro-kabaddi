package com.kabaddi.schedular;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.kabaddi.entities.Match;
import com.kabaddi.entities.Team;
/**
 * To shcedule the matches
 * @author KarambRa
 *
 */
@Configuration
public class DoubleRoundRobinScheduler implements RoundRobinScheduler {
	private static final String BYE = "BYE";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Match> scheduleMatches(List<Team> teams, LocalDate startDate) {
		Set<Match> matches = createMatches(teams);
		matches.stream().forEach(match -> logger.debug(match.getHomeTeam().getTeamName() + " -- "
				+ match.getAwayTeam().getTeamName() + " at ground  " + match.getVenue().getVenueName()));

		Map<LocalDate, List<Match>> scheduleMatches = scheduleMatches(matches, startDate);
		return scheduleMatches.values().stream().flatMap(list -> list.stream()).collect(Collectors.toList());
	}
	/**
	 * Generate the match List
	 * @param list
	 * @return
	 */
	private Set<Match> createMatches(List<Team> list) {

		Set<Match> matches = new HashSet<>();

		for (int i = 0; i < list.size() - 1; i++) {
			int mid = list.size() / 2;
			List<Team> home = new ArrayList<>();
			home.addAll(list.subList(0, mid));
			List<Team> away = new ArrayList<>();
			away.addAll(list.subList(mid, list.size()));
			Collections.reverse(away);

			for (int j = 0; j < mid; j++) {
				if (valdiateTeams(home.get(j), away.get(j)))
					matches.add(new Match(home.get(j), away.get(j)));
			}
			list.add(1, list.get(list.size() - 1));
			list.remove(list.size() - 1);
		}

		return matches;
	}
	/**
	 * schedule the match list as per start date
	 * @param matchesSet
	 * @param matchDate
	 * @return
	 */
	private Map<LocalDate, List<Match>> scheduleMatches(Set<Match> matchesSet, LocalDate matchDate) {
		List<Match> todayMatches = new ArrayList<>();
		Map<LocalDate, List<Match>> dateMatches = new LinkedHashMap<>();

		List<Match> matches = new ArrayList<>(matchesSet);
		Collections.shuffle(matches);

		int counter = 0;
		while (!matches.isEmpty()) {

			if (dateMatches.get(matchDate) != null && dateMatches.get(matchDate).size() == 2
					|| counter == matches.size()) {
				matchDate = matchDate.plusDays(1);
				todayMatches = new ArrayList<>();
				counter = 0;
			}
			Match toBeScheduled = matches.get(counter++);
			if (!isConsecutiveOrOnSameDay(toBeScheduled, dateMatches, matchDate.minusDays(1))) {
				todayMatches.add(toBeScheduled);
				toBeScheduled.setMatchDate(matchDate);
				dateMatches.put(matchDate, todayMatches);
				matches.remove(counter - 1);
				counter = 0;
			}
		}

		Iterator<LocalDate> iterator = dateMatches.keySet().iterator();
		while (iterator.hasNext()) {
			LocalDate date = iterator.next();
			dateMatches.get(date).forEach(match -> logger.debug(
					date + " : " + match.getHomeTeam().getTeamName() + "  vs " + match.getAwayTeam().getTeamName()));

		}

		return dateMatches;

	}

	private boolean isConsecutiveOrOnSameDay(Match match, Map<LocalDate, List<Match>> dateMatches, LocalDate prevDay) {
		List<Match> prevDaysMatches = dateMatches.get(prevDay);
		List<Match> toDaysMatches = dateMatches.get(prevDay.plusDays(1));
		if (toDaysMatches != null && prevDaysMatches != null)
			prevDaysMatches.addAll(toDaysMatches);

		return checkConsecutive(prevDaysMatches, match);

	}

	private boolean checkConsecutive(List<Match> prevDaysMatches, Match match) {
		if (prevDaysMatches != null) {

			for (Match yest : prevDaysMatches) {
				String awayTeam = yest.getAwayTeam().getTeamName();
				String homeTeam = yest.getHomeTeam().getTeamName();
				String currentHomeTeam = match.getHomeTeam().getTeamName();
				String currentAwayTeam = match.getAwayTeam().getTeamName();
				if (currentHomeTeam.equalsIgnoreCase(awayTeam) || currentHomeTeam.equalsIgnoreCase(homeTeam)) {
					return true;
				}
				if (currentAwayTeam.equalsIgnoreCase(awayTeam) || currentAwayTeam.equalsIgnoreCase(homeTeam)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean valdiateTeams(Team team, Team opponent) {
		if (team.equals(opponent)) {
			return false;
		}
		if (BYE.equalsIgnoreCase(team.getTeamName()) || BYE.equalsIgnoreCase(opponent.getTeamName()))
			return false;

		return true;
	}
}
