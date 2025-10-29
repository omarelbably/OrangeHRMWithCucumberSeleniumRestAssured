# HRM Open Demo - Test Automation Framework

[![Run Tests](https://github.com/YOUR_USERNAME/HRMOpenDemo/actions/workflows/maven-tests.yml/badge.svg)](https://github.com/YOUR_USERNAME/HRMOpenDemo/actions/workflows/maven-tests.yml)

This is an automated testing framework for the OrangeHRM demo application, featuring both UI and API tests using Cucumber BDD, Selenium WebDriver, and the Ellithium framework.

## Features

- ✅ **UI Testing**: E2E tests for employee management using Selenium WebDriver
- ✅ **API Testing**: REST API tests for candidate management
- ✅ **BDD Framework**: Cucumber with Gherkin syntax for readable test scenarios
- ✅ **Reporting**: Allure reports with detailed test execution logs
- ✅ **CI/CD**: GitHub Actions workflow for automated test execution

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- Chrome browser (for UI tests)

## Project Structure

```
HRMOpenDemo/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── pages/           # Page Object Model classes
│   └── test/
│       ├── java/
│       │   ├── hooks/           # Cucumber hooks
│       │   ├── runner/          # Test runner
│       │   └── steps/           # Step definitions
│       └── resources/
│           └── features/        # Cucumber feature files
├── Test-Output/                 # Test results and reports
└── .github/
    └── workflows/              # GitHub Actions CI/CD
```

## Running Tests Locally

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test
```bash
mvn test -Dtest=TestRunner
```

### Run Only E2E Tests
```bash
mvn test -Dcucumber.filter.tags="@E2E"
```

### Run Only API Tests
```bash
mvn test -Dcucumber.filter.tags="@api"
```

## Test Scenarios

### E2E Test - Employee Management
- Navigate to OrangeHRM login page
- Login with valid credentials
- Add a new employee
- Verify employee count increased
- Search for and delete the employee
- Verify employee count decreased

### API Test - Candidate Management
- Login via API
- Create a new candidate
- Retrieve candidate list
- Verify candidate exists
- Delete the candidate
- Verify candidate no longer exists

## CI/CD with GitHub Actions

This project includes a GitHub Actions workflow that automatically runs tests on every push to `main` or `master` branches.

### Workflow Features
- ✅ Automatically sets up Java 21
- ✅ Installs Chrome and ChromeDriver
- ✅ Runs all tests
- ✅ Uploads test reports as artifacts
- ✅ Generates and uploads Allure reports

### Viewing Test Results
After each workflow run:
1. Go to the "Actions" tab in your GitHub repository
2. Click on the latest workflow run
3. Download the test reports artifacts to view detailed results

## Reports

Test execution generates multiple reports:
- **Allure Reports**: `Test-Output/Reports/Allure/allure-report/`
- **Cucumber Reports**: `Test-Output/Reports/Cucumber/`
- **Surefire Reports**: `target/surefire-reports/`
- **Execution Logs**: `Test-Output/Logs/`

## Configuration

Configuration files are located in:
- `src/main/resources/properties/config.properties`
- `src/main/resources/properties/allure.properties`
- `src/main/resources/properties/log4j2.properties`

## Technologies Used

- **Java 21**: Programming language
- **Maven**: Build and dependency management
- **Selenium WebDriver**: Browser automation
- **Cucumber**: BDD framework
- **TestNG**: Test execution framework
- **RestAssured**: API testing
- **Allure**: Test reporting
- **Ellithium Framework**: Custom test automation framework
- **GitHub Actions**: CI/CD automation

## Author

Created for automated testing of OrangeHRM demo application.

## License

This project is for educational and testing purposes.

