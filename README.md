# Low-Level Design (LLD) & Spring API Architecture

![Java](https://img.shields.io/badge/Language-Java-orange.svg)
![Spring](https://img.shields.io/badge/Framework-Spring%20Boot-brightgreen.svg)

A structured collection of Java implementations for **Low-Level Design (LLD)** and **RESTful API Architecture**. 
Master the art of software architecture with this Java-based guide to Low-Level Design (LLD). This repository bridges the gap between pure OOP theory and production-grade applications. It covers everything from foundational SOLID principles and Design Patterns to modern REST API Design with Spring Boot, focusing on clean layering, DTO patterns, and decoupled business logic. This repository is designed to help developers write clean, maintainable code following industry best practices like SOLID and Design Patterns.

## 🚀 Key Modules

### 🌐 REST API Design (Spring Boot)
How to apply LLD principles to web services:
- **Layered Architecture:** Controller → Service → Repository.
- **Data Transfer Objects (DTOs):** Decoupling internal entities from public API responses.
- **Exception Handling:** Centralized `@ControllerAdvice` for consistent error responses.
- **Request Validation:** Using JSR-380 (Bean Validation) to ensure data integrity.
- **Pagination & Sorting:** Efficiently handling large datasets via Spring Data JPA.

### 🧱 SOLID Design Principles
Clear Java examples for writing extensible code:
- **S**ingle Responsibility: One class, one job.
- **O**pen/Closed: Extend functionality without modifying existing code.
- **L**iskov Substitution: Child classes must be replaceable with parent classes.
- **I**nterface Segregation: No "fat" interfaces; keep them specific.
- **D**ependency Inversion: Depend on abstractions, not concretions.

### 🎨 Design Patterns
Practical implementations of GOF patterns:
- **Creational:** Singleton, Factory, Builder.
- **Structural:** Adapter, Decorator, Facade, Proxy.
- **Behavioral:** Strategy, Observer, State, Command.

## 💻 Tech Stack
- **Language:** Java 11+
- **Frameworks:** Spring Boot, Spring Data JPA, Spring Web.
- **Concepts:** OOP, Multi-threading, Design Patterns, REST.

## 🛠️ Installation & Usage

1. **Clone the repo:**
   ```bash
   git clone [https://github.com/svetanis/low-level-design.git](https://github.com/svetanis/low-level-design.git)
Open in IDE: Import as a Maven or Gradle project in IntelliJ IDEA or Eclipse.

Explore: Each folder contains standalone examples with Main entry points or Spring Boot starters.

📄 License
MIT License.