package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import basic.RoomInformation;


public class HostView extends JFrame {

   private Client main;
   private RoomInformation info;
      
    public JButton btnConfirm;
    private JButton btnInit;
    public JTextField userText,numJoinText,secQText,secAText,yearText,monthText,dateText;
    public JComboBox joinNum;
   
    
    /*
     frame that user use when create a Room.
     User can insert Room Information.
     Room's inforamtion
     -Room's number of people constratin
     -Security Q&A
     -Open Date
     */
    
    
    public static void main(String[] args) {
    }
 
    public HostView() {
    	
        //frame setting
        setTitle("Create R;E");
        setSize(400, 260);
        setResizable(false);
        setLocation(400, 300);
       
        
        JPanel panel = new JPanel();
        placeLoginPanel(panel);
       
        
        add(panel);
        setVisible(true);

    }
   
    public void placeLoginPanel(JPanel panel){
        panel.setLayout(null);     
        JLabel userLabel = new JLabel("그룹 명");
        userLabel.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 12));  //=========================== 글씨체 바꾸는 코드
        userLabel.setForeground(Color.black);
        userLabel.setBounds(55, 10, 80, 25);
        panel.add(userLabel);
       
        JLabel userLabel3 = new JLabel("인원 수");
        userLabel3.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 12));  //=========================== 글씨체 바꾸는 코드
        userLabel3.setForeground(Color.black);
        userLabel3.setBounds(55, 40, 80, 25);
        panel.add(userLabel3);
        
        JLabel userLabel4 = new JLabel("열릴 날짜");
        userLabel4.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 12));  //=========================== 글씨체 바꾸는 코드
        userLabel4.setForeground(Color.black);
        userLabel4.setBounds(55, 70, 80, 25);
        panel.add(userLabel4);
        
        JLabel userLabel5 = new JLabel("보안질문");
        userLabel5.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 12));  //=========================== 글씨체 바꾸는 코드
        userLabel5.setForeground(Color.black);
        userLabel5.setBounds(50, 100, 80, 25);
        panel.add(userLabel5);
        
        JLabel userLabel6= new JLabel("보안질문 답");
        userLabel6.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 12));  //=========================== 글씨체 바꾸는 코드
        userLabel6.setForeground(Color.black);
        userLabel6.setBounds(45, 130, 80, 25);
        panel.add(userLabel6);
        
        JLabel userLabel7 = new JLabel("년");
        userLabel7.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 11));  //=========================== 글씨체 바꾸는 코드
        userLabel7.setForeground(Color.black);
        userLabel7.setBounds(245, 70, 15, 25);
        panel.add(userLabel7);
        
        JLabel userLabel8= new JLabel("월");
        userLabel8.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 11));  //=========================== 글씨체 바꾸는 코드
        userLabel8.setForeground(Color.black);
        userLabel8.setBounds(295, 70, 15, 25);
        panel.add(userLabel8);
        
        JLabel userLabel9= new JLabel("일");
        userLabel9.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 11));  //=========================== 글씨체 바꾸는 코드
        userLabel9.setForeground(Color.black);
        userLabel9.setBounds(345, 70, 15, 25);
        panel.add(userLabel9);
        
        
       
        userText = new JTextField(20);
        userText.setBounds(200, 10, 160, 25);
        panel.add(userText);
        
        String[] number={"1", "2", "3", "4","5"};
        joinNum = new JComboBox(number);
        joinNum.setBounds(200,40,160,25);
        panel.add(joinNum);
        

        
        yearText = new JTextField(20);
        yearText.setBounds(200, 70, 40, 25);
        panel.add(yearText);
        
        monthText = new JTextField(20);
        monthText.setBounds(265, 70, 25, 25);
        panel.add(monthText);
        
        dateText = new JTextField(20);
        dateText.setBounds(315, 70, 25, 25);
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
               //dispose HostView Frame
            }
        });
       
        btnConfirm = new JButton("Confirm");
        btnConfirm.setBounds(230, 175, 100, 25);
        panel.add(btnConfirm);
               
    }
   
 
   
    // mainProcess와 연동
    public void setMain(Client main) {
        this.main = main;
    }
    public void setInfo(RoomInformation info) {
    	this.info = info;
    }
   
}