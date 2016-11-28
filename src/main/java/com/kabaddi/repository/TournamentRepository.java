package com.kabaddi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kabaddi.entities.Tournament;

@RepositoryRestResource
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
	Tournament findByName(String name);
}
