# 🤖 Selenium + TestNG Automation Framework
### Target: [SauceDemo](https://www.saucedemo.com) | Pattern: Page Object Model

![CI](https://github.com/Aditya12218492/qa-automation-selenium-java/actions/workflows/selenium-tests.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-11-orange?logo=java)
![Selenium](https://img.shields.io/badge/Selenium-4.18.1-green?logo=selenium)
![TestNG](https://img.shields.io/badge/TestNG-7.9.0-red)
![Maven](https://img.shields.io/badge/Maven-3.8+-blue?logo=apachemaven)

---

## 📁 Project Structure

```
selenium-testng-project/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/saucedemo/
    │   ├── config/
    │   │   └── ConfigReader.java          ← Reads config.properties
    │   ├── pages/
    │   │   ├── BasePage.java              ← Reusable Selenium helpers
    │   │   ├── LoginPage.java             ← Login Page Object
    │   │   ├── InventoryPage.java         ← Products Page Object
    │   │   ├── CartPage.java              ← Cart Page Object
    │   │   └── CheckoutPage.java          ← Checkout Page Object
    │   └── utils/
    │       ├── DriverManager.java         ← Thread-safe WebDriver factory
    │       ├── ScreenshotUtils.java       ← Auto-screenshot on failure
    │       └── ReportManager.java         ← ExtentReports HTML reports
    └── test/java/com/saucedemo/
        ├── listeners/
        │   └── TestListener.java          ← TestNG lifecycle listener
        └── tests/
            ├── BaseTest.java              ← @BeforeMethod / @AfterMethod
            ├── LoginTest.java             ← 6 Login test cases
            ├── InventoryTest.java         ← 6 Inventory test cases
            └── CheckoutTest.java          ← 3 E2E Checkout test cases
```

---

## ✅ Test Cases Covered (15 Total)

| ID    | Module    | Scenario                                  |
|-------|-----------|-------------------------------------------|
| TC01  | Login     | Valid login → redirects to inventory      |
| TC02  | Login     | Invalid credentials → error message       |
| TC03  | Login     | Locked-out user → locked error            |
| TC04  | Login     | Empty username → validation error         |
| TC05  | Login     | Empty password → validation error         |
| TC06  | Login     | Logout → returns to login page            |
| TC07  | Inventory | 6 products displayed                      |
| TC08  | Inventory | Add single item → cart badge = 1          |
| TC09  | Inventory | Add all items → cart badge = 6            |
| TC10  | Inventory | Sort by Price (low to high)               |
| TC11  | Inventory | Sort by Name (A to Z)                     |
| TC12  | Inventory | Navigate to cart page                     |
| TC13  | Checkout  | Full E2E: login → cart → checkout → done  |
| TC14  | Checkout  | Missing first name → error                |
| TC15  | Checkout  | Missing postal code → error               |

---

## 🛠️ Tech Stack

| Tool              | Version | Purpose                        |
|-------------------|---------|--------------------------------|
| Java              | 11+     | Programming language           |
| Selenium WebDriver| 4.18.1  | Browser automation             |
| TestNG            | 7.9.0   | Test framework                 |
| WebDriverManager  | 5.7.0   | Auto browser driver setup      |
| ExtentReports     | 5.1.1   | Beautiful HTML reports         |
| Log4j2            | 2.22.1  | Logging                        |
| Apache POI        | 5.2.5   | Excel data-driven support      |
| Maven             | 3.8+    | Build & dependency management  |

---

## 🚀 How to Run

### Prerequisites
- Java 11+ installed
- Maven 3.8+ installed
- Chrome / Firefox / Edge browser

### Step 1: Clone / Download the project
```bash
cd selenium-testng-project
```

### Step 2: Configure (optional)
Edit `src/test/resources/config.properties` to change:
- `browser=chrome` → `firefox` or `edge`
- `headless=false` → `true` to run without GUI

### Step 3: Run all tests
```bash
mvn clean test
```

### Step 4: Run a specific test class
```bash
mvn test -Dtest=LoginTest
mvn test -Dtest=CheckoutTest
```

### Step 5: View HTML Report
After the run, open the generated file:
```
reports/TestReport_<timestamp>.html
```

---

## 🔑 Key Design Patterns

- **Page Object Model (POM)** — Each page is a separate class with locators and actions
- **ThreadLocal WebDriver** — Parallel execution safe
- **Fluent API** — Methods return page objects for chained calls
- **Singleton ConfigReader** — Config loaded once, reused everywhere
- **TestNG Listener** — Auto screenshots + report logging on every test

---

## 📸 Screenshots
All failure screenshots are saved to:
```
reports/screenshots/<TestName>_<timestamp>.png
```

---

## 💡 Interview Tips

This project demonstrates:
1. ✅ Page Object Model design pattern
2. ✅ Separation of concerns (pages / tests / utils / config)
3. ✅ TestNG annotations (@Test, @BeforeMethod, @AfterMethod, @Listeners)
4. ✅ Explicit waits (no Thread.sleep!)
5. ✅ Data-driven ready (Apache POI included)
6. ✅ Parallel execution support (ThreadLocal)
7. ✅ Reporting with ExtentReports
8. ✅ Screenshot on failure
9. ✅ Logging with Log4j2
10. ✅ WebDriverManager (no manual driver downloads)
