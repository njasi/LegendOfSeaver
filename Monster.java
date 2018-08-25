
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
public abstract class Monster
{
    static int zoom=GameFrame.getZoom();
    int x,y;
    String name;
    Image boi;
    public Monster(String n,int xx,int yy)
    {
        name=n;
        x=xx;
        y=yy;
        try{
            boi = ImageIO.read(new File("images/monsters/"+n+".png"));
            boi = boi.getScaledInstance(boi.getWidth(null)*zoom,boi.getHeight(null)*zoom,1);
        }
        catch(Exception e){}
    }

    public abstract void move();
    
    public abstract void attack();
    
    public void draw(Graphics2D g)
    {
        g.drawImage(boi,x,y,null);
        move();
    }
    
    public String toString()
    {
        return name+"#"+x+"#"+y+"#";
    }
}

