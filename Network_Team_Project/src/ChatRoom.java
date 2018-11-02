import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import basic.Room;



public class ChatRoom extends Room{
	
	private static HashMap<Integer, ObjectOutputStream> broadcaster = new HashMap<Integer, ObjectOutputStream>();
	
	public ChatRoom(Socket socket) {
		super(socket);
	}
	public ChatRoom(Socket socket, int port,String user, String email) {
		super(socket);
		portNumber = port;
		Name = user;
		Email = email;
		
	}
	
	public void run(){
		System.out.println("Enter chat room");
		try {

			// Create character streams for the socket.
			fromClient = new ObjectInputStream(roomSocket.getInputStream());
			toClient = new ObjectOutputStream(roomSocket.getOutputStream());
			
//			for(int i=0;i<10;i++) {
//				int input = (Integer)fromClient.readObject();
//				String message = "from chatServer" + input;
//				toClient.writeObject(message);
//			}
			
			synchronized (broadcaster) {
				broadcaster.put(portNumber, toClient);
			}
			
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
