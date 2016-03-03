package com.hzth.myapp.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ThreeDoors {

	public static void main(String[] args) {
		int rightTime = 0;
		int wrongTime = 0;
		int rightTime2 = 0;
		int wrongTime2 = 0;

		for (int i = 0; i < 10000; i++) {
			List<Integer> datas = new ArrayList<>();
			datas.add(1);
			datas.add(0);
			datas.add(0);
			Collections.shuffle(datas);
			Integer chooseIndex = 0;
			Integer choose = datas.get(chooseIndex);
			datas.remove(chooseIndex.intValue());
			Iterator<Integer> it = datas.iterator();
			while (it.hasNext()) {
				Integer data = it.next();
				if (data.equals(0)) {
					it.remove();
					break;
				}
			}
			// 不换
			if (choose.equals(1)) {
				rightTime++;
			} else {
				wrongTime++;
			}
			choose = datas.get(0);
			if (choose.equals(1)) {
				rightTime2++;
			} else {
				wrongTime2++;
			}
		}
		System.out.println(rightTime * 1d / (rightTime + wrongTime));
		System.out.println(rightTime2 * 1d / (rightTime2 + wrongTime2));
	}

}
