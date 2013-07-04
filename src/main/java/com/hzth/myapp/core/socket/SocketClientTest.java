package com.hzth.myapp.core.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClientTest {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket s = new Socket("192.168.1.122", 9999);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter pw = new PrintWriter(s.getOutputStream());
			pw.println("aaa");
			pw.flush();
			//			System.exit(0);
			String temp = br.readLine();
			System.out.println(temp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
