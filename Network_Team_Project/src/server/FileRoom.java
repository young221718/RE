package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import basic.Room;
import basic.RoomInformation;

public class FileRoom extends Room {

	//FileOutputStream out;
	int cnt=0;
	public FileRoom(Socket socket) {
		super(socket);
	}

	public FileRoom(Socket socket, int port) {
		super(socket);
		this.portNumber = port;
	}

	public void run() {
		int a =0;
		System.out.println("Enter the FileRoom!");
		try {
			//ObjectOutputStream toCleint = new ObjectOutputStream(roomSocket.getOutputStream());
				BufferedInputStream up = new BufferedInputStream(roomSocket.getInputStream());
				DataInputStream fromClient = new DataInputStream(up);
			
			String imageName = "익스샌프란01";
			// toClient.writeObject(imageName);
			System.out.println("file name: " + imageName);

			//InputStream in = roomSocket.getInputStream();
			//DataInputStream in = roomSocket.getInputStream();
			int sign = 0;
			

			String group_name = db.GetRoomName(portNumber);
			
			do {
			int fPro = up.read(); // 프로토콜 
			
			if (fPro == 77) {
				int len = fromClient.readInt();
				System.out.println(len + " received");
				File f = new File("C:\\RE\\" + group_name);
				if (f.exists() == false) 
					f.mkdirs();// folderNN - 폴더, groupNB - group_id
				
				FileOutputStream toFile = new FileOutputStream(f +"\\" + a + ".png");
				BufferedOutputStream outFile = new BufferedOutputStream(toFile);
				int ch =0;
				
				for(int i=0; i<len; i++) {
					outFile.write(up.read());
				}
				sign = fromClient.read();
				a++;
				outFile.flush();
				outFile.close();} }while(sign != 99);
				
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				roomSocket.close();
			} catch (IOException e) {

			}
		}

	}
}