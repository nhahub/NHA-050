# Selenium Java POM Framework - Cartify Testing

Framework احترافي للاختبارات الآلية باستخدام Selenium Java و Page Object Model

## 📁 هيكلية المشروع

```
src/
├── main/java/com/company/cartify/tests/
│   └── cartify/
│       ├── base/
│       │   └── BasePage.java          ← جميع الوظائف المشتركة
│       └── pages/
│           ├── CartPage.java          ← صفحة السلة
│           ├── ProductsPage.java      ← صفحة المنتجات
│           └── CheckoutPage.java      ← صفحة الدفع
│
└── test/java/com/company/cartify/tests/
    ├── base/
    │   └── BaseTest.java              ← إدارة WebDriver
    ├── CartPageTest.java              ← 7 اختبارات للسلة
    └── CheckoutPageTest.java          ← 4 اختبارات للدفع
```

## 🎯 المبادئ المطبقة

### 1. Page Object Model (POM)
- كل صفحة ويب = Page Class واحدة
- فصل كامل بين UI Logic و Test Logic
- الـ Test لا يعرف شيء عن Locators

### 2. DRY (Don't Repeat Yourself)
- BasePage تحتوي على 23 method مشتركة
- لا تكرار للكود في أي مكان

### 3. Single Responsibility
- كل Page مسؤولة عن صفحة واحدة فقط
- BaseTest مسؤول فقط عن WebDriver

## 🚀 كيفية الاستخدام

### تشغيل جميع الاختبارات
```bash
mvn test
```

### تشغيل اختبارات محددة
```bash
mvn test -Dtest=CartPageTest
mvn test -Dtest=CheckoutPageTest
```

### تشغيل Test Case معين
```bash
mvn test -Dtest=CartPageTest#testCartPageLoadsSuccessfully
```

## 📝 Test Cases المنفذة

### Cart Tests (TC-C-001 إلى TC-C-007)
- ✅ TC-C-001: تحميل صفحة السلة بنجاح
- ✅ TC-C-002: رسالة السلة الفارغة
- ✅ TC-C-003: زر Start Shopping
- ✅ TC-C-004: صفحة منتجات فارغة (سلبي)
- ✅ TC-C-005: صحة عدد المنتجات
- ✅ TC-C-006: زر Proceed to Checkout
- ✅ TC-C-007: زر Continue Shopping

### Checkout Tests (TC-C-008 إلى TC-C-011)
- ✅ TC-C-008: Validation للحقول الفارغة
- ✅ TC-C-009: سلة فارغة + COD
- ✅ TC-C-010: بيانات دفع ناقصة + سلة فارغة
- ✅ TC-C-011: توثيق القيود الحالية

## 💡 إضافة Test Case جديد

### الخطوات:

1. **تحديد الصفحة**:
   - إذا موجودة → أضف methods لها
   - إذا جديدة → أنشئ Page Class ترث من BasePage

2. **مثال لإنشاء Page جديدة**:
```java
public class LoginPage extends BasePage {
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-btn");
    
    public LoginPage(WebDriver driver, String url, int seconds) {
        super(driver, seconds, url);
    }
    
    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(loginButton);
    }
    
    public void login(String username, String password) {
        clearAndSendKeys(usernameField, username);
        clearAndSendKeys(passwordField, password);
        click(loginButton);
    }
}
```

3. **اكتب الـ Test**:
```java
public class LoginPageTest extends BaseTest {
    private LoginPage loginPage;
    
    @BeforeMethod
    public void setUpPages() {
        loginPage = new LoginPage(driver, baseUrl + "/login", defaultTimeout);
    }
    
    @Test
    public void testSuccessfulLogin() {
        loginPage.openPage();
        loginPage.login("user@test.com", "password123");
        Assert.assertTrue(/* verify logged in */);
    }
}
```

## 🛠️ المتطلبات

- Java 11+
- Maven
- Selenium 4.23.0
- TestNG 7.10.2
- ChromeDriver (يتم تحميله تلقائياً)

## 📊 الإحصائيات

- **Page Classes**: 3
- **BasePage Methods**: 23
- **Page Methods**: 52+
- **Test Cases**: 11
- **Lines of Code**: 1000+

## 📚 الموارد

- [BasePage Documentation](src/main/java/com/company/cartify/tests/cartify/base/BasePage.java)
- [Test Cases Details](walkthrough.md)
- [Implementation Plan](implementation_plan.md)
