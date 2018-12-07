package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import basic.Room;
import basic.RoomInformation;

public class WaitingRoom extends Room {

	private static HashMap<Integer, ServerSocket> chatRoomServerSockets = new HashMap<Integer, ServerSocket>();
	// private static HashMap<Integer, RoomInformation> roomInforMap = new
	// HashMap<Integer, RoomInformation>();
	private static HashMap<Integer, ServerSocket> fileRoomServerSockets = new HashMap<Integer, ServerSocket>();

	private RoomInformation roomInfor;

	WaitingRoom(Socket socket) {
		super(socket);
	}

	public void run() {
		try {
			System.out.println("Welcome Waiting Room");

			toClient = new ObjectOutputStream(roomSocket.getOutputStream());
			fromClient = new ObjectInputStream(roomSocket.getInputStream());

			// loop until login success
			while (!LogIn()) {
			}
			System.out.println("Success LogIn");

			// 사용자가 waiting room에서 하는 일을 확인
			// 소켓이 연결되어 있을 때까지 유지된다.
			while (roomSocket.isConnected()) {
				protocol = (Integer) fromClient.readInt();
				System.out.println("protocol: " + protocol);
				// protocol
				// 111 : user want to make a room
				// 222 : user want to enter the room
				// 888 : bye
				if (111 == protocol) { // Make a room
					// TODO : 방 옵션이 올바른지 확인한다.
					roomInfor = (RoomInformation) fromClient.readObject();
					roomInfor.print();

					// 방 만들기 --> 서버 소켓을 만들어 놓는다.
					// 방 만들기를 요청한 클라이언트에게 핀번호를 전송해준다.
					try {
						System.out.println("Enter protocol 111");
						int roomNumber = makeRoom();
						// makeFileRoom(roomNumber + 1);

						toClient.writeObject(roomNumber);
						toClient.flush();
						System.out.println(roomNumber + " room made");

					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("End Protocol 111");

				} else if (222 == protocol) { // Enter the room
					System.out.println("Enter protocol 222");

					int PIN = (Integer) fromClient.readObject();

					if (db.IsAlreadyUser(PIN, email) || db.IsPossibleEnterRoom(PIN)) {
						System.out.println("Possible");

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
						if (ck == 1)
							toClient.writeInt(149);
						else
							toClient.writeInt(151);
						toClient.flush();

						// TODO: update room information current people
						int temp = db.InsertRoomUser(PIN, email);
						if (temp == 1)
							db.UpdateCurPeople(PIN);
						System.out.println("temp = " + temp);
						db.CommitDB();

						// enter the room
						System.out.println("Enter room Pin in " + PIN);
						System.out.println("enter chat room : " + enterChatRoom(PIN));
						System.out.println("enter file room : " + enterFileRoom(PIN));
					} else {
						// TODO: 들어가지 못하다고 알리는 프로토콜
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
			System.out.println("Error 1");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error 2");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error 3");
			e.printStackTrace();
		} finally {
			try {
				if (!roomSocket.isClosed())
					roomSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			db.DisconnectDB();
		}

		System.out.println("End Watiing Room");
	}

	/**
	 * LogIn
	 * 
	 * 이 함수는 첫 화면에서 사용자의 이메일과 이름을 받는 함수이다. 이메일은 유니크해야하며, 같은 이메일이 2개가 동시에 들어 올 수 없게
	 * 해야한다.
	 * 
	 * @return : 로그인이 성공적으로 되었으면 true를 리턴, 다른 오류가 있을 경우 false 를 리턴한다.
	 */
	private boolean LogIn() throws Exception {

		protocol = fromClient.readInt();
		if (protocol == 170) { // 회원가입
			email = (String) fromClient.readObject();
			String userName = (String) fromClient.readObject();
			String password = (String) fromClient.readObject();

			System.out.println("from client: " + email + " " + userName + " " + password);

			int result = db.InsertUserInfor(userName, email, password);
			db.CommitDB();
			if (result == 1) {
				toClient.writeInt(171); // success
				toClient.flush();
			} else if (result == -1) {
				toClient.writeInt(175); // already exist
				toClient.flush();
			} else if (result == 0) {
				toClient.writeInt(179); // sql error
				toClient.flush();
			}
			System.out.println("join " + result);

		} else if (protocol == 180) { // 로그인
			email = (String) fromClient.readObject();
			String password = (String) fromClient.readObject();

			System.out.println("from client: " + email + " " + password);

			int result = db.CheckPassword(email, password);
			if (result == 1) {
				toClient.writeInt(181); // success
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
			System.out.println("join " + result);

		}

		db.CommitDB();
		return false;
	}

	// For ChatRoom
	// ============================================================================================
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
		PIN = makePIN();

		synchronized (db) {
			roomInfor.port = PIN;
			// Check if room is already exist
			if (db.CheckRoomExist(PIN))
				return -1; // already exist
			db.InsertRoomInfor(roomInfor); // not exist
			db.CommitDB();
		}
		return PIN;
	}

	/**
	 * enterChatRoom
	 * 
	 * 이 함수는 사용자가 PIN 번호를 입력하고 방에 들었가기를 눌렀을 때 사용될 함수이다. 사용자가 올바른 PIN 번호를 입력했을 때만 방으로
	 * 연결해 준다.
	 * 
	 * @param PIN:
	 *            들어가고 싶은 채팅방의 핀번호
	 * @return 오류 없이 방에 들어갔으면 true를 리턴해준다.
	 */
	private boolean enterChatRoom(int PIN) {

		if (!chatRoomServerSockets.containsKey(PIN)) {
			loadChatRoomServerSocket(PIN);
		}
		try {
			new ChatRoom(chatRoomServerSockets.get(PIN).accept(), PIN, email).start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * // * makeFileRoom // * // * 이 함수는 사용자가 방 만들기 버튼을 누르고, 올바른 옵션을 입력한 후 채팅방의
	 * 서버소켓을 할당하는 함수 이다. 유니크한 핀번호를 가지게 // * 될때 까지 핀 번호를 할당을 시도한다. // * // * @return
	 * : 파일룸의 PIN 번호를 리턴해준다. (채팅방의 PIN 번호는 짝수이다.) //
	 */
	// private void makeFileRoom(int fileRM) {
	// System.out.println("Enter makeFileRoom");
	// synchronized (fileRoomServerSockets) {
	//
	// if (fileRoomServerSockets.containsKey((Integer) fileRM)) {
	// System.out.println("eeeeeeaaaaak - file");
	// }
	// ServerSocket tempSV = new ServerSocket(fileRM);
	//
	// fileRoomServerSockets.put(fileRM, tempSV);
	// }
	// System.out.println("end makeFileRoom");
	// }

	/**
	 * enterFileRoom
	 * 
	 * 이 함수는 사용자가 PIN 번호를 입력하고 방에 들었가기를 눌렀을 때 사용될 함수이다. 사용자가 올바른 PIN 번호를 입력했을 때만 방으로
	 * 연결해 준다.
	 * 
	 * @param PIN
	 *            - 들어가고 싶은 채팅방의 핀번호
	 * @return 오류 없이 방에 들어갔으면 true를 리턴해준다.
	 */
	private boolean enterFileRoom(int PIN) {
		if (!fileRoomServerSockets.containsKey(PIN)) {
			loadFileRoomServerSocket(PIN);
		}
		try {
			if (false == db.IsSender(PIN)) {
				//TODO: 어떤방에 들어갔는지 알려주는 프로토콜이 필요함.
				toClient.writeBoolean(false);
				toClient.flush();
				System.out.println("FILE RECIEVE");
				new FileRoom(fileRoomServerSockets.get(PIN).accept(), PIN).start();
				
			} else {
				toClient.writeBoolean(true);
				toClient.flush();
				System.out.println("FILE SENDER");
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
	 * 올바른 핀번호를 램던으로 할당해 준다.
	 * 
	 * @return 10000 이상을 리턴함
	 */
	private int makePIN() {
		int PIN;

		synchronized (db) {
			PIN = db.GetRoomNumber();
			System.out.println(PIN);
			db.UpdateRoomNumber();
			db.CommitDB();
		}
		return PIN;
	}

	/**
	 * loadChatRoomServerSocket load memory from database
	 * 
	 * @param roomNum
	 *            room Number
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
	 * loadFileRoomServerSocket load memory from database
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
