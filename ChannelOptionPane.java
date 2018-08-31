import javax.swing.*;  
import javax.swing.table.*;          
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class ChannelOptionPane extends JOptionPane{
    public ChannelOptionPane()
    {
        super();
    }

    public int setChan()
    { 
        int r = super.showConfirmDialog(null,getPanel(),"Choose or Add a Channel",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if(r==0){
           return 1;
        }
        return -1;
    }

    private JPanel getPanel() {
        JPanel panel = new JPanel();
        panel.add(CreatorDriver.getFrame().getPanel().getSS().channels());
        return panel;
    }
}