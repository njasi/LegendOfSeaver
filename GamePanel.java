import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * @SuppressWarnings("unchecked")
 */
public class GamePanel extends JPanel
{
    //test comment for git script on windows
    //Another comment
    //Last comment
    //back to test comments
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private boolean w=false,a=false,s=false,d=false,showObs=false;//handles movement and stuff
    private int framesCount = 0,fps=0,x=0,dn=0,xm=0,ym=0,xs=0,ys=0,zoom=GameFrame.getZoom(),ext=GameFrame.extra(),wait=0,count=0;
    private static double width = screenSize.getWidth(),height = screenSize.getHeight();
    private long now,framesTimer=0;
    private String levelName="blank";
    private Image screen;
    private Door[] doors= new Door[0];//dont worry about this guy he's just here so that we dont get an error for the time being
    private Screen obs= new Screen(14,16,"filler");//here are your obstructions in screen form, you should make a method that converts the Screen into an int[][] like previously mentioned
    private Player seaver= new Player(8,8);
    public GamePanel()
    {
        //changeScreen(levelName);
        addMouseListener(new MyMouseListener());
        setBackground(Color.black);
        addKeyListener(new KeyListenBoy());
        setFocusable(true);
        requestFocus();
        setBackground(Color.black);
    }

    public void paintComponent(Graphics t)
    {  
        //System.out.println("running"+System.nanoTime());
        //starting it out
        super.paintComponent(t);
        Graphics2D g=(Graphics2D)t;//switch to 2D graphics
        g.setFont(new Font("hi",0,zoom*3));//set font
        g.setColor(Color.WHITE);//white font
        //drawing stuff below
        g.drawString("Doors: "+dn+" "+doors.length,0,zoom*6);//door info
        g.drawString(FPS(),0,zoom*3);//get FPS
        g.drawString(seaver.getCords().toString(),0,zoom*9);//get FPS
        g.drawString(""+seaver.touchingEdge(),0,zoom*12);//get FPS
        g.drawImage(screen,ext,16*zoom,null);//draws background
        if(showObs){
            obs.draw(g);//shows obs, this slows down the game, but good for testing
            System.out.println(obs);
        }
        seaver.draw(g);//draws player
        mover();//gets how player should move
        seaver.move(xm,ym);//moves player
        //checking for stuff here
        for(int i=0;i<doors.length;i++)/*doors*/{
            if(seaver.getCC() &&doors[i].isAt(seaver.getCords())){
                seaver.teleportTo(doors[i].getExit());
                load(doors[i].getDestination());
                break;
            }
        }
        count++;
        if(count>20){
            count=0;
            if(fps>65){
                wait++;
            }else if(fps<55){
                wait--;
            }
        }
        try{
            Thread.sleep(wait);
        }
        catch(Exception e)
        {}
        repaint();
    }

    public static double screenX()
    {
        return width;
    }

    public static double screenY()
    {
        return height;
    }

    public String FPS()
    {
        long beforeTime = System.nanoTime();
        //... Update program & draw program...
        // DRAW FPS: 
        now=System.currentTimeMillis(); 
        framesCount++; 
        if(now-framesTimer>1000)
        { 
            framesTimer=now; 
            fps=framesCount; 
            framesCount=0; 
        }
        return "FPS: "+fps;
    }

    public void mover()
    {
        if(a&&d)    {xm=0;}
        else if(a)  {xm=-1;}
        else if(d)  {xm=1;}
        else        {xm=0;}
        if(w&s)     {ym=0;}
        else if(w)  {ym=-1;}
        else if(s)  {ym=1;}
        else        {ym=0;}
    }

    /**used to load screens for now, just temporary*/
    public void load(ScreenPackage stuff)
    {
        levelName=stuff.getName();
        changeScreen(STH.changeToUsableName(levelName));
        System.out.println(STH.StringArrayToString(stuff.getObs()));
        obs=new Screen(stuff.getObs(),stuff.getObsR(),true);
        String[] boi=stuff.getDoors();
        doors=new Door[boi.length];
        for(int i=0;i<boi.length;i++)
        {
            doors[i]=new Door(boi[i]);
        }
        dn=stuff.howManyDoors();
        ys=0;//these are for scrolling (yScroll)
        xs=0;//these are for scrolling (xScroll)
    }

    /**used to load screens for now, just temporary*/
    public void load(String stu)
    {
        ScreenPackage stuff=new ScreenPackage();
        stuff.load("ScreenSaves/"+stu+".ser");
        levelName=stuff.getName();
        changeScreen(stu);
        obs=new Screen(stuff.getObs(),stuff.getObsR(),true);
        String[] boi=stuff.getDoors();
        doors=new Door[boi.length];
        for(int i=0;i<boi.length;i++)
        {
            doors[i]=new Door(boi[i]);
        }
        dn=stuff.howManyDoors();
        ys=0;//these are for scrolling (yScroll)
        xs=0;//these are for scrolling (xScroll)
    }

    public void changeScreen(String change)
    {
        try{
            change = STH.removeExtension(change);
            if(System.getProperty("os.name").indexOf("Windows")>=0){
                int index = change.lastIndexOf("\\");
                change = change.substring(index+1);
            }
            screen =ImageIO.read(new File("images/screens/"+change+".png"));
            screen= screen.getScaledInstance(screen.getWidth(null)*zoom,screen.getHeight(null)*zoom,1);
        }
        catch(Exception e)
        {
            System.out.println(change);
            e.printStackTrace();
        }
    }

    public class MyMouseListener extends MouseAdapter
    {
        public void mousePressed(MouseEvent e)
        {
            int xx=e.getX();
            int yy=e.getY();
        }
    }
    public class KeyListenBoy implements KeyListener
    {
        public void keyReleased(KeyEvent e)
        {
            int key=e.getKeyCode();
            if(key==KeyEvent.VK_W)
            {
                w=false;
            }
            else if(key==KeyEvent.VK_S)
            {
                s=false;
            }
            else if(key==KeyEvent.VK_D)
            {
                d=false;
            }
            else if(key==KeyEvent.VK_A)
            {
                a=false;
            }
            else if(key==KeyEvent.VK_O)
            {
                showObs=false;
            }
        }

        public void keyPressed(KeyEvent e)
        {
            int key=e.getKeyCode();
            System.out.println(key);
            if(key==KeyEvent.VK_W)
            {
                w=true;
            }
            else if(key==KeyEvent.VK_S)
            {
                s=true;
            }
            else if(key==KeyEvent.VK_D)
            {
                d=true;
            }
            else if(key==KeyEvent.VK_A)
            {
                a=true;
            }
            else if(key==KeyEvent.VK_O)
            {
                showObs=true;
            }
            else if(key==KeyEvent.VK_L)//will be removed later 
            {
                ScreenPackage saver=new ScreenPackage();
                JFileChooser filer= new JFileChooser("ScreenSaves");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Levels", "ser");
                filer.setFileFilter(filter);
                Component j = null;
                
                try
                {
                    int cancel=filer.showDialog(CreatorDriver.getFrame(),"Choose a file");
                    if(cancel==1){
                        return;
                    }
                    filer.setControlButtonsAreShown(false);
                    //filer.setFileView();
                    saver.changeName(filer.getSelectedFile().getPath());
                    saver.load(filer.getSelectedFile().getPath());
                    DontRunMe.getFrame().getPanel().load(saver);
                }
                catch(Exception w)
                {w.printStackTrace();}
            }
        }

        public void keyTyped(KeyEvent e)
        {

        }
    }
}
