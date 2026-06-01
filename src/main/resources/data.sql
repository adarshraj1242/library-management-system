-- Test Data for Horizon Library
-- This file is executed after tables are created by Hibernate

INSERT IGNORE INTO admins (id, username, password) VALUES (1, 'admin', 'admin123');

INSERT IGNORE INTO students (id, name, email, phone, dob, password, fines, profile_photo, bio, membership_type, membership_expiry) 
VALUES (1, 'John Doe', 'john@example.com', '9876543210', '1999', 'password123', 0.00, NULL, NULL, 'Premium', '2025-12-31');

INSERT IGNORE INTO books (id, title, author, category, status, total_copies, available_copies, pdf_path, cover_path, publication_year, description, view_count, added_date) 
VALUES 
(1, 'The Great Gatsby', 'F. Scott Fitzgerald', 'Classic', 'Available', 5, 3, NULL, NULL, 1925, 'A classic American novel', 10, CURRENT_TIMESTAMP),
(2, 'To Kill a Mockingbird', 'Harper Lee', 'Classic', 'Available', 4, 2, NULL, NULL, 1960, 'Southern Gothic novel', 8, CURRENT_TIMESTAMP),
(3, 'Pride and Prejudice', 'Jane Austen', 'Romance', 'Available', 3, 1, NULL, NULL, 1813, 'Romantic fiction', 15, CURRENT_TIMESTAMP),
(4, '1984', 'George Orwell', 'Dystopian', 'Checked-Out', 2, 0, NULL, NULL, 1949, 'Political fiction', 25, CURRENT_TIMESTAMP),
(5, 'The Catcher in the Rye', 'J.D. Salinger', 'Classic', 'Available', 3, 3, NULL, NULL, 1951, 'Coming of age novel', 5, CURRENT_TIMESTAMP),
(6, 'Brave New World', 'Aldous Huxley', 'Dystopian', 'Available', 4, 4, NULL, NULL, 1932, 'Science fiction', 12, CURRENT_TIMESTAMP);
