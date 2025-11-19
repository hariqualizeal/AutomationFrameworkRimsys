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

public class RegistrationsPage extends BaseScreenWeb {
    public RegistrationsPage(WebDriver driver) {
        super(driver);
    }

    ExcelReader excelReader = new ExcelReader();

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

    public void searchAndVerifySpecificData(Path inputFilePath, Path outputFilePath, String sheetName) throws IOException, InterruptedException {
        int totalRows = excelReader.getTotalRows(sheetName, inputFilePath);
        for (int r = 1; r <= 3; r++) {
            String productNameValue = excelReader.getCellValue(sheetName, "Product Name", r, inputFilePath);
            System.out.println(productNameValue);
            enterSearchInRegistrations(productNameValue);
            boolean ok = verifyProduct(productNameValue);
            if (ok) {
                excelReader.markCellGreen(sheetName, "Product Name", r, outputFilePath);
                waitAndClick(getRegistrationNumber(productNameValue));
                waitAndVerify(getRegistrationName);
                validateRegistrationName(r, inputFilePath, outputFilePath, sheetName);
                validateOwnerName(r, inputFilePath, outputFilePath, sheetName);
                validateRegistrationStatus(r, inputFilePath, outputFilePath, sheetName);
                validateStartDate(r, inputFilePath, outputFilePath, sheetName);
                waitAndClick(getRegistrationsLink);
                clearsearchBox();
            } else {
                excelReader.markCellRed(sheetName, "Product Name", r, outputFilePath);
                clearsearchBox();
            }
        }
    }

    public void validateRegistrationName(int r, Path inputFilePath, Path outputFilePath1, String sheetName) throws IOException {
        String registrationNameValue = excelReader.getCellValue(sheetName, "Registration Name*", r, inputFilePath);
        if (driver.findElement(getRegistrationName).getAttribute("value").equals(registrationNameValue)) {
            System.out.println("Registration Name: '" + registrationNameValue + "' matches for product");
            excelReader.markCellGreen(sheetName, "Registration Name*", r, outputFilePath1);
        } else {
            System.out.println("Registration Name: '" + registrationNameValue + "' doesn't matches for product");
            excelReader.markCellRed(sheetName, "Registration Name*", r, outputFilePath1);
        }
    }

    public void validateOwnerName(int r, Path inputFilePath, Path outputFilePath1, String sheetName) throws IOException {
        String ownerNameValue = excelReader.getCellValue(sheetName, "Owner*", r, inputFilePath);
        if (driver.findElement(getOwnerName).getText().equals(ownerNameValue)) {
            System.out.println("Owner Name: '" + ownerNameValue + "' matches for product");
            excelReader.markCellGreen(sheetName, "Owner*", r, outputFilePath1);
        } else {
            System.out.println("Owner Name: '" + ownerNameValue + "' doesnt matches for product");
            excelReader.markCellRed(sheetName, "Owner*", r, outputFilePath1);
        }
    }

    public void validateRegistrationStatus(int r, Path inputFilePath, Path outputFilePath1, String sheetName) throws IOException {
        String registrationStatus = excelReader.getCellValue(sheetName, "Registration Status", r, inputFilePath);
        if (driver.findElement(getRegistrationStatus).getText().equals(registrationStatus)) {
            System.out.println("Owner Name: '" + registrationStatus + "' matches for product");
            excelReader.markCellGreen(sheetName, "Registration Status", r, outputFilePath1);
        } else {
            System.out.println("Owner Name: '" + registrationStatus + "' doesnt matches for product");
            excelReader.markCellRed(sheetName, "Registration Status", r, outputFilePath1);
        }
    }

    public void validateStartDate(int r, Path inputFilePath, Path outputFilePath1, String sheetName) throws IOException {
        String startDate = excelReader.getCellValue(sheetName, "Start Date (YYYY-MM-DD)*", r, inputFilePath);
        if (driver.findElement(getStartDate).getAttribute("value").equals(startDate)) {
            System.out.println("start date: '" + startDate + "' matches for product");
            excelReader.markCellGreen(sheetName, "Start Date (YYYY-MM-DD)*", r, outputFilePath1);
        } else {
            System.out.println("start date: '" + startDate + "' doesn't matches for product");
            excelReader.markCellRed(sheetName, "Start Date (YYYY-MM-DD)*", r, outputFilePath1);
        }
    }

    public void copyFile() throws IOException {
        ExcelReader.copyExcelFile(
                Paths.get(System.getProperty("user.dir"), "\\src\\test\\resources\\excelfiles\\" + ConfigReader.get("inputFileName") + ".xlsx"),
                Paths.get(System.getProperty("user.dir"), "\\src\\test\\resources\\excelfiles\\" + ConfigReader.get("outputFileName1") + ".xlsx")
        );
        System.out.println("Copied input file to output file successfully.");
    }
}