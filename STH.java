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
public class STH
{
    public static void main(String[] args){
        String boi="level.ser";
        System.out.println(boi);
        System.out.println(changeToUsableName(boi));
    }

    public static String removeExtension(String s){
        int dot =s.lastIndexOf(".");
        if(dot!=-1){
            return s.substring(0,dot);
        }else{ 
            return s;
        }
    }

    public static String changeToUsableName(String s)
    {
        if(s==null)
        {
            return "null";
        }
        StringTokenizer token = new StringTokenizer(s,"/");
        int lim=token.countTokens();
        for(int i=0;i<lim-1;i++)
        {
            //System.out.println(lim+"\t"+token.nextToken());
            token.nextToken();
        }
        //while(token.countTokens()>1||!token.nextToken().equals("ScreenSaves"))
        //{
        //System.out.println(token.countTokens()+"\t"+s);
        //}
        if(lim>1)
        {
            String last=token.nextToken();
            //System.out.println(last.substring(0,last.length()-4));
            return last.substring(0,last.length()-4);
        }
        else
        {
            return s;
        }
    }

    public static ArrayList<Point> makeNoDupe(ArrayList<Point> boi)
    {
        ArrayList<Point> better= new ArrayList<Point>();
        for(int i=0;i<boi.size();i++)
        {
            if(!better.contains(boi.get(i)))
            {
                better.add(boi.get(i));
            }
        }
        return better;
    }

    public static Point getBL(Point one, Point two)
    {
        return new Point(Math.max((int)one.getX(),(int)two.getX()),Math.max((int)one.getY(),(int)two.getY()));
    }

    public static Point getTR(Point one, Point two)
    {
        return new Point(Math.min((int)one.getX(),(int)two.getX()),Math.min((int)one.getY(),(int)two.getY()));
    }

    public static Point[] toPointArray(ArrayList<Point> poo)
    {
        Point[] returner = new Point[poo.size()];
        for(int i=0;i<poo.size();i++)
        {
            returner[i]=poo.get(i);
        }
        return returner;
    }

    /**Use to make storing the obstructions easier*///not sure if this is better than storing it as one long string that will be changed into the obs
    //need to know which loads the screens better
    public static int[][] ObsToInt(String[][] type ,int[][] rot)
    {
        int[][] to= new int[type.length][type[0].length];
        for(int i=0;i<type.length;i++)
        {
            for(int j=0;j<type[0].length;j++)
            {
                if(type[i][j].equals("border1.png"))
                {
                    to[i][j]=1;
                }
                else if(type[i][j].equals("border2.png"))
                {
                    to[i][j]=2+rot[i][j];
                }
                else
                {
                    to[i][j]=6+rot[i][j];
                }
            }
        }
        return to;
    }

    /**Use to make storing the obstructions easier & will do same with death spots*///not sure this will be able to load faster than the above 
    //need to know which loads the screens better
    public static String IntArrayToString(int[][] boi)
    {
        String ret="";
        for(int i=0;i<boi.length;i++)
        {
            for(int j=0;j<boi[0].length;j++)
            {
                ret=ret+boi[i][j];
            }
        }
        return ret;
    }

    public static String StringArrayToString(String[][] boi)
    {
        String ret="";
        for(int i=0;i<boi.length;i++)
        {
            for(int j=0;j<boi[0].length;j++)
            {
                ret=ret+"\t"+boi[i][j];
            }
            ret+="\n";
        }
        return ret;
    }
}
