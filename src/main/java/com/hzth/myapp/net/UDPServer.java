package com.hzth.myapp.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class UDPServer {

	public static void main(String[] args) throws Exception {
		byte[] msg = new byte[10];
		byte[] time;
		DatagramSocket datagramSocket = new DatagramSocket(13);//分配端口13;
		DatagramPacket inDataPacket;
		DatagramPacket outDataPacket;
		InetAddress clientAddress;
		int clientPort;
		System.out.println("UDP server active on port 13...");
		while (true) {
			inDataPacket = new DatagramPacket(msg, msg.length);//构造接收包
			datagramSocket.receive(inDataPacket);//接受数据
			System.out.println(new String(inDataPacket.getData(), 0, inDataPacket.getLength()));
			clientAddress = inDataPacket.getAddress();//得到数据来源地址
			clientPort = inDataPacket.getPort();//得到数据来源端口
			time = getTime();
			outDataPacket = new DatagramPacket(time, time.length, clientAddress, clientPort);//构造发送包
			datagramSocket.send(outDataPacket);//发送数据包
		}
	}

	private static byte[] getTime() {
		Date d = new Date();
		return d.toString().getBytes();
	}
}
