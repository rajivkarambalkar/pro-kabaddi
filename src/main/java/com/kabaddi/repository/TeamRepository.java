package com.kabaddi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kabaddi.entities.Team;

@RepositoryRestResource
public interface TeamRepository extends JpaRepository<Team, Long> {
	Team findTeamByTeamName(@Param("teamName") String teamName);

	List<Team> findTeamByTeamNameIn(@Param("teamName") List<String> teamName);

}
