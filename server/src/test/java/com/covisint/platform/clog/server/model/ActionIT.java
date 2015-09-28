package com.covisint.platform.clog.server.model;

import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ActionIT {
	private static final String ALIAS = "alias";
	private static final String INDEX = "index";

	private Action action;

	/**
	 * setUp.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		action = new Action();
		
		String serverURL = System.getenv("CF_SERVER_URL");
		
		System.out.println("**************CF Server URL: " + serverURL);
	}

	/**
	 * createAdd.
	 * 
	 * @return Add
	 */
	private Add createAdd() {
		final Add add = new Add();
		add.setIndex(INDEX);
		add.setAlias(ALIAS);
		return add;
	}

	/**
	 * tearDown.
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.covisint.platform.clog.server.model.Action#getAdd()}.
	 */
	@Test
	public void testAdd() {
		action.setAdd(this.createAdd());
		Add add = action.getAdd();
		
		Assert.assertEquals(add.getIndex(), INDEX);
		Assert.assertEquals(add.getAlias(), ALIAS);
	}
	
	@Test
	public void testAdditionalProperty() {
		action.setAdditionalProperty("NAME", "VALUE");
		Map<String, Object> addProp = action.getAdditionalProperties();
		
		Set<String> keys = addProp.keySet();
		for (String key : keys) {
			Assert.assertEquals(key, "NAME");
			
			String value = (String) addProp.get(key);
			
			Assert.assertEquals(value, "VALUE");
		}
	}
}
