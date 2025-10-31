# 🏦 psuedo_banking_validator

### School Project — Object-Oriented Mock Banking System  
**Technologies:** Java • JUnit • GitLab CI/CD • Mutation Testing  
**Date:** October 2024  

---

## 📘 Overview
**psuedo_banking_validator** is an academic project designed to simulate a simplified yet robust **banking system** built using **Object-Oriented Programming (OOP)** principles and **Test-Driven Development (TDD)**.  

The project models the core functionality of a small bank, supporting **Checking**, **Savings**, and **Certificate of Deposit (CD)** accounts. It emphasizes modular design, extensibility, and test reliability through a fully automated testing framework.

---

## 🧩 Key Features

### 🔹 Object-Oriented Architecture
- **Encapsulation:** Account-specific logic is contained within dedicated classes, preventing unauthorized data access and preserving consistency.  
- **Inheritance & Polymorphism:** A shared `Account` superclass defines the structure and behavior for all account types, while subclasses (`Checkings`, `Savings`, and `CDAccount`) implement their own rules such as withdrawal limits, interest rates, or maturity constraints.  
- **Composition:** The `Bank` and `MasterControl` classes manage collections of accounts and coordinate system-wide operations such as transactions and command processing.  

---

### 🔹 Test-Driven Development (TDD)
- The system was developed **entirely through TDD**, ensuring every feature began as a test case before being implemented.  
- Includes over **200 automated JUnit test cases**, verifying deposits, withdrawals, transfers, and interest logic.  
- Utilized **mutation testing** to measure test robustness and detect weak assertions.  
- Achieved **100% passing tests** and full functional coverage across all modules.  

---

### 🔹 Continuous Integration (CI/CD)
- Integrated with **GitLab CI/CD pipelines** for continuous validation of all code submissions.  
- Automatic builds, test execution, and mutation analysis on every commit ensured reliable, regression-free updates.  
- Maintained version control discipline using **Git branches and merge requests** for feature isolation.

---

## 🗂️ Project Structure

| File | Description |
|------|-------------|
| **Account.java** | Abstract base class for all account types. Defines shared fields (e.g., balance, account ID) and methods for deposits and withdrawals. |
| **Bank.java** | Manages all bank accounts, enabling account creation, lookup, and transaction routing. Central hub for system operations. |
| **CDAccount.java** | Represents Certificate of Deposit accounts, enforcing maturity periods and early withdrawal penalties. |
| **Checkings.java** | Implements logic for checking accounts, including overdraft and transfer functionality. |
| **Savings.java** | Models savings accounts with interest accumulation and limited withdrawals. |
| **CommandProcessor.java** | Generic interface defining the structure for all command processors (deposit, withdraw, transfer, etc.). |
| **CommandStorage.java** | Stores and manages the sequence of commands issued during a session for auditing and validation. |
| **CommandValidator.java** | Abstract class providing a foundation for validating user commands before execution. |
| **CreateCommandProcessor.java / CreateCommandValidator.java** | Handle creation of new accounts and validation of account parameters. |
| **DepositCommandProcessor.java / DepositCommandValidator.java** | Implement and validate deposit transactions, ensuring positive values and valid targets. |
| **WithdrawCommandProcessor.java / WithdrawCommandValidator.java** | Handle withdrawal requests while ensuring sufficient funds and account eligibility. |
| **TransferCommandProcessor.java / TransferCommandValidator.java** | Enable fund transfers between accounts, with cross-validation for both source and destination. |
| **PassCommandProcessor.java / PassCommandValidator.java** | Manage time-based actions such as interest accrual and CD maturation. |
| **MasterControl.java** | The main orchestrator for the system—reads input commands, validates them, and delegates execution to the proper processors. |

---

## 🧠 Learning Objectives
This project demonstrates:
- Applying **OOP design principles** — abstraction, encapsulation, inheritance, and polymorphism — to manage system complexity.  
- Building production-quality software through **Test-Driven Development (TDD)**.  
- Using **Git** and **GitLab CI/CD** to enforce code reliability and ensure reproducible builds.  
- Simulating real-world banking logic with transaction validation, interest computation, and account management.  

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
./gradlew test Run all JUnit tests
./gradlew test
