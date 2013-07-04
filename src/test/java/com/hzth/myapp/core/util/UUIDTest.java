package com.hzth.myapp.core.util;

import junit.framework.Assert;

import org.junit.Test;

public class UUIDTest {

	@Test
	public void m1() {
		Assert.assertNotSame(UUID.getUUID(), UUID.getUUID());
	}
}
