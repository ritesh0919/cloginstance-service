/* Copyright (C) 2014 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.server.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * FilterTest.
 * 
 */
public class FilterTest {

	private Filter filter;

	/**
	 * setUp.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.filter = new Filter();
	}

	/**
	 * createBool.
	 * 
	 * @return Bool
	 */
	private Bool createBool() {
		final Bool bool = new Bool();
		final List<Should> should = new ArrayList<>();
		final Term filter = new Term();
		final Should should1 = new Should();
		should1.setAdditionalProperty("TEST", "TEST");
		filter.setGroupId("12345");
		filter.setX_realm("REALM_ID");
		should1.setTerm(filter);

		should.add(should1);

		bool.setShould(should);
		bool.setAdditionalProperty("TEST", "TEST");
		return bool;
	}

	/**
	 * tearDown.
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * testGet.
	 * 
	 */
	@Test
	public void testBool() {

		this.filter.setBool(this.createBool());
		assertEquals(filter.getBool().getShould().get(0).getTerm().getGroupId(), "12345");
		assertEquals(filter.getBool().getShould().get(0).getTerm().getX_realm(), "REALM_ID");

	}

	@Test
	public void testAdditionalProperty() {
		this.filter.setAdditionalProperty("NAME", "VALUE");
		Map<String, Object> addProp = this.filter.getAdditionalProperties();

		Set<String> keys = addProp.keySet();
		for (String key : keys) {
			Assert.assertEquals(key, "NAME");

			String value = (String) addProp.get(key);

			Assert.assertEquals(value, "VALUE");
		}
	}

}
