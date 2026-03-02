# 🏦 SecureBank – Console-Based Banking Management System

## 📌 Project Overview

SecureBank is a console-based Banking Management System built using:

- Java
- JDBC
- MySQL
- Maven

This project simulates real-world banking operations such as:

- User registration
- Account creation
- Deposits
- Withdrawals
- Money transfers
- Balance checking
- Transaction history management

The primary goal of this project is to gain strong practical understanding of:

- JDBC
- SQL
- Database relationships
- Transaction management (commit & rollback)
- DAO architecture
- Data consistency in financial systems

---

## 🎯 Features

### 1️⃣ Create User

- Register new users
- Email must be unique
- `created_at` timestamp stored automatically

### 2️⃣ Create Account

- Create bank account for an existing user
- Auto-generate unique account number (e.g., SB10001)
- Initial balance set to 0.00

### 3️⃣ Deposit Money

- Deposit money into account
- Update balance
- Record transaction

### 4️⃣ Withdraw Money

- Withdraw money from account
- Validate sufficient balance
- Record transaction

### 5️⃣ Transfer Money

- Transfer funds between two accounts
- Uses JDBC transaction handling
- Ensures atomicity using:
  - setAutoCommit(false)
  - commit()
  - rollback()

### 6️⃣ Check Balance

- Display current balance of an account

### 7️⃣ View Transaction History

- Display all transactions of an account
- Ordered by latest transaction first

---

## 🛠 Tech Stack

- Language: Java 17
- Build Tool: Maven
- Database: MySQL
- Database Connectivity: JDBC
- Architecture Pattern: DAO Pattern

---

## 🗂 Project Structure

```
src.main.java.com.harshil.bank
│
├── model
│ ├── User.java
│ ├── Account.java
│ └── Transaction.java
│
├── dao
│ ├── UserDAO.java
│ ├── AccountDAO.java
│ └── TransactionDAO.java
│
├── service
│ └── BankService.java
│
├── util
│ └── DBConnection.java
│
└── App.java
```

---

## 🗄 Database Schema

### users table

```sql
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### accounts table

```sql
CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    account_number VARCHAR(20) UNIQUE,
    balance DECIMAL(12,2) DEFAULT 0.00,
    account_type VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```

### transactions table

```sql
CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT,
    type VARCHAR(20),
    amount DECIMAL(12,2),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);
```

---

## 🔄 Transaction Handling (Transfer Feature)

The transfer feature uses manual transaction control:

```java
connection.setAutoCommit(false);

try {
    // deduct from sender
    // add to receiver
    // insert transaction records

    connection.commit();
} catch (Exception e) {
    connection.rollback();
}
```

This ensures:

- Atomicity
- Data consistency
- No partial updates
- Safe financial operations

---

## ▶️ How to Run the Project

### 1️⃣ Create Database

```sql
CREATE DATABASE securebank;
USE securebank;
```

### 2️⃣ Update DB Credentials

Inside `DBConnection.java`:

```java
String url = "jdbc:mysql://localhost:3306/securebank";
String username = "root";
String password = "your_password";
```

### 3️⃣ Run Using Maven

```bash
mvn clean compile
mvn exec:java
```

---

## 💡 Learning Outcomes

From this project, I learned:

- Practical JDBC usage
- Writing SQL queries with constraints
- Handling database transactions
- Implementing DAO pattern
- Maintaining financial data consistency
- Managing database relationships using foreign keys

---

## 📈 Future Improvements

- Add authentication system
- Add account PIN verification
- Add monthly interest calculation
- Convert into REST API using Spring Boot
- Add connection pooling (HikariCP)
- Add logging framework

---

## 👨‍💻 Author

Harshil Chaurasiya  
B.Sc. IT Graduate  
Backend Development Enthusiast
