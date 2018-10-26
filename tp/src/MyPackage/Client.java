package MyPackage;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;



public class Client extends JFrame {
	
   LoginView loginView;
   HostView hostView;
   BufferedReader in;
    PrintWriter out;
 
    static Client frame = new Client();
    
       JPanel contentPane;
       JTextField txtPhinNum;
       JTextField textField;

        
   public Client(){
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setBounds(100, 100, 940, 665);
        
        
         
         contentPane = new JPanel();
         contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
              setContentPane(contentPane);
              contentPane.setLayout(null);
              
              
              JLabel img = new JLabel();
              img.setIcon(new ImageIcon("R.PNG"));
              img.setBounds(70, 20, 381, 434);
      		  contentPane.add(img);
              
              
              JButton btnEntrance = new JButton("ENTRANCE");
              btnEntrance.setBounds(558, 67, 106, 38);
              contentPane.add(btnEntrance);
              
              txtPhinNum = new JTextField();
              txtPhinNum.setBounds(676, 68, 232, 37);
              txtPhinNum.setText("PHIN NUM");
              contentPane.add(txtPhinNum);
              txtPhinNum.setColumns(10);
              
              JButton btnRoom = new JButton("ROOM");
              btnRoom.setBounds(558, 20, 79, 40);
              btnRoom.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent arg0) {
                	 getHostInfo();
                 }
              });
              contentPane.add(btnRoom);
              
              textField = new JTextField();
              textField.setBounds(558, 573, 350, 33);
              contentPane.add(textField);
              textField.setColumns(10);
              
              JTextArea textArea = new JTextArea();
              textArea.setEditable(false);
              textArea.setBounds(558, 117, 350, 444);
              contentPane.add(textArea);
              
              JTextArea txtrPn = new JTextArea();
              txtrPn.setEditable(false);
              txtrPn.setText("PN");
              txtrPn.setBounds(645, 20, 263, 38);
              contentPane.add(txtrPn);
           }
           

   
      
      



   
   
   
   private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "prototype of R:E",
            JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Prompt for and return the desired screen name.
     */
   
   
    private void getUserInfo() {
       Client main = new Client();
        main.loginView = new LoginView(); // 로그인창 보이기
        main.loginView.setMain(main);

    }
    
    public void disposeLogin(){
        loginView.dispose(); // 로그인창닫기
    }
    
    
    
    //호스트 버튼 눌렀을 시 실행될 메소드
    private void getHostInfo() {
       Client main = new Client();
       main.hostView = new HostView();
       main.hostView.setMain(main);
       
       
    }
    public void disposeHost() {
       hostView.dispose();
    }
    
    
   
 
   private void run() throws IOException {
      
      //유저로부터 인풋받기
      //서버와의 통신담당
      
      
      
      String serverAddress = getServerAddress();
      getUserInfo();
        Socket socket = new Socket(serverAddress, 9001);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
   
      //이 밑으로는 프로톨콜 코드 필요
      
      
   }
   
   public static void main(String[] args) throws Exception {
		frame.setVisible(true);
      
      Client R_E = new Client();
      R_E.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        R_E.frame.setVisible(true);
        R_E.run();

      
   }
}