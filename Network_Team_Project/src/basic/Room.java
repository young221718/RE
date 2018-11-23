package basic;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import server.Database;

public abstract class Room extends Thread{
   protected ServerSocket welcomeSocket;
   protected Socket roomSocket;
   protected ObjectInputStream fromClient;
   protected ObjectOutputStream toClient;
   protected RoomInformation roomInfor;
   protected Database db;
   protected int portNumber;
   protected int protocol;
   
   public Room(Socket socket){
      this.roomSocket = socket;
   }
}