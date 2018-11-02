package basic;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Room extends Thread{
   protected ServerSocket welcomeSocket;
   protected Socket roomSocket;
   protected ObjectInputStream fromClient;
   protected ObjectOutputStream toClient;
   protected RoomInformation roomInfor;
   protected int portNumber;
   protected int protocol;
   protected String Email;
   protected String Name;
   protected String groupName;
   
   public Room(Socket socket){
      this.roomSocket = socket;
   }
}