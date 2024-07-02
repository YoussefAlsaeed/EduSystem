# Online Learning Platform

This project is an implementation of an online learning platform designed to familiarize with the concepts provided by EJBs and microservices. The platform is developed using three microservices: User Management Service, Course Management Service, and Notification Management Service. The Course Management Service is implemented using EJB, while the User Management and Notification Management Services are implemented using Spring Boot. All services are dockerized for easy deployment.

## Functional Requirements

### Admins
- View and manage user accounts (students and instructors).
- Review course content before publication to ensure quality and compliance.
- Edit or remove courses that violate policies.
- Track platform usage, course popularity, ratings, and reviews.

### Instructors
- Register and log in with details such as name, email, password, affiliation, years of experience, and bio.
- Create courses with attributes like name, duration, category, rating, capacity, number of enrolled students, and list of reviews.
- View detailed course information and search courses by name, category, or sort by ratings.
- Accept or reject student enrollments.

### Students
- Register and log in with details such as name, email, password, affiliation, and bio.
- View current and past course enrollments.
- View detailed course information and search courses by name, category, or sort by ratings.
- Enroll or cancel course enrollments, with special handling for server failures.
- Get notified of course enrollment updates.
- Review and rate courses.

## Microservices Architecture

### User Management Service
- Handles user registration, login, and profile management.
- Implemented using Spring Boot.
- Exposes REST APIs for user operations.

### Course Management Service
- Handles course creation, management, and enrollment processing asynchronously.
- Implemented using EJB (Stateless and Message Driven Beans).
- Exposes REST APIs for course operations.

### Notification Management Service
- Manages notifications for course enrollment updates.
- Implemented using Spring Boot.
- Exposes REST APIs for notification operations.

## Dockerization

The project is dockerized for easy deployment. Each microservice has its own Docker image, including the databases used.

## Running the Project

To run the project using Docker:

1. Ensure Docker is installed on your machine.
2. Open a terminal and navigate to the project directory.
3. Execute the `dockerize.bat` script:
   ```bash
   ./dockerize.bat
4. The script will build and run all the Docker containers required for the microservices and databases.

## Technologies Used
- Java
- Spring Boot
- EJB (Enterprise Java Beans)
- Docker
- REST APIs

## Directory Structure

/EduSystem
    /CourseMgmtMs
    /NotifMgmtMs
    /UserMgmtMs
    /frontend-lms
    dockerize.bat

## Conclusion
This project demonstrates the implementation of an online learning platform using EJBs and microservices architecture. The use of Docker ensures easy deployment and management of the microservices.    
