/* Copyright (C) 2014 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.server.model;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * AddTest .
 * 
 */
public class AddTest {
	
	private final static String ALIAS = "ALIAS";
	private final static String INDEX = "INDEX";
	
	private Add add;
	
	/**
	 * setUp.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.add = new Add();
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
	 * createFilter.
	 * 
	 * @return Filter
	 */
	private Filter createFilter() {
		final Filter filter = new Filter();
		filter.setAdditionalProperty("TEST", "TEST");
		return filter;
	}

	/**
	 * Test method for
	 * {@link com.covisint.platform.clog.server.model.Action#getAlias()}.
	 */
	@Test
	public void testAlias() {
		this.add.setAlias(ALIAS);
		assertEquals(this.add.getAlias(), ALIAS);
	}


	@Test
	public void testIndex() {
		this.add.setIndex(INDEX);
		assertEquals(add.getIndex(), INDEX);
	}
	
	@Test
	public void testAdditionalProperty() {
		this.add.setAdditionalProperty(ALIAS, INDEX);
		
		Map<String, Object> addProp = add.getAdditionalProperties();
		
		Set<String> keys = addProp.keySet();
		for (String key : keys) {
			Assert.assertEquals(key, ALIAS);
			
			String value = (String) addProp.get(key);
			
			Assert.assertEquals(value, INDEX);
		}
	}
	
	@Test
	public void testFilter() {
		
		this.add.setFilter(this.createFilter());
		
		Filter f = this.add.getFilter();
		Assert.assertNotNull(f);
	}
}
