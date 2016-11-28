package com.kabaddi.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kabaddi.entities.Venue;

@RepositoryRestResource
public interface VenueRepository extends JpaRepository<Venue, Long> {
	Collection<Venue> findVenuesByVenueLocation(@Param("venueLocation") String venueLocation);
}
