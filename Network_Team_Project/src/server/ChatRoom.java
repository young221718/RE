package server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;

import basic.Room;



public class ChatRoom extends Room{
	
	private static HashMap<Integer, HashSet<ObjectOutputStream>> broadcaster = new HashMap<Integer, HashSet<ObjectOutputStream>>();
	
	public ChatRoom(Socket socket) {
		super(socket);
	}
	
	public ChatRoom(Socket socket, int port) {
		super(socket);
		this.portNumber = port;
		synchronized (broadcaster) {
			if(!broadcaster.containsKey(portNumber)) {
				broadcaster.put(portNumber, new HashSet<ObjectOutputStream>());
			}
		}
	}
	
	
	public void run(){
		System.out.println("Enter chat room");
		try {

			// Create character streams for the socket.
			fromClient = new ObjectInputStream(roomSocket.getInputStream());
			toClient = new ObjectOutputStream(roomSocket.getOutputStream());
			
			System.out.println("Chat stream connect");
			System.out.println("port: " + portNumber);
			
			synchronized (broadcaster) {
				broadcaster.get(portNumber).add(toClient);
			}
			System.out.println("ChatRoom Log 1");
			while(true) {
				String input = (String)fromClient.readObject();
				if(input == null)
					return;
				
				for(ObjectOutputStream oos: broadcaster.get(portNumber)) {
					oos.writeObject(input);
					oos.flush();
				}
			
				
			}
		} catch (IOException e) {
			System.out.println(e);
			
		}catch (Exception e) {
			// TODO: handle exception
		} 
		finally {
			// This client is going down! Remove its name and its print
			// writer from the sets, and close its socket.
//			if (name != null) {
//				nameAndWriter.remove(name);
//			}
			try {
				roomSocket.close();
			} catch (IOException e) {
			}
		}
	
	}
}
