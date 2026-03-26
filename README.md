# bertila-java-playwright-automation-framework

A Java-based UI automation framework built using Playwright, JUnit 5,
and Maven.\
Designed with Page Object Model (POM) structure, externalized test data, and scalable automation practices.

------------------------------------------------------------------------

## Tech Stack

-   Java 17
-   Playwright
-   JUnit 5
-   Maven
-   Allure Reports

------------------------------------------------------------------------

## Project Structure

src/main/java\
├── base (BasePage)\
├── pages (Page Objects)\
└── utils (ConfigReader)

src/test/java\
├── base (BaseTest)\
└── tests (Test classes)

src/test/resources ├── config.properties └
                    ── **testdata (CSV test data)**

------------------------------------------------------------------------

## Features

-   Page Object Model (POM)
-   External CSV-based Data Driven Testing (DDT)
-   Configurable browser and headless mode
-   Centralized timeout configuration
-   Login positive and negative test coverage
-   End-to-end test coverage (Login, Dashboard, Employee workflows)
-   Allure reporting integration
-   Test isolation (new page per test)

-------------------------------------------------------------------------
## Automated Test Coverage

### Login (DDT – 8 Scenarios / 1 Test)

- Valid login
- Invalid credentials (wrong username/password combinations)
- Password case sensitivity
- Required field validation (empty username/password)

Implemented using external CSV (`@CsvFileSource`) with categorized outcomes:
- success
- invalid_credentials
- required_fields

---

### Dashboard (2 Tests)

- Verify PIM menu visibility after login
- Navigate to Employee List page

---

### Logout (1 Test)

- Verify user can successfully logout and return to login page

---

### Employee Management (CRUD) (4 Tests)

- Add new employee
- Edit employee details
- Delete employee
- Validate duplicate employee ID error

---

### Employee Search (1 Test)

- Search employee by name and validate results

---

## Total Test Execution

- 8 DDT scenarios (Login)
- 8 functional test cases (Dashboard, Logout, Employee workflows)

- Total: 16 automated test executions

------------------------------------------------------------------------

## Run Tests

Install Playwright browsers (first time only):

mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI
-Dexec.args="install chromium"

Run tests:

mvn clean test

Generate Allure report:

mvn allure:report

------------------------------------------------------------------------

## Configuration

Test settings are managed in:

src/test/resources/config.properties

Includes: - Base URL - Credentials - Browser type - Headless mode -
Timeout settings

------------------------------------------------------------------------
## Test Environment & Credentials

This framework uses the public OrangeHRM demo site for test execution:
https://opensource-demo.orangehrmlive.com/

Demo credentials used in the project:
Username: Admin  
Password: admin123  

These credentials are publicly available from the demo website and are included for demonstration purposes only.

In real-world projects, credentials and environment configurations should be managed securely using environment variables or secret management tools and should not be committed to version control.

--------------------------------------------------------------------------
## Key Implementation Highlights

- Refactored login tests from inline data to external CSV-based DDT
- Expanded test coverage from 4 to 8 login scenarios including edge cases
- Differentiated negative scenarios based on UI behavior (invalid credentials vs required field validation)
- Implemented null-safe handling for test data inputs
- Implemented screenshot capture on failure for better debugging and reporting
- Integrated CI/CD pipeline for automated test execution
- Designed scalable and maintainable test structure using POM and reusable methods

-----------------------------------------------------------------------------

## Future Enhancements

- Integrate cross-browser execution support  
- Enhance reporting with detailed failure screenshots and logs  

This framework will continue evolving to reflect scalable and maintainable automation design practices.
## Author

Bertila 
