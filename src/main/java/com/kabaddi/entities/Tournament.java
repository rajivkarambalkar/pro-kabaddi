package com.kabaddi.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Entity to represent the Tournament
 * @author Rajiv Karambalkar
 *
 */
@Entity
public class Tournament {
	@Id
	@GeneratedValue
	@Column(name = "tournamentId", nullable = false)
	private long tournamentId;

	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Match> matches;

	private LocalDate startDate;

	public Tournament() {
	}

	public Tournament(String name) {
		this.name = name;
	}

	public long getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(long tournamentId) {
		this.tournamentId = tournamentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "Tournament [tournamentId=" + tournamentId + ", name=" + name + ", matches=" + matches + ", startDate="
				+ startDate + "]";
	}

}
