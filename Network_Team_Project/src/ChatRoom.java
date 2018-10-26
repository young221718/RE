import java.net.Socket;

import basic.Room;

public class ChatRoom extends Room{
	
	public ChatRoom(Socket socket) {
		super(socket);
	}
	public ChatRoom(Socket socket, int port) {
		super(socket);
		portNumber = port;
	}
	
	public void run(){
		System.out.println("Enter chat room");
	}
}
