package com.hzth.myapp.test;

public class ByteUtil {

	public static short bytes2Short(byte b[]) {
		short s = 0;
		s = (short) ((b[1] & 0xff) << 8 | (b[0] & 0xff));
		return s;
	}
}
