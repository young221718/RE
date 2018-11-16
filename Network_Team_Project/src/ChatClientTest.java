import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import basic.RoomInformation;

public class ChatClientTest {

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
			while(ch.isAlive());
			
			
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
	
	
	
}
