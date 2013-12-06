package com.hzth.myapp.swing;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

import com.hzth.myapp.core.util.DateUtil;
import com.hzth.myapp.core.util.FileUtil;

public class SystemTrayDemo implements WrapperListener {

	public static void main(String[] args) {
		WrapperManager.start(new SystemTrayDemo(), args);
	}

	public SystemTrayDemo() {
		log("创建SystemTrayDemo对象");
	}

	private static void log(String msg) {
		FileUtil.append("D:/wrapper-windows-x86-32-3.2.3/demo/aa.txt", DateUtil.getCurrentDate() + ":" + msg + "\r\n");
	}

	@Override
	public void controlEvent(int arg0) {

	}

	@Override
	public Integer start(String[] arg0) {
		log("start");
		// new MainFrame();
		new MainFram2();
		return null;
	}

	@Override
	public int stop(int arg0) {
		return 0;
	}

	private class MainFram2 extends Frame {
		public MainFram2() {
			this.setLocation(100, 100);
			this.setBounds(100, 100, 100, 100);
			this.pack();
			this.setVisible(true);
		}
	}

	private class MainFrame extends JFrame {

		private static final long serialVersionUID = -6223337876330360373L;

		public MainFrame() {
			init();
		}

		private void init() {
			this.setSize(300, 200);
			this.setLocation(20, 30);
			this.setLocationRelativeTo(null);
			JLabel label = new JLabel("测试test");
			this.add(label);
			// setTray();
			this.setVisible(true);
		}

		private void exit() {
			System.exit(0);
		}

		private void hideFrame() {
			this.setVisible(false);
		}

		private void showFrame() {
			log("showFrame");
			this.setVisible(true);
		}

		private void setTray() {
			if (!SystemTray.isSupported()) {
				return;
			}
			SystemTray tray = SystemTray.getSystemTray();
			Image image = Toolkit.getDefaultToolkit().getImage(SystemTrayDemo.class.getResource("icon.jpg"));
			String title = "托盘程序提示";
			PopupMenu menu = new PopupMenu();
			MenuItem open = new MenuItem("打开");
			open.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					showFrame();
				}
			});
			menu.add(open);

			MenuItem hide = new MenuItem("隐藏");
			hide.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					hideFrame();
				}
			});
			menu.add(hide);

			MenuItem exit = new MenuItem("退出");
			exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					exit();
				}
			});
			menu.add(exit);

			TrayIcon trayIcon = new TrayIcon(image, title, menu);
			trayIcon.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					showFrame();
				}
			});
			try {
				tray.add(trayIcon);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
		}
	}
}
