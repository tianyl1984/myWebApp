package com.hzth.myapp.encode;

public class MyCustomUtil {

	public static String encode(String str) {
		for (char c : str.toCharArray()) {
			String temp = c + "";
			if (!temp.toLowerCase().equals(temp)) {
				throw new RuntimeException("无法加密");
			}
		}
		str = str.toUpperCase();
		String result = "";
		for (char c : str.toCharArray()) {
			int i = (int) c;
			if (i > 100) {
				throw new RuntimeException("无法加密");
			}
			result += (123 - i);
		}
		return result;
	}

	public static String decode(String str) {
		String result = "";
		for (int i = 0; i < str.length(); i = i + 2) {
			String temp = str.substring(i, i + 2);
			char c = (char) ((123 - Integer.valueOf(temp)));
			result += c;
		}
		return result.toLowerCase();
	}

	public static void main(String[] args) {
		// System.out.println(decode(encode("http://www.baidu.com/aa/bb.do?aa=bb&dd=cc")));
		System.out.println(encode("http://www.yeahvox.com/m_changepwd.php"));
	}
}
