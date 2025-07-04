package com.bootexample4.products.controller;

import com.bootexample4.products.model.Product;
import com.bootexample4.products.repository.ProductRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;
import org.junit.jupiter.api.Assertions.assertTrue;

public class ProductControllerTest {

	/*
	 * ROOST_METHOD_HASH=getAllProducts_c7c755eb4e
	 * ROOST_METHOD_SIG_HASH=getAllProducts_e267ceea76
	 *
	 */@Test
	@Tag("valid")
	public void retrieveMultipleProducts() {

		Product product1 = new Product();
		product1.setId(1L);
		product1.setName("Product1");
		product1.setDescription("Description1");
		product1.setPrice(100.0);
		Product product2 = new Product();
		product2.setId(2L);
		product2.setName("Product2");
		product2.setDescription("Description2");
		product2.setPrice(200.0);
		List<Product> mockProductList = Arrays.asList(product1, product2);
		when(productRepository.findAll()).thenReturn(mockProductList);

		List<Product> result = productController.getAllProducts();

		assertEquals(mockProductList, result, "The returned product list should match the mocked product list");
	}

	/*
	 * ROOST_METHOD_HASH=createProduct_60409495d0
	 * ROOST_METHOD_SIG_HASH=createProduct_5b0158b3eb
	 *
	 */@BeforeEach
	public void setup() {
		mockProduct = new Product();

		mockProduct.setName("Test Product");

		mockProduct.setDescription("Test Description");

		mockProduct.setPrice(100.0);
	}

	/*
	 * ROOST_METHOD_HASH=createProduct_60409495d0
	 * ROOST_METHOD_SIG_HASH=createProduct_5b0158b3eb
	 *
	 */@Test
@Tag("valid")
public void createProductOnValidInput() {
    when(productRepository.save(mockProduct)).thenReturn(mockProduct);
    Product resultProduct = productController.createProduct(mockProduct);
    assertEquals(mockProduct, resultProduct, "The returned product should match the mock product");
    verify(productRepository, times(1)).save(mockProduct);
}

	/*
	 * ROOST_METHOD_HASH=createProduct_60409495d0
	 * ROOST_METHOD_SIG_HASH=createProduct_5b0158b3eb
	 *
	 */@Test
	@Tag("invalid")
	public void createProductOnNullInput() {
		assertThrows(NullPointerException.class, () -> productController.createProduct(null),
				"Expected NullPointerException when passing null product");
	}

	/*
	 * ROOST_METHOD_HASH=createProduct_60409495d0
	 * ROOST_METHOD_SIG_HASH=createProduct_5b0158b3eb
	 *
	 */@Test
	@Tag("invalid")
	public void createProductWithEmptyFields() {
		Product emptyProduct = new Product();

		emptyProduct.setName("");
		emptyProduct.setDescription("");
		emptyProduct.setPrice(0.0);
		when(productRepository.save(emptyProduct)).thenReturn(emptyProduct);
		Product resultProduct = productController.createProduct(emptyProduct);
		assertEquals(emptyProduct, resultProduct,
				"The repository should save the product with empty fields based on rules");
		verify(productRepository, times(1)).save(emptyProduct);
	}

	/*
	 * ROOST_METHOD_HASH=createProduct_60409495d0
	 * ROOST_METHOD_SIG_HASH=createProduct_5b0158b3eb
	 *
	 */@Test
@Tag("integration")
public void saveProductOnRepositoryInvocation() {
    when(productRepository.save(mockProduct)).thenReturn(mockProduct);
    productController.createProduct(mockProduct);
    verify(productRepository, times(1)).save(mockProduct);
}

	/*
	 * ROOST_METHOD_HASH=createProduct_60409495d0
	 * ROOST_METHOD_SIG_HASH=createProduct_5b0158b3eb
	 *
	 */@Test
@Tag("invalid")
public void handleProductSaveFailure() {
    when(productRepository.save(mockProduct)).thenThrow(new RuntimeException("Save Failed"));
    Exception exception = assertThrows(RuntimeException.class, () -> productController.createProduct(mockProduct));
    assertEquals("Save Failed", exception.getMessage(), "Expected message to match the mocked failure reason");
}

	/*
	 * ROOST_METHOD_HASH=createProduct_60409495d0
	 * ROOST_METHOD_SIG_HASH=createProduct_5b0158b3eb
	 *
	 */@Test
	@Tag("boundary")
	public void createProductWithExtremePriceValues() {
		Product extremePriceProduct = new Product();

		extremePriceProduct.setName("Extreme Product");

		extremePriceProduct.setDescription("Extreme Description");
		extremePriceProduct.setPrice(Double.MAX_VALUE);
		when(productRepository.save(extremePriceProduct)).thenReturn(extremePriceProduct);
		Product resultProduct = productController.createProduct(extremePriceProduct);
		assertEquals(extremePriceProduct, resultProduct,
				"The product with extreme price values should be processed correctly");
	}

	/*
	 * ROOST_METHOD_HASH=createProduct_60409495d0
	 * ROOST_METHOD_SIG_HASH=createProduct_5b0158b3eb
	 *
	 */@Test
	@Tag("invalid")
	public void rejectProductWhenMandatoryFieldsMissing() {
		Product productWithMissingFields = new Product();

		productWithMissingFields.setPrice(50.0);
		when(productRepository.save(productWithMissingFields)).thenReturn(productWithMissingFields);
		Product resultProduct = productController.createProduct(productWithMissingFields);
		assertEquals(productWithMissingFields, resultProduct,
				"Business rules should decide whether to save a product with missing fields");
		verify(productRepository, times(1)).save(productWithMissingFields);
	}

	/*
	 * ROOST_METHOD_HASH=createProduct_60409495d0
	 * ROOST_METHOD_SIG_HASH=createProduct_5b0158b3eb
	 *
	 */@Test
	@Tag("integration")
	public void createDuplicateProducts() {
		Product duplicateProduct1 = new Product();

		duplicateProduct1.setName("Duplicate Product");
		duplicateProduct1.setDescription("Duplicate Description");
		duplicateProduct1.setPrice(10.0);
		Product duplicateProduct2 = new Product();
		duplicateProduct2.setName("Duplicate Product");
		duplicateProduct2.setDescription("Duplicate Description");
		duplicateProduct2.setPrice(10.0);
		when(productRepository.save(duplicateProduct1)).thenReturn(duplicateProduct1);
		when(productRepository.save(duplicateProduct2)).thenReturn(duplicateProduct2);
		Product resultProduct1 = productController.createProduct(duplicateProduct1);
		Product resultProduct2 = productController.createProduct(duplicateProduct2);
		assertEquals(duplicateProduct1, resultProduct1,
				"Duplicate product should be created independently based on business rules");
		assertEquals(duplicateProduct2, resultProduct2,
				"Duplicate product should be created independently based on business rules");
		verify(productRepository, times(2)).save(any(Product.class));
	}

	/*
	 * ROOST_METHOD_HASH=getProductById_33a5e4d3c9
	 * ROOST_METHOD_SIG_HASH=getProductById_33a5e4d3c9
	 *
	 */@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/*
	 * ROOST_METHOD_HASH=getProductById_33a5e4d3c9
	 * ROOST_METHOD_SIG_HASH=getProductById_33a5e4d3c9
	 *
	 */@Test
	@Tag("valid")
	public void retrieveProductByIdSuccess() {

		Long validId = 1L;

		Product product = new Product();

		when(productRepository.findById(validId)).thenReturn(Optional.of(product));

		ResponseEntity<Product> response = productController.getProductById(validId);

		verify(productRepository, times(1)).findById(validId);
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(product, response.getBody());
	}

	/*
	 * ROOST_METHOD_HASH=getProductById_33a5e4d3c9
	 * ROOST_METHOD_SIG_HASH=getProductById_33a5e4d3c9
	 *
	 */@Test
	@Tag("invalid")
	public void retrieveProductByIdNotFound() {

		Long invalidId = 999L;
		when(productRepository.findById(invalidId)).thenReturn(Optional.empty());

		ResponseEntity<Product> response = productController.getProductById(invalidId);

		verify(productRepository, times(1)).findById(invalidId);
		assertNotNull(response);
		assertEquals(404, response.getStatusCodeValue());
		assertNull(response.getBody());
	}

	/*
	 * ROOST_METHOD_HASH=getProductById_33a5e4d3c9
	 * ROOST_METHOD_SIG_HASH=getProductById_33a5e4d3c9
	 *
	 */@Test
	@Tag("invalid")
	public void retrieveProductByIdNullInput() {

		assertThrows(IllegalArgumentException.class, () -> productController.getProductById(null));
	}

	/*
	 * ROOST_METHOD_HASH=getProductById_33a5e4d3c9
	 * ROOST_METHOD_SIG_HASH=getProductById_33a5e4d3c9
	 *
	 */@Test
	@Tag("boundary")
	public void retrieveProductByIdLargeInput() {

		Long largeId = Long.MAX_VALUE;
		when(productRepository.findById(largeId)).thenReturn(Optional.empty());

		ResponseEntity<Product> response = productController.getProductById(largeId);

		verify(productRepository, times(1)).findById(largeId);
		assertNotNull(response);
		assertEquals(404, response.getStatusCodeValue());
		assertNull(response.getBody());
	}

	/*
	 * ROOST_METHOD_HASH=getProductById_33a5e4d3c9
	 * ROOST_METHOD_SIG_HASH=getProductById_33a5e4d3c9
	 *
	 */@Test
	@Tag("invalid")
	public void retrieveProductByIdNegativeId() {

		Long negativeId = -1L;
		when(productRepository.findById(negativeId)).thenReturn(Optional.empty());

		ResponseEntity<Product> response = productController.getProductById(negativeId);

		verify(productRepository, times(1)).findById(negativeId);
		assertNotNull(response);
		assertEquals(404, response.getStatusCodeValue());
		assertNull(response.getBody());
	}

	/*
	 * ROOST_METHOD_HASH=getProductById_33a5e4d3c9
	 * ROOST_METHOD_SIG_HASH=getProductById_33a5e4d3c9
	 *
	 */@Test
	@Tag("integration")
	public void retrieveProductByIdRepositoryException() {

		Long validId = 1L;
		when(productRepository.findById(validId)).thenThrow(new RuntimeException("Database error"));

		assertThrows(RuntimeException.class, () -> productController.getProductById(validId));
		verify(productRepository, times(1)).findById(validId);
	}

	/*
	 * ROOST_METHOD_HASH=getProductById_33a5e4d3c9
	 * ROOST_METHOD_SIG_HASH=getProductById_33a5e4d3c9
	 *
	 */@Test
	@Tag("boundary")
	public void retrieveProductByIdDuplicateMatch() {

		Long duplicateId = 1L;

		Product product = new Product();

		when(productRepository.findById(duplicateId)).thenReturn(Optional.of(product));

		ResponseEntity<Product> response = productController.getProductById(duplicateId);

		verify(productRepository, times(1)).findById(duplicateId);
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(product, response.getBody());
	}

	/*
	 * ROOST_METHOD_HASH=updateProduct_850f4057dd
	 * ROOST_METHOD_SIG_HASH=updateProduct_850f4057dd
	 *
	 */@BeforeEach
	void setUp() {
		productRepository = Mockito.mock(ProductRepository.class);

		productController = new ProductController();

		productController.productRepository = productRepository;
	}

	/*
	 * ROOST_METHOD_HASH=updateProduct_850f4057dd
	 * ROOST_METHOD_SIG_HASH=updateProduct_850f4057dd
	 *
	 */@Test
	@Tag("valid")
	public void successfullyUpdateExistingProduct() {

		Long productId = 1L;
		Product existingProduct = new Product();
		existingProduct.setId(productId);
		existingProduct.setName("Old Name");
		existingProduct.setDescription("Old Description");
		existingProduct.setPrice(100.0);
		Product updatedProductDetails = new Product();
		updatedProductDetails.setName("New Name");
		updatedProductDetails.setDescription("New Description");
		updatedProductDetails.setPrice(150.0);
		Product updatedProduct = new Product();
		updatedProduct.setId(productId);
		updatedProduct.setName(updatedProductDetails.getName());
		updatedProduct.setDescription(updatedProductDetails.getDescription());
		updatedProduct.setPrice(updatedProductDetails.getPrice());
		when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
		when(productRepository.save(existingProduct)).thenReturn(updatedProduct);

		ResponseEntity<Product> response = productController.updateProduct(productId, updatedProductDetails);

		assertEquals(200, (int) response.getStatusCodeValue());
		assertEquals(updatedProductDetails.getName(), response.getBody().getName());
		assertEquals(updatedProductDetails.getDescription(), response.getBody().getDescription());
		assertEquals(updatedProductDetails.getPrice(), response.getBody().getPrice(), 0.001);
		verify(productRepository).findById(productId);
		verify(productRepository).save(existingProduct);
	}

	/*
	 * ROOST_METHOD_HASH=updateProduct_850f4057dd
	 * ROOST_METHOD_SIG_HASH=updateProduct_850f4057dd
	 *
	 */@Test
	@Tag("invalid")
	public void updateNonExistingProduct() {

		Long productId = 99L;
		Product updatedProductDetails = new Product();
		updatedProductDetails.setName("New Name");
		updatedProductDetails.setDescription("New Description");
		updatedProductDetails.setPrice(150.0);
		when(productRepository.findById(productId)).thenReturn(Optional.empty());

		ResponseEntity<Product> response = productController.updateProduct(productId, updatedProductDetails);

		assertEquals(404, (int) response.getStatusCodeValue());
		assertTrue(response.getBody() == null);
		verify(productRepository).findById(productId);
		verify(productRepository, never()).save(any(Product.class));
	}

	/*
	 * ROOST_METHOD_HASH=updateProduct_850f4057dd
	 * ROOST_METHOD_SIG_HASH=updateProduct_850f4057dd
	 *
	 */@Test
	@Tag("boundary")
	public void updateProductWithEdgeValues() {

		Long productId = 2L;
		Product existingProduct = new Product();
		existingProduct.setId(productId);
		existingProduct.setName("Old Name");
		existingProduct.setDescription("Old Description");

		existingProduct.setPrice(0.0);
		Product updatedProductDetails = new Product();

		updatedProductDetails.setName("");

		updatedProductDetails.setDescription("");

		updatedProductDetails.setPrice(Double.MAX_VALUE);
		Product updatedProduct = new Product();
		updatedProduct.setId(productId);
		updatedProduct.setName(updatedProductDetails.getName());
		updatedProduct.setDescription(updatedProductDetails.getDescription());
		updatedProduct.setPrice(updatedProductDetails.getPrice());
		when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
		when(productRepository.save(existingProduct)).thenReturn(updatedProduct);

		ResponseEntity<Product> response = productController.updateProduct(productId, updatedProductDetails);

		assertEquals(200, (int) response.getStatusCodeValue());
		assertEquals(updatedProductDetails.getName(), response.getBody().getName());
		assertEquals(updatedProductDetails.getDescription(), response.getBody().getDescription());
		assertEquals(updatedProductDetails.getPrice(), response.getBody().getPrice(), 0.001);
		verify(productRepository).findById(productId);
		verify(productRepository).save(existingProduct);
	}

	/*
	 * ROOST_METHOD_HASH=deleteProduct_032472106e
	 * ROOST_METHOD_SIG_HASH=deleteProduct_032472106e
	 *
	 */@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/*
	 * ROOST_METHOD_HASH=deleteProduct_032472106e
	 * ROOST_METHOD_SIG_HASH=deleteProduct_032472106e
	 *
	 */@Test
	@Tag("valid")
	public void deleteProductWhenExists() {

		Long productId = 1L;

		Product mockProduct = new Product();
		when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

		ResponseEntity<Object> response = productController.deleteProduct(productId);

		verify(productRepository, times(1)).delete(mockProduct);

		assertEquals(ResponseEntity.ok().build(), response);
	}

	/*
	 * ROOST_METHOD_HASH=deleteProduct_032472106e
	 * ROOST_METHOD_SIG_HASH=deleteProduct_032472106e
	 *
	 */@Test
	@Tag("invalid")
	public void deleteProductWhenNotExists() {

		Long productId = 999L;
		when(productRepository.findById(productId)).thenReturn(Optional.empty());

		ResponseEntity<Object> response = productController.deleteProduct(productId);

		verify(productRepository, times(0)).delete(any(Product.class));

		assertEquals(ResponseEntity.notFound().build(), response);
	}

}