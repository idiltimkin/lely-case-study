package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.utilities.DriverManager;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DocumentsPage extends BasePage {
    @FindBy(id = "select2-id_q-container")
    private WebElement documentDropdown;

    @FindBy(xpath = "//input[@class='select2-search__field']")
    private WebElement dropdownInputArea;

    @FindBy(xpath = "//section[@class='result-item']")
    private List<WebElement> searchResults;

    private final By downloadButtonLocator = By.xpath("(footer//a)[1]");
    private final By searchResultTitleLocator = By.xpath("header//h3[@class='result-item-title']");
    private final By viewDocumentLinkLocator = By.xpath("(footer//a)[2]");

    public DocumentsPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    public void clickDocumentDropdown() {
        click(documentDropdown);
    }

    public void enterKeywordForSearch(String keyword) {
        sendKeys(dropdownInputArea, keyword);
        dropdownInputArea.sendKeys(Keys.ENTER);
    }

    public void assertLinksTargetIsBlank() {
        var originalWindow = DriverManager.getDriver().getWindowHandle();
        var driver = DriverManager.getDriver();

        for (var searchResultElement : searchResults) {
            var viewButtonElement = searchResultElement.findElement(viewDocumentLinkLocator);
            assertEquals(viewButtonElement.getAttribute("target"), "_blank");

            click(viewButtonElement);
            driver.switchTo().window(originalWindow);
        }

        assertEquals(getTabCount(), searchResults.size() + 1);
    }

    public void checkDownloads(List<String> expectedFiles) throws Exception {
        for (var searchResultElement : searchResults) {
            var downloadButtonElement = searchResultElement.findElement(downloadButtonLocator);
            click(downloadButtonElement);
        }

        for (var file : expectedFiles) {
            verifyFileDownload(file);
        }
    }

    public void verifySearchResults(List<Map<String, String>> expectedResults) {
        assertEquals(searchResults.size(), expectedResults.size());

        for (int i = 0; i < expectedResults.size(); i++) {
            var searchResultElement = searchResults.get(i);
            var expectedFileName = expectedResults.get(i).get("fileName");
            var expectedTitle = expectedResults.get(i).get("title");

            var titleElement = searchResultElement.findElement(searchResultTitleLocator);
            var downloadButtonElement = searchResultElement.findElement(downloadButtonLocator);
            var viewButtonElement = searchResultElement.findElement(viewDocumentLinkLocator);

            assertEquals(titleElement.getText(), expectedTitle);
            assertTrue(downloadButtonElement.getAttribute("href").contains(expectedFileName));
            assertTrue(viewButtonElement.getAttribute("href").contains(expectedFileName));
        }
    }
}