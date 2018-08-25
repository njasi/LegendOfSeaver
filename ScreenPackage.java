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
    int[][] tr;
    int[][] or;
    int[][] decor;
    int howManyDoors;
    String name,top,right,left,bottom;
    public ScreenPackage(Screen tiles, Screen obstructions, Screen decorations,Door[] d,String s,int doo)
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

    public void save(Screen tiles, Screen obstructions,Screen decorations,Door[] d,int doo,String n)
    {
        t=tiles.toStringArray();
        o=obstructions.toStringArray();
        tr=tiles.rotationArray();
        or=obstructions.rotationArray();
        decor=decorations.rotationArray();
        deco=decorations.toStringArray();
        doors=new String[d.length];
        name=n;
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
            t=((ScreenPackage)ois.readObject()).getTiles();
            ObjectInputStream oss = new ObjectInputStream(new FileInputStream(new File(s)));
            o=((ScreenPackage)oss.readObject()).getObs();
            oss = new ObjectInputStream(new FileInputStream(new File(s)));
            deco=((ScreenPackage)oss.readObject()).getDeco();
            oss = new ObjectInputStream(new FileInputStream(new File(s)));
            decor=((ScreenPackage)oss.readObject()).getDecoR();
            oss = new ObjectInputStream(new FileInputStream(new File(s)));
            tr=((ScreenPackage)oss.readObject()).getTilesR();
            oss = new ObjectInputStream(new FileInputStream(new File(s)));
            or=((ScreenPackage)oss.readObject()).getObsR();
            oss = new ObjectInputStream(new FileInputStream(new File(s)));
            doors=((ScreenPackage)oss.readObject()).getDoors();
            oss = new ObjectInputStream(new FileInputStream(new File(s)));
            howManyDoors=((ScreenPackage)oss.readObject()).howManyDoors();
            oss = new ObjectInputStream(new FileInputStream(new File(s)));
            top=((ScreenPackage)oss.readObject()).getTop();
            oss = new ObjectInputStream(new FileInputStream(new File(s)));
            right=((ScreenPackage)oss.readObject()).getRight();
            oss = new ObjectInputStream(new FileInputStream(new File(s)));
            left=((ScreenPackage)oss.readObject()).getLeft();
            oss = new ObjectInputStream(new FileInputStream(new File(s)));
            bottom=((ScreenPackage)oss.readObject()).getBottom();
            ois.close();
            oss.close();
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
}
