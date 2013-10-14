package com.hzth.myapp.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class DownloadServlet extends HttpServlet {

	private static final long serialVersionUID = -8632108367808041997L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("开始下载");
		printUserInfo(req, resp);
		downloadFile(req, resp);
		// downloadFilePlain(req, resp);
		System.out.println("完成下载");
	}

	private void printUserInfo(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println(req.getRemoteHost() + ":" + req.getHeader("User-Agent"));
		Enumeration e = req.getHeaderNames();
		while (e.hasMoreElements()) {
			String header = e.nextElement().toString();
			String val = "";
			Enumeration e2 = req.getHeaders(header);
			while (e2.hasMoreElements()) {
				val += "[" + e2.nextElement().toString() + "]";
			}
			System.out.println(header + ":" + val);
		}
	}

	private void downloadFile(HttpServletRequest request, HttpServletResponse response) {
		OutputStream toClient = null;
		// InputStream fis = null;
		RandomAccessFile raf = null;
		byte[] buffer = null;
		File file = new File("E://测试文件//test.docx");
		try {
			// fis = new BufferedInputStream(new FileInputStream(file));
			raf = new RandomAccessFile(file, "r");
			buffer = new byte[1024];
			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes(), "iso8859-1"));
			response.setContentType("application/octet-stream");
			// tell the client to allow accept-ranges
			response.addHeader("Accept-Ranges", "bytes");

			Long start = 0L;// 包含
			Long fileLength = file.length();
			Long end = fileLength;// 不包含
			// client requests a file block download start byte
			if (request.getHeader("Range") != null) {
				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
				String str = request.getHeader("Range").replaceAll("bytes=", "");
				if (str.indexOf("-") != -1) {
					start = Long.parseLong(str.split("-")[0].trim());
					if (str.split("-").length > 1 && StringUtils.isNotBlank(str.split("-")[1])) {
						end = Long.parseLong(str.split("-")[1].trim()) + 1;
					}
				}
			} else {
				response.setStatus(HttpServletResponse.SC_OK);
			}
			if (start > fileLength) {
				start = fileLength;
			}
			if (end > fileLength) {
				end = fileLength;
			}
			// support multi-threaded download
			// respone format:
			// Content-Length:[file size] - [client request start bytes from file block]
			response.setHeader("Content-Length", new Long(end - start).toString());

			if (start != 0) {
				// 断点开始
				// 响应的格式是:
				// Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
				String contentRange = "bytes " + new Long(start).toString() + "-" + new Long(end - 1).toString() + "/" + new Long(fileLength).toString();
				// String contentRange = new StringBuffer("bytes ").append(new Long(start).toString()).append("-").append(new Long(fileLength - 1).toString()).append("/").append(new Long(fileLength).toString()).toString();
				response.setHeader("Content-Range", contentRange);
				// pointer move to seek
				// fis.skip(start);
				raf.seek(start);
			}

			toClient = new BufferedOutputStream(response.getOutputStream());
			int bytesRead;
			if (end - start > buffer.length) {
				int hasRead = 0;
				int needRead = buffer.length;
				while ((bytesRead = raf.read(buffer, 0, needRead)) != -1) {
					toClient.write(buffer, 0, bytesRead);
					hasRead += bytesRead;
					if (end - start == hasRead) {
						break;
					}
					if (hasRead + needRead > end - start) {
						needRead = Long.valueOf(end - start).intValue() - hasRead;
					}
				}
			} else {
				bytesRead = raf.read(buffer, 0, Long.valueOf((end - start)).intValue());
				toClient.write(buffer, 0, bytesRead);
			}
			toClient.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
				}
				raf = null;
			}
			if (toClient != null) {
				try {
					toClient.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private void downloadFilePlain(HttpServletRequest request, HttpServletResponse response) {
		OutputStream toClient = null;
		InputStream fis = null;
		byte[] buffer = null;
		File file = new File("E://测试文件//文件1");
		try {
			fis = new BufferedInputStream(new FileInputStream(file));
			buffer = new byte[1024];
			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes(), "iso8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			int bytesRead;
			while (-1 != (bytesRead = fis.read(buffer, 0, buffer.length))) {
				toClient.write(buffer, 0, bytesRead);
			}
			toClient.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
				fis = null;
			}
			if (toClient != null) {
				try {
					toClient.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
