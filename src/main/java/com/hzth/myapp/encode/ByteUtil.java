package com.hzth.myapp.encode;

public class ByteUtil {

	public static final void print(byte[] bytes) {
		System.out.println(bytesToHexString(bytes));
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv + " ");
		}
		return stringBuilder.toString();
	}

	public static byte[] negate(byte[] bytes) {
		byte[] result = new byte[bytes.length];
		int i = 0;
		for (byte bt : bytes) {
			result[i] = (byte) ~bt;
			i++;
		}
		return result;
	}
}
