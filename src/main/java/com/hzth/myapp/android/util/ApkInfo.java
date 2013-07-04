package com.hzth.myapp.android.util;

import java.io.InputStream;
import java.util.List;

public class ApkInfo {

	private String versionCode = null;

	private String versionName = null;

	private String apkPackage = null;

	private String minSdkVersion = null;

	private String apkName = null;

	private List<String> usesPermissions = null;

	private long size;

	private InputStream iconInputStream = null;

	public String getVersionCode() {
		return this.versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return this.versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getApkPackage() {
		return this.apkPackage;
	}

	public void setApkPackage(String apkPackage) {
		this.apkPackage = apkPackage;
	}

	public String getMinSdkVersion() {
		return this.minSdkVersion;
	}

	public void setMinSdkVersion(String minSdkVersion) {
		this.minSdkVersion = minSdkVersion;
	}

	public String getApkName() {
		return this.apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public long getSize() {
		return this.size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public List<String> getUsesPermissions() {
		return usesPermissions;
	}

	public void setUsesPermissions(List<String> usesPermissions) {
		this.usesPermissions = usesPermissions;
	}

	public InputStream getIconInputStream() {
		return iconInputStream;
	}

	public void setIconInputStream(InputStream iconInputStream) {
		this.iconInputStream = iconInputStream;
	}

	public String toString() {
		return "ApkInfo [versionCode=" + this.versionCode + ", versionName=" + this.versionName + ", apkPackage=" + this.apkPackage + ", minSdkVersion=" + this.minSdkVersion + ", apkName=" + this.apkName + ", usesPermission=" + this.usesPermissions + ", size=" + this.size + "]";
	}
}
