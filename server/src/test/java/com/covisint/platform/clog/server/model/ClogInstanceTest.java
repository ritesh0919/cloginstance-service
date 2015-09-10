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
 * ClogInstanceTest.
 * 
 */
public class ClogInstanceTest {


	private ClogInstance cloginstance;

	/**
	 * setUp.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.cloginstance = new ClogInstance();
	}

	/**
	 * createAction.
	 * 
	 * @return List
	 */
	private List<Action> createAction() {
		final List<Action> list = new ArrayList<Action>();

		final Action a1 = new Action();
		final Action a2 = new Action();
		a1.setAdditionalProperty("TEST", "EMPTY");

		list.add(a1);
		list.add(a2);
		return list;
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
	 * testGetActions.
	 * 
	 */
	@Test
	public void testActions() {
		this.cloginstance.setActions(this.createAction());
		assertEquals(cloginstance.getActions().size(), 2);
	}

	@Test
	public void testAdditionalProperty() {
		this.cloginstance.setAdditionalProperty("NAME", "VALUE");
		Map<String, Object> addProp = this.cloginstance.getAdditionalProperties();
		
		Set<String> keys = addProp.keySet();
		for (String key : keys) {
			Assert.assertEquals(key, "NAME");
			
			String value = (String) addProp.get(key);
			
			Assert.assertEquals(value, "VALUE");
		}
	}

}
