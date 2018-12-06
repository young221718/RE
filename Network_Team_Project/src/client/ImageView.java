package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageView extends JFrame{

    JLabel Image1;
    JButton btnLeft;
    JButton btnRight;
    JButton btnDownload;
    
    static BufferedImage img = null;
    byte[][] imageInByte;
	
	public ImageView() {
        // setting
        setTitle("R");
        setSize(450, 500);
        setResizable(false);
        setBounds(200, 200, 700, 500); //왼쪽, 위, 가로, 세로
       
        // panel
        JPanel panel = new JPanel();
        placeImagePanel(panel);
        // add
        add(panel);
       
        // visible
        setVisible(true);
    }
	
	
	public void placeImagePanel(JPanel panel)
	{
		panel.setLayout(null); 
		ImageIcon ic  = new ImageIcon("R.png");
		Image1 = new JLabel(ic);
		Image1.setBounds(210,30,300,350);
		panel.add(Image1);
		
		btnLeft = new JButton("<-");
		btnLeft.setBounds(60,160, 50, 50);
		panel.add(btnLeft);
		btnLeft.addActionListener(new ActionListener() {          
            @Override
            public void actionPerformed(ActionEvent e) {
               
            }
        });
		
		btnRight = new JButton("->");
		btnRight.setBounds(610,160,50,50);
		panel.add(btnRight);
		btnRight.addActionListener(new ActionListener() {          
            @Override
            public void actionPerformed(ActionEvent e) {
               
            }
        });
		
		btnDownload = new JButton("Download");
		btnDownload.setBounds(320,390,100,40);
		panel.add(btnDownload);
		
	}
	
	public static void main(String[] args) {
	}

}