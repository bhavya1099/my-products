
package com.bootexample4.products.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.bootexample4.products.model.Product;
import com.bootexample4.products.repository.ProductRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SpringBootTest
public class ProductControllerGetAllProductsTest {

	@InjectMocks
	private ProductController productController;

	@Mock
	private ProductRepository productRepository;

	public ProductControllerGetAllProductsTest() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@Tag("valid")
	public void testGetAllProducts() {
		Product product1 = new Product();
		Product product2 = new Product();
		List<Product> productList = Arrays.asList(product1, product2);
		when(productRepository.findAll()).thenReturn(productList);
		List<Product> result = productController.getAllProducts();
		assertEquals(productList, result);
	}

	@Test
    @Tag("boundary")
    public void testGetAllProductsWhenNoneExist() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        List<Product> result = productController.getAllProducts();
        assertEquals(Collections.emptyList(), result);
    }

	@Test
    @Tag("invalid")
    public void testGetAllProductsExceptionHandling() {
        when(productRepository.findAll()).thenThrow(new RuntimeException());
        try {
            productController.getAllProducts();
        } catch (Exception e) {
            assertEquals(RuntimeException.class, e.getClass());
        }
    }

}