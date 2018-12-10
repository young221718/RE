package client;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * To use the RE program, a membership process is required. It is a membership
 * frame GUI class that pops up when you join a membership.
 * 
 * @author 조혜진
 * 
 **/

public class JoinView extends JFrame {

	private Client getInfo;

	public JPanel contentPane;
	public JTextField JoinName;
	public JTextField JoinEmailAdd;
	public JPasswordField Joinpass;
	public JPasswordField JoinCheck;
	public JButton btnConfirm;

	// Implement frame HostView
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JoinView frame = new JoinView();
					frame.setVisible(true);  //Make the HostView visible
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public JoinView() {

		// Color backColor = new Color(236,235,240);
		Color backColor = new Color(236, 223, 207); // Set the background color of the frame

		setTitle("Join"); // Set title
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 295, 470, 300); // 왼쪽, 아래, 가로, 세로 //set frame size and position

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(backColor); // Set the background color of the frame

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 15)); // Set font
		lblUsername.setForeground(Color.black); // Set font_color
		lblUsername.setBounds(91, 52, 78, 18); // Set 'lblUsername'JLabel size and position
		contentPane.add(lblUsername);  //Add lblUsername to ContentPane

		JoinName = new JTextField();
		JoinName.setBounds(190, 49, 160, 24); // Set 'JoinName'JTextField size and position
		contentPane.add(JoinName);  //Add JoinName to ContentPane
		JoinName.setColumns(10);  // Set JTextField's columns size

		JLabel lblEmail = new JLabel("E - mail");
		lblEmail.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 15)); //Set font
		lblEmail.setForeground(Color.black); // Set font_color
		lblEmail.setBounds(91, 85, 62, 18); // Set 'lblEmail'JLabel size and position
		contentPane.add(lblEmail);  //Add lblEmail to ContentPane

		JoinEmailAdd = new JTextField();
		JoinEmailAdd.setBounds(190, 82, 160, 24); // Set 'JoinEmailAdd'JTextField size and position
		contentPane.add(JoinEmailAdd);  //Add JoinEmailAdd to ContentPane
		JoinEmailAdd.setColumns(10); // Set JTextField's columns size

		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 15)); //Set font
		lblNewLabel.setForeground(Color.black);  // Set font_color
		lblNewLabel.setBounds(91, 118, 78, 18);  // Set 'JlblNewLabel'JLabel size and position
		contentPane.add(lblNewLabel);  //Add lblNewLabel to ContentPane

		Joinpass = new JPasswordField();
		Joinpass.setBounds(190, 115, 160, 24); // Set 'Joinpass'JPasswordField size and position
		contentPane.add(Joinpass);  //Add Joinpass to ContentPane
		Joinpass.setEchoChar('*');  // Convert character to *
		Joinpass.setColumns(10);  // Set JPasswordField's columns size

		JLabel lblPwCheck = new JLabel("Check PW");
		lblPwCheck.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 15));  //Set font
		lblPwCheck.setForeground(Color.black);  // Set font_color
		lblPwCheck.setBounds(91, 151, 78, 18);  // Set 'lblPwCheck'JJLabel size and position
		contentPane.add(lblPwCheck);  //Add lblPwCheck to ContentPane

		JoinCheck = new JPasswordField();
		JoinCheck.setBounds(190, 148, 160, 24); // Set 'JoinCheck'JPasswordField size and position
		contentPane.add(JoinCheck);  //Add JoinCheck to ContentPane
		JoinCheck.setEchoChar('*'); // Convert character to *
		JoinCheck.setColumns(10); // Set JPasswordField's columns size

		btnConfirm = new JButton("Confirm");
		btnConfirm.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 15)); // Set font
		btnConfirm.setForeground(Color.black); // Set font_color
		btnConfirm.setBounds(170, 197, 105, 27); // Set 'btnConfirm'Confirm size and position
		contentPane.add(btnConfirm);  //Add btnConfirm to ContentPane

	}

	// Method to connect to client
	public void setMain(Client client) {
		this.getInfo = client;

	}

}
