package com.auction.team.service;


import com.auction.team.entity.Team;
import com.auction.team.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team("Thunderbird", 500.0);
    }

    @Test
    void testCreateTeam_Success() {
        when(teamRepository.findByName("Thunderbird")).thenReturn(Optional.empty());
        when(teamRepository.save(any(Team.class))).thenReturn(team);

        Team createdTeam = teamService.createTeam(team);

        assertNotNull(createdTeam);
        assertEquals("Thunderbird", createdTeam.getName());
        verify(teamRepository, times(1)).save(team);
    }

    @Test
    void testCreateTeam_TeamExists() {
        when(teamRepository.findByName("Thunderbird")).thenReturn(Optional.of(team));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            teamService.createTeam(team);
        });

        assertEquals("Team already exists", exception.getMessage());
        verify(teamRepository, never()).save(any(Team.class));
    }

    @Test
    void testGetTeam_Success() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));

        Team foundTeam = teamService.getTeam(1L);

        assertNotNull(foundTeam);
        assertEquals("Thunderbird", foundTeam.getName());
    }

    @Test
    void testGetTeam_NotFound() {
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            teamService.getTeam(1L);
        });

        assertEquals("Team not found", exception.getMessage());
    }
}
