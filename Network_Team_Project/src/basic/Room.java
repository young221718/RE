package basic;
import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Room extends Thread{
	protected ServerSocket welcomeSocket;
	protected Socket roomSocket;
	protected DataInputStream fromClient;
	protected ObjectOutputStream toClient;
	protected int portNumber;
	protected int protocol;
	
	public Room(Socket socket){
		this.roomSocket = socket;
	}
	
	
//	public abstract boolean EnterRoom(int portNum);
//	
//	public abstract boolean MakeRoom(int portNum);
//	
	
}
