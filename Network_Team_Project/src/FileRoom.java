import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.File;
import java.io.FileOutputStream;

import basic.Room;

public class FileRoom extends Room{

   public FileRoom(Socket socket) {
      super(socket);
   }
   public FileRoom(Socket socket, int port, String group_name)
   {
      super(socket);
      portNumber = port; 
      groupName = group_name;   
   }
   
   public void run() {
      System.out.println("Enter the FileRoom!");
      try {
         fromClient = new ObjectInputStream(roomSocket.getInputStream());
         toClient = new ObjectOutputStream(roomSocket.getOutputStream());
         
         groupName = (String)fromClient.readObject();
         File f = new File("C:\\Users\\", groupName);
         FileOutputStream output = new FileOutputStream(f);
         
         byte[] buffer = new byte[1024];
         int bytesRead = 0;
         while ((bytesRead = fromClient.read(buffer))>=0) {
            toClient.write(buffer, 0, bytesRead);
         }
         toClient.flush();
         System.out.println("file recieve success");
         
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