package com.hzth.myapp.tools.batchRename;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame {
	private JFrame frame;
	private JLabel label;
	private FileChooserPanel fcp;
	private RenamePanel rp;

	public MainFrame() {
		frame = new JFrame("批量重命名工具");
		label = new JLabel("<html><Font size='6' color='blue'>批量重命名工具</font></html>");
		fcp = new FileChooserPanel();
		rp = new RenamePanel(fcp.getFileList());
		frame.setLayout(new BorderLayout());
		JPanel jp = new JPanel();
		jp.add(label);
		frame.add(jp, BorderLayout.NORTH);
		frame.add(fcp, BorderLayout.CENTER);
		frame.add(rp, BorderLayout.SOUTH);
		frame.pack();
		frame.setLocation(280, 250);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JFrame getJFrame() {
		return frame;
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}
