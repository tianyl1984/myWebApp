package com.hzth.myapp.encode;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {

	public static void main(String[] args) throws Exception {
		System.out.println(encryptBASE64("1234".getBytes()));
		ByteUtil.print(ByteUtil.negate("{:".getBytes()));
		ByteUtil.print(decryptBASE64("WuG11Wbl22ZmhvKA/6dPAhhGeQvBkgpnVZchMn8WI3EkYXz3zSn8IFd+o/6tRn0S6mKP+1E7w+idKX/L26hcVLYh45wD/AeUMobw7ytSWw/555aUK+j2Vh43fPAB4BPQj44oop7rQbS7MJPkZHvcnw=="));
	}

	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}
}
