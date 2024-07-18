# Banking System Application

<div style="display: flex; align-items: center;">

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Ryzeon_banking-system&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Ryzeon_banking-system)
<div style="width: 20%; margin-left: 10px">

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/summary/new_code?id=Ryzeon_banking-system)

</div>
</div>

## Table of Contents
- [Overview](#overview)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Contact](#contact)

## Overview

This Banking System application is architected using Domain-Driven Design (DDD) principles and implements the Command
Query Responsibility Segregation (CQRS) pattern. It utilizes Spring Boot for its framework, H2 as an in-memory database
for local testing, JaCoCo for test coverage analysis, and Mockito for mocking in tests.

## Technologies

- **Spring Boot**: Provides a comprehensive framework for building Java applications with minimal boilerplate.
- **H2 Database**: An in-memory database ideal for development and testing phases.
- **JaCoCo**: A tool for measuring and reporting Java code coverage.
- **Mockito**: Facilitates the creation of mock objects for the purpose of testing.

## Getting Started

### Prerequisites

- JDK 17
- Maven 3.6+

### Setup and Installation

1. Clone the repository:
   ```shell
   git clone https://github.com/Ryzeon/banking-system.git
    ```
2. Navigate to the project directory:
   ```shell
   cd banking-system
   ```
3. Build the project:
   ```shell
    mvn clean install
    ```
4. Run the application:
   ```shell
    mvn spring-boot:run
    ```

### Architecture
The application is structured around DDD and CQRS principles, with a clear separation between the command and query models. This separation enhances the scalability and maintainability of the application.

#### Domain Model
The domain model consists of entities, value objects, and aggregates that represent the core business concepts of the application. These classes are located in the `domain` package.

#### Application Layer
The application layer contains the command and query services that interact with the domain model. The command services handle the creation and modification of entities, while the query services handle read operations. These classes are located in the `application` package.

#### Infrastructure Layer
The infrastructure layer contains the repositories that interact with the database, as well as any other external dependencies. The repositories are implemented using Spring Data JPA and are located in the `infrastructure` package.

## API Endpoints

### Accounts
- `POST /accounts`: Create a new account
- `GET /accounts/{accountNumber}`: Retrieve account details by account number
- `PUT /accounts/details/{accountNumber}`: Update account details
- `POST /accounts/close/{accountNumber}`: Close an account

### Transactions
- `POST /transactions/deposit`: Perform a deposit transaction
- `POST /transactions/withdraw`: Perform a withdrawal transaction

### Testing
Unit tests are written using JUnit and Mockito, ensuring the reliability and functionality of the application. JaCoCo is used for generating test coverage reports.

To run the tests and generate a coverage report, use the following command:
```shell
mvn clean test
```

## Contact
For any questions or concerns, please open an issue on GitHub or contact us directly at [dev@ryzeon.me](mailto:dev@ryzeon.me).