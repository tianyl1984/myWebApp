package com.hzth.myapp.tools.fivechess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket s;
	private ObjectInputStream ois=null;
	private ObjectOutputStream oos=null;
	
	public Client(String ip,int port) throws UnknownHostException, IOException{
		s=new Socket(ip,port);
		ois=new ObjectInputStream(s.getInputStream());
		oos=new ObjectOutputStream(s.getOutputStream());
	}
	
	public void send(Location l) throws IOException{
		oos.writeObject(l);
		oos.flush();
	}
	
	public Location receive() throws IOException, ClassNotFoundException{
		Location l=null;
		l=(Location)
		ois.readObject();
		return l;
	}
	
//	public void 
}
