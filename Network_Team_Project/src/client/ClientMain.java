package client;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ClientMain {
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				
					Client REclient = new Client();

					ImageIcon back = new ImageIcon("wood2.PNG"); // 배경이미지
					JLabel imgLabel = new JLabel(back);
					REclient.add(imgLabel);
					imgLabel.setVisible(true);
					imgLabel.setBounds(-3, -20, 940, 665);

					REclient.setVisible(true);
					REclient.ConnectSocket();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
