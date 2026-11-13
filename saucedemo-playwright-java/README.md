# SauceDemo Playwright Java Automation Framework


## Requirements
- JDK 17 or later
- Maven 3.8+
- Internet access on first run to download Maven dependencies and browser binaries

## Setup
```bash
mvn clean test
```
Playwright's Java package downloads browser binaries through its CLI. If browsers are missing, install them using the Playwright Java CLI documented at https://playwright.dev/java/docs/browsers. On a Maven project the CLI can be run with the resolved Playwright driver; see the docs for your OS.

## Run
```bash
# Headless Chromium (default)
mvn clean test

# Headed Chromium
mvn -Pheaded clean test

# Select a browser engine
mvn test -Dbrowser=firefox
mvn test -Dbrowser=webkit

# Choose a SauceDemo account profile for exploratory execution
mvn test -DuserProfile=standard
mvn test -DuserProfile=problem
mvn test -DuserProfile=glitch
```

The main regression suite expects the standard user because the problem/glitch/error accounts intentionally behave differently. `userProfile` is provided for manual/exploratory execution; login tests cover the locked-out and invalid users explicitly.

## Structure
```
src/test/java/com/assignment/
  base/BaseTest.java
  data/Users.java
  pages/LoginPage.java
        InventoryPage.java
        ItemPage.java
        CartPage.java
        CheckoutPage.java
  tests/LoginTests.java
        InventoryTests.java
        CartTests.java
        CheckoutTests.java
src/test/resources/config.properties
pom.xml
testng.xml
TEST-CASES.md
```

## Design notes
- Page classes encapsulate locators and user actions.
- Test classes contain scenario setup and assertions.
- Each test gets a new browser context and browser instance to reduce state leakage.
- Failed tests save full-page screenshots under `test-results/screenshots/`.
- TestNG's Surefire integration writes test reports under `target/surefire-reports/`.

## Notes
The original Playwright Test runner features (`projects`, fixture metadata, HTML reporter, video and trace retention) are specific to the JavaScript runner. This Java implementation adapts them to TestNG and captures failure screenshots. Cross-browser execution is selected with `-Dbrowser=` rather than Playwright Test projects.
