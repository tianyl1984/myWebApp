package com.hzth.myapp.android.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.wrapper.XmlPullParserWrapper;
import org.xmlpull.v1.wrapper.XmlPullWrapperFactory;
import org.xmlpull.v1.wrapper.XmlSerializerWrapper;

import brut.androlib.Androlib;
import brut.androlib.res.data.ResPackage;
import brut.androlib.res.data.ResResource;
import brut.androlib.res.data.ResTable;
import brut.androlib.res.data.ResValuesFile;
import brut.androlib.res.data.value.ResStringValue;
import brut.androlib.res.data.value.ResValue;
import brut.androlib.res.decoder.AXmlResourceParser;
import brut.androlib.res.decoder.ResAttrDecoder;
import brut.androlib.res.util.ExtFile;
import brut.androlib.res.util.ExtMXSerializer;
import brut.androlib.res.util.ExtXmlSerializer;

public class ApkUtil {

	public static final Namespace ANDROID_NAMESPACE = Namespace.getNamespace("http://schemas.android.com/apk/res/android");

	public static ApkInfo getApkInfo(String apkPath) {
		InputStream xmlUnzipStream = null;
		ZipFile zipFile = null;
		InputStream xmlZipStream = null;
		File file = new File(apkPath);
		try {
			zipFile = new ZipFile(file);
			ZipEntry entry = zipFile.getEntry("AndroidManifest.xml");
			xmlZipStream = zipFile.getInputStream(entry);
			AXMLPrinter xmlPrinter = new AXMLPrinter();
			xmlPrinter.startRecover(xmlZipStream);
			StringBuffer buffer = xmlPrinter.getBuf();
			xmlUnzipStream = new ByteArrayInputStream(buffer.toString().getBytes("UTF-8"));
			ApkInfo apk = manifestSax(xmlUnzipStream);
			apk.setSize(file.length());
			return apk;
		} catch (FileNotFoundException e) {
			System.out.println("无法找到指定文件:" + apkPath);
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			releaseIO(xmlZipStream, zipFile);
		}
	}

	private static void releaseIO(InputStream is, ZipFile zipFile) {
		try {
			if (is != null) {
				is.close();
			}
			if (zipFile != null)
				zipFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static ApkInfo manifestSax(InputStream manifestStream) {
		ApkInfo apk = new ApkInfo();
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(manifestStream);
			Element root = document.getRootElement();

			apk.setVersionCode(root.getAttributeValue("versionCode", ANDROID_NAMESPACE));
			apk.setVersionName(root.getAttributeValue("versionName", ANDROID_NAMESPACE));

			//package
			String pkgName = root.getAttributeValue("package");
			apk.setApkPackage(pkgName);

			//sdk
			Element elemUseSdk = root.getChild("uses-sdk");
			if (elemUseSdk != null) {
				apk.setMinSdkVersion(elemUseSdk.getAttributeValue("minSdkVersion", ANDROID_NAMESPACE));
			}

			//权限
			@SuppressWarnings("unchecked")
			List<Element> listPermissions = root.getChildren("uses-permission");
			List<String> permissions = new ArrayList<String>();
			if (listPermissions != null) {
				for (Element element : listPermissions) {
					permissions.add(element.getAttributeValue("name", ANDROID_NAMESPACE));
				}
			}
			apk.setUsesPermissions(permissions);

			//名称
			@SuppressWarnings("unchecked")
			List<Element> applications = root.getChildren("application");
			if (applications != null && applications.size() > 0) {
				Element appElement = applications.get(0);
				apk.setApkName(appElement.getAttributeValue("label", ANDROID_NAMESPACE));
				//				System.out.println(appElement.getAttributeValue("label", ANDROID_NAMESPACE));
				//				System.out.println(appElement.getAttributeValue("icon", ANDROID_NAMESPACE));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			releaseIO(manifestStream, null);
		}
		return apk;
	}

	public static void main(String[] args) {
		//		ApkInfo info = getApkInfoNew("C:\\Documents and Settings\\Administrator\\桌面\\androidtest.apk");
		ApkInfo info = getApkInfoNew("E:\\android演示项目\\phoenix2-wandoujia.apk");
		System.out.println(info);
	}

	public static ApkInfo getApkInfoNew(String apkPath) {
		InputStream xmlUnzipStream = null;
		ZipFile zipFile = null;
		File file = new File(apkPath);
		try {
			zipFile = new ZipFile(file);
			StringBuffer buffer = getAndroidManifest(zipFile, apkPath);
			xmlUnzipStream = new ByteArrayInputStream(buffer.toString().getBytes("UTF-8"));
			ApkInfo apk = manifestSax(xmlUnzipStream);
			apk.setSize(file.length());
			if (apk.getApkName().startsWith("@")) {//不是真正名称
				fillRefValues(apk, apkPath);
			}
			return apk;
		} catch (FileNotFoundException e) {
			System.out.println("无法找到指定文件:" + apkPath);
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void fillRefValues(ApkInfo apk, String apkPath) throws Exception {
		Androlib mAndrolib = new Androlib();
		ResTable resTable = mAndrolib.getResTable(new ExtFile(new File(apkPath)));
		String appName = apk.getApkName();
		if (appName.startsWith("@")) {
			if (appName.split("/").length == 2) {
				appName = appName.split("/")[1];
			}
		}
		String appNameTrue = "";
		for (ResPackage pkg : resTable.listMainPackages()) {
			for (ResValuesFile valuesFile : pkg.listValuesFiles()) {
				String path = valuesFile.getPath();
				for (ResResource res : valuesFile.listResources()) {
					if (valuesFile.isSynthesized(res)) {
						continue;
					}
					String name = res.getResSpec().getName();
					if (name == null) {
						name = "";
					}
					if (name.equals(appName)) {
						ResValue val = res.getValue();
						if (val instanceof ResStringValue) {
							if (StringUtils.isBlank(appNameTrue)) {
								appNameTrue = ((ResStringValue) val).encodeAsResXmlValue();
							}
							if (path.contains("-zh")) {
								appNameTrue = ((ResStringValue) val).encodeAsResXmlValue();
							}
						}
					}
				}

			}
		}
		apk.setApkName(appNameTrue);
	}

	private static StringBuffer getAndroidManifest(ZipFile zipFile, String apkPath) throws Exception {
		StringBuffer buffer = new StringBuffer();

		ZipEntry entry = zipFile.getEntry("AndroidManifest.xml");
		Androlib mAndrolib = new Androlib();
		ResTable resTable = mAndrolib.getResTable(new ExtFile(new File(apkPath)));

		AXmlResourceParser axmlParser = new AXmlResourceParser();
		ResAttrDecoder attrDecoder = new ResAttrDecoder();
		attrDecoder.setCurrentPackage(resTable.listMainPackages().iterator().next());
		axmlParser.setAttrDecoder(attrDecoder);

		ExtMXSerializer mSerial = getResXmlSerializer();
		XmlPullWrapperFactory factory = XmlPullWrapperFactory.newInstance();
		XmlPullParserWrapper par = factory.newPullParserWrapper(axmlParser);
		XmlSerializerWrapper ser = factory.newSerializerWrapper(mSerial);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		par.setInput(zipFile.getInputStream(entry), null);
		ser.setOutput(out, null);

		while (par.nextToken() != XmlPullParser.END_DOCUMENT) {
			ser.event(par);
		}
		mSerial.newLine();
		ser.flush();
		buffer.append(out.toString("UTF-8"));
		return buffer;
	}

	private static ExtMXSerializer getResXmlSerializer() {
		ExtMXSerializer serial = new ExtMXSerializer();
		serial.setProperty(ExtXmlSerializer.PROPERTY_SERIALIZER_INDENTATION, "    ");
		serial.setProperty(ExtXmlSerializer.PROPERTY_SERIALIZER_LINE_SEPARATOR, System.getProperty("line.separator"));
		serial.setProperty(ExtMXSerializer.PROPERTY_DEFAULT_ENCODING, "utf-8");
		serial.setDisabledAttrEscape(true);
		return serial;
	}

}
