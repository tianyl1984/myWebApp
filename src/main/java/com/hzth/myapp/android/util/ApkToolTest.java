package com.hzth.myapp.android.util;

import java.io.File;

import brut.androlib.ApkDecoder;
import brut.androlib.res.util.ExtMXSerializer;
import brut.androlib.res.util.ExtXmlSerializer;

public class ApkToolTest {

	public static void main(String[] args) throws Exception {
		ApkDecoder apkDecoder = new ApkDecoder(new File("C:\\Documents and Settings\\Administrator\\桌面\\androidtest.apk"));
		apkDecoder.setOutDir(new File("E:\\temp"));
		apkDecoder.setForceDelete(true);
		apkDecoder.decode();

		//		if (true) {
		//			return;
		//		}

	}

	public static ExtMXSerializer getResXmlSerializer() {
		ExtMXSerializer serial = new ExtMXSerializer();
		serial.setProperty(ExtXmlSerializer.PROPERTY_SERIALIZER_INDENTATION, "    ");
		serial.setProperty(ExtXmlSerializer.PROPERTY_SERIALIZER_LINE_SEPARATOR, System.getProperty("line.separator"));
		serial.setProperty(ExtMXSerializer.PROPERTY_DEFAULT_ENCODING, "utf-8");
		serial.setDisabledAttrEscape(true);
		return serial;
	}
}
