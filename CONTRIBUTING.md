# Contributing to Calculator Spring Boot Application

Thank you for your interest in contributing to this project! We welcome contributions from the community.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [How to Contribute](#how-to-contribute)
- [Development Guidelines](#development-guidelines)
- [Testing Guidelines](#testing-guidelines)
- [Code Quality Standards](#code-quality-standards)
- [Submitting Changes](#submitting-changes)
- [Reporting Bugs](#reporting-bugs)
- [Suggesting Enhancements](#suggesting-enhancements)

## Code of Conduct

By participating in this project, you agree to maintain a respectful and inclusive environment for all contributors.

## Getting Started

1. **Fork the repository** on GitHub
2. **Clone your fork** locally:
   ```bash
   git clone https://github.com/YOUR-USERNAME/AIAgent.git
   cd AIAgent
   ```
3. **Add the upstream repository**:
   ```bash
   git remote add upstream https://github.com/ipsita-bit/AIAgent.git
   ```
4. **Create a feature branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```

## How to Contribute

### Types of Contributions

We appreciate all types of contributions:

- **Bug fixes** - Help us fix issues in the codebase
- **New features** - Add new functionality to the calculator
- **Documentation** - Improve or add to our documentation
- **Tests** - Increase test coverage or improve existing tests
- **Code quality** - Refactoring and code improvements

### Before You Start

1. Check the [issue tracker](../../issues) to see if your issue/feature is already being worked on
2. For major changes, open an issue first to discuss what you would like to change
3. Make sure you understand the project structure and codebase

## Development Guidelines

### Code Style

- Follow standard Java naming conventions
- Use meaningful variable and method names
- Keep methods focused and concise
- Add comments for complex logic
- Use proper indentation (4 spaces for Java)

### Project Structure

```
src/
├── main/java/com/example/calculator/
│   ├── CalculatorApplication.java     # Spring Boot application entry point
│   ├── CalculatorController.java      # REST API endpoints
│   ├── CalculatorService.java         # Business logic
│   ├── CalculatorResponse.java        # Response model
│   └── CodeCoverageAgent.java         # Coverage analysis tool
└── test/java/com/example/calculator/
    └── [Test classes]                 # Unit and integration tests
```

### Building and Running

Before submitting your changes, ensure the project builds successfully:

```bash
./gradlew build
```

To run the application locally:

```bash
./gradlew bootRun
```

## Testing Guidelines

### Writing Tests

- Write tests for all new functionality
- Follow the existing test structure
- Use descriptive test method names (e.g., `shouldReturnCorrectSumWhenAddingPositiveNumbers`)
- Test both success and failure cases
- Test edge cases and boundary conditions

### Running Tests

Run all tests:
```bash
./gradlew test
```

Run a specific test class:
```bash
./gradlew test --tests CalculatorServiceTest
```

### Test Coverage

- Maintain or improve test coverage with your changes
- Target: **80% minimum coverage** for all classes
- Verify coverage after your changes:
  ```bash
  ./gradlew test jacocoTestReport
  ```
- View coverage report: `build/reports/jacoco/test/html/index.html`
- Use the coverage analysis tool:
  ```bash
  ./gradlew analyzeCoverage
  ```

## Code Quality Standards

### Before Submitting

Ensure your changes meet these standards:

1. **All tests pass**
   ```bash
   ./gradlew test
   ```

2. **Code coverage is maintained**
   ```bash
   ./gradlew jacocoTestCoverageVerification
   ```

3. **Build succeeds**
   ```bash
   ./gradlew build
   ```

4. **Code is properly formatted**
   - Follow Java code conventions
   - Remove unnecessary imports
   - Remove debug statements and commented code

5. **Documentation is updated**
   - Update README.md if adding features
   - Add JavaDoc for public methods
   - Update inline comments as needed

## Submitting Changes

### Pull Request Process

1. **Update your branch** with the latest upstream changes:
   ```bash
   git fetch upstream
   git rebase upstream/main
   ```

2. **Run all checks** to ensure everything works:
   ```bash
   ./gradlew build
   ```

3. **Commit your changes** with a clear message:
   ```bash
   git add .
   git commit -m "Add feature: description of your changes"
   ```
   
   Commit message guidelines:
   - Use present tense ("Add feature" not "Added feature")
   - Be descriptive but concise
   - Reference issue numbers if applicable (e.g., "Fix #123")

4. **Push to your fork**:
   ```bash
   git push origin feature/your-feature-name
   ```

5. **Create a Pull Request** on GitHub with:
   - Clear title describing the change
   - Detailed description of what was changed and why
   - Reference to any related issues
   - Screenshots for UI changes (if applicable)

### Pull Request Checklist

Before submitting, verify:

- [ ] Code follows the project's style guidelines
- [ ] All tests pass
- [ ] New tests added for new functionality
- [ ] Code coverage maintained or improved
- [ ] Documentation updated (README, JavaDoc, etc.)
- [ ] Commit messages are clear and descriptive
- [ ] Branch is up to date with main
- [ ] No merge conflicts

## Reporting Bugs

### Before Reporting

1. Check if the bug has already been reported in [issues](../../issues)
2. Verify you're using the latest version
3. Try to isolate the problem

### Bug Report Template

When reporting a bug, include:

- **Description**: Clear description of the issue
- **Steps to Reproduce**: Detailed steps to reproduce the bug
- **Expected Behavior**: What you expected to happen
- **Actual Behavior**: What actually happened
- **Environment**:
  - OS (e.g., Windows 11, macOS 14, Ubuntu 22.04)
  - Java version (output of `java -version`)
  - Gradle version (if not using wrapper)
- **Error Messages**: Full error messages or stack traces
- **Additional Context**: Screenshots, logs, or any other relevant information

## Suggesting Enhancements

We welcome feature suggestions! When suggesting an enhancement:

1. **Check existing issues** to avoid duplicates
2. **Describe the enhancement** clearly
3. **Explain the use case** - why would this be useful?
4. **Provide examples** if possible
5. **Consider implementation** - any ideas on how to implement it?

### Enhancement Request Template

- **Title**: Clear, descriptive title
- **Description**: Detailed description of the enhancement
- **Use Case**: Why is this enhancement needed?
- **Proposed Solution**: How should it work?
- **Alternatives Considered**: Other approaches you've thought about
- **Additional Context**: Mockups, examples, or references

## Questions?

If you have questions about contributing:

- Open an [issue](../../issues) with the "question" label
- Check existing documentation in the [README](README.md)

---

Thank you for contributing to this project! 🎉
