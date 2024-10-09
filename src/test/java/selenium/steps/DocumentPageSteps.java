package selenium.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import selenium.pages.DocumentsPage;
import selenium.utilities.DriverManager;

import java.util.List;
import java.util.Map;

public class DocumentPageSteps {
    DocumentsPage technicalDocumentsPage = new DocumentsPage();

    @Given("the user is on the Technical documents page of the Lely website")
    public void the_user_is_on_the_technical_documents_page_of_the_lely_website() {
        var driver = DriverManager.getDriver();
        driver.manage().window().maximize();
        driver.get("https://www.lely.com/techdocs/");
    }

    @Given("the dropdown menu is visible")
    public void the_dropdown_menu_is_visible() {
        technicalDocumentsPage.acceptCookies();
    }

    @When("the user clicks on the dropdown menu")
    public void the_user_clicks_on_the_dropdown_menu() {
        technicalDocumentsPage.clickDocumentDropdown();
    }

    @When("the user enters {string} the selected word into the textarea")
    public void the_user_enters_the_selected_word_into_the_textarea(String keyword) {
        technicalDocumentsPage.enterKeywordForSearch(keyword);
    }


    @Then("the following results should be shown")
    public void the_following_results_should_be_shown(List<Map<String, String>> expectedResults) {
        technicalDocumentsPage.verifySearchResults(expectedResults);
    }

    @Then("verify links are opening in new tab")
    public void verify_links_are_opening_in_new_tab() {
        technicalDocumentsPage.assertLinksTargetIsBlank();
    }

    @Then("verify files are downloaded")
    public void verify_files_are_downloaded(List<String> fileNames) throws Exception {
        technicalDocumentsPage.checkDownloads(fileNames);
    }
}
