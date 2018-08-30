import java.util.*;
import javax.swing.*;          
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class MonsterEditPane extends JOptionPane{
    MonsterHolder[] info;
    MonsterEditPane self=this;
    int[] indices;
    JTextField[] channels;
    JTextField[] hps;
    JCheckBox[] aas;
    JTabbedPane tabs= new JTabbedPane(JTabbedPane.TOP);
    ImageIcon no;
    public MonsterEditPane(MonsterHolder[] toEdit,int[] is)
    {
        super();
        info=toEdit;
        indices=is;
        hps=new JTextField[is.length];
        channels=new JTextField[is.length];
        aas=new JCheckBox[is.length];
        try{
            no=new ImageIcon(ImageIO.read(new File("images/tiles/border1.png")));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public MonsterHolder[] editMonster(){
        //UIManager.put("OptionPane.minimumSize",new Dimension(500,500));
        int boi=super.showConfirmDialog(null,getTabDisplay(),"Edit",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        UIManager.put("OptionPane.minimumSize",new Dimension(262,90));//reset to default size settings
        return info;
    }

    private Component getTabDisplay(){
        for(int i=0;i<info.length;i++){
            tabs.addTab(""+i,new ImageIcon(info[i].getImage()),getOneDisplay(info[i],i));
        }
        return tabs;
    }

    private Component getOneDisplay(MonsterHolder base,int i){//just for multiples later
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

        gbc.gridwidth=1;
        gbc.gridy=2;
        gbc.gridx=0;
        holder.add(new JLabel("Channel: "+base.getChannel()),gbc);
        channels[i]=new JTextField("",5);
        gbc.gridx=1;
        holder.add(channels[i],gbc);

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