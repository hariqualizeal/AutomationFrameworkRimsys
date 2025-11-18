package cucumber.stepdefinitions;

import org.openqa.selenium.WebDriver;
import pages.*;

/**
 * this class is used to initialize page classes with driver.
 */
public class BaseSteps {
    protected Registrations registrations;

    public void setupScreensWebLocal(WebDriver driver) {
        registrations = new Registrations(driver);
    }
}