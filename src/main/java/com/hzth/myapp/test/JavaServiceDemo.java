package com.hzth.myapp.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

//使用wrapper实现java程序注册为服务
//》测试运行是否正常
//wrapper.exe -c wrapper.conf 
//》安装服务
//wrapper.exe -i wrapper.conf 
//》卸载服务
//wrapper.exe -r wrapper.conf 
//》启动
//wrapper.exe -t wrapper.conf 
//》停止
//wrapper.exe -p wrapper.conf
public class JavaServiceDemo implements WrapperListener {

	public static void main(String[] args) {
		WrapperManager.start(new JavaServiceDemo(), args);
	}

	@Override
	public void controlEvent(int arg0) {

	}

	@Override
	public Integer start(String[] arg0) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					FileWriter fw = null;
					try {
						fw = new FileWriter(new File("log.txt"), true);
						fw.append("当前时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (fw != null) {
							try {
								fw.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}).start();
		return null;
	}

	@Override
	public int stop(int arg0) {
		return 0;
	}
}
