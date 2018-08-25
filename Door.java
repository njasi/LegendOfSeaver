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
public class Door implements Serializable
{
    int xPosition, yPosition, xExit, yExit, transitionType;
    String goingTo;
    public Door(Point enter, String screen, Point exit,int type)
    {
        xPosition=(int)enter.getX();
        yPosition=(int)enter.getY();
        goingTo=screen;
        xExit=(int)exit.getX();
        yExit=(int)exit.getY();
        transitionType=type;
    }
    
    public Door(String load)
    {
        loadFromString(load);
    }

    public Point getPos()
    {
        return new Point(xPosition,yPosition);
    }

    public Point getExit()
    {
        return new Point(xExit,yExit);
    }

    public String getDestination()
    {
        return goingTo;
    }

    public String toString()
    {
        return xPosition+"#"+yPosition+"#"+xExit+"#"+yExit+"#"+transitionType+"#"+goingTo;
    }
    
    public boolean isAt(Point p)
    {
        return (int)p.getX()==xPosition&&(int)p.getY()==yPosition;
    }
    
    private void loadFromString(String load)
    {
        StringTokenizer token = new StringTokenizer(load,"#");
        xPosition=Integer.parseInt(token.nextToken());
        yPosition=Integer.parseInt(token.nextToken());
        xExit=Integer.parseInt(token.nextToken());
        yExit=Integer.parseInt(token.nextToken());
        transitionType=Integer.parseInt(token.nextToken());
        goingTo=token.nextToken();
    }
    
    public boolean doorMove(Player seaver)
    {
       
       return false;
    }
}
