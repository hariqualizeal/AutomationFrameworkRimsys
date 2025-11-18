package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ConfigReader;
import utilities.ExcelReader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

public class RegistrationsPage extends BaseScreenWeb {
    public RegistrationsPage(WebDriver driver) {
        super(driver);
    }

    private List<String> columnValues;
    Path excelPath = Paths.get(System.getProperty("user.dir"), "\\src\\test\\resources\\excelfiles\\" + ConfigReader.get("fileName"));
    ExcelReader excel = new ExcelReader(excelPath);
    Path excelPath2 = Paths.get(System.getProperty("user.dir"), "\\src\\test\\resources\\excelfiles\\" + ConfigReader.get("fileName2"));
    ExcelReader excel2 = new ExcelReader(excelPath2);

    String sheetName = ConfigReader.get("sheetName");


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
    By gridIcon = By.xpath("//span[@class='p-0.5 w-5 h-5 grid grid-cols-3 grid-rows-3 gap-0.5']");
    By allRegistrationsLink = By.xpath("//a[contains(text(),'All Registrations')]");
    By searchButtonRegistrations = By.xpath("//input[@id=\"search\"]");

    public By companyDiv(String companyName) {
        return By.xpath("//div[@title='" + companyName + "']");
    }

    public By getProductByName(String deviceName) {
        return By.xpath("//span[contains(text(),'" + deviceName + "')]");
    }

    public By getRegistrationNumber(String productName) {
        return By.xpath("//span[contains(text(),'" + productName + "')]/../..//span[contains(text(),'REG')]");
    }

    By getRegistrationName = By.xpath("//input[@aria-label='Name' and @type='text']");

    By getOwnerName = By.xpath("//label[text()='Owner']/../..//span[@class=\"flex items-center\"]");
    By getRegistrationStatus = By.xpath("//label[text()='Lifecycle Stage']/../..//span[@class=\"flex items-center\"]");
    By getStartDate = By.xpath("//label[text()='Start Date']/../..//input[@placeholder=\"YYYY-MM-DD\" and @type=\"text\"]");
    By getRegistrationsLink = By.xpath("//a[contains(text(),'Registrations')]");

    /**
     * Actions
     */
    public void launchURL() throws InterruptedException {
        String onlineUrl = ConfigReader.get("OnlineUrl");
        driver.manage().window().maximize();
        driver.get(onlineUrl);
        System.out.println("Navigated to URL: " + onlineUrl);
        Thread.sleep(5000);
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

    public void enterLoginReason() {
        waitAndSendKeys(loginReasonInputBox, "test");
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
        if (driver.findElement(searchButtonRegistrations).getAttribute("value").equals(searchText)) {
            System.out.println("Search text entered: " + searchText);
        } else {
            System.out.println("Failed to enter search text: " + searchText);
        }
    }

    public boolean verifyProduct(String productName) {
        if (waitAndVerify(getProductByName(productName))) {
            System.out.println("Product found: " + productName);
            return true;
        } else {
            System.out.println("Product not found: " + productName);
            return false;
        }
    }

    protected boolean waitAndVerifyProduct(By by) {
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(30));
        System.out.println("Verifying visibility of element: '" + by + "'");
        try {
            return wait2.until(ExpectedConditions.visibilityOfElementLocated(by)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clearsearchBox() {
        waitAndVerify(searchButtonRegistrations);
        driver.findElement(searchButtonRegistrations).clear();
    }

    public void searchAndVerifySpecificData() throws IOException, InterruptedException {
        int totalRows = excel.getTotalRows(sheetName);
        for (int r = 1; r <= 10; r++) {
            String productNameValue = excel.getCellValue(sheetName, "Product Name", r);
            System.out.println(productNameValue);
            enterSearchInRegistrations(productNameValue);
            boolean ok = verifyProduct(productNameValue);
            if (ok) {
                excel2.markCellGreen(sheetName, "Product Name", r);
                waitAndClick(getRegistrationNumber(productNameValue));
                waitAndVerify(getRegistrationName);
                validateRegistrationName(r);
                validateOwnerName(r);
                validateRegistrationStatus(r);
                validateStartDate(r);
                waitAndClick(getRegistrationsLink);
                clearsearchBox();
            } else {
                excel2.markCellRed(sheetName, "Product Name", r);
                clearsearchBox();
            }
        }
    }

    public void validateRegistrationName(int r) throws IOException {
        String registrationNameValue = excel.getCellValue(sheetName, "Registration Name*", r);
        if (driver.findElement(getRegistrationName).getAttribute("value").equals(registrationNameValue)) {
            System.out.println("Registration Name: '" + registrationNameValue + "' matches for product");
            excel2.markCellGreen(sheetName, "Registration Name*", r);
        } else {
            System.out.println("Registration Name: '" + registrationNameValue + "' doesn't matches for product");
            excel2.markCellRed(sheetName, "Registration Name*", r);
        }
    }

    public void validateOwnerName(int r) throws IOException {
        String ownerNameValue = excel.getCellValue(sheetName, "Owner*", r);
        if (driver.findElement(getOwnerName).getText().equals(ownerNameValue)) {
            System.out.println("Owner Name: '" + ownerNameValue + "' matches for product");
            excel2.markCellGreen(sheetName, "Owner*", r);
        } else {
            System.out.println("Owner Name: '" + ownerNameValue + "' doesnt matches for product");
            excel2.markCellRed(sheetName, "Owner*", r);
        }
    }

    public void validateRegistrationStatus(int r) throws IOException {
        String registrationStatus = excel.getCellValue(sheetName, "Registration Status", r);
        if (driver.findElement(getRegistrationStatus).getText().equals(registrationStatus)) {
            System.out.println("Owner Name: '" + registrationStatus + "' matches for product");
            excel2.markCellGreen(sheetName, "Registration Status", r);
        } else {
            System.out.println("Owner Name: '" + registrationStatus + "' doesnt matches for product");
            excel2.markCellRed(sheetName, "Registration Status", r);
        }
    }

    public void validateStartDate(int r) throws IOException {
        String startDate = excel.getCellValue(sheetName, "Start Date (YYYY-MM-DD)*", r);
        if (driver.findElement(getStartDate).getAttribute("value").equals(startDate)) {
            System.out.println("start date: '" + startDate + "' matches for product");
            excel2.markCellGreen(sheetName, "Start Date (YYYY-MM-DD)*", r);
        } else {
            System.out.println("start date: '" + startDate + "' doesn't matches for product");
            excel2.markCellRed(sheetName, "Start Date (YYYY-MM-DD)*", r);
        }
    }
}