import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.imageio.*;
public class Player
{
    double x,y,movementSpeed=1.5;
    int zoom=GameFrame.getZoom(), extra=GameFrame.extra(),ex,ey,c=0;
    int[][] obs;
    boolean canMove= true,canChange=true;
    Image p;
    Image resize;
    InventoryBar pBar= new InventoryBar();
    public Player(int xx,int yy)
    {
        try
        {
            int zoom=GameFrame.getZoom();
            p=ImageIO.read(new File("images/player/Seaver.png"));
            resize= p.getScaledInstance(p.getWidth(null)*zoom,p.getHeight(null)*zoom,0);
        }
        catch(Exception e){e.printStackTrace();}
        x=xx*16;
        y=yy*16;
    }

    public void move(int xx,int yy)
    {
        if(canMove)
        {
            if(Math.abs(xx)>0 && Math.abs(yy)>0)
            {
                x+=xx;
                y+=yy;
            }
            else if(Math.abs(xx)>0)
            {
                x+=movementSpeed*xx;
            }
            else 
            {
                y+=movementSpeed*yy;
            }
        }
        if(!canChange&&(Math.abs(ex-x)>16||Math.abs(ey-y)>16))
        {
            canChange= true;
        }
        //System.out.println("("+x+","+y+")");
    }

    public void draw(Graphics g)
    {
        g.drawImage(resize,(int)x*zoom+extra,(int)y*zoom-6*zoom,null);
        //pBar.draw(g,false);
    }

    /**for cutscenes*/
    public void walkTo(int xx, int yy)
    {

    }

    /**glides, for screens that scroll over like overworld*/
    public void glideTo(int xx, int yy,int times)
    {
        c++;
        //if
    }

    public int touchingEdge()
    {
        if((int)((x+16)/16)==0)
        {
            return 3;
        }
        else if((int)(y/16)+1==15)
        {
            return 2;
        }
        else if((int)(x/16)+1==16)
        {
            return 1;
        }
        else if((int)((y+8)/16)==1)
        {
            return 0;
        }
        return -1;
    }

    /**lock player movement in cutscenes*/
    public void lockMove(boolean move)
    {
        canMove=move;
    }

    /**for going through doors and stuff teleports to a tile*/
    public void teleportTo(int xx, int yy)
    {
        x=xx*16;
        y=yy*16;
        canChange=false;
    }

    /**for going through doors and stuff*/
    public void teleportTo(Point p)
    {
        x=(int)p.getX()*16;
        y=(int)p.getY()*16;
        ex=(int)p.getX()*16;
        ey=(int)p.getY()*16;
        canChange=false;
    }

    /**tells what tile the player is on*/
    public Point getCords()
    {
        return new Point((int)Math.round(x/16.0),(int)Math.round(y/16.0));
    }

    public boolean getCC()
    {
        return canChange;
    }
}
