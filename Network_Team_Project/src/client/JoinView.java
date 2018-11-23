package client;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JoinView extends JFrame {
	
	private Client getInfo;
	
	private JPanel contentPane;
	private JTextField JoinName;
	private JTextField JoinEmailAdd;
	private JTextField Joinpass;
	private JTextField JoinCheck;
	public JButton btnConfirm;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JoinView frame = new JoinView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JoinView() {
		
		setTitle("Join");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setBounds(91, 52, 78, 18);
		contentPane.add(lblUsername);
		
		JoinName = new JTextField();
		JoinName.setText("name");
		JoinName.setBounds(180, 49, 160, 24);
		contentPane.add(JoinName);
		JoinName.setColumns(10);
		
		
		JLabel lblEmail = new JLabel("E - mail");
		lblEmail.setBounds(91, 85, 62, 18);
		contentPane.add(lblEmail);
		
		JoinEmailAdd = new JTextField();
		JoinEmailAdd.setText("email address");
		JoinEmailAdd.setBounds(180, 82, 160, 24);
		contentPane.add(JoinEmailAdd);
		JoinEmailAdd.setColumns(10);
		
		
		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setBounds(91, 118, 78, 18);
		contentPane.add(lblNewLabel);
		
		Joinpass = new JTextField();
		Joinpass.setText("password");
		Joinpass.setBounds(180, 115, 160, 24);
		contentPane.add(Joinpass);
		Joinpass.setColumns(10);
		
		JLabel lblPwCheck = new JLabel("Check PW");
		lblPwCheck.setBounds(91, 151, 78, 18);
		contentPane.add(lblPwCheck);
		
		JoinCheck = new JTextField();
		JoinCheck.setText("check");
		JoinCheck.setBounds(180, 148, 160, 24);
		contentPane.add(JoinCheck);
		JoinCheck.setColumns(10);
		
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(156, 197, 105, 27);
		contentPane.add(btnConfirm);
		
		setVisible(true);
		
	}

	public void setMain(Client client) {
		this.getInfo = client;
		
	}


}
