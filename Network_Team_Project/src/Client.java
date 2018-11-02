import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import basic.RoomInformation;

public class Client {

	public static void main(String[] args) {
		try {
			System.out.println("Client Start...");
			InetAddress host = InetAddress.getLocalHost();
			Socket clientSocket = new Socket(host, 1234);

			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

			// 채팅방만들기 =======================================================
			Integer pro = new Integer(111); // 채팅방을 만들기 위한 프로토콜 111
			out.writeObject(pro);
			out.flush();
			// 방 정보 보내기
			RoomInformation roomInfor = new RoomInformation("group1", -1, 3, "Who are you?", "I don't know", null);
			out.writeObject(roomInfor);
			out.flush();
			// 방의 핀번호 받아오기
			int roomNum = (Integer) in.readObject();
			System.out.println("Room: " + roomNum);
			// ================================================================
			
			
			// 채팅방들어가기
			pro = new Integer(222);
			out.writeObject(pro);
			out.flush();
			out.writeObject(roomNum);

			// 채팅방의 소켓, in, out 연결하기
			Socket chatSocket = new Socket(host, roomNum);
			ObjectOutputStream cout = new ObjectOutputStream(chatSocket.getOutputStream());
			ObjectInputStream cin = new ObjectInputStream(chatSocket.getInputStream());

			// 채팅방 test
			for (int i = 0; i < 10; i++) {
				cout.writeObject((Integer) i);
				cout.flush();
				System.out.println((String) cin.readObject());
			}

			out.close();
			in.close();
			chatSocket.close();
			clientSocket.close();

		} catch (Exception e) {
			System.out.println("Error");
		}

		System.out.println("Client End");
	}

}
