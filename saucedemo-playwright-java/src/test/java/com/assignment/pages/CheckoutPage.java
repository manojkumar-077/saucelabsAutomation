package com.assignment.pages;
import com.microsoft.playwright.*;

public class CheckoutPage {
 private final Page page;
 public final Locator firstName,lastName,zip,continueButton,cancelButton,error,errorClose,items,summary,subtotal,tax,total,finishButton,backHomeButton,header,message;
 public CheckoutPage(Page page){
  this.page=page;
  firstName=page.locator("[data-test='firstName']");
  lastName=page.locator("[data-test='lastName']");
  zip=page.locator("[data-test='postalCode']");
  continueButton=page.locator("[data-test='continue']");
  cancelButton=page.locator("[data-test='cancel']");
  error=page.locator("[data-test='error']");
  errorClose=page.locator("[data-test='error-button']");
  items=page.locator(".cart_item");
  summary=page.locator(".summary_info");
  subtotal=page.locator(".summary_subtotal_label");
  tax=page.locator(".summary_tax_label");
  total=page.locator(".summary_total_label");
  finishButton=page.locator("[data-test='finish']");
  backHomeButton=page.locator("[data-test='back-to-products']");
  header=page.locator(".complete-header");
  message=page.locator(".complete-text");
 }
 public void fillInfo(String first,String last,String postal){
  firstName.fill(first);
  lastName.fill(last);
  zip.fill(postal);
 }
 public void continueCheckout(){
  continueButton.click();
 }
 public void cancel(){
  cancelButton.click();
 }
 public void finish(){
  finishButton.click();
 }
 public void backHome(){
  backHomeButton.click();
 }
 public void closeError(){
  errorClose.click();
 }
}
