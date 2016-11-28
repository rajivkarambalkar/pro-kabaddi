package com.kabaddi.controller;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kabaddi.entities.Team;
import com.kabaddi.exception.ValidationException;
import com.kabaddi.repository.TeamRepository;

/**
 * Rest Controller used for CRUD operations on Team
 * 
 * @author Rajiv karambalkar
 *
 */
@RequestMapping("/teams")
@RestController
public class TeamController {
	private TeamRepository teamRepository;

	private MessageSource messageSource;

	@Autowired
	public TeamController(TeamRepository teamRepository, MessageSource messageSource) {
		this.teamRepository = teamRepository;
		this.messageSource = messageSource;
	}

	/**
	 * to add the Team
	 * 
	 * @param team
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTeam", method = RequestMethod.POST)
	public ResponseEntity<Team> addTeam(@RequestBody Team team, @RequestHeader(name = "Accept-Language") Locale locale)
			throws Exception {
		validateTeam(team, locale);
		return new ResponseEntity<Team>(teamRepository.save(team), HttpStatus.OK);

	}

	/**
	 * List all Teams.
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Team>> listTeams() throws Exception {
		List<Team> teams = teamRepository.findAll();
		return new ResponseEntity<List<Team>>(teams, HttpStatus.OK);
	}

	/**
	 * List only team names.
	 * 
	 * @return List<String>
	 * @throws Exception
	 */
	@RequestMapping(value = "/names", method = RequestMethod.GET)
	public ResponseEntity<List<String>> listTournamentNames() throws Exception {
		List<Team> teams = teamRepository.findAll();
		List<String> teamsNames = teams.stream().map(team -> team.getTeamName()).collect(Collectors.toList());

		return new ResponseEntity<List<String>>(teamsNames, HttpStatus.OK);

	}

	private void validateTeam(Team team, Locale locale) {
		if (ObjectUtils.isEmpty(team) || StringUtils.isBlank(team.getTeamName()))
			throw new ValidationException(messageSource.getMessage("invalid.team", null, locale));
		Team teamExist = teamRepository.findTeamByTeamName(team.getTeamName());
		if (!ObjectUtils.isEmpty(teamExist))
			throw new ValidationException(messageSource.getMessage("team.name.already.present", null, locale));
	}

}
