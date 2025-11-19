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
        registrationsPage.clickCompany(ConfigReader.get("companyName"));
        registrationsPage.clickSearchButton();
        registrationsPage.clickAuthTab();
        registrationsPage.enterLoginReason();
        registrationsPage.clickAdministratorButton();
        registrationsPage.clickGridIcon();
        registrationsPage.clickAllRegistrationsLink();
    }

    @Then("user validates the registration details")
    public void userValidatesTheRegistrationDetails() throws IOException, InterruptedException {
        registrationsPage.copyFile();
        Path inputFilePath = Paths.get(System.getProperty("user.dir"), "\\src\\test\\resources\\excelfiles\\" + ConfigReader.get("inputFileName") + ".xlsx");
        Path outputFilePath = Paths.get(System.getProperty("user.dir"), "\\src\\test\\resources\\excelfiles\\" + ConfigReader.get("outputFileName1") + ".xlsx");
        String sheetName = ConfigReader.get("sheetName");
        registrationsPage.searchAndVerifySpecificData(inputFilePath, outputFilePath, sheetName);
    }
}