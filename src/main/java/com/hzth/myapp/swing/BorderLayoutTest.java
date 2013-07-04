package com.hzth.myapp.swing;

import java.awt.*;
import javax.swing.*;

public class BorderLayoutTest {
	public static void main(String[] args) {
		JFrame frame=new JFrame("Border Layout Test");
		frame.setLayout(new BorderLayout());
		frame.add(new JButton("东"),BorderLayout.EAST);
		frame.add(new JButton("南"),BorderLayout.SOUTH);
		frame.add(new JButton("西"),BorderLayout.WEST);
		frame.add(new JButton("北"),BorderLayout.NORTH);
		frame.add(new JButton("中"),BorderLayout.CENTER);
		
		frame.setSize(400, 300);//设置大小
		frame.setLocation(300, 250);//设置位置
		frame.setVisible(true);//设置可见
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭默认操作
	}

}
