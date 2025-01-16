package com.bootexample4.products.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation;
import com.bootexample4.products.model.Product;
import com.bootexample4.products.repository.ProductRepository;
import org.junit.experimental.categories.Category;

public class ProductControllerTest {

	@Test
	public void testSuccessfulProductCreation() {
		// Arrange
		Product product = new Product("TestProduct", "Description", 10.0);
		// Act
		Product createdProduct = productController.createProduct(product);
		// Assert
		assertNotNull(createdProduct);
		assertEquals(product.getName(), createdProduct.getName());
		assertEquals(product.getDescription(), createdProduct.getDescription());
		assertEquals(product.getPrice(), createdProduct.getPrice(), 0.001);
	}

	@Test
	public void testCreateProductWithNullInput() {
		// Arrange
		Product product = null;
		// Act
		Product createdProduct = productController.createProduct(product);
		// Assert
		assertNull(createdProduct);
	}

	@Test
	public void testCreateProductWithEmptyFields() {
		// Arrange
		Product product = new Product("", "", 0.0);
		// Act
		Product createdProduct = productController.createProduct(product);
		// Assert
		assertNull(createdProduct);
	}

	@Test
	public void testCreateProductWithDuplicateData() {
		// Arrange
		Product existingProduct = new Product("ExistingProduct", "Description", 20.0);
		productController.createProduct(existingProduct);
		Product duplicateProduct = new Product("ExistingProduct", "Duplicate Description", 30.0);
		// Act
		Product createdProduct = productController.createProduct(duplicateProduct);
		// Assert
		assertNull(createdProduct);
	}

	@Test
	public void testCreateProductWithValidData() {
		// Arrange
		Product product = new Product("ValidProduct", "Valid Description", 15.0);
		// Act
		Product createdProduct = productController.createProduct(product);
		// Assert
		assertNotNull(createdProduct);
		assertEquals(product.getName(), createdProduct.getName());
		assertEquals(product.getDescription(), createdProduct.getDescription());
		assertEquals(product.getPrice(), createdProduct.getPrice(), 0.001);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		return productRepository.findById(id)
			.map(product -> ResponseEntity.ok().body(product))
			.orElse(ResponseEntity.notFound().build());
	}

	@org.junit.Test
	@org.junit.experimental.categories.Category(Categories.valid.class)
	public void testRetrieveProductByIdValidId() {
		// TODO: Prepare a mock ProductRepository with a known product for the given valid
		// ID
		// TODO: Call getProductById with the known valid ID
		// TODO: Ensure that the response contains the expected product details
	}

	@org.junit.Test
	@org.junit.experimental.categories.Category(Categories.invalid.class)
	public void testRetrieveProductByInvalidId() {
		// TODO: Set up the mock ProductRepository to return an empty optional for the
		// invalid ID
		// TODO: Invoke getProductById with the invalid ID
		// TODO: Verify that the response is a not found status
	}

	@org.junit.Test
	@org.junit.experimental.categories.Category(Categories.boundary.class)
	public void testRetrieveProductByIdZero() {
		// TODO: Prepare the mock ProductRepository to return a specific product for ID
		// zero
		// TODO: Call getProductById with ID zero
		// TODO: Confirm that the response contains the expected product details
	}

	@org.junit.Test
	@org.junit.experimental.categories.Category(Categories.invalid.class)
	public void testRetrieveProductByNegativeId() {
		// TODO: Configure the mock ProductRepository to handle negative ID inputs
		// TODO: Execute getProductById with a negative ID value
		// TODO: Validate that the response indicates a not found status
	}

	@org.junit.Test
	@org.junit.experimental.categories.Category(Categories.boundary.class)
	public void testRetrieveProductByMaxIdValue() {
		// TODO: Prepare the mock ProductRepository to return a specific product for the
		// maximum ID value
		// TODO: Call getProductById with the maximum ID value
		// TODO: Ensure that the response includes the expected product details
	}

	@Test
	public void testUpdateProductSuccessfully() {
		// TODO: Mock ProductRepository with an existing product
		Product existingProduct = new Product(1L, "Existing Product", "Description", 100.0);
		Product updatedProductInfo = new Product(1L, "Updated Product", "New Description", 150.0);
		ResponseEntity<Product> responseEntity = updateProduct(1L, updatedProductInfo);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Updated Product", responseEntity.getBody().getName());
		assertEquals("New Description", responseEntity.getBody().getDescription());
		assertEquals(150.0, responseEntity.getBody().getPrice(), 0.01);
	}

	@Test
	public void testUpdateNonExistingProduct() {
		// TODO: Prepare mock ProductRepository without specified ID
		ResponseEntity<Product> responseEntity = updateProduct(2L,
				new Product(2L, "New Product", "Description", 200.0));
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	public void testUpdateProductWithEmptyFields() {
		// TODO: Mock ProductRepository with an existing product
		Product existingProduct = new Product(3L, "Existing Product", "Description", 100.0);
		Product updatedProductInfo = new Product(3L, "", "", 0.0);
		ResponseEntity<Product> responseEntity = updateProduct(3L, updatedProductInfo);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Existing Product", responseEntity.getBody().getName());
		assertEquals("Description", responseEntity.getBody().getDescription());
		assertEquals(100.0, responseEntity.getBody().getPrice(), 0.01);
	}

	@Test
	public void testUpdateProductWithNullValues() {
		// TODO: Mock ProductRepository with an existing product
		Product existingProduct = new Product(4L, "Existing Product", "Description", 100.0);
		Product updatedProductInfo = new Product(4L, null, null, null);
		ResponseEntity<Product> responseEntity = updateProduct(4L, updatedProductInfo);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Existing Product", responseEntity.getBody().getName());
		assertEquals("Description", responseEntity.getBody().getDescription());
		assertEquals(100.0, responseEntity.getBody().getPrice(), 0.01);
	}

	@Test
	public void testUpdateProductWithInvalidId() {
		// TODO: Mock ProductRepository with an existing product
		Product existingProduct = new Product(5L, "Existing Product", "Description", 100.0);
		Product updatedProductInfo = new Product(5L, "Updated Product", "New Description", 150.0);
		ResponseEntity<Product> responseEntity = updateProduct(10L, updatedProductInfo);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	private ResponseEntity<Product> updateProduct(Long id, Product product) {
		return productRepository.findById(id).map(existingProduct -> {
			existingProduct.setName(product.getName());
			existingProduct.setDescription(product.getDescription());
			existingProduct.setPrice(product.getPrice());
			Product updatedProduct = productRepository.save(existingProduct);
			return ResponseEntity.ok().body(updatedProduct);
		}).orElse(ResponseEntity.notFound().build());
	}

}