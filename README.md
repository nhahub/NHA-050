# NHA-050
# 🛒 Cartify Automation Framework & QA Report

**Project:** Cartify E-Commerce Testing  
**Client:** DEPI Mega Project  
**Team:** Team 50  
**Status:** 🛑 **Not Ready for Release** (Critical Defects Found)

---

## 📄 Project Overview
This repository contains the complete **Quality Assurance (QA) artifacts** for the Cartify E-Commerce platform. It includes the **Test Strategy**, **Manual Test Cases**, **API Validation results**, and a robust **Selenium Java Automation Framework**.

Our goal was to verify the platform against **21 Functional Requirements** across 5 domains (User, Shopping, Cart, Account, Support), ensuring a seamless user experience across Desktop and Mobile.

## 🛠️ Technology Stack

| Component | Tool / Version |
| :--- | :--- |
| **Language** | Java 17 (JDK 17) |
| **Automation Lib** | Selenium WebDriver 4.18.1 |
| **Test Runner** | TestNG 7.9.0 |
| **Build Tool** | Maven 3.x |
| **Browsers** | Chrome, Firefox (via WebDriverManager) |
| **API Testing** | Swagger / Postman |
| **Test Mgmt** | Microsoft Excel |

---

## 📂 Repository Structure

```text
NHA-050/
│
├── .github/
│   └── workflows/             # CI/CD Pipelines (GitHub Actions)
│
├── Test_Artifacts/            # 📄 Manual & API Test Sheets
│   ├── Testing_Project_Template.xlsx  # Manual Test Cases & Bug Report
│   └── API_Test_Cases.xlsx            # API Validation Results
│
├── src/                       # 🤖 Automation Code
│   ├── main/java/com/cartify/pages/   # Page Object Classes
│   │   ├── BasePage.java              # Wrapper with Explicit Waits
│   │   ├── LoginPage.java
│   │   └── ...
│   └── test/java/com/cartify/
│       ├── tests/                     # Test Scripts
│       │   ├── BaseTest.java          # Setup/Teardown logic
│       │   └── EndToEndFlowTest.java  # E2E Regression Scenario
│       └── utils/
│           └── CustomTestListener.java # Auto-generates Reports
│
├── pom.xml                    # Project Dependencies
├── testng.xml                 # Test Suite Runner
└── README.md                  # Project Documentation

📊 Test Execution Metrics
We employed a Hybrid Testing Strategy covering Manual UI, API, and Automation.

1. Manual UI Testing
Total Scenarios: 46

Pass Rate: 65% (30 Passed)

Critical Findings:

🔴 Session Failure (P1): User is logged out immediately after login (BUG-U-001).

🔴 Search Malfunction (P1): Search button refreshes the page instead of filtering results (PROD-005).

🟠 Cart Data (P2): Ghost items appear in the cart and cannot be removed (CART-002).

2. API Validation (Swagger)
Total Endpoints: 14

Pass Rate: 50% (7 Passed / 7 Failed)

Stability: ⚠️ Unstable

Key Backend Defects:

500 Internal Server Error on Order Status Update (TC_012).

500 Internal Server Error on Get Order Details (TC_011).

400 Bad Request on Product Creation (TC_013).

Note: The high failure rate in API testing directly correlates with the "Order Tracking" failures observed in the UI.

🚀 How to Run the Automation
Prerequisites
Install Java JDK 17.

Install Maven.

Clone this repository:

Bash

git clone [https://github.com/nhahub/NHA-050.git](https://github.com/nhahub/NHA-050.git)
Running Tests
Navigate to the project root and run:

Bash

mvn clean test
Tests will execute on Chrome by default. Reports (bug_report.txt) will be generated in the root folder.

🤖 Automation Highlights
This framework utilizes industry-standard design patterns for scalability:

Page Object Model (POM): Separates page elements from test logic for easy maintenance.

Explicit Waits: A custom BasePage wrapper prevents flaky tests by waiting for elements to be clickable/visible.

Java Streams: Used in WishlistPage to efficiently map and filter product lists.

Custom Listeners: Automatically generates a text-based summary and bug report after every execution.

## 📂 Documentation & Artifacts

All detailed test documentation is available in the [Test_Artifacts](./Test_Artifacts) folder.

| Artifact | Format | Description |
| :--- | :---: | :--- |
| **Master Test Plan** | [📥 Excel](./Test_Artifacts/Master_Test_Plan_v1.0.xlsx) | Combined Manual UI & API Test Cases, Traceability Matrix, and Bug Log. |
| **Executive Report** | [📊 PDF](./docs/QA_Strategy_Presentation.pdf) | High-level summary presentation for stakeholders (Strategy, Metrics, Defects). |
| **Bug Report** | [🐞 Text](./bug_report.txt) | Auto-generated log from the latest automation run. |

👥 The QA Team (Team 50)
Abdallah Mohamed: Team Lead, User Pages, Automation Architect.

Eslam Elhosany: Shopping Domain, Usability Testing.

Zeina Salem: Cart & Checkout Flows, API Testing.

Eslam Nowar: Account Management & Wishlist.

Sarah Elzaeiny: Support & Merchant Onboarding.

This project was delivered for the DEPI Mega Project - November 2025.
