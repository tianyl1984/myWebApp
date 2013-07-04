package com.hzth.myapp.file.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "struts-default")
@Namespace(value = "/fp")
@Action(value = "fileUpload_*")
@Results({})
public class FileUploadAction extends ActionSupport {

	public FileUploadAction() {
		System.out.println("FileUploadAction()");
	}

	private static final long serialVersionUID = 6752949353257608478L;

	public String upload() {
		System.out.println("处理请求..." + System.currentTimeMillis());
		try {
			if (myfileupload != null) {
				System.out.println("复制文件..." + System.currentTimeMillis());
				for (File f : myfileupload) {
					FileInputStream fis = new FileInputStream(f);
					FileOutputStream fos = new FileOutputStream(new File("D:/tomcat9090/mynewfile"));
					byte[] bs = new byte[1024];
					int read = -1;
					while ((read = fis.read(bs)) != -1) {
						fos.write(bs, 0, read);
					}
					fos.flush();
					fos.close();
					fis.close();
				}
				System.out.println("复制完文件..." + System.currentTimeMillis());
			}
			ServletActionContext.getResponse().setCharacterEncoding("utf-8");
			ServletActionContext.getResponse().setContentType("text/html");
			PrintWriter pw = ServletActionContext.getResponse().getWriter();
			pw.println("" + ServletActionContext.getRequest().getParameter("aaa"));
			pw.println("<a href=\"javascript:window.location.href='/myWebApp/fp/upload.jsp'\">返回</a>");
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("请求结束..." + System.currentTimeMillis());
		return null;
	}

	private File[] myfileupload;
	private String[] myfileuploadFileName;
	private String[] myfileuploadContentType;

	public File[] getMyfileupload() {
		return myfileupload;
	}

	public void setMyfileupload(File[] myfileupload) {
		this.myfileupload = myfileupload;
	}

	public String[] getMyfileuploadFileName() {
		return myfileuploadFileName;
	}

	public void setMyfileuploadFileName(String[] myfileuploadFileName) {
		this.myfileuploadFileName = myfileuploadFileName;
	}

	public String[] getMyfileuploadContentType() {
		return myfileuploadContentType;
	}

	public void setMyfileuploadContentType(String[] myfileuploadContentType) {
		this.myfileuploadContentType = myfileuploadContentType;
	}

}
