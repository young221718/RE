package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

public class SecurityQnA extends JFrame {
	
	private Client QNA;

	public JPanel contentPane;
	public JTextField textAnswer;
	public JTextArea showingQ;

	public JButton QNAConf;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SecurityQnA frame = new SecurityQnA();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public SecurityQnA() {
		
		setTitle("Security Question");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);  
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel labelQ = new JLabel("Question.");
		labelQ.setBounds(56, 42, 77, 24);
		//labelQ.setFont(new Font("", Font.BOLD, 15));
		contentPane.add(labelQ);
		
		showingQ = new JTextArea(); //showing Question
		showingQ.setBounds(56, 67, 335, 33);
		contentPane.add(showingQ);
		
		
		
		JLabel labelA = new JLabel("Answer"); 
		labelA.setBounds(56, 112, 62, 24);
		//labelA.setFont(new Font("", Font.BOLD, 15));
		contentPane.add(labelA);
		
		textAnswer = new JTextField();  //input Answer
		textAnswer.setBounds(56, 137, 335, 33);
		contentPane.add(textAnswer);
		textAnswer.setColumns(10);
		
		QNAConf = new JButton("confirm");
		QNAConf.setBounds(167, 195, 105, 33);
		contentPane.add(QNAConf);
		
		
	}
	
	public void setMain(Client client) {
		this.QNA = client;
	
	}
}
