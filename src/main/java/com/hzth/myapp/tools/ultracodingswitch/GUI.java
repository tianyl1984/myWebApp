package com.hzth.myapp.tools.ultracodingswitch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI {
	private JFrame frame;
	private JTextArea jta;
	private JLabel label1, label2;
	private JTextField fromFolder, toFolder;
	private JButton fromSelect, toSelect, okButton, cancelButton;
	private JRadioButton radio1, radio2;
	private File[] fromFile;
	private File toFile;

	public GUI() {
		frame = new JFrame("文档编码转换器");
		jta = new JTextArea(30, 10);
		label1 = new JLabel("文件来源：");
		label2 = new JLabel("保存位置：");
		fromFolder = new JTextField(20);
		toFolder = new JTextField(20);
		fromSelect = new JButton("浏览....");
		toSelect = new JButton("浏览....");
		okButton = new JButton("开始");
		cancelButton = new JButton("取消");
		radio1 = new JRadioButton("UTF-8-->GB2312", true);
		radio2 = new JRadioButton("GB2312-->UTF-8");
		init();
		addEventHandler();
	}

	private void init() {
		Box b = Box.createVerticalBox();
		jta.setEditable(false);
		JScrollPane jsp = new JScrollPane(jta);
		b.add(jsp);
		JPanel jp1 = new JPanel();
		jp1.add(label1);
		jp1.add(fromFolder);
		jp1.add(fromSelect);
		b.add(jp1);
		JPanel jp2 = new JPanel();
		jp2.add(label2);
		jp2.add(toFolder);
		jp2.add(toSelect);
		b.add(jp2);
		ButtonGroup bg = new ButtonGroup();
		bg.add(radio1);
		bg.add(radio2);
		JPanel jp3 = new JPanel();
		jp3.add(radio1);
		jp3.add(radio2);
		b.add(jp3);
		JPanel jp4 = new JPanel();
		jp4.add(okButton);
		jp4.add(cancelButton);
		b.add(jp4);
		frame.add(b);
	}

	private void addEventHandler() {
		fromSelect.addActionListener(new FileChooserActionListener(JFileChooser.FILES_AND_DIRECTORIES));
		toSelect.addActionListener(new FileChooserActionListener(JFileChooser.DIRECTORIES_ONLY));
		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Decoder d = new Decoder();
				if (radio1.isSelected()) {
					d.setFromCode("UTF-8");
					d.setToCode("GB2312");
				} else {
					d.setFromCode("GB2312");
					d.setToCode("UTF-8");
				}
				List<File> files = new ArrayList<File>();
				getAllFile(files, fromFile);
				for (File tempFile : files) {
					d.setFromFile(tempFile);
					d.setToFile(new File(toFile.getAbsoluteFile() + "/" + tempFile.getName()));
					d.start();
				}
				JOptionPane.showMessageDialog(frame, "完成转换，共转换文件" + files.size() + "个", "完成", JOptionPane.OK_OPTION, null);
			}

		});
		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});
	}

	private class FileChooserActionListener implements ActionListener {
		private int mode;

		public FileChooserActionListener(int mode) {
			this.mode = mode;
		}

		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));
			jfc.setFileSelectionMode(mode);
			if (mode == JFileChooser.FILES_AND_DIRECTORIES) {
				jfc.setMultiSelectionEnabled(true);
				int tempi = jfc.showOpenDialog(frame);
				if (tempi == JFileChooser.APPROVE_OPTION) {
					fromFile = jfc.getSelectedFiles();
					if (fromFile.length == 1) {
						fromFolder.setText(fromFile[0].getAbsolutePath());
					} else {
						fromFolder.setText(fromFile[0].getParent());
					}
					jta.setText("");
					List<File> fileList = new ArrayList<File>();
					getAllFile(fileList, fromFile);
					for (File tempFile : fileList) {
						jta.append(tempFile.getAbsolutePath() + "\n");
					}
				}
			}
			if (mode == JFileChooser.DIRECTORIES_ONLY) {
				int tempi = jfc.showSaveDialog(frame);
				if (tempi == JFileChooser.APPROVE_OPTION) {
					toFile = jfc.getSelectedFile();
					toFolder.setText(toFile.getAbsolutePath());
				}
			}
		}

	}

	private void getAllFile(List<File> list, File[] files) {
		for (File file : files) {
			if (file.isFile()) {
				list.add(file);
			} else {
				getAllFile(list, file.listFiles());
			}
		}

	}

	public void showMe() {
		frame.setBounds(200, 200, 400, 300);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new GUI().showMe();
	}
}
