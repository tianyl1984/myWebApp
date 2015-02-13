package com.hzth.myapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Test {

	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf1 = new SimpleDateFormat("y");
		SimpleDateFormat sdf2 = new SimpleDateFormat("Y");
		for (int i = 2014; i < 2015; i++) {
			Calendar c1 = Calendar.getInstance();
			c1.set(Calendar.YEAR, i);
			for (int j = 0; j < 12; j++) {
				c1.set(Calendar.MONTH, j);
				for (int k = 0; k < 31; k++) {
					c1.set(Calendar.DAY_OF_MONTH, k);
					if (!sdf1.format(c1.getTime()).equals(sdf2.format(c1.getTime()))) {
						System.out.println(i);
					}
				}
			}
		}
	}

}
