Feature: Validate registrations

  @excel2
  Scenario Outline: Validate registrations
    Given user is navigated to registration page
    When user searches with "<column name>" "<row number>"
    Then data should be displayed correctly

    Examples:
      | column name  |row number|
      | Product Catalog Number |          |