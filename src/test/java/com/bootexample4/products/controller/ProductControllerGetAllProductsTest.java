package com.bootexample4.products.controller;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import com.bootexample4.products.model.Product;
import com.bootexample4.products.repository.ProductRepository;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerGetAllProductsTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductController productController;

	private Product product1;

	private Product product2;

	@Before
	public void setUp() {
		product1 = new Product(); // TODO: Set properties
		product2 = new Product(); // TODO: Set properties
	}

	@Test
	@Category(Categories.IntegrationTests.class)
	public void getAllProductsSuccessfully() {
		List<Product> mockProducts = Arrays.asList(product1, product2);
		when(productRepository.findAll()).thenReturn(mockProducts);
		List<Product> resultProducts = productController.getAllProducts();
		assertNotNull(resultProducts);
		assertEquals(mockProducts.size(), resultProducts.size());
		assertArrayEquals(mockProducts.toArray(), resultProducts.toArray());
	}

	@Test
    @Category(Categories.BoundaryTests.class)
    public void getAllProductsReturnsEmptyList() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        List<Product> resultProducts = productController.getAllProducts();
        assertNotNull(resultProducts);
        assertTrue(resultProducts.isEmpty());
    }

	@Test(expected = RuntimeException.class)
    @Category(Categories.ExceptionTests.class)
    public void getAllProductsHandlesRepositoryExceptions() {
        when(productRepository.findAll()).thenThrow(RuntimeException.class);
        productController.getAllProducts();
    }

}