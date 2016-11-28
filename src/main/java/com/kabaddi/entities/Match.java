package com.kabaddi.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
/**
 * Entity to represent the Match
 * @author Rajiv Karambalkar
 *
 */
@Entity
public class Match {

	@Id
	@GeneratedValue
	@Column(name = "matchId", nullable = false)
	private long matchId;

	@OneToOne
	private Team homeTeam;

	@OneToOne
	private Team awayTeam;

	private LocalDate matchDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "venueId")
	private Venue venue;

	public Match() {
	}

	public Match(Team homeTeam, Team awayTeam) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.venue = homeTeam.getHomeGround();
	}

	public long getMatchId() {
		return matchId;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setMatchId(long matchId) {
		this.matchId = matchId;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}

	public LocalDate getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(LocalDate matchDate) {
		this.matchDate = matchDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + homeTeam.getTeamName().length();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if (!this.getHomeTeam().getTeamName().equals(other.getHomeTeam().getTeamName()))
			return false;
		if (!this.getAwayTeam().getTeamName().equals(other.getAwayTeam().getTeamName()))
			return false;
		return true;
	}

}
