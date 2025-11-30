package com.company.cartify.tests.cartify.pages;

import com.company.cartify.tests.cartify.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * ProductsPage: تمثل صفحة المنتجات (Products Page)
 * 
 * المشكلة: الاختبار يحتاج للتفاعل مع قائمة المنتجات وإضافتها للسلة
 * الحل POM: فصل كل Locators وActions الخاصة بالمنتجات في Page Class واحدة
 * 
 * الفوائد المعمارية:
 * 1. Single Responsibility: هذه الصفحة مسؤولة فقط عن المنتجات - لا تعرف شيء عن
 * السلة أو الدفع
 * 2. Separation of Concerns: فصل منطق عرض المنتجات عن منطق الاختبار
 * 3. Testability: سهولة اختبار السيناريوهات المختلفة (منتجات موجودة، صفحة
 * فارغة، إلخ)
 */
public class ProductsPage extends BasePage {

    // ============================================
    // Page Locators - محددات العناصر
    // ============================================

    private By pageTitle = By.cssSelector(".products-title");
    private By productsList = By.cssSelector(".products-list");
    private By productItems = By.cssSelector(".product-item");
    private By addToCartButtons = By.cssSelector(".add-to-cart-btn");
    private By emptyProductsMessage = By.cssSelector(".empty-products-message");
    private By cartIcon = By.cssSelector(".cart-icon");
    private By productName = By.cssSelector(".product-name");
    private By productPrice = By.cssSelector(".product-price");

    /**
     * Constructor
     * 
     * @param driver  WebDriver instance
     * @param url     عنوان صفحة المنتجات
     * @param seconds وقت الانتظار الافتراضي
     */
    public ProductsPage(WebDriver driver, String url, int seconds) {
        super(driver, seconds, url);
    }

    // ============================================
    // Page Verification Methods
    // ============================================

    /**
     * التحقق من تحميل الصفحة بنجاح
     * Test Cases: TC-C-003, TC-C-004, TC-C-007
     */
    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(pageTitle);
    }

    /**
     * فتح صفحة المنتجات
     */
    public void openProductsPage() {
        open();
    }

    // ============================================
    // Products Verification Methods
    // ============================================

    /**
     * فحص إذا صفحة المنتجات فارغة (لا توجد منتجات)
     * Test Case: TC-C-004, TC-C-011
     * السيناريو: صفحة المنتجات لا تعرض أي منتجات - المستخدم لا يمكنه إضافة شيء
     * للسلة
     */
    public boolean isProductsPageEmpty() {
        return getProductCount() == 0;
    }

    /**
     * الحصول على عدد المنتجات المعروضة
     * Test Case: TC-C-004
     */
    public int getProductCount() {
        return getElements(productItems).size();
    }

    /**
     * فحص إذا رسالة "لا توجد منتجات" ظاهرة
     * Test Case: TC-C-004
     */
    public boolean isEmptyProductsMessageDisplayed() {
        return isElementDisplayed(emptyProductsMessage);
    }

    /**
     * الحصول على رسالة الصفحة الفارغة
     */
    public String getEmptyProductsMessage() {
        return getElementText(emptyProductsMessage);
    }

    // ============================================
    // Add to Cart Methods
    // ============================================

    /**
     * محاولة إضافة منتج للسلة (بالترتيب - أول منتج)
     * Test Case: TC-C-004
     * ملاحظة: ستفشل إذا لم يكن هناك منتجات (Expected behavior for negative test)
     */
    public void attemptToAddProductToCart() {
        if (getProductCount() > 0) {
            // النقر على أول زر "Add to Cart"
            click(addToCartButtons);
        } else {
            throw new RuntimeException("Cannot add product to cart - Products page is empty");
        }
    }

    /**
     * إضافة منتج محدد للسلة بالترتيب (index)
     * 
     * @param index رقم المنتج (يبدأ من 0)
     */
    public void addProductToCartByIndex(int index) {
        var buttons = getElements(addToCartButtons);
        if (index >= 0 && index < buttons.size()) {
            buttons.get(index).click();
        } else {
            throw new RuntimeException("Product index out of range: " + index);
        }
    }

    /**
     * فحص إذا أزرار "Add to Cart" موجودة
     * Test Case: TC-C-004
     */
    public boolean areAddToCartButtonsVisible() {
        return getElements(addToCartButtons).size() > 0;
    }

    // ============================================
    // Navigation Methods
    // ============================================

    /**
     * النقر على أيقونة السلة للانتقال لصفحة Cart
     * الفائدة: للانتقال من المنتجات للسلة بعد إضافة منتجات
     */
    public void clickCartIcon() {
        click(cartIcon);
    }

    /**
     * الحصول على عنوان الصفحة
     */
    public String getProductsPageTitle() {
        return getPageTitle();
    }
}
