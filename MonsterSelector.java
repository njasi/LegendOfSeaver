import java.util.*;
import javax.swing.*;          
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class MonsterSelector implements ActionListener
{
    JComboBox results;
    ImageIcon monster= new ImageIcon();
    File[] listOfFiles;
    JFrame frame;
    MonsterSelector self=this;
    int index=0;
    JLabel boi =new JLabel("",JLabel.CENTER);
    JTextField searchBar = new JTextField("Search for monsters here (search png for a full list)");
    public MonsterSelector() 
    {
        frame = new JFrame("Monster Selector");
        getFileList();
        try {
            boi.setText(listOfFiles[0].getName().substring(0,listOfFiles[0].getName().length()-4));
            monster = new ImageIcon(ImageIO.read(listOfFiles[1]));
        } catch(Exception e) {
            e.printStackTrace();
        }
        Component contents = createComponents();
        frame.addWindowListener(new Closer());
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        frame.setSize(400,300);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setMaximumSize(new Dimension(400, 400));
        frame.setVisible(true);
    }

    private void getFileList()
    {
        File folder = new File("Images/Monsters");
        listOfFiles = folder.listFiles();
    }

    private Component createComponents() 
    {
        JPanel pane=new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        pane.setLayout(gridbag);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        JButton prev=new JButton("Previous");
        prev.addActionListener(new ImageListener());
        pane.add(prev,c);
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        JButton next=new JButton("Next");
        next.addActionListener(new ImageListener());
        pane.add(next,c);

        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        searchBar.addActionListener(new SearchListener());
        pane.add(searchBar,c);

        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        boi.setIcon(monster);
        pane.add(boi,c);

        c.weightx = 0.0;                //reset to the default

        return pane;
    }

    public void actionPerformed(ActionEvent e)
    {
        //System.out.println(e.getActionCommand());
    }

    public void resetContents(String s)
    {
        try {
            monster = new ImageIcon(ImageIO.read(new File("Images/Monsters/"+s+".png")));
        } catch(Exception e) {
            e.printStackTrace();
        }
        boi.setIcon(monster);
        boi.setText(s);
        searchBar = new JTextField("Search for monsters here (search png for a full list)");
    }

    public void setVisible(boolean n)
    {
        frame.setVisible(n);
    }

    public void changeSelection(int changeBy){
        index+=changeBy;
        while(index >= listOfFiles.length){//if scrolling by more than one is ever added you can scroll like 100 and it will loop throught
            index-=listOfFiles.length;
        }
        while(index<0){
            index+=listOfFiles.length;
        }
    }

    private class ImageListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Next")){
                index++;
            }else{
                index--;
            }
            if (index >= listOfFiles.length) {
                index = 0;
            }else if(index==-1){
                index = listOfFiles.length-1;
            }
            try
            {
                boi.setIcon(new ImageIcon(ImageIO.read(listOfFiles[index])));
                boi.setText(listOfFiles[index].getName().substring(0,listOfFiles[index].getName().length()-4));
            }catch(Exception nooo){}
        }
    }
    private class SearchListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            MonsterOption boi = new MonsterOption();
            String res = e.getActionCommand();
            res=boi.showConfirmDialog(res);
            if(res!=null)
            {
                self.resetContents(res);
            }
        }
        private class MonsterOption extends JOptionPane{
            JComboBox<String> results;
            public MonsterOption()
            {
                super();
            }

            public String showConfirmDialog(String s)
            { 
                int i=super.showConfirmDialog(null,getList(s),"Choose a result",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
                if(results.getSelectedItem().equals("There were no results, sorry."))
                {
                    return null;
                }
                if(i==0)
                {
                    return results.getItemAt(results.getSelectedIndex());
                }
                else{
                    return null;
                }
                //return results.getSelectedItem();
            }

            private JComboBox getList(String s){
                results= new JComboBox<String>(filesToStringArray(listOfFiles,s));
                return results;
            }

            private String[] filesToStringArray(File[] stuff,String search)
            {
                String[] returner= new String[1];
                ArrayList<String> boi= new ArrayList<String>();
                for(int i=0;i<stuff.length;i++)
                {
                    String s=stuff[i].getName();
                    if(s.toLowerCase().indexOf(search.toLowerCase())!=-1)
                    {
                        boi.add(s.substring(0,s.length()-4));
                    }
                }
                if(boi.size()!=0){
                    returner= new String[boi.size()];
                    for(int i=0;i<returner.length;i++)
                    {
                        returner[i]=boi.get(i);
                    }
                }
                else
                {
                    returner[0]="There were no results, sorry.";
                }
                searchBar = new JTextField("Search for monsters here (search png for a full list)");
                return returner;
            }
        }
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
            CreatorDriver.getFrame().getBar().toggleMonsel(false);
        }

        public void windowClosing(WindowEvent e)
        {
            CreatorDriver.getFrame().getBar().toggleMonsel(false);
        }

        public void windowOpened(WindowEvent e)
        {

        }
    }
}