Feature: Product API Integration Tests

  Background:
    Given the API base URL 'http://localhost:8080/products'

  Scenario: Retrieve all products successfully
    When I send a GET request to '/'
    Then the response status should be 200
    And the response should contain a list of products

  Scenario: Create a new product successfully
    Given the request payload
      """
      {
        "name": "Test Product",
        "description": "A product for testing",
        "price": 100.0
      }
      """
    When I send a POST request to '/' with the payload
    Then the response status should be 201
    And the response should contain the created product details

  Scenario: Retrieve a product by ID successfully
    Given a product with ID 1 exists
    When I send a GET request to '/1'
    Then the response status should be 200
    And the response should contain the product details for ID 1

  Scenario: Update an existing product successfully
    Given a product with ID 1 exists
    And the request payload
      """
      {
        "name": "Updated Product",
        "description": "Updated description",
        "price": 150.0
      }
      """
    When I send a PUT request to '/1' with the payload
    Then the response status should be 200
    And the response should contain the updated product details

  Scenario: Delete a product successfully
    Given a product with ID 1 exists
    When I send a DELETE request to '/1'
    Then the response status should be 200
    And the response should be empty

  Scenario: Retrieve a product by non-existent ID
    When I send a GET request to '/999'
    Then the response status should be 404
    And the response should be 'Product not found'

  Scenario: Update a product with non-existent ID
    Given the request payload
      """
      {
        "name": "Non-existent Product",
        "description": "This product does not exist",
        "price": 200.0
      }
      """
    When I send a PUT request to '/999' with the payload
    Then the response status should be 404
    And the response should be 'Product not found'

  Scenario: Delete a product with non-existent ID
    When I send a DELETE request to '/999'
    Then the response status should be 404
    And the response should be 'Product not found'
