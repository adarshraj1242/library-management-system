# Quick Start Guide

Get the Library Management System up and running in **5 minutes**! 🚀

---

## ⚡ Prerequisites

Before starting, ensure you have:

| Software | Version | Download |
|----------|---------|----------|
| **Java** | 17 or higher | [java.com](https://www.oracle.com/java/technologies/downloads/) |
| **MySQL** | 8.0+ | [mysql.com](https://dev.mysql.com/downloads/mysql/) |
| **Git** (optional) | Latest | [git-scm.com](https://git-scm.com/) |

**Verify installations:**
```bash
java -version
mysql --version
```

---

## 📥 Installation (Choose One)

### Option A: Clone from GitHub (Recommended)
```bash
git clone https://github.com/your-username/library-management-system.git
cd library-management-system
```

### Option B: Download ZIP
1. Click `Code` → `Download ZIP`
2. Extract to your desired location
3. Open terminal in the extracted folder

---

## 🗄️ Database Setup

### Step 1: Open MySQL
```bash
# Windows
mysql -u root -p

# Linux/Mac
mysql -u root -p
```

Enter your MySQL root password when prompted.

### Step 2: Create Database
```sql
CREATE DATABASE library_db;
USE library_db;
```

### Step 3: Exit MySQL
```sql
EXIT;
```

---

## ⚙️ Application Configuration

### Step 1: Open Configuration File
Navigate to: `src/main/resources/application.properties`

### Step 2: Update Database Credentials
Find these lines and enter your MySQL username and password:

**Before:**
```properties
spring.datasource.username="Enter username"
spring.datasource.password="Enter password"
```

**After:**
```properties
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

**Example:**
```properties
spring.datasource.username=root
spring.datasource.password=my_password123
```

---

## 🚀 Start the Application

### Using Maven Wrapper (Cross-Platform)

**Windows (Command Prompt/PowerShell):**
```bash
mvnw.cmd spring-boot:run
```

**Linux / macOS:**
```bash
chmod +x mvnw
./mvnw spring-boot:run
```

### Alternative: Build and Run JAR

```bash
# Build
mvnw clean package

# Run
java -jar target/library-management-1.0.0-SNAPSHOT.jar
```

---

## ✅ Verify Installation

### Check Application Status

1. **Check Console Output:**
   ```
   Tomcat started on port(s): 8081
   Started LibraryApplication in X.XXX seconds
   ```

2. **Open in Browser:**
   ```
   http://localhost:8081
   ```

3. **Should See:**
   - Landing page with login options
   - Admin and Student portals available

---

## 🔐 Login & Explore

### Admin Portal

1. Click "**Admin Login**"
2. Use default credentials:
   - **Username:** `admin`
   - **Password:** `admin123`
3. You'll see the admin dashboard with options to:
   - Manage books
   - Manage students
   - View transactions
   - Track fines

### Student Portal

1. Click "**Student Login**" or "**User**"
2. Use default credentials:
   - **Email:** `john@example.com`
   - **Password:** `password123`
3. You'll see the student dashboard with:
   - Browse books
   - Check borrowed books
   - View profile
   - Manage account

---

## 🧪 Test the API

### Using cURL (Command Line)

**Get All Books:**
```bash
curl -X GET http://localhost:8081/api/books
```

**Get Trending Books:**
```bash
curl -X GET http://localhost:8081/api/books/trending
```

**Search Books:**
```bash
curl -X GET "http://localhost:8081/api/books/search?query=gatsby"
```

### Using Postman (GUI)

1. Download [Postman](https://www.postman.com/downloads/)
2. Create new request: `GET http://localhost:8081/api/books`
3. Click "Send"
4. View JSON response

**Recommended Requests to Try:**
- `GET /api/books`
- `GET /api/students`
- `POST /api/auth` (with login credentials)
- `GET /api/books/trending`

---

## 🐛 Troubleshooting

### ❌ Error: "java: command not found"
**Solution:** Java is not installed or not in PATH
```bash
# Check installation
java -version

# If not found, install Java 17+
```

### ❌ Error: "Access denied for user 'root'@'localhost'"
**Solution:** MySQL credentials are wrong
```bash
# Verify your MySQL password in application.properties
# Update the credentials and restart the application
```

### ❌ Error: "Port 8081 is already in use"
**Solution:** Change the port in `application.properties`:
```properties
server.port=8082
```

Then access: `http://localhost:8082`

### ❌ Database Not Created
**Solution:** Make sure the database was created successfully:
```bash
mysql -u root -p
SHOW DATABASES;
# Should show 'library_db' in the list
```

### ❌ Application Won't Start
**Solution:** Check the logs:
1. Look at console output for errors
2. Check `logs/library-app.log` file
3. Verify all prerequisites are installed

---

## 📚 Next Steps

### Explore Features
- [ ] Login as admin and add new books
- [ ] Login as student and borrow books
- [ ] Check dashboard statistics
- [ ] Test API endpoints with Postman

### Read Documentation
- 📖 [Full API Documentation](API_DOCUMENTATION.md)
- 🏗️ [Project Architecture](DEVELOPMENT.md)
- 🤝 [Contributing Guidelines](CONTRIBUTING.md)

### Security Note
⚠️ **Important:** This is a learning/demonstration project. Before using in production:
- Implement password encryption (BCrypt)
- Add Spring Security
- Use environment variables for credentials
- Add input validation
- Deploy with HTTPS

---

## 💡 Common Tasks

### Add a New Book (Admin)
1. Login as admin
2. Go to "Book Inventory"
3. Click "Add Book"
4. Fill in details and upload PDF/Cover image
5. Click "Submit"

### Search for a Book (Student)
1. Login as student
2. Type in the search box
3. Results filter in real-time
4. Click book to view details
5. Click "Borrow" to request the book

### Change Password (Any User)
1. Go to "My Profile"
2. Click "Change Password"
3. Enter current and new password
4. Submit

---

## 🔗 Useful Links

- **GitHub Repository:** [library-management-system](https://github.com/your-username/library-management-system)
- **Spring Boot Docs:** [spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
- **MySQL Reference:** [dev.mysql.com/doc/](https://dev.mysql.com/doc/)
- **Java Docs:** [docs.oracle.com/javase/17/](https://docs.oracle.com/javase/17/)

---

## 📞 Need Help?

1. **Check README.md** for general information
2. **Check API_DOCUMENTATION.md** for API details
3. **Check DEVELOPMENT.md** for architecture
4. **Create a GitHub Issue** with your problem
5. **Check Troubleshooting** section above

---

## 🎓 Learning Resources

### Java & Spring Boot
- [Spring Boot Official Tutorial](https://spring.io/guides/gs/spring-boot/)
- [Java 17 Features](https://www.oracle.com/java/technologies/javase/17-relnotes.html)

### Database
- [MySQL 8.0 Tutorial](https://dev.mysql.com/doc/mysql-tutorial-excerpt/8.0/en/)
- [JPA/Hibernate Guide](https://spring.io/projects/spring-data-jpa)

### Frontend
- [Thymeleaf Documentation](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)
- [JavaScript ES6+](https://developer.mozilla.org/en-US/docs/Learn/JavaScript)

---

**Happy Coding! 🎉**

For the latest updates and versions, visit the [GitHub Repository](https://github.com/your-username/library-management-system)

