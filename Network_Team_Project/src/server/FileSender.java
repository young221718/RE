package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import basic.Room;

public class FileSender extends Room {

	BufferedOutputStream toClient_Re = null;
	BufferedInputStream Bin = null;
	FileInputStream Fin = null;
	DataOutputStream Dout = null;

	public FileSender(Socket socket) {
		super(socket);
		// TODO Auto-generated constructor stub
	}

	public FileSender(Socket socket, int port) {
		super(socket);
		this.portNumber = port;
	}

	public void run() {
		int sender_pro = 101; // 시간이 일치 후 사용자가 방에 들어가면 보내주는 프로토콜
		// int send_sign = 666;
		/*
		 * 폴더에 저장되어 있는 파일 전부 보내기!
		 */
		try {

			String group_name = db.GetRoomName(portNumber);
			System.out.println("group name: " + group_name);

			File file = new File("C:\\RE\\" + group_name); // 각 그룹명으로 저장된 폴더

			File[] fileList = file.listFiles();
			String filepath = null;

			toClient_Re = new BufferedOutputStream(roomSocket.getOutputStream());
			Dout = new DataOutputStream(roomSocket.getOutputStream());
			fromClient = new ObjectInputStream(roomSocket.getInputStream());
			System.out.println("FILE SENDER ROOM CONNECTED");

			int threeone = fromClient.readInt();
			System.out.println("please 31 " + threeone);
			
			Dout.write(fileList.length); // 보내는 파일 개수 전송
			int sign = 0;
			for (int i = 0; i < fileList.length; i++) {
				toClient_Re.write(sender_pro); // 프로토콜 전송 - 파일 간다는 신호
				toClient_Re.flush();
				threeone = fromClient.readInt();
				System.out.println("please 13 "+ threeone);

				File files = fileList[i];
				filepath = files.getPath();

				// Dout.writeUTF(filepath);
				Fin = new FileInputStream(filepath);
				Bin = new BufferedInputStream(Fin);
				System.out.println(Bin.available());
				Dout.writeInt(Bin.available());

				int bytesRead = 0;
				while ((bytesRead = Bin.read()) != -1) {
					toClient_Re.write(bytesRead);
				}
				toClient_Re.flush();
				Fin.close();
				Bin.close();

				// new Thread().sleep(1000);
				if (i == fileList.length - 1) {
					sign = 8;

				}
				Dout.write(sign);
				Dout.flush();
			}
			Fin.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {
				roomSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
