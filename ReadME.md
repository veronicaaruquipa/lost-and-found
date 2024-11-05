# Lost and Found Application

## The Story

The Lost and Found application is designed to help manage lost items and their claims. It provides REST API endpoints
for administrators to upload and manage lost items, and for users to claim lost items. The application also integrates
with a mock user service to retrieve user information.

## Requirements

1. **Upload & Store Data**: A REST API Admin endpoint to upload lost items with details from a file. The application
   should extract and store the following information from the uploaded file:
    - ItemName
    - Quantity
    - Place

2. **Read LostItems**: A REST API user endpoint to read the saved lost items.

3. **Claim LostItem Data and Save**: A REST API user endpoint for users to claim the lost item. For example, User 1001
   claimed certain items and certain quantities from the retrieved list and store them with their user ID. Note: One
   LostItem can be claimed by more than one user.

4. **Retrieve LostItems Claimed by People**: A REST API Admin endpoint to read all the lost items and users (userId and
   name) associated with that.

5. **Retrieve User Information from User Service**: This will be another service (a mock service) to get user details
   like the name of the user. Consider writing the least code as this is only a mock.

## Getting Started

### Tech Stack

- *Java 17*
- *Spring Boot 3.x*
- *Maven 3.x*
- *JPA/Hibernate*
- *Jackson*
- *Lombok*
- *Docker Compose* (for containerization)
- *Oracle* (for database)

## How to Get Started

1. **Clone the Repository**
   ```sh
   git clone <repository-url>
   cd lost-and-found-app

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

6. **Out of Scope**
* Detailed user authentication and authorization mechanisms.
* Advanced error handling and logging.