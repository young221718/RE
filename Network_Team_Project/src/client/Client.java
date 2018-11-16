package client;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import basic.RoomInformation;


public class Client extends JFrame {

	// protected static final String pinNumber = null;
	ObjectInputStream in;
	ObjectOutputStream out;
	Socket socket;

	PrintWriter OUT; // 유저가 문장을 입력하는 부분에 사용됨
	LoginView loginView;
	HostView hostView;
	RoomInformation info;

	JPanel contentPane;
	JTextField txtPinNum;
	JTextField textField;

	public Client() {
		info = new RoomInformation();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 940, 665);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		DragNDrop egg = new DragNDrop();
		egg.ta.setIcon(new ImageIcon("R.PNG"));
		egg.ta.setBounds(70, 20, 381, 434);
		contentPane.add(egg.ta);

		JTextArea textArea_1 = new JTextArea(); // 채팅내용 보여지는 부분
		textArea_1.setBounds(39, 493, 475, 113);
		contentPane.add(textArea_1);
		/*
		 * contentPane.add(new JScrollPane(textArea_1)); textArea_1.setEditable(false);
		 */ // 파일명 나열_스크롤 만드는 부분

		JButton btnSending = new JButton("Sending"); // 파일 전송 버튼
		btnSending.setBounds(218, 446, 106, 27);
		contentPane.add(btnSending);

		JButton btnEntrance = new JButton("ENTRANCE");// Pin번호가 맞으면-> 채팅방 입장
		btnEntrance.setBounds(558, 67, 106, 38);
		btnEntrance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 흐음......... 채팅방 들어가는 부분
				// ???????
			}

		});
		contentPane.add(btnEntrance);

		txtPinNum = new JTextField();
		txtPinNum.setBounds(676, 68, 232, 37);
		txtPinNum.setText("Input Pin Number"); // Pin번호 입력하는 부분
		contentPane.add(txtPinNum);
		txtPinNum.setColumns(10);

		JButton btnRoom = new JButton("ROOM"); // 방 만들기 버튼
		btnRoom.setBounds(558, 20, 79, 40);
		btnRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getHostInfo(); // 방 만들기_팝업창

			}
		});

		contentPane.add(btnRoom);

		textField = new JTextField();
		textField.setBounds(558, 573, 350, 33);
		contentPane.add(textField);
		textField.setColumns(10);

		JTextArea textArea = new JTextArea(); // 채팅이 보여지는 부분
		textArea.setEditable(false);
		textArea.setBounds(558, 117, 350, 444);
		contentPane.add(textArea);

		/*
		 * ontentPane.add(new JScrollPane(textArea_1)); textArea.setEditable(false);
		 * 
		 * JScrollPane scrollPane1 = new JScrollPane(textArea);
		 * contentPane.add(scrollPane1);
		 */ // 스크롤 만드는 부분 -> 마지막에 자동 스크롤로 바꾸기

		textField.addActionListener(new ActionListener() { /* 문장 입력하는 부분 */
			public void actionPerformed(ActionEvent e) {
				OUT.println(textField.getText());
				textField.setText("");
			}
		});

		JTextArea txtrPn = new JTextArea();
		txtrPn.setEditable(false);
		// txtrPn.setText("Showing Pin Number");
		txtrPn.setBounds(645, 20, 263, 38);
		contentPane.add(txtrPn);
	}

	private String getServerAddress() {
		return JOptionPane.showInputDialog(this, "Enter IP Address of the Server:", "prototype of R:E",
				JOptionPane.QUESTION_MESSAGE);
	}

	/**
	 * Prompt for and return the desired screen name.
	 */

	private void getUserInfo() {
		this.loginView = new LoginView(); // 로그인창 보이기
		this.loginView.setMain(this);
	}

	public void disposeLogin() {
		loginView.dispose(); // 로그인창닫기
	}

	// 호스트 버튼 눌렀을 시 실행될 메소드
	private void getHostInfo() {
		this.hostView = new HostView();
		this.hostView.setMain(this);
		this.hostView.setInfo(info);
		hostView.btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// confirm 눌렀을때 액션 들어가는 부분, 이 버튼의 액션리스너를 통해 인풋값이 전송되고 소켓이 연결될예정
				// 각각의 텍스트 Area에 입력된 값을 받아올경우엔 .getText() 등을 사용한다.(ex userText.getText() )
				info.groupName = hostView.userText.getText();
				info.securityQuestion = hostView.secQText.getText();
				info.securityAnswer = hostView.secAText.getText();
				info.howManyPeople = hostView.joinNum.getSelectedIndex() + 1;
				// info.endDate.set(Integer.parseInt(hostView.yearText.getText()),Integer.parseInt(hostView.monthText.getText()),Integer.parseInt(hostView.dateText.getText()));
				// Calendar cal= Calendar.getInstance();

				try {
					out.writeInt(111);
					out.flush();
					System.out.println("I send 111");
					out.writeObject(info);
					out.flush();

					Object pinNumber = in.readObject();
					// Client.Client().txtrPn.setText(pinNumber);

				} catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				disposeHost();

			}
		});

	}
	/*
	 * protected static Object Client() {
	 * 
	 * return null; }
	 */

	public void disposeHost() {
		hostView.dispose();
	}

	private void ConnectSocket() throws IOException {

		// 유저로부터 인풋받기
		// 서버와의 통신담당

		String serverAddress = getServerAddress(); // 서버의 주소 담을 변수
		socket = new Socket(serverAddress, 1234); // 소켓생성과 서버의 IP받기
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		
		System.out.println("Connected!");

		/*
		 * while (true) { String line = in.readLine(); if
		 * (line.startsWith("SUBMITNAME")) { //SUBMITNAME이름을 입력받았을때
		 * out.println(getName()); } else if(line.startsWith("Entry")){ //사람이 입장하면 입장을
		 * 알리는 부분 textArea_1.append(line.substring(5) + "\n"); } else if
		 * (line.startsWith("NAMEACCEPTED")) { textField.setEditable(true); } else if
		 * (line.startsWith("MESSAGE")) { //사람들의 MESSAGE를 모두에게 출력하라는 명령을 받는 부분
		 * textArea_1.append(line.substring(8) + "\n"); } else
		 * if(line.startsWith("Exit")){ //사람이 퇴장하면 퇴장을 알리는 부분
		 * textArea_1.append(line.substring(4) + "\n"); } }
		 */

		getUserInfo();

		// 이 밑으로는 프로토콜 코드 필요
	}

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client REclient = new Client();
					REclient.setVisible(true);
					REclient.ConnectSocket();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}