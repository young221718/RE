package server;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Calendar;
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
         roomInfor.startDate = Calendar.getInstance();
         roomInfor.endDate = Calendar.getInstance();
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

      public void run() {
         // 채팅방 test 
         Scanner keyboard = new Scanner(System.in);
         try {
            
            for (int i = 0; i < 100000; i++) {
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
               //this.fout = new ObjectOutputStream(fileSocket.getOutputStream());
               this.fin = new ObjectInputStream(fileSocket.getInputStream());
            } catch (IOException e) {
               e.printStackTrace();
            } catch (Exception e) {
               e.printStackTrace();
            }
          }
          public void run() {
             try {
            	   //fromClient = new ObjectInputStream(roomSocket.getInputStream());
                  //fin = new ObjectInputStream(fileSocket.getInputStream());
                   
                 //String str = fromClient.readLine();
            	 
            	 /*
            	  * 
            	  * foreach문으로  확인 - pro - 77(파일 전송), 88- 보낼 파일 없음
            	  */
            	
                    String str = "test1";
                 System.out.println("sending file name: " + str + "\n");
                
                 BufferedOutputStream out = new BufferedOutputStream( fileSocket.getOutputStream() );
                 FileInputStream fileIn = new FileInputStream( "C:\\Users\\윤혜주\\Downloads\\2018-2학기\\프잉\\us1.png");
                 //int FF_pro = 77;
                 //out.write(FF_pro);
                 byte[] buffer = new byte[8192];
                 int bytesRead =0;
                 while ((bytesRead = fileIn.read(buffer)) > 0) {
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
                   //fin.close();
                  
                   //fout.close();
                } catch (Exception e) {
                   e.printStackTrace();
                }
             }
          }
   }
}