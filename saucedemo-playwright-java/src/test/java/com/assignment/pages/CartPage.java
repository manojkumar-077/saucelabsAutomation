package com.assignment.pages;
import com.microsoft.playwright.*; import java.util.*;
public class CartPage {
 private final Page page;
 public final Locator title,items,continueButton,checkoutButton,cartBadge;
 public CartPage(Page page){
  this.page=page;title=page.locator(".title");
  items=page.locator(".cart_item");
  continueButton=page.locator("[data-test='continue-shopping']");
  checkoutButton=page.locator("[data-test='checkout']");
  cartBadge=page.locator(".shopping_cart_badge");
 }
 public void open(){

  page.locator(".shopping_cart_link").click();

 }
 public List<String> getNames(){

  return items.locator(".inventory_item_name").allTextContents();
 }
 public void remove(String name){
  items.filter(new Locator.FilterOptions().setHasText(name)).locator("button").click();
 }
 public void continueShopping(){
  continueButton.click();
 }
 public void checkout(){
  checkoutButton.click();
 }
}
