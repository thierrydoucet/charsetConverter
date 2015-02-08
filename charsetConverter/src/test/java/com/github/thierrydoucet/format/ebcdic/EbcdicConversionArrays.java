package com.github.thierrydoucet.format.ebcdic;

import java.io.UnsupportedEncodingException;

public class EbcdicConversionArrays {

	public static void main(String[] args) throws UnsupportedEncodingException {
		int[] conv = new int[256];
		byte[] b = new byte[256];
		for (int i = 0; i < 256; i++) {
			b[i] = (byte) (0x000000FF & i);
		}

		StringBuffer sb = new StringBuffer();
		sb.append("new byte[]{");
		String s = new String(b, "IBM297");
		for (int i = 0; i < 256; i++) {
			char c = s.charAt(i);
			if (0 != (c & 0xFFFF0000)) {
				throw new IllegalStateException("Erreur : 0 != (c& 0xFFFF0000)");
			}
			int n = 0x0000FFFF & c;
			conv[n] = i;
			String h = "00" + Integer.toHexString(n);

			sb.append("(byte)0x");
			sb.append(h.substring(h.length() - 2, h.length()));
			sb.append(",");
		}
		sb.append("}");
		System.out.println(sb.toString());

		sb = new StringBuffer();
		sb.append("new byte[]{");
		for (int i = 0; i < 256; i++) {
			int n = conv[i];
			String h = "00" + Integer.toHexString(n);
			sb.append("(byte)0x");
			sb.append(h.substring(h.length() - 2, h.length()));
			sb.append(",");
		}
		System.out.println(sb.toString());
	}
}
