
package com.bootexample4.products.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.bootexample4.products.model.Product;
import com.bootexample4.products.repository.ProductRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerGetAllProductsTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	ProductController productController;

	private List<Product> productList;

	@BeforeEach
	public void setup() {
		Product product1 = new Product();
		Product product2 = new Product();
		productList = Arrays.asList(product1, product2);
	}

	@Test
    @Tag("valid")
    public void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(productList);
        List<Product> result = productController.getAllProducts();
        verify(productRepository, times(1)).findAll();
        assertEquals(productList, result, "Product list should match the expected result");
    }

	@Test
    @Tag("boundary")
    public void testGetAllProductsWhenNoProductsExist() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        List<Product> result = productController.getAllProducts();
        verify(productRepository, times(1)).findAll();
        assertTrue(result.isEmpty(), "Product list should be empty");
    }

	@Test
    @Tag("invalid")
    public void testGetAllProductsExceptionHandling() {
        when(productRepository.findAll()).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> productController.getAllProducts(), "Expected exception to be thrown");
        verify(productRepository, times(1)).findAll();
    }

}