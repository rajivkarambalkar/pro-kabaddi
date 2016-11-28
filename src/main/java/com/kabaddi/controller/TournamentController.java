package com.kabaddi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kabaddi.entities.Match;
import com.kabaddi.entities.Tournament;
import com.kabaddi.exception.ValidationException;
import com.kabaddi.repository.TournamentRepository;

/**
 * Rest Controller used for CRUD operations on Tournament
 * 
 * @author Rajiv karambalkar
 *
 */
@RequestMapping("/tournaments")
@RestController
public class TournamentController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private TournamentRepository tournamentRepository;
	private MessageSource messageSource;

	@Autowired
	public TournamentController(TournamentRepository tournamentRepository, MessageSource messageSource) {
		this.tournamentRepository = tournamentRepository;
		this.messageSource = messageSource;
	}

	/**
	 * List all the Tournaments present
	 * 
	 * @return List<Tournament>
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Tournament>> listTournaments() throws Exception {
		List<Tournament> tournaments = tournamentRepository.findAll();
		logger.debug("List of all tournaments : " + tournaments);
		return new ResponseEntity<List<Tournament>>(tournaments, HttpStatus.OK);

	}

	/**
	 * TO list the Tournament by name
	 * 
	 * @param name valid tournament name
	 * @return Tournament
	 * @throws Exception
	 */
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public ResponseEntity<Tournament> listTournamentsByName(@PathVariable String name) throws Exception {
		Tournament tournament = tournamentRepository.findByName(name);
		if (ObjectUtils.isEmpty(tournament)) {
			throw new ValidationException(messageSource.getMessage("tournament.name.not.found", null, null));
		}
		return new ResponseEntity<Tournament>(tournament, HttpStatus.OK);
	}
	
	/**
	 * List only tournament names.
	 * @return List<String>
	 * @throws Exception
	 */
	@RequestMapping(value = "/names", method = RequestMethod.GET)
	public ResponseEntity<List<String>> listTournamentNames() throws Exception {
		List<Tournament> tournaments = tournamentRepository.findAll();
		List<String> tournamentNames = tournaments.stream().map(tournament -> tournament.getName())
				.collect(Collectors.toList());

		return new ResponseEntity<List<String>>(tournamentNames, HttpStatus.OK);

	}
	
	/**
	 * List all the matches of the specific team for the specific tournament
	 * @param name
	 * @param teamName
	 * @return List<Match>
	 * @throws Exception
	 */
	@RequestMapping(value = "/{name}/{teamName}", method = RequestMethod.GET)
	public ResponseEntity<List<Match>> listTournamentMatchesByNameTeamName(@PathVariable String name,
			@PathVariable String teamName) throws Exception {
		Tournament tournament = tournamentRepository.findByName(name);
		List<Match> list = getMatchesByTeamName(teamName, tournament);
		logger.debug(
				"List of all Matches for the tournament : " + name + ", and team : " + teamName + " are : " + list);

		return new ResponseEntity<List<Match>>(list, HttpStatus.OK);
	}

	private List<Match> getMatchesByTeamName(String teamName, Tournament tournament) {
		List<Match> homeList = tournament.getMatches().stream()
				.filter(match -> match.getHomeTeam().getTeamName().matches(teamName)).collect(Collectors.toList());

		List<Match> awayList = tournament.getMatches().stream()
				.filter(match -> match.getAwayTeam().getTeamName().matches(teamName)).collect(Collectors.toList());

		List<Match> list = new ArrayList<>();
		if (!ObjectUtils.isEmpty(homeList))
			list.addAll(homeList);
		if (!ObjectUtils.isEmpty(awayList))
			list.addAll(awayList);
		return list;
	}

}
