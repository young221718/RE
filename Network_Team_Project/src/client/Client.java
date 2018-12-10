package client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.LineNumberInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.io.DataOutputStream;

import basic.Chat;
import basic.RoomInformation;
import client.JoinView;

public class Client extends JFrame {

	ObjectInputStream in;
	ObjectOutputStream out;
	Socket socket; // waitingRoom socket
	ImageView imageView;
	// String serverAddress = getServerAddress();
	String serverAddress = "192.168.1.136";

	ObjectInputStream inChat;
	ObjectOutputStream outChat;
	Socket chatSocket; // chattingRomm socket

	BufferedOutputStream toServer;
	ObjectInputStream inFile;
	Socket fileSocket;  //fileRoom socket
	FileInputStream fis;
	BufferedInputStream bis;
	DataOutputStream dos;
	
	/*BufferedOutputStream toBuffer;
	BufferedInputStream fromBuffer;
	DataInputStream Dis;
	ObjectOutputStream os;
	Socket filesenderSocket;*/
	
	Socket filesenderSocket;
	BufferedInputStream Bis;
	DataInputStream Dis;
	ObjectOutputStream Oos;
	
	Integer roomNum;
	int valueQNA;
	
	PrintWriter OUT; // 유저가 문장을 입력하는 부분에 사용됨
	LoginView loginView;
	JoinView joinView;
	HostView hostView;
	RoomInformation info;
	SecurityQnA securityQnA;
	// UserInfomation data;
	String emailAdd;
	JPasswordField passWord;
	
	
	
	/*채팅에서 이름표시를 위해 받아옴(이메일은 동명이인 있을 경우를 대비 --> 슈퍼키로 사용)*/
	Chat myChat = new Chat();    //유저가 서버에게 보낼  이메일, 이름, 메세지
	Chat tempChat;               //서버에게 받는 유저의  이메일, 이름, 메세지
	
	EmailCheck mailCheck; //메일의 유효성 검사를 위해 선언
	
	JPanel contentPane;
	JTextField txtPinNum;
	JTextField textField;
	JTextArea txtrPn;
	JTextArea textArea; // 채팅내용 보여지는 곳
	JTextArea FileArea;
	DragNDrop egg;
	JButton btnSending;
	JButton btnOpen;

	ImageIcon img = null;
	
	public Client() {
		info = new RoomInformation();
		// data = new UserInfomation();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 940, 665);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		

		this.egg = new DragNDrop();
		this.egg.ta.setIcon(new ImageIcon("R.PNG"));
		this.egg.ta.setBounds(70, 20, 381, 434);
		this.contentPane.add(egg.ta);
		this.egg.setMain(this);

		FileArea = new JTextArea();// 파일명 보여지는 부분
		FileArea.setEditable(false);
		FileArea.setBounds(39, 493, 475, 113);
		JScrollPane scroll = new JScrollPane(FileArea);
		scroll.setBounds(39, 493, 475, 113);
		contentPane.add(scroll);
		
		btnOpen = new JButton("Open");
		btnOpen.setBounds(218, 250, 106, 27);
		contentPane.add(btnOpen);
		btnOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//서버한테 물어봐서 이미지 오는거 허락되면
				//ImageView 실행
		
				
				getImage();
				
				
				try {
					
					Oos.writeInt(31);
					Oos.flush();
					int file_num = Bis.read();//그룹 폴더에 저장되어 있는 파일 갯수 
					System.out.println("file_num: " + file_num);
					//imageView.imageInByte = new byte[imageView.file_num][]; 
					imageView.imageInByte = new byte[file_num][];
					System.out.println("debug1");
					int sign=0;
					int j=0;
					do {
						
						int pro = Bis.read();
						System.out.println("sender_pro ");
						if (pro == 101) {
							Oos.writeInt(13);
							Oos.flush();
							int len = Dis.readInt();
							
							System.out.println(len + " received");

							
								imageView.imageInByte[j] = new byte[len];
								for (int i = 0; i < len; i++) {
									imageView.imageInByte[j][i] = (byte)Bis.read();
								}
								//imageView.imageInByte[j] = buffer[j];
								j++;
							
							sign = Dis.read();
							System.out.println(sign);
							//a++;
							//outFile.flush();
							//outFile.close();
							//Bis.flush();
							//Dis.close();
						}
					} while (sign != 8);
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		
	});

		/*
		 * contentPane.add(new JScrollPane(textArea_1)); textArea_1.setEditable(false);
		 */ // 파일명 나열_스크롤 만드는 부분

		btnSending = new JButton("Sending"); // 파일 전송 버튼
		btnSending.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 15));
		btnSending.setBounds(218, 446, 106, 27);
		contentPane.add(btnSending);
		btnSending.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
				
				run();

			}

		});
	
		JButton btnEntrance = new JButton("ENTRANCE");// 핀번호가 맞으면(TODO**맞는지 확인 : 보안질문으로??) -> 채팅방 입장
		btnEntrance.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 13));
		btnEntrance.setBounds(558, 67, 106, 38);
		btnEntrance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					out.writeInt(222); // 채팅방에 들어가겠다는 신호
					out.flush();
					roomNum = Integer.parseInt(txtPinNum.getText()); // user가 입력한 방번호 (String-->Integer)
					out.writeObject(roomNum); // 서버에게 방번호를 보내주는 부분
					out.flush();
					
					getQnA();
					
					String severQ = (String)in.readObject();
					System.out.println(severQ);
					System.out.println(in.readInt());
		
					
					securityQnA.showingQ.append(severQ);
					
					
					
					
					

					/*
					 * fileSocket = new Socket(serverAddress, roomNum+1); // 소켓생성과 서버의 IP받기 inFile =
					 * new ObjectInputStream(fileSocket.getInputStream()); outFile = new
					 * BufferedOutputStream(fileSocket.getOutputStream());
					 */
				} catch (IOException | ClassNotFoundException e) {

					e.printStackTrace();
				}

			}

		});
		contentPane.add(btnEntrance);

		txtPinNum = new JTextField();
		txtPinNum.setBounds(676, 68, 232, 37);
		txtPinNum.setText("Input Pin Number"); // Pin번호 입력하는 부분
		contentPane.add(txtPinNum);
		txtPinNum.setColumns(10);
		

		JButton btnRoom = new JButton("ROOM"); // 방 만들기 버튼
		btnRoom.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 13));
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

		textArea = new JTextArea(); // 채팅이 보여지는 부분
		textArea.setEditable(false);
		textArea.setBounds(558, 117, 350, 444);
		JScrollPane scrollArea = new JScrollPane(textArea);
		
		scrollArea.setBounds(558, 117, 350, 444);
		contentPane.add(scrollArea);

		textField.addActionListener(new ActionListener() {         /* 문장 입력하는 부분 */
			public void actionPerformed(ActionEvent e) {
				try {
					myChat.message = ((textField.getText()) + '\n');
					outChat.writeObject(myChat);
					outChat.flush();
					outChat.reset();
					
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				textField.setText("");
			}
		});
		
		txtrPn = new JTextArea();      //서버한테 받은 핀번호가 보여지는 부분
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
	//////////////////////////////////////////////////////////////이미지 프레임 /////////////////////////////////////
	
	private void getImage() 
	{
		this.imageView = new ImageView();
		this.imageView.setVisible(true);
	}
	
//////////////////////////////////////////////////////////////////////// 보안 질문 프레임 ////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	private void getQnA() {
		this.securityQnA = new SecurityQnA(); // 로그인창 보이기
		//System.out.println("3");
		this.securityQnA.setMain(this);
		//System.out.println("2");
		this.securityQnA.setVisible(true);
		//System.out.println("1");
		
		securityQnA.QNAConf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String enSecurityAsr = securityQnA.textAnswer.getText();
				
				try {
					out.writeObject(roomNum);
					out.flush();
					out.writeObject(enSecurityAsr);
					out.flush();
				
					valueQNA = in.readInt();
					System.out.println(valueQNA);
					
					if(valueQNA == 149)
					{
						disposeQnA();
						
						chatSocket = new Socket(serverAddress, roomNum); // 소켓생성과 서버의 IP받기
						outChat = new ObjectOutputStream(chatSocket.getOutputStream());
						inChat = new ObjectInputStream(chatSocket.getInputStream());

						
						// 파일 소켓 연결
						
						if(in.readBoolean())
						{
							filesenderSocket = new Socket(serverAddress, roomNum + 1);
							Bis = new BufferedInputStream(filesenderSocket.getInputStream());
							Dis = new DataInputStream(Bis);
							Oos = new ObjectOutputStream(filesenderSocket.getOutputStream());
							//Bos = new BufferedOutputStream;
							
							
							/*filesenderSocket = new Socket(serverAddress, roomNum + 1);
							fromBuffer = new BufferedInputStream(filesenderSocket.getInputStream());
							Dis = new DataInputStream(fromBuffer);
							os = new ObjectOutputStream(filesenderSocket.getOutputStream());*/
						} else {
							fileSocket = new Socket(serverAddress, roomNum + 1);
							toServer = new BufferedOutputStream(fileSocket.getOutputStream());
							dos = new DataOutputStream(fileSocket.getOutputStream());
						}
						//toBuffer = new BufferedOutputStream(File);
						//toBuffer = new BufferedOutputStream(filesenderSocket.getOutputStream());
						//BufferedOutputStream toBuffer;
						//BufferedInputStream inBuffer;
						// TODO 쓰레드 끝내기
						new ChatThread().start(); // 채팅쓰레드 실행
						
					}
								
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
			
		});
	
	}
	
//========================================================================= 로그인창 프레임 ===================================================================================//
	
	private void getUserInfo() {
		this.loginView = new LoginView(); 
		this.loginView.setMain(this);
		// this.loginView.setData(userName, emailAdd);
		loginView.btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				

				emailAdd = loginView.emailText.getText();
			
				//passWord = loginView.passText.getPassword(); 
			
				String pw = "";
				char[] pwOrigin = loginView.passText.getPassword();
				
				for(char c : pwOrigin)
				{
					pw += c;
					System.out.println(c);
					System.out.println(pw);
				}

				try {
					out.writeInt(180);
					out.writeObject(emailAdd);
					out.writeObject(pw);
					out.flush();
					
					int value = in.readInt();
					System.out.println(value);
					
					
					if(value == 181)
					{
						myChat.email = (String)in.readObject();
						myChat.name = (String)in.readObject();
						disposeLogin();
					}
					else if(value == 183)
					{
						//버튼 누르기, 다시보내주기
						System.out.println("SQL-error : 183");
					}
					else if(value == 185)
					{
						//잘못된 비밀번호
						System.out.println("185 : 비밀번호 불일치!");
					}
					else if(value == 187)
					{
						//존재하지 않는 이메일
						System.out.println("187 : 가입되지 않은 이메일!");
					}
	
				} catch (IOException e1) {
				
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});

		loginView.btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getJoinInfo();
			}
		});

	}
	
//==================================================================== 회원가입 프레임 =======================================================================================//
	
	private void getJoinInfo() {                           
		this.joinView = new JoinView();
		this.joinView.setMain(this);
		this.joinView.setVisible(true);

		joinView.btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = joinView.JoinName.getText();
				String email = joinView.JoinEmailAdd.getText();
				/**/
				//if()
				
				/**/
				
				String pw1 = joinView.Joinpass.getText();
				String pw2 = joinView.JoinCheck.getText();

				if (pw1.equals(pw2)) { // 비밀번호 체크하는 부분(비밀번호 & 비밀번호 체크)
					
					try {
						out.writeInt(170);
						out.writeObject(email);
						out.writeObject(name);
						out.writeObject(pw1);
						out.flush();
						
						int value = in.readInt();
						System.out.println(value);
						
						if(value == 171)
						{
							//회원가입 성공
							disposeJoin();
						}
						else if(value == 175)
						{
							//이미 존재하는 아이디
							System.out.println("175 : 이미 가입된 이메일");
						}
						else if(value == 179)
						{
							//버튼 다시 누르기
							System.out.println("179 : SQL-error");
						}

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
						
					disposeJoin();
					
				} else { // 비번 불일치
					System.out.println("Do not match!");

				}

			}
		});
		// 확인 버튼 누르면 비번확인--> 서버에게 정보 전달 --> 확인받으면 창닫기/ 못 받으면 '에러'메시치 출력 후 재입력 받기
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void disposeLogin() {  // 로그인창닫기
		loginView.dispose(); 
	}
	
	public void disposeJoin() {  // 회원가입 창닫기
		joinView.dispose(); 
	}
	
	public void disposeHost() {  // 방 생성을 위한 정보 입력창
		hostView.dispose();
	}
	
	public void disposeQnA() {  // 로그인창닫기
		securityQnA.dispose(); 
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
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

				info.endDate = Calendar.getInstance();
				info.endDate.set(Calendar.YEAR, Integer.parseInt(hostView.yearText.getText()));
				info.endDate.set(Calendar.MONTH, Integer.parseInt(hostView.monthText.getText()));
				info.endDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(hostView.dateText.getText()));

				System.out.println("Year : " + info.endDate.get(Calendar.YEAR));
				System.out.println("Month : " + info.endDate.get(Calendar.MONTH));

				try {
					out.writeInt(111);
					out.flush();
					System.out.println("I send 111");
					out.writeObject(info);
					out.flush();

					Integer PinNumber = (Integer) in.readObject();
					String PIN = String.valueOf(PinNumber); // 방번호 저장
					System.out.println("받은 번호 : " + PIN);
					txtrPn.append(PIN); // 방번호를 보여주는 부분

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


	private void ConnectSocket() throws IOException {

		// 유저로부터 인풋받기
		// 서버와의 통신담당

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
		System.out.println("after get user infor");

		// 이 밑으로는 프로토콜 코드 필요
	}

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client REclient = new Client();
					
					ImageIcon back  = new ImageIcon("wood2.PNG");  //배경이미지
				    JLabel imgLabel  = new JLabel(back);
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
	
//================================================================= 채팅 쓰레드  ============================================================================================//
	
	public class ChatThread extends Thread { 
		public void run() { 
			try {
				while (true) {
					
					tempChat = (Chat)inChat.readObject();
					textArea.append(tempChat.getMessage(myChat.email)); // 채팅창에 출력 --> <유저 : 메세지>
					textArea.setCaretPosition(textArea.getDocument().getLength()); //자동 스크롤	

				}
				
			} catch (ClassNotFoundException | IOException e) {

			}
		}
	}

	
//	public class FileThread extends Thread {
		public void run() {
			int sign = 66;
			try {
				for(int i=0; i<egg.listA.size(); i++)
				 {
				   toServer.write(77);
				   toServer.flush();
				
					//프로토콜
				   System.out.println(egg.listA.get(i));
				   File f = new File(egg.listA.get(i));
				   fis = new FileInputStream(f);
				   bis = new BufferedInputStream(fis);
				   System.out.println(f.length() + "rec");
				   dos.writeInt(bis.available());
				   int ch =0;

				   while ((ch = bis.read()) != -1 ) {
					   toServer.write(ch);
	               }
	               System.out.println("end\n");
	               toServer.flush();
	               
	               if(i==egg.listA.size()-1)
	            	   sign=99;
	               dos.write(sign);       
                }
				fis.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	//}
}
