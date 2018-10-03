
import java.util.*;
import javax.swing.*;          
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class BigOptionPane extends JOptionPane
{
    MonsterHolder[] info;
    BigOptionPane self=this;
    int[] indices;
    JTextField[] hps;
    JCheckBox[] aas;
    JButton[] channames;
    JTabbedPane tabs= new JTabbedPane(JTabbedPane.TOP);
    ImageIcon no;

    JComboBox<ImageIcon> results;
    String[] stringResults;
    File[] listOfFiles;
    JTextField searchBar = new JTextField("Search for monsters here (search png for a full list)");
    public BigOptionPane(MonsterHolder[] toEdit,int[] is)//For the monster editing stuff
    {
        super();
        info=toEdit;
        indices=is;
        hps=new JTextField[is.length];
        aas=new JCheckBox[is.length];
        channames=new JButton[is.length];
        try{
            no=new ImageIcon(ImageIO.read(new File("images/tiles/border1.png")));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public BigOptionPane(boolean monster)//DoesMonsterSearch Stuff
    {
        super();
        if(monster){
            File folder = new File("Images/Monsters");
            listOfFiles = folder.listFiles();
        }
    }

    public MonsterHolder[] editMonster(){
        //UIManager.put("OptionPane.minimumSize",new Dimension(500,500));
        int boi=super.showConfirmDialog(null,getTabDisplay(),"Edit",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if(boi==0){//Selects "ok"
            for(int i=0;i<info.length;i++){
                if(!tabs.getIconAt(i).equals(no)){
                    info[i].setAppearAtStart(aas[i].isSelected());
                    if(hps[i].getText().length()>0){
                        info[i].setHp(Integer.parseInt(hps[i].getText()));
                    }
                    String name=channames[i].getText();
                    String num=name.substring(name.length()-2,name.length()-1);
                    if(!num.equals("n")){
                        info[i].setChannel(Integer.parseInt(num));
                    }
                }else{
                    info[i]=null;
                }
            }
        }
        UIManager.put("OptionPane.minimumSize",new Dimension(262,90));//reset to default size settings

        return info;
    }

    private Component getTabDisplay(){
        for(int i=0;i<info.length;i++){
            tabs.addTab(""+i,new ImageIcon(info[i].getImage()),getOneDisplay(info[i],i));
        }
        return tabs;
    }

    private Component getOneDisplay(MonsterHolder base,int i){//makes one tab at a time
        JPanel holder=new JPanel(new GridBagLayout());
        holder.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        GridBagConstraints gbc = new GridBagConstraints();
        ImageIcon pic= new ImageIcon(base.getImage());

        gbc.gridwidth = 2;
        gbc.gridy=0;
        gbc.gridx=0;
        holder.add(new JLabel(pic),gbc);

        gbc.gridwidth = 2;
        gbc.gridy=1;
        gbc.gridx=0;
        holder.add(new JLabel(base.getType()),gbc);  

        gbc.gridwidth=2;
        gbc.gridy=2;
        gbc.gridx=0;
        if(base.getChannel()!=-1){
            channames[i]=new JButton("Channel: "+CreatorDriver.getFrame().getPanel().getChannels().getValueAt(base.getChannel(), 1)+"("+base.getChannel()+")");
        }else{
            channames[i]=new JButton("Channel: None");
        }
        holder.add(channames[i],gbc);
        channames[i].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int result=setChannel();
                    if(result!=-1){
                        channames[i].setText("Channel: "+CreatorDriver.getFrame().getPanel().getChannels().getValueAt(result, 1)+" ("+result+")");
                    }
                }
            });

        gbc.gridwidth=1;
        gbc.gridy=3;
        gbc.gridx=0;
        holder.add(new JLabel("HP: "+base.getHP()),gbc);

        gbc.gridx=1;
        hps[i]=new JTextField("",5);
        holder.add(hps[i],gbc);

        gbc.gridwidth = 2;
        gbc.gridx=0;
        gbc.gridy=4;
        aas[i]=new JCheckBox("Appears at start",base.appearsAtStart());
        holder.add(aas[i],gbc);

        gbc.gridy=5;
        JButton delete= new JButton("Delete this monster");
        delete.addActionListener(new MonsterActionListener());
        holder.add(delete,gbc);
        return holder;
    }

    public int showScreenConfirmDialog(String s)
    { 
        return super.showConfirmDialog(
            null,
            getScreenImage(s),
            "Put the door(s) on this screen?",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);
    }

    //for the searching
    public String search(){
        return showConfirmDialog(showInputDialog(CreatorDriver.getFrame(),"What would you like to search for?"));
    }

    /**
     * shows search results
     */
    public String showConfirmDialog(String s)
    { 
        if(s.length()==0){
            s=".png";
        }
        int i=1;
        try{
            i=super.showConfirmDialog(null,getList(s),"Choose a result",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        }catch(Exception e){
            System.out.println("oof");
        }
        if(results.getSelectedItem().equals("There were no results, sorry."))
        {
            return null;
        }
        if(i==0)
        {
            return stringResults[results.getSelectedIndex()];
        }
        else{
            return null;
        }
        //return results.getSelectedItem();
    }

    /**
     * gets results list
     */
    private JPanel getList(String s){
        JPanel holder=new JPanel();
        stringResults=filesToStringArray(listOfFiles,s);
        try{
            results= new JComboBox<ImageIcon>(stringArrayToImageIconArray(stringResults));
        }catch(Exception e){
            e.printStackTrace();
        }
        holder.add(results);
        return holder;
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
        }else
        {
            returner[0]="There were no results, sorry.";
        }
        searchBar = new JTextField("Search for monsters here (search png for a full list)");
        return returner;
    }

    private ImageIcon[] stringArrayToImageIconArray(String[] names) throws Exception{
        ImageIcon[] images= new ImageIcon[names.length];
        for(int i=0;i<images.length;i++){
            images[i]= new ImageIcon(ImageIO.read(new File("Images/Monsters/"+names[i]+".png")));
        }
        return images;
    }

    private Component getScreenImage(String s) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        ImageIcon image = null;
        try {
            image = new ImageIcon(ImageIO.read(new File("Images/Screens/"+s+".png")));
        } catch(Exception e) {
            e.printStackTrace();
        }

        label.setIcon(image);
        panel.add(label);

        return panel;
    }

    //below deals with channel selection
    public int setChannel()
    { 
        int r = super.showConfirmDialog(null,getChannelPanel(),"Choose or Add a Channel",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if(r==0){
            return CreatorDriver.getFrame().getPanel().getChannels().getSelectedRow();
        }
        return -1;
    }

    private Component getChannelPanel() {
        JPanel panel = new JPanel();
        panel.add(CreatorDriver.getFrame().getPanel().getSS().channels());
        return panel;
    }

    private class MonsterActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            int index=tabs.getSelectedIndex();
            int r=self.showConfirmDialog(CreatorDriver.getFrame(),"Are you sure you want to delete this "+info[index].getType()+"?","Delete?",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
            if(r==0){
                tabs.setIconAt(index,no);
            }
        }
    }
}
