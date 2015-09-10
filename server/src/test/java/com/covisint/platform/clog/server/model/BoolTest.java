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
 * boolTest.
 * 
 */
public class BoolTest {
	
	private Bool bool;

	/**
	 * setup.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.bool = new Bool();
	}

	/**
	 * createShould.
	 * 
	 * @return List
	 */
	private List<Should> createShould() {
		final List<Should> should = new ArrayList<>();
		final Term t1 = new Term();
		final Should should1 = new Should();
		t1.setGroupId("12345");
		t1.setX_realm("REALM_ID");
		should1.setTerm(t1);

		should.add(should1);

		return should;
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
	 * Test method for
	 * {@link com.covisint.platform.clog.server.model.Action#getShould()}.
	 */
	@Test
	public void testGetShould() {
		this.bool.setShould(this.createShould());
		assertEquals(bool.getShould().get(0).getTerm().getGroupId(), "12345");
		assertEquals(bool.getShould().get(0).getTerm().getX_realm(), "REALM_ID");

	}

	@Test
	public void testAdditionalProperty() {
		this.bool.setAdditionalProperty("NAME", "VALUE");
		Map<String, Object> addProp = this.bool.getAdditionalProperties();
		
		Set<String> keys = addProp.keySet();
		for (String key : keys) {
			Assert.assertEquals(key, "NAME");
			
			String value = (String) addProp.get(key);
			
			Assert.assertEquals(value, "VALUE");
		}
	}

}
