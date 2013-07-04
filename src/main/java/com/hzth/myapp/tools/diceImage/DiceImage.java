package com.hzth.myapp.tools.diceImage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class DiceImage {

	public static void main(String[] args) throws Exception {
		BufferedImage bi = ImageIO.read(new File("E:/workspace/myWebApp/src/main/java/com/hzth/myapp/tools/diceImage/bg2011112607.jpg"));
		int width = bi.getWidth();
		int height = bi.getHeight();
		Map<Integer, List<Integer>> result = new HashMap<Integer, List<Integer>>();
		for (int i = 0; i < width / 16; i++) {
			List<Integer> rowResult = new ArrayList<Integer>();
			for (int j = 0; j < height / 16; j++) {
				Long sum = 0l;
				for (int m = 0; m < 16; m++) {
					for (int n = 0; n < 16; n++) {
						sum += getGray(bi.getRGB(i * 16 + m, j * 16 + n));
					}
				}
				int avg = Long.valueOf(sum / (16 * 16)).intValue();
				// System.out.println(avg);
				rowResult.add(getDiceNumber(avg));
			}
			result.put(i, rowResult);
		}

		BufferedImage[] bis = new BufferedImage[6];
		for (int i = 0; i < bis.length; i++) {
			bis[i] = ImageIO.read(new File("E:/workspace/myWebApp/src/main/java/com/hzth/myapp/tools/diceImage/" + (i + 1) + ".jpg"));
		}

		BufferedImage imageNew = new BufferedImage(19 * (width / 16), 19 * (height / 16), BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < width / 16; i++) {
			List<Integer> rowResult = result.get(i);
			for (int j = 0; j < rowResult.size(); j++) {
				BufferedImage temp = bis[rowResult.get(j) - 1];
				imageNew.setRGB(16 * i, 16 * j, 19, 19, temp.getRGB(0, 0, 19, 19, null, 0, 19), 0, 19);
			}
		}
		File outFile = new File("C:/Users/tianyl/Desktop/result2.jpg");
		ImageIO.write(imageNew, "jpg", outFile);
	}

	private static int getGray(int rgb) {
		Color c = new Color(rgb);
		return (c.getRed() + c.getGreen() + c.getBlue()) / 3;
	}

	private static Integer getDiceNumber(int x) {
		if (0 <= x && x <= 41)
			return 1;
		if (41 < x && x <= 83)
			return 2;
		if (83 < x && x <= 124)
			return 3;
		if (124 < x && x <= 165)
			return 4;
		if (165 < x && x <= 206)
			return 5;
		if (206 < x && x <= 247)
			return 6;
		return 6;
	}
}
