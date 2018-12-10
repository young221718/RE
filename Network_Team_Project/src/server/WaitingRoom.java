package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import basic.Room;
import basic.RoomInformation;

/**
 * WaitingRoom class
 *
 * This class manage login and connect to unique room(chat room and file sender
 * or receiver).
 * 
 * @author Young
 *
 */
public class WaitingRoom extends Room {

	private static HashMap<Integer, ServerSocket> chatRoomServerSockets = new HashMap<Integer, ServerSocket>();
	private static HashMap<Integer, ServerSocket> fileRoomServerSockets = new HashMap<Integer, ServerSocket>();
	private RoomInformation roomInfor;

	/**
	 * Constructor
	 * 
	 * Connect socket with client
	 * 
	 * @param socket
	 */
	WaitingRoom(Socket socket) {
		super(socket);
	}

	public void run() {
		try {
			System.out.println("Welcome Waiting Room");

			// Connect input and output stream to client
			toClient = new ObjectOutputStream(roomSocket.getOutputStream());
			fromClient = new ObjectInputStream(roomSocket.getInputStream());

			// loop until login success
			while (!LogIn()) {
			}
			System.out.println("Success LogIn");

			// Maintain connect while user use program
			while (roomSocket.isConnected()) {
				protocol = (Integer) fromClient.readInt();
				System.out.println("protocol: " + protocol);
				// protocol
				// 111 : user want to make a room
				// 222 : user want to enter the room
				// 888 : bye
				if (111 == protocol) { // Make a room
					// read room information from client
					roomInfor = (RoomInformation) fromClient.readObject();
					roomInfor.print();

					// Insert to database the room information
					// Return to unique room number to user
					try {
						System.out.println("Enter protocol 111");

						// make unique room number and send to user
						int roomNumber = makeRoom();
						toClient.writeObject(roomNumber);
						toClient.flush();
						System.out.println(roomNumber + " room made");
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("End Protocol 111");

				} else if (222 == protocol) { // Enter the room
					System.out.println("Enter protocol 222");

					// read the room number which user want to enter
					// check if the room can be entered or not
					int PIN = (Integer) fromClient.readObject();
					// TODO: 없는 방이라는 프로토콜이 있어야함
					boolean isPossible = db.IsPossibleEnterRoom(PIN) || db.IsAlreadyUser(PIN, email);
					toClient.writeBoolean(isPossible);
					toClient.flush();
					if (isPossible) {
						System.out.println("Possible"); // Possible case

						// send room's question
						String q = db.GetRoomQuestion(PIN);
						toClient.writeObject(q);
						toClient.flush();
						System.out.println("pin: " + PIN + "Question: " + q);
						toClient.writeInt(8);
						toClient.flush();

						// check room's answer is correct
						PIN = (Integer) fromClient.readObject();
						String an = (String) fromClient.readObject();
						int ck = db.CheckRoomAnswer(an, PIN);
						if (ck == 1) {
							toClient.writeInt(149); // user's answer is correct
							toClient.flush();

							// update to room user information in database if new user
							// do not update when user already entered in past
							int temp = db.InsertRoomUser(PIN, email);
							if (temp == 1)
								db.UpdateCurPeople(PIN);
							db.CommitDB();

							// enter the room
							System.out.println("Enter room Pin in " + PIN);
							System.out.println("enter chat room : " + enterChatRoom(PIN));
							System.out.println("enter file room : " + enterFileRoom(PIN));
						} else {
							toClient.writeInt(151); // user's answer is wrong or there is a error
							toClient.flush();
						}
					} else {
						System.out.println("Not Possible");
					}

				} else if (888 == protocol) {
					toClient.close();
					fromClient.close();
					break;

				} else {
					// TODO : 프로토콜 예외 처리해야함.
				}
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// close socket when user exit
				if (!roomSocket.isClosed())
					roomSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// disconnect database connection
			db.DisconnectDB();
		}
		System.out.println("End Watiting Room");
	}

	/**
	 * LogIn
	 * 
	 * Check user's id and password is correct. If user want to join our program
	 * update new user's information
	 * 
	 * @return : if success LogIn return true, else return false
	 */
	private boolean LogIn() throws Exception {

		// LogIn Protocol
		// 170: user want to join us
		// 180: user want to login
		protocol = fromClient.readInt();
		if (protocol == 170) { // Join

			// read user's information
			email = (String) fromClient.readObject();
			String userName = (String) fromClient.readObject();
			String password = (String) fromClient.readObject();

			// update to database and check error
			int result = db.InsertUserInfor(userName, email, password);
			db.CommitDB();
			if (result == 1) {
				toClient.writeInt(171); // success join
				toClient.flush();
			} else if (result == -1) {
				toClient.writeInt(175); // already exist
				toClient.flush();
			} else if (result == 0) {
				toClient.writeInt(179); // sql error
				toClient.flush();
			}

		} else if (protocol == 180) { // LogIn
			// read email and password
			email = (String) fromClient.readObject();
			String password = (String) fromClient.readObject();

			// check email and password
			int result = db.CheckPassword(email, password);
			if (result == 1) {
				toClient.writeInt(181); // success Login
				toClient.flush();

				// give to client email and name
				toClient.writeObject(email);
				toClient.writeObject(db.GetUserName(email));
				toClient.flush();

				System.out.println(email + " " + db.GetUserName(email));
				return true;
			} else if (result == 0) {
				toClient.writeInt(183); // sql error
				toClient.flush();
			} else if (result == -1) {
				toClient.writeInt(185); // wrong password
				toClient.flush();
			} else if (result == -2) {
				toClient.writeInt(187); // not exist email
				toClient.flush();
			}
		}

		db.CommitDB();
		return false;
	}

	/**
	 * makeRoom
	 * 
	 * Make unique room number which called 'PIN'. PIN is odd number because it
	 * means port number. Insert to room information in database.
	 * 
	 * @return : return PIN if success make room, else return -1 if fail to make
	 *         room.
	 */
	private int makeRoom() throws Exception {
		int PIN;
		PIN = makePIN(); // make unique pin

		synchronized (db) {
			roomInfor.port = PIN;
			// Check if room is already exist
			if (db.CheckRoomExist(PIN))
				return -1; // already exist

			// not exist room
			if (db.InsertRoomInfor(roomInfor))
				db.CommitDB();
			else
				return -1;
		}
		return PIN;
	}

	/**
	 * enterChatRoom
	 * 
	 * Enter the chat room which user want to enter. If room doesn't load memory
	 * make server socket.
	 * 
	 * @param PIN
	 *            - the room number which user want to enter
	 * @return if success to enter chat room return true, else return false
	 */
	private boolean enterChatRoom(int PIN) {
		// check if server socket is made or not
		if (!chatRoomServerSockets.containsKey(PIN)) {
			// if not in memory load it
			loadChatRoomServerSocket(PIN);
		}
		try {
			// connect to user to the chat room
			new ChatRoom(chatRoomServerSockets.get(PIN).accept(), PIN, email).start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * enterFileRoom
	 * 
	 * Enter the file room which user want to enter. If room doesn't load memory
	 * make server socket. This method distinguish if room is closed room or opened
	 * room
	 * 
	 * @param PIN
	 *            - the room number which user want to enter
	 * @return if success to enter chat room return true, else return false
	 */
	private boolean enterFileRoom(int PIN) {
		// check if file room is in memory
		if (!fileRoomServerSockets.containsKey(PIN)) {
			// doesn't exist load it
			loadFileRoomServerSocket(PIN);
		}
		try {
			if (false == db.IsSender(PIN)) { // closed room: server will receive file
				toClient.writeBoolean(false); // noticed to client room is closed
				toClient.flush();

				// connect to file room
				new FileRoom(fileRoomServerSockets.get(PIN).accept(), PIN).start();

			} else { // opened room: server will send file to user
				toClient.writeBoolean(true); // noticed to client room is opened
				toClient.flush();

				// connect to file sender room
				new FileSender(fileRoomServerSockets.get(PIN).accept(), PIN).start();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * makePIN
	 * 
	 * make unique room number. room number will be bigger or equal than 10000.
	 * room number must be even number.
	 * 
	 * @return return even number that bigger than 9999
	 */
	private int makePIN() {
		int PIN;

		synchronized (db) {
			PIN = db.GetRoomNumber();
			db.UpdateRoomNumber();
			db.CommitDB();
		}
		return PIN;
	}

	/**
	 * loadChatRoomServerSocket
	 * 
	 * load chat room server socket memory from database
	 * 
	 * @param roomNum
	 *            - room Number
	 * @return if success return true else return false
	 */
	private boolean loadChatRoomServerSocket(int roomNum) {
		try {
			synchronized (chatRoomServerSockets) {
				if (!chatRoomServerSockets.containsKey(roomNum)) {
					chatRoomServerSockets.put(roomNum, new ServerSocket(roomNum));
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * loadFileRoomServerSocket
	 * 
	 * load file room server socket memory from database
	 * 
	 * @param roomNum
	 *            room Number
	 * @return if success return true else return false
	 */
	private boolean loadFileRoomServerSocket(int roomNum) {
		try {
			synchronized (fileRoomServerSockets) {
				if (!fileRoomServerSockets.containsKey(roomNum)) {
					fileRoomServerSockets.put(roomNum, new ServerSocket(roomNum + 1));
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
