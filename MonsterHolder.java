import java.io.*;
import java.awt.*;
/**
 * This is used to hold monsters in the editor
 */
public class MonsterHolder implements Serializable
{
    private Image monster;
    static int zoom=CreatorDriver.getFrame().getZoom();
    String type;//type of monster
    int x,y,hp,channel=-1;//channel if it will not appear at start, when channel is active they will appear
    boolean appearsAtStart=true;//if false the thing is not present until channel turns active
    public MonsterHolder(Image img)
    {
        monster=img;
        appearsAtStart=true;
    }
    
    public MonsterHolder(Image img,String type)
    {
        monster=img;
        this.type=type;
    }
    
    public void setCordinates(int xx,int yy){
        x=xx;
        y=yy;
    }
    
    public void setCordinates(Point p){
        x=(int)p.getX();
        y=(int)p.getY();
    }
    //if a monster is not given a hp it will revert to its type's default
    public void setHp(int HP)
    {
        hp=HP;
    }
    
    public void setChannel(int chan){
        channel=chan;
    }
    
    public void setAppearAtStart(boolean appears){
        appearsAtStart=appears;
    }
    
    public void setType(String newType){
        type=newType;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
       return y; 
    }
    
    public int getHP(){
       return hp; 
    }
    
    public int getChannel(){
       return channel; 
    }
    
    public Image getImage(){
       return monster; 
    }
    
    public boolean appearsAtStart(){
        return appearsAtStart;
    }
    
    public String getType(){
        return type;
    }
    
    public void draw(Graphics g){
        g.drawImage(monster,x*zoom*16+zoom,(y)*zoom*16,null);
    }
    
    public static void updateZoom(){
        zoom = CreatorDriver.getFrame().getZoom();
    }
    
    public String toString(){
        return type+"\t("+x+","+y+")\tHP:"+hp+"\tChannel"+channel;
    }
    
    public boolean isAt(Point p){
        return x==(int)p.getX()&&y==(int)p.getY();
    }
}
