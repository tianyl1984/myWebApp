package com.hzth.myapp.tools.timer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

public class TimerMain {
	private JFrame jf;
	private JLabel label1, label2, label3, label4, label5, currentTime;
	private JTextField fileField, hour, minute, second;
	private JButton browse, addTask, deleteTask;
	private JTable taskListTable;
	private TableModel tableModel;
	private List<MyTask> taskList;

	public TimerMain() {
		jf = new JFrame("定时启动器");
		label1 = new JLabel("现在时间:");
		currentTime = new JLabel("正在获取本地时间...");
		currentTime.setFont(new Font("宋体", Font.BOLD, 20));
		new Thread() {

			@Override
			public void run() {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				while (true) {
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					currentTime.setText(sdf.format(new Date()));
				}
			}
		}.start();
		label2 = new JLabel("请选择程序或文件:");
		fileField = new JTextField(15);
		browse = new JButton("浏览...");
		label3 = new JLabel("所选文件若不是可运行程序，将会使用系统关联程序打开！");
		label3.setForeground(Color.RED);
		label4 = new JLabel("请设置启动时间：");
		hour = new JTextField(2);
		minute = new JTextField(2);
		second = new JTextField(2);
		addTask = new JButton("增加");
		deleteTask = new JButton("删除");
		label5 = new JLabel("任务列表");
		taskList = new ArrayList<MyTask>();
		taskListTable = new JTable(10, 2);
		tableModel = taskListTable.getModel();
		tableModel.setValueAt("时间", 0, 0);
		tableModel.setValueAt("任务", 0, 1);

		init();
		addEventHandler();
	}

	/**
	 * 界面初始化
	 */
	private void init() {
		jf.setLayout(new BorderLayout());

		JPanel north = new JPanel();
		north.setLayout(new BorderLayout());
		north.add(label1, BorderLayout.NORTH);
		JPanel north1 = new JPanel();
		north1.add(currentTime);
		north.add(north1, BorderLayout.CENTER);
		jf.add(north, BorderLayout.NORTH);

		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		JPanel center1 = new JPanel();
		center1.add(label2);
		center1.add(fileField);
		center1.add(browse);
		center.add(center1, BorderLayout.NORTH);
		JPanel center3 = new JPanel();
		center3.add(label3);
		center.add(center3, BorderLayout.CENTER);
		JPanel center2 = new JPanel();
		center2.add(label4);
		center2.add(hour);
		center2.add(new JLabel("时"));
		center2.add(minute);
		center2.add(new JLabel("分"));
		center2.add(second);
		center2.add(new JLabel("秒"));
		center.add(center2, BorderLayout.SOUTH);
		jf.add(center, BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setLayout(new BorderLayout());
		JPanel south1 = new JPanel();
		south1.add(addTask);
		south1.add(deleteTask);
		south.add(south1, BorderLayout.NORTH);
		south.add(label5, BorderLayout.CENTER);
		south.add(taskListTable, BorderLayout.SOUTH);
		jf.add(south, BorderLayout.SOUTH);

		jf.setBounds(250, 250, 400, 390);
		jf.setResizable(false);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * 事件
	 */
	private void addEventHandler() {

		/**
		 * 浏览按钮事件
		 */
		browse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				if (jfc.showOpenDialog(jf) == JFileChooser.CANCEL_OPTION) {
					return;
				}
				File file = jfc.getSelectedFile();
				if (!file.exists()) {
					JOptionPane.showMessageDialog(jf, "文件不存在，请重新选择");
					return;
				}
				fileField.setText(file.getAbsolutePath());
			}

		});

		/**
		 * 添加任务按钮
		 */
		addTask.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				File file = new File(fileField.getText().trim());
				if (!file.exists()) {
					JOptionPane.showMessageDialog(jf, "文件不存在，请重新选择");
					return;
				}
				int iHour;
				int iMinute;
				int iSecond;
				try {
					iHour = Integer.parseInt(hour.getText().trim());
					iMinute = Integer.parseInt(minute.getText().trim());
					iSecond = Integer.parseInt(second.getText().trim());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(jf, "时间只能是数字");
					return;
				}
				if (iHour < 0 || iHour > 23) {
					JOptionPane.showMessageDialog(jf, "小时只能在0-23之间");
					return;
				}
				if (iMinute < 0 || iMinute > 59) {
					JOptionPane.showMessageDialog(jf, "分钟只能在0-59之间");
					return;
				}
				if (iSecond < 0 || iSecond > 59) {
					JOptionPane.showMessageDialog(jf, "秒只能在0-59之间");
					return;
				}
				Calendar time = Calendar.getInstance();
				time.set(Calendar.HOUR_OF_DAY, iHour);
				time.set(Calendar.MINUTE, iMinute);
				time.set(Calendar.SECOND, iSecond);
				Timer timer = new Timer();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				MyTask taskTemp = new MyTask(file);
				timer.schedule(taskTemp, time.getTime());
				taskList.add(taskTemp);
				aaa: for (int i = 1; i < tableModel.getRowCount(); i++) {
					for (int j = 0; j < tableModel.getColumnCount(); j++) {
						if (tableModel.getValueAt(i, j) == null) {
							tableModel.setValueAt(sdf.format(time.getTime()), i, j);
							tableModel.setValueAt(file.getAbsoluteFile(), i, j + 1);
							break aaa;
						}
					}
				}
			}

		});

		/**
		 * 删除按钮
		 */
		deleteTask.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = taskListTable.getSelectedRows();
				for (int i = 0; i < selectedRows.length; i++) {
					if (selectedRows[i] == 0) {
						return;
					}
					tableModel.setValueAt(null, selectedRows[i], 0);
					tableModel.setValueAt(null, selectedRows[i], 1);
					taskList.get(selectedRows[i] - 1).cancel();
					taskList.remove(selectedRows[i] - 1);
				}
			}

		});
	}

	private class MyTask extends TimerTask {
		private File file;;

		public MyTask(File file) {
			this.file = file;
		}

		@Override
		public void run() {
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.open(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.cancel();
		}

	}

	public static void main(String[] args) {
		new TimerMain();
	}
}
