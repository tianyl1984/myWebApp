package com.hzth.myapp.other;

import java.util.HashSet;
import java.util.Set;

public class MyHash {

	public static void main(String[] args) {
		//		System.out.println("ba".hashCode() + ":" + "cB".hashCode());
		//		System.out.println("Yw".hashCode() + ":" + "ZX".hashCode());
		//		System.out.println("bbab".hashCode() + ":" + "bcBb".hashCode());
		//		System.out.println("abbaab".hashCode() + ":" + "abcBab".hashCode());
		//		findSameHash();
		//		hashSetTest();
		//		findSameHash("ba");
		//		System.out.println("aaa_en.properties".replaceAll("_.properties", ""));
	}

	public static void hashSetTest() {
		Set<String> data = new HashSet<String>();
		String a = "a";
		for (int i = 0; i < 80000; i++) {
			data.add(a);
			a = stringAdd(a);
		}
		Long t1 = System.currentTimeMillis();
		data.add(a);
		Long t2 = System.currentTimeMillis();
		System.out.println(t2 - t1);
	}

	public static void findSameHash(String str) {
		String temp = "a";
		for (int i = 0; i < 1000000; i++) {
			if (!str.equals(temp) && temp.hashCode() == str.hashCode()) {
				System.out.println(temp);
			}
			temp = stringAdd(temp);
		}
	}

	public static void findSameHash() {
		String str1 = "a";
		String str2 = "b";
		for (int i = 0; i < 1000000; i++) {
			boolean flag = false;
			for (int j = 0; j < 1000000; j++) {
				if (!str1.equals(str2) && str1.hashCode() == str2.hashCode()) {
					System.out.println(str1 + ":" + str2);
					//					flag = true;
					//					break;
				}
				str2 = stringAdd(str2);
			}
			if (flag) {
				break;
			}
			str1 = stringAdd(str1);
			str2 = str1;
			if (str1.length() == 3) {
				break;
			}
		}
	}

	private static String stringAdd(String str1) {
		return stringAdd(str1, str1.length() - 1);
	}

	public static String stringAdd(String str, int location) {
		String temp = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789;'[],./`-=~!@#$%^&*()_+{}:?><";
		char lastChar = str.charAt(location);
		int index = temp.indexOf(lastChar);
		if (index < temp.length() - 1) {
			String prefix1 = "";
			if (location > 0) {
				prefix1 = str.substring(0, location);
			}
			String prefix2 = "";
			if (location < str.length() - 1) {
				prefix2 = str.substring(location + 1);
			}
			return prefix1 + temp.charAt(index + 1) + prefix2;
		} else {
			if (location == 0) {
				return temp.charAt(0) + str;
			}
			return stringAdd(str.substring(0, str.length() - 1) + temp.charAt(0), location - 1);
		}
	}

}
