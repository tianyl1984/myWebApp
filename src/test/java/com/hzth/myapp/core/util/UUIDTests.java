package com.hzth.myapp.core.util;

import junit.framework.Assert;

import org.junit.Test;

public class UUIDTests {

	@Test
	public void m1() {
		System.out.println("Tests");
		Assert.assertNotSame(UUID.getUUID(), UUID.getUUID());
	}
}
