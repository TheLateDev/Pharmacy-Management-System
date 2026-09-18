# 💊 Pharmacy Management System (OOP Mini Project)

A console-based **Pharmacy & Medicine Inventory Management System** built with **Java** demonstrating core **Object-Oriented Programming (OOP)** principles, data structures, and custom exception handling.

---

## 📌 Features

- **🔐 User Authentication System**:
  - Secure Admin Registration & Login system.
  - Custom exception handling (`LoginException`) for validation errors (wrong credentials, duplicate user check).
- **📦 Medicine & Inventory Management**:
  - Add, update, view, and search medicines.
  - Track stock levels, pricing, and expiration details.
- **🧾 Billing & Invoicing System**:
  - Generate customer bills dynamically.
  - Calculate totals, discounts, and itemized receipts.
- **📂 Bill History & Records**:
  - Maintain historical transaction records using Java Collections.

---

## 🧠 OOP Concepts Demonstrated

| OOP Concept | Implementation in Project |
| :--- | :--- |
| **Encapsulation** | Private attributes in `Medicine`, `Bill`, and `Admin` classes with getter/setter methods. |
| **Inheritance** | Custom exception `LoginException` extends Java's built-in `Exception` class. |
| **Polymorphism** | Method overloading and custom string representations (`toStringLogin()`, `toStringRegister()`). |
| **Abstraction** | Modular separation of database operations, business logic, and UI display. |
| **Exception Handling** | Robust try-catch blocks and custom exception classes for handling invalid user input. |
| **Collections Framework** | Use of `Vector` / dynamic list structures to manage records and active sessions. |

---

## 📁 Project Structure

```text
OOP-MINI PROJECT/
└── src/
    ├── Pharmacy.java         # Main driver class and user authentication flow
    ├── Admin.java            # Admin dashboard and operation controller
    ├── Medicine.java         # Medicine entity (name, price, quantity, etc.)
    ├── MedicineDatabase.java # In-memory medicine storage and search operations
    ├── Bill.java             # Billing calculation and invoice generation
    ├── BillRecords.java      # Transaction history and receipt archives
    └── App.java              # Application launcher
