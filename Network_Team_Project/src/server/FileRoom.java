package server;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
<<<<<<< HEAD:Network_Team_Project/src/FileRoom.java
    	  toClient = new ObjectOutputStream(roomSocket.getOutputStream());
    	  
    	  String imageName = "익스샌프란01"; // 받아올 파일 이름
          toClient.writeObject(imageName);
          System.out.println("file name: " + imageName);
         
          InputStream in = roomSocket.getInputStream();
          FileOutputStream out = new FileOutputStream("C:\\Users\\윤혜주\\Downloads\\us1_test11.png"); // 파일 새로 저장할 때 이름 

           byte[] buffer = new byte[8192];
           int bytesRead=0;
           while ((bytesRead = in.read(buffer)) > 0) { // client로부터 전송된 파일 읽어서 쓰기
               out.write(buffer, 0, bytesRead);
           }
           out.flush();
           out.close();
=======
         fromClient = new ObjectInputStream(roomSocket.getInputStream());
         toClient = new ObjectOutputStream(roomSocket.getOutputStream());
         
         groupName = (String)fromClient.readObject();
         //File f = new File("C:\\Users\\", groupName);
 
         String str = (String)fromClient.readObject();
      System.out.println("sending file name: " + str);
      File f = new File("C:\\Users\\윤혜주\\Downloads\\", str + ".jpg");
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
>>>>>>> e8dcdb5ee410bc70301bf0a1eda599e40656785d:Network_Team_Project/src/server/FileRoom.java
         
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