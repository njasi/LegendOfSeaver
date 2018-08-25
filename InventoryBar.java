import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.imageio.*;
public class InventoryBar
{
    Image bar;
    Image resize;
    public InventoryBar()
    {
        try
        {
            int zoom=GameFrame.getZoom();
            bar=ImageIO.read(new File("images/other/bar.png"));
            resize= bar.getScaledInstance(bar.getWidth(null)*zoom,bar.getHeight(null)*zoom,0);
        }
        catch(Exception e){e.printStackTrace();}
    }
    
    public void draw(Graphics g,boolean h)
    {
        int zoom=GameFrame.getZoom();
        if(h)
        {
            g.drawImage(resize,0+zoom,0,null);
        }
        else
        {
            g.drawImage(resize,GameFrame.extra(),0,null);
        }
    }
}
