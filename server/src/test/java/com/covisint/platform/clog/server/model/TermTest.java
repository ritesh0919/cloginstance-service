/* Copyright (C) 2014 Covisint. All Rights Reserved. */

package com.covisint.platform.clog.server.model;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * TermTest.
 * 
 */
public class TermTest {

	private Term term;

	/**
	 * setup.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.term = new Term(); 
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
	 * testgetGroupId.
	 * 
	 */
	@Test
	public void testTerm() {
		this.term.setGroupId("12345");
		this.term.setX_realm("REALM_ID");
		assertEquals(term.getGroupId(), "12345");
		assertEquals(term.getX_realm(), "REALM_ID");
	}

	@Test
	public void testAdditionalProperty() {
		this.term.setAdditionalProperty("NAME", "VALUE");
		Map<String, Object> addProp = this.term.getAdditionalProperties();
		
		Set<String> keys = addProp.keySet();
		for (String key : keys) {
			Assert.assertEquals(key, "NAME");
			
			String value = (String) addProp.get(key);
			Assert.assertEquals(value, "VALUE");
		}
	}

}
