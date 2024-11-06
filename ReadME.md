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

## How to run the application locally or from Docker

1. **Clone the Repository**
   ```sh
   git clone https://github.com/veronicaaruquipa/lost-and-found.git
   cd lost-and-found

2. **Set Up the Database**

   * The application uses an Oracle database. To run the application, you need to have Oracle up and running. You can spin
   up docker container locally or use docker-compose to run the application.
     ```sh
     docker run -d --name oracle-express-db -p 1521:1521 -p 5500:5500 -e ORACLE_PWD=<YOUR_DB_USER_PASSWORD> -v <DIR_LOCALLY_DEFINED>:/opt/oracle/oradata container-registry.oracle.com/database/express:21.3.0-xe
     docker exec oracle-express-db ./setPassword.sh <YOUR_DB_USER_PASSWORD>
     
   *   The database will be available at `localhost:1521` with the following credentials:
       - *Username*: `SYSTEM`
       - *Password*: `<YOUR_DB_USER_PASSWORD>`
       - *Service Name*: `XE`  

3. **Run tests**
   ```sh
   mvn clean test

4. **Build the Project**
   ```sh
   mvn clean install

5. **Run the Application**
   ```sh  
   mvn spring-boot:run

6. **Docker Compose** (Optional)
   If you have Docker installed, you can use Docker Compose to run the application:
   ```sh
   docker-compose up --build

7. **Access the Endpoints**
    * Upload Lost Items:
      ```sh   
      curl -X POST -F "file=@C:\\<file-directory>\\LostItems.pdf" http://localhost:8080/api/lost-items/upload

    * Read Lost Items:
       ```sh
       curl -X GET "http://localhost:8080/api/lost-items"

    * Claim Lost Item:
      ```sh
      curl -X POST "http://localhost:8080/api/lost-items/claim" -d "lostItemId=43" -d "userId=1001" -d "quantity=2"

    * Retrieve Claims:
      ```sh
      curl -X GET "http://localhost:8080/api/lost-items/claims" 

### Out of Scope

* Detailed user authentication and authorization mechanisms.
* Advanced error handling and logging.
