@artists
Feature: Endpoints related to artists

  Scenario: Request for an id that does not exist
    When request artist Britney Spears by id
    Then the http response status code is 404

  Scenario: Request for an id that exists
    When request artist Nick Cave by id
    Then the http response status code is 200
    And the response contains key title with value Nick Cave

  Scenario: Request for artist list
    When artists list is requested
    Then the http response status code is 200
    # !!! is a band (aka chk-chk-chk). Safe assertion for the ordering scenario
    And the response contains key [0].title with value !!!
    When artists list is requested in descending order
    Then the http response status code is 200
    And the response does not contain key [0].title with value !!!

  Scenario: Request to create artist with a title that already exists
    Given artist with title Britney Spears exists
    When request to create artist with title Britney Spears
    Then the http response status code is 400

  Scenario: Request to create artist
    When request to create artist with title Britney Spears
    Then the http response status code is 201
    When request artist Britney Spears by id
    Then the http response status code is 200

  Scenario: Request to update an artist that does not exist
    When request to update artist with title Britney Spears to Beyonce
    Then the http response status code is 404

  Scenario: Request to update an artist with a title that already exists
    Given artist with title Britney Spears exists
    And artist with title Beyonce exists
    When request to update artist with title Britney Spears to Beyonce
    Then the http response status code is 400

  Scenario: Request to update an artist
    Given artist with title Britney Spears exists
    When request to update artist with title Britney Spears to Beyonce
    Then the http response status code is 204
    When request artist Britney Spears by id
    Then the http response status code is 404
    When request artist Beyonce by id
    Then the http response status code is 200

  Scenario: Request to delete an artist that does not exist
    When request to delete artist with title Britney Spears
    Then the http response status code is 400

  @wip #TODO Testing
  Scenario: Request to delete an artist that is related with some items

  Scenario: Request to delete an artist
    Given artist with title Britney Spears exists
    When request to delete artist with title Britney Spears
    Then the http response status code is 204
    When request artist Britney Spears by id
    Then the http response status code is 404

  Scenario: Request for a random artist
    When random artist is requested
    Then the http response status code is 200
    And the response contains not null attribute title

  Scenario: Request for the count of artists
    When count of artists is requested
    Then the http response status code is 200