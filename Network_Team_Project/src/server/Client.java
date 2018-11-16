<<<<<<< HEAD:Network_Team_Project/src/Client.java
import java.io.BufferedOutputStream;
=======
package server;
>>>>>>> e8dcdb5ee410bc70301bf0a1eda599e40656785d:Network_Team_Project/src/server/Client.java
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import basic.RoomInformation;

public class Client {

   public static void main(String[] args) {
      try {
         System.out.println("Client Start...");
         InetAddress host = InetAddress.getLocalHost();
         Socket clientSocket = new Socket(host, 1234);

         ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
         ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

         // 채팅&파일 만들기 =======================================================
         out.writeInt(111);// 채팅방을 만들기 위한 프로토콜 111
         out.flush();
         // 방 정보 보내기
         RoomInformation roomInfor = new RoomInformation("group1", -1, 3, "Who are you?", "I don't know", null);
         out.writeObject(roomInfor);
         out.flush();
         // 방의 핀번호 받아오기
         int roomNum = (Integer) in.readObject();
         System.out.println("Room: " + roomNum);
         // ================================================================

         // 채팅&파일 방 들어가기 =======================================================
         out.writeInt(222);// 채팅방에 들어가기 위한 프로토콜 222
         out.flush();
         // 입력한 핀번호 혹은 서버에게로 받은 핀번호를 서버한테 보낸다.
         out.writeObject(roomNum);
         out.flush();
         // =================================================================
         
         // 채팅을 시작함========================================================
         Chatting ch = new Chatting(roomNum);
         ch.start(); // 채팅 쓰레드를 시작
         Transmission tr = new Transmission(roomNum);
         tr.start();
         while(ch.isAlive() || tr.isAlive()) {
            // 채팅방이 살아있을 때까지 아무것도 안함
            // TODO: 수정이 필요함
         }
         //==================================================================
         System.out.println("Chatting & FILE is dead");
         
         // 종료하기 ==========================================================
         out.writeInt(888); // protocol bye-bye-bye
         // 스트림과 소켓 닫기
         out.close();
         in.close();
         clientSocket.close();
         // ================================================================
      
      } catch (Exception e) {
         System.out.println("Error");
      }
      System.out.println("Client End");
   }
   
   // 채팅방 쓰레드 =====================================================================
   private static class Chatting extends Thread {
      Socket chatSocket;
      ObjectInputStream cin;
      ObjectOutputStream cout;

      /**
       * Chatting Constructor
       * @param rm : room number의 줄임말
       */
      public Chatting(int rm) {
         try {
            InetAddress host = InetAddress.getLocalHost(); // 로컬 주소 받아오기
            this.chatSocket = new Socket(host, rm);
            this.cout = new ObjectOutputStream(chatSocket.getOutputStream());
            this.cin = new ObjectInputStream(chatSocket.getInputStream());
         } catch (IOException e) {
            e.printStackTrace();
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

<<<<<<< HEAD:Network_Team_Project/src/Client.java
      public void run() {
         // 채팅방 test 
         Scanner keyboard = new Scanner(System.in);
         try {
            
            for (int i = 0; i < 1; i++) {
               System.out.print("Enter Message: ");
               // 이 부부은 바꿔서 키보드로 입력하는 부분에 넣으면 될것같아요! =============
               String temp = keyboard.nextLine();
               cout.writeObject(temp);
               cout.flush();
               // =====================================================
               System.out.println((String) cin.readObject()); // 이건 채팅 뜨는데에 넣으면 될것같아요
            }
         } catch (Exception e) {
            e.printStackTrace();
         } finally {
            try {
               // 소켓과 스트림 종료
               chatSocket.close();
               cin.close();
               cout.close();
               keyboard.close();
               
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }
   }
   // ===============================================================================
   
   
   private static class Transmission extends Thread {
     
         Socket fileSocket;
         ObjectInputStream fin;
         ObjectOutputStream fout;
         
          public Transmission(int rm) {
             try {
               InetAddress host = InetAddress.getLocalHost(); // 로컬 주소 받아오기
               this.fileSocket = new Socket(host, rm+1);
               this.fin = new ObjectInputStream(fileSocket.getInputStream());
            } catch (IOException e) {
               e.printStackTrace();
            } catch (Exception e) {
               e.printStackTrace();
            }
          }
          public void run() {
             try {
            	
                //String str = fromClient.readLine();
                String str = "test1";
                System.out.println("sending file name: " + str + "\n");
               
                BufferedOutputStream out = new BufferedOutputStream( fileSocket.getOutputStream() );
                FileInputStream fileIn = new FileInputStream( "C:\\Users\\윤혜주\\Downloads\\2018-2학기\\프잉\\us1.png"); // 받아올 파일 위치 및 이름
                byte[] buffer = new byte[8192];
                int bytesRead =0;
                while ((bytesRead = fileIn.read(buffer)) > 0) { // 파일 바이트로 읽어서 전송
                    out.write(buffer, 0, bytesRead);
                }
                out.flush();
                out.close();
                fileIn.close();

                 
                System.out.println(str + " file recieve success");
             } catch (Exception e) {
                e.printStackTrace();
             } finally {
                try {
                   fileSocket.close();
                } catch (Exception e) {
                   e.printStackTrace();
                }
             }
          }
   }
}
=======
		public void run() {
			// 채팅방 test 
			Scanner keyboard = new Scanner(System.in);
			try {
				
				for (int i = 0; i < 1; i++) {
					System.out.print("Enter Message: ");
					// 이 부부은 바꿔서 키보드로 입력하는 부분에 넣으면 될것같아요! =============
					String temp = keyboard.nextLine();
					cout.writeObject(temp);
					cout.flush();
					// =====================================================
					System.out.println((String) cin.readObject()); // 이건 채팅 뜨는데에 넣으면 될것같아요
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					// 소켓과 스트림 종료
					chatSocket.close();
					cin.close();
					cout.close();
					keyboard.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	// ===============================================================================
	
	
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
					this.fileSocket = new Socket(host, rm+1);
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
>>>>>>> e8dcdb5ee410bc70301bf0a1eda599e40656785d:Network_Team_Project/src/server/Client.java
