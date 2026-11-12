# Misr Shoryan El-Ataa

### Integrated Non-Profit Organization Management System


## A Centralized Platform for Managing Donor Recruitment, Volunteer Operations, Organizational Workflows, and Community Campaigns.


---

## Overview

**Misr Shoryan El-Ataa** is a centralized management platform designed to digitize and coordinate the operational activities of a non-profit organization supporting children with Thalassemia.

The system brings together donor management, donor group organization, volunteer recruitment, Human Resources operations, organizational campaigns, and staff management in one integrated platform.

Instead of relying on disconnected processes and manual coordination, the platform provides structured workflows, centralized data management, automated operations, and dedicated management modules.

## Core Modules

### 1. Donor Management

Provides centralized donor information management and donor classification.

- Donor registration and profile management.
- Blood type classification.
- One-time and recurring donor categorization.
- Accepted and pending donor management.
- Donor availability tracking.
- Donor assignment to groups.

### 2. LEP – Life Engineering Program

Manages donor groups and their association with children.

**Key capabilities:**

- Child registration and management.
- Donor group creation and management.
- Blood type compatibility validation.
- Recurring donor assignment.
- Group capacity enforcement (3–6 donors).
- Ordered donor allocation.
- Current donor and backup donor selection.
- Automated donor rotation.

### 3. Human Resources Management

Coordinates internal recruitment and staff operations.

- Staff registration and management.
- Volunteer recruitment.
- Interview creation and scheduling.
- Interview slot management.
- Volunteer interview assignments.
- Recruitment status management.
- Administrative role checks.

### 4. Public Relations & Campaign Management

Provides tools for coordinating organizational campaigns.

- Campaign creation and management.
- Campaign updates and deletion.
- Campaign activation management.
- Campaign scheduling information.
- Email notifications for new and updated campaigns.

### 5. Staff & User Management

Centralizes organizational users and staff information.

- User management.
- Staff management.
- Organizational role classification.
- Administrative operations.
- Role-specific business rules.

### 6. Medical Values Management

Provides a dedicated module for managing medical values used by the organization's donor-related workflows.

---

## System Architecture

The backend follows a layered architecture that separates HTTP handling, business logic, persistence, and domain models.

```text
                Client / Browser
                       |
                       v
              Spring Boot Web Layer
                       |
                       v
                  Controllers
                       |
                       v
                   Services
                 (Business Logic)
                       |
                       v
                 Repositories
                       |
                       v
                 Spring Data JPA
                       |
                       v
                  SQL Server
```

### Architectural Components

| Layer        | Responsibility                                          |
| ------------ | ------------------------------------------------------- |
| Controllers  | Handle incoming HTTP requests and responses             |
| Services     | Implement business rules and workflows                  |
| Repositories | Handle database access through Spring Data JPA          |
| Models       | Represent domain entities                               |
| DTOs         | Transfer structured data between application components |
| Enums        | Define controlled business states and classifications   |
| Security     | Contains login rate-limiting functionality              |

---

## Technical Highlights

### 1. Login Rate Limiting

The application implements a custom in-memory login rate limiter.

**Implementation:**

- Maximum of 3 failed login attempts.
- Temporary blocking for 1 minute.
- ConcurrentHashMap for concurrent access.
- Remaining-attempt tracking.
- Remaining-block-duration tracking.
- Reset functionality.

This provides an additional layer of protection against repeated login attempts.

### 2. Scheduled Background Jobs

The system uses Spring Scheduling to automate donor group rotation checks.

**Implementation:**

```java
@EnableScheduling
@SpringBootApplication
public class MisrShoryanElataaApplication {
    public static void main(String[] args) {
        SpringApplication.run(
            MisrShoryanElataaApplication.class,
            args
        );
    }
}
```

The scheduled rotation process:

- Executes daily at midnight.
- Retrieves registered donor groups.
- Checks rotation eligibility.
- Delegates rotation processing to the LEP service.

### 3. Business Rule Enforcement

Business constraints are enforced in the service layer.

Examples include:

- Donor group capacity restrictions.
- Donor acceptance validation.
- Repeat-donor eligibility.
- Blood type compatibility.
- Prevention of duplicate group assignments.
- Minimum donor group size.

### 4. Data Persistence

The application uses Spring Data JPA to manage relational data.

- Entity-based data modeling.
- Repository-based persistence.
- SQL Server integration.
- Relationship management between organizational entities.

### 5. Email Notifications

Spring Mail is used to send organizational campaign notifications.

The system supports notifications when campaigns are created or updated.

---

## Technology Stack

| Technology                | Purpose                              |
| ------------------------- | ------------------------------------ |
| Java 17                   | Primary programming language         |
| Spring Boot 3.5.13        | Backend application framework        |
| Spring Web                | HTTP endpoints and web functionality |
| Spring Data JPA           | Persistence abstraction              |
| Hibernate                 | ORM                                  |
| Microsoft SQL Server      | Relational database                  |
| Spring Validation         | Input validation                     |
| Spring Mail               | Email notifications                  |
| Spring Scheduling         | Automated scheduled execution        |
| Thymeleaf                 | Server-side template support         |
| HTML5 / CSS3 / JavaScript | Frontend                             |
| Lombok                    | Boilerplate reduction                |
| Maven                     | Dependency and build management      |

---

## Project Structure

```text
Misr_Shoryan_ElAtaa/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/misrshoryanelataa/
│   │   │       └── misr_shoryan_elataa/
│   │   │           │
│   │   │           ├── Controllers/
│   │   │           ├── DTO/
│   │   │           ├── Dtos/
│   │   │           ├── Enums/
│   │   │           ├── Models/
│   │   │           ├── Repos/
│   │   │           ├── Security/
│   │   │           ├── Services/
│   │   │           │
│   │   │           └── MisrShoryanElataaApplication.java
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── CSS/
│   │       │   ├── JS/
│   │       │   ├── Images/
│   │       │   └── *.html
│   │       │
│   │       └── application.properties
│   │
│   └── test/
│
├── .mvn/
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

## Getting Started

### Prerequisites

Make sure you have installed:

- JDK 17 or later.
- Apache Maven (or use the included Maven Wrapper).
- Microsoft SQL Server.
- Git.
- An SMTP account if email functionality is required.

### 1. Clone the Repository

```bash
git clone https://github.com/Mariamatef123/Misr_Shoryan_ElAtaa.git
```

```bash
cd Misr_Shoryan_ElAtaa
```

### 2. Configure the Database

Open:

```text
src/main/resources/application.properties
```

Configure your SQL Server connection and database credentials.

Do not commit actual database passwords or email credentials to GitHub. Use environment variables for sensitive configuration.

### 3. Configure Email

Configure the SMTP settings required by Spring Mail using your own email provider credentials.

### 4. Run the Application

Windows:

```bash
mvnw.cmd spring-boot:run
```

Linux / macOS:

```bash
./mvnw spring-boot:run
```

### 5. Build the Application

```bash
mvn clean package
```

---

## Development Practices

The project uses several software engineering practices:

- Separation of concerns through layered architecture.
- Reusable service and repository components.
- DTO-based data transfer.
- Centralized domain models.
- Business rule validation.
- Scheduled task automation.
- Concurrent data structures for rate limiting.
- Dependency injection through Spring.

---

## Future Improvements

Potential technical enhancements include:

- Spring Security integration with JWT authentication and authorization.
- Distributed rate limiting using Redis.
- Centralized exception handling.
- Structured application logging.
- Comprehensive unit and integration testing.
- API documentation using OpenAPI / Swagger.
- More robust asynchronous job processing.
- Audit logging and monitoring.
- Docker-based deployment.

---

## Repository

**GitHub:** [https://github.com/Mariamatef123/Misr_Shoryan_ElAtaa](https://github.com/Mariamatef123/Misr_Shoryan_ElAtaa)

**Branch:** `main`

---

\<p align="center">
&#x20; \<strong>Misr Shoryan El-Ataa\</strong>
&#x20; \<br/>
&#x20; Technology Supporting Better Organizational Operations.
\</p>
