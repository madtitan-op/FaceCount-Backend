# FaceCount-Backend

A Spring Boot backend service for storing attendance and user data. \
Full project: [FaceCount](https://github.com/madtitan-op/FaceCount)

## Project Overview

FaceCount-Backend is a Java-based backend built with Spring Boot 3.4.2 that provides REST APIs for managing student attendance records and user information management.

## Technology Stack

- **Java 21**
- **Spring Boot 3.4.2**
- **PostgreSQL** - Database
- **Spring Data JPA** - Data persistence
- **Lombok** - Reduces boilerplate code
- **Maven** - Build and dependency management

## Prerequisites

- JDK 21 or higher
- Maven 3.6 or higher
- PostgreSQL database
- Git

## Getting Started

### Installation

1. Clone the repository:
```bash
git clone https://github.com/madtitan-op/FaceCount-Backend.git
cd FaceCount-Backend
```

2. Configure your PostgreSQL database:
   - Create a new database (I've named it `facecount`)
   - Update the application.properties file with your database credentials (optional)
   - Run the `schema.sql` script in your database
   - Run the `data.sql` script to insert dummy data _(OR You can insert your own data)_

   `These scripts are in` `src/main/resources/db`

3. Build the project:
```bash
mvn clean install
```
OR
```bash
./mvnw clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```
OR
```bash
cd target
java -jar <filename> --DB_URL=<database_URL> --DB_USERNAME=<username> --DB_PASSWORD=<password>
```
e.g.- `java -jar FaceCount-1.0.jar --DB_URL=jdbc:postgresql://localhost:5432/facecount --DB_USERNAME=postgres --DB_PASSWORD=secret`

The application will start on the default port 8080.

You will find the API endpoints at `http://localhost:8080/swagger-ui/index.html`

## Project Structure

```
FaceCount-Backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   └── resources/
│   └── test/
├── target/ (*after building folder will be created automatically)
├── .gitignore
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Dependencies

- **spring-boot-starter-web**: For building web applications
- **spring-boot-starter-security**: For securing the application
- **spring-boot-starter-data-jpa**: For database operations
- **postgresql**: PostgreSQL database driver
- **lombok**: For reducing boilerplate code
- **spring-boot-starter-test**: For testing

## Development

The project uses Maven as the build tool. Common Maven commands:

- `mvn clean`: Clean the project
- `mvn compile`: Compile the source code
- `mvn test`: Run tests
- `mvn package`: Package the application
- `mvn spring-boot:run`: Run the application

## Contact

Animesh Mahata \
Mail- manimesh12.10@gmail.com \
LinkedIn- [linkedin.com/in/animesh-mahata](https://www.linkedin.com/in/animesh-mahata/)
