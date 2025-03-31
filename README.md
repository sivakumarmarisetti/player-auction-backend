# Auction API Gateway

This project is an **API Gateway** for an auction system, built using Java and Spring Boot. It serves as the entry point for all client requests, routing them to appropriate microservices in the auction ecosystem. The API Gateway handles authentication, request routing, rate limiting, and other cross-cutting concerns.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)

## Features
- **Request Routing**: Routes incoming requests to the appropriate microservices (e.g., user service, bidding service).
- **Authentication**: Integrates with an authentication service to secure endpoints.
- **Rate Limiting**: Prevents abuse by limiting the number of requests per user.
- **Logging**: Centralized logging for monitoring and debugging.
- **Scalable Architecture**: Designed to handle high traffic for auction events.

## Technologies Used
- **Java**: Version 17
- **Spring Boot**: For building the API Gateway
- **Maven**: Dependency management and build tool
- **Spring Cloud Gateway**: For routing and filtering requests
- **JUnit**: For unit testing

## Prerequisites
Before setting up the project, ensure you have the following installed:
- Java 17 or higher
- Maven 3.6.x or higher
- Git (to clone the repository)
- An IDE like Eclipse or STS
