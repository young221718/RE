package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import basic.Room;

public class FileRoom extends Room {

   // FileOutputStream out;
   int cnt = 0;

   public FileRoom(Socket socket) {
      super(socket);
   }

   public FileRoom(Socket socket, int port) {
      super(socket);
      this.portNumber = port;
   }

   /*
    * <<<Explain The FileRoom>>
    * After client enter the file room, FileRoom receives the protocol from the client.
    * The client sends protocol '77' each time that it sends a file one by one.
    * Every time FileRoom receives the protocol '77', FileRoom reads and saves the file.
    * FileRoom reads the file during the protocol is 0.
    * When the sign goes to '99', it means that there are no more files to read.
    * The file that client send is stored in a folder created by the pin number of the room.
    * Each file is stored with the user's email and index to avoid duplication.
    * (non-Javadoc)
    * @see java.lang.Thread#run()
    */
   public void run() {
      int a = 0; // For counting file number
      System.out.println("Enter the FileRoom!");
      try {
         // Used to receive data from customer.
         BufferedInputStream up = new BufferedInputStream(roomSocket.getInputStream());
         DataInputStream fromClient = new DataInputStream(up);

         int sign = 0; // To send a sign to read the file.

         do {
            int fPro = up.read(); // Read the protocol that the client sends a picture

            if (fPro == 77) { // Client sends file
            	String Ext = (String)fromClient.readUTF();
            	System.out.println(Ext);
               int len = fromClient.readInt(); // file's length
               System.out.println(len + " received"); 
               
               // Create folder to save files according to group's port number
               File f = new File("C:\\RE\\" + portNumber); 
               
               // Create folder that does not exist.
               if (f.exists() == false)
                  f.mkdirs();

               // Save each file without duplication by user's email and index 
               FileOutputStream toFile = new FileOutputStream(f + "\\" + email + a + Ext);
               
               //Write the file using outFile
               BufferedOutputStream outFile = new BufferedOutputStream(toFile);
            
               // It takes the for loop as much as the file length, receives it from the client, and stores it in the file.
               for (int i = 0; i < len; i++) {
                  outFile.write(up.read());
               }
               
               // Read sign to check if any files are left
               sign = fromClient.read();
               
               a++; // file index
               
               outFile.flush(); //If there is anything left in the buffer
               outFile.close();
            }
         } while (sign != 99);

      } catch (IOException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            roomSocket.close();
         } catch (IOException e) {

         }
      }

   }
}