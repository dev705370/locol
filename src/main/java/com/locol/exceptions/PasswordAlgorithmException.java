package com.locol.exceptions;

import java.security.GeneralSecurityException;

public class PasswordAlgorithmException extends Exception {

	public PasswordAlgorithmException(GeneralSecurityException e) {
		super(e);
	}

	private static final long serialVersionUID = -996827230694442075L;

}
