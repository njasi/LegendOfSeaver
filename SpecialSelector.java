import javax.swing.*;  
import javax.swing.table.*;          
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class SpecialSelector implements ActionListener
{
    private static final Insets insets = new Insets(0, 0, 0, 0);
    static JFrame frame;
    JLabel sel= new JLabel(":Selected"),toAdd=new JLabel(":Selected"),doorBoi=new JLabel(""),switchBoi=new JLabel(""),changeBoi=new JLabel(""),pushBoi=new JLabel(""),moveBoi=new JLabel(""),breakBoi=new JLabel(""),chest= new JLabel("");
    JButton setChan= new JButton("Send Channel"),on= new JButton("Receive Channel"),helloThere= new JButton("Choose Contents");
    ImageIcon boi,spc;
    Special selected;
    File[] listOfFiles;
    JTabbedPane tabs= new JTabbedPane(JTabbedPane.TOP);
    TrigListen dude= new TrigListen();
    JTable chanboi=new JTable(new DefaultTableModel(new String[]{"Channel Number", "Description"},0));
    public SpecialSelector()
    {
        frame= new JFrame("Special Stuff");
        getFileList();
        addChannel("All Monsters Killed");
        try {
            toAdd.setText(listOfFiles[0].getName().substring(0,listOfFiles[0].getName().length()-4));
            spc = new ImageIcon(ImageIO.read(listOfFiles[1]));
        } catch(Exception e) {
            e.printStackTrace();
        }

        frame.getContentPane().add(createComponents(),BorderLayout.CENTER);

        frame.setSize(500,300);
        frame.setMinimumSize(new Dimension(500,300));
        frame.setMaximumSize(new Dimension(750,450));

        //frame.setVisible(true);
    }

    private void getFileList()
    {
        File folder = new File("Images/special");
        listOfFiles = folder.listFiles();
    }

    private Component createComponents()
    {
        //making panes
        JTabbedPane tabs= new JTabbedPane(JTabbedPane.TOP);

        tabs.add("Add Special",getAdderStuff());
        tabs.add("Triggers",triggers());
        tabs.add("Channels",channels());

        return tabs;
    }

    private Component getAdderStuff()
    {
        GridLayout grid= new GridLayout(1,1);

        JPanel pane = new JPanel();
        pane.setLayout(grid);

        tabs.add("Doors",doors());
        tabs.add("Switches",switches());
        tabs.add("Changing",changing());
        tabs.add("Pushable",push());
        tabs.add("Moving",moving());
        tabs.add("Chests",chests());
        tabs.add("Breakable",breakable());
        pane.add(tabs);

        return pane;
    }

    private Component triggers()
    {
        JPanel pane = new JPanel();
        JPanel buttons= new JPanel();
        JPanel buttons2= new JPanel();
        JPanel text= new JPanel();
        //layouts
        GridLayout layout = new GridLayout(2,1);
        GridLayout lay = new GridLayout(2,2);
        pane.setLayout(lay);
        buttons.setLayout(layout);
        buttons2.setLayout(layout);
        text.setLayout(layout);
        //making things
        JButton one= new JButton("Add LWT");
        JButton two= new JButton("Remove Trigger");
        JButton six= new JButton("Add Trigger");
        JButton three= new JButton("Change Trigger");
        JLabel four= new JLabel("Current trigger:");
        JLabel five= new JLabel("LWTs:");

        try
        {
            boi= new ImageIcon("Images/other/null.png");
            boi.setImage(boi.getImage().getScaledInstance(boi.getIconHeight()*6,boi.getIconWidth()*6,0));
            sel.setIcon(boi);
        }catch(Exception e){}

        //adding things
        buttons.add(three);
        buttons.add(six);

        buttons2.add(one);
        buttons2.add(two);

        text.add(four);
        text.add(five);

        pane.add(text);
        pane.add(sel);
        pane.add(buttons);
        pane.add(buttons2);
        return pane;
    }

    private Component chests()
    {
        JPanel pane= new JPanel();
        JPanel butt= new JPanel();
        JPanel chek= new JPanel();
        GridLayout lay= new GridLayout(2,2);
        pane.setLayout(lay);
        butt.setLayout(lay);
        chek.setLayout(lay);

        JButton change= new JButton("Change Type");
        JButton txt= new JButton("Opening Text");
        String[] tres= {"Choose Treasure","Money", "Sword","Heart Piece","...Ect"};
        JComboBox treasure = new JComboBox(tres);
        JCheckBox vis = new JCheckBox("Invisible");
        JCheckBox tig = new JCheckBox("Trigger");

        tig.addActionListener(dude);
        vis.addActionListener(dude);
        change.addActionListener(new ChooseListen());
        txt.addActionListener(new ChooseListen());
        setChan.addActionListener(new ChooseListen());
        on.addActionListener(new ChooseListen());

        butt.add(change);
        butt.add(txt);
        butt.add(on);
        butt.add(setChan);
        setChan.setEnabled(false);
        on.setEnabled(false);

        chek.add(vis);
        chek.add(tig);

        pane.add(chest);
        pane.add(treasure);
        pane.add(chek);
        pane.add(butt);

        return pane;
    }

    private Component moving()
    {
        JPanel pane= new JPanel();
        JPanel butt= new JPanel();
        JPanel chek= new JPanel();
        JPanel size= new JPanel();
        GridLayout lay= new GridLayout(2,2);
        GridLayout lay2= new GridLayout(2,3);
        pane.setLayout(lay);
        butt.setLayout(lay);
        chek.setLayout(lay);
        size.setLayout(lay2);

        JButton change= new JButton("Set Tile");
        JButton setS= new JButton("Set Start");
        JButton setE= new JButton("Set End");
        JButton setSp= new JButton("Set Speed");
        JCheckBox vis = new JCheckBox("Pushes");
        JCheckBox tig = new JCheckBox("Stand On");
        JTextField wid= new JTextField("Width");
        JTextField hei= new JTextField("Height");

        tig.addActionListener(dude);
        vis.addActionListener(dude);
        change.addActionListener(new ChooseListen());
        setS.addActionListener(new ChooseListen());
        setE.addActionListener(new ChooseListen());
        setSp.addActionListener(new ChooseListen());

        butt.add(setS);
        butt.add(setE);
        butt.add(setSp);
        butt.add(change);

        chek.add(vis);
        chek.add(tig);

        size.add(wid);
        size.add(hei);

        pane.add(moveBoi);
        pane.add(size);
        pane.add(chek);
        pane.add(butt);

        return pane;
    }

    private Component push()
    {
        JPanel pane= new JPanel();
        JPanel butt= new JPanel();
        JPanel chek= new JPanel();
        GridLayout lay= new GridLayout(2,2);
        GridLayout lay2= new GridLayout(3,1);
        pane.setLayout(lay);
        butt.setLayout(lay);
        chek.setLayout(lay2);

        JButton change= new JButton("Set Tile");
        JButton weight= new JButton("Set Weight");
        JCheckBox vis = new JCheckBox("Push Only");
        JCheckBox tig = new JCheckBox("One movement");
        JCheckBox mov = new JCheckBox("Instant move");

        tig.addActionListener(dude);
        vis.addActionListener(dude);
        mov.addActionListener(dude);
        change.addActionListener(new ChooseListen());
        weight.addActionListener(new ChooseListen());

        butt.add(change);
        butt.add(weight);

        chek.add(vis);
        chek.add(tig);
        chek.add(mov);

        pane.add(pushBoi);
        pane.add(new JPanel());
        pane.add(chek);
        pane.add(butt);

        return pane;
    }

    private Component changing()
    {
        JPanel pane= new JPanel();
        JPanel butt= new JPanel();
        JPanel chek= new JPanel();
        JPanel size= new JPanel();
        GridLayout lay= new GridLayout(2,2);
        GridLayout lay2= new GridLayout(2,3);
        pane.setLayout(lay);
        butt.setLayout(lay);
        chek.setLayout(lay);
        size.setLayout(lay2);

        JButton change= new JButton("Set Tile");
        JButton setS= new JButton("Receive Channel");
        JCheckBox vis = new JCheckBox("Time Based");
        JCheckBox tig = new JCheckBox("Stand and Fall");
        String[] tres= {"Choose Action","Disappear", "Fall","Fade"};
        JComboBox changeType = new JComboBox(tres);

        tig.addActionListener(dude);
        vis.addActionListener(dude);
        change.addActionListener(new ChooseListen());
        setS.addActionListener(new ChooseListen());

        butt.add(change);
        butt.add(setS);

        chek.add(vis);
        chek.add(tig);

        size.add(new JPanel());

        pane.add(changeBoi);
        pane.add(changeType);
        pane.add(chek);
        pane.add(butt);

        return pane;
    }

    private Component switches()
    {
        JPanel pane= new JPanel();
        JPanel butt= new JPanel();
        JPanel chek= new JPanel();
        GridLayout lay= new GridLayout(2,2);
        pane.setLayout(lay);
        butt.setLayout(lay);
        chek.setLayout(lay);

        JButton change= new JButton("Toggle State");
        JButton txt= new JButton("Send Channel");
        String[] hello= {"Choose Type","Pressure Plate","Switch", "Button","Color Switcher","Water valve","Eye target"};
        JComboBox switchType = new JComboBox(hello);

        change.addActionListener(new ChooseListen());
        txt.addActionListener(new ChooseListen());

        butt.add(change);
        butt.add(txt);

        pane.add(switchBoi);
        pane.add(switchType);
        pane.add(chek);
        pane.add(butt);

        return pane;
    }

    public void actionPerformed(ActionEvent a)
    {
        String action= a.getActionCommand();
    }

    private Component doors()
    {
        JPanel pane= new JPanel();
        JPanel butt= new JPanel();
        JPanel chek= new JPanel();
        GridLayout lay= new GridLayout(2,2);
        GridLayout lay2= new GridLayout(3,1);
        pane.setLayout(lay);
        butt.setLayout(lay);
        chek.setLayout(lay2);

        JButton change= new JButton("Change Visual");
        JButton dudee= new JButton("Receive Channel");
        JCheckBox vis = new JCheckBox("Close on Enter");
        JCheckBox tig = new JCheckBox("Bombable");
        JCheckBox hel = new JCheckBox("Locked");

        tig.addActionListener(dude);
        vis.addActionListener(dude);
        hel.addActionListener(dude);
        change.addActionListener(new ChooseListen());
        dudee.addActionListener(new ChooseListen());

        butt.add(change);
        butt.add(dudee);

        chek.add(vis);
        chek.add(tig);
        chek.add(hel);

        pane.add(doorBoi);
        pane.add(new JPanel());
        pane.add(chek);
        pane.add(butt);

        return pane;
    }

    private Component breakable()
    {
        JPanel pane= new JPanel();
        JPanel butt= new JPanel();
        JPanel chek= new JPanel();
        GridLayout lay= new GridLayout(2,2);
        pane.setLayout(lay);
        butt.setLayout(lay);
        chek.setLayout(lay);

        JButton txt= new JButton("Set Image");
        String[] tres= {"Choose Type","Pot", "Grass","Bush","...Ect"};
        JComboBox breakType = new JComboBox(tres);
        JCheckBox vis = new JCheckBox("Same Contents");
        JCheckBox tig = new JCheckBox("Liftable");

        tig.addActionListener(dude);
        vis.addActionListener(dude);
        helloThere.addActionListener(new ChooseListen());
        txt.addActionListener(new ChooseListen());
        setChan.addActionListener(new ChooseListen());
        on.addActionListener(new ChooseListen());

        butt.add(helloThere);
        butt.add(txt);
        helloThere.setEnabled(false);

        chek.add(vis);
        chek.add(tig);

        pane.add(breakBoi);
        pane.add(breakType);
        pane.add(chek);
        pane.add(butt);

        return pane;
    }

    public Component channels()
    {
        JPanel tempboi= new JPanel();
        tempboi.setLayout(new GridBagLayout());
        int gridy = 0;
        JButton meh=new JButton("Add Channel");
        meh.addActionListener(new Channels());
        addComponent(tempboi, new JScrollPane(chanboi), 0, gridy++, 1, 1, 1D, 4D,GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(tempboi, meh, 0, gridy++, 1, 1, 1D, 1D,GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        return tempboi;
    }

    private void addComponent(Container container, Component component,int gridx, int gridy, 
    int gridwidth, int gridheight,double weightx, double weighty, int anchor, int fill)
    {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy,gridwidth, gridheight, weightx, weighty, anchor, fill, insets, 0, 0);
        container.add(component, gbc);
    }

    /**
     * accepts stuff to add to table(Description)
     */
    public void addChannel(String stuffToAdd)
    {
        DefaultTableModel inside =(DefaultTableModel)chanboi.getModel();//get model
        inside.addRow(new String[]{""+inside.getRowCount(),stuffToAdd});
    }

    public String getSelectedTab()
    {
        return tabs.getTitleAt(tabs.getSelectedIndex());
    }

    public void setVisible(boolean b)
    {
        frame.setVisible(b);
    }

    private class BoxListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
            String action= a.getActionCommand();
        }
    }
    private class Channels implements ActionListener//add channels and see when they change
    {
        public void actionPerformed(ActionEvent a)
        {
            String action= a.getActionCommand();
            if(action.equals("Add Channel"))
            {
                CreatorDriver.getFrame().getPanel().getSS().addChannel("Click to edit");
            }
        }
    }
    private class TrigListen implements ActionListener//deals with most checkboxes
    {
        boolean check= false;
        boolean vis= false;
        boolean coe= false;
        boolean bomb= false;
        boolean lock= false;
        boolean tb= false;
        boolean sat= false;
        boolean po= false;
        boolean om= false;
        boolean im= false;
        boolean pushes= false;
        boolean so= false;
        boolean sc= false;
        boolean lift= false;
        public void actionPerformed(ActionEvent a)
        {
            String action= a.getActionCommand();
            String tab=tabs.getTitleAt(tabs.getSelectedIndex());
            if(tab.equals("Doors"))
            {
                if(action.equals("Close on Enter"))
                {
                    coe=!coe;
                }
                else if(action.equals("Bombable"))
                {
                    bomb=!bomb;
                }
                else if(action.equals("Locked"))
                {
                    lock=!lock;
                }
            }
            else if(tab.equals("Switches"))
            {

            }
            else if(tab.equals("Changing"))
            {  
                if(action.equals("Time Based"))
                {
                    tb=!tb;
                }
                else if(action.equals("Stand and Fall"))
                {
                    sat=!sat;
                }
            }
            else if(tab.equals("Pushable"))
            {
                if(action.equals("Push Only"))
                {
                    po=!po;
                }
                else if(action.equals("One movement"))
                {
                    om=!om;
                }
                else if(action.equals("Instant move"))
                {
                    im=!im;
                }
            }
            else if(tab.equals("Moving"))
            {
                if(action.equals("Pushes"))
                {
                    pushes=!pushes;
                }
                else if(action.equals("Stand On"))
                {
                    so=!so;
                }
            }
            else if(tab.equals("Chests"))
            {
                if(action.equals("Trigger"))
                {
                    check=!check;
                    setChan.setEnabled(check);
                }
                else 
                {
                    vis=!vis;
                    on.setEnabled(vis);
                }
            }
            else if(tab.equals("Breakable"))
            {
                if(action.equals("Same Contents"))
                {
                    sc=!sc;
                    helloThere.setEnabled(sc);
                }
                else 
                {
                    lift=!lift;
                }
            }
        }

        public boolean[] getCheckBoxes()
        {
            String tab=tabs.getTitleAt(tabs.getSelectedIndex());
            boolean[] ret= new boolean[3];
            if(tab.equals("Doors"))
            {
                ret[0]=coe;
                ret[1]=bomb;
                ret[2]=lock;
            }
            else if(tab.equals("Switches"))
            {
                //nothing for now
            }
            else if(tab.equals("Changing"))
            {
                ret[0]=tb;
                ret[1]=sat;
            }
            else if(tab.equals("Pushable"))
            {
                ret[0]=po;
                ret[1]=om;
                ret[2]=im;
            }
            else if(tab.equals("Moving"))
            {
                ret[0]=pushes;
                ret[1]=so;
            }
            else if(tab.equals("Chests"))
            {
                ret[0]=check;
                ret[1]=vis;
            }
            else if(tab.equals("Breakable"))
            {
                ret[0]=sc;
                ret[1]=lift;
            }
            return ret;
        }
    }
    private class ChooseListen implements ActionListener//deals with most buttons
    {
        ChannelOptionPane charles =new ChannelOptionPane();
        public void actionPerformed(ActionEvent a)
        {
            String action= a.getActionCommand();
            String tab=tabs.getTitleAt(tabs.getSelectedIndex());
            try
            {
                if(tab.equals("Doors"))
                {
                    if(action.equals("Change Visual"))
                    {
                        doorBoi.setIcon(new ImageIcon(ImageIO.read(new File(chooseImage()))));
                    }
                    else if(action.equals("Receive Channel"))
                    {
                        charles.setChan();
                    }
                }
                else if(tab.equals("Switches"))
                {
                    if(action.equals("Toggle State"))
                    {

                    }
                    else if(action.equals("Send Channel"))
                    {

                    }
                }
                else if(tab.equals("Changing"))
                {
                    if(action.equals("Receive Visual"))
                    {

                    }
                    else if(action.equals("Set Tile"))
                    {
                        changeBoi.setIcon(new ImageIcon(ImageIO.read(new File(chooseImage()))));
                    }
                }
                else if(tab.equals("Pushable"))
                {
                    if(action.equals("Set Weight"))
                    {

                    }
                    else if(action.equals("Set Tile"))
                    {
                        pushBoi.setIcon(new ImageIcon(ImageIO.read(new File(chooseImage()))));
                    }
                }
                else if(tab.equals("Moving"))
                {
                    if(action.equals("Set Start"))
                    {

                    }
                    else if(action.equals("Set Tile"))
                    {
                        moveBoi.setIcon(new ImageIcon(ImageIO.read(new File(chooseImage()))));
                    }
                    else if(action.equals("Set End"))
                    {

                    }
                    else if(action.equals("Set Speed"))
                    {

                    }
                }
                else if(tab.equals("Chests"))
                {
                    if(action.equals("Change Type"))
                    {
                        chest.setIcon(new ImageIcon(ImageIO.read(new File(chooseImage()))));
                    }
                    else if(action.equals("Opening Text"))
                    {

                    }
                    else if(action.equals("Receive Channel"))
                    {

                    }
                    else if(action.equals("Send Channel"))
                    {

                    }
                }
                else if(tab.equals("Breakable"))
                {
                    if(action.equals("Set Image"))
                    {
                        breakBoi.setIcon(new ImageIcon(ImageIO.read(new File(chooseImage()))));
                    }
                    else if(action.equals("Set Contents"))
                    {

                    }
                }
            }
            catch(Exception e)
            {

            }
        }

        private String chooseImage()
        {
            JFileChooser filer= new JFileChooser("Images");
            try
            {
                filer.showDialog(frame,"Choose an image");
                filer.setControlButtonsAreShown(false);
                return filer.getSelectedFile().getPath();
            }
            catch(Exception w)
            {
                w.printStackTrace();
            }
            return "null";
        }
    }
    public class ChannelOptionPane extends JOptionPane{
        public ChannelOptionPane()
        {
            super();
        }

        public int setChan()
        { 
            return super.showConfirmDialog(
                null,
                getPanel(),
                "Choose or Add a Channel",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        }

        private JPanel getPanel() {
            JPanel panel = new JPanel();
            panel.add(CreatorDriver.getFrame().getPanel().getSS().channels());

            return panel;
        }
    }
}
