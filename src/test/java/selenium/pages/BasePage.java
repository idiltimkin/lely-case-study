package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.utilities.DriverManager;
import selenium.utilities.FileHelper;

import java.time.Duration;

public abstract class BasePage {
    public static Duration DEFAULT_DURATION = Duration.ofSeconds(1);
    public static Duration DOWNLOAD_DURATION = Duration.ofSeconds(10);

    protected By acceptCookieLocator = By.id("cookienotice-button-accept");

    public void click(WebElement clickElement) {
        var wait = new WebDriverWait(DriverManager.getDriver(), DEFAULT_DURATION);
        wait.until(ExpectedConditions.elementToBeClickable(clickElement));
        clickElement.click();
    }

    public void acceptCookies() {
        var button = getOrNull(acceptCookieLocator);
        if (button != null) {
            click(button);
        }
    }

    public WebElement getOrNull(By locator) {
        try {
            var wait = new WebDriverWait(DriverManager.getDriver(), DEFAULT_DURATION);
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException ignore) {
            return null;
        }
    }

    public Integer getTabCount() {
        return DriverManager.getDriver().getWindowHandles().size();
    }

    public void sendKeys(WebElement sendKeysElement, String value) {
        var wait = new WebDriverWait(DriverManager.getDriver(), DEFAULT_DURATION);
        wait.until(ExpectedConditions.visibilityOf(sendKeysElement));
        sendKeysElement.sendKeys(value);
    }

    public void verifyFileDownload(String fileName) {
        var wait = new WebDriverWait(DriverManager.getDriver(), DOWNLOAD_DURATION);
        wait.until(
                new ExpectedCondition<>() {
                    @Override
                    public Boolean apply(WebDriver driver) {
                        return FileHelper.isFileDownloaded(fileName);
                    }

                    @Override
                    public String toString() {
                        return String.format("File '%s' to be present in directory '%s'", fileName, FileHelper.DOWNLOAD_FOLDER);
                    }
                }
        );
    }

    public void selectElementFromDropdown(WebElement dropdown, String element) {
        var wait = new WebDriverWait(DriverManager.getDriver(), DEFAULT_DURATION);
        wait.until(ExpectedConditions.visibilityOf(dropdown));
        var slc = new Select(dropdown);
        slc.selectByVisibleText(element);
    }
}