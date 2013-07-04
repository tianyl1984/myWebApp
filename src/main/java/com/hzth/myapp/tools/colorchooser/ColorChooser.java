package com.hzth.myapp.tools.colorchooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorChooser {
	private JFrame jf;
	private JLabel r, g, b;
	private JSpinner r1, g1, b1;
	private JTextArea jta;
	private JSlider js1, js2, js3;
	private JTextField jtf;
	private JButton button;
	private Color c;

	public ColorChooser() {
		jf = new JFrame("颜色选择器");
		r = new JLabel("R:");
		r1 = new JSpinner(new SpinnerNumberModel(127, 0, 255, 1));
		g = new JLabel("G:");
		g1 = new JSpinner(new SpinnerNumberModel(127, 0, 255, 1));
		b = new JLabel("B:");
		b1 = new JSpinner(new SpinnerNumberModel(127, 0, 255, 1));
		jta = new JTextArea(5, 10);
		js1 = new JSlider(0, 255);
		js2 = new JSlider(0, 255);
		js3 = new JSlider(0, 255);
		jtf = new JTextField(5);
		button = new JButton(new ImageIcon(ColorChooser.class.getResource("1.png")));
		button.setToolTipText("点击此按钮在屏幕上选取颜色");
		button.setMargin(new Insets(0, 0, 0, 0));
		init();
	}

	public void init() {
		r1.setValue(127);
		g1.setValue(127);
		b1.setValue(127);
		jta.setBackground(new Color(127, 127, 127));
		jtf.setText("#7f7f7f");
		jtf.setEditable(false);
		JPanel jp = new JPanel();
		jp.add(r);
		jp.add(js1);
		jp.add(r1);
		jp.add(g);
		jp.add(js2);
		jp.add(g1);
		jp.add(b);
		jp.add(js3);
		jp.add(b1);
		jf.add(jp, BorderLayout.CENTER);
		jta.setEditable(false);
		JPanel jp2 = new JPanel();
		jp2.add(button);
		jp2.add(jtf);
		jp2.add(jta);
		jf.add(jp2, BorderLayout.SOUTH);
		addEventhandler();
	}

	public void showMe() {
		jf.setBounds(300, 350, 300, 200);
		jf.setVisible(true);
		// jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void addEventhandler() {
		js1.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				r1.setValue(js1.getValue());
				c = new Color(js1.getValue(), js2.getValue(), js3.getValue());
				jta.setBackground(c);
				jtf.setText("#" + Integer.toHexString(c.getRed()) + Integer.toHexString(c.getGreen()) + Integer.toHexString(c.getBlue()));
			}

		});
		js2.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				g1.setValue(js2.getValue());
				c = new Color(js1.getValue(), js2.getValue(), js3.getValue());
				jta.setBackground(c);
				jtf.setText("#" + Integer.toHexString(c.getRed()) + Integer.toHexString(c.getGreen()) + Integer.toHexString(c.getBlue()));
			}

		});
		js3.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				b1.setValue(js3.getValue());
				c = new Color(js1.getValue(), js2.getValue(), js3.getValue());
				jta.setBackground(c);
				jtf.setText("#" + Integer.toHexString(c.getRed()) + Integer.toHexString(c.getGreen()) + Integer.toHexString(c.getBlue()));
			}

		});
		r1.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				js1.setValue(Integer.parseInt(r1.getValue().toString()));
				c = new Color(js1.getValue(), js2.getValue(), js3.getValue());
				jta.setBackground(c);
				jtf.setText("#" + Integer.toHexString(c.getRed()) + Integer.toHexString(c.getGreen()) + Integer.toHexString(c.getBlue()));
			}

		});
		g1.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				js2.setValue(Integer.parseInt(g1.getValue().toString()));
				c = new Color(js1.getValue(), js2.getValue(), js3.getValue());
				jta.setBackground(c);
				jtf.setText("#" + Integer.toHexString(c.getRed()) + Integer.toHexString(c.getGreen()) + Integer.toHexString(c.getBlue()));
			}

		});
		b1.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				js3.setValue(Integer.parseInt(b1.getValue().toString()));
				c = new Color(js1.getValue(), js2.getValue(), js3.getValue());
				jta.setBackground(c);
				jtf.setText("#" + Integer.toHexString(c.getRed()) + Integer.toHexString(c.getGreen()) + Integer.toHexString(c.getBlue()));
			}

		});
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				c = new JWindowTool(js1, js2, js3).getColor();
				// c=JWindowTool.showJWindow();
				// System.out.println(c==null);
				// r1.setValue(c.getRed());
				// g1.setValue(c.getGreen());
				// b1.setValue(c.getBlue());
			}

		});
	}

	public static void main(String[] args) {
		new ColorChooser().showMe();
	}
}
