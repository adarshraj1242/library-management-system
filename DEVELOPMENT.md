# Project Structure & Development Guide

## 📁 Directory Structure

```
library-management-system/
│
├── src/
│   ├── main/
│   │   ├── java/com/library/library/
│   │   │   ├── LibraryApplication.java          # Spring Boot entry point
│   │   │   ├── config/
│   │   │   │   └── ApplicationConfig.java       # CORS & Bean configuration
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java          # Authentication endpoints
│   │   │   │   ├── BookController.java          # Book API endpoints
│   │   │   │   ├── StudentController.java       # Student API endpoints
│   │   │   │   ├── IssueController.java         # Transaction endpoints
│   │   │   │   ├── ViewController.java          # HTML page routing
│   │   │   │   └── PageController.java          # Additional page routes
│   │   │   ├── model/
│   │   │   │   ├── Admin.java                   # Admin entity
│   │   │   │   ├── Student.java                 # Student entity
│   │   │   │   ├── Book.java                    # Book entity
│   │   │   │   └── Issue.java                   # Transaction/Issue entity
│   │   │   ├── repository/
│   │   │   │   ├── AdminRepository.java         # Admin data access
│   │   │   │   ├── StudentRepository.java       # Student data access
│   │   │   │   ├── BookRepository.java          # Book data access
│   │   │   │   └── IssueRepository.java         # Issue data access
│   │   │   ├── service/
│   │   │   │   └── LibraryService.java          # Business logic layer
│   │   │   ├── dto/
│   │   │   │   ├── BookDTO.java                 # Book DTO
│   │   │   │   ├── StudentDTO.java              # Student DTO
│   │   │   │   └── IssueDTO.java                # Issue DTO
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java  # Global error handler
│   │   │   │   └── ResourceNotFoundException.java # Custom exception
│   │   │   ├── security/                        # Security-related classes (expandable)
│   │   │   └── util/
│   │   │       └── ApiResponse.java             # Standardized API response wrapper
│   │   └── resources/
│   │       ├── application.properties           # Application configuration
│   │       ├── data.sql                         # Mock data initialization
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   ├── landing.css              # Landing page styles
│   │       │   │   └── styles.css               # Global styles
│   │       │   ├── js/
│   │       │   │   ├── api.js                   # API client utilities
│   │       │   │   └── dashboard.js             # Dashboard interactions
│   │       │   └── img/                         # Static images
│   │       ├── templates/
│   │       │   ├── index.html                   # Landing page
│   │       │   ├── admin.html                   # Admin portal
│   │       │   ├── admin-students.html          # Student registry
│   │       │   ├── admin-books.html             # Book inventory
│   │       │   ├── user.html                    # Student portal
│   │       │   ├── user-profile.html            # Profile page
│   │       │   └── user-checkout.html           # Checkout interface
│   │       └── sql/
│   │           └── schema.sql                   # Database schema (backup)
│   └── test/
│       └── java/com/library/library/            # Unit & integration tests
│
├── sql/
│   └── schema.sql                               # SQL database setup
│
├── .mvn/wrapper/
│   ├── maven-wrapper.jar                        # Maven wrapper JAR
│   ├── maven-wrapper.properties                 # Maven wrapper config
│   └── MavenWrapperDownloader.java
│
├── book_covers/                                 # Uploaded book cover images
├── ebooks/                                      # Uploaded PDF e-books
├── logs/                                        # Application logs
├── screenshots/                                 # UI screenshots
│
├── pom.xml                                      # Maven configuration
├── mvnw                                         # Maven wrapper (Linux/Mac)
├── mvnw.cmd                                     # Maven wrapper (Windows)
│
├── README.md                                    # Main project documentation
├── CONTRIBUTING.md                              # Contribution guidelines
├── API_DOCUMENTATION.md                         # API reference
├── DEVELOPMENT.md                               # Development guide (this file)
├── LICENSE                                      # MIT License
└── .gitignore                                   # Git ignore rules
```

---

## 🏗️ Architecture Layers

### 1. **Controller Layer** (`controller/`)
- Handles HTTP requests and responses
- Maps URLs to appropriate handlers
- Validates input parameters
- Returns JSON or HTML responses

**Key Files:**
- `AuthController.java` - Authentication endpoints
- `BookController.java` - Book-related APIs
- `StudentController.java` - Student management APIs
- `IssueController.java` - Transaction management
- `ViewController.java` - Page routing

### 2. **Service Layer** (`service/`)
- Core business logic implementation
- Transaction management
- Fine calculations
- Data validation and processing

**Key File:**
- `LibraryService.java` - Main business logic service

### 3. **Repository Layer** (`repository/`)
- Database access abstraction
- Spring Data JPA interfaces
- Auto-generated query methods

**Key Files:**
- `BookRepository.java`
- `StudentRepository.java`
- `IssueRepository.java`
- `AdminRepository.java`

### 4. **Model Layer** (`model/`)
- JPA entity classes
- Database table mappings
- Getter/setter methods

**Key Files:**
- `Book.java` - Books entity
- `Student.java` - Students entity
- `Issue.java` - Transactions entity
- `Admin.java` - Admins entity

### 5. **DTO Layer** (`dto/`)
- Data transfer objects for API responses
- Protects internal database structure
- Separates internal models from API contracts

**Key Files:**
- `BookDTO.java`
- `StudentDTO.java`
- `IssueDTO.java`

### 6. **Configuration Layer** (`config/`)
- Application-wide configuration beans
- CORS configuration
- Spring framework setup

**Key File:**
- `ApplicationConfig.java`

### 7. **Exception Handling** (`exception/`)
- Global exception handlers
- Custom exception classes
- Error response formatting

**Key Files:**
- `GlobalExceptionHandler.java`
- `ResourceNotFoundException.java`

---

## 🔄 Data Flow

### Book Checkout Request Flow

```
1. Client → POST /api/issues (HTTP Request)
    ↓
2. IssueController → validates input & calls service
    ↓
3. LibraryService → business logic (check availability, etc.)
    ↓
4. IssueRepository → save to database
    ↓
5. Database → persist issue record
    ↓
6. Response → JSON {"status": "ok", "issue_id": 50}
    ↓
7. Client → receives response
```

---

## 🔧 Development Workflow

### Setup Development Environment

1. **Install Dependencies:**
   ```bash
   # Using Maven Wrapper (cross-platform)
   ./mvnw clean install
   ```

2. **Configure Database:**
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Run Application:**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access Application:**
   - Web UI: `http://localhost:8081`
   - API endpoints: `http://localhost:8081/api/*`

### Adding a New Feature

#### Example: Add Book Rating System

1. **Update Model:**
   ```java
   // In Book.java
   private double averageRating;
   private int totalRatings;
   
   public void addRating(double rating) {
       this.totalRatings++;
       this.averageRating = (this.averageRating * (this.totalRatings - 1) + rating) / this.totalRatings;
   }
   ```

2. **Update Repository:**
   ```java
   // In BookRepository.java
   List<Book> findTop10ByOrderByAverageRatingDesc();
   ```

3. **Add Service Logic:**
   ```java
   // In LibraryService.java
   public void rateBook(int bookId, double rating) {
       bookRepository.findById(bookId).ifPresent(book -> {
           book.addRating(rating);
           bookRepository.save(book);
       });
   }
   ```

4. **Create API Endpoint:**
   ```java
   // In BookController.java
   @PostMapping("/api/books/{id}/rate")
   public ResponseEntity<Map<String, Object>> rateBook(
       @PathVariable int id,
       @RequestBody Map<String, Double> body) {
       libraryService.rateBook(id, body.get("rating"));
       return ResponseEntity.ok(Map.of("status", "ok"));
   }
   ```

5. **Update Frontend:**
   - Add rating UI component
   - Call new API endpoint

---

## 🧪 Testing

### Run Tests
```bash
./mvnw clean test
```

### Add Unit Test

Create `src/test/java/com/library/library/service/LibraryServiceTest.java`:

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryServiceTest {
    
    @Autowired
    private LibraryService libraryService;
    
    @Test
    void testAddStudent() {
        Student student = libraryService.addStudent(
            "Test User", "test@example.com", "1234567890", "2000-01-01"
        );
        assertNotNull(student);
        assertEquals("Test User", student.getName());
    }
}
```

---

## 🚀 Deployment

### Build Production JAR
```bash
./mvnw clean package -DskipTests
```

Creates: `target/library-management-1.0.0-SNAPSHOT.jar`

### Run Production JAR
```bash
java -jar target/library-management-1.0.0-SNAPSHOT.jar \
  --spring.datasource.username=prod_user \
  --spring.datasource.password=prod_password
```

### Docker Deployment

Create `Dockerfile`:
```dockerfile
FROM openjdk:17-slim
COPY target/library-management-1.0.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:
```bash
docker build -t library-app:1.0 .
docker run -p 8081:8081 \
  -e SPRING_DATASOURCE_USERNAME=user \
  -e SPRING_DATASOURCE_PASSWORD=password \
  library-app:1.0
```

---

## 📚 Key Technologies

| Component | Technology | Version |
|-----------|-----------|---------|
| Language | Java | 17+ |
| Framework | Spring Boot | 3.2.5 |
| Database | MySQL | 8.0+ |
| View Engine | Thymeleaf | (via Boot) |
| Build Tool | Maven | 3.9.14 |
| ORM | Hibernate/JPA | (via Boot) |

---

## 🐛 Common Issues & Solutions

### Issue: Connection Refused (MySQL)
**Solution:** Ensure MySQL is running and credentials are correct in `application.properties`

### Issue: Port 8081 Already in Use
**Solution:** Change port in `application.properties`:
```properties
server.port=8082
```

### Issue: HibernateException During Startup
**Solution:** Check database URL and DDL settings:
```properties
spring.jpa.hibernate.ddl-auto=update
```

---

## 📖 Useful Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/projects/spring-data-jpa)
- [Thymeleaf Documentation](https://www.thymeleaf.org/)
- [MySQL Documentation](https://dev.mysql.com/doc/)

---

## 📞 Support & Questions

For questions or issues:
1. Check existing GitHub issues
2. Create a new issue with details
3. Refer to API_DOCUMENTATION.md
4. Check troubleshooting section in README.md

