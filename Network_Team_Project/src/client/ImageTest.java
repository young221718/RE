package client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ImageTest {

   static BufferedImage img = null;
   
public static void main(String[] args) {

    try {

        byte[] imageInByte;
        BufferedImage originalImage = ImageIO.read(new File("c:\\temp.png"));

        // convert BufferedImage to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", baos);
        baos.flush();
        imageInByte = baos.toByteArray();
        baos.close();
        
        img = ImageIO.read(new ByteArrayInputStream(imageInByte));

    } catch (IOException e) {
        System.out.println(e.getMessage());
       }
   
        JFrame frm = new JFrame("그림 보기 예제");
        ImageIcon ic  = new ImageIcon(img);
        JLabel lbImage1  = new JLabel(ic);
       
        frm.add(lbImage1);
        frm.setVisible(true);
        frm.setBounds(10, 10, 800, 600);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
   

   




}