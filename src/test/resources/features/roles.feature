@roles
Feature: Endpoints related to roles

  Administrator is the only value that is supported by the application.

  Scenario: Request for an id that does not exist
    When request role Moderator by id
    Then the http response status code is 404

  Scenario: Request for an id that exists
    When request role Administrator by id
    Then the http response status code is 200
    And the response contains key title with value Administrator

  Scenario: Request for roles list
    When roles list is requested
    Then the http response status code is 200
    And title contains in any order Administrator

  Scenario: Request to create role with a title that already exists
    Given role with title Moderator exists
    When request to create role with title Moderator
    Then the http response status code is 400

  Scenario: Request to create role
    When request to create role with title Moderator
    Then the http response status code is 201
    When roles list is requested
    Then the http response status code is 200
    And title contains in any order Administrator,Moderator

  Scenario: Request to update a role that does not exist
    When request to update role with title Moderator to Viewer
    Then the http response status code is 404

  Scenario: Request to update a role with a title that already exists
    Given role with title Moderator exists
    And role with title Viewer exists
    When request to update role with title Moderator to Viewer
    Then the http response status code is 400

  Scenario: Request to update a role
    Given role with title Moderator exists
    When request to update role with title Moderator to Viewer
    Then the http response status code is 204
    When roles list is requested
    Then the http response status code is 200
    And title contains in any order Administrator,Viewer

  Scenario: Request to delete a role that does not exist
    When request to delete role with title Moderator
    Then the http response status code is 400

  @wip
  Scenario: Request to delete a role that is related with a user

  Scenario: Request to delete a role
    Given role with title Moderator exists
    When request to delete role with title Moderator
    Then the http response status code is 204
    When roles list is requested
    Then the http response status code is 200
    And the response list does not contain entry with key title and value Moderator