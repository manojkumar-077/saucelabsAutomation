package com.assignment.pages;
import com.microsoft.playwright.*; import java.util.*;
public class InventoryPage {
 private final Page page; public final Locator title,items,sort,cartLink,cartBadge;
 public InventoryPage(Page page){this.page=page;title=page.locator(".title");items=page.locator(".inventory_item");sort=page.locator("[data-test='product-sort-container']");cartLink=page.locator(".shopping_cart_link");cartBadge=page.locator(".shopping_cart_badge");}
 public Locator item(String name){return items.filter(new Locator.FilterOptions().setHasText(name));}
 public List<String> getNames(){return items.locator(".inventory_item_name").allTextContents();}
 public List<Double> getPrices(){return items.locator(".inventory_item_price").allTextContents().stream().map(s->Double.parseDouble(s.replace("$", "").trim())).toList();}
 public void sortBy(String option){sort.selectOption(option);} public void add(String name){item(name).locator("button").click();}
 public void addAll(){for(int i=0;i<items.count();i++) items.nth(i).locator("button").click();}
 public void remove(String name){item(name).locator("button").click();} public void openCart(){cartLink.click();}
 public void openItem(String name){item(name).locator(".inventory_item_name").click();} public void openItemImg(String name){item(name).locator(".inventory_item_img img").click();}
}
