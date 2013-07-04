package com.hzth.myapp.tools.ultracodingswitch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Decoder {
	private File fromFile;
	private File toFile;
	private String fromCode;
	private String toCode;
	
	public Decoder(){
		
	}
	
	public Decoder(String fromFileName,String toFileName,String fromCode,String toCode){
		this(new File(fromFileName),new File(toFileName),fromCode,toCode);
	}
	
	public Decoder(File fromFile,File toFile,String fromCode,String toCode){
		this.fromFile=fromFile;
		this.toFile=toFile;
		this.fromCode=fromCode;
		this.toCode=toCode;
	}

	public String getFromCode() {
		return fromCode;
	}

	public void setFromCode(String fromCode) {
		this.fromCode = fromCode;
	}

	public File getFromFile() {
		return fromFile;
	}

	public void setFromFile(File fromFile) {
		this.fromFile = fromFile;
	}

	public String getToCode() {
		return toCode;
	}

	public void setToCode(String toCode) {
		this.toCode = toCode;
	}

	public File getToFile() {
		return toFile;
	}

	public void setToFile(File toFile) {
		this.toFile = toFile;
	}
	
	public void start(){
		BufferedReader br=null;
		PrintWriter pw=null;
		try {
			br=new BufferedReader(new InputStreamReader(new FileInputStream(fromFile),fromCode));
			pw=new PrintWriter(new OutputStreamWriter(new FileOutputStream(toFile),toCode));
			String temp;
			while((temp=br.readLine())!=null){
				pw.println(temp);
				pw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(pw!=null){
				pw.close();
			}
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {}
			}
		}
	}

}
