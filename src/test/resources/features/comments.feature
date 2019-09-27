@comments
Feature: Endpoints related to comments

  Scenario: Request for an id that does not exist
    When request comment Blaxploitation by id
    Then the http response status code is 404

  Scenario: Request for an id that exists
    When request comment Documentary by id
    Then the http response status code is 200
    And the response contains key title with value Documentary

  Scenario: Request for comments list
    When comment list is requested
    Then the http response status code is 200
    # 7'' is a good candidates for assertion when list is requested by title in ASC order
    And the response list contains entry with key title and value 7''

  Scenario: Request to create comment with a title that already exists
    Given comment with title Blaxploitation exists
    When request to create comment with title Blaxploitation
    Then the http response status code is 400

  Scenario: Request to create comment
    When request to create comment with title Blaxploitation
    Then the http response status code is 201
    When request comment Blaxploitation by id
    Then the http response status code is 200

  Scenario: Request to update a comment that does not exist
    When request to update comment with title Blaxploitation to Western
    Then the http response status code is 404

  Scenario: Request to update a comment with a title that already exists
    Given comment with title Blaxploitation exists
    And comment with title Western exists
    When request to update comment with title Blaxploitation to Western
    Then the http response status code is 400

  Scenario: Request to update a comment
    Given comment with title Blaxploitation exists
    When request to update comment with title Blaxploitation to Western
    Then the http response status code is 204
    When request comment Western by id
    Then the http response status code is 200
    And the response contains key title with value Western

  Scenario: Request to delete a comment that does not exist
    When request to delete comment with title Blaxploitation
    Then the http response status code is 400

  @wip
  Scenario: Request to delete a comment that is related with some items

  Scenario: Request to delete a comment
    Given comment with title Blaxploitation exists
    When request to delete comment with title Blaxploitation
    Then the http response status code is 204