# 🔗 Kritika URL Shortener

A full-stack URL Shortener application built using Spring Boot and PostgreSQL.

## 🚀 Features

- URL Shortening
- User Authentication & Authorization
- Private URLs
- URL Expiration
- Analytics Dashboard
- Click Tracking
- QR Code Generation
- PostgreSQL Database Integration
- Docker Support

## 🛠 Tech Stack

- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- PostgreSQL
- Thymeleaf
- Maven
- Docker
- ZXing QR Generator

## 📊 Analytics Features

- Total URLs Created
- Total Clicks
- Top 5 Most Clicked URLs

## 📱 QR Code Feature

Each shortened URL automatically generates a QR Code which can be scanned directly to open the original URL.

## ▶️ Run Locally

Clone the repository:

```bash
git clone https://github.com/KritikaaSinghh/kritika-url-shortener.git
```

Move into project directory:

```bash
cd kritika-url-shortener
```

Run PostgreSQL:

```bash
docker compose up -d
```

Start application:

```bash
./mvnw spring-boot:run
```

Open in browser:

```text
http://localhost:8080
```

## 📸 Project Screenshots

- Home Page
- Login & Registration
- URL Analytics Dashboard
- QR Code Generation
- Public URL Listing

## 👩‍💻 Author

Kritika Singh

GitHub:
https://github.com/KritikaaSinghh
