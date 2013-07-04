package com.hzth.myapp.httpDebug;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main extends JFrame {

	private static final long serialVersionUID = -4564221383019888098L;

	private JPanel northPanel;
	private JTabbedPane centerPanel;

	private JTextField urlTF;
	private JButton getBtn, postBtn;

	public Main() {
		initComponent();
		this.setTitle("网络请求工具");
		this.setLocation(300, 100);
		this.setSize(600, 500);
		this.setMinimumSize(this.getSize());
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		northPanel.setLayout(new FlowLayout());
		northPanel.add(urlTF);
		northPanel.add(getBtn);
		northPanel.add(postBtn);
		newTab();
		newTab();
	}

	public void newTab() {
		// main panel
		JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sp.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

		// in
		JPanel panelIn = new JPanel();
		panelIn.setLayout(new BorderLayout());
		panelIn.setPreferredSize(new Dimension(600, 180));

		JLabel lblIn = new JLabel("Request:");

		panelIn.add(lblIn, BorderLayout.NORTH);

		// in textarea
		JTextArea in = new JTextArea();
		JScrollPane scrIn = new JScrollPane(in);
		scrIn.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		scrIn.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrIn.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		panelIn.add(scrIn, BorderLayout.CENTER);

		// out
		JPanel panelOut = new JPanel();
		panelOut.setLayout(new BorderLayout());
		JLabel lblOut = new JLabel("Response:");

		panelOut.add(lblOut, BorderLayout.NORTH);

		// out textarea
		JTextArea out = new JTextArea();
		JScrollPane scrOut = new JScrollPane(out);
		scrOut.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
		scrOut.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrOut.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		panelOut.add(scrOut, BorderLayout.CENTER);

		sp.add(panelIn);
		sp.add(panelOut);

		String title = "request:" + (centerPanel.getTabCount() + 1);
		centerPanel.addTab(title, sp);
	}

	private void initComponent() {
		northPanel = new JPanel();
		centerPanel = new JTabbedPane();
		urlTF = new JTextField(100);
		getBtn = new JButton("Get");
		postBtn = new JButton("Post");
	}

	private void startup() {
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main().startup();
			}
		});
	}
}
