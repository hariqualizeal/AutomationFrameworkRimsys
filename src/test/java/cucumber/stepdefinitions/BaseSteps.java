package cucumber.stepdefinitions;

import org.openqa.selenium.WebDriver;
import pages.*;

/**
 * this class is used to initialize page classes with driver.
 */
public class BaseSteps {
    protected RegistrationsPage registrationsPage;

    public void setupScreensWebLocal(WebDriver driver) {
        registrationsPage = new RegistrationsPage(driver);
    }
}