
/**
 * 菜单的使用
 */
package com.hzth.myapp.swing;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuTest {
	private JFrame frame;
	private JMenuBar bar;//菜单条
	private JMenu file,edit,help;
	
	public MenuTest(){
		frame=new JFrame("Menu Test");
		bar=new JMenuBar();
		file=new JMenu("文件");
		edit=new JMenu("编辑");
		help=new JMenu("帮助");
		
		file.add(new JMenuItem("新建..."));
		file.add(new JMenuItem("打开..."));
		file.addSeparator();//添加分割线
		file.add(new JMenuItem("保存..."));
		file.add(new JMenuItem("另存为..."));
		file.addSeparator();
		file.add(new JMenuItem("退出..."));
		
		edit.add(new JMenuItem("查找..."));
		edit.add(new JMenuItem("替换..."));
		edit.addSeparator();
		edit.add(new JMenuItem("复制..."));
		edit.add(new JMenuItem("粘贴..."));
		edit.add(new JMenuItem("拷贝..."));
		
		help.add(new JMenuItem("帮助主题..."));
		help.add(new JMenuItem("关于..."));
		
		bar.add(file);
		bar.add(edit);
		bar.add(help);
		
		frame.setJMenuBar(bar);//添加菜单条
	}
	
	public void showMe(){
		frame.setSize(400,300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new MenuTest().showMe();
	}

}
