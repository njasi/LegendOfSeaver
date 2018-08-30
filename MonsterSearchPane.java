import java.util.*;
import javax.swing.*;          
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class MonsterSearchPane extends JOptionPane{
    JComboBox<ImageIcon> results;
    String[] stringResults;
    File[] listOfFiles;
    JTextField searchBar = new JTextField("Search for monsters here (search png for a full list)");
    public MonsterSearchPane()
    {
        super();
        File folder = new File("Images/Monsters");
        listOfFiles = folder.listFiles();
    }

    public String search(){
        return showConfirmDialog(showInputDialog(CreatorDriver.getFrame(),"What would you like to search for?"));
    }

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
        }
        else
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
}