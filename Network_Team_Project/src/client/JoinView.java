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

public class JoinView extends JFrame {
	
	private Client getInfo;
	
	public JPanel contentPane;
	public JTextField JoinName;
	public JTextField JoinEmailAdd;
	public JPasswordField Joinpass;
	public JPasswordField JoinCheck;
	public JButton btnConfirm;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JoinView frame = new JoinView();
					//frame.setUndecorated(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected void setBorderPainted(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public JoinView() {
		//setUndecorated(true);
		Color backColor = new Color(255,255,255);
		/*색깔 후보*/ //(250,224,212)
		
		setTitle("Join");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(350, 295, 470, 300);  //왼쪽, 아래, 가로, 세로
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(backColor);   //배경색 바꾸는 부분
		
		
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 15));  //=========================== 글씨체 바꾸는 코드
		lblUsername.setForeground(Color.black);
		lblUsername.setBounds(91, 52, 78, 18);
		contentPane.add(lblUsername);
		
		JoinName = new JTextField();
		JoinName.setText("name");
		JoinName.setBounds(190, 49, 160, 24);
		contentPane.add(JoinName);
		JoinName.setColumns(10);
		
		
		JLabel lblEmail = new JLabel("E - mail");
		lblEmail.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 15));  //=========================== 글씨체 바꾸는 코드
		lblEmail.setForeground(Color.black);
		lblEmail.setBounds(101, 85, 62, 18);
		contentPane.add(lblEmail);
		
		JoinEmailAdd = new JTextField();
		JoinEmailAdd.setText("email address");
		JoinEmailAdd.setBounds(190, 82, 160, 24);
		contentPane.add(JoinEmailAdd);
		JoinEmailAdd.setColumns(10);
		
		
		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 15));  //=========================== 글씨체 바꾸는 코드
		lblNewLabel.setForeground(Color.black);
		lblNewLabel.setBounds(91, 118, 78, 18);
		contentPane.add(lblNewLabel);
		
		
		Joinpass = new JPasswordField();
		Joinpass.setText("password");
		Joinpass.setBounds(190, 115, 160, 24);
		contentPane.add(Joinpass);
		Joinpass.setEchoChar('*');
		Joinpass.setColumns(10);
		
		JLabel lblPwCheck = new JLabel("Check PW");
		lblPwCheck.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 15));  //=========================== 글씨체 바꾸는 코드
		lblPwCheck.setForeground(Color.black);
		lblPwCheck.setBounds(101, 151, 78, 18);
		contentPane.add(lblPwCheck);
		
		JoinCheck = new JPasswordField();
		JoinCheck.setText("check");
		JoinCheck.setBounds(190, 148, 160, 24);
		contentPane.add(JoinCheck);
		JoinCheck.setEchoChar('*');
		JoinCheck.setColumns(10);
		
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 15));  //=========================== 글씨체 바꾸는 코드
		btnConfirm.setForeground(Color.black);
		btnConfirm.setBounds(170, 197, 105, 27);
		contentPane.add(btnConfirm);
		
		
	}

//	private Color Color(int i, int j, int k) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public void setMain(Client client) {
		this.getInfo = client;
		
	}


}
