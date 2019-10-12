@items
Feature: Endpoints related to items

  Scenario: Request for an id that does not exist
    When request item From Him To Eternity by id
    Then the http response status code is 404

  Scenario: Request for an id that exists
    When request item From Her To Eternity by id
    Then the http response status code is 200
    And the response contains key titleEng with value From Her To Eternity
    And the response contains key category.title with value Popular Music
    And the response list contains entry with key artists.artist.title and value Nick Cave & The Bad Seeds
    And the response list contains entry with key artists.activity.title and value Musician

  @wip
  Scenario: Request for items list

  Scenario: Request to create item with a category that does not exist
    Given activity with title Developer exists
    And comment with title Spring Applications exists
    When request to create item
      | title            | category         | year | artist    | activity    | comment             |
      | List application | Applications     | 2019 | blxbrgld  | Developer   | Spring Applications |
    Then the http response status code is 400

  Scenario: Request to create item with invalid year
    Given category with title Applications exists
    And activity with title Developer exists
    And comment with title Spring Applications exists
    When request to create item
      | title            | category     | year | artist   | activity  | comment             |
      | List application | Applications | 1800 | blxbrgld | Developer | Spring Applications |
    Then the http response status code is 400

  Scenario: Request to create item with an artist having a not existing activity
    Given category with title Applications exists
    And comment with title Spring Applications exists
    When request to create item
      | title            | category     | year | artist   | activity  | comment             |
      | List application | Applications | 2019 | blxbrgld | Developer | Spring Applications |
    Then the http response status code is 400

  Scenario: Request to create item with a comment that does not exist
    Given category with title Applications exists
    And activity with title Developer exists
    When request to create item
      | title            | category     | year | artist   | activity  | comment             |
      | List application | Applications | 2019 | blxbrgld | Developer | Spring Applications |
    Then the http response status code is 400

  Scenario: Request to create item
    Given category with title Applications exists
    And activity with title Developer exists
    And comment with title Spring Applications exists
    When request to create item
      | title            | category     | year | artist    | activity  | comment             |
      | List application | Applications | 2019 | blxbrgld  | Developer | Spring Applications |
    Then the http response status code is 201
    When request item List application by id
    Then the http response status code is 200
    And the response contains key titleEng with value List application
    And the response contains key category.title with value Applications
    And the response list contains entry with key artists.artist.title and value blxbrgld
    And the response list contains entry with key artists.activity.title and value Developer
    And the response list contains entry with key comments.comment.title and value Spring Applications

  Scenario: Request to update item's category to a not existing one
    Given category with title Applications exists
    And activity with title Developer exists
    And comment with title Spring Applications exists
    And the following items exists
      | title            | category     | year | artist    | activity  | comment             |
      | List application | Applications | 2019 | blxbrgld  | Developer | Spring Applications |
    When request to update item with title List application to
      | title            | category         | year | artist   | activity  | comment             |
      | List application | Web Applications | 2019 | blxbrgld | Developer | Spring Applications |
    Then the http response status code is 400

  Scenario: Request to update item's year to a not valid value
    Given category with title Applications exists
    And activity with title Developer exists
    And comment with title Spring Applications exists
    And the following items exists
      | title            | category     | year | artist    | activity  | comment             |
      | List application | Applications | 2019 | blxbrgld  | Developer | Spring Applications |
    When request to update item with title List application to
      | title            | category     | year | artist   | activity  | comment             |
      | List application | Applications | 1800 | blxbrgld | Developer | Spring Applications |
    Then the http response status code is 400

  Scenario: Request to update item with an artist activity that does not exist
    Given category with title Applications exists
    And activity with title Developer exists
    And comment with title Spring Applications exists
    And the following items exists
      | title            | category     | year | artist    | activity  | comment             |
      | List application | Applications | 2019 | blxbrgld  | Developer | Spring Applications |
    When request to update item with title List application to
      | title            | category     | year | artist   | activity      | comment             |
      | List application | Applications | 2019 | blxbrgld | Web Developer | Spring Applications |
    Then the http response status code is 400

  Scenario: Request to update item's comment to a not existing one
    Given category with title Applications exists
    And activity with title Developer exists
    And comment with title Spring Applications exists
    And the following items exists
      | title            | category     | year | artist    | activity  | comment             |
      | List application | Applications | 2019 | blxbrgld  | Developer | Spring Applications |
    When request to update item with title List application to
      | title            | category     | year | artist   | activity  | comment                 |
      | List application | Applications | 2019 | blxbrgld | Developer | Spring Web Applications |
    Then the http response status code is 400

  Scenario: Request to update item
    Given category with title Applications exists
    And category with title Web Applications exists
    And activity with title Developer exists
    And activity with title Web Developer exists
    And comment with title Spring Applications exists
    And comment with title Spring Web Applications exists
    And the following items exists
      | title            | category     | year | artist    | activity  | comment             |
      | List application | Applications | 2019 | blxbrgld  | Developer | Spring Applications |
    When request to update item with title List application to
      | title           | category         | year | artist        | activity      | comment                 |
      | Web application | Web Applications | 2020 | npapadopoulos | Web Developer | Spring Web Applications |
    Then the http response status code is 204
    When request item Web application by id
    Then the http response status code is 200
    And the response contains key titleEng with value Web application
    And the response contains key category.title with value Web Applications
    # Ensure that the new artist/activity pair created and the old one deleted
    And the response list contains entry with key artists.artist.title and value npapadopoulos
    And the response list contains entry with key artists.activity.title and value Web Developer
    And the response list does not contain entry with key artists.artist.title and value blxbrgld
    And the response list does not contain entry with key artists.activity.title and value Developer
    # Ensure that the new comment created and the old one deleted
    And the response list contains entry with key comments.comment.title and value Spring Web Applications
    And the response list does not contain entry with key comments.comment.title and value Spring Applications

  @wip
  Scenario: Request to delete item that does not exist

  @wip
  Scenario: Request to delete item