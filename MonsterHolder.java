import java.io.*;
import java.awt.*;
/**
 * This is used to hold monsters in the editor
 */
public class MonsterHolder implements Serializable
{
    private Image monster;
    String type;//type of monster
    int x,y,hp,channel;//channel if it will not appear at start, when channel is active they will appear
    boolean appearsAtStart;//if false the thing is not present until channel turns active
    public MonsterHolder(String givenType)
    {
        type=givenType;
    }
    
    public void setCordinates(int xx,int yy){
        x=xx;
        y=yy;
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
    
    public boolean appearsAtStart(){
        return appearsAtStart;
    }
    
    public String getType(){
        return type;
    }
}
