package com.bootexample4.products.controller;

import org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.ArgumentMatchers.anyLong;
import org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.bootexample4.products.model.Product;
import com.bootexample4.products.repository.ProductRepository;
import org.junit.jupiter.api;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation;

public class ProductController262Test {

	@BeforeEach
	public void setUp() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}

	@Test
	@Tag("valid")
	public void testGetProductByIdWithValidId() {
		Product product = new Product();
		product.setId(1L);
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
		ResponseEntity<Product> responseEntity = productController.getProductById(1L);
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(1L, responseEntity.getBody().getId().longValue());
	}

@Test
@Tag("invalid")
public void testGetProductByIdWithInvalidId() {
    when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
    ResponseEntity<Product> responseEntity = productController.getProductById(1L);
    assertEquals(404, responseEntity.getStatusCodeValue());
}

	@Test
	@Tag("boundary")
	public void testGetProductByIdWithNullId() {
		try {
			productController.getProductById(null);
		}
		catch (Exception ex) {
			assertTrue(ex instanceof IllegalArgumentException || ex instanceof NullPointerException);
		}
	}

}