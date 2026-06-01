#!/usr/bin/env powershell
# Library Management System - Login Test Script

Write-Host "=====================================" -ForegroundColor Cyan
Write-Host "Library Management System - Test Suite" -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan

$baseUrl = "http://localhost:8081"
$apiUrl = "$baseUrl/api"

# Test 1: Admin Login
Write-Host "`n[Test 1] Admin Login..." -ForegroundColor Yellow
$adminLoginBody = @{
    type = "admin_login"
    identity = "admin"
    password = "admin123"
} | ConvertTo-Json

try {
    $response = Invoke-WebRequest -Uri "$apiUrl/auth" `
        -Method Post `
        -ContentType "application/json" `
        -Body $adminLoginBody `
        -UseBasicParsing
    
    $result = $response.Content | ConvertFrom-Json
    Write-Host "✅ Admin Login: $($result.status)" -ForegroundColor Green
}
catch {
    Write-Host "❌ Admin Login Failed: $_" -ForegroundColor Red
}

# Test 2: Student/Patron Login
Write-Host "`n[Test 2] Student Login (john@example.com)..." -ForegroundColor Yellow
$patronLoginBody = @{
    type = "patron_login"
    identity = "john@example.com"
    password = "password123"
} | ConvertTo-Json

try {
    $response = Invoke-WebRequest -Uri "$apiUrl/auth" `
        -Method Post `
        -ContentType "application/json" `
        -Body $patronLoginBody `
        -UseBasicParsing
    
    $result = $response.Content | ConvertFrom-Json
    Write-Host "✅ Student Login: $($result.status)" -ForegroundColor Green
    Write-Host "   Student ID: $($result.id), Name: $($result.name)" -ForegroundColor Green
}
catch {
    Write-Host "❌ Student Login Failed: $_" -ForegroundColor Red
}

# Test 3: Get All Books
Write-Host "`n[Test 3] Get All Books..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "$apiUrl/books" `
        -Method Get `
        -UseBasicParsing
    
    $books = $response.Content | ConvertFrom-Json
    Write-Host "✅ Books Retrieved: $($books.Count) books found" -ForegroundColor Green
    if ($books.Count -gt 0) {
        Write-Host "   First Book: $($books[0].title)" -ForegroundColor Gray
    }
}
catch {
    Write-Host "❌ Get Books Failed: $_" -ForegroundColor Red
}

# Test 4: Add New Student
Write-Host "`n[Test 4] Add New Student..." -ForegroundColor Yellow
$newStudentBody = @{
    name = "Test User"
    email = "test@example.com"
    phone = "9999999999"
    dob = "2000"
} | ConvertTo-Json

try {
    $response = Invoke-WebRequest -Uri "$apiUrl/students" `
        -Method Post `
        -ContentType "application/json" `
        -Body $newStudentBody `
        -UseBasicParsing
    
    $result = $response.Content | ConvertFrom-Json
    Write-Host "✅ Student Created: $($result.status)" -ForegroundColor Green
    Write-Host "   Generated Password: $($result.generated_password)" -ForegroundColor Green
}
catch {
    Write-Host "❌ Add Student Failed: $_" -ForegroundColor Red
}

# Test 5: Get All Students
Write-Host "`n[Test 5] Get All Students..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "$apiUrl/students" `
        -Method Get `
        -UseBasicParsing
    
    $students = $response.Content | ConvertFrom-Json
    Write-Host "✅ Students Retrieved: $($students.Count) students found" -ForegroundColor Green
}
catch {
    Write-Host "❌ Get Students Failed: $_" -ForegroundColor Red
}

# Test 6: Checkout Book
Write-Host "`n[Test 6] Checkout Book (Student 1, Book 1)..." -ForegroundColor Yellow
$checkoutBody = @{
    student_id = "1"
    book_id = "1"
} | ConvertTo-Json

try {
    $response = Invoke-WebRequest -Uri "$apiUrl/issues" `
        -Method Post `
        -ContentType "application/json" `
        -Body $checkoutBody `
        -UseBasicParsing
    
    $result = $response.Content | ConvertFrom-Json
    if ($result.status -eq "ok") {
        Write-Host "✅ Book Checkout Successful" -ForegroundColor Green
    } else {
        Write-Host "❌ Book Checkout: $($result.message)" -ForegroundColor Red
    }
}
catch {
    Write-Host "❌ Checkout Failed: $_" -ForegroundColor Red
}

Write-Host "`n=====================================" -ForegroundColor Cyan
Write-Host "Tests Completed!" -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan
