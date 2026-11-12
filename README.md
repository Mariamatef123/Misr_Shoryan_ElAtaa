# Misr Shoryan El-Ataa

### Integrated Charity Organization Management System

**A Centralized Digital Platform for Managing Donor Recruitment, Medical Screening Workflows, Volunteer Operations, and Charity Campaigns.**

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.13-brightgreen?logo=springboot)
![SQL Server](https://img.shields.io/badge/Database-SQL%20Server-red?logo=microsoftsqlserver)
![Maven](https://img.shields.io/badge/Build-Maven-blue?logo=apachemaven)
![Architecture](https://img.shields.io/badge/Architecture-Layered-purple)

---

## Overview

**Misr Shoryan El-Ataa** is an integrated charity management platform designed to support the operational activities of a non-profit organization dedicated to helping children suffering from Thalassemia.

The platform aims to centralize the charity's major workflows, connecting donor recruitment, preliminary medical screening, donor group coordination, volunteer recruitment, internal team management, and public relations campaigns.

The system provides a structured environment for charity staff to manage information, coordinate departments, enforce business rules, and automate selected operational processes.

Its primary objective is to improve donor coordination and support a more organized and reliable donation recruitment process.

---

## Business Requirements & System Scope

The platform is designed around the operational requirements of the charity and the needs of its internal departments and external users.

### 1. Donor Recruitment & Management

The system is designed to simplify donor registration and maintain centralized donor profiles.

**Business Requirements:**

- Donor registration using personal information such as name, email, phone, city, and age.
- Selection between one-time and repeat donation.
- Donor classification based on blood type and donation status.
- Preliminary medical eligibility screening.
- Medical history questionnaire covering medications, recent travel, and relevant lifestyle factors.
- Management of accepted, pending, and unavailable donors.
- Donor availability tracking and group assignment.

**Medical Screening Requirements Defined by the SRS:**

| Screening Criteria | Required Condition |
|---|---|
| Donor Age | 18–60 years |
| Hemoglobin – Men | At least 13.0 g/dL |
| Hemoglobin – Women | At least 12.5 g/dL |
| Weight | At least 50 kg |
| Vital Signs | Within applicable medical ranges |
| Medical History | Eligibility questionnaire |

> These are preliminary screening requirements documented for the project. Final donor eligibility must be determined by qualified medical professionals and the relevant blood bank or hospital.

### 2. LEP – Life Engineering Program

The Life Engineering Program (LEP) is responsible for coordinating children and their dedicated donor groups.

**Business Requirements:**

- Register and manage children supported by the charity.
- Create dedicated donor groups for individual children.
- Assign eligible repeat donors to specific groups.
- Maintain an ordered donor allocation.
- Track donation cycles every 3–4 months.
- Identify alternative or backup donors.
- Support urgent donor coordination when a group member is unavailable.
- Monitor donor group rotation.

**Donation Cycle Reference:**

| Item | Requirement |
|---|---|
| Donor Group | 5–6 repeat donors per child |
| Donation Frequency | Every 3–4 months |
| Backup Donors | Available one-time donors |
| Coordination | Managed by LEP staff |

### 3. Human Resources Management

The HR module supports the volunteer recruitment lifecycle, from application to department assignment.

**Business Requirements:**

- Digital volunteer application.
- Collection of academic information and previous student activity experience.
- Interview scheduling and management.
- Interview attendance tracking.
- Identification of applicants who do not attend interviews.
- Acceptance or rejection decisions.
- Assignment of accepted volunteers to suitable departments.

**Supported Department Categories:**

- LEP
- PR
- Media
- Decoration

### 4. Public Relations & Campaign Management

The PR module supports the planning and management of charity campaigns.

**Business Requirements:**

- Create, update, and manage campaigns.
- Validate campaign location.
- Check campaign dates against university examination periods.
- Verify campaign budget availability.
- Track the annual campaign target.
- Notify Media and Decoration teams when a campaign becomes active.

**Campaign Constraints Defined by the SRS:**

| Constraint | Requirement |
|---|---|
| Location | Inside Ain Shams University |
| Academic Timing | Must not conflict with examination periods |
| Budget | Must meet event requirements |
| Annual Frequency | At least two campaigns per year |

### 5. Media & Decoration Operations

The platform's planned scope includes coordination with Media and Decoration teams after campaign approval.

**Business Requirements:**

- Receive campaign activation notifications.
- Coordinate marketing and social media activities.
- Organize campaign setup and materials.
- Record the number of potential donors recruited.
- Track resources and materials used.
- Document public engagement and campaign results.

### 6. Educational & Public Information

The system's public-facing scope includes informational content intended to educate and encourage potential donors.

- Information about Thalassemia.
- Why blood donation matters.
- Frequently Asked Questions (FAQ).
- Charity mission and activities.
- Contact information and social media links.
- Donor registration entry point.

---

## User Roles & Responsibilities

The platform is designed to support both external users and internal charity teams.

| User / Role | Main Responsibilities |
|---|---|
| Donor | Registration and preliminary medical information submission |
| LEP | Child management, donor groups, donation cycles, and emergency coordination |
| HR | Volunteer applications, interviews, and department assignments |
| PR | Campaign planning and campaign management |
| Media | Campaign promotion and results reporting |
| Decoration | Campaign preparation and resource coordination |
| Administrators | Internal user and organizational management |

---

## System Workflows

### Donor Recruitment Workflow

```text
Donor Registration
       |
       v
Personal Information
       |
       v
Medical Information & Questionnaire
       |
       v
Preliminary Eligibility Review
       |
       v
Approved / Declined
       |
       v
Hospital or Blood Bank Appointment
```

### Repeat Donor Coordination Workflow

```text
Child Registration
       |
       v
Create Donor Group
       |
       v
Assign Eligible Repeat Donors
       |
       v
Organize Donation Order
       |
       v
Track Donation Cycle
       |
       v
Check Rotation Eligibility
       |
       v
Rotate Donor Group
```

### Volunteer Recruitment Workflow

```text
Volunteer Application
       |
       v
Interview Scheduling
       |
       v
HR Interview & Attendance
       |
       v
Accepted / Rejected
       |
       v
Department Assignment
       |
       v
Volunteer Management
```

### Campaign Management Workflow

```text
Campaign Creation
       |
       v
Validate Campaign Conditions
       |
       v
PR Approval
       |
       v
Notify Media & Decoration
       |
       v
Campaign Execution
       |
       v
Results & Resource Reporting
```

---

## System Architecture

The backend follows a layered architecture that separates HTTP handling, business logic, data access, and domain models.

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
                  Business Logic
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

| Component | Responsibility |
|---|---|
| Controllers | Handle HTTP requests and responses |
| Services | Implement business logic and workflows |
| Repositories | Handle database operations |
| Models | Represent application entities |
| DTOs | Transfer structured application data |
| Enums | Define controlled business classifications |
| Security | Contains login rate-limiting functionality |

---

## Technical Highlights

### 1. Login Rate Limiting

The backend implements a custom in-memory login rate limiter.

**Features:**

- Maximum of 3 failed login attempts.
- Temporary blocking for 1 minute.
- ConcurrentHashMap for concurrent access.
- Remaining-attempt tracking.
- Remaining-block-duration tracking.
- Reset functionality.

### 2. Scheduled Background Jobs

Spring Scheduling automates donor group rotation checks.

The application enables scheduling through `@EnableScheduling`.

The rotation scheduler:

- Executes daily at midnight.
- Retrieves registered donor groups.
- Checks rotation eligibility.
- Delegates rotation processing to the LEP service.

### 3. Business Rule Enforcement

The service layer implements business constraints, including:

- Donor group capacity restrictions.
- Donor acceptance validation.
- Repeat-donor eligibility.
- Blood type compatibility.
- Prevention of duplicate donor assignments.
- Donor allocation and rotation logic.

### 4. Database Management

The application uses Spring Data JPA and SQL Server for relational data persistence.

- Entity-based data modeling.
- Repository-based database access.
- Relational entity management.
- Centralized persistence operations.

### 5. Email Notifications

Spring Mail supports campaign-related email notifications when campaigns are created or updated.

---

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Primary programming language |
| Spring Boot 3.5.13 | Backend framework |
| Spring Web | HTTP endpoints and web functionality |
| Spring Data JPA | Data persistence |
| Hibernate | Object-relational mapping |
| Microsoft SQL Server | Relational database |
| Spring Validation | Input validation |
| Spring Mail | Email notifications |
| Spring Scheduling | Scheduled background execution |
| Thymeleaf | Server-side template support |
| HTML5 / CSS3 / JavaScript | Frontend technologies |
| Lombok | Boilerplate code reduction |
| Maven | Dependency and build management |

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
│   │   │           ├── Controllers/
│   │   │           ├── DTO/
│   │   │           ├── Dtos/
│   │   │           ├── Enums/
│   │   │           ├── Models/
│   │   │           ├── Repos/
│   │   │           ├── Security/
│   │   │           ├── Services/
│   │   │           └── MisrShoryanElataaApplication.java
│   │   │
│   │   └── resources/
│   │       ├── static/
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

- JDK 17 or later.
- Apache Maven or Maven Wrapper.
- Microsoft SQL Server.
- Git.
- SMTP credentials if email functionality is required.

### 1. Clone the Repository

```bash
git clone https://github.com/Mariamatef123/Misr_Shoryan_ElAtaa.git
cd Misr_Shoryan_ElAtaa
```

### 2. Configure the Database

Open:

```text
src/main/resources/application.properties
```

Configure your SQL Server connection and database credentials.

### 3. Configure Email

Configure the SMTP settings required by Spring Mail.

Keep database passwords and email credentials outside version control.

### 4. Run the Application

**Windows:**

```bash
mvnw.cmd spring-boot:run
```

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

### 5. Build the Application

```bash
mvn clean package
```

---

## Security & Quality Considerations

The SRS identifies the following requirements as important areas for the system:

- Role-Based Access Control (RBAC).
- Protection of sensitive donor and medical information.
- Secure staff authentication.
- Separation of access between medical and non-medical departments.
- Input validation and data integrity.
- Reliable database transactions.
- Availability during urgent donor coordination.

These are system requirements and security objectives; they should not be interpreted as confirmation that every security control has already been implemented.

---

## System Boundaries

The platform focuses on charity operations, donor recruitment, preliminary eligibility workflows, and coordination.

It does not replace hospitals or blood banks.

The system's responsibility ends at coordinating approved potential donors and providing appointment information. Physical blood extraction, laboratory testing, and final medical clearance remain the responsibility of qualified medical institutions.

---

## Backend Contributors

The backend development was collaboratively implemented by:

| Contributor | Role | GitHub |
|---|---|---|
| **Mariam Atef** | Backend Developer | [@Mariamatef123](https://github.com/Mariamatef123) |
| **Malak Ehab** | Backend Developer | [@malak-ehab](https://github.com/malak-ehab) |

**Backend Technologies:** Java 17, Spring Boot, Spring Data JPA, Hibernate, SQL Server, Maven.

---

## Future Improvements

Potential enhancements based on the project requirements include:

- Spring Security integration with JWT authentication and authorization.
- Role-based access control across all departments.
- Distributed rate limiting using Redis.
- Enhanced medical screening workflows.
- Automated donation-cycle reminders.
- Emergency donor notification workflows.
- Campaign constraint validation and team notifications.
- Centralized exception handling and structured logging.
- Comprehensive unit and integration testing.
- API documentation using OpenAPI / Swagger.
- Audit logging and monitoring.
- Docker-based deployment.

---

## Repository

**GitHub:** [Misr Shoryan El-Ataa](https://github.com/Mariamatef123/Misr_Shoryan_ElAtaa)

**Branch:** `main`

---

**Misr Shoryan El-Ataa**  
*Technology Supporting Charity Operations and Community Impact.*
