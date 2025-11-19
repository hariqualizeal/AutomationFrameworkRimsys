Feature: Validate registrations

  @Registrations
  Scenario: Validate registrations
    Given user is navigated to registration page
    # in below then statement, pass row numbers and use it in file name by appending row numbers
    Then user validates the registration details
