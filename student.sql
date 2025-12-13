-- ========================================================
--  Database Setup for MVC CRUD Application (JSP + Servlet)
-- ========================================================

-- 1️⃣ Create Database
DROP DATABASE IF EXISTS studentdb;
CREATE DATABASE studentdb;

-- Switch to database
\c studentdb;

-- 2️⃣ Create Table (Final Version)
DROP TABLE IF EXISTS students;

CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    mobile VARCHAR(15) NOT NULL
);

-- 3️⃣ Insert Optional Sample Data
INSERT INTO students (name, email, mobile) VALUES
('Suresh Agrawal', 'suresh.agrawal@netsecretsgroup.com', '9876543210'),
('Mukesh Jain', 'mukesh.jain@netsecretsgroup.com', '9123456789'),
('Prajakta Shende', 'prajakta.shende@netsecretsgroup.com', '9988776655');

-- 4️⃣ View Data Command
SELECT * FROM students;
