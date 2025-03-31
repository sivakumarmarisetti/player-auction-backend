package com.auction.player.service;

import com.auction.team.entity.Team;
import com.auction.player.exception.AuctionException;
import com.auction.player.exception.BusinessLogicException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class TeamClientService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    public Team fetchTeam(Long teamId) {
        // Resolve the team service URL and fetch team details in a single method
        String teamServiceUrl = resolveTeamServiceUrl() + "/api/teams";
        
        try {
            Team team = restTemplate.getForObject(teamServiceUrl + "/" + teamId, Team.class);
            if (team == null) {
                throw new BusinessLogicException("Team with ID " + teamId + " not found", 404);
            }
            return team;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new BusinessLogicException("Team with ID " + teamId + " not found", 404);
            }
            throw new RuntimeException("Failed to fetch team: " + e.getMessage(), e);
        }
    }

    @CircuitBreaker(name = "teamServiceCall", fallbackMethod = "teamServiceUrlFallback")
    private String resolveTeamServiceUrl() {
        List<ServiceInstance> instances = discoveryClient.getInstances("team");
        if (instances.isEmpty()) {
            throw new RuntimeException("Team service is unavailable. Please try again later.");
        	  
        }
        String teamServiceUrl = instances.get(0).getUri().toString();
        // Fetch team details with circuit breaker
        return teamServiceUrl;
    }

    @CircuitBreaker(name = "teamServiceFetch", fallbackMethod = "teamFetchFallback")
    public Team fetchTeamWithCircuitBreaker(String teamServiceUrl, Long teamId) {
        try {
            return restTemplate.getForObject(teamServiceUrl + "/" + teamId, Team.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new BusinessLogicException("Team with ID " + teamId + " not found", 404);
            }
            throw new RuntimeException("Failed to fetch team: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unused")
    private String teamServiceUrlFallback(Throwable t) {
        System.out.println("Service URL resolution fallback triggered due to: " + t.getMessage());
        throw new AuctionException("Team service is unavailable. Please try again later.", 503);
    }

    @SuppressWarnings("unused")
    private Team teamFetchFallback(String teamServiceUrl, Long teamId, Throwable t) {
        System.out.println("Team fetch fallback triggered due to: " + t.getMessage());
        throw new AuctionException("Team service is unavailable. Please try again later.", 500);
    }
}