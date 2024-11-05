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
- *Oracle* (for database)

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

* Upload Lost Items:
   ```sh   
   POST /api/lost-items/upload

* Read Lost Items:
   ```sh
   GET /api/lost-items/all-lost-items
    
* Claim Lost Item:
  ```sh
  POST /api/lost-items/claim

* Retrieve Claims:
  ```sh
  GET /api/lost-items/claims

### Out of Scope
* Detailed user authentication and authorization mechanisms.
* Advanced error handling and logging.