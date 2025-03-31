# Player Auction Backend

This repository, `player-auction-backend`, contains the backend system for a player auction platform. It is built using a microservices architecture, where each service handles a specific domain of the application. The system enables users to manage players, teams, and auctions in a scalable and modular way.

## Table of Contents
- [Overview](#overview)
- [Microservices](#microservices)
  - [Eureka Server](#eureka-server)
  - [API Gateway](#api-gateway)
  - [User Service](#user-service)
  - [Team Service](#team-service)
  - [Player Service](#player-service)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [Contributing](#contributing)

## Overview
The `player-auction-backend` is a microservices-based system designed for managing a player auction platform. It includes services for service discovery, API routing, user management, team management, and player management. The microservices communicate with each other using REST APIs, and the system is designed to be scalable and resilient.

## Microservices

### Eureka Server
- **Purpose**: Acts as a service registry for all microservices in the system. Each microservice registers itself with the Eureka Server, enabling dynamic service discovery and load balancing.
- **Port**: `8761`
- **Key Features**:
  - Service registration and discovery.
  - Health monitoring of registered services.
- **Dependencies**:
  - Spring Cloud Netflix Eureka Server

### API Gateway
- **Purpose**: Serves as the entry point for all client requests. It routes requests to the appropriate microservices, handles authentication, and provides cross-cutting concerns like rate limiting.
- **Port**: `8080`
- **Key Features**:
  - Request routing to microservices.
  - Authentication and authorization.
  - Rate limiting and logging.
- **Dependencies**:
  - Spring Cloud Gateway

### User Service
- **Purpose**: Manages user-related operations, such as registration, login, and user profile management.
- **Port**: `8081`
- **Key Features**:
  - User registration and authentication.
  - User profile CRUD operations.
- **Dependencies**:
  - Spring Boot
  - Spring Data JPA (for database operations)
  - MySQL 

### Team Service
- **Purpose**: Handles team-related operations, such as creating teams, adding players to teams, and managing team budgets for auctions.
- **Port**: `8082`
- **Key Features**:
  - Team creation and management.
  - Budget tracking for auctions.
- **Dependencies**:
  - Spring Boot
  - Spring Data JPA
  - MySQL

### Player Service
- **Purpose**: Manages player-related operations, such as adding players, updating player stats, and listing players available for auction.
- **Port**: `8083`
- **Key Features**:
  - Player CRUD operations.
  - Player statistics and auction eligibility.
- **Dependencies**:
  - Spring Boot
  - Spring Data JPA
  - MySQL

## Technologies Used
- **Java**: Version 17
- **Spring Boot**: For building microservices
- **Spring Cloud**: For microservices features (Eureka, Gateway)
- **Maven**: Dependency management and build tool
- **MySQL**: Database for storing user, team, and player data
- **Spring Data JPA**: For database operations
- **JUnit**: For unit testing

## Prerequisites
Before setting up the project, ensure you have the following installed:
- Java 17 or higher
- Maven 3.6.x or higher
- Git (to clone the repository)
- MySQL (or your preferred database)
- An IDE like Eclipse or STS

## Setup Instructions
Follow these steps to set up the project locally:

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/player-auction-backend.git
   cd player-auction-backend

## Set Up the Database:
Install MySQL if not already installed.
Update the database configuration in each service's application.properties or application.yml file (located in src/main/resources of each microservice)

## Running the Application
Start the Eureka Server:
The Eureka Server will start on http://localhost:8761. Open this URL in your browser to verify the dashboard.

Start the API Gateway:
The API Gateway will start on http://localhost:8080

Start the Microservices:
Start each microservice in a separate terminal window (or use an IDE to run them):
cd user
mvn spring-boot:run

cd team
mvn spring-boot:run

cd player
mvn spring-boot:run

Verify the System:
Use a tool like Postman to test the API endpoints through the API Gateway. Example:
GET http://localhost:8080/api/users (routes to User Service)
GET http://localhost:8080/api/teams (routes to Team Service)
GET http://localhost:8080/api/players (routes to Player Service)

## Project Structure
A brief overview of the repository structure:

player-auction-backend/
├── eureka-server/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/auction/eureka/
│   │       └── resources/
│   │           └── application.yml
│   └── pom.xml
├── api-gateway/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/auction/api_gateway/
│   │       └── resources/
│   │           └── application.yml
│   └── pom.xml
├── user/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/auction/user/
│   │       └── resources/
│   │           └── application.yml
│   └── pom.xml
├── team/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/auction/team/
│   │       └── resources/
│   │           └── application.yml
│   └── pom.xml
├── player/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/auction/player/
│   │       └── resources/
│   │           └── application.yml
│   └── pom.xml
├── .gitignore
└── README.md

## Contributing
Contributions are welcome! To contribute:

Fork the repository.
Create a new branch (git checkout -b feature/your-feature).
Make your changes in the relevant microservice directory.
Commit your changes (git commit -m "Add your feature to user-service").
Push to your branch (git push origin feature/your-feature).
Open a pull request with a detailed description of your changes.
Please ensure your code follows the existing style and includes unit tests where applicable.
