package com.bootexample4.products.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import com.bootexample4.products.model.Product;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions.assertNull;
import org.mockito.Mock;
import java.io.PrintStream;

public class ProductTest {

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

	/*
	 * ROOST_METHOD_HASH=getId_addb608839 ROOST_METHOD_SIG_HASH=getId_ba349b1eff
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
	 * ROOST_METHOD_HASH=getId_addb608839 ROOST_METHOD_SIG_HASH=getId_ba349b1eff
	 *
	 */@Test
	@Tag("invalid")
	public void verifyNullIdReturn() {

		Product product = new Product();

		Long actualId = product.getId();

		assertNull(actualId, "The id should be null if not set");
	}

	/*
	 * ROOST_METHOD_HASH=getId_addb608839 ROOST_METHOD_SIG_HASH=getId_ba349b1eff
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
	 * ROOST_METHOD_HASH=getId_addb608839 ROOST_METHOD_SIG_HASH=getId_ba349b1eff
	 *
	 */@Test
	@Tag("valid")
	public void verifyGetIdMethodPrint() {

		Product product = PowerMockito.spy(new Product());
		Long expectedId = 1000L;
		product.setId(expectedId);

		Product.getId();

		Mockito.verify(product, Mockito.times(1)).println("inside get id function");
	}

	/*
	 * ROOST_METHOD_HASH=getName_bb63e7b83f ROOST_METHOD_SIG_HASH=getName_8400ac6fb7
	 *
	 */@Test
	@Tag("valid")
	public void getNamePrintsInsideGetNameFunction() {
		String testName = "Test product";
		Product product = new Product();
		product.setName(testName);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		product.getName();

		assertTrue(outContent.toString().contains("inside get name function"),
				"The method should print 'inside get name function'.");
	}

	/*
	 * ROOST_METHOD_HASH=getDescription_ef9e262f97
	 * ROOST_METHOD_SIG_HASH=getDescription_b1844ea396
	 *
	 */@Test
	public void getDescriptionWhenDescriptionIsNull() {
		Product product = new Product();
		assertNull(product.getDescription(), "Description should be null when not set");
	}

	/*
	 * ROOST_METHOD_HASH=getDescription_ef9e262f97
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
	 * ROOST_METHOD_HASH=getDescription_ef9e262f97
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
	 * ROOST_METHOD_HASH=getDescription_ef9e262f97
	 * ROOST_METHOD_SIG_HASH=getDescription_b1844ea396
	 *
	 */@Test
	public void testGetDescriptionPrintsMessage() {
		Product product = new Product();
		PrintStream originalOut = System.out;
		try {
			System.setOut(new PrintStream(outContent));
			String description = "Test Product";
			product.setDescription(description);
			product.getDescription();
			assertTrue(outContent.toString().contains("inside get get description function"),
					"GetDescription function should print specific debug message");
		}
		finally {
			System.setOut(originalOut);
		}
	}

}