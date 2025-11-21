package cucumber.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import utilities.ConfigReader;
import utilities.ThreadLocalDriver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RegistrationsStepDefinitions extends BaseSteps {

    @Before
    public void setupLoginSteps() {
        setupScreensWebLocal(ThreadLocalDriver.getWebDriverThreadLocal());
    }

    @Given("user is navigated to registration page")
    public void userIsNavigatedToRegistrationPage() throws InterruptedException {
        registrationsPage.launchURL();
        registrationsPage.clickLoginButton();
        registrationsPage.enterEmail(ConfigReader.get("email"));
        registrationsPage.clickNextButton();
        registrationsPage.enterPassword(ConfigReader.get("password"));
        registrationsPage.clickSignInButton();
        registrationsPage.clickYesButton();
//        Thread.sleep(60000);
        registrationsPage.clickCompany(ConfigReader.get("companyName"));
//        Thread.sleep(60000);
        registrationsPage.clickSearchButton();
//        Thread.sleep(60000);
        registrationsPage.clickAuthTab();
//        Thread.sleep(60000);
        registrationsPage.enterLoginReason();
        registrationsPage.clickAdministratorButton();
        registrationsPage.clickGridIcon();
        registrationsPage.clickAllRegistrationsLink();
    }

    @Then("user validates the registration details {string} {string}")
    public void userValidatesTheRegistrationDetails(String fromRowNumber, String toRowNumber) throws IOException, InterruptedException {
        String rows = fromRowNumber + "-" + toRowNumber;
        Path inputFilePath = Paths.get(System.getProperty("user.dir"), "\\src\\test\\resources\\excelfiles\\" + ConfigReader.get("inputFileName") + ".xlsx");
        Path outputFilePath = Paths.get(System.getProperty("user.dir"), "\\target\\excel-file-reports\\" + ConfigReader.get("inputFileName") + "Row" + rows + ".xlsx");
        registrationsPage.copyFile(inputFilePath, outputFilePath);
        String sheetName = ConfigReader.get("sheetName");
        registrationsPage.searchAndVerifySpecificData(inputFilePath, outputFilePath, sheetName, Integer.parseInt(fromRowNumber), Integer.parseInt(toRowNumber));
    }
}