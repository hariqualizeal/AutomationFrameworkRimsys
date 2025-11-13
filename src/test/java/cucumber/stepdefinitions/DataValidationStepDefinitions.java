package cucumber.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Reporter;
import utilities.ConfigReader;
import utilities.ExcelUtil;
import utilities.ThreadLocalDriver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DataValidationStepDefinitions extends BaseSteps{

    boolean mobile = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("WebOrMobile").equalsIgnoreCase("Mobile");
    boolean webCloud = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("WebOrMobile").equalsIgnoreCase("WebCloud");

    @Before
    public void setupLoginSteps() {
        if (mobile) {
            //mobile code - for both cloud/local
            setupScreensMobile(ThreadLocalDriver.getAppiumDriverThreadLocal());
        } else if (webCloud) {
            //web code - for cloud
            setupScreensWebCloud(ThreadLocalDriver.getRemoteWebDriverThreadLocal());
        } else {
            //web code - for local
            setupScreensWebLocal(ThreadLocalDriver.getWebDriverThreadLocal());
        }
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
    public void userSearchesWith(String columnHeader) throws IOException {
        dataValidationPage.searchAndVerifyProducts(columnHeader);
    }

    @Then("data should be displayed correctly")
    public void dataShouldBeDisplayedCorrectly() {
    }
}