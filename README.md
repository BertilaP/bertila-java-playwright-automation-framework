# bertila-java-playwright-automation-framework

A Java-based UI automation framework built using Playwright, JUnit 5,
and Maven.\
Designed with Page Object Model structure and configurable test
settings.

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

src/test/resources\
└── config.properties

------------------------------------------------------------------------

## Features

-   Page Object Model (POM)
-   Configurable browser and headless mode
-   Centralized timeout configuration
-   Login positive and negative test coverage
-   Allure reporting integration
-   Test isolation (new page per test)

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

## Author

Bertila 
