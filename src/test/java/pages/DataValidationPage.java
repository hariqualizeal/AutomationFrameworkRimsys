package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utilities.ConfigReader;
import utilities.ExcelUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

public class DataValidationPage extends BaseScreenWeb {
  public DataValidationPage(WebDriver driver) {super(driver);}
  private List<String> columnValues;

  /**
   * Web Elements
   */
  By loginButton = By.xpath("//img[@alt=\"Login with Azure\"]");
  By emailInputBox = By.xpath("//input[@type=\"email\"]");
  By nextButton = By.xpath("//input[@value=\"Next\"]");
  By passwordInputBox = By.xpath("//input[@type=\"password\"]");
  By signInButton = By.xpath("//input[@value='Sign in']");
  By yesButton = By.xpath("//input[@value=\"Yes\"]");
  By searchButton = By.xpath("(//button[@type=\"button\" and @class=\"table-action mr-3\"])[2]");
  By authTab = By.xpath("//span[text()='Auth']");
  By loginReasonInputBox = By.xpath("//input[@placeholder=\"I need to login because ...\"]");
  By adminstratorButton = By.xpath("(//span[@class=\"absolute inset-0\" and @aria-hidden=\"true\"])[1]");
  By gridIcon = By.xpath("//span[@class=\"p-0.5 w-5 h-5 grid grid-cols-3 grid-rows-3 gap-0.5\"]");
  By allRegistrationsLink = By.xpath("//a[contains(text(),'All Registrations')]");
  By searchButtonRegistrations = By.xpath("//input[@id=\"search\"]");
  public By companyDiv(String companyName) {
    return By.xpath("//div[@title='" + companyName + "']");
  }
  public By getProductByName(String deviceName) {
    return By.xpath("//span[contains(text(),'" + deviceName + "')]");
  }


  /**
   * Actions
   */
  public void launchURL() {
    String onlineUrl = ConfigReader.get("OnlineUrl");
    driver.manage().window().maximize();
    driver.get(onlineUrl);
    System.out.println("Navigated to URL: " + onlineUrl);
//      test.get().log(Status.INFO, "Home Page", MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshotAsBase64WebLocal()).build());
  }

  public void clickLoginButton() {
    waitAndClick(loginButton);
  }

  public void enterEmail(String email) {
    waitAndSendKeys(emailInputBox, email);
  }

  public void clickNextButton() {
    waitAndClick(nextButton);
  }

  public void enterPassword(String password) {
    waitAndSendKeys(passwordInputBox, password);
  }

  public void clickSignInButton() {
    waitAndClick(signInButton);
  }

  public void clickYesButton() {
    waitAndClick(yesButton);
  }

  public void clickCompany(String companyName) {
    waitAndClick(companyDiv(companyName));
  }

  public void clickSearchButton() {
    waitAndClick(searchButton);
  }

  public void clickAuthTab() {
    waitAndClick(authTab);
  }

  public void enterLoginReason(String reason) {
    waitAndSendKeys(loginReasonInputBox, reason);
  }

  public void clickAdministratorButton() {
    waitAndClick(adminstratorButton);
    switchToWindow();
  }

  public void clickGridIcon() {
    waitAndClick(gridIcon);
  }

  public void clickAllRegistrationsLink() {
    waitAndClick(allRegistrationsLink);
  }

  public void enterSearchInRegistrations(String searchText) throws InterruptedException {
    waitAndSendKeys(searchButtonRegistrations, searchText);
    if(driver.findElement(searchButtonRegistrations).getAttribute("value").equals(searchText)){
        System.out.println("Search text entered: " + searchText);
        } else {
        System.out.println("Failed to enter search text: " + searchText);
    }
  }

  public boolean verifyProduct(String productName) {
    if(waitAndVerifyProduct(getProductByName(productName))){
      System.out.println("Product found: " + productName);
      return true;
        } else {
      System.out.println("Product not found: " + productName);
      return false;
    }
  }

  protected boolean waitAndVerifyProduct(By by) {
    WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
    System.out.println("Verifying visibility of element: '" + by+ "'");
    try {
      return wait2.until(ExpectedConditions.visibilityOfElementLocated(by)).isDisplayed();
    } catch (Exception e){
      return false;
    }
  }

  public void clearsearchBox() {
    driver.findElement(searchButtonRegistrations).clear();
  }

  public void searchAndVerifyProducts(String columnHeader) throws IOException, InterruptedException {
    Path excelPath = Paths.get(System.getProperty("user.dir"),"\\src\\test\\resources\\excelfiles\\"+ ConfigReader.get("fileName"));
    ExcelUtil excel = new ExcelUtil(excelPath);
    columnValues = excel.getColumn(ConfigReader.get("sheetName"), columnHeader);
    Path excelPath2 = Paths.get(System.getProperty("user.dir"),"\\src\\test\\resources\\excelfiles\\"+ ConfigReader.get("fileName2"));
    ExcelUtil excel2 = new ExcelUtil(excelPath2);
    columnValues = excel2.getColumn(ConfigReader.get("sheetName"), columnHeader);

    int count=0;
    for (String c : columnValues){
      enterSearchInRegistrations(c);
      boolean ok = verifyProduct(c);
      if (ok) {
        excel2.markCellGreen(ConfigReader.get("sheetName"), columnHeader, c);
      } else {
        excel2.markCellRed(ConfigReader.get("sheetName"), columnHeader, c);
      }
      clearsearchBox();
      System.out.println(c);
      count++;
      if(count>5) break;
      System.out.println("Number of records searched so far: "+count);
    }
  }
}