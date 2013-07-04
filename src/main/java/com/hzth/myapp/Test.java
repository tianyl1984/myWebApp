package com.hzth.myapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.hzth.myapp.core.util.FileUtil;
import com.hzth.myapp.core.util.UUID;
import com.sun.jna.Library;

public class Test {

	static final List<String> names = new ArrayList<String>();

	static {
		names.add(".ico");
		names.add(".png");
		names.add(".gif");
		names.add(".jpeg");
		names.add(".jpg");
		names.add(".bmp");
	}

	public static void main(String[] args) throws Exception {
		// copyFiles(new File("F:/source2"));
		// m1();
		// System.out.println(UUID.getUUID());
		// m2();
		// m3();

		// Set dm = createobject("dm.dmsoft")
		// dm.SetPath "c:\"
		// dm_ret = dm.SetDictPwd("123456789")
		// dm_ret = dm.SetDict(0,"aa.txt")
		// MessageBox dm_ret

		// E8 0D 4B 39

		System.out.println(Integer.valueOf("19", 16));
		if (true) {
			return;
		}

		FileInputStream fis = new FileInputStream(new File("E:/3.1233/123456.txt"));
		byte[] b = new byte[500];
		int length = fis.read(b);
		fis.close();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File("C:/Users/tianyl/Desktop/1111.txt"));
			for (int i = 0; i < length; i++) {
				if (i < 4) {
					continue;
				}
				fos.write(b[i]);
			}
			// fos.write('D');
			// fos.write('M');
			// fos.write('D');
			// fos.write('M');
			// fos.write(Integer.valueOf("E8", 16));
			// fos.write(Integer.valueOf("0D", 16));
			// fos.write(Integer.valueOf("4B", 16));
			// fos.write(Integer.valueOf("39", 16));
			// fos.write(Integer.valueOf("00", 16));
			// fos.write(Integer.valueOf("C2", 16));
			// fos.write(Integer.valueOf("BD", 16));
			// fos.write(Integer.valueOf("DD", 16));
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

	public interface DLibrary extends Library {
		long SetDictPwd(String str);

		String Ver();
	}

	private static void m3() throws Exception {
		String str1 = "XmFOQZNSyfbMoJfDj4VEHw5GkmTV6HnAyb1ITJnGf1xeqvy5hQQNP6Hx4KjJvlBUnaNVa8Bf+tdK1+602s93ndsgZ43jOlJOqLej6Qu/K5PNWzXbLt3FtpRxFp/tcVv5n8cCY4dy7H5vxEpFkIRuTud8zviuwT7kxFIIimJkiaYrthhIqEJTKVodJbBjAe3oaxwEXX4etLfA9RkSYYM38dv62KZju6VBTWG9IWBF83IrfCx1+q8O1PW/at8DSpsXmlb+LuYY4M7R2q/K1C7qmeilVMEHwB/JTv07onstFbEIFDEwQjDyv7d46CXq4+02Jpb5rkEP1sJpXVj0mjvfDn9N+37Thi8+ifMyGgs3GK8EZvVnKfnY9P5y4KyPCynYPfk73HSfArmW4N+ogoBwEhBBxtOtJ2UE4HpxzJ5K5pHLXtXUmFHlJ4aY/AeHQJ4VUcRNxMl4rZFX0QOKlW8XZ+sfjorDGPPuN05zExg2hHAyfTgAih4EEgtOFS9qa5d2QB7ybvKQ1vXQKZScVEgN12VP8JdaPBLqYxrsU78R8o7CkqlOaDHqW1FTNkqkdmJpHmrGImzqUgEoxyf1zFmeK+CnKp1mBeJD1EI6TmvUodNNdzmkkynKxHtlwkLcqIxquCgAJ0YDUE5TzzCXF/Jwqi1HDxP4+uTdttiDFz4LNGqQdEdZflYlbe6TjjsWrD0Jyx72rwLHS1igMC5ckwWyOce2l9lQFtbd2z0/EocMk1AEGptmtg3r692ZSi11DkbjAFi7uFoXXBFduh6b2I4ln/XilypANGMbEbVXmLPndPxCDIyZrN8lqocnfVuamSY6SS9K3o3r5an2b9+5Gf8h7gyuWm88sITPOg95+vBeFnCZH/dnD7+8J7nIvSS2CxHl56wiFTG86S8dbS+JMWKpW+eXUWpspYhZPWb4L2WhKRKbAud+tPShpJZ16BmVIQrLOtf6qA9O682w69wRKo4pkEi/jdIH3cgMLy1oVdNjbb6918NNj5fDjKYGWyiV/JtQIp7dIfJp9lco4JbBHceLt42VAiLSbtvihgCGw1buead8jCCqwxdETtaSw5iYrMi+CuZbLdp7xxe9ebvE8iXrlenEOYqRqOUYcTwOuL6X1qh1/2OHyXQjUmywiK3pFWKiRufbP09PdGtNZYmrN2c7V85Lf9PX8xv5JifHnENq6Jh1aKUn7M0+wFduimKlU1aarZBkuBus8p+Y4PbKzymHSZoE1kgxmnN1tM4oXsGGq1rKNB8DOBVj6muGLVLaymjL8FBWnfIYKEUq4MzmLm+UcXW3ckP3Gd0qe0M0h/K2JR6q3PWbgxrYT/FvkrAIDJVcDffJaBWAPTZeJeG3Bg8wOMUAUQmH7kJMM8KXIav/L5r7TYifk9N/fm64uZtFTTvVKHx3OzGo6XbPvGqqudclErivlVbZSrfvpQTArJiO6XSyB88O2gEC+z66Gv+9+5tvTczxSOQz3ZHsdbraCbXkqYlpZvibjw8L+19nNYzNGvacmT50XpzOshp+yRJDc/APXPHasR1A3fqvK+p08bEq4PNwWc8AeupKPKOXlyOVQCri3U4AokjPxy5JVicfJJ4BX8sqjQBFYxpJGdfoK+JFSDvaJbSHJlK+KuxjLVmbHJQlx+UsrdtcpmChlK8k1ZcFt/1DyBGC+r9lTii1JTX53URiVEx8OXtObHjbnLQ=";
		String str2 = "XmFOQZNSyfbMoJfDj4VEHw5GkmTV6HnAyb1ITJnGf1Neqvy5h X4FPaCA4a+/y1ZZna1UbscriqdN1py32shx4d4nG/jjOlJOqLej6Qu/K5PNWzXbLt3FtpRxFp/tcVv5n8cCY4dy7H5vxEpFkIRuTud8zviuwT7kxFIIimJkiaYrthhIqEJTKVodJbBjAe3oaxwEXX4etLfA9RkSYYM38dv62KZju6VBTWG9IWBF83IrfCx1+q8O1PW/at8DSpsXmlb+LuYY4M7R2q/K1C7qmeilVMEHwB/JTv07onstFbEIFDEwQjDyv7d46CXq4+02Jpb5rkEP1sJpXVj0mjvfDn9N+37Thi8+ifMyGgs3GK8EZvVnKfnY9P5y4KyPCynYPfk73HSfArmW4N+ogoBwEhBBxtOtJ2UE4HpxzJ5K5pHLXtXUmFHlJ4aY/AeHQJ4VUcRNxMl4rZFX0QOKlW8XZ+sfjorDGPPuN05zExg2hHAyfTgAih4EEgtOFS9qa5d2QB7ybvKQ1vXQKZScVEgN12VP8JdaPBLqYxrsU78R8o7CkqlOaDHqW1FTNkqkdmJpHmrGImzqUgEoxyf1zFmeK+CnKp1mBeJD1EI6TmvUodNNdzmkkynKxHtlwkLcqIxquCgAJ0YDUE5TzzCXF/Jwqi1HDxP4+uTdttiDFz4LNGqQdEdZflYlbe6TjjsWrD0Jyx72rwLHS1igMC5ckwWyOce2l9lQFtbd2z0/EocMk1AEGptmtg3r692ZSi11DkbjAFi7uFoXXBFduh6b2I4ln/XilypANGMbEbVXmLPndPxCDIyZrN8lqocnfVuamSY6SS9K3o3r5an2b9+5Gf8h7gyuWm88sITPOg95+vBeFnCZH/dnD7+8J7nIvSS2CxHl56wiFTG86S8dbS+JMWKpW+eXUWpspYhZPWb4L2WhKRKbAud+tPShpJZ16BmVIQrLOtf6qA9O682w69wRKo4pkEi/jdIH3cgMLy1oVdNjbb6918NNj5fDjKYGWyiV/JtQIp7dIfJp9lco4JbBHceLt42VAiLSbtvihgCGw1buead8jCCqwxdETtaSw5iYrMi+CuZbLdp7xxe9ebvE8iXrlenEOYqRqOUYcTwOuL6X1qh1/2OHyXQjUmywiK3pFWKiRufbP09PdGtNZYmrN2c7V85Lf9PX8xv5JifHnENq6Jh1aKUn7M0+wFduimKlU1aarZBkuBus8p+Y4PbKzymHSZoE1kgxmnN1tM4oXsGGq1rKNB8DOBVj6muGLVLaymjL8FBWnfIYKEUq4MzmLm+UcXW3ckP3Gd0qe0M0h/K2JR6q3PWbgxrYT/FvkrAIDJVcDffJaBWAPTZeJeG3Bg8wOMUAUQmH7kJMM8KXIav/L5r7TYifk9N/fm64uZtFTTvVKHx3OzGo6XbPvGqqudclErivlVbZSrfvpQTArJiO6XSyB88O2gEC+z66Gv+9+5tvTczxSOQz3ZHsdbraCbXkqYlpZvibjw8L+19nNYzNGvacmT50XpzOshp+yRJDc/APXPHasR1A3fqvK+p08bEq4PNwWc8AeupKPKOXlyOVQCri3U4AokjPxy5JVicfJJ4BX8sqjQBFYxpJGdfoK+JFSDvaJbSHJlK+KuxjLVmbHJQlx+UsrdtcpmChlK8k1ZcFt/1DyBGC+r9lTii1JTX53URiVEx8OXtOY3jbnLQ=";
		String str3 = "XmFOQZNSyfbMoJfDj4VEHw5GkmTV6HnAyb1ITJnGf1Jeqvy5h HJwOaeG5tq4uCEun6NXGsZejaNI1pOz3LhwkqlWHf/jOlJOqLej6Qu/K5PNWzXbLt3FtpRxFp/tcVv5n8cCY4dy7H5vxEpFkIRuTud8zviuwT7kxFIIimJkiaYrthhIqEJTKVodJbBjAe3oaxwEXX4etLfA9RkSYYM38dv62KZju6VBTWG9IWBF83IrfCx1+q8O1PW/at8DSpsXmlb+LuYY4M7R2q/K1C7qmeilVMEHwB/JTv07onstFbEIFDEwQjDyv7d46CXq4+02Jpb5rkEP1sJpXVj0mjvfDn9N+37Thi8+ifMyGgs3GK8EZvVnKfnY9P5y4KyPCynYPfk73HSfArmW4N+ogoBwEhBBxtOtJ2UE4HpxzJ5K5pHLXtXUmFHlJ4aY/AeHQJ4VUcRNxMl4rZFX0QOKlW8XZ+sfjorDGPPuN05zExg2hHAyfTgAih4EEgtOFS9qa5d2QB7ybvKQ1vXQKZScVEgN12VP8JdaPBLqYxrsU78R8o7CkqlOaDHqW1FTNkqkdmJpHmrGImzqUgEoxyf1zFmeK+CnKp1mBeJD1EI6TmvUodNNdzmkkynKxHtlwkLcqIxquCgAJ0YDUE5TzzCXF/Jwqi1HDxP4+uTdttiDFz4LNGqQdEdZflYlbe6TjjsWrD0Jyx72rwLHS1igMC5ckwWyOce2l9lQFtbd2z0/EocMk1AEGptmtg3r692ZSi11DkbjAFi7uFoXXBFduh6b2I4ln/XilypANGMbEbVXmLPndPxCDIyZrN8lqocnfVuamSY6SS9K3o3r5an2b9+5Gf8h7gyuWm88sITPOg95+vBeFnCZH/dnD7+8J7nIvSS2CxHl56wiFTG86S8dbS+JMWKpW+eXUWpspYhZPWb4L2WhKRKbAud+tPShpJZ16BmVIQrLOtf6qA9O682w69wRKo4pkEi/jdIH3cgMLy1oVdNjbb6918NNj5fDjKYGWyiV/JtQIp7dIfJp9lco4JbBHceLt42VAiLSbtvihgCGw1buead8jCCqwxdETtaSw5iYrMi+CuZbLdp7xxe9ebvE8iXrlenEOYqRqOUYcTwOuL6X1qh1/2OHyXQjUmywiK3pFWKiRufbP09PdGtNZYmrN2c7V85Lf9PX8xv5JifHnENq6Jh1aKUn7M0+wFduimKlU1aarZBkuBus8p+Y4PbKzymHSZoE1kgxmnN1tM4oXsGGq1rKNB8DOBVj6muGLVLaymjL8FBWnfIYKEUq4MzmLm+UcXW3ckP3Gd0qe0M0h/K2JR6q3PWbgxrYT/FvkrAIDJVcDffJaBWAPTZeJeG3Bg8wOMUAUQmH7kJMM8KXIav/L5r7TYifk9N/fm64uZtFTTvVKHx3OzGo6XbPvGqqudclErivlVbZSrfvpQTArJiO6XSyB88O2gEC+z66Gv+9+5tvTczxSOQz3ZHsdbraCbXkqYlpZvibjw8L+19nNYzNGvacmT50XpzOshp+yRJDc/APXPHasR1A3fqvK+p08bEq4PNwWc8AeupKPKOXlyOVQCri3U4AokjPxy5JVicfJJ4BX8sqjQBFYxpJGdfoK+JFSDvaJbSHJlK+KuxjLVmbHJQlx+UsrdtcpmChlK8k1ZcFt/1DyBGC+r9lTii1JTX53URiVEx8OXtOYnjbnLQ=";
		System.out.println(str1.length());
		byte[] bs = (new BASE64Decoder()).decodeBuffer(str2);
		System.out.println(new String(bs, "utf-8"));
		System.out.println(new String(bs, "gbk"));
		System.out.println(new String(bs, "gb2312"));
		String str = (new BASE64Encoder()).encode(str1.getBytes());
		System.out.println(str);
	}

	private static void m2() throws IOException {
		List<File> files = new ArrayList<File>();
		findXtbFiles(files, new File("F:/source2"));
		for (File f : files) {
			if (!f.getName().endsWith("zh-CN.xtb")) {
				continue;
			}
			File newFile = new File("F:/xtb/" + f.getName());
			if (newFile.exists()) {
				newFile = new File("F:/xtb/" + f.getName() + UUID.getUUID() + ".xtb");
			}
			FileUtils.copyFile(f, newFile);
		}
	}

	private static void findXtbFiles(List<File> files, File file) {
		for (File f : file.listFiles()) {
			if (f.isFile()) {
				if (f.getName().endsWith(".xtb")) {
					files.add(f);
				}
			} else {
				findXtbFiles(files, f);
			}
		}
	}

	private static void m1() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("F:/temp/log.txt"))));
			String line = null;
			while ((line = br.readLine()) != null) {
				String path = line.substring(0, line.indexOf(":"));
				if (new File("F:/temp/" + path).exists()) {
					FileUtil.append("f:/temp/log2.txt", line + "\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static String get(String aaa) {
		if (aaa.indexOf(".") == -1) {
			return "";
		}
		return aaa.substring(aaa.lastIndexOf("."), aaa.length());
	}

	private static void copyFiles(File file) throws IOException {
		if (file.isDirectory()) {
			for (File cf : file.listFiles()) {
				copyFiles(cf);
			}
		} else {
			if (names.contains(get(file.getName()))) {
				File destFile = new File("f:/temp/" + UUID.getUUID() + get(file.getName()));
				FileUtils.copyFile(file, destFile);
				FileUtil.append("f:/temp/log.txt", destFile.getName() + ":" + file.getAbsolutePath() + "\n");
			}
		}
	}
}
