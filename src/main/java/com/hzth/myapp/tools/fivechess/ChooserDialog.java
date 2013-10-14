package com.hzth.myapp.tools.fivechess;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ChooserDialog {
	private JDialog dialog;
	private JLabel ipLabel, portLabel;
	private JTextField ip, port;
	private JButton ok, cancel;

	public ChooserDialog() {

	}

	public ChooserDialog(boolean flag) {
		dialog = new JDialog();
		ipLabel = new JLabel("地址:");
		portLabel = new JLabel("端口:");
		ip = new JTextField(10);
		port = new JTextField(10);
		ipLabel.setVisible(flag);
		ip.setVisible(flag);
		ok = new JButton("确定");
		cancel = new JButton("取消");
		init();
	}

	private void init() {
		dialog.setTitle("请填写");
		dialog.setLayout(new FlowLayout());
		dialog.add(ipLabel);
		dialog.add(ip);
		dialog.add(portLabel);
		dialog.add(port);
		dialog.add(ok);
		dialog.add(cancel);

		dialog.setBounds(300, 400, 180, 120);
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}
