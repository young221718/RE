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