package com.kabaddi.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kabaddi.entities.Match;
import com.kabaddi.entities.Team;
import com.kabaddi.entities.Tournament;
import com.kabaddi.exception.ApplicationException;
import com.kabaddi.exception.ValidationException;
import com.kabaddi.repository.TeamRepository;
import com.kabaddi.repository.TournamentRepository;
import com.kabaddi.schedular.RoundRobinScheduler;
/**
 * Rest Controller to schedule matches for the tournament.
 * @author Rajiv Karambalkar
 *
 */
@RestController
@RequestMapping("/pro-kabaddi")
public class MatchSchedulerController {
	private static final String BYE = "BYE";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static final String format = "yyyy-MM-dd";

	private TeamRepository teamRepository;

	private TournamentRepository tournamentRepository;

	private RoundRobinScheduler roundRobinScheduler;

	private MessageSource messageSource;

	@Autowired
	MatchSchedulerController(MessageSource messageSource, TeamRepository teamRepository,
			RoundRobinScheduler roundRobinScheduler, TournamentRepository tournamentRepository) {
		this.teamRepository = teamRepository;
		this.roundRobinScheduler = roundRobinScheduler;
		this.tournamentRepository = tournamentRepository;
		this.messageSource = messageSource;
	}

	@RequestMapping(value = "/generateSchedule", method = RequestMethod.POST)
	public void generateSchedule(@RequestParam("teamNames") String[] teamNames,
			@RequestParam("tournamentName") String tournamentName,
			@RequestParam("tournamentStartDate") String tournamentStartDate,
			@RequestHeader(name = "Accept-Language") Locale locale) throws ApplicationException {

		validateInputs(teamNames, tournamentName, tournamentStartDate, locale);

		List<Team> teams = teamRepository.findTeamByTeamNameIn(Arrays.asList(teamNames));
		if (teams.size() % 2 == 1) {
			teams.add(new Team(BYE));
		}
		List<Team> away = new ArrayList<>();
		away.addAll(teams);
		teams.addAll(away);
		LocalDate startDate = getStartDate(tournamentStartDate, locale);
		List<Match> matchList = roundRobinScheduler.scheduleMatches(teams,startDate);
		
		Tournament proKabaddi = new Tournament(tournamentName);
		proKabaddi.setMatches(matchList);
		proKabaddi.setStartDate(getStartDate(tournamentStartDate, locale));
		tournamentRepository.save(proKabaddi);
		
		logger.info("Schedule is generated for the tournament : " + tournamentName);

	}

	private LocalDate getStartDate(String tournamentStartDate, Locale locale) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			formatter = formatter.withLocale(locale);
			return LocalDate.parse(tournamentStartDate, formatter);
		} catch (Exception ex) {
			throw new ValidationException(messageSource.getMessage("invalid.start.date", null, locale));
		}
	}

	private void validateInputs(String[] teamNames, String tournamentName, String tournamentStartDate, Locale locale) {

		if (ObjectUtils.isEmpty(teamNames) || teamNames.length <= 1) {
			throw new ValidationException(messageSource.getMessage("invalid.numberOfTeams", null, locale));
		}

		Tournament tournament = tournamentRepository.findByName(tournamentName);
		if (!ObjectUtils.isEmpty(tournament)) {
			logger.debug("Tournament is already present with name : " + tournamentName);
			throw new ValidationException(messageSource.getMessage("tournament.name.alreadypresent", null, locale));
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			simpleDateFormat.parse(tournamentStartDate);
		} catch (ParseException ex) {
			throw new ValidationException(messageSource.getMessage("invalid.start.date", null, locale));
		}

	}

}
