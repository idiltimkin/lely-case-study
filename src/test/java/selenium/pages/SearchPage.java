package selenium.pages;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import selenium.utilities.DriverManager;
import selenium.utilities.SearchResult;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;
import static selenium.utilities.AssertionHelper.assertElementExists;

public class SearchPage extends BasePage {
    private static final Logger log = LoggerFactory.getLogger(SearchPage.class);

    @FindBy(xpath = "//div[@class='header-navigation-button' and @data-do='trigger-search']")
    private WebElement searchButton;

    @FindBy(xpath = "//input[@id='global-search']")
    private WebElement searchInputField;

    @FindBy(xpath = "//button[@class='button button-tertiary' and @type='submit']")
    private WebElement searchSubmitButton;

    @FindBy(xpath = "//ul[contains(@class, 'search-results-list')]/li")
    private List<WebElement> searchResults;

    private By nextPageLocator = By.xpath("//nav[contains(@class, 'search-pagination')]//li[contains(@class, 'page-next')]/a");

    public SearchPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    public void clickSearchButton() {
        click(searchButton);
    }

    public void verifySearchInputFieldExists() {
        assertElementExists(searchInputField);
    }

    public void typeSearchInputField(String text) {
        sendKeys(searchInputField, text);
    }

    public void clickSearchSubmitButton() {
        click(searchSubmitButton);
    }

    public void verifySearchResultNotEmpty() {
        assertFalse(searchResults.isEmpty());
    }

    public void verifyAllResultsContainKeyword(String keyword) {
        var notFoundResults = new ArrayList<SearchResult>();

        //TODO: Loop can be finite
        while (true) {
            var notFounds = verifyResultsContainKeyword(keyword);
            notFoundResults.addAll(notFounds);
            var nextLink = getOrNull(nextPageLocator);
            if (nextLink != null) {
                click(nextLink);
            } else {
                break;
            }
        }

        log.warn("--- Failed Search Results -----");
        for (SearchResult e : notFoundResults) {
            log.debug("Title : {}", e.getTitle());
            log.debug("Description : {}", e.getDescription());
            log.debug("------------------------------");
        }

        assertTrue(notFoundResults.isEmpty());
    }

    public ArrayList<SearchResult> verifyResultsContainKeyword(String keyword) {
        var notFoundResults = new ArrayList<SearchResult>();
        for (WebElement result : searchResults) {
            var title = result.findElement(By.xpath(".//h3")).getText();
            var description = result.findElement(By.xpath(".//p")).getText();

            if (!StringUtils.containsIgnoreCase(title, keyword) && !StringUtils.containsIgnoreCase(description, keyword)) {
                notFoundResults.add(new SearchResult(title, description));
            }
        }
        return notFoundResults;
    }
}
