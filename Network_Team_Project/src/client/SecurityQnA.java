package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * When the user sends the room number and the room number is correct, the
 * security question window appears. This is the security question GUI part that
 * appears.
 * 
 * @author Á¶ÇýÁø
 *
 **/

public class SecurityQnA extends JFrame {

	private Client QNA;

	public JPanel contentPane;
	public JTextField textAnswer;
	public JTextArea showingQ;
	public JButton QNAConf;

	// Implement frame SecurityQnA
	public static void main(String[] args) { 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SecurityQnA frame = new SecurityQnA();
					frame.setVisible(true);  //Make the SecurityQnA visible
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SecurityQnA() {
		
		Color backColor = new Color(236,223,207);  //Set the background color of the frame
		
		setTitle("Security Question");  //Set title
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);  //set frame size and position
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(backColor);  //Set the background color of the frame
		setContentPane(contentPane);  
		contentPane.setLayout(null);

		JLabel labelQ = new JLabel("Question.");
		labelQ.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 15));  //set font
		labelQ.setBounds(56, 42, 77, 24);  //set 'labelQ'JLabel size and position
		contentPane.add(labelQ);  //Add labelQ to ContentPane

		showingQ = new JTextArea(); // showing Question
		showingQ.setBounds(56, 67, 335, 33);  //set 'showingQ'JTextArea size and position
		contentPane.add(showingQ);  //Add showingQ to ContentPane

		JLabel labelA = new JLabel("Answer");
		labelA.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 15)); //set font
		labelA.setBounds(56, 112, 62, 24);  //set 'labelA'JLabel size and position
		contentPane.add(labelA);  //Add labelA to ContentPane

		textAnswer = new JTextField(); // input Answer
		textAnswer.setBounds(56, 137, 335, 33);  //set 'textAnswer'JTextField size and position
		contentPane.add(textAnswer);  //Add textAnswer to ContentPane
		textAnswer.setColumns(10);  //Set JTextField's columns size

		QNAConf = new JButton("confirm");  //Create confirm button 
		QNAConf.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 15));  //set font
		QNAConf.setBounds(167, 195, 105, 33);  //set 'QNAConf'button size and position
		contentPane.add(QNAConf);   //Add QNAConf to ContentPane

	}
	
	//Method to connect to client
	public void setMain(Client client) {  
		this.QNA = client;

	}
}
