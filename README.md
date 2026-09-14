# 🔐 JWT Authentication Demo

A basic **Spring Boot JWT Authentication** project demonstrating how JSON Web Tokens (JWT) can be used to authenticate and authorize API requests.

The project contains two main endpoints:

* `POST /login` — Authenticates the user and generates a JWT token.
* `GET /api/hello` — A protected endpoint that can be accessed only by providing a valid JWT token.

---

## 🏗️ System Architecture

![Architecture](/jwt-auth/src/main/resources/static/images/System_Archittecture.png)

---

## 🏗️ Authentication Flow
![Authentication Flow](/jwt-auth/src/main/resources/static/images/Authentication_Flow.png)

---

## ✨ Features

* 🔐 JWT-based authentication
* 🎟️ JWT token generation
* 🛡️ Protected REST endpoint
* 🔑 Bearer token authentication
* 🚫 Unauthorized request handling
* 🌱 Spring Security integration
* 📚 Simple implementation for learning JWT

---

## 🛠️ Tech Stack

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square\&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?style=flat-square\&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring_Security-6.x-green?style=flat-square\&logo=springsecurity)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=flat-square\&logo=apachemaven)
![JWT](https://img.shields.io/badge/JWT-Authentication-black?style=flat-square)

---

## 📂 Project Structure

```text
\---src/main/java/com
    \---techcoder
        \---jwt_auth
            |   JwtAuthApplication.java
            |   
            +---config
            |       AppConfig.java
            |       AppRunner.java
            |       AuthEntryPoint.java
            |       SecurityConfig.java
            |       
            +---controller
            |       DemoController.java
            |       LoginController.java
            |       
            +---model
            |       LoginRequest.java
            |       LoginResponse.java
            |       Role.java
            |       UserApp.java
            |       
            +---repo
            |       RoleRepo.java
            |       UserRepo.java
            |       
            +---security
            |       JwtAuthFilter.java
            |       
            +---service
            |       LoginService.java
            |       UserService.java
            |       
            \---utils
                    JwtUtils.java
```

---

# 🔑 API Endpoints

## 1. Login

### `POST /login`

Authenticates the user and generates a JWT token.

### Request

```http
POST /login
Content-Type: application/json
```

Example request body:

```json
{
  "username": "user",
  "password": "password"
}
```

### Response

A successful authentication returns a JWT token.

```json
{
  "jwt": "eyJhbGciOiJIUzI1NiJ9..."
}
```


---

# 🛡️ 2. Protected Hello API

### `GET /api/hello`

This endpoint is protected using JWT authentication.

A valid JWT must be included in the request.

### Request

```http
GET /api/hello
Authorization: Bearer <JWT_TOKEN>
```

### Example

```bash
curl --location 'http://localhost:8080/api/hello' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...'
```

### Successful Response

```text
Server says hello to : user
```

### Without Token

```text
{
    "error": "Unauthorized - AuthEntryPoint"
}
```

---

# 🔄 JWT Request Flow

### Step 1 — Login

The client sends credentials to:

```text
POST /login
```

```json
{
  "username": "user",
  "password": "password"
}
```

### Step 2 — Token Generation

After successful authentication, the server generates a JWT:

```text
Client
   │
   │ username + password
   ▼
/login
   │
   │ Authentication successful
   ▼
JWT Token
```

### Step 3 — Send Token

The client sends the JWT with subsequent requests:

```http
Authorization: Bearer <JWT_TOKEN>
```

### Step 4 — Validate Token

Spring Security validates the JWT.

```text
Request
   │
   ▼
JWT Filter
   │
   ├── Invalid / Missing ──► 401 Unauthorized
   │
   └── Valid
        │
        ▼
   /api/hello
        │
        ▼
     Response
```

---

# 🧩 JWT Structure

A JWT consists of three parts:

```text
Header.Payload.Signature
```

Example:

```text
xxxxx.yyyyy.zzzzz
```

### Header

Contains information about the token, such as the signing algorithm.

### Payload

Contains claims associated with the authenticated user.

Example:

```json
{
  "sub": "user",
  "iat": 1690000000,
  "exp": 1690003600
}
```

### Signature

The signature is used to verify that the token has not been modified.

---

# ⚙️ Configuration

JWT-related configuration can be maintained in:

```text
src/main/resources/application.properties
```

Example:

```properties
server.port=8080

jwt.secret=your-secret-key
jwt.expiration=3600000
```

---

# 🚀 Getting Started

## Prerequisites

Make sure you have installed:

* Java 21+
* Maven
* Git

## Clone the Repository

```bash
git clone <repository-url>
cd jwt-auth
```

## Build the Project

```bash
mvn clean install
```

## Run the Application

```bash
mvn spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

# 🧪 Testing the APIs

You can test the APIs using:

* Postman
* cURL
* IntelliJ HTTP Client
* Any REST API client

### 1️⃣ Login

```http
POST http://localhost:8080/login
```

Provide valid credentials and copy the returned JWT token.

### 2️⃣ Access Protected API

```http
GET http://localhost:8080/api/hello
```

Add:

```http
Authorization: Bearer <JWT_TOKEN>
```

If the token is valid, the request will be authorized.

---

# 📌 Key Concepts Demonstrated

This project demonstrates the basic concepts of:

* Authentication vs Authorization
* JWT token generation
* JWT token validation
* Bearer authentication
* Spring Security
* Security filters
* Protected REST APIs
* Stateless authentication

---

# ⚠️ Security Note

This project is intended as a **learning/demo project** to understand JWT authentication.

For production applications, consider implementing:

* Secure password hashing
* Refresh tokens
* Token expiration and rotation
* HTTPS
* Secure secret/key management
* Role-based authorization
* Proper exception handling
* Token revocation strategies
* Secure storage of JWTs on the client

---

## 👨‍💻 Author

**Vansh Gala**

* 💼 [LinkedIn](https://linkedin.com/in/vansh-gala)
* 🐙 [GitHub](https://github.com/Vansh-829)
* 📧 [Email](mailto:vansh.gala2024@gmail.com)

---

⭐ If this project helped you understand JWT authentication, consider giving the repository a star.
