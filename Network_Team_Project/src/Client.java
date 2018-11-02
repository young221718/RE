import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		try {
			System.out.println("Client Start...");
			InetAddress host = InetAddress.getLocalHost();
			Socket clientSocket = new Socket(host, 1234);

			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
			
				
			// 채팅방만들기
			Integer pro = new Integer(111);
			out.writeObject(pro);
			out.flush();
			int roomNum = (Integer)in.readObject();
			System.out.println("Room: "+ roomNum);
			
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
			for(int i=0;i<10;i++) {
				cout.writeObject((Integer)i);
				cout.flush();
				System.out.println((String)cin.readObject());
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
