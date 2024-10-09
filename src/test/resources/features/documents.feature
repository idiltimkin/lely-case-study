Feature: Verification of Document Viewing and Download for "Luna EUR"

  Scenario: User verifies document features for "Luna EUR"
    Given the user is on the Technical documents page of the Lely website
    And the user accepts cookies
    When the user clicks on the dropdown menu
    When the user enters "LUNA EUR" the selected word into the textarea
    And  the following results should be shown
      | title                     | fileName        |
      | LUNA EUR (Multi Language) | D-S006VT_-.pdf  |
      | LUNA EUR (Multi Language) | D-S032VT_-.pdf  |
    And verify links are opening in new tab
    And verify files are downloaded
      | D-S006VT_-.pdf  |
      | D-S032VT_-.pdf  |