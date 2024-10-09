Feature: Lely English website search functionality

  Scenario: Go to Lely English website and test the search for an existing word
    Given the user navigates to the Lely English website
    And the user accepts cookies
    When the user clicks the search button
    Then the user verifies that the search text area exists
    When the user types "happy" into the search text area
    And the user clicks the search submit button
    Then the user should see the results listed
    And the user should verify that all results contain "happy"