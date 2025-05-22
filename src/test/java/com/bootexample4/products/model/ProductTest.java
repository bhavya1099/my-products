package com.bootexample4.products.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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

}