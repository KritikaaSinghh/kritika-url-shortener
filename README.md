# 🔗 Kritika URL Shortener

A full-stack **URL Shortener** application built using **Spring Boot**, **Spring Security**, and **PostgreSQL** that allows users to create secure, shareable, and trackable short URLs with authentication and analytics.

---

# 📂 GitHub Repository

👉 https://github.com/KritikaaSinghh/kritika-url-shortener

---

# ✨ Features

- 🔗 URL Shortening
- 🔐 User Authentication & Authorization
- 🌐 Public and Private URLs
- ⏳ URL Expiration Support
- 📊 Analytics Dashboard
- 👆 Click Tracking
- 👤 User-specific URL Management
- 🌍 Public URL Listing
- 📱 Responsive UI with Thymeleaf
- 🗄 PostgreSQL Database Integration

---

# 🛠 Tech Stack

## Backend

- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Hibernate

## Database

- PostgreSQL

## Frontend

- Thymeleaf
- HTML5
- CSS3
- Bootstrap

## Tools

- Maven
- Docker
- Git
- GitHub

---

# 📊 Analytics Features

- Total URLs Created
- Total Clicks
- Top 5 Most Clicked URLs
- Public URL Listing
- User Dashboard

---

# 📁 Project Structure

```text
src
├── main
│   ├── java
│   │   ├── controller
│   │   ├── service
│   │   ├── repository
│   │   ├── entity
│   │   ├── dto
│   │   ├── config
│   │   ├── security
│   │   └── util
│   └── resources
│       ├── templates
│       ├── static
│       └── application.properties
└── test
```

---

# ▶️ Run Locally

## Clone Repository

```bash
git clone https://github.com/KritikaaSinghh/kritika-url-shortener.git
```

## Move to Project Directory

```bash
cd spring-boot-url-shortener
```

## Configure Database

Update the following file:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## Run Application

```bash
./mvnw spring-boot:run
```

## Open Browser

```text
http://localhost:8080
```

---

# 📸 Screenshots

## 🏠 Home Page

![Home Page](screenshots/home%20page.png)

---

## 🔑 Login Page

![Login Page](screenshots/Login%20page.png)

---

## 📝 Registration Page

![Registration Page](screenshots/Register%20page.png)

---

## 📄 URL Dashboard

![URL Dashboard](screenshots/URL%20page.png)

---

## 📈 Analytics Dashboard

![Analytics Dashboard](screenshots/analytics-dashboard.png)

---

## 🔒 Private URLs

![Private URLs](screenshots/Private-URls.png)

---

# 🚀 Future Enhancements

- QR Code Generation
- Custom Short URLs
- Password Protected URLs
- Email Verification
- Redis Cache Integration
- Rate Limiting
- URL Performance Analytics
- REST API Documentation using Swagger

---

# 👩‍💻 Author

## Kritika Singh

📧 **Email**

singhkritika8449@gmail.com

💼 **LinkedIn**

https://www.linkedin.com/in/kritika8070

💻 **GitHub**

https://github.com/KritikaaSinghh

---

# ⭐ Support

If you found this project useful, please consider giving it a ⭐ on GitHub.

Happy Coding! 🚀
