# 💳 Banking Management System - Spring Boot Application

A RESTful API built using **Spring Boot** that simulates core banking operations such as customer management, account handling, and fund transfers. 
This project demonstrates clean architecture, exception handling, validation, and use of service and repository layers with JPA.

---

## 🚀 Features

- ✅ Add, update, and delete **Customers**
- ✅ Create and manage **Accounts** for customers
- ✅ View **all accounts of a customer**
- ✅ Transfer funds:
  - 💸 Between a customer’s own accounts
  - 💸 To another customer’s account
- ✅ Filter accounts by **balance** or **account type**
- ✅ Fetch customers by **name** or **ID**
- ✅ Exception handling for all operations

---

## 🧰 Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **H2 / MySQL (configurable)**
- **Jakarta Validation**
- **Lombok (optional)**
- **SLF4J Logging**

---

## 📦 Project Structure

```
com.bed
│
├── controller          # REST controllers
├── service             # Business logic
├── model               # Entity classes
├── repositary          # JPA repositories
├── Exceptions          # Custom exception classes
├── dto                 # DTOs (e.g., CustomerDetails, Amount)
└── advice              # Global exception handler (MyControllerAdvice)
```


---

## ⚠️ Custom Exceptions

This application uses meaningful custom exceptions to handle runtime issues like:

- `ResourseNotFoundException`
- `InvalidCredintialsException`
- `SameAccountException`
- `SingleAccountException`
- `InsufficientBalance`
- `NotBelongsException`

All exceptions are centralized using a global handler `MyControllerAdvice`.

---

## 📬 Sample API Endpoints

| Method | Endpoint                                      | Description                            |
|--------|-----------------------------------------------|----------------------------------------|
| POST   | `/customer`                                   | Add new customer                       |
| GET    | `/customer`                                   | Get all customers                      |
| GET    | `/customer/{cid}`                             | Get customer by ID                     |
| PUT    | `/customer/addaccount/{cid}`                  | Add account to customer                |
| DELETE | `/customer/{cid}`                             | Delete customer                        |
| PUT    | `/customer/self-transfer/{cid}/{from}/{to}`   | Transfer funds between own accounts    |
| PUT    | `/customer/funds-transfer/{cid1}/{cid2}/...`  | Transfer funds to other customer       |
| GET    | `/accounts/bytype/{type}`                     | Get accounts by account type           |
| GET    | `/accounts/bybalance/{cid}/{min}`             | Get accounts by balance threshold      |

---

## 🧪 Validation & Rules

- A customer **cannot have multiple accounts of the same type**
- A customer must have **at least one account**
- Transfers:
  - Cannot be made **to/from the same account**
  - Cannot be made **if funds are insufficient**
  - Cannot be made **to self using same account IDs**

---


