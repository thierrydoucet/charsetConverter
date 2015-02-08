package com.github.thierrydoucet.format.ebcdic;

public class EbcdicValidatingByteDecoder extends EbcdicByteDecoder {

	protected void preProcess(byte[] b) {
		for (int i = 0; i < b.length; i++) {
			int n = 0x000000ff & b[i];
			if (n < 0x00000040 || n > 0x000000FE) {
				throw new EbcdicConversionException("Invalid EBCDIC character : '" + Integer.toHexString(n) + "'");
			}
		}
	}
}