# 🛒 Cartify Automation Framework

## 📄 Project Overview
This repository contains the automated testing framework for the **Cartify E-Commerce Platform**. It is designed to validate the functionality, stability, and reliability of the application's critical user journeys, including Registration, Shopping, and Checkout.

The framework is built using the **Page Object Model (POM)** design pattern to ensure maintainability and code reusability.

## 🛠 Tech Stack
* **Language:** Java 17
* **Core Library:** Selenium WebDriver (v4.27.0)
* **Test Runner:** TestNG (v7.10.2)
* **Build Tool:** Maven
* **Reporting:** ExtentReports 5 (HTML Reports with Screenshots)
* **Utilities:** Apache Commons IO, WebDriverManager

## 🚀 Key Features
* **Page Object Model (POM):** UI Locators and logic are separated from Test Classes (`/pages` vs `/tests`).
* **Robust Synchronization:** Implements `WebDriverWait` and dynamic element handling in a `BasePage` wrapper.
* **Automated Reporting:** Generates interactive HTML reports with pie charts and execution logs.
* **Failure Capture:** Custom TestListener automatically captures and attaches screenshots when a test fails.
* **Cross-Browser Ready:** Infrastructure set up for easy browser switching (Chrome default).

## 📂 Project Structure
```text
src/test/java/com/cartify/
├── tests/              # Test Scripts (EndToEnd, Cart, Checkout)
├── listeners/          # TestNG Listeners for logging & screenshots
└── utils/              # Reporting & Helper classes

src/main/java/com/cartify/
└── pages/              # Page Objects (Locators & Page Actions)
⚡ How to Run Tests
Run all tests (Suite):

Bash

mvn clean test
Run specific test class:

Bash

mvn -Dtest=EndToEndFlowTest test
View Reports: After execution, open the report at: target/extent-report.html

Team 50 | QA Department
Here are the deliverables you requested. I have analyzed your project files (`pom.xml`, test code, and Master Test Plan) to ensure these are tailored exactly to your "Cartify" project.

### 1\. Professional README.md

*Add this to the root of your repository. It highlights your technical choices (POM, ExtentReports, Listeners) which will impress the evaluators.*

````markdown
# 🛒 Cartify Automation Framework

## 📄 Project Overview
This repository contains the automated testing framework for the **Cartify E-Commerce Platform**. It is designed to validate the functionality, stability, and reliability of the application's critical user journeys, including Registration, Shopping, and Checkout.

The framework is built using the **Page Object Model (POM)** design pattern to ensure maintainability and code reusability.

## 🛠 Tech Stack
* **Language:** Java 17
* **Core Library:** Selenium WebDriver (v4.27.0)
* **Test Runner:** TestNG (v7.10.2)
* **Build Tool:** Maven
* **Reporting:** ExtentReports 5 (HTML Reports with Screenshots)
* **Utilities:** Apache Commons IO, WebDriverManager

## 🚀 Key Features
* **Page Object Model (POM):** UI Locators and logic are separated from Test Classes (`/pages` vs `/tests`).
* **Robust Synchronization:** Implements `WebDriverWait` and dynamic element handling in a `BasePage` wrapper.
* **Automated Reporting:** Generates interactive HTML reports with pie charts and execution logs.
* **Failure Capture:** Custom TestListener automatically captures and attaches screenshots when a test fails.
* **Cross-Browser Ready:** Infrastructure set up for easy browser switching (Chrome default).

## 📂 Project Structure
```text
src/test/java/com/cartify/
├── tests/              # Test Scripts (EndToEnd, Cart, Checkout)
├── listeners/          # TestNG Listeners for logging & screenshots
└── utils/              # Reporting & Helper classes

src/main/java/com/cartify/
└── pages/              # Page Objects (Locators & Page Actions)
````

## ⚡ How to Run Tests

**Run all tests (Suite):**

```bash
mvn clean test
```

**Run specific test class:**

```bash
mvn -Dtest=EndToEndFlowTest test
```

**View Reports:**
After execution, open the report at:
`target/extent-report.html`

-----

**Team 50 | QA Department**

```

---

### 2. SQL Injection (Security) Test Cases ("SIQs")
*I interpreted "siqs" as **SQL Injection / Security Scenarios**. Add these to your "Test Cases" sheet in the Master Test Plan to show you covered security risks.*

| TC_ID | Feature | Req ID | Title | Description | Preconditions | Test Steps | Expected Result | Priority |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **SEC-001** | **Login** | **FR-U-002** | **SQL Injection - Admin Bypass** | Verify that the login input is sanitized against common SQL injection attacks. | User is on Login Page | 1. Enter Email: `' OR '1'='1` <br> 2. Enter Password: `' OR '1'='1` <br> 3. Click Login | System should display "Invalid Credentials" error. User MUST NOT be logged in. | **Critical** |
| **SEC-002** | **Products** | **FR-S-006** | **SQL Injection - Search Bar** | Verify that search queries do not expose database errors or dump data. | User is on Products Page | 1. In Search bar, enter: `Apple'; DROP TABLE Products; --` <br> 2. Click Search | System should show "No results found" or handle input gracefully. No Database Error/Stack Trace should appear. | **High** |
| **SEC-003** | **Register** | **FR-U-001** | **XSS Injection - Name Field** | Verify that the name field rejects script tags (Cross-Site Scripting). | User is on Register Page | 1. Enter First Name: `<script>alert('Hacked')</script>` <br> 2. Complete other fields and Submit | The script should NOT execute. The name should be saved as plain text or rejected. | **High** |

---

### 3. Traceability Matrix
*This maps your Requirements to your Test Cases. Copy this data into the **Traceability.csv** tab of your Excel sheet.*

| Req_ID | Functional Requirement Summary | Linked Test Cases (UI & API) | Coverage Status |
| :--- | :--- | :--- | :--- |
| **FR-U-001** | User Registration (Valid/Invalid) | TC-U-1.1, TC-U-1.6, TC-U-1.7, TC_001, TC_002 | ✅ Covered |
| **FR-U-002** | Login Validation & Security | TC-U-1.2, TC_003, SEC-001 | ✅ Covered |
| **FR-S-005** | Product Grid Display | TC-S-005 | ✅ Covered |
| **FR-S-006** | Search Functionality | TC-S-006, SEC-002 | ✅ Covered |
| **FR-S-007** | Product Filtering (Price/Category) | TC-S-007 | ✅ Covered |
| **FR-C-010** | Add to Cart Functionality | TC-C-010, TC_014 | ✅ Covered |
| **FR-C-011** | Checkout Process & Validation | TC-C-011, TC_014 | ✅ Covered |

---

### 4. Team Leader Discussion Script & Flow
*Use this script for your presentation/discussion. It is structured to be authoritative and covers the full scope of your work.*

**Phase 1: Introduction (The Hook)**
"Good [Morning/Afternoon], everyone. I am **[Your Name]**, the QA Lead for **Team 50**. Today, we are presenting the Quality Assurance sign-off report for the **Cartify E-Commerce Platform**.
Our goal wasn't just to 'find bugs'—it was to ensure the platform is reliable, secure, and ready for real users. We adopted a **Hybrid Testing Strategy**, combining rigorous Manual Testing with a robust Java-based Automation Framework."

**Phase 2: The Scope & Strategy**
"We targeted 5 core business domains: User Management, Shopping, Cart, Checkout, and Admin features.
* **Manual Testing:** We executed **60 Test Cases** covering UI, Usability, and Negative scenarios.
* **API Testing:** We used Swagger to validate the backend independently, ensuring data integrity before it even reached the UI.
* **Automation:** This is where we added the most value..."

**Phase 3: Automation Framework Deep Dive (The Technical Flex)**
*(This is where you show off the code structure)*
"We built a custom automation framework using **Java and Selenium**.
* **Architecture:** We used the **Page Object Model**. This means if a button ID changes on the Login page, we update it in *one* file (`LoginPage.java`), and all 50 tests update automatically.
* **Reliability:** We implemented 'Explicit Waits' in our `BasePage` class to handle network lag, eliminating flaky tests.
* **Reporting:** We integrated **ExtentReports**. As you can see in our screenshots, if a test fails, the framework *automatically* takes a screenshot and attaches it to the report, making debugging instant for developers."

**Phase 4: Findings & Traceability**
"In terms of results:
* We identified **20 Valid Defects**, including Critical (P1) issues in the Checkout flow and Search module.
* We achieved **100% Traceability**, meaning every single requirement in the project document is linked to at least one test case, ensuring nothing was left untested."

**Phase 5: Conclusion & Recommendation**
"Based on our execution:
While the Core Functional Flows (Registration, Browsing) are stable, the **Checkout logic** still has critical bugs involving empty cart submissions.
Therefore, our Quality Verdict is: **Conditional Pass**. We recommend fixing the identified P1 defects before the full public release.
Thank you. I’m happy to answer any questions about our code or test coverage."
```
