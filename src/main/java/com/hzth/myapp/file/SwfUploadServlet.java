package com.hzth.myapp.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.hzth.myapp.core.util.WebUtil;

public class SwfUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1490339965654435066L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// doPost(request, response);
		System.out.println("doGet in SwfUploadServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebUtil.printParameter(request);
		System.out.println("处理请求..." + System.currentTimeMillis());
		try {
			request.setCharacterEncoding("utf-8");
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					String fileName = item.getName();
					InputStream fis = item.getInputStream();
					FileOutputStream fos = new FileOutputStream(new File("D:/tomcat9090/" + fileName));
					byte[] bs = new byte[1024];
					int read = -1;
					System.out.println("保存文件..." + System.currentTimeMillis());
					while ((read = fis.read(bs)) != -1) {
						fos.write(bs, 0, read);
					}
					fos.flush();
					fos.close();
					fis.close();
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.print("OK");
					out.flush();
					out.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("请求结束..." + System.currentTimeMillis());
	}

}
