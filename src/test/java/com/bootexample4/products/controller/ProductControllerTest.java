package com.bootexample4.products.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation;
import com.bootexample4.products.model.Product;
import com.bootexample4.products.repository.ProductRepository;
import org.junit.experimental.categories.Category;
import org.junit.Test;
import org.junit.Assert.assertEquals;
import org.mockito.Mockito.mock;
import org.mockito.Mockito.when;

public class ProductControllerTest {

	@Test
	@Category(Categories.valid.class)
	public void testSuccessfulProductCreation() {
		// TODO: Change values as needed
		Product product = new Product("Test Product", "Description", 100.0);
		Product createdProduct = productController.createProduct(product);
		assertNotNull(createdProduct);
	}

	@Test
	@Category(Categories.invalid.class)
	public void testNullProductInput() {
		Product product = null;
		assertThrows(IllegalArgumentException.class, () -> productController.createProduct(product));
	}

	@Test
	@Category(Categories.invalid.class)
	public void testEmptyProductName() {
		// TODO: Change values as needed
		Product product = new Product("", "Description", 100.0);
		assertThrows(IllegalArgumentException.class, () -> productController.createProduct(product));
	}

	@Test
	@Category(Categories.invalid.class)
	public void testProductWithExistingId() {
		// Assuming 1 already exists in the repository
		Long existingId = 1L;
		Product product = new Product("New Product", "Description", 100.0, existingId);
		Product createdProduct = productController.createProduct(product);
		assertNull(createdProduct);
	}

	@Test
	@Category(Categories.valid.class)
	public void testValidProductCreationAndPersistence() {
		// TODO: Change values as needed
		Product product = new Product("Test Product", "Description", 100.0);
		Product createdProduct = productController.createProduct(product);
		Product retrievedProduct = productRepository.findById(createdProduct.getId()).orElse(null);
		assertNotNull(retrievedProduct);
		assertEquals(createdProduct, retrievedProduct);
	}

	@Test
	@Category(Categories.valid.class)
	public void testRetrieveProductByIdValidId() {
		// Arrange
		Long validId = 1L;
		Product mockProduct = new Product(validId, "Product 1", "Description 1", 10.0);
		when(productRepository.findById(validId)).thenReturn(java.util.Optional.of(mockProduct));
		// Act
		ResponseEntity<Product> response = new ProductController().getProductById(validId);
		// Assert
		assertEquals(ResponseEntity.ok().body(mockProduct), response);
	}

	@Test
	@Category(Categories.invalid.class)
	public void testRetrieveProductByIdInvalidId() {
		// Arrange
		Long invalidId = 100L;
		when(productRepository.findById(invalidId)).thenReturn(java.util.Optional.empty());
		// Act
		ResponseEntity<Product> response = new ProductController().getProductById(invalidId);
		// Assert
		assertEquals(ResponseEntity.notFound().build(), response);
	}

	@Test
	@Category(Categories.boundary.class)
	public void testRetrieveProductByNullId() {
		// Arrange
		Long nullId = null;
		// Act
		ResponseEntity<Product> response = new ProductController().getProductById(nullId);
		// Assert
		assertEquals(ResponseEntity.notFound().build(), response);
	}

	@Test
	@Category(Categories.boundary.class)
	public void testRetrieveProductByNonExistentId() {
		// Arrange
		Long nonExistentId = 999L;
		when(productRepository.findById(nonExistentId)).thenReturn(java.util.Optional.empty());
		// Act
		ResponseEntity<Product> response = new ProductController().getProductById(nonExistentId);
		// Assert
		assertEquals(ResponseEntity.notFound().build(), response);
	}

	@Test
	@Category(Categories.boundary.class)
	public void testRetrieveProductByNegativeId() {
		// Arrange
		Long negativeId = -1L;
		// Act
		ResponseEntity<Product> response = new ProductController().getProductById(negativeId);
		// Assert
		assertEquals(ResponseEntity.notFound().build(), response);
	}

	@Test
	@Category(Categories.boundary.class)
	public void testRetrieveProductByMaxValidId() {
		// Arrange
		Long maxValidId = Long.MAX_VALUE;
		Product mockProduct = new Product(maxValidId, "Max Product", "Max Description", 1000.0);
		when(productRepository.findById(maxValidId)).thenReturn(java.util.Optional.of(mockProduct));
		// Act
		ResponseEntity<Product> response = new ProductController().getProductById(maxValidId);
		// Assert
		assertEquals(ResponseEntity.ok().body(mockProduct), response);
	}

	@Test
	@Category(Categories.boundary.class)
	public void testRetrieveProductByEmptyId() {
		// Arrange
		Long emptyId = Long.parseLong("");
		// Act
		ResponseEntity<Product> response = new ProductController().getProductById(emptyId);
		// Assert
		assertEquals(ResponseEntity.notFound().build(), response);
	}

	@Test
	@Category(Categories.invalid.class)
	public void testRetrieveProductByAlphanumericId() {
		// Arrange
		String alphanumericId = "abc";
		// Act
		ResponseEntity<Product> response = new ProductController().getProductById(Long.parseLong(alphanumericId));
		// Assert
		assertEquals(ResponseEntity.notFound().build(), response);
	}

	@Test
	@Category(Categories.invalid.class)
	public void testRetrieveProductBySpecialCharacterId() {
		// Arrange
		String specialCharacterId = "!@#";
		// Act
		ResponseEntity<Product> response = new ProductController().getProductById(Long.parseLong(specialCharacterId));
		// Assert
		assertEquals(ResponseEntity.notFound().build(), response);
	}

}