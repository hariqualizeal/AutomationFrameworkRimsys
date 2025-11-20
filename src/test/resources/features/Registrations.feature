Feature: Validate registrations

  @Registrations1
  Scenario Outline: Validate registrations
    Given user is navigated to registration page
    Then user validates the registration details "<from row>" "<to row>"
    Examples:
      | from row | to row |
      | 1        | 5     |

  @Registrations2
  Scenario Outline: Validate registrations
    Given user is navigated to registration page
    Then user validates the registration details "<from row>" "<to row>"
    Examples:
      | from row | to row |
      | 6        | 10     |

  @Registrations3
  Scenario Outline: Validate registrations
    Given user is navigated to registration page
    Then user validates the registration details "<from row>" "<to row>"
    Examples:
      | from row | to row |
      | 21        | 30     |

  @Registrations4
  Scenario Outline: Validate registrations
    Given user is navigated to registration page
    Then user validates the registration details "<from row>" "<to row>"
    Examples:
      | from row | to row |
      | 31        | 40     |

  @Registrations5
  Scenario Outline: Validate registrations
    Given user is navigated to registration page
    Then user validates the registration details "<from row>" "<to row>"
    Examples:
      | from row | to row |
      | 41        | 50     |
