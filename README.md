# Gourme — Food Delivery Platform

A full-stack food delivery application. The backend is a Spring Boot REST API
(auth, menus, orders, delivery tracking) consumed by a custom-built frontend.

## Architecture
[Frontend (your stack — React/vanilla JS?)]  →  REST API (JSON)  →  Spring Boot  →  MySQL

## Features
- User registration & login with secure authentication
- Restaurant & menu browsing
- Order placement, processing, and delivery status tracking
- Fully custom frontend consuming the REST API

## Tech Stack
- **Backend:** Java, Spring Boot, Spring Data JPA, Spring Security, MySQL
- **Frontend:** [React.js / your actual stack]
- **API:** REST (JSON over HTTP)

## Getting Started

**Backend:**
```bash
cd backend   # (or whatever your folder is named)
./mvnw spring-boot:run
