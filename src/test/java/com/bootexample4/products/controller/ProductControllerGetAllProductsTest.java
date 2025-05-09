
package com.bootexample4.products.controller;

import com.bootexample4.products.model.Product;
import com.bootexample4.products.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

class ProductControllerGetAllProductsTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductController productController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@Tag("valid")
	public void getAllProductsReturnsAllProducts() {
		// Arrange
		List<Product> mockProducts = new ArrayList<>();
		mockProducts.add(new Product(1L, "Product 1", "Description 1", 100.0));
		mockProducts.add(new Product(2L, "Product 2", "Description 2", 200.0));
		when(productRepository.findAll()).thenReturn(mockProducts);
		// Act
		List<Product> result = productController.getAllProducts();
		// Assert
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(mockProducts, result);
		verify(productRepository, times(1)).findAll();
	}

	@Test
    @Tag("boundary")
    public void getAllProductsReturnsEmptyList() {
        // Arrange
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        // Act
        List<Product> result = productController.getAllProducts();
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

	@Test
	@Tag("boundary")
	public void getAllProductsReturnsSingleProduct() {
		// Arrange
		List<Product> mockProducts = new ArrayList<>();
		mockProducts.add(new Product(1L, "Product 1", "Description 1", 100.0));
		when(productRepository.findAll()).thenReturn(mockProducts);
		// Act
		List<Product> result = productController.getAllProducts();
		// Assert
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(mockProducts, result);
		verify(productRepository, times(1)).findAll();
	}

	@Test
    @Tag("integration")
    public void getAllProductsCallsFindAllOnce() {
        // Arrange
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        // Act
        productController.getAllProducts();
        // Assert
        verify(productRepository, times(1)).findAll();
    }

	@Test
    @Tag("invalid")
    public void getAllProductsHandlesRepositoryException() {
        // Arrange
        when(productRepository.findAll()).thenThrow(new RuntimeException("Repository exception"));
        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> productController.getAllProducts());
        assertEquals("Repository exception", exception.getMessage());
        verify(productRepository, times(1)).findAll();
    }

	@Test
	@Tag("boundary")
	public void getAllProductsReturnsImmutableList() {
		// Arrange
		List<Product> mockProducts = new ArrayList<>();
		mockProducts.add(new Product(1L, "Product 1", "Description 1", 100.0));
		when(productRepository.findAll()).thenReturn(Collections.unmodifiableList(mockProducts));
		// Act
		List<Product> result = productController.getAllProducts();
		// Assert
		assertThrows(UnsupportedOperationException.class,
				() -> result.add(new Product(2L, "Product 2", "Description 2", 200.0)));
		verify(productRepository, times(1)).findAll();
	}

	@Test
    @Tag("invalid")
    public void getAllProductsHandlesNullRepositoryResponse() {
        // Arrange
        when(productRepository.findAll()).thenReturn(null);
        // Act
        List<Product> result = productController.getAllProducts();
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

}