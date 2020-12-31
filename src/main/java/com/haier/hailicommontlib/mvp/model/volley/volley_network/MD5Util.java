package com.haier.hailicommontlib.mvp.model.volley.volley_network;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Util {
	private MD5Util() {
	}

	public static String toHexString(byte[] paramArrayOfByte,
                                     String paramString, boolean paramBoolean) {
		StringBuilder localStringBuilder = new StringBuilder();
		int i = paramArrayOfByte.length;
		for (int j = 0; j < i; j++) {
			String str = Integer.toHexString(0xFF & paramArrayOfByte[j]);
			if (paramBoolean) {
				str = str.toUpperCase();
			}
			if (str.length() == 1) {
				localStringBuilder.append("0");
			}
			localStringBuilder.append(str).append(paramString);
		}
		return localStringBuilder.toString();
	}

	public static String toMD5(byte[] paramArrayOfByte, boolean paramBoolean) {
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.reset();
			localMessageDigest.update(paramArrayOfByte);
			String str = toHexString(localMessageDigest.digest(), "",
					paramBoolean);
			return str;
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
			throw new RuntimeException(localNoSuchAlgorithmException);
		}
	}
	
	public static String toMD5(String originalString, boolean isUpper) {
		return toMD5(originalString.getBytes(), isUpper);
	}

}
