# Community Food Sharing Platform

A microservices-based application built with **Spring Boot** that enables communities to share surplus food, reduce waste, and track their collective environmental and social impact.

## Overview

The Community Food Sharing Platform allows users to list surplus food items for sharing, browse available listings, and track the positive impact of food sharing activities through a points and badge reward system. The application follows a microservices architecture with an API Gateway handling request routing across services.

## Architecture

The platform consists of three independently running microservices:

| Service | Port | Description |
|---------|------|-------------|
| **Food Listing Service** | 8080 | Core service for creating, browsing, updating, and deleting food listings |
| **API Gateway** | 8081 | Central entry point that routes client requests to the appropriate microservice |
| **Impact Tracking Service** | 8082 | Tracks community impact metrics, awards points, and assigns badges for food sharing activities |

## Tech Stack

- **Java** with **Spring Boot**
- **Spring Cloud Gateway** — for API routing and request forwarding
- **Spring Data JPA** with **Hibernate** — ORM for the Food Listing Service
- **SQLite** — persistent database for food listings
- **RestTemplate** — for synchronous inter-service communication
- **ConcurrentHashMap** — in-memory data store for the Impact Tracking Service
- **Springdoc OpenAPI (Swagger UI)** — API documentation
- **JUnit 5, MockMvc & Mockito** — unit testing

## Services Breakdown

### Food Listing Service (Port 8080)

Handles all CRUD operations for food listings. Each listing includes details such as food name, description, quantity, expiry date, and pickup location. Data is persisted in an SQLite database using Spring Data JPA.

**Key Endpoints:**
- `GET /api/food` — Retrieve all food listings
- `GET /api/food/{id}` — Retrieve a specific listing
- `POST /api/food` — Create a new food listing
- `PUT /api/food/{id}` — Update an existing listing
- `DELETE /api/food/{id}` — Delete a listing

### API Gateway (Port 8081)

Acts as the single entry point for all client requests. Built with Spring Cloud Gateway, it uses a RouteLocator bean to route requests to the appropriate downstream microservice based on the request path.

**Routing Configuration:**
- `/api/food/**` → Food Listing Service (Port 8080)
- `/api/impact/**` → Impact Tracking Service (Port 8082)

### Impact Tracking Service (Port 8082)

Tracks the community's food sharing impact using an in-memory ConcurrentHashMap (no database). It communicates with the Food Listing Service via RestTemplate to fetch listing data. Users earn points for sharing food and receive badges based on their contribution levels.

**Key Endpoints:**
- `GET /api/impact` — Retrieve all impact records
- `GET /api/impact/{userId}` — Retrieve impact data for a specific user
- `POST /api/impact` — Log a new food sharing activity and award points
- `GET /api/impact/{userId}/badges` — Retrieve badges earned by a user

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Running the Services

Start each service in a separate terminal, in the following order:

```bash
# 1. Food Listing Service
cd food-listing-service
mvn spring-boot:run

# 2. Impact Tracking Service
cd impact-tracking-service
mvn spring-boot:run

# 3. API Gateway
cd api-gateway
mvn spring-boot:run
```

### API Documentation

Once the services are running, Swagger UI is available at:

- Food Listing Service: `http://localhost:8080/swagger-ui.html`
- Impact Tracking Service: `http://localhost:8082/swagger-ui.html`

## Project Structure

```
community-food-sharing-platform/
├── food-listing-service/       # Core CRUD microservice (SQLite + JPA)
├── api-gateway/                # Spring Cloud Gateway routing service
├── impact-tracking-service/    # Points & badges tracking microservice
└── README.md
```

## Testing

Unit tests are written using JUnit 5 with MockMvc and Mockito, following the Arrange-Act-Assert pattern.

```bash
# Run tests for a specific service
cd food-listing-service
mvn test
```

## Future Development

- Frontend application (group coursework — in progress)
- User authentication and authorisation
- Persistent database for the Impact Tracking Service
- Service discovery with Spring Cloud Eureka

---

*Built as part of the KF7014 Advanced Programming module at Northumbria University.*
