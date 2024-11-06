# Lost and Found Application

## Getting Started
### Purpose

The Lost and Found application is designed to help manage lost items and their claims. It provides REST API endpoints
for administrators to upload and manage lost items, and for users to claim lost items. The application also integrates
with a mock user service to retrieve user information.

### Tech Stack

- *Java 17*
- *Spring Boot 3.x*
- *Maven 3.x*
- *JPA/Hibernate*
- *Jackson*
- *Lombok*
- *Docker Compose* (for containerization)
- *Oracle* (dockerized Oracle database). To run the app, Oracle must be up and running.

## How to

1. **Clone the Repository**
   ```sh
   git clone https://github.com/veronicaaruquipa/lost-and-found.git
   cd lost-and-found

2. **Build the Project**
   ```sh
   mvn clean install

3. **Run the Application**
   ```sh  
   mvn spring-boot:run

4. **Docker Compose** (Optional)
   If you have Docker installed, you can use Docker Compose to run the application:
   ```sh
   docker-compose up

5. **Access the Endpoints**
