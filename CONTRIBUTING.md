# Contributing to NatalCard

First off, thank you for considering contributing to NatalCard! 🎉

## Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code.

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check the existing issues as you might find out that you don't need to create one.

**When you are creating a bug report, please include as many details as possible:**

- Use a clear and descriptive title
- Describe the exact steps which reproduce the problem
- Provide specific examples to demonstrate the steps
- Describe the behavior you observed after following the steps
- Explain which behavior you expected to see instead and why
- Include screenshots if relevant

**Bug Report Template:**

```markdown
**Description**
A clear description of what the bug is.

**To Reproduce**
Steps to reproduce the behavior:
1. Send POST request to '...'
2. With payload '...'
3. See error

**Expected behavior**
What you expected to happen.

**Actual behavior**
What actually happened.

**Environment:**
- Java Version: [e.g. 17]
- Spring Boot Version: [e.g. 4.0.2]
- OS: [e.g. Windows 11]

**Additional context**
Any other context about the problem.
```

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. Create an issue and provide the following information:

- Use a clear and descriptive title
- Provide a step-by-step description of the suggested enhancement
- Provide specific examples to demonstrate the steps
- Describe the current behavior and explain which behavior you expected to see instead
- Explain why this enhancement would be useful

### Pull Requests

1. **Fork the repo** and create your branch from `main`
2. **If you've added code that should be tested**, add tests
3. **If you've changed APIs**, update the documentation
4. **Ensure the test suite passes** (`mvn test`)
5. **Make sure your code follows the existing style**
6. **Issue that pull request!**

## Development Setup

### Prerequisites

- Java 17+
- Maven 3.6+
- Git

### Setup Steps

```bash
# Clone your fork
git clone https://github.com/YOUR_USERNAME/NatalCard.git
cd NatalCard

# Add upstream remote
git remote add upstream https://github.com/ORIGINAL_OWNER/NatalCard.git

# Create a branch
git checkout -b feature/your-feature-name

# Install dependencies
mvn clean install

# Run tests
mvn test

# Run the application
mvn spring-boot:run
```

## Coding Standards

### Java Style Guide

- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Use meaningful variable and method names
- Write comments for complex logic
- Keep methods short and focused

### Code Organization

```
com.natalcard.natalcard
├── api/              # REST controllers and DTOs
├── service/          # Business logic
├── calc/             # Calculation engines
├── config/           # Configuration classes
└── i18n/             # Internationalization
```

### Naming Conventions

- **Classes**: PascalCase (e.g., `NatalChartService`)
- **Methods**: camelCase (e.g., `calculatePlanetPositions`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_ORBS`)
- **Packages**: lowercase (e.g., `com.natalcard.natalcard.api`)

### Comments

```java
/**
 * Calculate natal chart for given birth data
 * 
 * @param request Birth data including datetime, location, and preferences
 * @return Complete natal chart with planets, houses, and aspects
 * @throws IllegalArgumentException if zodiac system is not TROPICAL
 */
public NatalChartResponseDTO calculateNatalChart(NatalChartRequestDTO request) {
    // Implementation
}
```

### Testing

- Write unit tests for all new features
- Use JUnit 5 and Mockito
- Test coverage should be > 80%
- Test file naming: `ClassNameTest.java`

```java
@Test
void testCalculateNatalChart_validInput_returnsCorrectResult() {
    // Arrange
    NatalChartRequestDTO request = createTestRequest();
    
    // Act
    NatalChartResponseDTO response = service.calculateNatalChart(request);
    
    // Assert
    assertNotNull(response);
    assertEquals("PLACIDUS", response.getMeta().getRequestedHouseSystem());
}
```

## Git Commit Messages

- Use the present tense ("Add feature" not "Added feature")
- Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
- Limit the first line to 72 characters or less
- Reference issues and pull requests liberally after the first line

**Good commit messages:**

```
Add WHOLE_SIGN house system implementation

- Implement cusp calculation using signStart formula
- Add house assignment based on sign difference
- Update tests and documentation
- Fixes #123
```

```
Fix isApplying null pointer exception

Change boolean to Boolean (nullable) since planet speeds
are not calculated yet.

Closes #456
```

## Branch Naming

- `feature/description` - New features
- `bugfix/description` - Bug fixes
- `docs/description` - Documentation updates
- `refactor/description` - Code refactoring
- `test/description` - Test updates

## Adding New Languages

To add a new language (e.g., Spanish):

1. Edit `AstroTranslations.java`
2. Add translations for all categories:

```java
// In initSignTranslations()
addTranslation(SIGN_TRANSLATIONS, "ARIES", "es", "Aries");
addTranslation(SIGN_TRANSLATIONS, "TAURUS", "es", "Tauro");
// ... etc

// In initPlanetTranslations()
addTranslation(PLANET_TRANSLATIONS, "SUN", "es", "Sol");
addTranslation(PLANET_TRANSLATIONS, "MOON", "es", "Luna");
// ... etc
```

3. Update README.md with new language
4. Add tests for new translations

## Documentation

- Update README.md for new features
- Add JSDoc comments to all public methods
- Update API documentation
- Add examples where helpful

## Review Process

All submissions require review. We use GitHub pull requests for this purpose.

**The review process:**

1. Automated checks (build, tests) must pass
2. Code review by maintainers
3. Discussion and potential changes
4. Approval and merge

## Questions?

Feel free to open an issue with the `question` label or reach out to the maintainers.

---

Thank you for contributing! 🙏
