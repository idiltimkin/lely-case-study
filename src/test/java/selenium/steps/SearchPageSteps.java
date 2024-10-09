package selenium.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import selenium.pages.SearchPage;
import selenium.utilities.DriverManager;

public class SearchPageSteps {

    SearchPage searchPage = new SearchPage();

    @Given("the user navigates to the Lely English website")
    public void the_user_navigates_to_the_lely_english_website() {
        var driver = DriverManager.getDriver();
        driver.manage().window().maximize();
        driver.get("https://www.lely.com/en");
    }

    @Given("the user accepts cookies")
    public void the_user_accepts_cookies() {
        searchPage.acceptCookies();
    }

    @When("the user clicks the search button")
    public void the_user_clicks_the_search_button() {
        searchPage.clickSearchButton();
    }

    @Then("the user verifies that the search text area exists")
    public void the_user_verifies_that_the_search_text_area_exists() {
        searchPage.verifySearchInputFieldExists();
    }

    @When("the user types {string} into the search text area")
    public void the_user_types_into_the_search_text_area(String text) {
        searchPage.typeSearchInputField(text);
    }

    @When("the user clicks the search submit button")
    public void the_user_clicks_the_search_submit_button() {
        searchPage.clickSearchSubmitButton();
    }

    @Then("the user should see the results listed")
    public void the_user_should_see_the_results_listed() {
        searchPage.verifySearchResultNotEmpty();
    }

    @Then("the user should verify that all results contain {string}")
    public void the_user_should_verify_that_all_results_contain(String keyword) {
        searchPage.verifyAllResultsContainKeyword(keyword);
    }
}