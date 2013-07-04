package com.hzth.myapp.test;

public class XYCoordinate {

	private byte[] data;

	public XYCoordinate(byte[] data) {
		this.data = data;
	}

	public boolean isStatusNormal() {
		return data[0] == -128;
	}

	public boolean isColorMove() {
		return data[1] == -127;
	}

	public boolean isColorUp() {
		return data[1] == -120;
	}

	public Float getX() {
		return (((ByteUtil.bytes2Short(new byte[] { data[2], data[3] })) / 10 + 521) * 0.69f);
		// return (((ByteUtil.bytes2Short(new byte[] { data[2], data[3] })) / 10 + 521) * 1.0f);
	}

	public Float getY() {
		return (((ByteUtil.bytes2Short(new byte[] { data[4], data[5] })) / 10 - 75) * 0.798f);
		// return (((ByteUtil.bytes2Short(new byte[] { data[4], data[5] })) / 10 - 75) * 1.0f);
	}

	@Override
	public String toString() {
		return "X:" + getX() + ",Y:" + getY() + ",Status:" + data[0] + ",Color:" + data[1];
	}
}
