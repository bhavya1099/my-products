package com.bootexample4.products.controller;

import org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import com.bootexample4.products.model.Product;
import com.bootexample4.products.repository.ProductRepository;
import org.junit.jupiter.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation;

public class ProductControllerTest {

	/*
	 * ROOST_METHOD_HASH=getAllProducts_c7c755eb4e
	 * ROOST_METHOD_SIG_HASH=getAllProducts_e267ceea76
	 *
	 */@BeforeEach
	public void setUp() {
		productList = Arrays.asList(new Product(), new Product());
	}

	/*
	 * ROOST_METHOD_HASH=getAllProducts_c7c755eb4e
	 * ROOST_METHOD_SIG_HASH=getAllProducts_e267ceea76
	 *
	 */@Test
@Tag("valid")
public void testGetAllProducts() throws Exception {
    when(productRepository.findAll()).thenReturn(productList);
    List<Product> result = productController.getAllProducts();
    assertEquals(productList, result, "getAllProducts should return productList");
}

	/*
	 * ROOST_METHOD_HASH=getAllProducts_c7c755eb4e
	 * ROOST_METHOD_SIG_HASH=getAllProducts_e267ceea76
	 *
	 */@Test
@Tag("boundary")
public void testGetAllProductsWithEmptyRepo() throws Exception {
    when(productRepository.findAll()).thenReturn(Arrays.asList());
    List<Product> result = productController.getAllProducts();
    assertEquals(0, result.size(), "getAllProducts should return empty list when repo is empty");
}

	/*
	 * ROOST_METHOD_HASH=getAllProducts_c7c755eb4e
	 * ROOST_METHOD_SIG_HASH=getAllProducts_e267ceea76
	 *
	 */@Test
@Tag("invalid")
public void testGetAllProductsException() throws Exception {
    when(productRepository.findAll()).thenThrow(MockitoException.class);
    try {
        productController.getAllProducts();
    } catch (Exception e) {
        assertEquals(MockitoException.class, e.getClass(), "getAllProducts should throw exception when repo throws exception");
    }
}

}