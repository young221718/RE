package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import basic.Chat;
import basic.Room;

public class ChatRoom extends Room {

	private static HashMap<Integer, HashMap<String, ObjectOutputStream>> broadcaster = new HashMap<Integer, HashMap<String, ObjectOutputStream>>();
	private Chat input;

	public ChatRoom(Socket socket) {
		super(socket);
	}

	public ChatRoom(Socket socket, int port, String email) {
		super(socket);
		this.portNumber = port;
		synchronized (broadcaster) {
			if (!broadcaster.containsKey(portNumber)) {
				broadcaster.put(portNumber, new HashMap<String, ObjectOutputStream>());
			}
		}
		this.email = email;
	}

	public void run() {
		System.out.println("Enter chat room");
		try {

			// Create character streams for the socket.
			fromClient = new ObjectInputStream(roomSocket.getInputStream());
			toClient = new ObjectOutputStream(roomSocket.getOutputStream());
			

			System.out.println("Chat stream connect");
			System.out.println("port: " + portNumber);

			synchronized (broadcaster) {
				broadcaster.get(portNumber).put(email, toClient);
			}

			// broadcast enter
			input = new Chat();
			input.email = "";
			input.name = "";
			input.message = "================<" + db.GetUserName(email) + " enter>=================\n";
			for (ObjectOutputStream oos : broadcaster.get(portNumber).values()) {
				oos.writeObject(input);
				oos.flush();
			}

			System.out.println("ChatRoom Log 1");

			while (true) {
				input = (Chat)fromClient.readObject();
//				input = ((Chat)fromClient.readObject());
				System.out.println(input);
				if (input == null) {
					System.out.println("NULL");
					return;
				}

				for (ObjectOutputStream oos : broadcaster.get(portNumber).values()) {
					oos.writeObject(input);
					oos.flush();
				}	
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// This client is going down! Remove its name and its print
			// writer from the sets, and close its socket.

			try {
				// remove exit user
				if (!broadcaster.get(portNumber).containsKey(email))
					broadcaster.get(portNumber).remove(email);

				// broadcast exit
				input.email = "";
				input.name = "";
				input.message = "========<" + db.GetUserName(email) + " exit>=========\n";
				for (ObjectOutputStream oos : broadcaster.get(portNumber).values()) {
					oos.writeObject(input);
					oos.flush();
				}

				// if hash map is empty free memory
				synchronized (broadcaster) {
					if (broadcaster.get(portNumber).isEmpty())
						broadcaster.remove(portNumber);
				}
				
				// close stream and socket
				toClient.close();
				fromClient.close();
				roomSocket.close();
				
				System.out.println("end Chat");
			} catch (IOException e) {
			}
		}

	}
}
