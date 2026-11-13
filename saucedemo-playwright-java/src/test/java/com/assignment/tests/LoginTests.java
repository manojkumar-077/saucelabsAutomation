package com.assignment.tests;
import com.assignment.base.BaseTest; import com.assignment.data.Users; import org.testng.annotations.Test; import static org.testng.Assert.*;
public class LoginTests extends BaseTest {

 @Test
 public void loginPageShowsLogoAndForm(){
  login.open();
  assertEquals(login.logo.textContent().trim(),"Swag Labs");
  assertTrue(login.username.isVisible());
  assertTrue(login.password.isVisible());
  assertTrue(login.loginButton.isVisible());
 }

 @Test
 public void passwordIsMasked(){
  login.open();
  login.password.fill(Users.PASSWORD);
  assertEquals(login.password.getAttribute("type"),"password");
 }

 @Test
 public void standardUserLogin(){
  login.open();
  login.login(Users.profile("standard").username(),Users.PASSWORD);
  assertTrue(page.url().contains("inventory.html"));
 }

 @Test
 public void wrongUsernameAndPassword(){
  login.open();
  var u=Users.profile("invalid");
  login.login(u.username(),u.password());
  assertTrue(login.error.textContent().contains("Username and password do not match"));
 }

 @Test
 public void wrongUsername(){
  login.open();
  var u=Users.profile("badUser");
  login.login(u.username(),u.password());
  assertTrue(login.error.textContent().contains("Username and password do not match"));
 }

 @Test
 public void wrongPassword(){
  login.open();
  var u=Users.profile("badPass");
  login.login(u.username(),u.password());
  assertTrue(login.error.textContent().contains("Username and password do not match"));
 }

 @Test
 public void emptyUsernameAndPassword(){
  login.open();
  login.login("","");
  assertTrue(login.error.textContent().contains("Username is required"));
 }

 @Test
 public void emptyUsername(){
  login.open();
  login.login("",Users.PASSWORD);
  assertTrue(login.error.textContent().contains("Username is required"));
 }

 @Test
 public void emptyPassword(){
  login.open();
  login.login(Users.profile("standard").username(),"");
  assertTrue(login.error.textContent().contains("Password is required"));
 }

 @Test
 public void lockedOutUser(){
  login.open();
  var u=Users.profile("locked");
  login.login(u.username(),u.password());
  assertTrue(login.error.textContent().toLowerCase().contains("locked out"));
 }

 @Test
 public void closeErrorMessage(){
  login.open();
  login.login("invalid_user","wrong_password");
  assertTrue(login.error.isVisible());
  login.closeError();
  assertFalse(login.error.isVisible());
 }

 @Test
 public void logoutAndCannotReopenInventory(){
  login.open();
  login.login(Users.profile("standard").username(),Users.PASSWORD);
  assertTrue(page.url().contains("inventory.html"));
  login.logout();
  assertTrue(page.url().equals("https://www.saucedemo.com/")||page.url().equals("https://www.saucedemo.com"));
  page.navigate(baseUrl+"/inventory.html");
  assertTrue(login.loginButton.isVisible());
 }

}
