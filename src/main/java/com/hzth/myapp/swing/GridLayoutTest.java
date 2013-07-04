package com.hzth.myapp.swing;

import java.awt.*;
import javax.swing.*;

public class GridLayoutTest {

	public static void main(String[] args) {
		JFrame frame=new JFrame("Grid Layout Test");
		frame.setLayout(new GridLayout(3,5,10,15));//参数代表3行5列的网格,水平间距10，竖直间距15
		for(int i=0;i<15;i++){
			frame.add(new JButton("Button"+i));
		}
		
		frame.setSize(450, 300);//设置大小
		frame.setLocation(300, 250);//设置位置
		frame.setVisible(true);//设置可见
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭默认操作
	}

}
