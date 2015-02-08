package com.github.thierrydoucet.format.ebcdic;

import com.github.thierrydoucet.format.ConversionException;

public class EbcdicConversionException extends ConversionException {

	private static final long serialVersionUID = 1L;

	public EbcdicConversionException() {
		super();
	}

	public EbcdicConversionException(String message, Throwable cause) {
		super(message, cause);
	}

	public EbcdicConversionException(String message) {
		super(message);
	}

	public EbcdicConversionException(Throwable cause) {
		super(cause);
	}
}