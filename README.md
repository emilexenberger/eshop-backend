# E-shop Backend Project
*Ing. Emil Exenberger, PhD., MBA*

## Overview
This is the backend component of an e-shop application. It is developed using Java and Spring Boot. The backend provides RESTful APIs for user authentication, product management, shopping cart functionality, and order processing.

## Technologies Used

### Frameworks and Libraries
- **Spring Boot** (Spring MVC, Spring Security, Spring Data JPA, Spring Boot Test)
- **JUnit** (JUnit 5, MockMvc)

### Database Technologies
- **MySQL** (Database initialization with SQL dumps)
- **Hibernate** (JPA implementation)

### Additional Java Concepts and Skills
- **Collections and Streams** (Working with Java collections and streams)
- **Annotations** (Spring Boot and JPA)
- **RESTful APIs**
- **JWT Authentication** (Custom JWT utilities and filters)
- **CORS Configuration** (Custom CORS setup)

### Unit Testing and Integration Testing
- **Unit Testing** (JUnit 5)
- **Integration Testing** (MockMvc, Spring Boot Test)

### Security
- **JWT Authentication**
- **Spring Security**


## Application Features
- **User Authentication**
   - JWT-based authentication with Spring Security
   - User registration, login, and token refresh
- **Product Management**
   - CRUD operations for products
   - Product data stored in MySQL
- **Shopping Cart**
   - Add, modify, and remove items in the cart
   - Cart data stored in MySQL
- **Order Processing**
   - Create orders from cart items
   - View order history and order details
- **Role-Based Access Control**
   - Separate permissions for admin and regular users
- **CORS Configuration**
   - Custom configuration to allow cross-origin requests from specific sources

## Installation and Running the Project
To run this project locally, follow these steps:

1. **Clone the repository**


2. **Set up MySQL database**:
   - Install MySQL if needed.
   - Create a new database named `eshop_db`.
   - Import the SQL dump (`db/eshop_db.sql`) to initialize the schema and populate initial data.


3. **Configure application properties**:
   - Create `src/main/resources/application-local.yml`
```
spring:
  datasource:
    url: jdbc:mysql://<ip-address>:<port>/eshop_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
    username: <username>
    password: <password>
```


4. **Run the application**:
    - Run the EshopApplication.java with the local profile.
```
mvn spring-boot:run -Dspring-boot.run.profiles=local
```


5. **Access the application**:
    - The application will start on `localhost:8080`. Use tools like Postman (export of the collection is included in the folder `/postman` or curl to interact with the RESTful APIs.

 
6. **Frontend application**:
    - The frontend for this project is developed using React in this public repository: [eshop-frontend](https://github.com/emilexenberger/eshop-frontend).


7. Already available users in the database:
   - **name: admin, password: admin, role: ADMIN** - Users with the role ADMIN have additional access to Item management and User Management accessable from the Home page.
   - **name: test, password: test, role: USER**

## Unit Testing
Tests are available in the location `/src/test`.

## Contributions
This project serves as a showcase of my skills and knowledge. Although this repository is public, I kindly ask that you do not submit pull requests or make changes. The purpose of this repository is to demonstrate my capabilities, and any changes might affect its integrity. Thank you for understanding.



