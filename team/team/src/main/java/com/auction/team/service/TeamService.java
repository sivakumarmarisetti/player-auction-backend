package com.auction.team.service;


import com.auction.team.entity.Team;
import com.auction.team.exception.AuctionException;
import com.auction.team.repository.TeamRepository;

import jakarta.transaction.Transactional;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public Team createTeam(Team team) {
        if (teamRepository.findByName(team.getName()).isPresent()) {
            throw new AuctionException("Team with name " + team.getName() + " already exists", 400);
        }
        return teamRepository.save(team);
    }

    public Team getTeam(Long id) {
        return teamRepository.findById(id)
            .orElseThrow(() -> new AuctionException("Team with ID " + id + " not found", 404));
    }
    public Team updateTeam(Team team) {
    	return teamRepository.save(team);
    }

	public List<Team> getAllTeams() {
		// TODO Auto-generated method stub
		return teamRepository.findAll();
	}

	public String deleteTeam(Long id) {
		// TODO Auto-generated method stub
		teamRepository.deleteById(id);
		return "id "+id+" deleted Successfully";
	}
    @Transactional
	public String deleteTeamByName(String name) {
		// TODO Auto-generated method stub
		teamRepository.deleteByName(name);
		return "name with "+name+" deleted successfully";
	}

	public Team getTeamByName(String name) {
		// TODO Auto-generated method stub
//		Optional<Team> team =  teamRepository.findByName(name);
//		if(team.isPresent()) {
//			return Optional.of(team.get());
//		}
//		return Optional.empty();
		return teamRepository.findByName(name).orElseThrow(() -> new RuntimeException("Team not Found!"));
		
		
	}
}
