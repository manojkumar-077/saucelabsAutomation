package com.assignment.pages;
import com.microsoft.playwright.*;

public class LoginPage {
 private final Page page;
 private final String baseUrl;
 public final Locator username,password,loginButton,error,errorClose,logo,menuButton,logoutLink;
 public LoginPage(Page page,String baseUrl){
  this.page=page;
  this.baseUrl=baseUrl;
  username=page.locator("[data-test='username']");
  password=page.locator("[data-test='password']");
  loginButton=page.locator("[data-test='login-button']");
  error=page.locator("[data-test='error']");
  errorClose=page.locator("[data-test='error-button']");
  logo=page.locator(".login_logo");
  menuButton=page.locator("#react-burger-menu-btn");
  logoutLink=page.locator("[data-test='logout-sidebar-link']");
 }
 public void open(){
  page.navigate(baseUrl+"/");
 }
 public void login(String user,String pass){
  username.fill(user);
  password.fill(pass);
  loginButton.click();
 }
 public void closeError(){
  errorClose.click();
 }
 public void logout(){
  menuButton.click();
  logoutLink.waitFor();
  logoutLink.click();
 }
 public Page page(){
  return page;
 }
}
