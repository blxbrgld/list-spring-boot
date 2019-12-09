@categories
Feature: Endpoints related to categories

  Each category can have others as children or not. Some categories are allowed to be
  related with items and some not. Currently the categories tree is the following:

    Music
      Popular Music
      Classical Music
      Greek Music
    Films
      DVD Films
      DivX Films
    Books

  Items can be related with categories having no children (i.e. Popular Music, but no
  Music) or categories that do not have children but also have no parent (i.e. Music).

  Scenario: Request for an id that does not exist
    When request category Folk Music by id
    Then the http response status code is 404

  Scenario: Request for a leaf category by id
    When request category Popular Music by id
    Then the http response status code is 200
    And the response contains key title with value Popular Music

  Scenario: Request for a root category by id
    When request category Music by id
    Then the http response status code is 200
    And the response contains key title with value Music
    And categories.title contains in any order Popular Music,Classical Music,Greek Music

  Scenario: Request for categories list with no filtering
    When category list with filtering NONE is requested
    Then the http response status code is 200
    And the response list has size 8

  Scenario: Request for categories list with MENU filtering
    When category list with filtering MENU is requested
    Then the http response status code is 200
    And the response list has size 3
    And title contains in any order Books,Films,Music

  Scenario: Request for categories list with DROPDOWN filtering
    When category list with filtering DROPDOWN is requested
    Then the http response status code is 200
    And the response list has size 6
    And title contains in any order Books,Classical Music,Greek Music,Popular Music,DivX Films,DVD Films

  Scenario: Request to create category with a title that already exists
    Given category with title Furniture exists
    When request to create category with title Furniture
    Then the http response status code is 400

  Scenario: Request to create category with the same title and parent title
    When request to create category with title Furniture and parent title Furniture
    Then the http response status code is 400

  Scenario: Request to create category without a parent
    When request to create category with title Furniture
    Then the http response status code is 201
    When request category Furniture by id
    Then the http response status code is 200

  Scenario: Request to create category with parent
    When request to create category with title Furniture and parent title Music
    Then the http response status code is 201
    When request category Music by id
    Then the http response status code is 200
    And the response contains key title with value Music
    And the response list contains entry with key categories.title and value Furniture

  Scenario: Request to update a category that does not exist
    When request to update category with title Furniture to Devices
    Then the http response status code is 404

  Scenario: Request to update a category with a title that already exists
    Given category with title Furniture exists
    And category with title Devices exists
    When request to update category with title Furniture to Devices
    Then the http response status code is 400

  Scenario: Request to update a category's title
    Given category with title Furniture exists
    When request to update category with title Furniture to Devices
    Then the http response status code is 204
    When request category Furniture by id
    Then the http response status code is 404
    When request category Devices by id
    Then the http response status code is 200
    And the response contains key title with value Devices

  Scenario: Request to update a category's parent
    Given category with title Furniture exists
    And category with title Devices exists
    When request to update parent of category Furniture to Devices
    Then the http response status code is 204
    When request category Devices by id
    Then the http response status code is 200
    And the response contains key title with value Devices
    And the response list contains entry with key categories.title and value Furniture

  Scenario: Request to delete a category that is related with some items
    Given category with title Applications exists
    And activity with title Developer exists
    And comment with title Spring Applications exists
    When request to create item
      | title            | category     | year | artist    | activity  | comment             |
      | List application | Applications | 2019 | blxbrgld  | Developer | Spring Applications |
    When request to delete category with title Applications
    Then the http response status code is 400

  Scenario: Request to delete a category
    Given category with title Furniture exists
    When request to delete category with title Furniture
    Then the http response status code is 204
    When request category Furniture by id
    Then the http response status code is 404