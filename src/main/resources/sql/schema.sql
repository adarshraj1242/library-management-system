-- Horizon Library Schema (H2 Embedded Database Version)
-- This schema works with both H2 and MySQL

CREATE TABLE IF NOT EXISTS Admins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

MERGE INTO Admins KEY(id) VALUES (1, 'admin', 'admin123');

CREATE TABLE IF NOT EXISTS Students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    dob VARCHAR(50),
    password VARCHAR(255) NOT NULL,
    fines DECIMAL(10, 2) DEFAULT 0.00,
    profile_photo VARCHAR(500),
    bio TEXT,
    membership_type VARCHAR(50) DEFAULT 'Basic',
    membership_expiry DATE
);

MERGE INTO Students KEY(id) VALUES (1, 'John Doe', 'john@example.com', '9876543210', '1999', 'password123', 0.00, NULL, NULL, 'Premium', '2025-12-31');

CREATE TABLE IF NOT EXISTS Books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    status VARCHAR(20) DEFAULT 'Available',
    total_copies INT DEFAULT 1,
    available_copies INT DEFAULT 1,
    pdf_path VARCHAR(500),
    cover_path VARCHAR(500),
    publication_year INT,
    description TEXT,
    view_count INT DEFAULT 0,
    added_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Issues (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    book_id INT,
    issue_type VARCHAR(50) DEFAULT 'Check-Out',
    issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date TIMESTAMP NULL,
    return_date TIMESTAMP NULL,
    status VARCHAR(20) DEFAULT 'Issued',
    fine_amount DECIMAL(10, 2) DEFAULT 0.00,
    FOREIGN KEY (student_id) REFERENCES Students(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES Books(id) ON DELETE CASCADE
);
