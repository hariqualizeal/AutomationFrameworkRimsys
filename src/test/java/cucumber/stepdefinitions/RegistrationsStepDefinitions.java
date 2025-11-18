package cucumber.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utilities.ThreadLocalDriver;

import java.io.IOException;

public class RegistrationsStepDefinitions extends BaseSteps{

    @Before
    public void setupLoginSteps() {
            setupScreensWebLocal(ThreadLocalDriver.getWebDriverThreadLocal());
    }

    @Given("user is navigated to registration page")
    public void userIsNavigatedToRegistrationPage() throws InterruptedException {
        registrations.launchURL();
        Thread.sleep(5000);
        registrations.clickLoginButton();
        registrations.enterEmail("santosh.mangalapalli@rimsys.io");
        registrations.clickNextButton();
        registrations.enterPassword("@Pple1726");
        registrations.clickSignInButton();
        registrations.clickYesButton();
        registrations.clickCompany("Philips");
        registrations.clickSearchButton();
        registrations.clickAuthTab();
        registrations.enterLoginReason("test");
        registrations.clickAdministratorButton();
        registrations.clickGridIcon();
        registrations.clickAllRegistrationsLink();
    }

    @Then("user validates the registration details")
    public void userValidatesTheRegistrationDetails() throws IOException, InterruptedException {
        registrations.searchAndVerifySpecificData();
    }
}