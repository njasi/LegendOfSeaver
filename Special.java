import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
public abstract class Special
{
    private int x,y,chan;
    private String name;
    private Image thing;
    private boolean triggering;
    public Special(int xx,int yy,String namey,Image boi)
    {
        x=xx;
        y=yy;
        name=namey;
        thing=boi;
    }

    public Special(int xx,int yy,String namey,String path)
    {
        x=xx;
        y=yy;
        name=namey;
        try
        {
            thing= ImageIO.read(new File(path));
        }catch(Exception e){}
    }

    public Point getPoint()
    {
        return new Point(x,y);
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
    
    public boolean isChan(int channel)
    {
        return chan==channel;
    }
    
    public void setChan(int boiiii)
    {
        chan=boiiii;
    }
    
    public int getChan()
    {
        return chan;
    }
    
    public abstract void trigger();//TRIGGERED
}
