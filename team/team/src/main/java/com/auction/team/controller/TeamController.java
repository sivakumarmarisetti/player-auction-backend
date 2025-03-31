package com.auction.team.controller;


import com.auction.team.entity.Team;
import com.auction.team.service.TeamService;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        return ResponseEntity.ok(teamService.createTeam(team));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeam(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeam(id));
    }
    @GetMapping("/getAllTeams")
    public ResponseEntity<List<Team>> getAllTeams(){
    	return ResponseEntity.ok(teamService.getAllTeams());
    }
    @GetMapping("/getTeamByName/{name}")
    public Team team(@PathVariable String name) {
    	return teamService.getTeamByName(name);
    }
    
    @PutMapping
    public ResponseEntity<Team> updateTeam(@RequestBody Team team){
    	return ResponseEntity.ok(teamService.updateTeam(team));
    }
    @DeleteMapping("/{id}")
    public String deleteTeam(@PathVariable Long id){
    	return teamService.deleteTeam(id);
    }
    @DeleteMapping("/delete/{name}")
    public String deleteTeamByName(@PathVariable String name) {
    	return teamService.deleteTeamByName(name);
    }
}
