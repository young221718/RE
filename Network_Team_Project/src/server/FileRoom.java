package server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.File;
import java.io.FileInputStream;

import basic.Room;
import basic.RoomInformation;

public class FileRoom extends Room{

   public FileRoom(Socket socket) {
      super(socket);
   }
   public FileRoom(Socket socket, RoomInformation rf)
   {
      super(socket);
      this.roomInfor = rf;
   }
   
   public void run() {
      System.out.println("Enter the FileRoom!");
      try {
         fromClient = new ObjectInputStream(roomSocket.getInputStream());
         toClient = new ObjectOutputStream(roomSocket.getOutputStream());
         
         groupName = (String)fromClient.readObject();
         //File f = new File("C:\\Users\\", groupName);
 
         String str = (String)fromClient.readObject();
      System.out.println("sending file name: " + str);
      File f = new File("C:\\Users\\À±ÇýÁÖ\\Downloads\\", str + ".jpg");
      FileInputStream fis = new FileInputStream(f);
      
      byte[] buf = new byte[1024];
      while(fromClient.read(buf)>0)
      {
         toClient.writeObject(buf);
         toClient.flush();
         
      }
         
       /*  ObjectOutputStream fout = new ObjectOutputStream();
         
         byte[] buffer = new byte[1024];
         int bytesRead = 0;
         while ((bytesRead = fromClient.read(buffer))>=0) {
            toClient.write(buffer, 0, bytesRead);
            toClient.flush();
         }*/
         System.out.println(str + " file recieve success");
         
      } catch (IOException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
      finally {
         try {
            roomSocket.close();
         } catch (IOException e) {
            
         }
      }
      
      
   }
}