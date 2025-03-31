package com.auction.player.service;




import com.auction.player.entity.Player;
import com.auction.player.repository.PlayerRepository;
import com.auction.team.entity.Team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PlayerService playerService;

    private Player player;
    private Team team;

    @BeforeEach
    void setUp() {
        player = new Player("Player1", 100.0, 1L);
        team = new Team("Thunderbird", 500.0);

        // Set the team-service URL using ReflectionTestUtils
        ReflectionTestUtils.setField(playerService, "teamServiceUrl", "http://localhost:8082/api/teams");
    }

    @Test
    void testCreatePlayer_Success() {
        when(restTemplate.getForObject("http://localhost:8082/api/teams/1", Team.class)).thenReturn(team);
        when(playerRepository.findByTeamId(1L)).thenReturn(Arrays.asList());
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player createdPlayer = playerService.createPlayer(player);

        assertNotNull(createdPlayer);
        assertEquals("Player1", createdPlayer.getName());
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void testCreatePlayer_TeamNotFound() {
        when(restTemplate.getForObject("http://localhost:8082/api/teams/1", Team.class)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            playerService.createPlayer(player);
        });

        assertEquals("Team not found", exception.getMessage());
        verify(playerRepository, never()).save(any(Player.class));
    }

    @Test
    void testCreatePlayer_ExceedsBudget() {
        when(restTemplate.getForObject("http://localhost:8082/api/teams/1", Team.class)).thenReturn(team);
        when(playerRepository.findByTeamId(1L)).thenReturn(Arrays.asList(new Player("Player2", 450.0, 1L)));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            playerService.createPlayer(player);
        });

        assertEquals("Player can't be tagged to this team as it exceeds team's budget", exception.getMessage());
        verify(playerRepository, never()).save(any(Player.class));
    }

    @Test
    void testGetPlayer_Success() {
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        Player foundPlayer = playerService.getPlayer(1L);

        assertNotNull(foundPlayer);
        assertEquals("Player1", foundPlayer.getName());
    }

    @Test
    void testGetPlayersByTeam_Success() {
        List<Player> players = Arrays.asList(player);
        when(playerRepository.findByTeamId(1L)).thenReturn(players);

        List<Player> result = playerService.getPlayersByTeam(1L);

        assertEquals(1, result.size());
        assertEquals("Player1", result.get(0).getName());
    }
}