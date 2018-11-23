package client;

import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class DragNDrop extends JFrame implements DropTargetListener
{
	public Client main;
    JLabel ta;
    DropTarget dt;
    java.util.List list;
    List<String> listA = new ArrayList<String>();
    int i=0,j=0;

    public DragNDrop()
    {
       ta = new JLabel();
        dt = new DropTarget(ta, DnDConstants.ACTION_COPY_OR_MOVE, this, true, null);
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde)
    {
        System.out.println("dragEnter");
    }

    @Override
    public void dragExit(DropTargetEvent dte)
    {
        System.out.println("dragExit");
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde)
    {
        System.out.println("dragOver");
    }

    @Override
    public void drop(DropTargetDropEvent dtde)
    {
        System.out.println("dragDrop");

        if ((dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0)
        {
            dtde.acceptDrop(dtde.getDropAction());
            Transferable tr = dtde.getTransferable();

            try
            {
                //파일명 얻어오기
                list = (java.util.List) tr.getTransferData(DataFlavor.javaFileListFlavor);
                String strFile = list.toString();
                listA.add(i,strFile);
                main.FileArea.append(strFile+"\n");
                
                //파일명 출력
                for(int j=0;j < listA.size();j++)
                {
                	System.out.println(listA.get(j));
                }
                i++;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde)
    {
        System.out.println("dragActionChanged");
    }
    
    public void setMain(Client main) {
        this.main = main;
    }
    

}