package com.company.cartify.tests;

import com.company.cartify.tests.base.BaseTest;
import com.company.cartify.tests.cartify.pages.CartPage;
import com.company.cartify.tests.cartify.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartPageTest extends BaseTest {

        private CartPage cartPage;
        private ProductsPage productsPage;

        @BeforeMethod
        public void setUpPages() {
                cartPage = new CartPage(driver, cartUrl, defaultTimeout);
                productsPage = new ProductsPage(driver, productsUrl, defaultTimeout);
        }

        /**
         * TC-C-001: Cart page loads successfully
         *
         * الهدف: التحقق من أن صفحة السلة تفتح بدون أخطاء وجميع العناصر ظاهرة
         *
         * ملاحظة POM:
         * - Test لا يعرف شيء عن CSS Selectors
         * - Test يستدعي methods واضحة من CartPage
         * - القراءة سهلة: cartPage.isPageLoaded() بدلاً من driver.findElement(...)
         */
        @Test(priority = 1, description = "Verify cart page loads successfully with all elements visible")
        public void testCartPageLoadsSuccessfully() {
                // Step 1: فتح صفحة السلة
                cartPage.openCartPage();

                // Verification: التحقق من تحميل الصفحة
                Assert.assertTrue(cartPage.isPageLoaded(),
                                "Cart page should load successfully");

                // Verification: التحقق من ظهور Container السلة
                Assert.assertTrue(cartPage.isCartContainerDisplayed(),
                                "Cart container should be displayed");

                // Verification: التحقق من ظهور أزرار Checkout و Continue Shopping
                Assert.assertTrue(cartPage.isCheckoutButtonVisible(),
                                "Checkout button should be visible");
                Assert.assertTrue(cartPage.isContinueShoppingButtonVisible(),
                                "Continue Shopping button should be visible");

                // Verification: التحقق من أن الأزرار مفعّلة (functional)
                Assert.assertTrue(cartPage.isCheckoutButtonEnabled(),
                                "Checkout button should be enabled and functional");

                // Verification: التحقق من ظهور زر Delete للمنتجات
                // Note: قد يكون غير ظاهر للسلة الفارغة - هذا normal behavior
                boolean deleteButtonExists = cartPage.isDeleteButtonVisible();
                System.out.println("Delete button visible: " + deleteButtonExists);
        }

        /**
         * TC-C-002: Empty Cart – Display message
         * 
         * الهدف: التحقق من ظهور رسالة "Your cart is empty" عند عدم وجود منتجات
         * 
         * ملاحظة معمارية:
         * - CartPage تغلف كل التفاصيل الخاصة بالسلة الفارغة
         * - Test يستدعي methods بسيطة: isCartEmpty(), getEmptyCartMessage()
         * - Maintainability: لو تغير نص الرسالة أو الـ locator، نعدل CartPage فقط
         */
        @Test(priority = 2, description = "Verify empty cart displays correct placeholder message")
        public void testEmptyCartDisplaysMessage() {
                // Precondition: المستخدم على صفحة السلة والسلة فارغة
                cartPage.openCartPage();

                // Verification: التحقق من أن السلة فارغة
                Assert.assertTrue(cartPage.isCartEmpty(),
                                "Cart should be empty");

                // Verification: التحقق من نص الرسالة
                String emptyMessage = cartPage.getEmptyCartMessage();
                Assert.assertEquals(emptyMessage, "Your cart is empty",
                                "Empty cart should display 'Your cart is empty' message");
        }

        /**
         * TC-C-003: "Start Shopping" redirects user to products
         * 
         * الهدف: التحقق من أن زر "Start Shopping" ينقل المستخدم لصفحة المنتجات
         * 
         * ملاحظة POM Navigation:
         * - CartPage تعرف كيف تنقر على "Start Shopping"
         * - ProductsPage تعرف كيف تتحقق من تحميلها
         * - Test ينسق بينهما فقط - لا يتعامل مع WebDriver مباشرة
         */
        @Test(priority = 3, description = "Verify Start Shopping button redirects to product listing")
        public void testStartShoppingRedirectsToProducts() {
                // Precondition: المستخدم على صفحة السلة الفارغة
                cartPage.openCartPage();
                Assert.assertTrue(cartPage.isCartEmpty(), "Cart must be empty for this test");

                // Action: النقر على "Start Shopping"
                cartPage.clickStartShopping();

                // Verification: التحقق من الانتقال لصفحة المنتجات
                Assert.assertTrue(productsPage.isPageLoaded(),
                                "User should be redirected to Products Page");

                // Additional check: التحقق من الـ URL
                String currentUrl = driver.getCurrentUrl();
                Assert.assertTrue(currentUrl.contains("products") || currentUrl.contains("home"),
                                "URL should contain 'products' or navigate to home page");
        }

        /**
         * TC-C-004: Unable to add product to cart
         * 
         * الهدف: التحقق من سيناريو سلبي - صفحة المنتجات فارغة ولا يمكن إضافة منتجات
         * 
         * ملاحظة Testing Strategy:
         * - هذا Negative Test Case - نختبر سلوك النظام عند وجود مشكلة
         * - ProductsPage توفر methods للتحقق من الحالة الفارغة
         * - الفائدة: نوثق السلوك الحالي (bug) ونتحقق من عدم تدهور الوضع
         */
        @Test(priority = 4, description = "Verify behavior when Products Page displays no products")
        public void testUnableToAddProductToCart() {
                // Step 1: الانتقال لصفحة المنتجات
                productsPage.openProductsPage();
                Assert.assertTrue(productsPage.isPageLoaded(),
                                "Products page should load");

                // Step 2: التحقق من قائمة المنتجات
                int productCount = productsPage.getProductCount();
                System.out.println("Products displayed: " + productCount);

                // Step 3: محاولة إضافة منتج للسلة
                if (productCount == 0) {
                        // Expected behavior: لا يمكن الإضافة - صفحة فارغة
                        Assert.assertTrue(productsPage.isProductsPageEmpty(),
                                        "Products page should be empty (bug scenario)");

                        // التحقق من أن أزرار "Add to Cart" غير موجودة
                        Assert.assertFalse(productsPage.areAddToCartButtonsVisible(),
                                        "Add to Cart buttons should not be visible when no products");

                        // محاولة الإضافة ستفشل (expected)
                        try {
                                productsPage.attemptToAddProductToCart();
                                Assert.fail("Should not be able to add product when page is empty");
                        } catch (RuntimeException e) {
                                // Expected exception - test passes
                                Assert.assertTrue(e.getMessage().contains("empty"),
                                                "Exception should indicate empty products");
                        }
                } else {
                        // إذا كانت المنتجات موجودة - نضيف واحد للسلة
                        productsPage.addProductToCartByIndex(0);
                        System.out.println("Product added successfully");
                }
        }

        /**
         * TC-C-005: Cart shows incorrect item count
         * 
         * الهدف: التحقق من أن عدد المنتجات المعروض يطابق العدد الفعلي
         * 
         * ملاحظة Data Verification:
         * - CartPage توفر methods لقراءة العدد بطريقتين:
         * 1. getCartItemCount() - من الـ Counter المعروض
         * 2. getActualCartItemsCount() - من عدد العناصر في DOM
         * - Test يقارن بينهما للتحقق من التطابق
         */
        @Test(priority = 5, description = "Verify cart displays accurate item count")
        public void testCartShowsCorrectItemCount() {
                // Precondition: السلة فارغة
                cartPage.openCartPage();

                // Verification: التحقق من العدد في السلة الفارغة
                if (cartPage.isCartEmpty()) {
                        int displayedCount = cartPage.getCartItemCount();
                        int actualCount = cartPage.getActualCartItemsCount();

                        Assert.assertEquals(displayedCount, 0,
                                        "Displayed count should be 0 for empty cart");
                        Assert.assertEquals(actualCount, 0,
                                        "Actual items count should be 0 for empty cart");
                        Assert.assertEquals(displayedCount, actualCount,
                                        "Displayed count should match actual items count");
                } else {
                        // إذا كانت السلة تحتوي على منتجات
                        int displayedCount = cartPage.getCartItemCount();
                        int actualCount = cartPage.getActualCartItemsCount();

                        System.out.println("Displayed count: " + displayedCount);
                        System.out.println("Actual count: " + actualCount);

                        Assert.assertEquals(displayedCount, actualCount,
                                        "Cart item count should match actual items added");
                }
        }

        /**
         * TC-C-006: Proceed to Checkout button functionality
         * 
         * الهدف: التحقق من أن زر "Proceed to Checkout" ينقل للـ Checkout Page
         * 
         * ملاحظة Navigation Pattern:
         * - CartPage مسؤولة عن النقر على الزر
         * - CheckoutPage مسؤولة عن التحقق من الوصول
         * - Test ينسق بين الصفحتين - Clean Separation of Concerns
         */
        @Test(priority = 6, description = "Verify Proceed to Checkout button redirects correctly")
        public void testProceedToCheckoutButton() {
                // Step 1: فتح صفحة السلة
                cartPage.openCartPage();
                Assert.assertTrue(cartPage.isPageLoaded(), "Cart page should load");

                // Step 2: النقر على "Proceed to Checkout"
                cartPage.clickProceedToCheckout();

                // Verification: التحقق من الانتقال لـ Checkout Page
                // Note: نستخدم driver.getCurrentUrl() للتحقق حتى لو لم نضع CheckoutPage في
                // @BeforeMethod
                // Verification: التحقق من الانتقال لـ Checkout Page
                Assert.assertTrue(cartPage.waitForUrlToContain("checkout"),
                                "User should be redirected to Checkout Page");

                // يمكن أيضاً التحقق من عنوان الصفحة
                String pageTitle = driver.getTitle();
                System.out.println("Current page title: " + pageTitle);
        }

        /**
         * TC-C-007: Continue Shopping button functionality
         * 
         * الهدف: التحقق من أن زر "Continue Shopping" يعيد المستخدم للمنتجات
         * 
         * ملاحظة Reusability:
         * - نفس pattern كـ TC-C-006 لكن اتجاه مختلف
         * - CartPage تغلف كل تفاصيل الأزرار
         * - كود الـ Test نظيف وسهل القراءة - يشبه User Story
         */
        @Test(priority = 7, description = "Verify Continue Shopping button redirects to Products Page")
        public void testContinueShoppingButton() {
                // Step 1: فتح صفحة السلة
                cartPage.openCartPage();
                Assert.assertTrue(cartPage.isPageLoaded(), "Cart page should load");

                // Step 2: النقر على "Continue Shopping"
                cartPage.clickContinueShopping();

                // Verification: التحقق من الانتقال لصفحة المنتجات
                boolean isRedirected = cartPage.waitForUrlToContain("products") || cartPage.waitForUrlToContain("home");
                Assert.assertTrue(isRedirected, "User should be redirected to Products Page");

                // Additional verification
                Assert.assertTrue(productsPage.isPageLoaded(),
                                "Products Page should be loaded");
        }
}
