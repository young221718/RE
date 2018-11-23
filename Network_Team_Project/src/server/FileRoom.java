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
    	
    	  
    	  
    	  ObjectOutputStream toCleint = new ObjectOutputStream(roomSocket.getOutputStream());
          String imageName = "익스샌프란01";
          //toClient.writeObject(imageName);
          System.out.println("file name: " + imageName);
         
          InputStream in = roomSocket.getInputStream();
         // FileOutputStream out = new FileOutputStream("C:\\work\\apple.jpg"); 
          int fPro = in.read();
          
          /*
           * group_id - DB에서 불러오기 // 지금은 그룹명으로 일단!!
           * fpro는 client가 파일을 보낼때 77, 보낼 파일이 없으면 88
           * fpro = 77이면 파일 받아서 저장
           * fpro = 88이면 socket 닫기
           */
        //String groupN = (String)fin.readObject(); // 그룹명
          //String str = (String)in.readObject();
          
          if(fPro == 77)
          {
        	  File f = new File("C:\\Users\\윤혜주\\Downloads\\folderNN\\groupNB"); // folderNN - 폴더, groupNB - group_id
        	  if(f.exists() == false)
        		  f.mkdirs();
        	  FileOutputStream out = new FileOutputStream(f+ "\\us1.png"); 

        	  byte[] buffer = new byte[8192];
        	  int bytesRead=0;
        	  while ((bytesRead = in.read(buffer)) > 0) {
        		  out.write(buffer, 0, bytesRead);
                  out.flush();
                  out.close();
           }
          } else if(fPro == 88) {
          }
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