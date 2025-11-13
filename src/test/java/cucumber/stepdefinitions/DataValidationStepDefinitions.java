package cucumber.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Reporter;
import utilities.ThreadLocalDriver;

import java.io.IOException;

public class DataValidationStepDefinitions extends BaseSteps{

    @Before
    public void setupLoginSteps() {
            setupScreensWebLocal(ThreadLocalDriver.getWebDriverThreadLocal());
    }

    @Given("user is navigated to registration page")
    public void userIsNavigatedToRegistrationPage() throws InterruptedException {
        dataValidationPage.launchURL();
        Thread.sleep(5000);
        dataValidationPage.clickLoginButton();
        dataValidationPage.enterEmail("santosh.mangalapalli@rimsys.io");
        dataValidationPage.clickNextButton();
        dataValidationPage.enterPassword("@Pple1726");
        dataValidationPage.clickSignInButton();
        dataValidationPage.clickYesButton();
        dataValidationPage.clickCompany("Philips");
        dataValidationPage.clickSearchButton();
        dataValidationPage.clickAuthTab();
        dataValidationPage.enterLoginReason("test");
        dataValidationPage.clickAdministratorButton();
        dataValidationPage.clickGridIcon();
        dataValidationPage.clickAllRegistrationsLink();
    }

    @When("user searches with {string}")
    public void userSearchesWith(String columnHeader) throws IOException, InterruptedException {
        dataValidationPage.searchAndVerifyProducts(columnHeader);
    }

    @Then("data should be displayed correctly")
    public void dataShouldBeDisplayedCorrectly() {
    }
}