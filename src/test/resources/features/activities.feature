@activities
Feature: Endpoints related to activities

  Musician, Conductor, Director, Actor, Author are considered values that surely exist.

  Scenario: Request for an id that does not exist
    When request activity Cameraman by id
    Then the http response status code is 404

  Scenario: Request for an id that exists
    When request activity Actor by id
    Then the http response status code is 200
    And the response contains key title with value Actor

  Scenario: Request for activities list
    When activity list is requested
    Then the http response status code is 200
    And title contains in any order Actor,Author,Conductor,Director,Musician

  Scenario: Request to create activity with a title that already exists
    Given activity with title Cameraman exists
    When request to create activity with title Cameraman
    Then the http response status code is 400

  Scenario: Request to create activity
    When request to create activity with title Cameraman
    Then the http response status code is 201
    When activity list is requested
    Then the http response status code is 200
    And the response list contains entry with key title and value Cameraman

  Scenario: Request to update an activity that does not exist
    When request to update activity with title Cameraman to Photographer
    Then the http response status code is 404

  Scenario: Request to update an activity with a title that already exists
    Given activity with title Cameraman exists
    And activity with title Photographer exists
    When request to update activity with title Cameraman to Photographer
    Then the http response status code is 400

  Scenario: Request to update an activity
    Given activity with title Cameraman exists
    When request to update activity with title Cameraman to Photographer
    Then the http response status code is 204
    When activity list is requested
    Then the http response status code is 200
    And the response list contains entry with key title and value Photographer

  Scenario: Request to delete an activity that does not exist
    When request to delete activity with title Cameraman
    Then the http response status code is 400

  @wip
  Scenario: Request to delete an activity that is related with some items

  Scenario: Request to delete an activity
    Given activity with title Cameraman exists
    When request to delete activity with title Cameraman
    Then the http response status code is 204
    When activity list is requested
    Then the http response status code is 200
    And the response list does not contain entry with key title and value Cameraman

  Scenario: Request for the count of activities
    When count of activities is requested
    Then the http response status code is 200
    And the response is 5