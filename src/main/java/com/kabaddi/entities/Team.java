package com.kabaddi.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Entity to represent the Team
 * @author Rajiv Karambalkar
 *
 */
@Entity
public class Team {
	@Id
	@GeneratedValue
	@Column(name = "teamId", nullable = false)
	private long teamId;
	private String teamName;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "venueId" )
	private Venue homeGround;

	public Team() {
	}
	public Team(String teamName) {
		this.teamName = teamName;
	}
	public Venue getHomeGround() {
		return homeGround;
	}

	public long getTeamId() {
		return teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setHomeGround(Venue homeGround) {
		this.homeGround = homeGround;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Override
	public String toString() {
		return "Team [teamId=" + teamId + ", teamName=" + teamName + ", homeGround=" + homeGround + "]";
	}

}
