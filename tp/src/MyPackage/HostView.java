package MyPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ComboBoxEditor;

public class HostView extends JFrame {

   private Client main;
      
    private JButton btnConfirm;
    private JButton btnInit;
    private JTextField userText,numJoinText,secQText,secAText,dateText;
   
    public static void main(String[] args) {
        //new LoginView();
    }
 
    public HostView() {
        // setting
        setTitle("Create R;E");
        setSize(400, 260);
        setResizable(false);
        setLocation(400, 300);
       // setDefaultCloseOperation(EXIT_ON_CLOSE);
       
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
        JLabel userLabel = new JLabel("그룹 명");
        userLabel.setBounds(55, 10, 80, 25);
        panel.add(userLabel);
       
        JLabel userLabel3 = new JLabel("인원 수");
        userLabel3.setBounds(55, 40, 80, 25);
        panel.add(userLabel3);
        
        JLabel userLabel4 = new JLabel("열릴 날짜");
        userLabel4.setBounds(55, 70, 80, 25);
        panel.add(userLabel4);
        
        JLabel userLabel5 = new JLabel("보안질문");
        userLabel5.setBounds(50, 100, 80, 25);
        panel.add(userLabel5);
        
        JLabel userLabel6= new JLabel("보안질문 답");
        userLabel6.setBounds(45, 130, 80, 25);
        panel.add(userLabel6);
        
        
       
        userText = new JTextField(20);
        userText.setBounds(200, 10, 160, 25);
        panel.add(userText);
       
        /*numJoinText = new JTextField(20);
        numJoinText.setBounds(200, 40, 160, 25);
        panel.add(numJoinText);*/
        
        String[] number={"1", "2", "3", "4","5"};
        JComboBox joinNum = new JComboBox(number);
        joinNum.setBounds(200,40,160,25);
        panel.add(joinNum);
        
        
        
        
        dateText = new JTextField(20);
        dateText.setBounds(200, 70, 160, 25);
        panel.add(dateText);
        
        secQText = new JTextField(20);
        secQText.setBounds(200, 100, 160, 25);
        panel.add(secQText);
        
        secAText = new JTextField(20);
        secAText.setBounds(200, 130, 160, 25);
        panel.add(secAText);
        secAText.addActionListener(new ActionListener() {          
            @Override
            public void actionPerformed(ActionEvent e) {
               main.disposeHost();
            }
        });
        
        
       
        btnInit = new JButton("Close");
        btnInit.setBounds(70, 175, 100, 25);
        panel.add(btnInit);
        btnInit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               main.disposeHost();
            }
        });
       
        btnConfirm = new JButton("Confirm");
        btnConfirm.setBounds(230, 175, 100, 25);
        panel.add(btnConfirm);
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               //confirm 눌렀을때 액션 들어가는 부분, 이 버튼의 액션리스너를 통해 인풋값이 전송될 예정
               //각각의 텍스트 Area에 입력된 값을 받아올경우엔 .getText() 등을 사용한다.(ex secQText.getText)
               // combo박스 (인원수)를 받아올때는 joinNum.getSelectedItem() 이 사용
               
            	btnConfirm.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						
					}
            	});
               
               main.disposeHost();// 현재 confirm 눌렀을때 창 사라지는 disposeHost가 등록되어있다. 이위에다 전송하는 코드 넣어야함
               
            
            }
        });
    }
   
 
   
    // mainProcess와 연동
    public void setMain(Client main) {
        this.main = main;
    }
   
}