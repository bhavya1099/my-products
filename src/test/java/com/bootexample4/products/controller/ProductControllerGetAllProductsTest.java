
package com.bootexample4.products.controller;

import com.bootexample4.products.model.Product;
import com.bootexample4.products.repository.ProductRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SpringBootTest
public class ProductControllerGetAllProductsTest {

	@Autowired
	private ProductController productController;

	@MockBean
	private ProductRepository productRepository;

	@Test
	@Tag("valid")
	public void testGetAllProductsReturnsAllProducts() {
		Product product1 = new Product();
		Product product2 = new Product();
		List<Product> productList = Arrays.asList(product1, product2);
		when(productRepository.findAll()).thenReturn(productList);
		List<Product> result = productController.getAllProducts();
		assertEquals(productList, result);
	}

	@Test
    @Tag("valid")
    public void testGetAllProductsReturnsEmptyListWhenNoProducts() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        List<Product> result = productController.getAllProducts();
        assertEquals(Collections.emptyList(), result);
    }

	@Test
    @Tag("invalid")
    public void testGetAllProductsHandlesExceptions() {
        when(productRepository.findAll()).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> productController.getAllProducts());
    }

}