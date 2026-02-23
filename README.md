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

## Author

Bertila 
