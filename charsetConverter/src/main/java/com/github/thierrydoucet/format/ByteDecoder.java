package com.github.thierrydoucet.format;

import java.nio.ByteBuffer;

public interface ByteDecoder {

	String readString(ByteBuffer buffer, int length);

	int readSignedShort(ByteBuffer buffer);

	int readSignedInteger(ByteBuffer buffer);
}