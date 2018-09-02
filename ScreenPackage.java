import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
public class ScreenPackage implements Serializable
{
    String[][] t;
    String[][] o;
    String[][] deco;
    String[] doors;
    String[] channels;
    int[][] tr;
    int[][] or;
    int[][] decor;
    //MonsterHolder[] monsters;
    SpecialHolder[] specials;
    int howManyDoors;
    String name,top,right,left,bottom;
    public ScreenPackage(Screen tiles, Screen obstructions, Screen decorations,Door[] d,String s,int doo,MonsterHolder[] mon,JTable chan,SpecialHolder[] things)
    {
        name=s;
        t=tiles.toStringArray();
        o=obstructions.toStringArray();
        deco=decorations.toStringArray();
        tr=tiles.rotationArray();
        or=obstructions.rotationArray();
        decor=decorations.rotationArray();
        doors=new String[d.length];
        for(int i=0;i<d.length;i++)
        {
            doors[i]=d[i].toString();
        }
        howManyDoors=doo;
        //monsters=mon;
        channels= new String[chan.getRowCount()];
        for(int i=0;i<chan.getRowCount();i++){
            channels[i]=(String)chan.getValueAt(i,1);
        }
        specials=things;
    }

    public ScreenPackage()
    {
        name="null";
    }

    public void setPlaces(String t,String r,String l,String b)
    {
        //         top=STH.changeToUsableName(t);
        //         right=STH.changeToUsableName(r);
        //         left=STH.changeToUsableName(l);
        //         bottom=STH.changeToUsableName(b);
        top=t;
        right=r;
        left=l;
        bottom=b;
    }

    public void save()
    {
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("ScreenSaves/"+name+".ser")));
            oos.writeObject(this);
            oos.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void save(Screen tiles, Screen obstructions,Screen decorations,Door[] d,int doo,String n,MonsterHolder[] mon,JTable chan,SpecialHolder[] things)
    {
        t=tiles.toStringArray();
        o=obstructions.toStringArray();
        tr=tiles.rotationArray();
        or=obstructions.rotationArray();
        decor=decorations.rotationArray();
        deco=decorations.toStringArray();
        doors=new String[d.length];
        //monsters=mon;
        channels= new String[chan.getRowCount()];
        for(int i=0;i<chan.getRowCount();i++){
            channels[i]=""+chan.getValueAt(i,1);
        }
        name=n;
        specials=things;
        for(int i=0;i<d.length;i++)
        {
            doors[i]=d[i].toString();
        }
        howManyDoors=doo;
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("ScreenSaves/"+name+".ser")));
            oos.writeObject(this);
            oos.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void load(String s)
    {
        try
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(s)));
            ScreenPackage loading=(ScreenPackage)ois.readObject();
            ois.close();
            t=loading.getTiles();
            o=loading.getObs();
            deco=loading.getDeco();
            decor=loading.getDecoR();
            tr=loading.getTilesR();
            or=loading.getObsR();
            doors=loading.getDoors();
            howManyDoors=loading.howManyDoors();
            top=loading.getTop();
            right=loading.getRight();
            left=loading.getLeft();
            bottom=loading.getBottom();
            //monsters=loading.getMonsters();
            specials=loading.getSpecials();
            channels=loading.getChannels();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String[][] getTiles()
    {
        return t;
    }

    public String[][] getObs()
    {
        return o;
    }

    public String[][] getDeco()
    {
        return deco;
    }

    public int[][] getTilesR()
    {
        return tr;
    }

    public int[][] getObsR()
    {
        return or;
    }

    public int[][] getDecoR()
    {
        return decor;
    }

    public String[] getDoors()
    {
        return doors;
    }

    public int howManyDoors()
    {
        return howManyDoors;
    }

    public String getName()
    {
        return name;
    }

    public void changeName(String newName)
    {
        name=newName;
    }

    public String getTop()
    {
        return top;
    }

    public String getLeft()
    {
        return left;
    }

    public String getRight()
    {
        return right;
    }

    public String getBottom()
    {
        return bottom;
    }

    /*public MonsterHolder[] getMonsters()
    {
    return monsters;
    }*/

    public SpecialHolder[] getSpecials()
    {
        return specials;
    }

    public String[] getChannels()
    {
        return channels;
    }
    
    public String[][] getTableData()
    {
        String[][] data=new String[channels.length][2];
        for(int i=0;i<channels.length;i++){
            data[i][0]=""+i;
            data[i][1]=channels[i];
        }
        return data;
    }
}
