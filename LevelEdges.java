import java.util.*;
import javax.swing.*;          
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class LevelEdges implements ActionListener
{
    JFrame frame;
    static String top,right,center,left,bottom;
    ImageIcon topp,rightt,centerr,leftt,bottomm;
    JButton one = new JButton("Above");
    JLabel two= new JLabel("");
    JButton three = new JButton("Right");
    JLabel  four= new JLabel("");
    JLabel  five= new JLabel("");
    JLabel  six= new JLabel("");
    JButton seven = new JButton("Left");
    JLabel eight = new JLabel("");
    JButton nine = new JButton("Bottom");
    public LevelEdges()
    {
        frame= new JFrame("Level Edges");
        frame.getContentPane().add(createComponents(),BorderLayout.CENTER);
        frame.setSize(760,672);
        frame.setMinimumSize(new Dimension(760, 672));
        frame.addWindowListener(new Closer());
    }

    public void update()
    {
        two.setIcon(topp);
        four.setIcon(leftt);
        five.setIcon(centerr);
        six.setIcon(rightt);
        eight.setIcon(bottomm);
    }

    public static void setPlaces(String t,String r,String c,String l,String b)
    {
        top=STH.changeToUsableName(t);
        right=STH.changeToUsableName(r);
        center=STH.changeToUsableName(c);
        left=STH.changeToUsableName(l);
        bottom=STH.changeToUsableName(b);
    }

    private Component createComponents()
    {
        //setting up pane
        JPanel pane = new JPanel();
        GridLayout grid = new GridLayout(3,3);
        pane.setLayout(grid);

        //setting icons+adding label
        try
        {
            topp=new ImageIcon("Images/Screens/"+top+".png");
            leftt=new ImageIcon("Images/Screens/"+left+".png");
            centerr=new ImageIcon("Images/Screens/"+center+".png");
            rightt=new ImageIcon("Images/Screens/"+right+".png");
            bottomm=new ImageIcon("Images/Screens/"+bottom+".png");
            two.setIcon(topp);
            four.setIcon(leftt);
            five.setIcon(centerr);
            six.setIcon(rightt);
            eight.setIcon(bottomm);
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        //setting listeners
        one.addActionListener(this);
        three.addActionListener(this);
        seven.addActionListener(this);
        nine.addActionListener(this);

        //adding stuff
        pane.add(one);
        pane.add(two);
        pane.add(three);
        pane.add(four);
        pane.add(five);
        pane.add(six);
        pane.add(seven);
        pane.add(eight);
        pane.add(nine);
        return pane;
    }

    public void actionPerformed(ActionEvent e)
    {
        JFileChooser filer= new JFileChooser("ScreenSaves");
        String action= e.getActionCommand();
        String changer="";
        try
        {
            filer.showDialog(CreatorDriver.getFrame(),"Choose a file");
            filer.setControlButtonsAreShown(false);
            changer=STH.changeToUsableName(filer.getSelectedFile().getPath());
        }catch(Exception w){w.printStackTrace();}
        if(action.equals("Right")){
            try {
                rightt = new ImageIcon(ImageIO.read(new File("Images/Screens/"+changer+".png")));
            } catch(Exception i) {
                i.printStackTrace();
            }
            right=changer;
            six.setIcon(rightt);
        } else if(action.equals("Above")){
            try {
                topp = new ImageIcon(ImageIO.read(new File("Images/Screens/"+changer+".png")));
            } catch(Exception i) {
                i.printStackTrace();
            }
            top=changer;
            two.setIcon(topp);
        } else if(action.equals("Left")){
            try {
                leftt = new ImageIcon(ImageIO.read(new File("Images/Screens/"+changer+".png")));
            } catch(Exception i) {
                i.printStackTrace();
            }
            left=changer;
            four.setIcon(leftt);
        } else if(action.equals("Bottom")){
            try {
                bottomm = new ImageIcon(ImageIO.read(new File("Images/Screens/"+changer+".png")));
            } catch(Exception i) {
                i.printStackTrace();
            }
            bottom=changer;
            eight.setIcon(bottomm);
        }
        CreatorDriver.getFrame().getPanel().setPlaces(top,right,left,bottom);
    }

    public void kill()
    {
        frame.dispose();
    }

    public void setVisible(boolean hello)
    {
        frame.setVisible(hello);
    }

    private class Closer implements WindowListener
    {
        public void windowDeactivated(WindowEvent e)
        {

        }

        public void windowActivated(WindowEvent e)
        {

        }

        public void windowDeiconified(WindowEvent e)
        {

        }

        public void windowIconified(WindowEvent e)
        {

        }

        public void windowClosed(WindowEvent e)
        {
            CreatorDriver.getFrame().getBar().toggleEdges(false);
        }

        public void windowClosing(WindowEvent e)
        {
            CreatorDriver.getFrame().getBar().toggleEdges(false);
        }

        public void windowOpened(WindowEvent e)
        {

        }
    }
    public JFrame getFrame()
    {
        return frame;
    }
}
