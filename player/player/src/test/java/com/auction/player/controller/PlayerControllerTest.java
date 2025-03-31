package com.auction.player.controller;


import com.auction.player.entity.Player;
import com.auction.player.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @Autowired
    private ObjectMapper objectMapper;

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Player1", 100.0, 1L);
        player.setId(1L);
    }

    @Test
    void testCreatePlayer_Success() throws Exception {
        when(playerService.createPlayer(any(Player.class))).thenReturn(player);

        mockMvc.perform(post("/api/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Player1"));
    }

    @Test
    void testGetPlayer_Success() throws Exception {
        when(playerService.getPlayer(1L)).thenReturn(player);

        mockMvc.perform(get("/api/players/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Player1"));
    }

    @Test
    void testGetPlayersByTeam_Success() throws Exception {
        when(playerService.getPlayersByTeam(1L)).thenReturn(Arrays.asList(player));

        mockMvc.perform(get("/api/players/team/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Player1"));
    }
}
