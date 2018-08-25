import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
public class Selector
{
    private BufferedImage sel1;
    private BufferedImage sel2;
    private Image resize1;
    private Image resize2;
    private int time=1;
    int count=0;
    int zoom = CreatorDriver.getFrame().getZoom();
    public Selector()
    {
        try
        {
            sel1 = ImageIO.read(new File("images/other/selector.png"));
            sel2 = ImageIO.read(new File("images/other/selector2.png"));
            resize1 = sel1.getScaledInstance(18*zoom,18*zoom,0);
            resize2 = sel2.getScaledInstance(18*zoom,18*zoom,0);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g,int x, int y)
    {
        try
        {
            if(zoom!=CreatorDriver.getFrame().getZoom())
            {
                zoom=CreatorDriver.getFrame().getZoom();
                resize1 = sel1.getScaledInstance(18*zoom,18*zoom,0);
                resize2 = sel2.getScaledInstance(18*zoom,18*zoom,0);
            }
        }catch(Exception e)
        {
        }
        count++;
        if(count==10)
        {
            time*=-1;
            count=0;
        }
        if(time==1)
        {
            g.drawImage(resize1,((x*16)*zoom),((y*16-1)*zoom),null);
        }
        else
        {
            g.drawImage(resize2,((x*16)*zoom),((y*16-1)*zoom),null);
        }
    }
}
