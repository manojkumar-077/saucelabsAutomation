package com.assignment.pages;
import com.microsoft.playwright.*;
public class ItemPage {
 private final Page page;
 public final Locator name,description,price,image,addButton,removeButton,backButton;
 public ItemPage(Page page){
  this.page=page;
  name=page.locator(".inventory_details_name");
  description=page.locator(".inventory_details_desc");
  price=page.locator(".inventory_details_price");
  image=page.locator(".inventory_details_img");
  addButton=page.locator("[data-test='add-to-cart']");
  removeButton=page.locator("[data-test='remove']");
  backButton=page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Back to products"));
 }
 public void add(){
  addButton.click();
 }
 public void remove(){
  removeButton.click();
 }
 public void back(){
  backButton.click();
 }
}
