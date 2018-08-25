import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.imageio.*;
public class Item
{
    boolean Moveable;
    int amount;
    Image icon;
    Image resize;
    /**
     * use this constructor if an item has a certain amount (like bombs)
     */
    public Item(boolean move,int amount,String iconName)
    {
        this.amount = amount;
        Moveable=move;
        try
        {
            int zoom=GameFrame.getZoom();
            icon=ImageIO.read(new File("images/items/"+iconName));
            resize= icon.getScaledInstance(icon.getWidth(null)*zoom,icon.getHeight(null)*zoom,0);
        }
        catch(Exception e){e.printStackTrace();}
    }

    /**
     * Use this constructor when an item has unlimited uses
     */
    public Item(boolean move,String iconName)
    {
        Moveable=move;
        try
        {
            int zoom=GameFrame.getZoom();
            icon=ImageIO.read(new File("images/items/"+iconName));
            resize= icon.getScaledInstance(icon.getWidth(null)*zoom,icon.getHeight(null)*zoom,0);
        }
        catch(Exception e){e.printStackTrace();}
    }
}
