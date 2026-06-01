# API Documentation

## Overview

The Library Management System provides a complete REST API for managing books, students, issues, and authentication. All endpoints use JSON for request/response bodies and have CORS enabled for cross-origin access.

## Base URL
```
http://localhost:8081
```

## Authentication

The API supports basic authentication for two roles:
- **Admin:** Administrative user with full system access
- **Patron/Student:** Regular user with limited access

### Login Endpoint

**Endpoint:** `POST /api/auth`

**Request Body:**
```json
{
  "type": "admin_login",
  "identity": "admin",
  "password": "admin123"
}
```

**Success Response (200 OK):**
```json
{
  "status": "ok",
  "user_id": 1,
  "user_type": "admin"
}
```

**Error Response (401 Unauthorized):**
```json
{
  "message": "Invalid credentials"
}
```

---

## Books Endpoints

### Get All Books
**Endpoint:** `GET /api/books`

**Response:**
```json
[
  {
    "id": 1,
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "category": "Fiction",
    "status": "Available",
    "totalCopies": 5,
    "availableCopies": 3,
    "publicationYear": 1925,
    "viewCount": 150,
    "description": "A classic novel of the Jazz Age"
  }
]
```

### Get Book by ID
**Endpoint:** `GET /api/books/{id}`

**Parameters:**
- `id` (path) - Book ID (integer)

**Response:**
```json
{
  "id": 1,
  "title": "The Great Gatsby",
  "author": "F. Scott Fitzgerald",
  "category": "Fiction",
  "status": "Available",
  "totalCopies": 5,
  "availableCopies": 3,
  "publicationYear": 1925,
  "description": "A classic novel of the Jazz Age",
  "viewCount": 150,
  "pdfPath": "/ebooks/gatsby.pdf",
  "coverPath": "/book_covers/gatsby.jpg"
}
```

### Get Trending Books
**Endpoint:** `GET /api/books/trending`

Returns top 6 most viewed books.

**Response:**
```json
[
  {
    "id": 5,
    "title": "1984",
    "author": "George Orwell",
    "viewCount": 342
  }
]
```

### Get New Arrivals
**Endpoint:** `GET /api/books/new`

Returns 6 most recently added books.

**Response:** Similar to trending books endpoint

### Add New Book
**Endpoint:** `POST /api/books`

**Request Body:**
```json
{
  "title": "New Book Title",
  "author": "Author Name",
  "category": "Fiction",
  "totalCopies": 5,
  "publicationYear": 2024,
  "description": "Book description here",
  "pdf_base64": "JVBERi0xLjQKJ...",
  "pdf_name": "book.pdf",
  "cover_base64": "iVBORw0KGgoAAAAN...",
  "cover_name": "cover.jpg"
}
```

**Success Response (200 OK):**
```json
{
  "status": "ok",
  "book_id": 10
}
```

### Search Books
**Endpoint:** `GET /api/books/search?query=gatsby`

**Parameters:**
- `query` (query string) - Search term (title, author, or category)

**Response:**
```json
[
  {
    "id": 1,
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald"
  }
]
```

---

## Students Endpoints

### Get All Students
**Endpoint:** `GET /api/students`

**Response:**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "9876543210",
    "fines": 0.0,
    "membershipType": "Premium"
  }
]
```

### Get Student by ID
**Endpoint:** `GET /api/students/{id}`

**Parameters:**
- `id` (path) - Student ID (integer)

**Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "9876543210",
  "dob": "1999-05-15",
  "fines": 150.50,
  "profilePhoto": "/photos/john.jpg",
  "bio": "Student at XYZ University",
  "membershipType": "Premium",
  "membershipExpiry": "2025-12-31"
}
```

### Add New Student
**Endpoint:** `POST /api/students`

**Request Body:**
```json
{
  "name": "Jane Smith",
  "email": "jane@example.com",
  "phone": "9876543211",
  "dob": "2000-03-20"
}
```

**Success Response (200 OK):**
```json
{
  "status": "ok",
  "generated_password": "jane5211"
}
```

**Note:** Password is auto-generated as: `firstName + last4DigitsOfPhone`

### Update Student Profile
**Endpoint:** `POST /api/profile/update`

**Request Body:**
```json
{
  "id": 1,
  "name": "John Doe Updated",
  "phone": "9999999999",
  "bio": "Updated bio"
}
```

**Response:**
```json
{
  "status": "ok"
}
```

### Change Student Password
**Endpoint:** `POST /api/profile/password`

**Request Body:**
```json
{
  "id": 1,
  "old_password": "oldpass123",
  "new_password": "newpass456"
}
```

**Response:**
```json
{
  "status": "ok"
}
```

### Update Fine Amount
**Endpoint:** `POST /api/students`

**Request Body:**
```json
{
  "action": "update_fine",
  "student_id": 1,
  "fine": 50.00
}
```

**Response:**
```json
{
  "status": "ok"
}
```

---

## Issues (Transactions) Endpoints

### Get All Issues
**Endpoint:** `GET /api/issues`

**Response:**
```json
[
  {
    "id": 1,
    "studentId": 1,
    "bookId": 5,
    "issueType": "Check-Out",
    "issueDate": "2024-01-15T10:30:00",
    "dueDate": "2024-02-15T10:30:00",
    "returnDate": null,
    "status": "Issued",
    "fineAmount": 0.0
  }
]
```

### Get Issue by ID
**Endpoint:** `GET /api/issues/{id}`

**Response:**
```json
{
  "id": 1,
  "studentId": 1,
  "bookId": 5,
  "issueType": "Check-Out",
  "issueDate": "2024-01-15T10:30:00",
  "dueDate": "2024-02-15T10:30:00",
  "returnDate": null,
  "status": "Issued",
  "fineAmount": 0.0
}
```

### Create Book Request
**Endpoint:** `POST /api/issues`

**Request Body:**
```json
{
  "student_id": 1,
  "book_id": 5,
  "issue_type": "Check-Out"
}
```

**Success Response (200 OK):**
```json
{
  "status": "ok",
  "issue_id": 50
}
```

### Get Student Issues
**Endpoint:** `GET /api/issues?student_id=1`

Returns all transactions for a specific student.

---

## Error Responses

### 404 Not Found
```json
{
  "timestamp": "2024-01-20T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Resource not found"
}
```

### 400 Bad Request
```json
{
  "timestamp": "2024-01-20T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid request parameters"
}
```

### 500 Internal Server Error
```json
{
  "timestamp": "2024-01-20T10:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred"
}
```

---

## Rate Limiting & Quotas

Currently, there are no rate limits. However, best practices include:
- Limit requests to 100 per minute per IP
- Implement pagination for large datasets
- Cache frequently accessed data

---

## CORS Configuration

The API allows requests from all origins (`*`). For production, configure specific allowed origins in `ApplicationConfig.java`.

---

## Testing with Postman

### Collection Template

You can import the following as a Postman collection:

```json
{
  "info": {
    "name": "Library Management API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Auth",
      "item": [
        {
          "name": "Admin Login",
          "request": {
            "method": "POST",
            "url": "{{baseUrl}}/api/auth",
            "body": {
              "type": "json",
              "raw": "{\"type\":\"admin_login\",\"identity\":\"admin\",\"password\":\"admin123\"}"
            }
          }
        }
      ]
    }
  ]
}
```

---

## Versioning

Current API Version: **1.0.0**

No breaking changes are currently planned. Future versions will maintain backward compatibility when possible.

---

## Support

For API issues, questions, or feature requests, please create an issue on GitHub or contact the development team.
