package server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import basic.Room;
import basic.RoomInformation;



public class ChatRoom extends Room{
	
	private static HashMap<Integer, ObjectOutputStream> broadcaster = new HashMap<Integer, ObjectOutputStream>();
	
	public ChatRoom(Socket socket) {
		super(socket);
	}
	public ChatRoom(Socket socket, RoomInformation rf) {
		super(socket);
		this.roomInfor = rf;
		this.portNumber = this.roomInfor.port;
		
	}
	
	public void run(){
		System.out.println("Enter chat room");
		try {

			// Create character streams for the socket.
			fromClient = new ObjectInputStream(roomSocket.getInputStream());
			toClient = new ObjectOutputStream(roomSocket.getOutputStream());
			
//			for(int i=0;i<10;i++) {
//				String tstr = (String)fromClient.readObject();
//				System.out.println(tstr);
//				String message = "RE: " +tstr;
//				toClient.writeObject(message);
//			}
			
			synchronized (broadcaster) {
				broadcaster.put(portNumber, toClient);
			}
			System.out.println("ChatRoom Log 1");
			while(true) {
				String input = (String)fromClient.readObject();
				if(input == null)
					return;
				
				for(ObjectOutputStream oos: broadcaster.values()) {
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
