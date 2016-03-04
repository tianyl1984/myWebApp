package com.hzth.myapp.jdk7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TryDemo {

	public static void main(String[] args) {
		// try-with-resources 自动关闭流
		try (BufferedReader br = new BufferedReader(new FileReader(new File("pom.xml")))) {
			System.out.println(br.readLine());
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		try {
			new FileReader(new File("aaa"));
			DriverManager.getConnection("url1");
		} catch (IOException | SQLException e) {// 一次catch多个异常
			e.printStackTrace();
		}
	}
}
