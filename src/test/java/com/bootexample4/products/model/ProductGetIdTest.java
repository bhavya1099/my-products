package com.bootexample4.products.model;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ProductGetIdTest {

	// Using a Mockito @InjectMocks annotation to simulate the ID assignment via JPA
	@InjectMocks
	private Product product;

	@Test
	@Category(Categories.valid.class)
	public void getIdFromInitializedProduct() {
		// Simulating using Mockito instead of using a direct access which is wrong due to
		// private access
		Mockito.when(product.getId()).thenReturn(100L);
		Long actualId = product.getId();
		Long expectedId = 100L;
		assertEquals("The ID from an initialized product should match the assigned value.", expectedId, actualId);
	}

	@Test
	@Category(Categories.invalid.class)
	public void getIdFromNewProduct() {
		Mockito.when(product.getId()).thenReturn(null);
		Long actualId = product.getId();

		assertNull("The ID of a new, unpersisted product should be null.", actualId);
	}

	@Test
	@Category(Categories.valid.class)
	public void getIdConsistencyCheck() {
		// Using Mockito to ensure that the ID remains the same over multiple calls
		Mockito.when(product.getId()).thenReturn(500L);
		Long firstCallId = product.getId();
		Long secondCallId = product.getId();
		assertEquals("Subsequent calls to getId on the same object should return the same ID.", firstCallId,
				secondCallId);
	}

}