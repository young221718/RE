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
 
public class LoginView extends JFrame{
    private Client main;
   
    private JButton btnLogin;
    private JButton btnInit;
    private JPasswordField passText;
    private JTextField userText,emailText;
    private boolean bLoginCheck;
   
    public static void main(String[] args) {
        //new LoginView();
    }
 
    public LoginView() {
        // setting
        setTitle("login");
        setSize(280, 150);
        setResizable(false);
        setLocation(450, 350);
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
        JLabel userLabel = new JLabel("Name");
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);
       
        JLabel passLabel = new JLabel("E-mail");
        passLabel.setBounds(10, 40, 80, 25);
        panel.add(passLabel);
       
        userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        panel.add(userText);
       
        emailText = new JTextField(20);
        emailText.setBounds(100, 40, 160, 25);
        panel.add(emailText);
        emailText.addActionListener(new ActionListener() {          
            @Override
            public void actionPerformed(ActionEvent e) {
               main.disposeLogin();       
            }
        });
       
        btnInit = new JButton("Reset");
        btnInit.setBounds(10, 80, 100, 25);
        panel.add(btnInit);
        btnInit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userText.setText("");
                emailText.setText("");
            }
        });
       
        btnLogin = new JButton("Login");
        btnLogin.setBounds(160, 80, 100, 25);
        panel.add(btnLogin);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               //confirm 눌렀을때 액션 들어가는 부분, 이 버튼의 액션리스너를 통해 인풋값이 전송되고 소켓이 연결될예정
               //각각의 텍스트 Area에 입력된 값을 받아올경우엔 .getText() 등을 사용한다.(ex userText.getText()  )
               
               
               
               main.disposeLogin();
            }
        });
    }
   
 
   
    // mainProcess와 연동
    public void setMain(Client main) {
        this.main = main;
    }
   
}