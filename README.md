# 🚀 OrangeHRM with Cucumber, Selenium & RestAssured 🚀

A lightweight, modular test automation framework for the OrangeHRM demo application combining UI and API testing with BDD (Cucumber), Selenium WebDriver, RestAssured and Allure reporting.

---

## 📚 Table of Contents

- [Highlights](#-highlights)
- [Prerequisites](#-prerequisites)
- [Quick Commands](#-quick-commands)
- [Project Layout](#-project-layout)
- [Test Scenarios](#-test-scenarios)
- [CI / CD](#-ci--cd)
- [Reports & Logs](#-reports--logs)
- [Configuration](#-configuration)
- [Tech Stack](#-tech-stack)
- [Author & License](#-author--license)

---

## ✨ Highlights

- 🧪 UI E2E tests (Selenium WebDriver)
- 🔁 API tests (RestAssured)
- 🎯 BDD using Cucumber (Gherkin) for readable scenarios
- 📊 Allure reports with rich execution traces and attachments
- 🔁 GitHub Actions CI for automated runs
- 🧩 Modular Page Object Model + reusable steps & hooks

---

## 🛠 Prerequisites

- Java 21+ (JDK)
- Maven 3.6+
- Chrome browser (for UI tests)
- Optional: Allure CLI for local report viewing

---

## ▶️ Quick Commands (run in your project root)

Run all tests:

```cmd
mvn clean test
```

Run a specific test runner:

```cmd
mvn test -Dtest=TestRunner
```

Run only E2E (UI) scenarios:

```cmd
mvn test -Dcucumber.filter.tags="@E2E"
```

Run only API scenarios:

```cmd
mvn test -Dcucumber.filter.tags="@api"
```

Serve Allure report locally (if Allure CLI installed):

```cmd
allure serve Test-Output/Reports/Allure/allure-results
```

---

## 📁 Project Layout

```
HRMOpenDemo/
├── src/
│   ├── main/
│   │   └── java/           # Page objects & helpers (pages/ etc.)
│   └── test/
│       ├── java/
│       │   ├── hooks/      # Cucumber hooks
│       │   ├── runner/     # Test runner(s)
│       │   └── steps/      # Step definitions
│       └── resources/
│           └── features/   # Gherkin feature files
├── Test-Output/            # Generated test results & reports
└── .github/                # CI workflows
```

---

## 🧾 Test Scenarios (examples)

### E2E — Employee Management
- Navigate to OrangeHRM login page
- Login with valid credentials
- Add a new employee
- Verify employee count increased
- Search for and delete the employee
- Verify employee count decreased

### API — Candidate Management
- Authenticate via API
- Create a new candidate
- Retrieve candidate list and verify candidate exists
- Delete the candidate and verify removal

---

## ⚙ CI / CD

A GitHub Actions workflow runs the test suite on pushes to `main`/`master`. Workflow responsibilities:

- ✅ Set up Java 21
- ✅ Install Chrome & ChromeDriver
- ✅ Run tests
- ✅ Upload test artifacts (Allure & Cucumber reports)

After a run you can download artifacts from the workflow run to inspect results.

---

## 📊 Reports & Logs

Generated outputs:

- Allure results: `Test-Output/Reports/Allure/allure-results/` (HTML output in `allure-report/`)
- Cucumber JSON/HTML: `Test-Output/Reports/Cucumber/`
- Surefire reports: `target/surefire-reports/`
- Execution logs: `Test-Output/Logs/`

Tip: If you don't have the Allure CLI installed, the CI artifacts usually include a generated HTML report you can download.

---

## 🧩 Configuration

Main config files:

- `src/main/resources/properties/config.properties`
- `src/main/resources/properties/allure.properties`
- `src/main/resources/properties/log4j2.properties`

Keep sensitive values (if any) out of the repo and inject them via CI secrets or environment variables.

---

## 🛠 Tech Stack

- Java 21
- Maven
- Selenium WebDriver
- Cucumber (Gherkin)
- TestNG
- RestAssured
- Allure
- GitHub Actions
- Ellithium framework (project-specific helpers)

---

## 👤 Author

Created for demonstration and educational use with the OrangeHRM demo application.

---

## 📜 License

For educational and testing purposes.
