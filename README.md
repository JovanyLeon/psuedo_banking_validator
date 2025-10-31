# psuedo_banking_validator
### School Project — Object-Oriented Mock Banking System  
**Technologies:** Java • JUnit • GitLab CI/CD • Mutation Testing  
**Date:** October 2024  

---

## 📘 Overview
**psuedo_banking_validator** is an academic project designed to simulate a simplified yet robust **banking system** built with **Object-Oriented Programming (OOP)** principles and **Test-Driven Development (TDD)**.

The project models the core functionality of a small bank, supporting **Checking**, **Savings**, and **Certificate of Deposit (CD)** accounts. It emphasizes clean architecture, modularity, and automated testing to ensure high code quality and maintainability.

---

## 🧩 Key Features

### 🔹 Object-Oriented Architecture
- **Encapsulation:** Each account type manages its own transaction logic internally, protecting sensitive data such as balances and interest rates.  
- **Inheritance & Polymorphism:** The `Account` superclass defines shared attributes and behaviors, while subclasses (`CheckingAccount`, `SavingsAccount`, and `CDAccount`) override methods to enforce account-specific rules (e.g., withdrawal limits, penalties, or interest application).  
- **Composition:** The `Bank` class aggregates multiple accounts, offering methods to create, retrieve, and manage account records.

---

### 🔹 Test-Driven Development (TDD)
- Developed entirely through a **TDD workflow**, where tests were written before implementing functionality.  
- Contains over **200+ automated JUnit test cases** ensuring full behavioral and functional coverage.  
- Implemented **mutation testing** to evaluate the strength and completeness of test cases.  
- Achieved **100% passing rate** with full validation of deposits, withdrawals, and transfers.

---

### 🔹 Continuous Integration & Deployment (CI/CD)
- Configured **GitLab CI/CD pipelines** to automatically compile, test, and validate new code changes.  
- Continuous integration ensures regression-free builds and consistent testing across environments.  
- Automated build and test reports improve visibility, reliability, and maintainability.

---

## 🏗️ Core Components

| Component | Description |
|------------|-------------|
| **Bank.java** | Central manager for account creation, lookup, and validation. |
| **Account.java** | Abstract base class defining shared structure and methods for all account types. |
| **CheckingAccount.java** | Implements overdraft protection and transaction limits. |
| **SavingsAccount.java** | Handles interest accrual and withdrawal restrictions. |
| **CDAccount.java** | Enforces maturity dates and early withdrawal penalties. |
| **TransactionValidator.java** | Ensures deposits, withdrawals, and transfers follow banking rules. |
| **BankTestSuite.java** | Master JUnit test suite executing all validation tests for the system. |

---

## 🧠 Learning Objectives
This project demonstrates:
- Applying **OOP principles** such as abstraction, encapsulation, inheritance, and polymorphism.  
- Practicing **Test-Driven Development (TDD)** to produce maintainable, bug-resistant code.  
- Using **Git** and **GitLab CI/CD** for automated testing and continuous integration.  
- Understanding **banking domain logic** through realistic transaction and account behavior.

---

## 🚀 Getting Started

### Prerequisites
- Java 17 or later  
- JUnit 5  
- GitLab or local Git environment  

### Running Tests
```bash
# Clone the repository
git clone https://gitlab.com/your-username/psuedo_banking_validator.git
cd psuedo_banking_validator

# Run all JUnit tests
./gradlew test
