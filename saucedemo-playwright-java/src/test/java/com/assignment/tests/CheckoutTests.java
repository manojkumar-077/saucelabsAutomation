package com.assignment.tests;
import com.assignment.base.BaseTest;
import org.testng.annotations.*;
import static org.testng.Assert.*;

public class CheckoutTests extends BaseTest {

 @BeforeMethod
 public void startCheckout(){
  loginAsStandard();
  inventory.add("Sauce Labs Backpack");
  cart.open();
  cart.checkout();
 }

 private void validInfo(){
  checkout.fillInfo("Test","User","570001");
  checkout.continueCheckout();
 }

 @Test
 public void checkoutInfoPageShowsFields(){
  assertTrue(checkout.firstName.isVisible());
  assertTrue(checkout.lastName.isVisible());
  assertTrue(checkout.zip.isVisible());
  assertTrue(checkout.continueButton.isVisible());
  assertTrue(checkout.cancelButton.isVisible());
 }

 @Test
 public void cannotContinueWithEmptyFields(){
  checkout.continueCheckout();
  assertTrue(checkout.error.textContent().contains("First Name is required"));
 }

 @Test
 public void firstNameRequired(){
  checkout.fillInfo("","Tester","570001");
  checkout.continueCheckout();
  assertTrue(checkout.error.textContent().contains("First Name is required"));
 }

 @Test
 public void lastNameRequired(){
  checkout.fillInfo("Test","","570001");
  checkout.continueCheckout();
  assertTrue(checkout.error.textContent().contains("Last Name is required"));
 }

 @Test
 public void postalCodeRequired(){
  checkout.fillInfo("Test","User","");
  checkout.continueCheckout();
  assertTrue(checkout.error.textContent().contains("Postal Code is required"));
 }

 @Test
 public void closeErrorMessage(){
  checkout.continueCheckout();
  checkout.closeError();
  assertFalse(checkout.error.isVisible());
 }

 @Test
 public void cancelFromInfoGoesToCart(){
  checkout.cancel();
  assertTrue(page.url().contains("cart.html"));
  assertEquals(cart.items.count(),1);
 }

 @Test
 public void validInfoOpensOverview(){
  validInfo();
  assertTrue(page.url().contains("checkout-step-two.html"));
  assertEquals(inventory.title.textContent().trim(),"Checkout: Overview");
 }

 @Test
 public void overviewShowsSelectedItem(){
  validInfo();
  assertEquals(checkout.items.count(),1);
  assertEquals(checkout.items.first().locator(".inventory_item_name").textContent().trim(),"Sauce Labs Backpack");
  assertEquals(checkout.items.first().locator(".cart_quantity").textContent().trim(),"1");
  assertEquals(checkout.items.first().locator(".inventory_item_price").textContent().trim(),"$29.99");
 }

 @Test
 public void overviewShowsPaymentAndShipping(){
  validInfo();
  assertTrue(checkout.summary.textContent().contains("Payment Information"));
  assertTrue(checkout.summary.textContent().contains("Shipping Information"));
 }

 @Test
 public void overviewTotalsForOneItem(){
  validInfo();
  assertEquals(checkout.subtotal.textContent().trim(),"Item total: $29.99");
  assertEquals(checkout.tax.textContent().trim(),"Tax: $2.40");
  assertEquals(checkout.total.textContent().trim(),"Total: $32.39");
 }

 @Test
 public void cancelFromOverviewGoesToProducts(){
  validInfo();
  checkout.cancel();
  assertTrue(page.url().contains("inventory.html"));
  assertEquals(inventory.title.textContent().trim(),"Products");
 }

 @Test
 public void finishCompletesOrder(){
  validInfo();
  checkout.finish();
  assertTrue(page.url().contains("checkout-complete.html"));
  assertEquals(checkout.header.textContent().trim(),"Thank you for your order!");
  assertTrue(checkout.message.textContent().contains("Your order has been dispatched"));
 }

 @Test
 public void backHomeAfterOrder(){
  validInfo();
  checkout.finish();
  checkout.backHome();
  assertTrue(page.url().contains("inventory.html"));
  assertEquals(inventory.title.textContent().trim(),"Products");
 }

 @Test
 public void completedOrderClearsCart(){
  validInfo();
  checkout.finish();
  checkout.backHome();
  cart.open();
  assertEquals(cart.items.count(),0);
  assertEquals(cart.cartBadge.count(),0);
 }

 @Test public void totalsForTwoItems(){
  checkout.cancel();
  cart.continueShopping();
  inventory.add("Sauce Labs Bike Light");
  cart.open();
  cart.checkout();
  validInfo();
  assertEquals(checkout.items.count(),2);
  assertEquals(checkout.subtotal.textContent().trim(),"Item total: $39.98");
  assertEquals(checkout.tax.textContent().trim(),"Tax: $3.20");
  assertEquals(checkout.total.textContent().trim(),"Total: $43.18");
 }

 @Test
 public void twoItemsListedOnOverview(){
  checkout.cancel();
  cart.continueShopping();
  inventory.add("Sauce Labs Bike Light");
  cart.open();
  cart.checkout();
  validInfo();
  assertEquals(checkout.items.filter(new com.microsoft.playwright.Locator.FilterOptions().setHasText("Sauce Labs Backpack")).count(),1);
  assertEquals(checkout.items.filter(new com.microsoft.playwright.Locator.FilterOptions().setHasText("Sauce Labs Bike Light")).count(),1);
 }

}
