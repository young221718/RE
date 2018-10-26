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
			String message = "This is message from client";
			
			
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
			
			
			
			out.close();
			in.close();
			clientSocket.close();
			
		} catch (Exception e) {
			System.out.println("Error");
		}
		
		System.out.println("Client End");
	}

}
