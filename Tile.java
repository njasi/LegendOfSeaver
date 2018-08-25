import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
public class Tile implements Serializable
{
    private BufferedImage tile;
    private BufferedImage resize;
    String type;
    int rotation=0,bigness=1;
    boolean png,big=false;
    static int zoom = CreatorDriver.getFrame().getZoom();
    static int ext=CreatorDriver.getFrame().extra();
    int pZoom = CreatorDriver.getFrame().getZoom();
    public Tile(String s,boolean test)
    {
        type=s;
        png=test;
        try
        {
            int zoom = CreatorDriver.getFrame().getZoom();
            if(test)
            {
                tile = ImageIO.read(new File("images/tiles/"+s));
            }
            else
            {
                tile = ImageIO.read(new File("images/tiles/"+s+".png"));
            }
        }
        catch(Exception e)
        {
            //e.printStackTrace();
            //System.out.println("The Trouble Maker:+\t"+s);
        }
    }

    public Tile(String s,int r,boolean test)
    {
        type=s;
        png=test;
        rotation=r;
        try
        {
            if(test)
            {
                tile = ImageIO.read(new File("images/tiles/"+s));
            }
            else
            {
                tile = ImageIO.read(new File("images/tiles/"+s+".png"));
            }
            tile=rotateBy(90*r);
        }
        catch(Exception e)
        {
            //e.printStackTrace();
            //System.out.println("The Trouble Maker:+\t"+s);
        }
    }

    public void draw(Graphics2D g,int x,int y,boolean h)
    {
        try
        {
            ext = CreatorDriver.getFrame().extra();
            if(pZoom!=zoom)
            {
                pZoom=zoom;
            }
            if(h)
            {
                //g.drawImage(resize,x+ext,y,null);
                g.drawImage(tile, x+ext, y, x+ext+16*zoom*bigness, y+16*zoom*bigness, 0, 0, 16, 16, null);
            }
            else
            {
                //g.drawImage(resize,x+zoom,y,null);
                g.drawImage(tile, x+zoom, y, x+zoom+16*zoom*bigness, y+16*zoom*bigness, 0, 0, 16, 16, null);
            }
        }
        catch(Exception e)
        {
        }
    }

    public void resize()
    {
        resize= (BufferedImage)((Image)tile).getScaledInstance(16*CreatorDriver.getFrame().getZoom(),16*CreatorDriver.getFrame().getZoom(),0);
    }

    public BufferedImage getImage()
    {
        return toBufferedImage(tile);
    }

    public void setRotation(int r)
    {
        tile=rotateBy(90*r);
        rotation=r;
    }

    public int getRotation()
    {
        return rotation;
    }

    public String getType()
    {
        return type;
    }

    public String toString()
    {
        if(!png)
        {
            return type+".png";
        }
        else
        {
            return type;
        }
    }

    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }

    public void setBig(int space)
    {
        big=true;
        bigness=space;
    }

    public static void updateZoom()
    {
        zoom = CreatorDriver.getFrame().getZoom();
    }

    public boolean equals(Tile hello)
    {
        return type.equals(hello.getType());
    }

    public BufferedImage rotateBy(double degrees) {
        try{
            int w = tile.getWidth();
            int h = tile.getHeight();
            double rads = Math.toRadians(degrees);

            BufferedImage rotated = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = rotated.createGraphics();

            AffineTransform at = new AffineTransform();
            int x = w / 2;
            int y = h / 2;
            at.rotate(rads, x, y);
            g2d.setTransform(at);
            g2d.drawImage(tile, 0, 0, null);
            g2d.dispose();

            return rotated;
        }catch(Exception e){
        }
        return null;
    }
}
