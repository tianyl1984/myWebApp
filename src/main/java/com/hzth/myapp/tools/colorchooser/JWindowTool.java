package com.hzth.myapp.tools.colorchooser;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JSlider;
import javax.swing.JWindow;

public class JWindowTool extends JWindow{
	private static final long serialVersionUID = -2134193743189428739L;
	private Image image;
	private Color c;
	private Robot r;
	private int screenWidth;
	private int screenHeight;
	private JSlider js1,js2,js3;
	
	public JWindowTool(){
		super();
		Toolkit tk=Toolkit.getDefaultToolkit();
		screenWidth=new Double(tk.getScreenSize().getWidth()).intValue();
		screenHeight=new Double(tk.getScreenSize().getHeight()).intValue();
		Cursor cursor=tk.createCustomCursor(tk.createImage(JWindowTool.class.getResource("1.png")), new Point(), "aaa");
		setCursor(cursor);
		init();
	}
	
	public JWindowTool(JSlider js1,JSlider js2,JSlider js3){
		this();
		this.js1=js1;
		this.js2=js2;
		this.js3=js3;
	}
	
	public JWindowTool(Color c){
		this();
		this.c=c;
	}
	
	private void init(){
		setSize(screenWidth,screenHeight);
//		setSize(500,500);
		try {
			r=new Robot();
			image=r.createScreenCapture(new Rectangle(screenWidth,screenHeight));
		} catch (AWTException e2) {
			e2.printStackTrace();
		}
		setVisible(true);
		addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Robot r=new Robot();
					JWindowTool.this.c=r.getPixelColor(e.getX(), e.getY());
					js1.setValue(JWindowTool.this.c.getRed());
					js2.setValue(JWindowTool.this.c.getGreen());
					js3.setValue(JWindowTool.this.c.getBlue());
				} catch (AWTException e1) {
					e1.printStackTrace();
				}finally{
					JWindowTool.this.dispose();
				}
			}

		});
		
	}
	
	public Color getColor(){
		return c;
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, screenWidth, screenHeight, this);
	}

//	public static void main(String[] args) {
//		new JWindowTool().getColor();
//	}
	
	public static Color showJWindow(){
		JWindowTool jwt=new JWindowTool();
		return jwt.c;
	}

}
