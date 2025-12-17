# Java CRUD MVC Playground

![Java](https://img.shields.io/badge/Java-17+-orange)
![Servlet](https://img.shields.io/badge/Jakarta%20Servlet-API-blue)
![JSP](https://img.shields.io/badge/JSP-View-yellow)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![Tomcat](https://img.shields.io/badge/Tomcat-11-yellow)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-purple)
![MVC](https://img.shields.io/badge/Architecture-MVC-success)
![License](https://img.shields.io/badge/License-Educational-lightgrey)

A clean and structured **Java MVC CRUD application** built using **Servlets, JSP, JDBC, and PostgreSQL**, following industry-standard design principles.

This repository acts as a **playground for evolving CRUD features step by step**, while keeping each phase stable, isolated, and maintainable.

> **Current Stable Release:** `v1.1-pagination`

---

## ✨ Features (Current Version – v1.1 Pagination)

- **Core CRUD Operations**
  - Add Student
  - View Student List
  - Edit Student
  - Delete Student

- **Pagination**
  - Page navigation (First / Previous / Next / Last)
  - Page size selection
  - Go-to-page option
  - Record range indicator

- **Validation**
  - Client-side validation using HTML5
  - Server-side validation in Servlet

- **UI / UX Enhancements**
  - Responsive layout using Bootstrap
  - Clean and consistent action buttons
  - User-friendly messages and alerts

- **Centralized Exception Handling**
  - Custom `DAOException`
  - Dedicated error page (`error.jsp`)

- **Clean MVC Separation**
  - Model → Data representation
  - DAO → Database access
  - Controller → Request handling
  - View → UI rendering

---

## 🛠️ Technology Stack

| Layer | Technology |
|------|-----------|
| Frontend (View) | JSP, HTML5, CSS3, Bootstrap 5 |
| UI Icons | Font Awesome |
| Controller | Jakarta Servlet API |
| Backend (Business Logic) | Java |
| Database Access | JDBC |
| Database | PostgreSQL |
| Application Server | Apache Tomcat 11 |
| Build Tool | Maven |
| Architecture Pattern | MVC (Model–View–Controller) |
| Version Control | Git & GitHub |

---

## 📁 Project Structure

```
java-crud-mvc-playground
│
├── src/main/java
│   └── com/nsgacademy/crudmvc
│       ├── model                # Domain & request models
│       │   ├── Student.java
│       │   ├── Pagination.java
│       │   └── StudentFilter.java
│       │
│       ├── dao                  # Data Access Layer (JDBC)
│       │   └── StudentDAO.java
│       │
│       ├── exception            # Centralized exception handling
│       │   └── DAOException.java
│       │
│       ├── utils                # Utility & Infrastructure classes
│       │   └── JDBCUtils.java
│       │
│       └── web                  # Controller layer (Servlets)
│           └── StudentServlet.java
│
├── src/main/webapp               # View Layer (JSP)
│   ├── student-list.jsp
│   ├── student-form.jsp
│   └── error.jsp
│
├── screenshots                   # Versioned UI screenshots
│   ├── v1.0
│   └── v1.1
│
├── pom.xml
└── README.md
```

---

## 🧠 Architecture Overview

This application follows the **classic MVC (Model–View–Controller) architecture**.

- **Model**
  - Represents application data (`Student`)

- **DAO Layer**
  - Contains all SQL queries
  - Handles database interaction only

- **Controller (Servlet)**
  - Routes HTTP requests
  - Performs server-side validation
  - Controls application flow

- **View (JSP)**
  - Responsible only for UI rendering
  - Contains no business logic

**Design Principles**
- No SQL in JSP
- No JDBC code in Servlet
- Clear separation of responsibilities
- Easy to extend and maintain

---

## 🗄️ Database Schema

```sql
CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    name   VARCHAR(50) NOT NULL,
    email  VARCHAR(100) NOT NULL,
    mobile VARCHAR(10) NOT NULL
);
```

---

## ⚙️ Configuration

### Database Configuration

Update database credentials in `JDBCUtils.java`:

```java
private static final String URL  = "jdbc:postgresql://localhost:5432/studentdb";
private static final String USER = "postgres";
private static final String PASS = "password";
```

---

## ▶️ How to Run the Project (Detailed)

### 1️⃣ Prerequisites
- JDK 17 or higher
- PostgreSQL installed and running
- Apache Tomcat 11
- IDE (IntelliJ IDEA / Eclipse)

---

### 2️⃣ Clone the Repository
```bash
git clone https://github.com/sureshagrawal/java-crud-mvc-playground.git
```

---

### 3️⃣ Create Database
```sql
CREATE DATABASE studentdb;
```

Create table using the schema provided above.

---

### 4️⃣ Import Project into IDE
- Open IDE
- Import as **Maven Project**
- Configure Apache Tomcat 11 in IDE

---

### 5️⃣ Run Application
- Deploy project on Tomcat
- Access application at:

```
http://localhost:8080/<context-root>/students
```

---

## 🏷️ Versioning Strategy (Planned Roadmap)

This project follows **incremental, tagged releases** to ensure stability.

| Version | Features |
|------|--------|
| v1.0-crud | Core CRUD (current stable) |
| v1.1-pagination | Pagination |
| v1.2-search | Search |
| v1.3-sorting | Sorting |
| v1.4-advanced-fields | Gender, DOB, Age Calculation |
| v1.5-file-upload | Student Photo Upload |
| v1.6-import | Import (CSV / Excel) |
| v1.7-export | Export (CSV, Excel, PDF, HTML) |
| v1.8-auth | Login System & Roles |
| v1.9-audit | Audit Logs |
| v2.0-deployment | Deployment Configuration |
| v3.0-hibernate | Hibernate Migration |
| v4.0-jpa | JPA Migration |

Each version will be **independently stable and tagged**.

---

## 🔮 Future Architecture Direction

This project is intentionally designed to align with modern Spring-based applications.

Planned transitions:
- Servlet MVC → Spring MVC
- JDBC → Spring Data JPA
- JSP → REST APIs + Frontend (React / Thymeleaf)
- Manual pagination → `Pageable`
- Manual filters → Specification / Criteria-based filtering

---

## ⚠️ Known Limitations (Intentional)

- No Service layer (kept simple for Servlet MVC)
- No ORM in early versions (JDBC used for clarity)
- No REST APIs yet
- Authentication not included in early versions

---

## 📸 Screenshots

### v1.0 – Core CRUD Functionality

| Feature                | Screenshot                                                   |
|------------------------|--------------------------------------------------------------|
| Student List           | ![List](screenshots/v1.0/list.png)                           |
| Delete Confirmation    | ![Delete](screenshots/v1.0/delete-confirm.png)               |
| Delete Success Message | ![Success](screenshots/v1.0/delete-success-message.png)      |
| Add Student            | ![Add](screenshots/v1.0/add-student.png)                     |
| Add Success Message    | ![Success](screenshots/v1.0/add-success-message.png)         |
| Edit Student           | ![Edit](screenshots/v1.0/edit-student.png)                   |
| Update Success Message | ![Success](screenshots/v1.0/update-success-message.png)      |
| Validation – Name      | ![Name Validation](screenshots/v1.0/validation-name.png)     |
| Validation – Mobile    | ![Mobile Validation](screenshots/v1.0/validation-mobile.png) |
| Error Page             | ![Error](screenshots/v1.0/error-page.png)                    |

---

### v1.1 – Pagination Enhancements

| Feature | Screenshot |
|-------|------------|
| Pagination Toolbar | ![Toolbar](screenshots/v1.1/pagination-toolbar.png) |
| Pagination Navigation | ![Pagination](screenshots/v1.1/pagination-navigation.png) |

---

## 🎯 Purpose of This Project

- Learn MVC fundamentals deeply
- Build CRUD applications the right way
- Understand incremental feature evolution
- Prepare an interview-ready project
- Serve as a teaching and learning reference
- Act as a base for ORM migration (Hibernate / JPA)

---

## 🤝 Contributing

Contributions are welcome for:
- Code improvements
- Refactoring
- Documentation
- Feature suggestions

Please create a feature branch and submit a pull request.

---

## 🧹 Git Hygiene

The repository includes a proper `.gitignore` to exclude:
- IDE configuration files
- Build artifacts
- Temporary files and logs

---

## 📄 License

This project is open-source and intended for **educational purposes**.

---

## 👤 Author

**Suresh Agrawal**  
Java Full Stack Developer & Trainer  

---

## 🙏 Acknowledgement

- Java open-source community
- Apache Tomcat & PostgreSQL teams
- Bootstrap & Font Awesome
- Students and developers who inspire continuous learning
