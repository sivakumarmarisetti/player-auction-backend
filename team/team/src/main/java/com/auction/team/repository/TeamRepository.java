package com.auction.team.repository;


import com.auction.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);

	void deleteByName(String name);
	
	
	

//	Team findAll(Team team);
	
}
