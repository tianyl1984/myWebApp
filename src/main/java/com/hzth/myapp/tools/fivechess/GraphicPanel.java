package com.hzth.myapp.tools.fivechess;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GraphicPanel extends JPanel {
	private static final long serialVersionUID = -2539776081879497163L;

	private boolean isBlack = true;// 当前是否是黑方下子
	private char[][] allChess;// 用二维数组保存棋盘所有棋子状态

	private int lastX = -1;// 最后一次下子有效坐标
	private int lastY = -1;

	private static final int ROW_NUM = 20;// 行数和列数
	private static final int COL_NUM = 20;

	private static final int ROW_GAP = 30;// 行间距，列间距
	private static final int COL_GAP = 30;

	private static final int LEFT = 20;// 距离4边的距离
	private static final int RIGTH = 20;
	private static final int TOP = 20;
	private static final int DOWN = 20;

	private static final int CHESS_RADIO = 10;

	public GraphicPanel() {
		allChess = new char[ROW_NUM][COL_NUM];
		for (int i = 0; i < ROW_NUM; i++) {
			for (int j = 0; j < COL_NUM; j++) {
				allChess[i][j] = '*';// 开始全部设置成*表示未放子
			}
		}

		ChessBoard.label.setText((isBlack ? "黑" : "白") + "方请下子");

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() != MouseEvent.BUTTON1) {
					return;
				}
				int x = e.getX();
				int y = e.getY();

				if (x < LEFT / 2 || x > LEFT + (COL_NUM - 1) * COL_GAP + RIGTH / 2 || y < TOP / 2 || y > TOP + (ROW_NUM - 1) * ROW_GAP + DOWN / 2) {
					ChessBoard.label.setText("<html><font color='red'>无效位置" + (isBlack ? "黑" : "白") + "方请下子</font></html>");
					return;// 则不做任何处理
				}

				// 将鼠标点击点的x，y坐标换算成棋盘上的x，y坐标。
				int boardX = Math.round((float) (x - LEFT) / ROW_GAP);
				int boardY = Math.round((float) (y - TOP) / COL_GAP);

				// 如果该位置上有棋子
				if (allChess[boardY][boardX] != '*') {
					ChessBoard.label.setText("<html><font color='red'>该位置有棋子" + (isBlack ? "黑" : "白") + "方请下子.</font></html>");
					return;// 则不做任何处理
				}
				// 将棋盘上该位置设置为指定棋子
				allChess[boardY][boardX] = isBlack ? '@' : 'O';

				GraphicPanel.this.lastX = boardX;// 保存最后一次有效落子的坐标
				GraphicPanel.this.lastY = boardY;

				// 重画棋盘
				repaint();

				if (wasWin()) {
					String mesg = (isBlack ? "黑" : "白") + "方获胜！";
					ChessBoard.label.setText(mesg);
					JOptionPane.showMessageDialog(GraphicPanel.this, mesg);
					clear();
					return;
				}
				// 改变下棋方
				isBlack = !isBlack;
				ChessBoard.label.setText((isBlack ? "黑" : "白") + "方请下子...");
			}

		});

	}

	public boolean wasWin() {
		return wasWinAtV() || wasWinAtH() || wasWinAtLD() || wasWinAtRD();
	}

	private boolean wasWinAtV() {
		char ch = isBlack ? '@' : 'O';
		int i = lastX;
		while (i >= 0 && allChess[lastY][i] == ch) {// 向左找到第一个不是ch的字符
			i--;
		}
		int num = 0;
		i++;
		while (i < allChess.length && allChess[lastY][i] == ch) {
			num++;
			i++;
		}
		return num >= 5;

	}

	private boolean wasWinAtH() {
		char ch = isBlack ? '@' : 'O';
		int i = lastY;
		while (i >= 0 && allChess[i][lastX] == ch) {// 向左找到第一个不是ch的字符
			i--;
		}
		int num = 0;
		i++;
		while (i < allChess.length && allChess[i][lastX] == ch) {
			num++;
			i++;
		}
		return num >= 5;
	}

	private boolean wasWinAtLD() {
		char ch = isBlack ? '@' : 'O';
		int i = lastY;
		int j = lastX;
		while (i >= 0 && j < allChess.length && allChess[i][j] == ch) {// 向左找到第一个不是ch的字符
			i--;
			j++;
		}
		int num = 0;
		i++;
		j--;
		while (i < allChess.length && j >= 0 && allChess[i][j] == ch) {
			num++;
			i++;
			j--;
		}
		return num >= 5;
	}

	private boolean wasWinAtRD() {
		char ch = isBlack ? '@' : 'O';
		int i = lastY;
		int j = lastX;
		while (i >= 0 && j >= 0 && allChess[i][j] == ch) {// 向左找到第一个不是ch的字符
			i--;
			j--;
		}
		int num = 0;
		i++;
		j++;
		while (i < allChess.length && j < allChess.length && allChess[i][j] == ch) {
			num++;
			i++;
			j++;
		}
		return num >= 5;
	}

	/**
	 * 该方法在获胜后重新绘制棋盘，并初始化参数。
	 * 
	 */
	public void clear() {
		lastX = -1;
		lastY = -1;
		for (int i = 0; i < ROW_NUM; i++) {
			for (int j = 0; j < COL_NUM; j++) {
				allChess[i][j] = '*';// 初始时都赋为‘*’
			}
		}
		isBlack = true;
		// 重画棋盘
		repaint();
		ChessBoard.label.setText((isBlack ? "黑" : "白") + "方请下子");
	}

	/**
	 * 把当前棋盘状态保存到指定的文件中！
	 * 
	 * @param f
	 */
	public boolean saveToFile(File f) {
		// 1,建立一个FileOutputStream
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			for (int i = 0; i < allChess.length; i++) {
				for (int j = 0; j < allChess[i].length; j++) {
					if (i == lastY && j == lastX) {
						fos.write(isBlack ? 'W' : 'B');
					} else {
						fos.write(allChess[i][j]);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
				}
		}

		return true;
	}

	/**
	 * 从指定的文件中装载棋盘信息
	 * 
	 * @param f
	 */
	public boolean loadFromFile(File f) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
			for (int i = 0; i < allChess.length; i++) {
				for (int j = 0; j < allChess[i].length; j++) {
					char c = (char) fis.read();
					if (c != '*' && c != 'O' && c != '@' && c != 'W' && c != 'B') {
						return false;
					}
					if (c == 'B') {
						allChess[i][j] = '@';
						lastX = j;
						lastY = i;
						isBlack = false;
					} else if (c == 'W') {
						allChess[i][j] = 'O';
						lastX = j;
						lastY = i;
						isBlack = true;
					} else {
						allChess[i][j] = c;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
				}
		}

		this.repaint();
		return true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Stroke s = new BasicStroke(2.0f);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(s);

		for (int i = 0; i < ROW_NUM; i++) {
			g2.drawLine(LEFT, i * ROW_GAP + TOP, (COL_NUM - 1) * COL_GAP + LEFT, i * ROW_GAP + TOP);
		}
		for (int j = 0; j < COL_NUM; j++) {
			g2.drawLine(LEFT + COL_GAP * j, TOP, j * COL_GAP + LEFT, (COL_NUM - 1) * ROW_GAP + LEFT);
		}

		for (int i = 0; i < ROW_NUM; i++) {
			for (int j = 0; j < COL_NUM; j++) {
				if (allChess[i][j] != '*') {
					Shape shape = new Ellipse2D.Double(j * COL_GAP + LEFT - CHESS_RADIO, i * ROW_GAP + TOP - CHESS_RADIO, CHESS_RADIO * 2, CHESS_RADIO * 2);
					g2.setColor(Color.BLACK);
					g2.draw(shape);
					if (allChess[i][j] == 'O') {
						g2.setColor(Color.WHITE);
					} else {
						g2.setColor(Color.BLACK);
					}
					g2.fill(shape);
				}
			}
		}

		if (lastX != -1) {// 在最后一次有效落点位置画一个红‘X’，用以标识。
			g2.setColor(Color.RED);
			g2.drawLine(lastX * ROW_GAP + LEFT - 5, lastY * COL_GAP + TOP - 5, lastX * ROW_GAP + LEFT + 5, lastY * COL_GAP + TOP + 5);
			g2.drawLine(lastX * ROW_GAP + LEFT + 5, lastY * COL_GAP + TOP - 5, lastX * ROW_GAP + LEFT - 5, lastY * COL_GAP + TOP + 5);
		}
	}

}
