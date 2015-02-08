package com.github.thierrydoucet.format.ebcdic;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.github.thierrydoucet.format.ByteDecoder;

public class EbcdicByteDecoder implements ByteDecoder {

	private static final String CODE_PAGE = "IBM297";

	private byte[] readBytes(ByteBuffer buffer, int length) {
		byte[] b = new byte[length];
		buffer.get(b);
		return b;
	}

	public String readString(ByteBuffer buffer, int length) {
		if (buffer == null) {
			throw new IllegalArgumentException("buffer must not be null");
		}
		if (length < 0) {
			throw new IllegalArgumentException("length must be positive");
		}
		if (buffer.capacity() < length) {
			throw new IllegalArgumentException("buffer overflow (length is greated than buffer capacity)");
		}
		byte[] b = readBytes(buffer, length);
		preProcess(b);
		try {
			return new String(b, CODE_PAGE);
		}
		catch (UnsupportedEncodingException e) {
			throw new EbcdicConversionException(e);
		}
	}

	protected void preProcess(byte[] b) {

	}

	public int readSignedShort(ByteBuffer buffer) {
		if (buffer == null) {
			throw new IllegalArgumentException("buffer must not be null");
		}
		return buffer.getShort();
	}

	public int readSignedInteger(ByteBuffer buffer) {
		if (buffer == null) {
			throw new IllegalArgumentException("buffer must not be null");
		}
		return buffer.getInt();
	}
}