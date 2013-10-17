package com.hzth.myapp.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpDemo {

	public static void main(String[] args) throws Exception {
		FTPClient client = login();
		// listFiles(client);
		// checkDir(client, "中文目录");
		// createDir(client);
		// uploadFile(client);

		download(client);

		System.out.println("退出。。。。");
		client.logout();
	}

	public static void download(FTPClient client) throws Exception {
		client.enterLocalPassiveMode();
		client.setFileType(FTPClient.BINARY_FILE_TYPE);
		client.changeWorkingDirectory("/");
		client.setBufferSize(1024);
		String name = "中文目录/二级目录/员工信息表.doc";
		OutputStream local = new FileOutputStream(new File("C:/Users/tianyl/Desktop/员工信息表.doc"));
		name = new String(name.getBytes("gbk"), "iso-8859-1");
		System.out.println("开始下载。。。");
		boolean flag = client.retrieveFile(name, local);
		local.close();
		System.out.println("下载" + (flag ? "成功" : "失败"));
	}

	public static void checkDir(FTPClient client, String dir) throws Exception {
		client.enterLocalPassiveMode();
		boolean flag = client.changeWorkingDirectory("/" + new String(dir.getBytes("gbk"), "iso-8859-1"));
		System.out.println("文件夹" + (flag ? "存在" : "不存在"));
	}

	public static void createDir(FTPClient client) throws Exception {
		client.enterLocalPassiveMode();
		client.changeWorkingDirectory("/");
		// client.setControlEncoding("utf-8");
		System.out.println("开始创建文件夹。。。");
		String name = "中文目录/二级目录";
		name = new String(name.getBytes("gbk"), "iso-8859-1");
		boolean flag = client.makeDirectory(name);
		System.out.println("文件夹创建" + (flag ? "成功" : "失败"));
	}

	public static void listFiles(FTPClient client) throws Exception {
		FTPFile[] files = client.listFiles();
		for (FTPFile file : files) {
			System.out.println(file.getName());
		}
	}

	public static void uploadFile(FTPClient client) throws Exception {
		client.enterLocalPassiveMode();
		client.setFileType(FTPClient.BINARY_FILE_TYPE);
		client.changeWorkingDirectory("/");
		client.setBufferSize(1024);
		client.setControlEncoding("gbk");
		InputStream in = new FileInputStream(new File("e:/测试文件/员工信息表.doc"));
		System.out.println("开始上传文件。。。");
		String name = "员工信息表.doc";
		boolean flag = client.storeFile("/" + new String(name.getBytes("gbk"), "iso-8859-1"), in);
		in.close();
		System.out.println("文件上传" + (flag ? "成功" : "失败"));
	}

	public static FTPClient login() throws Exception {
		FTPClient client = new FTPClient();
		System.out.println("开始连接服务器。。。");
		client.connect("192.168.30.159", 21);
		int reply = client.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			client.disconnect();
			System.out.println("登录失败。。。");
			System.exit(0);
		}
		boolean login = client.login("anonymous", "aaa@aaa.com");
		System.out.println("登录" + (login ? "成功" : "失败"));
		if (!login) {
			client.disconnect();
			System.exit(0);
		}
		return client;
	}
}
