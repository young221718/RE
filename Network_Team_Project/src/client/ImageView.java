package client;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.io.ByteArrayInputStream;


/*
 Frame that user use for open R
 User can check a File, and download a File
 
 Left Button, Right Button, Download button, image1 Label is exist
 */



public class ImageView extends JFrame{

   int index=0;
    JLabel Image1;
    JButton btnLeft;
    JButton btnRight;
    JButton btnDownload;
    public ImageIcon ic;
    Image imgC;
    
    
    static BufferedImage img = null;
    byte[][] imageInByte; 
    /*
     imageInByte[file_num][] 
     Server's Byte Stream data save in this Byte array
     */
    
   
   public ImageView() {
        // setting
	   Color backColor = new Color(255,255,255);
	   
	  
	   
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
        panel.setBackground(backColor);
    }
   
   
   public void placeImagePanel(JPanel panel)
   {
	   /*
	    ic = image icon that bufferedimage will be seen
	    Image1 JLabel = JLabel is contatin ic, placed in frame's middle
	    */
	   
      panel.setLayout(null); 
      ic  = new ImageIcon("R.png");
      Image1 = new JLabel(ic);
      Image1.setBounds(210,30,300,370);
      panel.add(Image1);
      
      
      /*
       when it clicked.
       image will be change previous photo
       */
      ImageIcon icleft = new ImageIcon("left.png");
      btnLeft = new JButton(icleft);
      btnLeft.setBounds(40,160, 70, 90);
      btnLeft.setBorderPainted(false);
      panel.add(btnLeft);
      btnLeft.addActionListener(new ActionListener() {          
            @Override
            public void actionPerformed(ActionEvent e) {
               
               if(index==0)
               {
                  try {
                     index = imageInByte.length-1;
                     img = ImageIO.read(new ByteArrayInputStream(imageInByte[index]));
                     imgC = img.getScaledInstance(300, 370, java.awt.Image.SCALE_SMOOTH);
                     
                  } catch (IOException e1) {
                     e1.printStackTrace();
                  }
                  ic.setImage(imgC);
                  Image1.setIcon(ic);
               Image1.setVisible(false);
               Image1.setVisible(true);
               }
               else {
                  try {
                     index--;
                  img = ImageIO.read(new ByteArrayInputStream(imageInByte[index]));
                  imgC = img.getScaledInstance(300, 370, java.awt.Image.SCALE_SMOOTH);
               } catch (IOException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               }
                  ic.setImage(imgC);
                  Image1.setIcon(ic);
               Image1.setVisible(false);
               Image1.setVisible(true);
              
               }
               
               
            }
        });
      
      
      
      /*
       when it clicked,
       image will be change to next photo
       */
      ImageIcon right = new ImageIcon("right.png");
      btnRight = new JButton(right);
      btnRight.setBounds(590,160,70,85);
      btnRight.setBorderPainted(false);
      panel.add(btnRight);
      btnRight.addActionListener(new ActionListener() {          
            @Override
            public void actionPerformed(ActionEvent e) {
               if(index==imageInByte.length-1)
               {
                  try {
                     index = 0;
                     img = ImageIO.read(new ByteArrayInputStream(imageInByte[index]));
                     imgC = img.getScaledInstance(300, 370, java.awt.Image.SCALE_SMOOTH);
                     
                  } catch (IOException e1) {
                     // TODO Auto-generated catch block
                     e1.printStackTrace();
                  }
                  ic.setImage(imgC);
                  Image1.setIcon(ic);
               Image1.setVisible(false);
               Image1.setVisible(true);
               }
               
               else {
                  try {
                     index++;
                  img = ImageIO.read(new ByteArrayInputStream(imageInByte[index]));
                  imgC = img.getScaledInstance(300, 370, java.awt.Image.SCALE_SMOOTH);
                  
                  
               } catch (IOException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               }
                  
                  ic.setImage(imgC);
               Image1.setIcon(ic);
               Image1.setVisible(false);
               Image1.setVisible(true);
               System.out.println("work"+imageInByte.length+index);
               }
               
            }
        });
      
      
      
      /*
       Download button
       Download all of File, in "C:\\RE\\Download"
       */
      btnDownload = new JButton("Download");
      btnDownload.setBounds(320,410,100,40);
      Color yellow = new Color(255,220,108);
      btnDownload.setBackground(yellow);
      panel.add(btnDownload);
      btnDownload.addActionListener(new ActionListener() {          
            @Override
            public void actionPerformed(ActionEvent e) {
               
               for(int i=0;i<imageInByte.length;i++)
               {
                 try {
                       File f = new File("C:\\RE\\Download"); //save path
                    if (f.exists() == false)
                       f.mkdirs();
                    Path path = Paths.get(f +"\\"+i+Client.ExtArr[i]);
                    Files.write(path,imageInByte[i]);
                 
                    //ImageIO.write(img,Client.ExtArr[i], new File(f+"\\"+i+Client.ExtArr[i]));
            } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
               }
               
               
            }
       });
      
   }
   
   public static void main(String[] args) {
      
   }

}