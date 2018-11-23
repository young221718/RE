package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import basic.RoomInformation;
 

public class LoginView extends JFrame{
	
    private Client main;
    /////////////////////////////
    private String userName;
    private String emailAdd;
    
   
    public JButton btnJoin;
    public JButton btnLogin;
    //public JPasswordField ssText;
    public JTextField emailText,passText;
    private boolean bLoginCheck;
   
    public static void main(String[] args) {
        //new LoginView();
    }
 
    public LoginView() {
        // setting
        setTitle("login");
        setSize(280, 150);
        setResizable(false);
        setBounds(100, 100, 940, 665);
       // setLocation(450, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
       
        // panel
        JPanel panel = new JPanel();
        placeLoginPanel(panel);
       
       
        // add
        add(panel);
       
        // visiible
        setVisible(true);
    }
   
    public void placeLoginPanel(JPanel panel){
        panel.setLayout(null);     
        
        JLabel HelloLabel = new JLabel("\u2605 Hello! Welcome to RE! \u2605");
        HelloLabel.setBounds(122, 112, 209, 18);
		panel.add(HelloLabel);
        
        JLabel passLabel = new JLabel("E-mail");
        passLabel.setBounds(122, 163, 62, 18);
        panel.add(passLabel);
        
        emailText = new JTextField();
        emailText.setBounds(122, 193, 181, 24);
        panel.add(emailText);
        emailText.setColumns(10);
        
        
        JLabel PassLabel = new JLabel("Password");
        PassLabel.setBounds(122, 229, 90, 18);
        panel.add(PassLabel);
       
        
        passText = new JTextField();
        passText.setBounds(122, 260, 181, 24);
        panel.add(passText);
		passText.setColumns(10);
        
        
		btnJoin = new JButton("JOIN");
		btnJoin.setBounds(122, 311, 79, 27);
		panel.add(btnJoin);
		
		
      
       
        btnLogin = new JButton("LOGIN");
        btnLogin.setBounds(226, 311, 75, 27);
        panel.add(btnLogin);
        
//        btnLogin.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//               //confirm 눌렀을때 액션 들어가는 부분, 이 버튼의 액션리스너를 통해 인풋값이 전송되고 소켓이 연결될예정
//               //각각의 텍스트 Area에 입력된 값을 받아올경우엔 .getText() 등을 사용한다.(ex userText.getText()  )
//               main.disposeLogin();
//            }
//        });
    }
   
   
    // mainProcess와 연동
    public void setMain(Client main) {
        this.main = main;
    }
   
}