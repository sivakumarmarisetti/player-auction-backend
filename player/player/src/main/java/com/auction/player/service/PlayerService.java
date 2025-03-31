package com.auction.player.service;

import java.util.List;
import com.auction.player.entity.Player;
import com.auction.player.repository.PlayerRepository;
import com.auction.team.entity.Team;
import com.auction.player.exception.AuctionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamClientService teamClientService;
    
    public Player createPlayer(Player player) {
        // Fetch team details from Team Service
        Team team = teamClientService.fetchTeam(player.getTeamId());

        // Calculate current budget for the team
        List<Player> teamPlayers = playerRepository.findByTeamId(player.getTeamId());
        double currentBudget = teamPlayers.stream()
            .mapToDouble(Player::getBudgetPrice)
            .sum();

        if (currentBudget + player.getBudgetPrice() > team.getMaxBudget()) {
            throw new AuctionException("Player can't be tagged to this team as it exceeds team's budget", 400);
        }

        return playerRepository.save(player);
    }
    public Player updatePlayer(Player updatedPlayer) {
        // Fetch the existing player from the repository using the player's ID
        Player existingPlayer = playerRepository.findById(updatedPlayer.getId())
            .orElseThrow(() -> new AuctionException("Player not found", 404));

        // Fetch team details from Team Service
        Team team = teamClientService.fetchTeam(updatedPlayer.getTeamId());

        // Calculate current budget for the team, excluding the current player if the team changes
        List<Player> teamPlayers = playerRepository.findByTeamId(updatedPlayer.getTeamId());
        double currentBudget = teamPlayers.stream()
            .mapToDouble(Player::getBudgetPrice)
            .sum();

        // If the player is changing teams, subtract the old player's budget from the current budget
        if (updatedPlayer.getTeamId() != existingPlayer.getTeamId()) {
//            Team oldTeam = teamClientService.fetchTeam(existingPlayer.getTeamId());
            currentBudget -= existingPlayer.getBudgetPrice();
        }

        // Check if the new budget exceeds the team's max budget
        if (currentBudget + updatedPlayer.getBudgetPrice() > team.getMaxBudget()) {
            throw new AuctionException("Player can't be tagged to this team as it exceeds team's budget", 400);
        }

        // Update player details
        existingPlayer.setName(updatedPlayer.getName());
        existingPlayer.setBudgetPrice(updatedPlayer.getBudgetPrice());
        existingPlayer.setTeamId(updatedPlayer.getTeamId());
        // You can update other fields as necessary

        return playerRepository.save(existingPlayer);
    }


    public Player getPlayer(Long id) {
        return playerRepository.findById(id)
            .orElseThrow(() -> new AuctionException("Player with ID " + id + " not found", 404));
    }

    public List<Player> getPlayersByTeam(Long teamId) {
        return playerRepository.findByTeamId(teamId);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
}