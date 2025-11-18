package cucumber.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import utilities.ThreadLocalDriver;

import java.io.IOException;

public class RegistrationsStepDefinitions extends BaseSteps{

    @Before
    public void setupLoginSteps() {
            setupScreensWebLocal(ThreadLocalDriver.getWebDriverThreadLocal());
    }

    @Given("user is navigated to registration page")
    public void userIsNavigatedToRegistrationPage() throws InterruptedException {
        registrationsPage.launchURL();
        Thread.sleep(5000);
        registrationsPage.clickLoginButton();
        registrationsPage.enterEmail("santosh.mangalapalli@rimsys.io");
        registrationsPage.clickNextButton();
        registrationsPage.enterPassword("@Pple1726");
        registrationsPage.clickSignInButton();
        registrationsPage.clickYesButton();
        registrationsPage.clickCompany("Philips");
        registrationsPage.clickSearchButton();
        registrationsPage.clickAuthTab();
        registrationsPage.enterLoginReason("test");
        registrationsPage.clickAdministratorButton();
        registrationsPage.clickGridIcon();
        registrationsPage.clickAllRegistrationsLink();
    }

    @Then("user validates the registration details")
    public void userValidatesTheRegistrationDetails() throws IOException, InterruptedException {
        registrationsPage.searchAndVerifySpecificData();
    }
}