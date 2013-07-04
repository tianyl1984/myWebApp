package com.hzth.myapp.swing;

import java.awt.*;
import javax.swing.*;

public class FlowLayoutTest {

	public static void main(String[] args) {
		JFrame frame=new JFrame("FlowLayout Test");
		frame.setLayout(new FlowLayout());
		for(int i=0;i<10;i++){
			frame.add(new JButton("button"+i));
//			frame.getContentPane().add(new JButton("button"+i));//jdk5.0之前用法
		}
		
		frame.setSize(400, 300);//设置大小
		frame.setLocation(300, 250);//设置位置
		frame.setVisible(true);//设置可见
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭默认操作
	}

}
