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

	public static HashMap<Integer, ServerSocket> chatRoomServerSockets = new HashMap<Integer, ServerSocket>();
	public static HashMap<Integer, RoomInformation> roomInforMap = new HashMap<Integer, RoomInformation>();
	public static HashMap<Integer, ServerSocket> fileRoomServerSockets = new HashMap<Integer, ServerSocket>();

	private static Integer ROOMPIN = new Integer(10000);

	WaitingRoom(Socket socket) {
		super(socket);
	}

	public void run() {
		try {
			System.out.println("Welcome Waiting Room");
			db = new Database();
			toClient = new ObjectOutputStream(roomSocket.getOutputStream());
			fromClient = new ObjectInputStream(roomSocket.getInputStream());

			// loop until login success
			while (!LogIn())
				;
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
						int roomNumber = makeChatRoom();
						makeFileRoom(roomNumber + 1);

						toClient.writeObject(roomNumber);
						toClient.flush();
						System.out.println(roomNumber + " room made");

					} catch (Exception e) {
						// TODO : 오류 프로토콜 처리해야한다!!!!!!
						toClient.writeBytes("ERROR: FAILED MAKING ROOM");
						toClient.writeBytes("ERROR: FAILED MAKING FileROOM");
						e.printStackTrace();
					}
					System.out.println("End Protocol 111");

				} else if (222 == protocol) { // Enter the room
					System.out.println("Enter protocol 222");
					
					// send room's question
					int PIN = (Integer) fromClient.readObject();
					toClient.writeObject(db.GetRoomQuestion(PIN));
					
					// check room's answer is correct
					PIN = (Integer) fromClient.readObject();
					String an = (String)fromClient.readObject();
					toClient.writeInt(db.CheckRoomAnswer(an, PIN));
					
					// enter the room
					System.out.println("Enter room Pin in " + PIN);
					enterChatRoom(PIN);
					enterFileRoom(PIN + 1);

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

			}
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
			String email = (String) fromClient.readObject();
			String userName = (String) fromClient.readObject();
			String password = (String) fromClient.readObject();

			System.out.println("from client: " + email + " " + userName + " " + password);

			int result = db.InsertUserInfor(userName, email, password);
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
			String email = (String) fromClient.readObject();
			String password = (String) fromClient.readObject();

			System.out.println("from client: " + email + " " + password);

			int result = db.CheckPassword(email, password);
			if (result == 1) {
				toClient.writeInt(181); // success
				toClient.flush();
				db.CommitDB();
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
	 * makeChatRoom
	 * 
	 * 이 함수는 사용자가 방 만들기 버튼을 누르고, 올바른 옵션을 입력한 후 채팅방의 서버소켓을 할당하는 함수 이다. 유니크한 핀번호를 가지게
	 * 될때 까지 핀 번호를 할당을 시도한다.
	 * 
	 * @return : 채팅방의 PIN 번호를 리턴해준다. (채팅방의 PIN 번호는 홀수이다.)
	 */
	private int makeChatRoom() throws Exception {
		int PIN;
		System.out.println("Enter makeChatRoom");
		synchronized (chatRoomServerSockets) {
			PIN = makePIN();
			if (chatRoomServerSockets.containsKey((Integer) PIN)) {
				System.out.println("Already Exist - ChatRoom:" + PIN);
			}
			synchronized (db) {
				roomInfor.port = PIN;
				db.InsertRoomInfor(roomInfor);
			}
			synchronized (roomInforMap) {
				roomInforMap.put(PIN, roomInfor);
			}
			ServerSocket tempSS = new ServerSocket(PIN);
			roomInfor.port = PIN;
			chatRoomServerSockets.put(PIN, tempSS);
		}
		System.out.println("end makeChatRoom");
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
		if (chatRoomServerSockets.containsKey(PIN)) {
			try {
				new ChatRoom(chatRoomServerSockets.get(PIN).accept(), roomInforMap.get(PIN)).start();

				System.out.println("Method enterChatroom successed");
				return true;
			} catch (Exception e) {
				System.out.println("Error in enterChatroom");
			}
		}
		return false;
	}
	// End ChatRoom
	// Method=======================================================================================

	// 파일룸
	/**
	 * makeFileRoom
	 * 
	 * 이 함수는 사용자가 방 만들기 버튼을 누르고, 올바른 옵션을 입력한 후 채팅방의 서버소켓을 할당하는 함수 이다. 유니크한 핀번호를 가지게
	 * 될때 까지 핀 번호를 할당을 시도한다.
	 * 
	 * @return : 파일룸의 PIN 번호를 리턴해준다. (채팅방의 PIN 번호는 짝수이다.)
	 */
	private void makeFileRoom(int fileRM) throws Exception {
		System.out.println("Enter makeFileRoom");
		synchronized (fileRoomServerSockets) {

			if (fileRoomServerSockets.containsKey((Integer) fileRM)) {
				System.out.println("eeeeeeaaaaak - file");
			}
			ServerSocket tempSV = new ServerSocket(fileRM);

			fileRoomServerSockets.put(fileRM, tempSV);
		}
		System.out.println("end makeFileRoom");
	}

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
	private static boolean enterFileRoom(int PIN) {
		if (fileRoomServerSockets.containsKey(PIN)) {
			try {
				new FileRoom(fileRoomServerSockets.get(PIN).accept(), roomInforMap.get(PIN - 1)).start();
				System.out.println("enterFileroom very good");
				return true;
			} catch (Exception e) {
				System.out.println("Error in enterFileroom");
			}
		}
		System.out.println("FFFFFFFFFF - file");
		return false;
	}

	/**
	 * makePIN
	 * 
	 * 올바른 핀번호를 램던으로 할당해 준다.
	 * 
	 * @return 10000 이상을 리턴함
	 */
	private static int makePIN() {
		int PIN;

		synchronized (ROOMPIN) {
			PIN = ROOMPIN;
			ROOMPIN += 2;
		}
		System.out.println("End makePin");
		return PIN;
	}

}
