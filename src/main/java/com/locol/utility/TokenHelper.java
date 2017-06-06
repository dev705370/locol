package com.locol.utility;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class TokenHelper {
	private static final Random SECURE_RANDOM = new SecureRandom();

	public static String generateToken() {
		String token = new BigInteger(130, SECURE_RANDOM).toString(32);
		return token;
	}

}
