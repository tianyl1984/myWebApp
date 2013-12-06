package com.hzth.myapp.javaAgent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class JavaAgentUtil {

	public static void main(String[] args) {
		createJar();
	}

	private static void createJar() {
		String zipFile = "C:/Users/tianyl/git/mywebapp/src/main/java/com/hzth/myapp/javaAgent/agent.jar";
		byte[] buffer = new byte[1024];
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
			out.putNextEntry(new ZipEntry("META-INF/MANIFEST.MF"));
			StringBuffer mf = new StringBuffer();
			mf.append("Manifest-Version: 1.0\r\n");
			mf.append("Premain-Class: com.hzth.myapp.javaAgent.JavaAgentDemo\r\n");
			mf.append("Boot-Class-Path: javassist.jar\r\n");
			out.write(mf.toString().getBytes());

			String path = JavaAgentUtil.class.getResource("JavaAgentUtil.class").getPath();
			File file = new File(path).getParentFile();
			for (File f : file.listFiles()) {
				if (f.getName().startsWith("JavaAgentDemo") || f.getName().startsWith("MonitorTransformer")) {
					String name = f.getPath().replaceFirst(".*com\\\\hzth\\\\myapp", "com\\\\hzth\\\\myapp");
					name = name.replaceAll("\\\\", "/");
					out.putNextEntry(new ZipEntry(name));
					FileInputStream fis = new FileInputStream(f);
					int len;
					while ((len = fis.read(buffer)) != -1) {
						out.write(buffer, 0, len);
					}
					out.closeEntry();
					fis.close();
				}
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
