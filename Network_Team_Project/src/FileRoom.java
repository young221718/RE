import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
        // toClient = new ObjectOutputStream(roomSocket.getOutputStream());
        
      //String str = fromClient.readLine();
         String str = "test1";
      System.out.println("sending file name: " + str + "\n");
     
      BufferedOutputStream out = new BufferedOutputStream( roomSocket.getOutputStream() );
      FileInputStream fileIn = new FileInputStream( "C:\\Users\\À±ÇýÁÖ\\Downloads\\2018-2ÇÐ±â\\ÇÁÀ×\\us1.png");
      byte[] buffer = new byte[8192];
      int bytesRead =0;
      while ((bytesRead = fileIn.read(buffer)) > 0) {
          out.write(buffer, 0, bytesRead);
      }
      out.flush();
      out.close();
      fileIn.close();

       
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