package com.hzth.myapp.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

	public static void main(String[] args) throws Exception {
		byte[] msg = { 'a', 'b', 'c', 'd', 'e', 'f' };
		byte[] rmsg = new byte[100];
		InetAddress serverAddress = InetAddress.getLocalHost();// 得到本机地址，因为本机是服务器;
		DatagramSocket datagramSocket = new DatagramSocket();// 由系统分配一个端口;
		DatagramPacket outDataPacket = new DatagramPacket(msg, msg.length, serverAddress, 13);// 构造发送包;
		DatagramPacket inDataPacket;
		datagramSocket.send(outDataPacket);// 发送数据包
		inDataPacket = new DatagramPacket(rmsg, rmsg.length);// 构造接收包
		datagramSocket.receive(inDataPacket);// 接收数据
		String receivedMsg = new String(inDataPacket.getData(), 0, inDataPacket.getLength());
		System.out.println(receivedMsg);
		datagramSocket.close();
	}
}
