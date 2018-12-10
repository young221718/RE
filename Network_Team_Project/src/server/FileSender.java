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

	/*
	 * <<< Explain the FileSender >>>
	 * The FileSender is executed when the user reaches the agreed time.
	 * And sends the files stored in the group- specific folder to the client again.
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		int sender_pro = 101; // When the client enters the room after the time matches.
		/*
		 * 폴더에 저장되어 있는 파일 전부 보내기!
		 */
		try {

			//Folder of port number assigned to group
			File file = new File("C:\\RE\\" + portNumber); 
			
			// List of files stored in folder
			File[] fileList = file.listFiles();
			String filepath = null;

			toClient_Re = new BufferedOutputStream(roomSocket.getOutputStream());
			Dout = new DataOutputStream(roomSocket.getOutputStream());
			fromClient = new ObjectInputStream(roomSocket.getInputStream());
			System.out.println("FILE SENDER ROOM CONNECTED");

			// the protocol comes means the client is ready to receive.
			int threeone = fromClient.readInt(); 
			System.out.println("please 31 " + threeone);
			
			Dout.write(fileList.length); // Send number of outgoing files
			int sign = 0; 
			
			
			for (int i = 0; i < fileList.length; i++) {
				toClient_Re.write(sender_pro); // Protocol transfer - signal to file
				toClient_Re.flush();
				
				threeone = fromClient.readInt(); // Means that the file was well received. 
				System.out.println("please 13 "+ threeone);

				File files = fileList[i]; // file list in group's folder 
				filepath = files.getPath(); // location that file saved

				Fin = new FileInputStream(filepath); 
				Bin = new BufferedInputStream(Fin);
				System.out.println(Bin.available());
				Dout.writeInt(Bin.available()); // Send the file size

				int bytesRead = 0;
				
				// Until file is read the file, write the byte to client
				while ((bytesRead = Bin.read()) != -1) {
					toClient_Re.write(bytesRead);
				}
				toClient_Re.flush();
				Fin.close();
				Bin.close();
				
				// sign that there are no more files to send
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
