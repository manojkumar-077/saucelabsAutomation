package com.assignment.base;
import com.microsoft.playwright.*;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.nio.file.*;
import java.util.*;
import com.assignment.data.Users;
import com.assignment.pages.*;

public abstract class BaseTest {
 protected Playwright playwright;
 protected Browser browser;
 protected BrowserContext context;
 protected Page page;

 protected LoginPage login;
 protected InventoryPage inventory;
 protected ItemPage itemPage;
 protected CartPage cart;
 protected CheckoutPage checkout;
 protected String baseUrl="https://www.saucedemo.com";

 @BeforeMethod(alwaysRun=true)
 @Parameters({"userProfile"})
 public void setUp(@Optional("standard") String suiteProfile) {
   String envProfile=System.getProperty("userProfile", suiteProfile);
   try { Properties p=new Properties();
       try(var in=getClass().getResourceAsStream("/config.properties")){
           if(in!=null)p.load(in);
       }
       baseUrl=p.getProperty("baseUrl",baseUrl);
   }
   catch(Exception ignored){}
   boolean headless=Boolean.parseBoolean(System.getProperty("headless", "true"));
   playwright=Playwright.create();
   BrowserType.LaunchOptions options=new BrowserType.LaunchOptions().setHeadless(headless);
   int slowMo=Integer.parseInt(System.getProperty("slowMoMs","0"));
   if(slowMo>0) options.setSlowMo(slowMo);
   String browserName=System.getProperty("browser","chromium");
   browser=switch(browserName.toLowerCase()){
       case "firefox"->playwright.firefox().launch(options);
       case "webkit"->playwright.webkit().launch(options);
       default->playwright.chromium().launch(options);
   };
   context=browser.newContext(new Browser.NewContextOptions().setViewportSize(1440,900));
   page=context.newPage();
   page.setDefaultTimeout(10000);
   page.setDefaultNavigationTimeout(15000);
   login=new LoginPage(page,baseUrl);
   inventory=new InventoryPage(page);
   itemPage=new ItemPage(page);
   cart=new CartPage(page);
   checkout=new CheckoutPage(page);
 }
 protected void loginAsStandard(){
     login.open();
     login.login(Users.profile("standard").username(), Users.PASSWORD);
 }
 protected void startShopAsConfiguredUser(){
     String profile=System.getProperty("userProfile","standard");
     var user=Users.profile(profile);
     login.open();
     login.login(user.username(),user.password());
 }

 @AfterMethod(alwaysRun=true)
 public void tearDown(ITestResult result) {
   try { if(result!=null && !result.isSuccess() && page!=null && !page.isClosed()) {
       Path dir=Path.of("test-results","screenshots");
       Files.createDirectories(dir);
       String name=result.getMethod().getMethodName().replaceAll("[^a-zA-Z0-9_-]","_");
       page.screenshot(new Page.ScreenshotOptions().setPath(dir.resolve(name+"-"+System.currentTimeMillis()+".png")).setFullPage(true));
        }
   }
   catch(Exception e){
       System.err.println("Could not capture failure screenshot: "+e.getMessage());
   }
   if(context!=null)context.close();
   if(browser!=null)browser.close();
   if(playwright!=null)playwright.close();
 }
}
