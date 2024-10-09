package selenium.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;

public class DriverManager {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            var chromePref = new HashMap<>();
            chromePref.put("download.default_directory", FileHelper.DOWNLOAD_FOLDER);
            chromePref.put("webdriver.chrome.driver", "./drivers/chromedriver.exe");

            var options = new ChromeOptions();
            options.setExperimentalOption("prefs", chromePref);

            driver = new ChromeDriver(options);
        }
        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
