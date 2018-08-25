import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import java.util.*;
import java.awt.geom.AffineTransform;
public class Screen  implements Serializable
{
    Tile[][] tiles;
    int x,y,c=0;
    Image tile;
    Image resize;
    boolean obs=false;
    public Screen(String[][] init,boolean png)
    { 
        tiles= new Tile[init.length][init[0].length];
        for(int i=0;i<init.length;i++)
        {
            for(int j=0;j<init[0].length;j++)
            {
                tiles[i][j]=new Tile(init[i][j],png);
            }
        }
    }

    public Screen(String[][] init, int[][] rot ,boolean png)
    { 
        tiles= new Tile[init.length][init[0].length];
        for(int i=0;i<init.length;i++)
        {
            for(int j=0;j<init[0].length;j++)
            {
                Tile temp=new Tile(init[i][j],rot[i][j],png);
                tiles[i][j]=temp;
            }
        }
    }

    public Screen(int x,int y,String s)
    { 
        tiles= new Tile[x][y];
        for(int i=0;i<x;i++)
        {
            for(int j=0;j<y ;j++)
            {
                tiles[i][j]=new Tile(s,false);
            }
        }
        obs=true;
    }

    public void draw(Graphics2D g)
    {
        int zoom=TestFrame.getZoom();
        for(int i=0;i<tiles.length;i++)
        {
            for(int j=0;j<tiles[i].length;j++)
            {
                try
                {
                    tiles[i][j].draw(g,(j)*zoom*16,+(i+1)*zoom*16,true);
                }
                catch(Exception e)
                {
                }
            }
        }
    }

    public void draw(Graphics2D g,boolean h)
    {
        int zoom=TestFrame.getZoom();
        for(int i=0;i<tiles.length;i++)
        {
            for(int j=0;j<tiles[i].length;j++)
            {
                try
                {
                    tiles[i][j].draw(g,(j)*zoom*16,+(i+1)*zoom*16,true);
                }
                catch(Exception e)
                {
                }
            }
        }
    }

    public void draw(Graphics2D g,boolean h,int x,int y)
    {
        int zoom=TestFrame.getZoom();
        for(int i=0;i<14;i++)
        {
            for(int j=0;j<16;j++)
            {
                try
                {
                    tiles[i+y][j+x].draw(g,(j)*zoom*16,+(1+i)*zoom*16,h);
                }
                catch(Exception e)
                {
                }
            }
        }
    }

    public void setTile(int x, int y, String s,boolean h,int r)
    {
        tiles[y-1][x]=new Tile(s,h);
        tiles[y-1][x].setRotation(r);
    }
    
    public void setTile(Point p,String s,int r)
    {
        tiles[(int)p.getY()-1][(int)p.getX()]= new Tile(s,true);
        tiles[(int)p.getY()-1][(int)p.getX()].setRotation(r);
    }

    public String getTile(int x, int y)
    {
        return tiles[y-1][x].toString();
    }
    
    public Tile getTile(Point p)
    {
        return tiles[(int)p.getY()-1][(int)p.getX()];
    }
    
    public int getLength()
    {
        return tiles.length;
    }

    public int getWidth()
    {
        return tiles[0].length;
    }

    public String getDimensions()
    {
        return tiles.length+" by "+tiles[0].length;
    }

    public void addRow(boolean onTop,String fill)
    {
        Tile[][] temp=new Tile[tiles.length+1][tiles[0].length];
        if(onTop)
        {
            for(int i=0;i<tiles.length;i++)
            {
                for(int j=0;j<tiles[i].length;j++)
                {
                    temp[i+1][j]=tiles[i][j];
                }
            }
            for(int i=0;i<tiles[0].length;i++)
            {
                temp[0][i]=new Tile(fill,false);
            }
        }
        else
        {
            for(int i=0;i<tiles.length;i++)
            {
                for(int j=0;j<tiles[i].length;j++)
                {
                    temp[i][j]=tiles[i][j];
                }
            }
            for(int i=0;i<tiles[0].length;i++)
            {
                temp[tiles.length][i]=new Tile(fill,false);
            }
        }
        tiles=temp;
    }

    public void addCol(boolean right,String fill)
    {
        Tile[][] temp=new Tile[tiles.length][tiles[0].length+1];
        if(right)
        {
            for(int i=0;i<tiles.length;i++)
            {
                for(int j=0;j<tiles[i].length;j++)
                {
                    temp[i][j]=tiles[i][j];
                }
            }
            for(int i=0;i<tiles.length;i++)
            {
                temp[i][tiles[0].length]=new Tile(fill,false);
            }
        }
        else
        {
            for(int i=0;i<tiles.length;i++)
            {
                for(int j=0;j<tiles[i].length;j++)
                {
                    temp[i][j+1]=tiles[i][j];
                }
            }
            for(int i=0;i<tiles.length;i++)
            {
                temp[i][0]=new Tile(fill,false);
            }
        }
        tiles=temp;
    }

    public void writeToPNG(String name,Screen deco)
    {
        BufferedImage result = new BufferedImage(tiles[0].length*16,tiles.length*16,BufferedImage.TYPE_INT_RGB);
        Graphics g = result.getGraphics();
        for(int i=0;i<tiles[0].length;i++)
        {
            for(int j=0;j<tiles.length;j++)
            {
                BufferedImage temp = tiles[j][i].getImage();
                double rotationRequired = Math.toRadians(90*tiles[j][i].getRotation());
                AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, 8, 8);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

                g.drawImage(op.filter(temp, null), i*16, j*16, null);
                //g.drawImage(temp,i*16,j*16,null);
            }
        }
        Tile[][] d= deco.getTiles();
        for(int i=0;i<d[0].length;i++)
        {
            for(int j=0;j<d.length;j++)
            {
                BufferedImage temp = d[j][i].getImage();
                double rotationRequired = Math.toRadians(90*d[j][i].getRotation());
                AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, 8, 8);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

                g.drawImage(op.filter(temp, null), i*16, j*16, null);
                //g.drawImage(temp,i*16,j*16,null);
            }
        }

        try
        {
            ImageIO.write(result,"png",new File("images/screens/"+name+".png"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String[][] toStringArray()
    {
        String[][] re= new String[tiles.length][tiles[0].length];

        for(int i=0;i<tiles.length;i++)
        {
            for(int j=0;j<tiles[0].length;j++)
            {
                re[i][j]=tiles[i][j].toString();
            }
        }
        return re;
    }

    public int[][] rotationArray()
    {
        int[][] re= new int[tiles.length][tiles[0].length];

        for(int i=0;i<tiles.length;i++)
        {
            for(int j=0;j<tiles[0].length;j++)
            {
                re[i][j]=tiles[i][j].getRotation();
            }
        }
        return re;
    }

    public Tile[][] getTiles()
    {
        return tiles;
    }

    public String toString()
    {
        String[][] boi=toStringArray();
        String re="";
        for(int i=0;i<boi.length;i++)
        {
            for(int j=0;j<boi[0].length;j++)
            {
                re+=tiles[i][j].toString()+"\t";
            }
            re+="\n";
        }
        return re;
    }

    public void fillTiles(Tile target, Point start, String diff,int r)
    {
        if(target.getType().equals(diff))
        {
            return;
        }
        setTile(start,diff,r);
        if((int)start.getX()+1<tiles[0].length&&tiles[(int)start.getY()-1][(int)start.getX()+1].equals(target))
        {
            fillTiles(target,new Point((int)start.getX()+1,(int)start.getY()),diff,r);
        }
        
        if((int)start.getX()-1>=0&&tiles[(int)start.getY()-1][(int)start.getX()-1].equals(target))
        {
            fillTiles(target,new Point((int)start.getX()-1,(int)start.getY()),diff,r);
        }
        
        if((int)start.getY()<tiles.length&&tiles[(int)start.getY()][(int)start.getX()].equals(target))
        {
            fillTiles(target,new Point((int)start.getX(),(int)start.getY()+1),diff,r);
        }
        
        if((int)start.getY()!=1&&tiles[(int)start.getY()-2][(int)start.getX()].equals(target))
        {
            fillTiles(target,new Point((int)start.getX(),(int)start.getY()-1),diff,r);
        }
    }
}