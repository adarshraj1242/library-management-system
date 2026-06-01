# Contributing to Library Management System

Thank you for your interest in contributing! We welcome contributions from the community. Please follow the guidelines below to ensure a smooth collaboration process.

## 📋 Code of Conduct

We are committed to providing a welcoming and inclusive environment. Please be respectful and constructive in all interactions.

## 🚀 Getting Started

### Fork and Clone
1. Fork this repository to your GitHub account
2. Clone your fork locally:
   ```bash
   git clone https://github.com/your-username/library-management-system.git
   cd library-management-system
   ```

### Setup Development Environment
1. Install Java 17+, Maven, and MySQL 8.0+
2. Configure `application.properties` with your database credentials
3. Build the project:
   ```bash
   ./mvnw clean install
   ```

## 📝 Making Changes

### Branch Naming Convention
- `feature/description` - For new features
- `bugfix/description` - For bug fixes
- `docs/description` - For documentation updates
- `refactor/description` - For code refactoring

### Commit Message Format
```
[TYPE] Brief description (50 chars max)

Longer explanation if needed (wrap at 72 chars).
- List any important details
- Keep it concise and clear

Fixes #123 (if applicable)
```

### Code Style Guidelines
- Follow Java naming conventions (camelCase for variables/methods, PascalCase for classes)
- Use 4 spaces for indentation
- Keep methods focused and under 30 lines when possible
- Add JavaDoc comments for public methods
- Keep lines under 120 characters

### Git Workflow

1. **Create a feature branch:**
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make your changes:**
   - Write clean, well-commented code
   - Test thoroughly before committing
   - Keep commits small and logical

3. **Commit your changes:**
   ```bash
   git add .
   git commit -m "[FEATURE] Add description of changes"
   ```

4. **Push to your fork:**
   ```bash
   git push origin feature/your-feature-name
   ```

5. **Create a Pull Request:**
   - Provide a clear title and description
   - Reference any related issues
   - Ensure CI/CD checks pass
   - Request review from maintainers

## 🧪 Testing

Before submitting a PR, ensure:
- [ ] All existing tests pass: `./mvnw clean test`
- [ ] Code compiles without errors: `./mvnw clean compile`
- [ ] No new warnings introduced
- [ ] Add unit tests for new features
- [ ] Test your changes manually in the application

## 📋 PR Review Checklist

Your PR should include:
- [ ] Clear, descriptive title
- [ ] Detailed description of changes
- [ ] Reference to related issues (if any)
- [ ] All tests passing
- [ ] No breaking changes (or clearly documented)
- [ ] Updated documentation if needed
- [ ] Follows code style guidelines

## 🐛 Reporting Bugs

Found a bug? Please report it by creating an issue with:
1. **Title:** Clear and concise description
2. **Environment:** Java version, OS, Spring Boot version
3. **Steps to Reproduce:** Detailed steps to reproduce the issue
4. **Expected Behavior:** What should happen
5. **Actual Behavior:** What actually happens
6. **Screenshots/Logs:** If applicable

## 💡 Feature Requests

Have an idea? Share it by creating an issue with:
1. **Title:** Feature description
2. **Use Case:** Why this feature is needed
3. **Proposed Solution:** How it should work (if you have ideas)
4. **Alternatives:** Other approaches you considered

## 📚 Areas for Contribution

### High Priority
- Spring Security integration for authentication
- Password encryption (BCrypt/Argon2)
- Email notification system
- Advanced search and filtering

### Medium Priority
- Barcode scanner support
- API documentation (Swagger/OpenAPI)
- Performance optimization
- Database query optimization

### Low Priority
- UI/UX enhancements
- New themes
- Additional export formats

## 📖 Documentation

Help improve documentation by:
- Fixing typos or unclear explanations
- Adding examples or use cases
- Improving API documentation
- Creating tutorials or guides

## 🎯 Questions or Need Help?

- Check existing issues and discussions first
- Ask in GitHub Discussions
- Create an issue for clarification

---

**Thank you for contributing! 🙏**
