Feature: Score calculation

  Scenario: Gutter Game
    Given a new bowling game
    When all of my balls are landing in the gutter
    Then my total score should be 0

  Scenario: All Strikes
    Given a new bowling game
    When all of my rolls are strikes
    Then my total score should be 300

  Scenario: Beginners game
    Given a new bowling game
    When I roll 2 and 7
    And I roll 3 and 4
    And I roll 8 times 1 and 1
    Then my total score should be 32

  Scenario: Another beginners game
    Given a new bowling game
    When I roll the following series:
      | 2 | 7 | 3 | 4 | 1 | 1 | 5 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 5 | 1 |
    Then my total score should be 40


  Scenario: Uncle Bobs game
    Given a new bowling game
    When I roll the following formatted series:
      |1 4|4 5|6 /|5 /|  X|0 1|7 /|6 /|  X|2 / 6|
    Then my formatted score should be
      |  5| 14| 29| 49| 60| 61| 77| 97|117|  133|

  @spare
  Scenario: One single spare
    Given a new bowling game
    When I roll the following series:
      | 3 | 7 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 |
    Then my total score should be 29

  @spare
  Scenario: All Spares
    Given a new bowling game
    When I roll 10 times 1 and 9
    And I roll 1
    Then my total score should be 110

