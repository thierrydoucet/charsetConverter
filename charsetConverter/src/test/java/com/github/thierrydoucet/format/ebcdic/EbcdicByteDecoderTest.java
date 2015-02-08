package com.github.thierrydoucet.format.ebcdic;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.junit.Before;
import org.junit.Test;

import com.github.thierrydoucet.format.ByteDecoder;

public class EbcdicByteDecoderTest {

	private byte[] byteArray;

	private ByteDecoder decoder;

	private ByteBuffer buffer;

	private String expected;

	@Before
	public void initialize() throws Exception {
		byteArray = new byte[] { (byte) 0x40, (byte) 0x81, (byte) 0xc1, (byte) 0xF0 };
		expected = " aA0";
		buffer = ByteBuffer.wrap(byteArray);
		decoder = new EbcdicByteDecoder();
	}

	@Test
	public void testEbcdicString() {
		assertEquals(expected.substring(0, 4), decoder.readString(buffer, 4));
	}

	@Test
	public void testEbcdicValidatedString() {
		ByteDecoder dec = new EbcdicValidatingByteDecoder();
		assertEquals(expected.substring(0, 4), dec.readString(buffer, 4));
	}

	@Test(expected = EbcdicConversionException.class)
	public void testEbcdicInvalidString_0x00() {
		ByteDecoder dec = new EbcdicValidatingByteDecoder();
		dec.readString(ByteBuffer.wrap(new byte[] { (byte) 0x00 }), 1);
	}

	@Test(expected = EbcdicConversionException.class)
	public void testEbcdicInvalidString_0xFF() {
		ByteDecoder dec = new EbcdicValidatingByteDecoder();
		dec.readString(ByteBuffer.wrap(new byte[] { (byte) 0xFF }), 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeLength() {
		decoder.readString(buffer, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testOverflow() {
		decoder.readString(buffer, expected.length() + 1);
	}

	@Test
	public void testNullLength() {
		assertEquals("", decoder.readString(buffer, 0));
	}

	@Test
	public void testShortMinusOne() {
		assertEquals(-1, decoder.readSignedShort(ByteBuffer.wrap(new byte[] { (byte) 0xFF, (byte) 0xFF })));
	}

	@Test
	public void testIntegerMinusOne() {
		assertEquals(
				-1,
				decoder.readSignedInteger(ByteBuffer.wrap(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
						(byte) 0xFF })));
	}

	@Test
	public void testShortPositive() {
		assertEquals(511, decoder.readSignedShort(ByteBuffer.wrap(new byte[] { (byte) 0x01, (byte) 0xFF })));
	}

	@Test
	public void testIntegerPositive() {
		assertEquals(
				(1 << 25) - 1,
				decoder.readSignedInteger(ByteBuffer.wrap(new byte[] { (byte) 0x01, (byte) 0xFF, (byte) 0xFF,
						(byte) 0xFF })));
	}

	@Test
	public void testShortNegative() {
		assertEquals(-((1 << 15)), decoder.readSignedShort(ByteBuffer.wrap(new byte[] { (byte) 0x80, (byte) 0x00 })));
	}

	@Test
	public void testIntegerNegative() {
		assertEquals(
				-(1 << 31),
				decoder.readSignedInteger(ByteBuffer.wrap(new byte[] { (byte) 0x80, (byte) 0x00, (byte) 0x00,
						(byte) 0x00 })));
	}
}