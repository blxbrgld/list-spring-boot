@items
Feature: Endpoints related to items

  Scenario: Request for an id that does not exist
    When request item Java for dummies by id
    Then the http response status code is 404

  Scenario: Request for an id that exists
    When request item From Her To Eternity by id
    Then the http response status code is 200
    And the response contains key titleEng with value From Her To Eternity
    And the response contains key category.title with value Popular Music
    And the response list contains entry with key artists.artist.title and value Nick Cave & The Bad Seeds
    And the response list contains entry with key artists.activity.title and value Musician

  @wip
  Scenario: in progress
    Given category with title Applications exists
    And activity with title Developer exists
    And comment with title Spring applications exists
    And the following items exists
      | title            | category         | year | artist    | activity    | comment             |
      | List application | Applications     | 2019 | blxbrgld  | Developer   | Spring applications |