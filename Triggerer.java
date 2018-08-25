import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
public abstract class Triggerer extends Special
{
    boolean triggering=false;
    int channel;
    public Triggerer(int xx,int yy,String namey,String path)
    {
        super(xx,yy,namey,path);
    }
    
    public abstract void trigger();
}
