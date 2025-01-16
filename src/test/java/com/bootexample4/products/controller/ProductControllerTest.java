package com.bootexample4.products.controller;

import java.util.Optional;
import org.junit.jupiter.api;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;
import com.bootexample4.products.model.Product;
import com.bootexample4.products.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation;
import org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.Mockito.when;
import org.mockito.ArgumentMatchers.any;
import org.mockito.ArgumentMatchers.anyLong;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

public class ProductControllerTest {

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

}