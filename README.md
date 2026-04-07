# 🚀 Project Base - Java Spring Boot Template

<p align="center">
  <b>Scalable • Layered Architecture • Secure • Production-ready</b>
</p>

---

## 📌 Overview

**Project Base** là một backend template được thiết kế theo chuẩn **enterprise-level**, sử dụng **Spring Boot 3** và kiến trúc phân lớp rõ ràng.

Mục tiêu:

* Tăng tốc phát triển backend
* Chuẩn hóa cấu trúc code
* Dễ scale và maintain trong production

---

## 🧱 Tech Stack

| Layer     | Technology                  |
| --------- | --------------------------- |
| Language  | Java 17                     |
| Framework | Spring Boot 3.2             |
| Security  | Spring Security + JWT       |
| Database  | MySQL                       |
| ORM       | Spring Data JPA (Hibernate) |
| Cache     | Redis                       |
| Mapping   | MapStruct                   |
| Docs      | Swagger (OpenAPI)           |
| Build     | Maven                       |
| Upload    | Cloudinary                  |

---

## 🏗️ Architecture

### 🔹 High-Level Architecture

```id="arch1"
Client → Controller → Service → Repository → Database
                ↓
            Security (JWT Filter)
                ↓
        Exception Handler (Global)
```

---

### 🔹 Package Structure

```id="arch2"
src/main/java/com/example/projectbase
│
├── controller/        # Entry point (REST API)
├── service/           # Business logic layer
├── repository/        # Data access layer
│
├── domain/
│   ├── entity/        # JPA Entities
│   ├── dto/           # Request/Response models
│   └── mapper/        # MapStruct mapping
│
├── security/          # JWT + Security config
├── config/            # App configurations
├── exception/         # Global error handling
├── base/              # Base response wrapper
├── constant/          # Constants / enums
├── util/              # Helper utilities
├── aop/               # Cross-cutting concerns
```

---

## 🔄 Request Lifecycle

```id="flow1"
HTTP Request
   ↓
Security Filter (JWT Validation)
   ↓
Controller
   ↓
Service Layer (Business Logic)
   ↓
Repository (Database)
   ↓
Response Mapping (DTO)
   ↓
Global Response Wrapper
   ↓
HTTP Response
```

---

## 🔐 Authentication & Authorization

### JWT Flow

```id="jwt1"
[Login]
   ↓
Generate JWT Token
   ↓
Client stores token
   ↓
Attach token in header:
Authorization: Bearer <token>
   ↓
JWT Filter validates token
   ↓
Access granted / denied
```

### Security Features

* Stateless authentication
* JWT token validation
* Role-based access control (RBAC - extendable)
* Password hashing (BCrypt)

---

## 📦 Standard API Response

Tất cả API tuân theo chuẩn response:

```json id="resp1"
{
  "status": "SUCCESS",
  "message": "Request successful",
  "data": {}
}
```

### Error Response

```json id="resp2"
{
  "status": "ERROR",
  "message": "Resource not found",
  "errorCode": "NOT_FOUND"
}
```

---

## ⚙️ Configuration

### application.yml (example)

```yaml id="cfg1"
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/project_base
    username: root
    password: password

  redis:
    host: localhost
    port: 6379

jwt:
  secret: your-secret-key
  expiration: 86400000
```

---

## 🚀 Getting Started

### 1. Clone repository

```bash id="cmd1"
git clone https://github.com/your-username/project-base.git
cd project-base
```

---

### 2. Run application

```bash id="cmd2"
./mvnw spring-boot:run
```

---

### 3. Build JAR

```bash id="cmd3"
mvn clean package
java -jar target/project-base-1.0.jar
```

---

## 📄 API Documentation

Swagger UI:

```id="swagger1"
http://localhost:8080/swagger-ui.html
```

---

## 🧪 Testing Strategy (Recommended)

* Unit Test: Service layer
* Integration Test: Controller + DB
* Security Test: JWT flow

---

## 📈 Scalability & Best Practices

* Layered architecture (Controller → Service → Repository)
* DTO pattern (no entity exposure)
* Centralized exception handling
* Stateless authentication (JWT)
* Redis caching support
* AOP for logging (extensible)

---

## 📜 License

This project is intended for learning, development, and portfolio purposes.
