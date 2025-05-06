package com.bootexample4.products.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import org.junit.jupiter.api.Assertions;
import com.bootexample4.products.model.Product;
import com.bootexample4.products.repository.ProductRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Optional;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito.when;
import org.mockito.ArgumentMatchers.any;
import org.mockito.ArgumentMatchers.anyLong;
import org.springframework.http.HttpStatus;

public class ProductControllerTest {

	@Test
	@Tag("valid")
	public void getAllProductsReturnsAllProducts() {
		// Arrange
		Product product1 = new Product();
		Product product2 = new Product();
		List<Product> expectedProducts = Arrays.asList(product1, product2);
		when(productRepository.findAll()).thenReturn(expectedProducts);
		// Act
		List<Product> actualProducts = productController.getAllProducts();
		// Assert
		assertEquals(expectedProducts, actualProducts);
	}

@Test
@Tag("boundary")
public void getAllProductsHandlesEmptyList() {
    // Arrange
    when(productRepository.findAll()).thenReturn(Collections.emptyList());
    // Act
    List<Product> actualProducts = productController.getAllProducts();
    // Assert
    assertTrue(actualProducts.isEmpty());
}

@Test
@Tag("invalid")
public void getAllProductsHandlesNull() {
    // Arrange
    when(productRepository.findAll()).thenReturn(null);
    // Act
    List<Product> actualProducts = productController.getAllProducts();
    // Assert
    assertNull(actualProducts);
}

	@BeforeEach
	public void setUp() {
		product = new Product();
		product.setName("Test Product");
		product.setDescription("Test Product Description");
		product.setPrice(100.0);
	}

	@Test
	@Tag("valid")
	public void testCreateProductWithValidInput() {
		Mockito.when(productRepository.save(product)).thenReturn(product);
		Product createdProduct = productController.createProduct(product);
		assertEquals(product, createdProduct);
	}

	@Test
	@Tag("invalid")
	public void testCreateProductWithNullInput() {
		Mockito.when(productRepository.save(null)).thenThrow(IllegalArgumentException.class);
		assertThrows(IllegalArgumentException.class, () -> productController.createProduct(null));
	}

	@Test
	@Tag("boundary")
	public void testCreateProductWhenSaveFails() {
		Mockito.when(productRepository.save(product)).thenThrow(RuntimeException.class);
		assertThrows(RuntimeException.class, () -> productController.createProduct(product));
	}

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@Tag("valid")
	public void shouldReturnProductWhenIdIsValid() {
		// Arrange
		Product product = new Product();
		product.setId(1L);
		when(productRepository.findById(1L)).thenReturn(Optional.of(product));
		// Act
		ResponseEntity<Product> responseEntity = productController.getProductById(1L);
		// Assert
		assertEquals(ResponseEntity.ok().body(product), responseEntity);
	}

@Test
@Tag("invalid")
public void shouldReturnNotFoundWhenIdIsNonExistent() {
    // Arrange
    when(productRepository.findById(1L)).thenReturn(Optional.empty());
    // Act
    ResponseEntity<Product> responseEntity = productController.getProductById(1L);
    // Assert
    assertEquals(ResponseEntity.notFound().build(), responseEntity);
}

	@Test
	@Tag("invalid")
	public void shouldThrowExceptionWhenIdIsNull() {
		// Act and Assert
		assertThrows(IllegalArgumentException.class, () -> {
			productController.getProductById(null);
		});
	}

	@Test
	@Tag("valid")
	public void testSuccessfulProductUpdate() {
		Product existingProduct = new Product();
		existingProduct.setId(1L);
		existingProduct.setName("Old Name");
		existingProduct.setDescription("Old Description");
		existingProduct.setPrice(100.0);
		Product newProduct = new Product();
		newProduct.setName("New Name");
		newProduct.setDescription("New Description");
		newProduct.setPrice(200.0);
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(existingProduct));
		when(productRepository.save(any(Product.class))).thenReturn(newProduct);
		ResponseEntity<Product> response = productController.updateProduct(1L, newProduct);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(newProduct, response.getBody());
	}

	@Test
	@Tag("invalid")
	public void testProductUpdateWithNonExistingId() {
		Product newProduct = new Product();
		newProduct.setName("New Name");
		newProduct.setDescription("New Description");
		newProduct.setPrice(200.0);
		when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
		ResponseEntity<Product> response = productController.updateProduct(1L, newProduct);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	@Tag("invalid")
	public void testProductUpdateWithNullData() {
		Product existingProduct = new Product();
		existingProduct.setId(1L);
		existingProduct.setName("Old Name");
		existingProduct.setDescription("Old Description");
		existingProduct.setPrice(100.0);
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(existingProduct));
		assertThrows(NullPointerException.class, () -> productController.updateProduct(1L, null));
	}

	@Test
	@Tag("valid")
	public void testDeleteProductWithValidId() {
		// Arrange
		Long testId = 1L;
		Product testProduct = new Product();
		when(productRepository.findById(testId)).thenReturn(Optional.of(testProduct));
		// Act
		ResponseEntity<Object> response = productController.deleteProduct(testId);
		// Assert
		verify(productRepository, times(1)).delete(testProduct);
		assertEquals(ResponseEntity.ok().build(), response);
	}

	@Test
	@Tag("invalid")
	public void testDeleteProductWithInvalidId() {
		// Arrange
		Long testId = 1L;
		when(productRepository.findById(testId)).thenReturn(Optional.empty());
		// Act
		ResponseEntity<Object> response = productController.deleteProduct(testId);
		// Assert
		verify(productRepository, never()).delete(any(Product.class));
		assertEquals(ResponseEntity.notFound().build(), response);
	}

	@Test
	@Tag("boundary")
	public void testDeleteProductWithNullId() {
		// Arrange
		// No arrangement necessary as id is null
		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> productController.deleteProduct(null));
	}

}