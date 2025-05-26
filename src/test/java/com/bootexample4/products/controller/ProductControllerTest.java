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
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import java.util.Optional;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.ArgumentMatchers.anyLong;
import org.springframework.http.HttpStatus;
import org.mockito.Mockito.any;

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

	/*
	 * ROOST_METHOD_HASH=createProduct_60409495d0
	 * ROOST_METHOD_SIG_HASH=createProduct_5b0158b3eb
	 *
	 */@BeforeEach
	public void setUp() {
		testProduct = new Product();
		testProduct.setName("Test Product");
		testProduct.setDescription("Test Description");
		testProduct.setPrice(123.45);
	}

	/*
	 * ROOST_METHOD_HASH=createProduct_60409495d0
	 * ROOST_METHOD_SIG_HASH=createProduct_5b0158b3eb
	 *
	 */@Test
@Tag("valid")
public void testCreateProductWithValidProduct() {
    when(productRepository.save(any(Product.class))).thenReturn(testProduct);
    Product createdProduct = productController.createProduct(testProduct);
    assertNotNull(createdProduct);
    assertEquals(testProduct.getName(), createdProduct.getName());
}

	/*
	 * ROOST_METHOD_HASH=createProduct_60409495d0
	 * ROOST_METHOD_SIG_HASH=createProduct_5b0158b3eb
	 *
	 */@Test
	@Tag("invalid")
	public void testCreateProductWithNullProduct() {
		assertThrows(IllegalArgumentException.class, () -> {
			productController.createProduct(null);
		});
	}

	/*
	 * ROOST_METHOD_HASH=createProduct_60409495d0
	 * ROOST_METHOD_SIG_HASH=createProduct_5b0158b3eb
	 *
	 */@Test
@Tag("boundary")
public void testCreateProductWithExistingProduct() {
    when(productRepository.save(any(Product.class))).thenReturn(testProduct);
    Product createdProduct = productController.createProduct(testProduct);
    assertNotNull(createdProduct);
    assertEquals(testProduct.getName(), createdProduct.getName());
}

	/*
	 * ROOST_METHOD_HASH=createProduct_60409495d0
	 * ROOST_METHOD_SIG_HASH=createProduct_5b0158b3eb
	 *
	 */@Test
	@Tag("invalid")
	public void testCreateProductWithIncompleteProductData() {
		Product incompleteProduct = new Product();
		assertThrows(IllegalArgumentException.class, () -> {
			productController.createProduct(incompleteProduct);
		});
	}

	/*
	 * ROOST_METHOD_HASH=getProductById_33a5e4d3c9
	 * ROOST_METHOD_SIG_HASH=getProductById_33a5e4d3c9
	 *
	 */@BeforeEach
	public void setup() {
		productRepository = Mockito.mock(ProductRepository.class);
	}

	/*
	 * ROOST_METHOD_HASH=getProductById_33a5e4d3c9
	 * ROOST_METHOD_SIG_HASH=getProductById_33a5e4d3c9
	 *
	 */@Test
	@Tag("valid")
	public void shouldReturnProductWhenIdIsValid() {
		Product mockProduct = new Product();
		mockProduct.setName("Product 1");
		mockProduct.setDescription("Description 1");
		mockProduct.setPrice(10.0);
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(mockProduct));
		ResponseEntity<Product> response = productController.getProductById(1L);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(mockProduct, response.getBody());
	}

	/*
	 * ROOST_METHOD_HASH=getProductById_33a5e4d3c9
	 * ROOST_METHOD_SIG_HASH=getProductById_33a5e4d3c9
	 *
	 */@Test
@Tag("invalid")
public void shouldReturnNotFoundWhenIdIsInvalid() {
    when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
    ResponseEntity<Product> response = productController.getProductById(1L);
    assertEquals(404, response.getStatusCodeValue());
}

	/*
	 * ROOST_METHOD_HASH=getProductById_33a5e4d3c9
	 * ROOST_METHOD_SIG_HASH=getProductById_33a5e4d3c9
	 *
	 */@Test
	@Tag("invalid")
	public void shouldThrowExceptionWhenIdIsNull() {
		assertThrows(IllegalArgumentException.class, () -> productController.getProductById(null));
	}

	/*
	 * ROOST_METHOD_HASH=updateProduct_850f4057dd
	 * ROOST_METHOD_SIG_HASH=updateProduct_850f4057dd
	 *
	 */@Test
	@Tag("valid")
	public void testProductExistsAndUpdated() {
		Product existingProduct = new Product();
		existingProduct.setId(1L);
		existingProduct.setName("Existing Name");
		existingProduct.setDescription("Existing Description");
		existingProduct.setPrice(50.00);
		Product newProduct = new Product();
		newProduct.setId(1L);
		newProduct.setName("New Name");
		newProduct.setDescription("New Description");
		newProduct.setPrice(30.00);
		when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(existingProduct));
		when(productRepository.save(any(Product.class))).thenReturn(newProduct);
		ResponseEntity<Product> responseEntity = productController.updateProduct(1L, newProduct);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(newProduct, responseEntity.getBody());
	}

	/*
	 * ROOST_METHOD_HASH=updateProduct_850f4057dd
	 * ROOST_METHOD_SIG_HASH=updateProduct_850f4057dd
	 *
	 */@Test
	@Tag("invalid")
	public void testProductDoesNotExist() {
		Product nonExistentProduct = new Product();
		nonExistentProduct.setId(2L);
		nonExistentProduct.setName("Name");
		nonExistentProduct.setDescription("Description");
		nonExistentProduct.setPrice(30.00);
		when(productRepository.findById(2L)).thenReturn(java.util.Optional.empty());
		ResponseEntity<Product> responseEntity = productController.updateProduct(2L, nonExistentProduct);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	/*
	 * ROOST_METHOD_HASH=updateProduct_850f4057dd
	 * ROOST_METHOD_SIG_HASH=updateProduct_850f4057dd
	 *
	 */@Test
	@Tag("invalid")
	public void testProductUpdateWithInvalidDetails() {
		Product productWithInvalidData = new Product();
		productWithInvalidData.setId(1L);
		productWithInvalidData.setName("");
		productWithInvalidData.setDescription("");

		productWithInvalidData.setPrice(-20.00);
		Product existingProduct = new Product();
		existingProduct.setId(1L);
		existingProduct.setName("Existing Name");
		existingProduct.setDescription("Existing Description");
		existingProduct.setPrice(50.00);
		when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(existingProduct));
		ResponseEntity<Product> responseEntity = productController.updateProduct(1L, productWithInvalidData);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	/*
	 * ROOST_METHOD_HASH=deleteProduct_032472106e
	 * ROOST_METHOD_SIG_HASH=deleteProduct_032472106e
	 *
	 */@Test
	@Tag("valid")
	public void deleteProductByIdTest() {

		Product p = new Product();
		p.setId(1L);
		Product savedProduct = productRepository.save(p);

		when(productRepository.findById(savedProduct.getId())).thenReturn(Optional.of(savedProduct));
		ResponseEntity<Object> result = productController.deleteProduct(savedProduct.getId());

		assertSame(ResponseEntity.ok().build(), result);
	}

	/*
	 * ROOST_METHOD_HASH=deleteProduct_032472106e
	 * ROOST_METHOD_SIG_HASH=deleteProduct_032472106e
	 *
	 */@Test
	@Tag("invalid")
	public void deleteNonExistentProductTest() {

		Long invalidId = 1000L;

		when(productRepository.findById(invalidId)).thenReturn(Optional.empty());
		ResponseEntity<Object> result = productController.deleteProduct(invalidId);

		assertSame(ResponseEntity.notFound().build(), result);
	}

	/*
	 * ROOST_METHOD_HASH=deleteProduct_032472106e
	 * ROOST_METHOD_SIG_HASH=deleteProduct_032472106e
	 *
	 */@Test
	@Tag("invalid")
	public void deleteProductWithNullIdTest() {

		Long nullId = null;

		assertThrows(NullPointerException.class, () -> productController.deleteProduct(nullId));
	}

}