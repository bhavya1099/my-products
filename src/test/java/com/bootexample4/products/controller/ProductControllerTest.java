
// ********RoostGPT********
/*

roost_feedback [04/07/2025, 1:33:41 PM]:remove compilation errors \n\n
*/

// ********RoostGPT********

package com.bootexample4.products.controller;

import com.bootexample4.products.model.Product;
import com.bootexample4.products.repository.ProductRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class ProductControllerTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductController productController;

    private Product mockProduct;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockProduct = new Product();
        mockProduct.setName("Test Product");
        mockProduct.setDescription("Test Description");
        mockProduct.setPrice(100.0);
    }

    @Test
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

    @Test
    @Tag("valid")
    public void createProductOnValidInput() {
        when(productRepository.save(mockProduct)).thenReturn(mockProduct);
        Product resultProduct = productController.createProduct(mockProduct);
        assertEquals(mockProduct, resultProduct, "The returned product should match the mock product");
        verify(productRepository, times(1)).save(mockProduct);
    }

    @Test
    @Tag("invalid")
    public void createProductOnNullInput() {
        assertThrows(NullPointerException.class, () -> productController.createProduct(null),
                "Expected NullPointerException when passing null product");
    }

    @Test
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

    @Test
    @Tag("integration")
    public void saveProductOnRepositoryInvocation() {
        when(productRepository.save(mockProduct)).thenReturn(mockProduct);
        productController.createProduct(mockProduct);
        verify(productRepository, times(1)).save(mockProduct);
    }

    @Test
    @Tag("invalid")
    public void handleProductSaveFailure() {
        when(productRepository.save(mockProduct)).thenThrow(new RuntimeException("Save Failed"));
        Exception exception = assertThrows(RuntimeException.class, () -> productController.createProduct(mockProduct));
        assertEquals("Save Failed", exception.getMessage(), "Expected message to match the mocked failure reason");
    }

    @Test
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

    @Test
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
}
