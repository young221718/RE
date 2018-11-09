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
         
         // 파일룸 들어가기
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
         //종료하기
         out.writeInt(889);
         // 스트림과 소켓 닫기
         out.close();
         in.close();
         
         // 파일룸의 소켓, in, out 연결하기
         /*Socket fileSocket = new Socket(host, roomNum);
         ObjectOutputStream fout = new ObjectOutputStream(fileSocket.getOutputStream());
         ObjectInputStream fin = new ObjectInputStream(fileSocket.getInputStream());
         
         // 파일룸 test
         int i=0;
         fout.writeObject((Integer)i);
         fout.flush();
         
         //String groupN = (String)fin.readObject(); // 그룹명
         //String str = (String)in.readObject();
         String imageName = "익스샌프란01";
         fout.writeObject((String)imageName);
         System.out.println("file name: " + imageName);
         File f = new File("C:\\Users\\윤혜주\\Downloads\\2018-2학기\\프잉", imageName + ".jpg");
         FileInputStream fileIn = new FileInputStream(f);
         
         byte[] buf = new byte[1024];
         while(fin.read(buf)>0) {
            fout.write(buf);
            fout.flush();
         }
            fout.close();               
         
         out.close();
         in.close();
         fileSocket.close();*/
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
				InetAddress host = InetAddress.getLocalHost(); // 로컬 주소 받아오기
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
    	// 파일룸 test
           int i=0;
           fout.writeObject((Integer)i);
           fout.flush();
           
           //String groupN = (String)fin.readObject(); // 그룹명
           //String str = (String)in.readObject();
           String imageName = "익스샌프란01";
           fout.writeObject((String)imageName);
           System.out.println("file name: " + imageName);
           File f = new File("C:\\Users\\윤혜주\\Downloads\\2018-2학기\\프잉", imageName + ".jpg");
           //FileInputStream fileIn = new FileInputStream(f);
          FileOutputStream fos = new FileOutputStream(f);
          // ObjectInputStream ois = new ObjectInputStream(fileSocket.getInputStream());
           byte[] buf = new byte[1024];
           int n =0;
    	   int cnt = 0;
    	   long fileSize = 0;
          // while(fin.read(buf, 0, 1024) ) {
           while ((n = fin.read(buf)) != -1) {
        	  fileSize += n;
              fout.write(buf);
              fout.flush();
              cnt++;
           }
              this.fout.close();               
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