import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println("print!");
			Socket clientSocket = new Socket("127.0.0.1", 1234);

			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
			
			System.out.println("client test");
			String message = "This is message from client";
			out.writeInt(7777);
			System.out.println("end");
			
			clientSocket.close();
			
		} catch (Exception e) {
			System.out.println("Error");
		}
		
	}

}
