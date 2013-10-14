package com.hzth.myapp.tools.screenprinter;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.filechooser.FileNameExtensionFilter;

//截图工具
public class ScreenPrinter extends JWindow {
	private static final long serialVersionUID = -7350251203611166767L;

	private BufferedImage image;
	private Robot r;
	private int screenWidth;
	private int screenHeight;
	private int mouseStartX;
	private int mouseStartY;
	private int mouseEndX;
	private int mouseEndY;
	private int rectWidth = 6;
	private boolean isMousePressed;

	public ScreenPrinter() {
		super();
		Toolkit tk = Toolkit.getDefaultToolkit();
		screenWidth = new Double(tk.getScreenSize().getWidth()).intValue();
		screenHeight = new Double(tk.getScreenSize().getHeight()).intValue();
		Cursor cursor = tk.createCustomCursor(tk.createImage(ScreenPrinter.class.getResource("cursor.gif")), new Point(), "aaa");
		setCursor(cursor);
		init();
		MouseAdapter listener = new MouseEventImpl();
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}

	private void init() {
		setSize(screenWidth, screenHeight);
		// setSize(500,500);
		try {
			r = new Robot();
			image = r.createScreenCapture(new Rectangle(screenWidth, screenHeight));
		} catch (AWTException e2) {
			e2.printStackTrace();
		}
		setVisible(true);
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ScreenPrinter.this.dispose();
			}

		});

	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, screenWidth, screenHeight, this);
		if (isMousePressed) {
			// 填充矩形
			g.setColor(new Color(10, 100, 130, 10));
			g.fillRect(mouseStartX > mouseEndX ? mouseEndX : mouseStartX, mouseStartY > mouseEndY ? mouseEndY : mouseStartY, Math.abs(mouseEndX - mouseStartX), Math.abs(mouseEndY - mouseStartY));
			// 绘制矩形
			g.setColor(new Color(10, 100, 130));
			g.drawRect(mouseStartX > mouseEndX ? mouseEndX : mouseStartX, mouseStartY > mouseEndY ? mouseEndY : mouseStartY, Math.abs(mouseEndX - mouseStartX), Math.abs(mouseEndY - mouseStartY));
			// 绘制矩形边框各个脚上的小矩形
			int width = (mouseEndX - mouseStartX) / 2;
			int height = (mouseEndY - mouseStartY) / 2;
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++)
					if (i != 1 || j != 1)
						g.fillRect(mouseStartX + i * width - rectWidth / 2, mouseStartY + j * height - rectWidth / 2, rectWidth, rectWidth);
			// 绘制图小大小提示
			String sizeTip = Math.abs(mouseStartX - mouseEndX) + "x" + Math.abs(mouseStartY - mouseEndY);
			g.setColor(Color.BLACK);
			g.fillRect((mouseStartX > mouseEndX ? mouseEndX : mouseStartX), (mouseStartY > mouseEndY ? mouseEndY : mouseStartY) - 20, sizeTip.length() * 7, 18);
			g.setColor(Color.WHITE);
			g.drawString(sizeTip, (mouseStartX > mouseEndX ? mouseEndX : mouseStartX), (mouseStartY > mouseEndY ? mouseEndY : mouseStartY) - 7);
		}
	}

	private class MouseEventImpl extends MouseAdapter {

		@Override
		public void mouseDragged(MouseEvent e) {
			super.mouseDragged(e);
			mouseEndX = e.getXOnScreen();
			mouseEndY = e.getYOnScreen();
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			super.mouseReleased(e);
			mouseStartX = e.getXOnScreen();
			mouseStartY = e.getYOnScreen();
			isMousePressed = true;
		}

		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
			while (isMousePressed) {
				saveToFile();
			}
		}

		private void saveToFile() {
			BufferedImage subImage = image.getSubimage(mouseStartX > mouseEndX ? mouseEndX : mouseStartX, mouseStartY > mouseEndY ? mouseEndY : mouseStartY, Math.abs(mouseEndX - mouseStartX), Math.abs(mouseEndY - mouseStartY));
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("请保存图片");
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setFileFilter(new FileNameExtensionFilter("GIF文件 (*.gif)", ".gif"));
			chooser.setFileFilter(new FileNameExtensionFilter("JPEG文件 (*.jpg;*.jpeg)", ".jpg", ".jpeg"));
			chooser.setFileFilter(new FileNameExtensionFilter("BMP文件 (*.bmp)", ".bmp"));
			chooser.setFileFilter(new FileNameExtensionFilter("PNG文件 (*.png)", ".png"));
			ScreenPrinter.this.dispose();
			chooser.setSelectedFile(new File("Java截图" + new SimpleDateFormat("yyyyMMddHHmm").format(new Date())));
			int op = chooser.showSaveDialog(ScreenPrinter.this);
			if (op == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				String[] exname = ((FileNameExtensionFilter) chooser.getFileFilter()).getExtensions();
				String saveFileStr = file.getAbsolutePath();
				if (!saveFileStr.toLowerCase().endsWith(exname[0])) {
					saveFileStr += exname[0];
				}
				File saveFile = new File(saveFileStr);
				if (saveFile.exists()) {
					int tempi = JOptionPane.showConfirmDialog(ScreenPrinter.this, "文件已存在是否覆盖？", "请选择", JOptionPane.YES_NO_OPTION);
					if (tempi == JOptionPane.NO_OPTION) {
						return;
					}
				}
				try {
					boolean b = ImageIO.write(subImage, exname[0].substring(1, exname[0].length()), saveFile);
					if (!b) {
						JOptionPane.showMessageDialog(ScreenPrinter.this, "保存失败");
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(ScreenPrinter.this, "保存图片时发生错误:" + e1.getMessage());
				}
			}
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new ScreenPrinter();
	}
}
