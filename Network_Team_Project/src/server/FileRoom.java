package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import basic.Room;

public class FileRoom extends Room {

	int cnt=0;
	public FileRoom(Socket socket) {
		super(socket);
	}
	public FileRoom(Socket socket, int port) {
		super(socket);
		this.portNumber = port;
	}

	

	public void run() {
		System.out.println("Enter the FileRoom!");
		try {

			ObjectOutputStream toCleint = new ObjectOutputStream(roomSocket.getOutputStream());
			String imageName = "익스샌프란01";
			// toClient.writeObject(imageName);
			System.out.println("file name: " + imageName);

			InputStream in = roomSocket.getInputStream();
			
			int fPro = in.read(); // 프로토콜 
			
			
			String group_name = db.GetRoomName(portNumber);
					
			
			if (fPro == 77) {
				//루프할 숫자 받기
				int count = in.read();
				for(int i=0;i<count;i++)
				{
				System.out.println("roof\n");
				File f = new File("C:\\RE\\" + group_name); // folderNN - 폴더, groupNB - group_id
				if (f.exists() == false)
					f.mkdirs();
				FileOutputStream out = new FileOutputStream(f + "\\us" +cnt +".png");

				byte[] buffer = new byte[8192];
				int bytesRead = 0;
				while ((bytesRead = in.read(buffer)) > 0) {
					out.write(buffer, 0, bytesRead);

				}
				cnt++;
				out.flush();
				//out.close();
				}
			}
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