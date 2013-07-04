package com.hzth.myapp.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadDocServlet extends HttpServlet {

	private static final long serialVersionUID = 2418455402437714620L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		downloadFile(req, resp);
	}

	private void downloadFile(HttpServletRequest request, HttpServletResponse response) {
		OutputStream toClient = null;
		InputStream fis = null;
		byte[] buffer = null;
		File file = new File("E://测试文件//aaa.docx");
		try {
			fis = new BufferedInputStream(new FileInputStream(file));
			buffer = new byte[1024];
			response.reset();
			response.addHeader("Content-Disposition", "inline;filename=" + new String(file.getName().getBytes(), "iso8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/doc");
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
