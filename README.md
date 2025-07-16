# Microservices-Based Quiz Application

This project is a full-stack backend system built using Spring Boot, following a microservices architecture. It features JWT-based authentication, API Gateway, Eureka Service Discovery, and modular services for authentication, quiz management, and question management.

------------------Project Structure---------------------
###### Microservice	Description #######
2) api-gateway:- 	      Central entry point, routes external requests to microservices. Also validates JWT tokens.
3) auth-service:-     	Handles user registration, login, and JWT token generation.
4) quiz-service:-	      Manages quiz-related logic (e.g., creating quizzes, listing them).
5) question-service:-	  Manages question-related data like adding and retrieving questions.
6) eureka-server:-	    Service registry for enabling service discovery among microservices.

---------------Authentication Flow (JWT)--------------------------
1) A user sends credentials to /auth/login.
2) auth-service validates credentials and returns a JWT token.
3) This token must be sent in the Authorization header with every secured request:
4) Authorization: Bearer <your_token>
4) api-gateway verifies the token using a custom filter before routing to downstream services.

⚙️ Technologies Used: 
                 1) Spring Boot 3.5.3
                 2) Spring Cloud 2025.0.0 (Leyton)
                 3) JWT (io.jsonwebtoken)
                 4) Spring Security
                 5) Spring Data JPA (MySQL)
                 6) Eureka Server
                 7) Spring Cloud Gateway (MVC based)

####################################### Service Details #######################################
----------------------------------1. Auth Service (auth-service)-----------------------------------
Responsibilities:- User registration (/auth/register)
                   User login (/auth/login)
                   JWT generation & validation logic

Key Classes:
            JwtUtil: for token creation and validation
            UserDetailsServiceImpl: loads user from DB
            SecurityConfig: Spring Security configuration

----------------------------------2. Question Service (question-service)-----------------------------------
Responsibilities:- CRUD operations on questions
                   Routes protected by JWT

Endpoints:
        /Questions/getAll
        /Questions/add

Exposed via: /question/** through the API Gateway

-----------------------------------------3. Quiz Service (quiz-service)----------------------------------
Responsibilities:- Managing quizzes, linking with questions

Endpoints:
          /quiz/create
          /quiz/getAll

Exposed via: /quiz/** through the API Gateway

--------------------------------------4. API Gateway (api-gateway)---------------------------------------
Responsibilities: Entry point for all clients
                  Route management for microservices
                  Validates JWT tokens using a custom filter

Configuration (example):
                      spring.cloud.gateway.server.webmvc.routes[0].id=question
                      spring.cloud.gateway.server.webmvc.routes[0].uri=lb://QUESTION-SERVICE
                      spring.cloud.gateway.server.webmvc.routes[0].predicates[0]=Path=/question/**
                      spring.cloud.gateway.server.webmvc.routes[0].filters[0]=RewritePath=/question/(?<segment>.*), /${segment}


----------------------------------5. Eureka Server (eureka-server)-----------------------------------
Responsibilities: Keeps track of all running services
                  Enables dynamic service discovery

URL: http://localhost:8761

🧪 How to Test
Start Eureka Server.

---------------------------Start all other microservices: auth-service, quiz-service, question-service, api-gateway............................

---Register a user via:---
POST /auth/register

---Log in to get a JWT:---
POST /auth/login

---Call a protected endpoint via Gateway:---
GET /question/Questions/getAll
Authorization: Bearer <token>


------------------Notes-------------------
Each microservice runs on a separate port and registers itself with Eureka.

All external traffic is routed through api-gateway.

Stateless JWT-based auth — token must be sent with every request.

Uses spring-cloud-gateway-server-webmvc (the correct dependency for Spring Boot 3.2+).

Would you like a markdown version or a badge-enhanced README template as well?

