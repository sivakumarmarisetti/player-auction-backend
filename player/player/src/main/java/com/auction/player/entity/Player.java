package com.auction.player.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double budgetPrice;
    private Long teamId; // Reference to Team ID

    // Constructors
    public Player() {}
    public Player(String name, double budgetPrice, Long teamId) {
        this.name = name;
        this.budgetPrice = budgetPrice;
        this.teamId = teamId;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getBudgetPrice() { return budgetPrice; }
    public void setBudgetPrice(double budgetPrice) { this.budgetPrice = budgetPrice; }
    public Long getTeamId() { return teamId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }
}
