package com.kabaddi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entity to represent the Venue
 * @author Rajiv Karambalkar
 *
 */
@Entity
public class Venue {

	@Id
	@GeneratedValue
	@Column(name = "venueId", nullable = false)
	private long venueId;
	private String venueName;
	private String venueLocation;

	public Venue() {
	}

	public long getVenueId() {
		return venueId;
	}

	public String getVenueLocation() {
		return venueLocation;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueId(long venueId) {
		this.venueId = venueId;
	}

	public void setVenueLocation(String venueLocation) {
		this.venueLocation = venueLocation;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	@Override
	public String toString() {
		return "Venue [venueId=" + venueId + ", venueName=" + venueName + ", venueLocation=" + venueLocation + "]";
	}

}
