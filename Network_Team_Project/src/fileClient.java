import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class fileClient {
   
   public static void main(String[] args) {
      try {
         System.out.println("fileClient Start");
         InetAddress host = InetAddress.getLocalHost();
         Socket fileClientSocket = new Socket(host, 1234);
         
         ObjectInputStream in = new ObjectInputStream(fileClientSocket.getInputStream());
         ObjectOutputStream out = new ObjectOutputStream(fileClientSocket.getOutputStream());
         
         // ÆÄÀÏ·ë µé¾î°¡±â
         //Integer pro = new Integer(222);
         //int pro = (Integer) in.readObject();
         int pro = in.readInt();
        // out.writeObject(pro);
         out.writeInt(pro);
         out.flush();
         
         int roomNum = (Integer)in.readObject();
         out.writeObject(roomNum);
         out.flush();
         System.out.println("File Room; "+ roomNum);
         
         Transmission tr = new Transmission(roomNum);
         tr.start();
         //Á¾·áÇÏ±â
         out.writeInt(889);
         // ½ºÆ®¸²°ú ¼ÒÄÏ ´Ý±â
         out.close();
         in.close();
         fileClientSocket.close();
         
      } catch (Exception e) {
         System.out.println("File Exception error");
      }
      System.out.println("fileClient end");
   }

   private static class Transmission extends Thread {
	  // Socket fileSocket = new Socket(host, roomNum);
       //ObjectOutputStream fout = new ObjectOutputStream(fileSocket.getOutputStream());
       //ObjectInputStream fin = new ObjectInputStream(fileSocket.getInputStream());
       
	   Socket fileSocket;
	   ObjectInputStream fin;
	   ObjectOutputStream fout;
	   
       public Transmission(int rm) {
    	   try {
				InetAddress host = InetAddress.getLocalHost(); // ·ÎÄÃ ÁÖ¼Ò ¹Þ¾Æ¿À±â
				this.fileSocket = new Socket(host, rm);
				this.fout = new ObjectOutputStream(fileSocket.getOutputStream());
				this.fin = new ObjectInputStream(fileSocket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
       }
       public void run() {
    	   try {
    	// ÆÄÀÏ·ë test
           int i=0;
           fout.writeObject((Integer)i);
           fout.flush();
           
           //String groupN = (String)fin.readObject(); // ±×·ì¸í
           //String str = (String)in.readObject();
           String imageName = "ÀÍ½º»÷ÇÁ¶õ01";
           fout.writeObject((String)imageName);
           System.out.println("file name: " + imageName);
           //File f = new File("C:\\Users\\À±ÇýÁÖ\\Downloads\\2018-2ÇÐ±â\\ÇÁÀ×", imageName + ".jpg");
           //FileInputStream fileIn = new FileInputStream(f);
           FileOutputStream fos = new FileOutputStream("C:\\Users\\À±ÇýÁÖ\\Downloads\\test.png");
          //FileOutputStream fos = new FileOutputStream(f);
          // ObjectInputStream ois = new ObjectInputStream(fileSocket.getInputStream());
           byte[] buf = new byte[8192];
          int bytesRead =0;
          // while(fin.read(buf, 0, 1024) ) {
           while ((bytesRead = fin.read(buf)) >0) {
        	  //fileSize += n;
              fos.write(buf, 0, bytesRead);
             // cnt++;
           }
           fos.flush();
           fos.close();
             // this.fout.close();               
    	   } catch (Exception e) {
    		   e.printStackTrace();
    	   } finally {
    		   try {
    			   fileSocket.close();
    			   this.fin.close();
    			   this.fout.close();
    		   } catch (Exception e) {
    			   e.printStackTrace();
    		   }
    	   }
       }
   }
   
}