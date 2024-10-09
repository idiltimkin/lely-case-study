package selenium.utilities;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static selenium.pages.BasePage.DEFAULT_DURATION;

public class AssertionHelper {

    public static void assertElementExists(WebElement element) {
        assertElementExists(element, DEFAULT_DURATION);
    }

    public static void assertElementExists(WebElement element, Duration duration) {
        var wait = new WebDriverWait(DriverManager.getDriver(), duration);
        wait.until(ExpectedConditions.visibilityOf(element));
        Assert.assertNotNull(element, "Element does not exist");
    }

    public static void assertTextEquals(WebElement element, String expected){
        assertTextEquals(element, expected, DEFAULT_DURATION);
    }

    public static void assertTextEquals(WebElement element, String expected, Duration duration){
        var wait = new WebDriverWait(DriverManager.getDriver(), duration);
        wait.until (ExpectedConditions.visibilityOf(element));
        Assert.assertEquals(element.getText(),expected);
    }
}
