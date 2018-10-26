import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import basic.Room;
import java.util.HashMap;


<natures>
<nature>org.eclipse.jdt.core.javanature</nature>
</natures>
public class ChatRoom extends Room{
	
	private static HashMap
	
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
			
			
			
				
			}
		} catch (IOException e) {
			System.out.println(e);
			// Notice to every client
			// a client exit chat room
			for (PrintWriter writer : nameAndWriter.values()) {
				writer.println("NOTICE ***[" + name + "] exit chat room.***");
			}
		} finally {
			// This client is going down! Remove its name and its print
			// writer from the sets, and close its socket.
			if (name != null) {
				nameAndWriter.remove(name);
			}
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
	
	}
}
