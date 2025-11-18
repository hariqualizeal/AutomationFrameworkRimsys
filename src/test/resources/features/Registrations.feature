Feature: Validate registrations

  @excel2
  Scenario Outline: Validate registrations
    Given user is navigated to registration page
    Then user validates the registration details

    Examples:
      | column name  |row number|
      | Product Catalog Number |          |