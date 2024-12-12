# Employee Management System

This repository contains two Spring Boot applications, **Employee-Management** and **Employee-Database**, designed to manage employee data. **Employee-Management** exposes REST APIs to external consumers, while **Employee-Database** handles database operations, stores data in an H2 database, and performs operations like employee creation, updates, and deletions.

---

## **Table of Contents**

1. [Project Overview](#project-overview)
2. [Requirements](#requirements)
3. [Employee-Management](#employee-management)
    - [Setup Instructions](#setup-instructions)
    - [API Documentation](#api-documentation)
    - [Security and Role-Based Access Control](#security-and-role-based-access-control)
4. [Employee-Database](#employee-database)
    - [Setup Instructions](#setup-instructions-1)
    - [API Documentation](#api-documentation-1)
    - [Database Schema](#database-schema)
5. [Testing](#testing)
6. [Docker Setup](#docker-setup)
7. [Usage Example](#usage-example)

---

## **Project Overview**

This project consists of two Spring Boot applications:

- **Employee-Management**: Exposes REST APIs for managing employee data. It validates incoming requests, ensures role-based access control, and interacts with **Employee-Database** via RESTful calls.
- **Employee-Database**: Manages employee data in an H2 database, handles CRUD operations, defines the database schema, and performs logic such as employee creation, updates, and deletions.

---

## **Requirements**

- **JDK 17+**
- **Maven**
- **Spring Boot 2.7.x or later**
- **H2 Database**
- **Spring Security**
- **MapStruct for DTO transformations**
- **Docker (for containerization)**

---

## **Employee-Management**

### **Setup Instructions**

1. Clone the repository:
   ```bash
   git clone https://github.com/aichmonali94/employee-management-system.git
   cd Employee-Management-System/Employee-Management
   ```

2. Build the application using Maven:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access **Employee-Management** on:
    - `http://localhost:8001`

---

### **API Documentation**

**Employee-Management** exposes the following REST endpoints:

#### **1. Create Employee**
- **Endpoint**: `POST /employee-management/v1/employees`
- **Request Body**:
   ```json
   {
     "first_name": "John",
     "surname": "Doe"
   }
   ```
- **Request Header**:
   ```
   Role: ADMIN
   ```
- **Authorization**:
   ```
   AuthType: Basic Auth
   username: user
   password: user123
   ```  
- **Response**:
   ```json
   {
     "id": 1,
     "first_name": "John",
     "surname": "Doe",
     "role_id": 1
   }
   ```

#### **2. Get Employee by ID**
- **Endpoint**: `GET /employee-management/v1/employees/{employeeId}`
- **Request Header**:
 ```
 Role: ADMIN,USER,MANAGER
 ```
- **Authorization**:
 ```
 AuthType: Basic Auth
 username: user
 password: user123
 ```  
- **Response**:
   ```json
   {
     "id": 1,
     "first_name": "John",
     "surname": "Doe",
     "role_id": 1
   }
   ```

#### **3. Update Employee**
- **Endpoint**: `PUT /employee-management/v1/employees/{employeeId}`
- **Authorization**:
 ```
 AuthType: Basic Auth
 username: user
 password: user123
 ```  
- **Request Body**:
   ```json
   {
     "first_name": "Jane",
     "surname": "Doe"
   }
   ```
- **Request Header**:
   ```
   Role: USER,ADMIN,MANAGER
   ```
- **Response**:
   ```json
   {
     "id": 1,
     "first_name": "Jane",
     "surname": "Doe",
     "role_id": 2
   }
   ```

#### **4. Delete Employee**
- **Endpoint**: `DELETE /employee-management/v1/delete/employees/{employeeId}`
- **Request Header**:
 ```
 Role: ADMIN,USER,MANAGER
 ```
- **Authorization**:
 ```
 AuthType: Basic Auth
 username: user
 password: user123
 ```  
- **Response**:
   ```json
   {
     "message": "Employee deleted successfully"
   }
   ```

---

### **Security and Role-Based Access Control**

- **Spring Security** is configured to secure all REST APIs.
- **Role-Based Access Control** ensures that only **ADMIN** users can create and delete employees, while **USER** users can only view and update employee details.

---

## **Employee-Database**

### **Setup Instructions**

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repository-url
   cd Employee-Management-System/Employee-Database
   ```

2. Build the application using Maven:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access **Employee-Database** on:
    - `http://localhost:9001`

---

### **API Documentation**

**Employee-Database** exposes the following REST endpoints:

#### **1. Create Employee**
- **Endpoint**: `POST /database/v1/api/employees`
- **Request Body**:
   ```json
   {
     "name": "John Doe",
     "role_id": 1
   }
   ```
- **Response**:
   ```json
   {
     "id": 1,
     "name": "John Doe",
     "role_id": 1
   }
   ```

#### **2. Get Employee by ID**
- **Endpoint**: `GET /database/v1/api/employees/{employeeId}`
- **Response**:
   ```json
   {
     "id": 1,
     "name": "John Doe",
     "role_id": 1
   }
   ```

#### **3. Update Employee**
- **Endpoint**: `PUT /database/v1/api/employees/{employeeId}`
- **Request Body**:
   ```json
   {
     "name": "Jane Doe",
     "role_id": 2
   }
   ```
- **Response**:
   ```json
   {
     "id": 1,
     "name": "Jane Doe",
     "role_id": 2
   }
   ```

#### **4. Delete Employee**
- **Endpoint**: `DELETE /database/v1/api/employees/{employeeId}`
- **Response**:
   ```json
   {
     "message": "Employee deleted successfully"
   }
   ```

---

### **Database Schema**

The database schema includes the following tables:

1. **Employee Table**:

   | Field Name | Type  |
      |------------|-------|
   | id         | Long  |
   | firstname  | String |
   | surname    | String |
   | roleid     | Long  |

2. **Project Table**:

   | Field Name  | Type  |
      |-------------|-------|
   | id          | Long  |
   | name        | String |
   | employee_id | Long  |

3. **Role Table**:

   | Field Name | Type  |
      |------------|-------|
   | id         | Long  |
   | name       | String |

---
### **DDL**

```sql
INSERT INTO role (role_id, name) VALUES (1,'ADMIN');
INSERT INTO role (role_id, name) VALUES (2,'USER');

INSERT INTO employee (employee_id, name, role_id) VALUES (101, 'User One', 1);
INSERT INTO employee (employee_id, name, role_id) VALUES (102, 'User Two', 2);

INSERT INTO project (project_id, name, employee_id) VALUES (1001, 'Project A', 101);
INSERT INTO project (project_id, name, employee_id) VALUES (1002, 'Project B', 102);

-- Employee Table (Assuming primary key 'id' is auto-created)
CREATE INDEX idx_employee_roleid ON Employee(role_id);

-- Project Table (Assuming primary key 'id' is auto-created)
CREATE INDEX idx_project_employee_id ON Project(employee_id);

-- Role Table (Assuming primary key 'id' is auto-created)
CREATE INDEX idx_role_name ON Role(name);
```

---
### **Stored Procedure**

**Employee-Database** implements a stored procedure to handle the deletion of roles while ensuring the associated employees are deleted and their projects reassigned to a default employee.

```sql
CREATE PROCEDURE delete_role_and_reassign_projects(IN role_id INT)
BEGIN
   DECLARE default_employee_id INT DEFAULT 1;

   -- Reassign all projects linked to employees with the given role_id
   UPDATE project SET employee_id = default_employee_id WHERE employee_id IN (SELECT id FROM employee WHERE roleid = role_id);

   -- Delete all employees with the given role_id
   DELETE FROM employee WHERE roleid = role_id;

   -- Delete the role
   DELETE FROM role WHERE id = role_id;
END;
```

---

## **Testing**

We have written **JUnit tests** to cover the application logic, ensuring over **90% code coverage**. Tests are provided for:

- Validating incoming requests.
- Ensuring proper role-based access control.
- Testing the REST endpoints for employee creation, updates, and deletion.
- Verifying database operations in **Employee-Database**.

To run the tests, use the following Maven command:

```bash
mvn test
```

---

## **Docker Setup**

Both **Employee-Management** and **Employee-Database** are dockerized for easy deployment.

### **Employee-Management Dockerfile**

```Dockerfile
FROM openjdk:17-jdk-alpine
VOLUME /tmp
EXPOSE 8001
ADD target/employee-management.jar employee-management.jar
ENTRYPOINT ["java", "-jar", "/employee-management.jar"]
```

### **Employee-Database Dockerfile**

```Dockerfile
FROM openjdk:17-jdk-alpine
VOLUME /tmp
EXPOSE 9001
ADD target/employee-database.jar employee-database.jar
ENTRYPOINT ["java", "-jar", "/employee-database.jar"]
```

---

## **Usage Example**

1. **Create an Employee (Employee-Management)**:
    - Send a `POST` request to `http://localhost:8001/employee-management/v1/employees` with the following JSON body:
   ```json
   {
     "first_name": "John",
     "surname": "Doe"
   }
   ```
    - Set the request header `Role: ADMIN`.

2. **Get an Employee (Employee-Management)**:
    - Send a `GET` request to `http://localhost:8001/employee-management/v1/employees/{employeeId}`.

3. **Update an Employee (Employee-Management)**:
    - Send a `PUT` request to `http://localhost:8001/employee-management/v1/employees/{employeeId}` with the new employee details:
   ```json
   {
     "first_name": "Jane",
     "surname": "Doe"
   }
   ```

4. **Delete an Employee (Employee-Management)**:
    - Send a `DELETE` request to `http://localhost:8001/employee-management/v1/delete/employees/{employeeId}`.

---

## **Open API Swagger Documentation**

You can access the OpenAPI documentation for both applications via the following URL:

- **Swagger UI**: [http://localhost:9092/swagger-ui/index.html](http://localhost:9092/swagger-ui/index.html)
- **Log In Details**:
 ```
 username: user
 password: user123
 ```  

