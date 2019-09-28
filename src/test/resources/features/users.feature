@users
Feature: Endpoints related to users

  The only user existing, with Administrator role, is blixabargeld.

  Scenario: Request for an id that does not exist
    When request user mickharvey by id
    Then the http response status code is 404

  Scenario: Request for an id that exists
    When request user blixabargeld by id
    Then the http response status code is 200
    And the response contains key username with value blixabargeld
    And the response contains key role.title with value Administrator

  Scenario: Request for users list
    When users list is requested
    Then the http response status code is 200
    And username contains in any order blixabargeld

  Scenario: Request to create user with a username that already exists
    Given the following users exist
      | username   | password  | email                | role          |
      | mickharvey | 123456789 | mickharvey@gmail.com | Administrator |
    When request to create user
      | username   | password  | email                | role          |
      | mickharvey | 987654321 | mickharvey@yahoo.com | Administrator |
    Then the http response status code is 400

  Scenario: Request to create user with an email that already exists
    Given the following users exist
      | username   | password  | email                | role          |
      | mickharvey | 123456789 | mickharvey@gmail.com | Administrator |
    When request to create user
      | username   | password  | email                | role          |
      | mickrooney | 987654321 | mickharvey@gmail.com | Administrator |
    Then the http response status code is 400

  Scenario: Request to create user with an invalid role
    When request to create user
      | username   | password  | email                | role   |
      | mickharvey | 123456789 | mickharvey@gmail.com | Viewer |
    Then the http response status code is 400

  Scenario: Request to create user
    When request to create user
      | username   | password  | email                | role          |
      | mickharvey | 123456789 | mickharvey@gmail.com | Administrator |
    Then the http response status code is 201
    When request user mickharvey by id
    Then the http response status code is 200
    And the response contains key username with value mickharvey

  Scenario: Request to update user with a username that already exists
    Given the following users exist
      | username   | password  | email                | role          |
      | mickharvey | 123456789 | mickharvey@gmail.com | Administrator |
      | mickrooney | 123456789 | mickrooney@gmail.com | Administrator |
    When request to update user with username mickharvey to
      | username   | password  | email                | role          |
      | mickrooney | 123456789 | mickharvey@yahoo.com | Administrator |
    Then the http response status code is 400

  Scenario: Request to update user with an email that already exists
    Given the following users exist
      | username   | password  | email                | role          |
      | mickharvey | 123456789 | mickharvey@gmail.com | Administrator |
      | mickrooney | 123456789 | mickrooney@gmail.com | Administrator |
    When request to update user with username mickharvey to
      | username   | password  | email                | role          |
      | mickharvey | 123456789 | mickrooney@gmail.com | Administrator |
    Then the http response status code is 400

  Scenario: Request to update user with an invalid role
    Given the following users exist
      | username   | password  | email                | role          |
      | mickharvey | 123456789 | mickharvey@gmail.com | Administrator |
    When request to update user with username mickharvey to
      | username   | password  | email                | role   |
      | mickharvey | 123456789 | mickharvey@gmail.com | Viewer |
    Then the http response status code is 400

  @wip
  Scenario: Request to update user

  @wip
  Scenario: Request to delete user