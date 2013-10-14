package com.hzth.myapp.tools.fivechess;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;

public class ChessBoard {
	private JFrame frame;
	public static JLabel label;
	private GraphicPanel gp;
	private JMenuBar menuBar;
	private JMenu game, net;
	private JMenuItem saveGame, loadGame, exit;
	private JRadioButtonMenuItem server, client, single;

	public ChessBoard() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame = new JFrame("五子棋");
		label = new JLabel();
		gp = new GraphicPanel();
		menuBar = new JMenuBar();
		game = new JMenu("游戏");
		net = new JMenu("网络");
		saveGame = new JMenuItem("保存游戏...");
		loadGame = new JMenuItem("加载游戏...");
		exit = new JMenuItem("退出");
		server = new JRadioButtonMenuItem("作为服务器");
		client = new JRadioButtonMenuItem("作为客户端");
		single = new JRadioButtonMenuItem("单机模式", true);
		ButtonGroup bg = new ButtonGroup();
		bg.add(server);
		bg.add(client);
		bg.add(single);
		init();
		addEventHandler();
	}

	private void addEventHandler() {
		saveGame.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				int temp = jfc.showSaveDialog(frame);
				if (temp == JFileChooser.APPROVE_OPTION) {
					gp.saveToFile(jfc.getSelectedFile());
				}
			}

		});

		loadGame.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				int temp = jfc.showOpenDialog(frame);
				if (temp == JFileChooser.APPROVE_OPTION) {
					gp.loadFromFile(jfc.getSelectedFile());
				}
			}

		});

		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int temp = JOptionPane.showConfirmDialog(frame, "你确定退出吗？", "提示", JOptionPane.YES_NO_OPTION);
				if (temp == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}

		});

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				exit.doClick();
			}

		});

		single.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				gp.clear();
			}

		});

		server.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new ChooserDialog(false);
			}

		});

		client.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new ChooserDialog(true);
			}

		});
	}

	private void init() {
		game.add(saveGame);
		game.add(loadGame);
		game.addSeparator();
		game.add(exit);
		menuBar.add(game);
		net.add(server);
		net.add(client);
		net.addSeparator();
		net.add(single);
		menuBar.add(net);
		frame.setJMenuBar(menuBar);
		frame.setLayout(new BorderLayout());
		frame.add(gp, BorderLayout.CENTER);
		frame.add(label, BorderLayout.SOUTH);

		frame.setBounds(50, 10, 650, 670);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	public static void main(String[] args) {
		new ChessBoard();
	}

}
