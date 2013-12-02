package com.hzth.myapp.web.action;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.hzth.myapp.core.web.MyBaseAction;

@ParentPackage(value = "struts-default")
@Namespace(value = "/web")
@Action(value = "pojo_*")
public class PojoAction extends MyBaseAction {

	private static final long serialVersionUID = -7759621793390643967L;

	public void exportWebToDoc() throws Exception {
		// String host = this.getRequest().getRemoteHost();
		// Integer port = this.getRequest().getLocalPort();
		// String url = "http://" + host + ":" + port + this.getRequest().getContextPath() + "/jsp/webToDoc.jsp";
		// String content = NetUtil.getUrlResponse(url);

		String jspUrl = "/jsp/webToDoc.jsp";
		RequestDispatcher rd = this.getRequest().getRequestDispatcher(jspUrl);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintWriter pw = new PrintWriter(new OutputStreamWriter(baos, "utf-8"));
		HttpServletResponse resp = new HttpServletResponseWrapper(this.getResponse()) {
			@Override
			public PrintWriter getWriter() throws IOException {
				return pw;
			}
		};
		// jsp页面需要的数据
		rd.include(getRequest(), resp);
		pw.flush();
		pw.close();
		String content = baos.toString("utf-8");

		HttpServletResponse response = this.getResponse();
		OutputStream toClient = null;
		InputStream fis = null;
		byte[] buffer = null;
		try {
			fis = new ByteArrayInputStream(content.getBytes());
			buffer = new byte[1024];
			response.reset();
			response.addHeader("Content-Disposition", "inline;filename=" + new String("文件.doc".getBytes(), "iso8859-1"));
			// response.addHeader("Content-Length", "" + content.getBytes().length);
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

	public void ajaxDemo() {
		this.print(this.getRequest().getParameter("aaa"));
	}
}
