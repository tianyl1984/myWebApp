package com.hzth.myapp.tools.fivechess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket ss;
	private Socket s;
	private ObjectInputStream ois=null;
	private ObjectOutputStream oos=null;
	
	public Server(int port) throws IOException{
		ss=new ServerSocket(port);
	}
	
	public void start(){
		try {
			s=ss.accept();
			oos=new ObjectOutputStream(s.getOutputStream());
			ois=new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(Location l) throws IOException{
		oos.writeObject(l);
		oos.flush();
	}
	
	public Location receive() throws IOException, ClassNotFoundException{
		Location l=null;
		l=(Location)ois.readObject();
		return l;
	}
	
	public void close(){
		if(oos!=null){
			try {
				oos.close();
			} catch (IOException e) {}
		}
		if(ois!=null){
			try {
				ois.close();
			} catch (IOException e) {}
		}
		if(s!=null){
			try {
				s.close();
			} catch (IOException e) {}
		}
	}
	
}
