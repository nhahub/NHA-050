package com.company.cartify.tests.cartify.pages;

import com.company.cartify.tests.cartify.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * CartPage: تمثل صفحة السلة (Cart Page)
 * 
 * المشكلة: الاختبار يحتاج للتفاعل مع عناصر السلة (عرض المنتجات، الأسعار،
 * الانتقال للدفع)
 * الحل POM: فصل كل Locators وActions الخاصة بالسلة في Page Class واحدة
 * 
 * الفوائد المعمارية:
 * 1. Encapsulation: كل تفاصيل صفحة السلة مخفية هنا - الـ Test لا يعرف شيء عن
 * Locators
 * 2. Abstraction: الـ Test يستدعي methods واضحة مثل clickCheckout() بدلاً من
 * التعامل مع CSS Selectors
 * 3. Maintainability: لو تغير الـ locator، نعدله في مكان واحد فقط
 * 4. Reusability: أي test يحتاج السلة يستخدم نفس الـ CartPage
 */
public class CartPage extends BasePage {

    // ============================================
    // Page Locators - محددات العناصر
    // ============================================
    // ملاحظة: كل الـ Locators private - لا يمكن للـ Test الوصول لها مباشرة
    // (Encapsulation)

    private By cartTitle = By.cssSelector(".cart-title");
    private By cartCountText = By.cssSelector(".cart-count");
    private By cartContainer = By.id("cart-container");
    private By checkoutBtn = By.cssSelector(".checkout-btn");
    private By continueShoppingBtn = By.cssSelector(".continue-shopping");
    private By startShoppingBtn = By.cssSelector(".start-shopping"); // للسلة الفارغة
    private By emptyCartMessage = By.cssSelector(".empty-cart-message");

    // Price elements
    private By subtotalElement = By.id("subtotal");
    private By shippingElement = By.id("shipping");
    private By taxElement = By.id("tax");
    private By totalElement = By.id("total");

    // Cart items
    private By cartItems = By.cssSelector(".cart-item");
    private By deleteItemBtn = By.cssSelector(".delete-item");

    /**
     * Constructor
     * 
     * @param driver  WebDriver instance
     * @param url     عنوان صفحة السلة
     * @param seconds وقت الانتظار الافتراضي
     */
    public CartPage(WebDriver driver, String url, int seconds) {
        super(driver, seconds, url);
    }

    // ============================================
    // Page Verification Methods - طرق التحقق من الصفحة
    // ============================================

    /**
     * التحقق من تحميل الصفحة بنجاح
     * Test Case: TC-C-001
     * الفائدة: نتأكد أن الصفحة فتحت بدون أخطاء قبل بدء الاختبار
     */
    @Override
    public boolean isPageLoaded() {
        try {
            waitForElementToBeVisible(cartTitle);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * فتح صفحة السلة
     */
    public void openCartPage() {
        open();
    }

    // ============================================
    // Cart Item Methods - طرق العناصر في السلة
    // ============================================

    /**
     * الحصول على عدد المنتجات في السلة
     * Test Case: TC-C-005
     * الفائدة: للتحقق من أن العدد المعروض صحيح
     */
    public int getCartItemCount() {
        if (isCartEmpty()) {
            return 0;
        }
        String countText = getElementText(cartCountText);
        // إزالة أي نصوص إضافية والحصول على الرقم فقط
        return Integer.parseInt(countText.replaceAll("[^0-9]", ""));
    }

    /**
     * الحصول على عدد عناصر السلة الفعلية (من DOM)
     * Test Case: TC-C-005
     */
    public int getActualCartItemsCount() {
        return getElements(cartItems).size();
    }

    /**
     * فحص إذا السلة فارغة
     * Test Case: TC-C-002
     * الفائدة: للتحقق من ظهور رسالة السلة الفارغة
     */
    public boolean isCartEmpty() {
        return isElementDisplayed(emptyCartMessage);
    }

    /**
     * الحصول على رسالة السلة الفارغة
     * Test Case: TC-C-002
     * Expected: "Your cart is empty"
     */
    public String getEmptyCartMessage() {
        return getElementText(emptyCartMessage);
    }

    // ============================================
    // Price Methods - طرق الأسعار
    // ============================================

    /**
     * الحصول على المجموع الفرعي (Subtotal)
     * الفائدة: للتحقق من صحة الحسابات في الاختبارات
     */
    public String getSubtotal() {
        return getElementText(subtotalElement);
    }

    /**
     * الحصول على قيمة الشحن (Shipping)
     */
    public String getShipping() {
        return getElementText(shippingElement);
    }

    /**
     * الحصول على قيمة الضريبة (Tax)
     */
    public String getTax() {
        return getElementText(taxElement);
    }

    /**
     * الحصول على المجموع الكلي (Total)
     */
    public String getTotal() {
        return getElementText(totalElement);
    }

    // ============================================
    // Navigation Methods - طرق التنقل
    // ============================================

    /**
     * النقر على زر "Proceed to Checkout"
     * Test Case: TC-C-006
     * الفائدة: الانتقال لصفحة الدفع
     * 
     * ملاحظة معمارية: الـ Test يستدعي هذه الـ method ولا يعرف شيء عن الـ CSS
     * Selector
     * فصل واضح: UI Logic (هنا) vs Test Logic (في Test Class)
     */
    public void clickProceedToCheckout() {
        click(checkoutBtn);
    }

    /**
     * النقر على زر "Continue Shopping"
     * Test Case: TC-C-007
     * الفائدة: العودة لصفحة المنتجات
     */
    public void clickContinueShopping() {
        click(continueShoppingBtn);
    }

    /**
     * النقر على زر "Start Shopping" (للسلة الفارغة)
     * Test Case: TC-C-003
     * الفائدة: الانتقال لصفحة المنتجات من السلة الفارغة
     */
    public void clickStartShopping() {
        click(startShoppingBtn);
    }

    // ============================================
    // Button Verification Methods - طرق التحقق من الأزرار
    // ============================================

    /**
     * فحص إذا زر Checkout ظاهر ومفعّل
     * Test Case: TC-C-001
     */
    public boolean isCheckoutButtonVisible() {
        return isElementDisplayed(checkoutBtn);
    }

    /**
     * فحص إذا زر Checkout مفعّل (Enabled)
     */
    public boolean isCheckoutButtonEnabled() {
        return isElementEnabled(checkoutBtn);
    }

    /**
     * فحص إذا زر Continue Shopping ظاهر
     * Test Case: TC-C-001
     */
    public boolean isContinueShoppingButtonVisible() {
        return isElementDisplayed(continueShoppingBtn);
    }

    /**
     * فحص إذا زر Delete ظاهر للمنتجات
     * Test Case: TC-C-001
     */
    public boolean isDeleteButtonVisible() {
        return isElementDisplayed(deleteItemBtn);
    }

    /**
     * فحص إذا Container السلة ظاهر
     * Test Case: TC-C-001
     */
    public boolean isCartContainerDisplayed() {
        try {
            waitForElementToBeVisible(cartContainer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * الحصول على عنوان الصفحة للتحقق
     */
    public String getCartPageTitle() {
        return getPageTitle();
    }
}
