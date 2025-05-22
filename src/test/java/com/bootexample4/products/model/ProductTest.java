package com.bootexample4.products.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions.assertNull;
import com.bootexample4.products.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public class ProductTest {

	/*
	 * ROOST_METHOD_HASH=getId_7023725436 ROOST_METHOD_SIG_HASH=getId_ba349b1eff
	 *
	 */@Test
	@Tag("valid")
	public void verifyGetIdReturnValue() {

		Product product = new Product();
		Long expectedId = 1000L;
		product.setId(expectedId);

		Long actualId = product.getId();

		assertNotNull(actualId, "The id should not be null");
		assertEquals(expectedId, actualId, "The returned id should match the set id");
	}

	/*
	 * ROOST_METHOD_HASH=getId_7023725436 ROOST_METHOD_SIG_HASH=getId_ba349b1eff
	 *
	 */@Test
	@Tag("invalid")
	public void verifyNullIdReturn() {

		Product product = new Product();

		Long actualId = product.getId();

		assertNull(actualId, "The id should be null if not set");
	}

	/*
	 * ROOST_METHOD_HASH=getId_7023725436 ROOST_METHOD_SIG_HASH=getId_ba349b1eff
	 *
	 */@Test
	@Tag("boundary")
	public void verifyExtremeValueIdReturn() {

		Product product = new Product();
		Long maxLongValue = Long.MAX_VALUE;
		product.setId(maxLongValue);

		Long actualId = product.getId();

		assertNotNull(actualId, "The id should not be null");
		assertEquals(maxLongValue, actualId, "The returned id should match the set id for max long value");

		Long minLongValue = Long.MIN_VALUE;
		product.setId(minLongValue);

		actualId = product.getId();

		assertNotNull(actualId, "The id should not be null");
		assertEquals(minLongValue, actualId, "The returned id should match the set id for min long value");
	}

	/*
	 * ROOST_METHOD_HASH=getName_3a12ffc596 ROOST_METHOD_SIG_HASH=getName_8400ac6fb7
	 *
	 */@Test
	@Tag("boundary")
	public void getNameReturnsNullWhenUnset() {
		Product product = new Product();
		assertNull(product.getName(), "The name should be NULL since it wasn't set.");
	}

	/*
	 * ROOST_METHOD_HASH=getName_3a12ffc596 ROOST_METHOD_SIG_HASH=getName_8400ac6fb7
	 *
	 */@Test
	@Tag("valid")
	public void getNameReturnsCorrectlyWhenSet() {
		String testName = "Test product";
		Product product = new Product();
		product.setName(testName);
		assertEquals(testName, product.getName(), "The returned name should match the set name.");
	}

	/*
	 * ROOST_METHOD_HASH=getName_3a12ffc596 ROOST_METHOD_SIG_HASH=getName_8400ac6fb7
	 *
	 */@Test
	@Tag("valid")
	public void getNameReturnsConsistentlyAcrossMultipleCalls() {
		String testName = "Test product";
		Product product = new Product();
		product.setName(testName);
		for (int i = 0; i < 10; i++)
			assertEquals(testName, product.getName(), "The returned name should be consistent across multiple calls.");
	}

	/*
	 * ROOST_METHOD_HASH=getName_3a12ffc596 ROOST_METHOD_SIG_HASH=getName_8400ac6fb7
	 *
	 */@Test
	@Tag("invalid")
	public void getNameReturnsEmptyWhenNameIsSetEmpty() {
		Product product = new Product();
		product.setName("");
		assertEquals("", product.getName(), "The returned name should be an empty string.");
	}

	/*
	 * ROOST_METHOD_HASH=getName_3a12ffc596 ROOST_METHOD_SIG_HASH=getName_8400ac6fb7
	 *
	 */@Test
	@Tag("valid")
	public void getNameReturnsSensitiveCase() {
		String testName = "tEsT PrOdUcT";
		Product product = new Product();
		product.setName(testName);
		assertEquals(testName, product.getName(), "The returned name should match the original case.");
	}

	/*
	 * ROOST_METHOD_HASH=getDescription_791d670f82
	 * ROOST_METHOD_SIG_HASH=getDescription_b1844ea396
	 *
	 */@Test
	public void getDescriptionWhenDescriptionIsNull() {
		Product product = new Product();
		assertNull(product.getDescription(), "Description should be null when not set");
	}

	/*
	 * ROOST_METHOD_HASH=getDescription_791d670f82
	 * ROOST_METHOD_SIG_HASH=getDescription_b1844ea396
	 *
	 */@Test
	public void getDescriptionReturnsCorrectDescription() {
		Product product = new Product();
		product.setDescription("A high quality product");
		assertEquals("A high quality product", product.getDescription(),
				"getDescription should return the correct description when one is set");
	}

	/*
	 * ROOST_METHOD_HASH=getDescription_791d670f82
	 * ROOST_METHOD_SIG_HASH=getDescription_b1844ea396
	 *
	 */@Test
	public void getDescriptionWithEmptyString() {
		Product product = new Product();
		product.setDescription("");
		assertEquals("", product.getDescription(),
				"getDescription should return an empty string when description is set as empty string");
	}

	/*
	 * ROOST_METHOD_HASH=getPrice_b54117587b ROOST_METHOD_SIG_HASH=getPrice_d2cb73a47d
	 *
	 */@BeforeEach
	public void setup() {
		product = new Product();
	}

	/*
	 * ROOST_METHOD_HASH=getPrice_b54117587b ROOST_METHOD_SIG_HASH=getPrice_d2cb73a47d
	 *
	 */@Test
	@Tag("valid")
	public void testReturnedPrice() {

		double predefinedPrice = 120.50;
		product.setPrice(predefinedPrice);

		double returnedPrice = product.getPrice();

		assertEquals(predefinedPrice, returnedPrice, "The returned product price should match the predefined price.");
	}

	/*
	 * ROOST_METHOD_HASH=getPrice_b54117587b ROOST_METHOD_SIG_HASH=getPrice_d2cb73a47d
	 *
	 */@Test
	@Tag("valid")
	public void testDefaultPrice() {

		double defaultPrice = 0.0;

		double returnedPrice = product.getPrice();

		assertEquals(defaultPrice, returnedPrice,
				"The returned product price should match default value when price is not set.");
	}

	/*
	 * ROOST_METHOD_HASH=getPrice_b54117587b ROOST_METHOD_SIG_HASH=getPrice_d2cb73a47d
	 *
	 */@Test
	@Tag("valid")
	public void testMultiplePriceSetsGets() {

		double firstPrice = 100.20;
		product.setPrice(firstPrice);
		assertEquals(firstPrice, product.getPrice(), "The returned product price should match the first set price.");
		double secondPrice = 200.30;
		product.setPrice(secondPrice);
		assertEquals(secondPrice, product.getPrice(), "The returned product price should match the second set price.");
		double thirdPrice = 300.40;
		product.setPrice(thirdPrice);
		assertEquals(thirdPrice, product.getPrice(), "The returned product price should match the third set price.");
	}

}