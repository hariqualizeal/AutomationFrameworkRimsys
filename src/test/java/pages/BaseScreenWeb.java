package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ConfigReader;
import utilities.ThreadLocalDriver;

import java.time.Duration;

public class BaseScreenWeb {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BaseScreenWeb(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfigReader.get("waitTime"))));
    }

    protected void waitAndClick(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
        System.out.println("Clicked on element: '" + by+ "'");
    }

    protected void waitAndSendKeys(By by, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by)).sendKeys(text);
        System.out.println("Sent text '"+text+"' to element '" + by+ "'");
    }

    protected boolean waitAndVerify(By by) {
        System.out.println("Verifying visibility of element: '" + by+ "'");
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by)).isDisplayed();
        } catch (Exception e){
            return false;
        }
    }



    protected void switchToWindow() {
        String original = driver.getWindowHandle();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.getWindowHandles().size() > 1);
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(original)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        System.out.println("Title: " + driver.getTitle());
    }
}
