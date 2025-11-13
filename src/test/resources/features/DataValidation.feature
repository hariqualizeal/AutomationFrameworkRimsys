Feature: Validate registrations

  @excel
  Scenario Outline: Validate registrations
    Given user is navigated to registration page
    When user searches with "<column name>"
    Then data should be displayed correctly

    Examples:
      | column name  |
      | Product Name |