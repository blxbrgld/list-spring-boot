@subtitles
Feature: Endpoints related to subtitles

  Greek Subtitles, English Subtitles and No Subtitles are considered values that surely exist.

  Scenario: Request for an id that does not exist
    When request subtitles Danish Subtitles by id
    Then the http response status code is 404

  Scenario: Request for an id that exists
    When request subtitles Greek Subtitles by id
    Then the http response status code is 200
    And the response contains key title with value Greek Subtitles

  Scenario: Request for subtitles list
    When subtitles list is requested
    Then the http response status code is 200
    And title contains in any order No Subtitles,Greek Subtitles,English Subtitles

  Scenario: Request to create subtitle with a title that already exists
    Given subtitles with title Danish Subtitles exist
    When request to create subtitles with title Danish Subtitles
    Then the http response status code is 400

  Scenario: Request to create subtitle
    When request to create subtitles with title Danish Subtitles
    Then the http response status code is 201
    When subtitles list is requested
    Then the http response status code is 200
    And the response list contains entry with key title and value Danish Subtitles

  Scenario: Request to update a subtitle that does not exist
    When request to update subtitles with title Danish Subtitles to Spanish Subtitles
    Then the http response status code is 404

  Scenario: Request to update a subtitle with a title that already exists
    Given subtitles with title Danish Subtitles exist
    And subtitles with title Spanish Subtitles exist
    When request to update subtitles with title Danish Subtitles to Spanish Subtitles
    Then the http response status code is 400

  Scenario: Request to update a subtitle
    Given subtitles with title Danish Subtitles exist
    When request to update subtitles with title Danish Subtitles to Spanish Subtitles
    Then the http response status code is 204
    When subtitles list is requested
    Then the http response status code is 200
    And the response list contains entry with key title and value Spanish Subtitles

  Scenario: Request to delete a subtitle that does not exist
    When request to delete subtitles with title Danish Subtitles
    Then the http response status code is 400

  @wip #TODO Testing
  Scenario: Request to delete a subtitle that is related with some items

  Scenario: Request to delete a subtitle
    Given subtitles with title Danish Subtitles exist
    When request to delete subtitles with title Danish Subtitles
    Then the http response status code is 204
    When subtitles list is requested
    Then the http response status code is 200
    And the response list does not contain entry with key title and value Danish Subtitles