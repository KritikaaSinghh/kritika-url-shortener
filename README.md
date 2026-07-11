# Kritika URL Shortener

A full-stack URL Shortener application built using Spring Boot and PostgreSQL that allows users to create secure, shareable, and trackable short URLs with authentication, analytics, and QR code generation.

## 🚀 Live Demo

https://kritika-url-shortener-production.up.railway.app

## 📂 GitHub Repository

https://github.com/KritikaaSinghh/kritika-url-shortener

---

## ✨ Features

- URL Shortening
- User Authentication & Authorization
- Public and Private URLs
- URL Expiration Support
- Analytics Dashboard
- Click Tracking
- QR Code Generation
- User-specific URL Management
- Public URL Listing
- Responsive UI with Thymeleaf
- PostgreSQL Database Integration
- Cloud Deployment on Railway
- Neon PostgreSQL Integration

---

## 🛠 Tech Stack

### Backend
- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Hibernate

### Database
- PostgreSQL
- Neon Database

### Frontend
- Thymeleaf
- HTML5
- CSS3
- Bootstrap

### Deployment & Tools
- Maven
- Docker
- Railway
- Git & GitHub

### Additional Libraries
- ZXing QR Generator

---

## 📊 Analytics Features

- Total URLs Created
- Total Clicks
- Top 5 Most Clicked URLs
- Public URL Listing
- User Dashboard

---

## 📱 QR Code Feature

Each shortened URL automatically generates a QR Code that can be scanned directly to open the original URL.

---

## ☁️ Production Deployment

- Application deployed on Railway
- PostgreSQL hosted on Neon Database
- GitHub integrated Continuous Deployment (CI/CD)

### Production URL

https://kritika-url-shortener-production.up.railway.app

---

## ▶️ Run Locally

### Clone Repository

```bash
git clone https://github.com/KritikaaSinghh/kritika-url-shortener.git
```

### Move to Project Directory

```bash
cd kritika-url-shortener
```

### Configure Database

Update:

```properties
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### Start PostgreSQL Using Docker

```bash
docker compose up -d
```

### Run Application

```bash
./mvnw spring-boot:run
```

### Open Browser

```text
http://localhost:8080
```

---

## 📸 Screenshots

- Home Page
- Login Page
- Registration Page
- URL Dashboard
- QR Code Generation
- Public URL Listing
- Analytics Dashboard

---

## 👩‍💻 Author

### Kritika Singh

📧 Email: singhkritika8449@gmail.com

💼 LinkedIn:
https://www.linkedin.com/in/kritika8070

💻 GitHub:
https://github.com/KritikaaSinghh

🌐 Live Project:
https://kritika-url-shortener-production.up.railway.app

---

## ⭐ If you found this project useful, please give it a star on GitHub.
