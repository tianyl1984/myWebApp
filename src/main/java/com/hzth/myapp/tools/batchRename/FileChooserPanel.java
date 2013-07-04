package com.hzth.myapp.tools.batchRename;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FileChooserPanel extends JPanel {
	private static final long serialVersionUID = 5471693409741568756L;
	private JTextField fileField;
	private JButton chooseButton;
	private JLabel label1, label2;
	public static JTextArea jta1, jta2;
	private List<File> fileList;

	public FileChooserPanel() {
		fileList = new ArrayList<File>();
		Box b = Box.createVerticalBox();
		fileField = new JTextField(20);
		chooseButton = new JButton("浏览");
		JPanel jp1 = new JPanel();
		jp1.add(fileField);
		jp1.add(chooseButton);
		b.add(jp1);
		label1 = new JLabel("原文件");
		JPanel jp2 = new JPanel();
		jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp2.add(label1);
		b.add(jp2);
		label2 = new JLabel("预览");
		JPanel jp3 = new JPanel();
		jp3.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp3.add(label2);
		jta1 = new JTextArea(5, 15);
		jta1.setEditable(false);
		JScrollPane jsp1 = new JScrollPane(jta1);
		b.add(jsp1);
		b.add(jp3);
		jta2 = new JTextArea(5, 15);
		jta2.setEditable(false);
		JScrollPane jsp2 = new JScrollPane(jta2);
		b.add(jsp2);
		add(b);
		addEventHandler();
	}

	private void addEventHandler() {
		chooseButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jfc.setMultiSelectionEnabled(true);
				int tempi = jfc.showOpenDialog(FileChooserPanel.this);
				if (tempi == JFileChooser.CANCEL_OPTION) {
					return;
				}
				File[] files = jfc.getSelectedFiles();
				fileField.setText(jfc.getSelectedFile().getAbsolutePath());
				// fileList.removeAll(fileList);
				fileList.clear();
				fileList.addAll(getAllFiles(files));
				jta1.setText("");
				for (File file : fileList) {
					jta1.append(file.getAbsolutePath() + "\n");
				}
			}

			private List<File> getAllFiles(File[] files) {
				List<File> fileList = new ArrayList<File>();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						fileList.addAll(getAllFiles(files[i].listFiles()));
					} else {
						fileList.add(files[i]);
					}
				}
				return fileList;
			}

		});
	}

	public List<File> getFileList() {
		return fileList;
	}

	public static void main(String[] args) {
		JFrame jf = new JFrame("");
		FileChooserPanel fcp = new FileChooserPanel();
		jf.add(fcp);
		jf.setBounds(200, 200, 450, 500);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
