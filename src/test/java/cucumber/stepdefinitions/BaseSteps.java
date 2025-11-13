package cucumber.stepdefinitions;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.*;

/**
 * this class is used to initialize page classes with driver.
 */
public class BaseSteps {
    protected DataValidationPage dataValidationPage;

    public void setupScreensWebLocal(WebDriver driver) {
        dataValidationPage = new DataValidationPage(driver);
    }
}