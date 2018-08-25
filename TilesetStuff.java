import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.util.*;
import java.awt.geom.AffineTransform;
public class TilesetStuff
{
    public static void TilesetToTiles()
    {
        BufferedImage start;
        JFileChooser filer= new JFileChooser("Images/TilesetsToBeConverted");
        try
        {
            filer.showDialog(new JFrame(),"Choose a file");
            filer.setControlButtonsAreShown(false);
            start= ImageIO.read(new File(filer.getSelectedFile().getPath()));
            String partOne=STH.changeToUsableName(filer.getSelectedFile().getPath());
            int w=start.getWidth()/16;
            int h=start.getHeight()/16;
            int name=0;
            for(int i=0;i/16<h;i+=16)
            {
                for(int j=0;j/16<w;j+=16)
                {
                    try
                    {
                        BufferedImage temp=start.getSubimage(j,i,16,16);
                        if(isValid(temp))
                        {
                            ImageIO.write(temp,"png",new File("images/tiles/"+partOne+"_"+name+".png"));
                        }
                    }
                    catch(Exception boi)
                    {
                        boi.printStackTrace();
                    }
                    name++;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private static boolean isValid(BufferedImage img) throws Exception {
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        int[] pixels = new int[w * h];
        PixelGrabber pg = new PixelGrabber(img, 0, 0, w, h, pixels, 0, w);
        pg.grabPixels();
        boolean isValid = false;
        for (int pixel : pixels) {
            Color color = new Color(pixel);
            if (color.hashCode() !=-16777216) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }
    
    public static void createTileSet(String name,Screen[] boi)
    {
        ArrayList<String> init= new ArrayList<String>();
        for(int i=0;i<boi.length;i++)
        {
            Tile[][] temp=boi[i].getTiles();
            for(int j=0;j<temp.length;j++)
            {
                for(int k=0;k<temp[j].length;k++)
                {
                    if(!init.contains(STH.changeToUsableName(temp[j][k].getType())+temp[j][k].getRotation()))
                    {
                        init.add(STH.changeToUsableName(temp[j][k].getType())+temp[j][k].getRotation());
                    }
                }
            }
        }
        
        int guy=(int)Math.ceil(Math.sqrt(init.size()));
        Tile[][] temp = new Tile[guy][guy];
        int c=0;
        BufferedImage result = new BufferedImage(guy*16,guy*16,BufferedImage.TYPE_INT_RGB);
        Graphics g = result.getGraphics();
        for(int i=0;i<guy;i++)
        {
            for(int j=0;j<guy;j++,c++)
            {
                if(c>=guy)
                {
                    break;
                }
                temp[i][j]= new Tile(init.get(c).substring(0,init.get(c).length()-1),false);
                BufferedImage hell = temp[j][i].getImage();
                double rotationRequired = Math.toRadians(90*temp[j][i].getRotation());
                AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, 8, 8);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

                g.drawImage(op.filter(hell, null), i*16, j*16, null);
            }
        }
        
        
        try
        {
            ImageIO.write(result,"png",new File("Images/tilesets/"+name+".png"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
