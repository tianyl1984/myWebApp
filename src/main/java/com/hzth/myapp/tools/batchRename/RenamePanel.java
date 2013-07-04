package com.hzth.myapp.tools.batchRename;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RenamePanel extends JPanel {
	private static final long serialVersionUID = 4496337728121654134L;
	private JLabel label1, label2, label3;
	private JTextField jtf1, jtf2, jtf3;
	private JButton previewButton, okButton, cancelButton;
	// private JCheckBox jcb;
	private List<File> oldFiles;
	private List<File> newFiles;

	public RenamePanel(List<File> files) {
		oldFiles = files;
		label1 = new JLabel("新名字*代表变量");
		label2 = new JLabel("开始");
		label3 = new JLabel("增量");
		jtf1 = new JTextField(15);
		jtf2 = new JTextField(5);
		jtf3 = new JTextField(5);
		// jcb=new JCheckBox("保留扩展名",true);
		previewButton = new JButton("预览");
		okButton = new JButton("确定");
		cancelButton = new JButton("取消");
		JPanel jp1 = new JPanel();
		jp1.add(label1);
		jp1.add(jtf1);
		setLayout(new BorderLayout());
		add(jp1, BorderLayout.NORTH);
		JPanel jp2 = new JPanel();
		jp2.add(label2);
		jp2.add(jtf2);
		jp2.add(label3);
		jp2.add(jtf3);
		add(jp2, BorderLayout.CENTER);
		// add(jcb);
		JPanel jp3 = new JPanel();
		jp3.add(previewButton);
		jp3.add(okButton);
		jp3.add(cancelButton);
		add(jp3, BorderLayout.SOUTH);
		// setSize(300,150);
		addEventHandler();
	}

	private void addEventHandler() {
		previewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String jtf1Str = jtf1.getText().trim();
				if (jtf1Str.contains("/")) {// 不同系统命名规范不同。可用正则表达式验证
					JOptionPane.showMessageDialog(RenamePanel.this, "请重新定义新名字，包含非法字符");
					return;
				}
				try {
					Integer.parseInt(jtf2.getText().trim());
					Integer.parseInt(jtf3.getText().trim());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(RenamePanel.this, "开始值和增量只能是数字");
					return;
				}
				try {
					newFiles = getNewName(oldFiles);
				} catch (NumberFlowOverException e1) {
					JOptionPane.showMessageDialog(RenamePanel.this, "增量太大，数字超过原来位数");
					return;
				}
				FileChooserPanel.jta2.setText("");
				for (File file : newFiles) {
					FileChooserPanel.jta2.append(file.getAbsolutePath() + "\n");
				}
			}

			private List<File> getNewName(List<File> oldFiles) throws NumberFlowOverException {
				List<File> newFiles = new ArrayList<File>();
				int starti = 0;
				if (!jtf2.getText().trim().equals("")) {
					starti = Integer.parseInt(jtf2.getText().trim());
				} else {
					starti = Integer.parseInt(jtf2.getText().trim());
				}
				for (int i = 0; i < oldFiles.size(); i++) {
					String name = oldFiles.get(i).getParent() + File.separator + jtf1.getText().trim();
					String temps = "" + starti;
					int ii = jtf2.getText().trim().length() - temps.length();
					if (ii < 0) {
						throw new NumberFlowOverException("增量太大，数字超过原来位数");
					}
					for (int j = 0; j < ii; j++) {
						temps = "0" + temps;
					}
					name = name.replace("*", temps);
					newFiles.add(new File(name));
					starti = starti + Integer.parseInt(jtf3.getText().trim());
				}
				return newFiles;
			}

		});

		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				previewButton.doClick();
				int count = 0;
				for (int i = 0; i < oldFiles.size(); i++) {
					if (newFiles.get(i).exists()) {
						continue;
					} else {
						oldFiles.get(i).renameTo(newFiles.get(i));
						count++;
					}
				}
				JOptionPane.showMessageDialog(RenamePanel.this, "成功重命名" + count + "个文件");
			}

		});

		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});

	}

	public static void main(String[] args) {
		JFrame jf = new JFrame();
		// jf.add(new RenamePanel());
		jf.setBounds(100, 100, 300, 150);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
