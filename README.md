# C6.-Java-Spark-for-Web-Apps

# Challenge 6: Collectible Store API

This repository contains the backend API service for the Collectible Store project, as part of the Infosys Backend Development Bootcamp.

This service is built with **Java** and the **Spark** micro-framework. It provides a RESTful API for managing users and (eventually) collectible items.

## How to Run the Project

### Prerequisites

* Java 17 (or newer)
* Apache Maven

### Steps to Run

1.  **Clone the repository:**
    ```sh
    git clone [YOUR-REPOSITORY-URL-HERE]
    cd CollectorZone 
    ```

2.  **Build the project using Maven:**
    This command will download dependencies and create a single, runnable "uber-jar" in the `target/` directory.
    ```sh
    mvn clean package
    ```

3.  **Run the application:**
    ```sh
    java -jar target/CollectorZone-1.0-SNAPSHOT.jar
    ```

4.  The server will start on `http://localhost:4567`.

---

## API Documentation (Key Decisions)

This section documents the API endpoints as required for the project. The decision was made to use an in-memory `HashMap` to store data for this stage, allowing for rapid API-first development before implementing a persistent database.

### Base URL

* `http://localhost:4567`

### User Endpoints

The API provides the following endpoints for managing users:

#### 1. Get All Users

* **Endpoint:** `GET /users`
* **Description:** Retrieves a list of all users in the system.
* **Bash (cURL) Example:**
    ```sh
    curl http://localhost:4567/users
    ```
* **Success Response (200 OK):**
    ```json
    [
      {
        "id": "1",
        "username": "ramon_collector",
        "email": "ramon@example.com"
      }
    ]
    ```

#### 2. Get Single User

* **Endpoint:** `GET /users/:id`
* **Description:** Retrieves a single user by their unique ID.
* **Bash (cURL) Example:**
    ```sh
    curl http://localhost:4567/users/1
    ```
* **Success Response (200 OK):**
    ```json
    {
      "id": "1",
      "username": "ramon_collector",
      "email": "ramon@example.com"
    }
    ```
* **Error Response (404 Not Found):**
    ```json
    {
      "error": "User not found."
    }
    ```

#### 3. Add a User

* **Endpoint:** `POST /users/:id`
* **Description:** Adds a new user with a specified ID.
* **Bash (cURL) Example:**
    ```sh
    curl -X POST http://localhost:4567/users/1 \
         -H "Content-Type: application/json" \
         -d '{"username":"ramon_collector","email":"ramon@example.com"}'
    ```
* **Success Response (201 Created):**
    ```json
    {
      "id": "1",
      "username": "ramon_collector",
      "email": "ramon@example.com"
    }
    ```
* **Error Response (409 Conflict):**
    ```json
    {
      "error": "User with this ID already exists"
    }
    ```

#### 4. Edit a User

* **Endpoint:** `PUT /users/:id`
* **Description:** Updates an existing user's information.
* **Bash (cURL) Example:**
    ```sh
    curl -X PUT http://localhost:4567/users/1 \
         -H "Content-Type: application/json" \
         -d '{"username":"ramon_pro","email":"ramon.new@example.com"}'
    ```
* **Success Response (200 OK):**
    ```json
    {
      "id": "1",
      "username": "ramon_pro",
      "email": "ramon.new@example.com"
    }
    ```
* **Error Response (404 Not Found):**
    ```json
    {
      "error": "User not found, cannot update."
    }
    ```

#### 5. Check if User Exists

* **Endpoint:** `OPTIONS /users/:id`
* **Description:** Checks for the existence of a user by ID.
* **Bash (cURL) Example (use `-v` for verbose to see status):**
    ```sh
    curl -v -X OPTIONS http://localhost:4567/users/1
    ```
* **Success Response:** Returns an HTTP `200 OK` status with the text "User exists".
* **Error Response:** Returns an HTTP `404 Not Found` status with the text "User not found".

#### 6. Delete a User

* **Endpoint:** `DELETE /users/:id`
* **Description:** Deletes a user by their ID.
* **Bash (cURL) Example:**
    ```sh
    curl -X DELETE http://localhost:4567/users/1
    ```
* **Success Response (200 OK):**
    ```json
    {
      "message": "User deleted successfully"
    }
    ```
* **Error Response (404 Not Found):**
    ```json
    {
      "error": "User not found"
    }
    ```