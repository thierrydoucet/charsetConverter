package com.github.thierrydoucet.format;

import java.nio.ByteBuffer;

public interface ByteEncoder {

	void writeString(ByteBuffer buffer, int size, String value);

	void writeSignedShort(ByteBuffer buffer, int value);

	void writeSignedInteger(ByteBuffer buffer, int value);
}