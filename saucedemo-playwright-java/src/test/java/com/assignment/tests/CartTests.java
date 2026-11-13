package com.assignment.tests;
import com.assignment.base.BaseTest;
import org.testng.annotations.*;
import static org.testng.Assert.*;

public class CartTests extends BaseTest {
 @BeforeMethod
 public void loginToInventory(){
  loginAsStandard();
 }
 @Test
 public void emptyCart(){
  cart.open();
  assertEquals(cart.title.textContent().trim(),"Your Cart");
  assertEquals(cart.items.count(),0);
  assertTrue(cart.continueButton.isVisible());
  assertTrue(cart.checkoutButton.isVisible());
 }

 @Test
 public void cartShowsItemNameDescriptionPriceAndQuantity(){
  inventory.add("Sauce Labs Backpack");
  cart.open();
  var item=cart.items.first();
  assertEquals(item.locator(".inventory_item_name").textContent().trim(),"Sauce Labs Backpack");
  assertFalse(item.locator(".inventory_item_desc").textContent().trim().isEmpty());
  assertEquals(item.locator(".inventory_item_price").textContent().trim(),"$29.99");
  assertEquals(item.locator(".cart_quantity").textContent().trim(),"1");
 }

 @Test
 public void cartShowsTwoItems(){
  inventory.add("Sauce Labs Backpack");
  inventory.add("Sauce Labs Bike Light");
  cart.open();
  assertEquals(cart.items.count(),2);
  assertEquals(cart.cartBadge.textContent().trim(),"2");
 }

 @Test
 public void badgeMatchesCartCountAfterRemove(){
  inventory.add("Sauce Labs Backpack");
  inventory.add("Sauce Labs Bike Light");
  cart.open();
  cart.remove("Sauce Labs Backpack");
  assertEquals(cart.items.count(),1);
  assertEquals(cart.cartBadge.textContent().trim(),"1");
 }

 @Test
 public void removeLastItemLeavesEmptyCart(){
  inventory.add("Sauce Labs Backpack");
  cart.open();
  cart.remove("Sauce Labs Backpack");
  assertEquals(cart.items.count(),0);
  assertEquals(cart.cartBadge.count(),0);
 }

 @Test
 public void removeOneItemKeepsOther(){
  inventory.add("Sauce Labs Backpack");
  inventory.add("Sauce Labs Bike Light");
  cart.open();
  cart.remove("Sauce Labs Backpack");
  assertEquals(cart.items.count(),1);
  assertEquals(cart.items.first().locator(".inventory_item_name").textContent().trim(),"Sauce Labs Bike Light");
  assertEquals(cart.cartBadge.textContent().trim(),"1");
 }

 @Test
 public void badgeUpdatesOnAddAndRemove(){
  inventory.add("Sauce Labs Backpack");
  assertEquals(inventory.cartBadge.textContent().trim(),"1");
  inventory.add("Sauce Labs Bike Light");
  assertEquals(inventory.cartBadge.textContent().trim(),"2");
  inventory.remove("Sauce Labs Backpack");
  assertEquals(inventory.cartBadge.textContent().trim(),"1");
  inventory.remove("Sauce Labs Bike Light");
  assertEquals(inventory.cartBadge.count(),0);
 }

 @Test
 public void continueShoppingGoesBackToProducts(){
  inventory.add("Sauce Labs Backpack");
  cart.open();
  cart.continueShopping();
  assertEquals(inventory.title.textContent().trim(),"Products");
  assertEquals(inventory.items.count(),6);
 }

 @Test
 public void cartKeepsItemsAfterContinueShopping(){
  inventory.add("Sauce Labs Backpack");
  cart.open();
  cart.continueShopping();
  cart.open();
  assertEquals(cart.items.count(),1);
  assertEquals(cart.items.first().locator(".inventory_item_name").textContent().trim(),"Sauce Labs Backpack");
 }

 @Test
 public void cartKeepsItemsAfterRefresh(){
  inventory.add("Sauce Labs Backpack");
  inventory.add("Sauce Labs Bike Light");
  cart.open();
  page.reload();
  assertEquals(cart.items.count(),2);
  assertEquals(cart.cartBadge.textContent().trim(),"2");
 }

 @Test
 public void emptyCartStaysEmptyAfterRefresh(){
  cart.open();
  page.reload();
  assertEquals(cart.items.count(),0);
  assertEquals(cart.cartBadge.count(),0);
 }

 @Test
 public void checkoutOpensInfoPage(){
  inventory.add("Sauce Labs Backpack");
  cart.open();
  cart.checkout();
  assertTrue(page.url().contains("checkout-step-one.html"));
 }

 @Test
 public void checkoutFromEmptyCartOpensInfoPage(){
  cart.open();
  assertEquals(cart.items.count(),0);
  cart.checkout();
  assertTrue(page.url().contains("checkout-step-one.html"));
 }

 @Test
 public void addFromDetailsThenSeeInCart(){
  inventory.openItem("Sauce Labs Backpack");
  assertEquals(itemPage.name.textContent().trim(),"Sauce Labs Backpack");
  itemPage.add();
  assertEquals(inventory.cartBadge.textContent().trim(),"1");
  cart.open();
  assertEquals(cart.items.count(),1);
  assertEquals(cart.items.first().locator(".inventory_item_name").textContent().trim(),"Sauce Labs Backpack");
 }

}
