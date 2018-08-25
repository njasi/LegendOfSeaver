import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
public class SideBar
{
    static int width = (int)TestFrame.getW(), x=12,y=0,verticalScroll=0,stage=0;
    static int zoom=CreatorDriver.getFrame().getZoom(),end =(257)*zoom,spaceLeft=width-end;
    static int ArrayWidth=(spaceLeft)/(32*zoom);
    static int length= new File("images/tiles").listFiles().length;
    static Tile[][] displayed =new Tile[1+(int)Math.ceil((length+0.0)/(ArrayWidth+0.0))][ArrayWidth];
    static Selector hello= new Selector();
    static String selected;
    public SideBar()
    {
        int c=1;
        File folder = new File("images/tiles");
        File[] listOfFiles = folder.listFiles();
        for(int i=0;i< displayed.length;i++)
        {
            for(int j =0;j<displayed[i].length;j++)
            {
                if(c<length)
                {
                    displayed[i][j]= new Tile(listOfFiles[c].getName(),true);
                }
                c++;
            }
        }
    }

    /**
     * Draws the entire sidebar, includes a rectangle, the tiles,
     * and a string that shows the name of the tile selected
     */
    public void draw(Graphics2D g)
    {
        zoom=CreatorDriver.getFrame().getZoom();
        g.setColor(new Color(100,100,100));
        g.fillRect((257)*zoom,0,(zoom*16),(int)TestFrame.getH());
        g.fillRect((257)*zoom+(ArrayWidth+1)*16*zoom,0,spaceLeft,(int)TestFrame.getH());
        if(stage==0||stage==1)
        {
            g.setColor(Color.black);
            disp(g);
            try
            {
                g.drawString("Selected: "+displayed[y][x-12].toString(),0,g.getFont().getSize()*4);
            }
            catch(Exception e)
            {
                g.drawString("Error",0,g.getFont().getSize()*4);
            }
            hello.draw(g,x+5,y+verticalScroll+1);
        }
    }

    /**
     * draws out the tiles currently being dsiplayed
     */
    private void disp(Graphics2D g)
    {
        int c=0;
        for(int i=0; i<displayed.length;i++)
        {
            for(int j=0;j<displayed[i].length;j++)
            {
                if(displayed[i][j]!=null)
                {
                    int x=((j+1)*16*zoom+(256)*zoom);//+4;
                    displayed[i][j].draw(g,x,(i+verticalScroll)*16*zoom+16*zoom,false);
                }
                c++;
            }
        }
    }

    /**
     * Changes the selected tile being displayed
     */
    public void setSelected(int xx,int yy)
    {
        x=xx+12;
        if(yy<displayed.length-1)
        {
            y=yy;
            if(y>14+(-1*verticalScroll))
            {
                verticalScroll--;
            }
            else if(y+verticalScroll==-1)
            {
                verticalScroll++; 
            }
        }
        selected=getSelected();
    }
    
    /**
     * Changes the selected tile being displayed
     * given a tile name
     */
    public void setSelected(String name)
    {
        selected=name;
    }

    /**
     * Gets the name of tile's image that is currently selected by the side bar
     */
    public static String getSelected()
    {
        try
        {
            if(stage==0||stage==1)
            {
                return displayed[y][x-12].toString();
            }
            else
            {
                return selected;
            }
        }
        catch(Exception e)
        {
            return "unknown.png";
        }
    }

    public int getWidth()
    {
        return displayed[0].length-1;
    }

    public int getLength()
    {
        return displayed.length-1;
    }

    public void setStage(int i)
    {
        stage=i;
    }

    public int getStage()
    {
        return stage;
    }
}
