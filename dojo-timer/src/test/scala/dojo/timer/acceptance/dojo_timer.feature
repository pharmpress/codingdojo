Feature: Timer

  Scenario: Timer initial state
    Given a new timer
    Then the timer should display: 00:00:00

  Scenario: Timer 3 minutes
    Given a new timer
    When the start button is pushed
    And 3 minutes have passed
    And the stop button is pushed
    Then the timer should display: 00:03:00

  Scenario: Timer 5 minutes with 1 minute pause
    Given a new timer
    When the start button is pushed
    And 3 minutes have passed
    And the pause button is pushed
    And 1 minute have passed
    And the start button is pushed
    And 2 minutes have passed
    Then the timer should display: 00:05:00

  Scenario: Timer Countdown 5 minutes
    Given a new timer countdown
    When the time is set to: 00:05:00
    Then the timer should display: 00:05:00

  Scenario: Timer Countdown 5 minutes and stop at 2 minutes
    Given a new timer countdown
    When the time is set to: 00:05:00
    And 3 minutes have passed
    And the stop button is pushed
    Then the timer should display: 00:02:00

  Scenario: Timer Countdown with 3 participants
    Given a new timer countdown
    When the time is set to: 00:05:00
    And participants are:
      | john | jane | judy |
    And the start button is pushed
    And 3 minutes have passed
    Then the timer should display: john 00:02:00
    When 2 minutes have passed
    Then the timer should display: jane 00:05:00