package server;

import java.net.ServerSocket;

/**
 * ReServer class
 * 
 * This class welcome to every client who access with 1234 port(you can change
 * port number to any number). This class connect client to waiting room to help
 * log in and enter the room.
 * 
 * @author Young
 *
 */
public class ReServer {

	// if you want to change server's port number change final variable PORT
	private static final int PORT = 1234;

	public static void main(String[] args) throws Exception {
		System.out.println("The RE server is running.");
		ServerSocket listener = new ServerSocket(PORT);
		try {
			while (true) {
				new WaitingRoom(listener.accept()).start();
			}
		} finally {
			listener.close();
		}
	}
}
