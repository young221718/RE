package MyPackage;

import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class DragNDrop extends JFrame implements DropTargetListener
{
    JLabel ta;
    DropTarget dt;

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
                java.util.List list = (java.util.List) tr.getTransferData(DataFlavor.javaFileListFlavor);
                //파일명 출력
                for(int i=0;i < list.size();i++)
                {
                    System.out.println(list.size() + "-" + list.get(i));
                }
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
    

}