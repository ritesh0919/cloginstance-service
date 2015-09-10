/* Copyright (C) 2014 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.core.cloginstance;

import static com.covisint.platform.clog.core.cloginstance.ClogInstanceTestUtil.mockClogInstance;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import org.junit.Test;

import com.covisint.platform.clog.core.ClogInstanceStatusE;

/**
 * @since Jul 10, 2015
 *
 */
public class ClogInstanceTest {

	/**
	 * Test method for
	 * {@link com.covisint.platform.clog.core.cloginstance.ClogInstance#hashCode()}
	 * .
	 */
	@Test
	public void testHashCode() {
		ClogInstance ci1 = mockClogInstance("SomeUUID", 1234L, "Inst");
		ClogInstance ci2 = mockClogInstance("SomeUUID", 1234L, "SomeName2");
		ClogInstance ci3 = mockClogInstance("SomOtherUUID", 1234L, "SomeName3");

		assertThat(ci1.hashCode(), equalTo(ci2.hashCode()));
		assertThat(ci1.hashCode(), not(equalTo(ci3.hashCode())));
	}

	/**
	 * Test method for
	 * {@link com.covisint.platform.clog.core.cloginstance.ClogInstance#equals(java.lang.Object)}
	 * .
	 */
	@Test
	public void testEqualsObject() {
		ClogInstance ci1 = mockClogInstance("SomeUUID", 1234L, "Inst");
		ClogInstance ci2 = mockClogInstance("SomeUUID", 1234L, "SomeName2");
		ClogInstance ci3 = mockClogInstance("SomOtherUUID", 1234L, "SomeName3");
		
		assertThat(ci1, equalTo(ci2));
		assertThat(ci1, not(equalTo(ci3)));
		
		assertEquals(ci1.getCreationInstant(), 1234567890L);
		assertEquals(ci1.getCreator(), "testCreator");
		assertEquals(ci1.getCreatorApplicationId(), "testAppId");
		assertEquals(ci1.getId(), "SomeUUID");
		assertEquals(ci1.getName(), "Inst");
		assertEquals(ci1.getPlatformGroupId(), "testGroupId");
		assertEquals(ci1.getPlatformInstanceId(), "testInstanceId");
		assertEquals(ci1.getPlatformSolutionId(), "testSolutionId");
		assertEquals(ci1.getRealm(), "SampleRealm");
		assertEquals(ci1.getStatus(), ClogInstanceStatusE.ACTIVE);
		
	}

	/**
	 * Test method for
	 * {@link com.covisint.platform.clog.core.cloginstance.ClogInstance#toString()}
	 * .
	 */
	@Test
	public void testToString() {
		ClogInstance ci = mockClogInstance("SomeUUID", 1234L, "SomeName");
		assertThat(
				ci.toString(),
				equalTo("ClogInstance{name=SomeName, platformSolutionId=testSolutionId, platformInstanceId=testInstanceId, platformGroupId=testGroupId, status=ACTIVE, id=SomeUUID, version=1234, creator=testCreator, creation=1234567890, creatorApplicationId=testAppId, realm=SampleRealm}"));
	}

}
