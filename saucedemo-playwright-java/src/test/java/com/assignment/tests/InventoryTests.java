package com.assignment.tests;
import com.assignment.base.BaseTest; import org.testng.annotations.*; import java.util.*; import static org.testng.Assert.*;
public class InventoryTests extends BaseTest {
 @BeforeMethod
 public void loginToInventory(){
  loginAsStandard();
 }
 @Test
 public void productsPageLoadsWithSixItems(){
  assertEquals(inventory.title.textContent().trim(),"Products");
  assertEquals(inventory.items.count(),6);
 }

 @Test
 public void itemShowsNameDescriptionPriceAndImage(){
  var item=inventory.items.first();
  assertTrue(item.locator(".inventory_item_name").isVisible());
  assertTrue(item.locator(".inventory_item_desc").isVisible());
  assertTrue(item.locator(".inventory_item_price").isVisible());
  assertTrue(item.locator(".inventory_item_img img").isVisible());
 }

 @Test
 public void sortAToZ(){
  inventory.sortBy("az");
  var names=inventory.getNames();
  var sorted=new ArrayList<>(names);
  Collections.sort(sorted);assertEquals(names,sorted);
 }

 @Test
 public void sortZToA(){
  inventory.sortBy("za");
  var names=inventory.getNames();
  var sorted=new ArrayList<>(names);
  sorted.sort(Collections.reverseOrder());
  assertEquals(names,sorted);
 }

 @Test
 public void sortPriceLowToHigh(){
  inventory.sortBy("lohi");
  var prices=inventory.getPrices();
  var sorted=new ArrayList<>(prices);
  Collections.sort(sorted);
  assertEquals(prices,sorted);
 }

 @Test
 public void sortPriceHighToLow(){
  inventory.sortBy("hilo");
  var prices=inventory.getPrices();
  var sorted=new ArrayList<>(prices);
  sorted.sort(Collections.reverseOrder());
  assertEquals(prices,sorted);
 }

 @Test
 public void addOneItemToCart(){
  inventory.add("Sauce Labs Backpack");
  assertEquals(inventory.cartBadge.textContent().trim(),"1");
 }

 @Test
 public void addTwoItemsToCart(){
  inventory.add("Sauce Labs Backpack");
  inventory.add("Sauce Labs Bike Light");
  assertEquals(inventory.cartBadge.textContent().trim(),"2");
 }
 @Test
 public void addAllSixProducts(){
  inventory.addAll();
  assertEquals(inventory.cartBadge.textContent().trim(),"6");
 }

 @Test
 public void removeItemFromInventory(){
  inventory.add("Sauce Labs Backpack");
  inventory.remove("Sauce Labs Backpack");
  assertEquals(inventory.cartBadge.count(),0);
 }

 @Test
 public void openItemDetails(){
  inventory.openItem("Sauce Labs Backpack");
  assertTrue(page.url().contains("inventory-item.html"));
  assertEquals(itemPage.name.textContent().trim(),"Sauce Labs Backpack");
 }

 @Test
 public void openItemFromImage(){
  inventory.openItemImg("Sauce Labs Backpack");
  assertTrue(page.url().contains("inventory-item.html"));
  assertEquals(itemPage.name.textContent().trim(),"Sauce Labs Backpack");
 }

 @Test
 public void detailsPageShowsDescriptionPriceAndImage(){
  inventory.openItem("Sauce Labs Backpack");
  assertFalse(itemPage.description.textContent().trim().isEmpty());
  assertTrue(itemPage.price.isVisible());
  assertTrue(itemPage.image.isVisible());
 }

 @Test
 public void detailsMatchListItem(){
  var item=inventory.item("Sauce Labs Backpack");
  String name=item.locator(".inventory_item_name").textContent().trim();
  String desc=item.locator(".inventory_item_desc").textContent().trim();
  String price=item.locator(".inventory_item_price").textContent().trim();
  inventory.openItem("Sauce Labs Backpack");
  assertEquals(itemPage.name.textContent().trim(),name);
  assertEquals(itemPage.description.textContent().trim(),desc);
  assertEquals(itemPage.price.textContent().trim(),price);
 }

 @Test
 public void addFromDetailsPage(){
  inventory.openItem("Sauce Labs Backpack");
  itemPage.add();
  assertEquals(inventory.cartBadge.textContent().trim(),"1");
  assertTrue(itemPage.removeButton.isVisible());
 }

 @Test
 public void removeFromDetailsPage(){
  inventory.openItem("Sauce Labs Backpack");
  itemPage.add();
  itemPage.remove();
  assertEquals(inventory.cartBadge.count(),0);
  assertTrue(itemPage.addButton.isVisible());
 }

 @Test
 public void backToProductsFromDetails(){
  inventory.openItem("Sauce Labs Backpack");
  itemPage.back();
  assertTrue(page.url().contains("inventory.html"));
  assertEquals(inventory.items.count(),6);
 }

 @Test
 public void defaultSortIsAToZ(){
  var names=inventory.getNames();
  var sorted=new ArrayList<>(names);
  Collections.sort(sorted);
  assertEquals(names,sorted);
 }

}
