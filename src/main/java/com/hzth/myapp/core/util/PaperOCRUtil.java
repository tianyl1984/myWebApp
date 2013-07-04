package com.hzth.myapp.core.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class PaperOCRUtil {

	public interface PaperCrop extends Library {
		PaperCrop instance = (PaperCrop) Native.loadLibrary("PaperCrop", PaperCrop.class);

		public int DoAutoCrop(String a, String b);
	}

	public interface PaperOMR extends Library {
		PaperOMR instance = (PaperOMR) Native.loadLibrary("PaperOMR", PaperOMR.class);

		public String OMRPage(String a);

		public String OMRID(String a);

		public String OMRSubject(String a);
	}

	public static final int SUCCESS = 1;

	public static final int FAIL = 0;

	public static int autoCrop(String srcPath, String decPath) {
		int result = PaperCrop.instance.DoAutoCrop(srcPath, decPath);
		return result;
	}

	public static Integer omrPage(String srcPath) {
		String result = PaperOMR.instance.OMRPage(srcPath);
		int len = result.replaceAll("0", "").length();
		return len;
	}

	public static String omrId(String srcPath) {
		String result = PaperOMR.instance.OMRID(srcPath);
		return result;
	}

	public static String omrSubject(String srcPath) {
		String result = PaperOMR.instance.OMRSubject(srcPath);
		return result;
	}

	public static Double omrScore(String srcPath) {
		return RecognitionScore.getScore(srcPath);
	}

	public static void main(String[] args) {
		//		System.out.println(omrScore("d:/aaa/1.bmp"));
		//		System.out.println(omrScore("d:/aaa/2.bmp"));
		//		System.out.println(omrScore("d:/aaa/4.bmp"));
		//		System.out.println(omrScore("d:/aaa/5.bmp"));
		//		System.out.println(omrScore("d:/aaa/6.bmp"));
		//		System.out.println(omrScore("d:/aaa/7.bmp"));
		//		System.out.println(omrScore("d:/aaa/8.bmp"));
		System.out.println(omrScore("d:/aaa/a1.bmp"));
		System.out.println(omrScore("d:/aaa/a2.bmp"));
		System.out.println(omrScore("d:/aaa/a3.bmp"));
		System.out.println(omrScore("d:/aaa/a4.bmp"));
	}
}

class RecognitionScore {

	public static Double getScore(String fileName) {
		try {
			File f = new File(fileName);
			BufferedImage img = ImageIO.read(f);
			int width = img.getWidth();
			int heigh = img.getHeight();
			double result = 0;
			for (int i = 0; i < 3; i++) {
				if (i == 3 || i == 14) {
					continue;
				}
				BufferedImage subImg = img.getSubimage((i) * width / 16, 0, width / 16, heigh);
				WBCount wbCount = getWBCount(subImg);
				if (wbCount.isBlank()) {
					result += (i + 1) * 10;
				}
			}
			Integer gw = null;
			for (int i = 4; i < 14; i++) {
				BufferedImage subImg = img.getSubimage((i) * width / 16, 0, width / 16, heigh);
				WBCount wbCount = getWBCount(subImg);
				if (wbCount.isBlank()) {
					if (gw != null) {//个位数字涂的多余一个
						//						return null;
					}
					gw = i - 4;
				}
			}
			if (gw == null) {//个位数字没有涂
				//				return null;
			}
			result += gw;
			BufferedImage subImg = img.getSubimage((15) * width / 16, 0, width / 16, heigh);
			WBCount wbCount = getWBCount(subImg);
			if (wbCount.isBlank()) {
				result += 0.5;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static WBCount getWBCount(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		Long bSum = 0L;
		Long wSum = 0L;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int color = img.getRGB(i, j);
				if (isBlank(color)) {
					bSum++;
				} else {
					wSum++;
				}
			}
		}
		return new WBCount(wSum, bSum);
	}

	private static boolean isBlank(int rgb) {
		Color black = Color.black;
		Color white = Color.white;
		return (rgb - white.getRGB()) < (black.getRGB() - rgb);
	}

}

class WBCount {
	private Long wCount;
	private Long bCount;

	public WBCount() {

	}

	public WBCount(Long wCount, Long bCount) {
		super();
		this.wCount = wCount;
		this.bCount = bCount;
	}

	public Long getwCount() {
		return wCount;
	}

	public void setwCount(Long wCount) {
		this.wCount = wCount;
	}

	public Long getbCount() {
		return bCount;
	}

	public void setbCount(Long bCount) {
		this.bCount = bCount;
	}

	public boolean isBlank() {
		System.out.println(toString());
		return bCount > (bCount + wCount) / 4;
	}

	@Override
	public String toString() {
		return "白点个数:" + wCount + "     黑点个数:" + bCount + "           百分比：" + (bCount.doubleValue() / (bCount.doubleValue() + wCount.doubleValue()));
	}
}