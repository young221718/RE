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
			
			//Communication cm = new Communication("Client Request.");
			
			out.writeObject("Message: 12345");
			out.flush();
			System.out.println((String)in.readObject());
//			Communication re = (Communication)in.readObject();
//			System.out.println(re);
			
			out.close();
			in.close();
			clientSocket.close();
			
		} catch (Exception e) {
			System.out.println("Error");
		}
		
		System.out.println("Client End");
	}

}
