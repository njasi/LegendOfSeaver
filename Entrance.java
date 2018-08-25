
/**
 * Write a description of class Entrance here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Entrance extends Special
{
    private boolean bombable;
    private boolean open=true;
    private int channel;
    public Entrance(int xx,int yy,String namey,String path,boolean b)
    {
        super(xx,yy,namey,path);
        bombable=b;
    }
    
    public void setLockOnEnter(int chan)
    {
        channel=chan;
    }
    
    public void trigger()//open door based on channel
    {
        open=false;
    }
    
    public int getChan()
    {
        return channel;
    }
}
