/* Copyright (C) 2014 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.server.model;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * ShouldTest.
 * 
 */
public class ShouldTest {
	
	private Should should;

	/**
	 * setUp.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.should = new Should();
	}

	/**
	 * addTerm.
	 * 
	 * @return Term
	 */
	private Term addTerm() {
		final Term term = new Term();
		term.setGroupId("12345");
		term.setX_realm("REALM_ID");
		term.setAdditionalProperty("TEST", "TEST");
		return term;
	}

	/**
	 * testgetTerm.
	 * 
	 */
	@Test
	public void testgetTerm() {
		this.should.setTerm(this.addTerm());
		assertEquals(should.getTerm().getGroupId(), "12345");
		assertEquals(should.getTerm().getX_realm(), "REALM_ID");
	}

	@Test
	public void testAdditionalProperty() {
		this.should.setAdditionalProperty("NAME", "VALUE");
		Map<String, Object> addProp = this.should.getAdditionalProperties();
		
		Set<String> keys = addProp.keySet();
		for (String key : keys) {
			Assert.assertEquals(key, "NAME");
			
			String value = (String) addProp.get(key);
			Assert.assertEquals(value, "VALUE");
		}
	}

}
