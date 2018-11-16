package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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
    	//String groupN = (String)fin.readObject(); // ±×·ì¸í
          //String str = (String)in.readObject();
    	  
    	  ObjectOutputStream toCleint = new ObjectOutputStream(roomSocket.getOutputStream());
          String imageName = "ÀÍ½º»÷ÇÁ¶õ01";
          //toClient.writeObject(imageName);
          System.out.println("file name: " + imageName);
         
          InputStream in = roomSocket.getInputStream();
          FileOutputStream out = new FileOutputStream("C:\\Users\\À±ÇýÁÖ\\Downloads\\us1_test111.png"); 

           byte[] buffer = new byte[8192];
           int bytesRead=0;
           while ((bytesRead = in.read(buffer)) > 0) {
               out.write(buffer, 0, bytesRead);
           }
           out.flush();
           out.close();
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