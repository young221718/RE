import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println("print!");
			InetAddress host = InetAddress.getLocalHost();
			Socket clientSocket = new Socket(host, 1234);

			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
			
			System.out.println("client test");
			
			
			
			// 잘 되는지 테스트 하는거
			out.writeObject("Message: 12345");
			out.flush();
			System.out.println((String)in.readObject());

			// 방만들기
			Integer pro = new Integer(111);
			out.writeObject(pro);
			out.flush();
			int roomNum = (Integer)in.readObject();
			System.out.println("Room: "+ roomNum);
			
			// 방들어가기
			pro = new Integer(222);
			out.writeObject(pro);
			out.flush();
			
			Socket chatSocket = new Socket(host, roomNum);
			System.out.println(3);
			ObjectInputStream cin = new ObjectInputStream(chatSocket.getInputStream());
			ObjectOutputStream cout = new ObjectOutputStream(chatSocket.getOutputStream());
			
			System.out.println(4);
			for(int i=0;i<10;i++) {
				System.out.println("gggg"+i);
				cout.writeObject((Integer)i);
				cout.flush();
				System.out.println("!");
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
