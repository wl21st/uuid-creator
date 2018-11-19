package com.github.f4b6a3.uuid.factory.abst;

import org.junit.Test;

import static com.github.f4b6a3.uuid.factory.abst.AbstractUUIDCreator.*;

import static org.junit.Assert.*;

public class AbstractUUIDCreatorTest {

	
	@Test
	public void testValid() {
		
		long lsb = 0x0000000000000000L | RFC4122_VARIANT_BITS;
		
		AbstractUUIDCreator creator = new AbstractUUIDCreator(VERSION_0) { };
		long msb = 0x0000000000000000L | RFC4122_VERSION_BITS[VERSION_0];
		assertTrue(creator.valid(msb, lsb));
		
		creator = new AbstractUUIDCreator(VERSION_1) { };
		msb = 0x0000000000000000L | RFC4122_VERSION_BITS[VERSION_1];
		assertTrue(creator.valid(msb, lsb));
		
		creator = new AbstractUUIDCreator(VERSION_2) { };
		msb = 0x0000000000000000L | RFC4122_VERSION_BITS[VERSION_2];
		assertTrue(creator.valid(msb, lsb));
		
		creator = new AbstractUUIDCreator(VERSION_3) { };
		msb = 0x0000000000000000L | RFC4122_VERSION_BITS[VERSION_3];
		assertTrue(creator.valid(msb, lsb));
		
		creator = new AbstractUUIDCreator(VERSION_4) { };
		msb = 0x0000000000000000L | RFC4122_VERSION_BITS[VERSION_4];
		
		assertTrue(creator.valid(msb, lsb));
		creator = new AbstractUUIDCreator(VERSION_5) { };
		msb = 0x0000000000000000L | RFC4122_VERSION_BITS[VERSION_5];
		assertTrue(creator.valid(msb, lsb));
	}
	
	@Test
	public void testSetVersionBits() {
		
		AbstractUUIDCreator creator = new AbstractUUIDCreator(VERSION_4) { };
		long msb = 0x0000000000000000L | RFC4122_VERSION_BITS[VERSION_4];
		long result1 = creator.setVersionBits(msb);
		long result2 = creator.getVersionBits(result1);
		assertEquals(msb, result2);
	}
	
	@Test
	public void testSetVariantBits() {
		AbstractUUIDCreator creator = new AbstractUUIDCreator(VERSION_4) { };
		long lsb = 0x0000000000000000L | RFC4122_VARIANT_BITS;
		long result1 = creator.setVariantBits(lsb);
		long result2 = creator.getVariantBits(result1);
		assertEquals(lsb, result2);
	}
}
