import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.filechooser.FileNameExtensionFilter;
public class TestPanel extends JPanel
{
    boolean w=false,a=false,s=false,d=false,f=false,recent=false,ind=true,placingDoor=false,control=false,alt=false;
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static double width = screenSize.getWidth(),height = screenSize.getHeight();
    private static int x=0,xm=0,ym=0,xs=0,ys=0,sx=0,sy=0,counter=0,c2=0,type=1,rotate=0,dn=0,zoom;
    private long now,framesTimer=0;
    private int framesCount=0,fps;
    private InventoryBar testBar= new InventoryBar();
    Screen test= new Screen(14,16,"unknown"),obs= new Screen(14,16,"filler"),deco= new Screen(14,16,"filler");
    //Player seaver= new Player();
    Selector sell= new Selector();
    SideBar bar= new SideBar();
    String changeTo="unknown.png",levelName,ss,doorInfo="",tops="empty",rights="empty",lefts="empty",bottoms="empty";
    Image up,down,left,right,door,fadedDoor,cornerInd;
    Point old=null,p,doorTo,doorFrom,TR= new Point(),BL;
    MyMouseListener clicky=new MyMouseListener();
    Door[] doors= new Door[0];
    MonsterHolder[] monsters = new MonsterHolder[0];
    Point[] faded = new Point[0];
    ScreenPackage currentScreen,saver= new ScreenPackage();
    TestPanel it=this;
    JOptionPane lee= new JOptionPane();
    ImprovedJOptionPane jasinski= new ImprovedJOptionPane();
    MonsterSelector monsel = new MonsterSelector();
    LevelEdges edger;
    SpecialSelector selectorSpecial= new SpecialSelector();
    public TestPanel()
    {
        zoom=CreatorDriver.getFrame().getZoom();
        //addMouseListener(new MyMouseListener());
        setBackground(Color.white);
        addKeyListener(new KeyListenBoy());
        addMouseListener(clicky);
        setFocusable(true);
        requestFocus();
        try
        {
            up =ImageIO.read(new File("images/other/up1.png")).getScaledInstance(160*zoom,3*zoom,1);
            down =ImageIO.read(new File("images/other/up2.png")).getScaledInstance(160*zoom,3*zoom,1);
            left =ImageIO.read(new File("images/other/side1.png")).getScaledInstance(3*zoom,128*zoom,1);
            right =ImageIO.read(new File("images/other/side2.png")).getScaledInstance(3*zoom,128*zoom,1);
            door =ImageIO.read(new File("images/tiles/door.png")).getScaledInstance(16*zoom,16*zoom,1);
            fadedDoor =ImageIO.read(new File("images/tiles/fadedDoor.png")).getScaledInstance(16*zoom,16*zoom,1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        LevelEdges.setPlaces("empty","empty","empty","empty","empty");
        edger= new LevelEdges();
    }

    public TestPanel(int zoomV)
    {
        zoom=zoomV;
        addMouseListener(new MyMouseListener());
        setBackground(Color.white);
        addKeyListener(new KeyListenBoy());
        addMouseListener(clicky);
        setFocusable(true);
        requestFocus();
        try
        {
            up =ImageIO.read(new File("images/other/up1.png")).getScaledInstance(160*zoom,3*zoom,1);
            down =ImageIO.read(new File("images/other/up2.png")).getScaledInstance(160*zoom,3*zoom,1);
            left =ImageIO.read(new File("images/other/side1.png")).getScaledInstance(3*zoom,128*zoom,1);
            right =ImageIO.read(new File("images/other/side2.png")).getScaledInstance(3*zoom,128*zoom,1);
        }
        catch(Exception e)
        {}
    }

    public void paintComponent(Graphics g)
    {  
        super.paintComponent(g);
        updateZoom();
        Graphics2D g2d = (Graphics2D)g;
        g2d.setFont(new Font("njasi",Font.PLAIN,zoom*3));
        counters();
        test.draw(g2d,!ind,xs,ys);//tiles here
        deco.draw(g2d,!ind,xs,ys);//deco here
        if(bar.getStage()!=0&&bar.getStage()!=1)
        {
            obs.draw(g2d,!ind,xs,ys);//obstructions
        }
        if(bar.getStage()==3)//door stage
        {
            try{
                drawDoors(g2d);//drawsDoors
                drawTempDoors(faded,g2d);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            if(doorAt(p)!=-1)
            {
                doorInfo=doors[doorAt(p)].toString();
            }
            else
            {
                doorInfo="No door here";
            }
        }
        //testBar.draw(g2d,ind);//draws top bar (kinda pointless here)
        p=mover();//where mouse is 
        if(!p.equals(old))//drag draw
        {
            changeBoi();
        }
        old=p;//where mouse was
        //try
        //{
        //    Thread.sleep(10);
        //}
        //catch(Exception e)
        //{}
        drawIndicators(g2d);
        repaint();
    }

    public void counters()
    {
        c2++;
        if(c2==80)
        {
            c2=0;//makes bars flash
        }
    }

    public void drawIndicators(Graphics2D g)
    {
        if(ind)
        {
            try
            {
                sell.draw(g,(int)p.getX(),(int)p.getY());//selector
                Tile temp=new Tile(SideBar.getSelected(),true);
                temp.setRotation(rotate);
                setBackground(Color.white);
                bar.draw(g);//sidebar
                drawArrows(g);
                FPS();
                g.drawString("Size: "+test.getDimensions()+"\tScrolling: ("+xs+","+ys+") ",0,g.getFont().getSize());//text row 1
                g.drawString("Rotation: "+rotate,0,g.getFont().getSize()*2);//text row 2
                g.drawString("Name: "+STH.changeToUsableName(levelName),0,g.getFont().getSize()*3);//text row 3
                g.drawString("Door: "+doorInfo+"    Door#: "+dn+" "+doors.length,("Size: "+test.getDimensions()+"\tScrolling: ("+xs+","+ys+") ").length()*g.getFont().getSize()/2,g.getFont().getSize());//door info
                g.drawString("Cursor: ("+((int)p.getX()+xs)+","+((int)p.getY()+ys)+")",("Size: "+test.getDimensions()+"\tScrolling: ("+xs+","+ys+") ").length()*g.getFont().getSize()/2,g.getFont().getSize()*2);//door info
                g.drawString("FPS: "+fps,0,g.getFont().getSize()*5);
                if(bar.getStage()!=4){
                    try{
                        temp.setBig(4);//makes displayer tile big (may throw error)
                        temp.draw(g,256*zoom+(bar.getWidth()+2)*16*zoom,0,false);
                    }catch(Exception e){}
                }else if(bar.getStage()==4){
                    g.fillRect(257*zoom+(bar.getWidth()+2)*16*zoom,0,100,100);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            Tile temp=new Tile(SideBar.getSelected(),true);
            temp.setRotation(rotate);
            temp.draw(g,257*zoom+(bar.getWidth()+1)*16*zoom/*4*GameFrame.getZoom()+GameFrame.extra()*/,0,true);//257*zoom+(bar.getWidth()+1)*16*zoom
            setBackground(Color.black);
        }
    }

    private void drawArrows(Graphics g)
    {
        int zoom=CreatorDriver.getFrame().getZoom();
        if(ys>0)//top
        {
            if(c2<40)
            {
                g.drawImage(up,zoom,zoom*13,null);
            }
            else
            {
                g.drawImage(down,zoom,zoom*13,null);
            }
        }
        if(ys+14<test.getLength())//bottom
        {
            if(c2<40)
            {
                g.drawImage(up,zoom,zoom*237,null);
            }
            else
            {
                g.drawImage(down,zoom,zoom*237,null);
            }
        }
        if(xs+16<test.getWidth())//right
        {
            if(c2<40)
            {
                g.drawImage(right,zoom+256*zoom,16*zoom,null);
            }
            else
            {
                g.drawImage(left,zoom+256*zoom,16*zoom,null);
            }
        }
        if(xs>0)//left
        {
            if(c2<40)
            {
                g.drawImage(right,0,16*zoom,null);
            }
            else
            {
                g.drawImage(left,0,16*zoom,null);
            }
        }
    }

    private void drawMonsters(){

    }

    public void drawInd(Point pt,Graphics g)
    {
        int dx=(int)pt.getX();
        int dy=(int)pt.getY();
        g.drawImage(cornerInd,(dx-xs)*16*zoom+zoom,(dy-ys)*16*zoom,null);
    }

    public void drawDoors(Graphics g)
    {
        for(int i=0;i<doors.length;i++)
        {
            int dx=(int)doors[i].getPos().getX();
            int dy=(int)doors[i].getPos().getY();
            g.drawImage(door,(dx-xs)*16*zoom+zoom,(dy-ys)*16*zoom,null);
        }
    }

    public void drawTempDoors(Point[] points,Graphics g)
    {
        for(int i=0;i<points.length;i++)
        {
            int dx=(int)points[i].getX();
            int dy=(int)points[i].getY();
            g.drawImage(fadedDoor,(dx-xs)*16*zoom+zoom,(dy-ys)*16*zoom,null);
        }
    }

    public static double screenX()
    {
        return width;
    }

    public static double screenY()
    {
        return height;
    }

    public Point mover()
    {
        try
        {
            int mx=0;
            int my=0;
            if(ind)
            {
                mx=(int)getMousePosition().getX();
                my=(int)getMousePosition().getY();
            }
            else
            {
                mx=(int)getMousePosition().getX()-CreatorDriver.getFrame().extra();
                my=(int)getMousePosition().getY();
            }
            if(mx>15*16*zoom)
            {
                mx=15*16*zoom;
            }
            mx=(mx/(16*zoom));
            if(my<16*zoom)
            {
                my=my+16*zoom;
            }
            my=(my/(16*zoom));
            return new Point(mx,my);
        }
        catch (Exception e)
        {
            return new Point(0,1);
        }
    }

    public JPanel getPanel()
    {
        return this;
    }

    public void changeBoi()
    {
        try
        {
            Point toChange= mover();
            changeTo=SideBar.getSelected();
            bar.setSelected(sx,sy);
            if(bar.getStage()==0&&clicky.down())
            {
                test.setTile((int)toChange.getX()+xs,(int)toChange.getY()+ys,changeTo,true,rotate);
            }
            if(bar.getStage()==2&&clicky.down())
            {
                if(control){
                    obs.setTile((int)toChange.getX()+xs,(int)toChange.getY()+ys,"filler.png",true,rotate);
                }else{
                    obs.setTile((int)toChange.getX()+xs,(int)toChange.getY()+ys,changeTo,true,rotate);
                }
            }
        }
        catch(Exception killMe)
        {
            killMe.printStackTrace();
        }
    }

    public int getZoom()
    {
        return zoom;
    }

    public void addDoor(Point from, String dest, Point to, int type)
    {
        if(dn==doors.length)
        {
            Door[] temp= new Door[doors.length+1];
            for(int i=0;i<doors.length;i++)
            {
                temp[i]=doors[i];
            }
            doors=temp;
        }
        doors[dn]= new Door(from,STH.changeToUsableName(dest),to,type);
        dn++;
    }

    public void addDoors(ArrayList<Point> from, String dest, ArrayList<Point> to, int type)
    {
        from=STH.makeNoDupe(from);
        to=STH.makeNoDupe(to);
        if(dn+from.size()>=doors.length)
        {
            Door[] temp= new Door[doors.length+from.size()];
            for(int i=0;i<dn;i++)
            {
                temp[i]=doors[i];
            }
            doors=temp;
            for(int i=dn;i<temp.length;i++)
            {
                doors[i]= new Door(from.get(i-dn),STH.changeToUsableName(dest),to.get(i-dn),type);
            }
        }
        dn+=from.size();
    }

    private int doorAt(Point p)
    {
        for(int i=0;i<doors.length;i++)
        {
            if(doors[i].isAt(p))
            {
                return i;
            }
        }
        return -1;
    }

    private void updateZoom()//lets the window be resized and everything shrinks
    {
        Tile.updateZoom();
        try
        {
            TestFrame.changeW(CreatorDriver.getFrame().getWidth());
            TestFrame.changeH(CreatorDriver.getFrame().getHeight());
            zoom=CreatorDriver.getFrame().getZoom();
            //CreatorDriver.getFrame().setSize(433*zoom,250*zoom);
        }
        catch(Exception e){}
        if(door.getWidth(null)/16!=zoom)
        {
            try
            {
                door =ImageIO.read(new File("images/tiles/door.png")).getScaledInstance(16*zoom,16*zoom,1);
                fadedDoor =ImageIO.read(new File("images/tiles/fadedDoor.png")).getScaledInstance(16*zoom,16*zoom,1);
            }catch(Exception e){}
        }
    }

    public void changeStage(int stage)
    {
        int ans=1;
        if(stage==0)
        {
            ans =lee.showConfirmDialog(TestFrame.getPanel(),"Edit tiles?","Stages",0);
            if(ans==0)
            {
                bar.setStage(0);
            }
        }
        else if(stage==1)
        {
            ans =lee.showConfirmDialog(TestFrame.getPanel(),"Edit decorations?","Stages",0);
            if(ans==0)
            {
                bar.setStage(1);
            }
        }
        else if(stage==2)
        {
            ans =lee.showConfirmDialog(TestFrame.getPanel(),"Edit obstructions?","Stages",0);
            if(ans==0)
            {
                bar.setSelected("border1.png");
                bar.setStage(2);
            }
        }
        else if(stage==3)
        {
            ans =lee.showConfirmDialog(TestFrame.getPanel(),"Edit doors?","Stages",0);
            if(ans==0)
            {
                boolean booo=true;
                try
                {
                    levelName.equals("five");
                    bar.setStage(3);
                }
                catch(Exception kill)
                {
                    String s=lee.showInputDialog(TestFrame.getPanel(),"Your level has no name, you MUST save before adding doors.","Save",0);
                    while(booo)
                    {
                        if(s!=null&&!s.equals(""))
                        {
                            test.writeToPNG(s,deco);
                            saver=new ScreenPackage(test,obs,deco,doors,s,dn);
                            saver.setPlaces(tops,rights,lefts,bottoms);
                            saver.save();
                            booo=false;
                        }
                        else
                        {
                            s=lee.showInputDialog(TestFrame.getPanel(),"Something happened, try again, Current name is "+STH.changeToUsableName(levelName),"Export as PNG",0);
                        }
                    }
                    levelName=s;
                    ScreenPackage saver=new ScreenPackage();
                    saver.load("ScreenSaves/"+s+".ser");
                    it.load(saver);
                    bar.setStage(3);
                }
            }
        }
        else if(stage==4)//monsters
        {
            ans =lee.showConfirmDialog(TestFrame.getPanel(),"Edit Monsters?","Stages",0);
            if(ans==0)
            {
                bar.setStage(4);
            }
        }
    }

    public void toggleInd()
    {
        if(ind)
        {
            ind=false;
        }
        else
        {
            ind=true;
        }
    }

    public void load(ScreenPackage stuff)
    {
        levelName=stuff.getName();
        test=new Screen(stuff.getTiles(),stuff.getTilesR(),true);
        obs=new Screen(stuff.getObs(),stuff.getObsR(),true);
        deco=new Screen(stuff.getDeco(),stuff.getDecoR(),true);
        String[] boi=stuff.getDoors();
        doors=new Door[boi.length];
        tops=stuff.getTop();
        rights=stuff.getRight();
        lefts=stuff.getLeft();
        bottoms=stuff.getBottom();
        LevelEdges.setPlaces(tops,rights,levelName,lefts,bottoms);
        edger.kill();
        edger= new LevelEdges();
        for(int i=0;i<boi.length;i++)
        {
            doors[i]=new Door(boi[i]);
        }
        dn=stuff.howManyDoors();
        ys=0;
        xs=0;
    }

    public void loadFromMenu()
    {
        saver=new ScreenPackage();
        JFileChooser filer= new JFileChooser("ScreenSaves");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Levels", "ser");
        filer.setFileFilter(filter);
        try
        {
            filer.showDialog(CreatorDriver.getFrame(),"Choose a file");
            filer.setControlButtonsAreShown(false);
            //filer.setFileView();
            saver.changeName(filer.getSelectedFile().getPath());
            saver.load(filer.getSelectedFile().getPath());
            it.load(saver);
        }
        catch(Exception w)
        {
            w.printStackTrace();
        }
    }

    public void saveFromMenu()
    {
        if(STH.changeToUsableName(levelName).equals("null"))
        {
            String s=lee.showInputDialog(TestFrame.getPanel(),"This level has no name, what would you like to call it?","Save Level",0);
            if(s!=null&&!s.equals(""))
            {
                test.writeToPNG(s,deco);
                levelName=s;
                saver.setPlaces(tops,rights,lefts,bottoms);
                saver.save(test,obs,deco,doors,dn,s);
            }
        }
        else
        {
            int s=lee.showConfirmDialog(TestFrame.getPanel(),"This level is called "+STH.changeToUsableName(levelName)+", save it?","Save Level",0);
            if(s==0)
            {
                test.writeToPNG(STH.changeToUsableName(levelName),deco);
                levelName=STH.changeToUsableName(levelName);
                saver.setPlaces(tops,rights,lefts,bottoms);
                saver.save(test,obs,deco,doors,dn,STH.changeToUsableName(levelName));
            }
        }
    }

    public void renameFromMenu(String s)
    {
        levelName= s;
    }

    public MonsterSelector getMonsterSelector()
    {
        return monsel;
    }

    public LevelEdges getLevelEdges()
    {
        return edger;
    }

    public SpecialSelector getSS()
    {
        return selectorSpecial;
    }

    private void FPS()
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
    }

    public void setPlaces(String t,String r,String l,String b)
    {
        tops=t;
        rights=r;
        lefts=l;
        bottoms=b;
    }
    public class MyMouseListener extends MouseAdapter
    {
        boolean down=false,multipleDoors=false;
        int thingy=1,thingyy=1,thingyyy=1,thingyyyy=1;
        ArrayList<Point> doorsTo= new ArrayList<Point>(),doorsFrom= new ArrayList<Point>();
        public void mousePressed(MouseEvent e)
        {
            if(e.getX()<zoom*256)
            {
                down=true;
                saver=new ScreenPackage();//loads the screen the door will go to;
                try{
                    Point toChange= mover();
                    if(bar.getStage()==0)//tiles
                    {
                        if(f&&control)//fill
                        {
                            try
                            {
                                test.fillTiles(test.getTile(toChange),new Point((int)toChange.getX()+xs,(int)toChange.getY()+ys),changeTo,rotate);
                            }
                            catch(Exception j)
                            {

                            }
                        }
                        else if((thingyyy==1)&(!e.isAltDown()))//select first corner of rect
                        {
                            test.setTile((int)toChange.getX()+xs,(int)toChange.getY()+ys,changeTo,true,rotate);
                            changeTo=SideBar.getSelected();
                            bar.setSelected(sx,sy);
                        }
                        else//select second corner of rect and fill
                        {
                            if(thingyyy==0)
                            {
                                BL=STH.getBL(TR,toChange);
                                TR=STH.getTR(TR,toChange);
                                for(int i=0;i<=(int)(BL.getX()-TR.getX());i++)
                                {
                                    for(int j=0;j<=(int)(BL.getY()-TR.getY());j++)
                                    {
                                        test.setTile((int)TR.getX()+xs+i,(int)TR.getY()+ys+j,changeTo,true,rotate);
                                    }
                                }
                                changeTo=SideBar.getSelected();
                                bar.setSelected(sx,sy);
                                thingyyy=1;
                            }
                            else//eg thingyyy==1
                            {
                                TR= toChange;
                                thingyyy=0;
                            }
                        }
                    }
                    else if(bar.getStage()==1)//decorations
                    {
                        if((thingyyyy==1)&(!e.isAltDown()))
                        {
                            if(control){
                                deco.setTile((int)toChange.getX()+xs,(int)toChange.getY()+ys,"filler.png",true,rotate);
                            }else{
                                deco.setTile((int)toChange.getX()+xs,(int)toChange.getY()+ys,changeTo,true,rotate);
                                changeTo=SideBar.getSelected();
                                bar.setSelected(sx,sy);
                            }
                        }
                        else
                        {
                            if(thingyyyy==0)
                            {
                                BL=STH.getBL(TR,toChange);
                                TR=STH.getTR(TR,toChange);
                                for(int i=0;i<=(int)(BL.getX()-TR.getX());i++)
                                {
                                    for(int j=0;j<=(int)(BL.getY()-TR.getY());j++)
                                    {
                                        deco.setTile((int)TR.getX()+xs+i,(int)TR.getY()+ys+j,changeTo,true,rotate);
                                    }
                                }
                                changeTo=SideBar.getSelected();
                                bar.setSelected(sx,sy);
                                thingyyyy=1;
                            }
                            else//eg thingyyyy==1
                            {
                                TR= toChange;
                                thingyyyy=0;
                            }
                        }
                    }
                    else if(bar.getStage()==2)//obstructions
                    {
                        if(e.isControlDown())
                        {
                            if(e.isAltDown())
                            {
                                if(thingyy==0)
                                {
                                    BL=STH.getBL(TR,toChange);
                                    TR=STH.getTR(TR,toChange);
                                    for(int i=0;i<=(int)(BL.getX()-TR.getX());i++)
                                    {
                                        for(int j=0;j<=(int)(BL.getY()-TR.getY());j++)
                                        {
                                            obs.setTile((int)TR.getX()+xs+i,(int)TR.getY()+ys+j,"filler.png",true,rotate);
                                        }
                                    }
                                    changeTo=SideBar.getSelected();
                                    bar.setSelected(sx,sy);
                                    thingyy=1;
                                }
                                else//eg thingyy==1
                                {
                                    TR= toChange;
                                    thingyy=0;
                                }
                            }
                            else
                            {
                                obs.setTile((int)toChange.getX()+xs,(int)toChange.getY()+ys,"filler.png",true,rotate);
                            }
                        }
                        else if(!e.isAltDown())
                        {
                            obs.setTile((int)toChange.getX()+xs,(int)toChange.getY()+ys,"border"+type+".png",true,rotate);
                        }
                        else if(e.isAltDown())
                        {
                            if(thingy==0)
                            {
                                BL=STH.getBL(TR,toChange);
                                TR=STH.getTR(TR,toChange);
                                for(int i=0;i<=(int)(BL.getX()-TR.getX());i++)
                                {
                                    for(int j=0;j<=(int)(BL.getY()-TR.getY());j++)
                                    {
                                        obs.setTile((int)TR.getX()+xs+i,(int)TR.getY()+ys+j,"border"+type+".png",true,rotate);
                                    }
                                }
                                changeTo=SideBar.getSelected();
                                bar.setSelected(sx,sy);
                                thingy=1;
                            }
                            else//eg thingy==1
                            {
                                TR= toChange;
                                thingy=0;
                            }
                        }
                    }
                    else if(bar.getStage()==3)//doors
                    {
                        if(!placingDoor)//does stuff
                        {
                            if(it.doorAt(toChange)==-1){
                                if(multipleDoors)
                                {
                                    if(e.isControlDown())
                                    {
                                        if(lee.showConfirmDialog(TestFrame.getPanel(),"Done Placing?","helloo there",0)==1)//not done
                                        {
                                            placingDoor = false;
                                        }
                                        else //done
                                        {
                                            JFileChooser filer= new JFileChooser("ScreenSaves");
                                            try
                                            {
                                                int doit=1;
                                                while(doit==1)
                                                {
                                                    filer.showDialog(CreatorDriver.getFrame(),"Door to here?");
                                                    ss=filer.getSelectedFile().getPath();
                                                    doit=jasinski.showConfirmDialog(STH.changeToUsableName(ss));
                                                    if(doit==2)
                                                    {
                                                        faded= new Point[0];
                                                        ArrayList<Point> doorsTo= new ArrayList<Point>(),doorsFrom= new ArrayList<Point>();
                                                        placingDoor=false;
                                                        return;
                                                    }
                                                }
                                                currentScreen = new ScreenPackage(test,obs,deco,doors,levelName,dn);//This holds the screen until you're done placing the door
                                                saver.load(ss);
                                                saver.changeName(ss);
                                                faded= new Point[0];
                                                it.load(saver);//see load method above *it = panel
                                                placingDoor=true;
                                            }
                                            catch(Exception killMe)
                                            {
                                                placingDoor=false;
                                            }
                                        }
                                    }
                                    if(!e.isControlDown()&&pointAt(toChange,doorsFrom)==-1)
                                    {
                                        doorsFrom.add(new Point((int)toChange.getX()+xs,(int)toChange.getY()+ys));
                                    }
                                    else if(!e.isControlDown())
                                    {
                                        doorsFrom.remove(pointAt(toChange,doorsFrom));
                                    }
                                    if(!placingDoor)
                                    {
                                        faded=STH.toPointArray(doorsFrom);
                                    }   
                                }
                                else//not multiple doors
                                {
                                    doorFrom= new Point((int)toChange.getX()+xs,(int)toChange.getY()+ys);//entry of door
                                    int ans =lee.showConfirmDialog(TestFrame.getPanel(),"Place MultipleDoors?","hi",0);
                                    doorsFrom= new ArrayList<Point>();
                                    if(ans==0)
                                    {
                                        multipleDoors=true;
                                        doorsFrom.add(doorFrom);
                                        faded=STH.toPointArray(doorsFrom);
                                    }
                                    else
                                    {
                                        faded= new Point[1];
                                        faded[0]=doorFrom;
                                        int an = lee.showConfirmDialog(TestFrame.getPanel(),"Place door here?","doors",0);
                                        if(an==1)
                                        {
                                            faded= new Point[0];
                                            return;
                                        }

                                        JFileChooser filer= new JFileChooser("ScreenSaves");
                                        try
                                        {
                                            int doit=1;
                                            while(doit==1)
                                            {
                                                filer.showDialog(CreatorDriver.getFrame(),"Door to here?");
                                                ss=filer.getSelectedFile().getPath();
                                                doit=jasinski.showConfirmDialog(STH.changeToUsableName(ss));
                                                if(doit==2)
                                                {
                                                    faded= new Point[0];
                                                    placingDoor=false;
                                                    return;
                                                }
                                            }
                                            currentScreen = new ScreenPackage(test,obs,deco,doors,levelName,dn);//This holds the screen until you're done placing the door
                                            saver.load(ss);
                                            saver.changeName(ss);
                                            it.load(saver);//see load method above *it = panel
                                            placingDoor=true;
                                            faded[0]= new Point();
                                        }
                                        catch(Exception killMe)
                                        {
                                            placingDoor=false;
                                        }
                                    }
                                }
                            }
                            else if(0==lee.showConfirmDialog(TestFrame.getPanel(),"Delete this door?","Door Stuff",0))
                            {
                                int i=0;
                                for(i=0;i<doors.length;i++)
                                {
                                    if(doors[i].isAt(toChange))
                                    {
                                        break;
                                    }
                                }
                                for(i=i;i<doors.length-1;i++)
                                {
                                    doors[i]=doors[i+1];
                                }
                                Door[] temp= new Door[doors.length-1];
                                for(i=0;i<temp.length;i++)
                                {
                                    temp[i]=doors[i];
                                }
                                dn--;
                                doors=temp;
                            }
                        }
                        else //function to place door(s) on chosen screen
                        {
                            if(multipleDoors)
                            {
                                doorsTo= new ArrayList<Point>();
                                int ans;
                                int minX=0,minY=0;
                                for(int i=0;i<doorsFrom.size();i++)
                                {
                                    if(minX<(int)doorsFrom.get(i).getX())
                                    {
                                        minX=(int)doorsFrom.get(i).getX();
                                    }
                                    if(minY<(int)doorsFrom.get(i).getY())
                                    {
                                        minY=(int)doorsFrom.get(i).getY();
                                    }
                                }
                                for(int i=0;i<doorsFrom.size();i++)
                                {
                                    int dx=(int)doorsFrom.get(i).getX();
                                    int dy=(int)doorsFrom.get(i).getY();
                                    doorsTo.add(new Point((dx-minX)+((int)toChange.getX()+xs),(dy-minY)+((int)toChange.getY()+ys)));
                                }
                                faded=STH.toPointArray(doorsTo);
                                ans=lee.showConfirmDialog(TestFrame.getPanel(),"Place Doors Here?","Door Stuff",0);
                                if(ans==0)
                                {
                                    String sn=ss;//saver.getName();
                                    it.addDoors(doorsTo,currentScreen.getName(),doorsFrom,0);//adds door on screen you went to
                                    saver.setPlaces(tops,rights,lefts,bottoms);
                                    saver.save(test,obs,deco,doors,dn,STH.changeToUsableName(ss));//saves the door on the screen you went to somthing is wronghere fix this
                                    lee.showMessageDialog(TestFrame.getPanel(),"Returning to level...","Door Stuff",0);
                                    load(currentScreen);//returns you to the orginal screen
                                    it.addDoors(doorsFrom,sn,doorsTo,0);//adds the door on the first screen
                                    placingDoor= false;
                                    ArrayList<Point> doorsTo= new ArrayList<Point>(),doorsFrom= new ArrayList<Point>();
                                    faded= new Point[0];
                                    multipleDoors=false;
                                }
                                faded= new Point[0];
                            }
                            else
                            {
                                int ans;
                                doorTo= new Point((int)toChange.getX()+xs,(int)toChange.getY()+ys);
                                faded= new Point[1];
                                faded[0]=doorTo;
                                ans=lee.showConfirmDialog(TestFrame.getPanel(),"Place Door Here? travel","Door Stuff",0);
                                try
                                {
                                    if(ans==0)
                                    {
                                        String sn=ss;//saver.getName();
                                        it.addDoor(doorTo,currentScreen.getName(),doorFrom,0);//adds door on screen you went to
                                        saver.setPlaces(tops,rights,lefts,bottoms);
                                        saver.save(test,obs,deco,doors,dn,STH.changeToUsableName(ss));//saves the door on the screen you went to somthing is wronghere fix this
                                        lee.showMessageDialog(TestFrame.getPanel(),"Returning to level...","Door Stuff",0);
                                        load(currentScreen);//returns you to the orginal screen
                                        it.addDoor(doorFrom,sn,doorTo,0);//adds the door on the first screen
                                        placingDoor= false;
                                    }
                                    faded= new Point[0];
                                }
                                catch(Exception killMe)
                                {
                                    killMe.printStackTrace();
                                }
                            }
                        }
                    }
                    else if(bar.getStage()==4)//monsters
                    {

                    }
                    else if(bar.getStage()==5)//special
                    {
                        String tab=selectorSpecial.getSelectedTab();
                        if(tab.equals("Doors"))
                        {
                        }
                        else if(tab.equals("Switches"))
                        {
                        }
                        else if(tab.equals("Changing"))
                        {  
                        }
                        else if(tab.equals("Pushable"))
                        {
                        }
                        else if(tab.equals("Moving"))
                        {
                        }
                        else if(tab.equals("Chests"))
                        {
                        }
                    }
                }
                catch(Exception killMe)
                {
                    killMe.printStackTrace();
                }
            }
        }

        public void mouseReleased(MouseEvent e)
        {
            down=false;
        }

        public boolean down()
        {
            return down;
        }

        private int pointAt(Point p,ArrayList<Point> pts)
        {
            for(int i=0;i<pts.size();i++)
            {
                if(pts.get(i).equals(p))
                {
                    return i;
                }
            }
            return -1;
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
            else if(key==KeyEvent.VK_F)
            {
                f=false;
            }
            else if(key==KeyEvent.VK_CONTROL)
            {
                control=false;
            }
            else if(key==KeyEvent.VK_ALT)
            {
                alt=false;
            }
        }

        public void keyPressed(KeyEvent e)
        {
            int key=e.getKeyCode();
            int ans=0;
            if(key==KeyEvent.VK_W)
            {
                if(ys==0)
                {
                    if(bar.getStage()==0&&!control)
                    {
                        ans =lee.showConfirmDialog(TestFrame.getPanel(),"Add a row above?","Size Changes",0);
                        if(ans==0)
                        {
                            test.addRow(true,"unknown");
                            obs.addRow(true,"filler");
                            ys+=1;
                        }
                    }
                    ans=15;
                }
                if(ys!=0&&ans!=15)
                {
                    ys--;
                }
            }
            else if(key==KeyEvent.VK_S)
            {
                if(ys+14==test.getLength())
                {
                    if(bar.getStage()==0&&!control)
                    {
                        ans =lee.showConfirmDialog(TestFrame.getPanel(),"Add a row below?","Size Changes",0);
                        if(ans==0)
                        {
                            test.addRow(false,"unknown");
                            obs.addRow(false,"filler");
                        }
                    }
                    ans=15;
                }
                if(ys<test.getLength()&&ans!=15)
                {
                    ys++;
                }
            }
            else if(key==KeyEvent.VK_D)
            {
                if(xs+16==test.getWidth())
                {   
                    if(bar.getStage()==0&&!control)
                    {
                        ans =lee.showConfirmDialog(TestFrame.getPanel(),"Add a column to the right?","Size Changes",0);
                        if(ans==0)
                        {
                            test.addCol(true,"unknown");
                            obs.addCol(true,"filler");
                        }
                    }
                    ans=15;
                }
                if(xs!=test.getWidth()&&ans!=15)
                {
                    xs++;
                }
            }
            else if(key==KeyEvent.VK_A)
            {
                if(xs==0)
                {   
                    if(bar.getStage()==0&&!control)
                    {
                        ans =lee.showConfirmDialog(TestFrame.getPanel(),"Add a column to the left?","Size Changes",0);
                        if(ans==0)
                        {
                            test.addCol(false,"unknown");
                            obs.addCol(false,"filler");
                            xs+=1;
                        }
                    }
                    ans=15;
                }
                if(xs!=0&&ans!=15)
                {
                    xs--;
                }
            }
            else if(key==KeyEvent.VK_UP&&(bar.getStage()==0||bar.getStage()==1))
            {
                if(sy>0)
                {
                    sy--;
                }
            }
            else if(key==KeyEvent.VK_DOWN)
            {
                if(sy+1<bar.getLength())
                {
                    sy++;
                }
            }
            else if(key==KeyEvent.VK_LEFT)
            {
                if(bar.getStage()==0||bar.getStage()==1)
                {
                    if(sx>0)
                    {
                        sx--;
                    }
                    else
                    {
                        sx=bar.getWidth();
                        if(sy>0)
                        {
                            sy--;
                        }
                    }
                }
                else if(bar.getStage()==2)
                {
                    if(type>1)
                    {
                        type--;
                    }
                    else
                    {
                        type=3;
                    }
                    bar.setSelected("border"+type+".png");
                }
            }
            else if(key==KeyEvent.VK_RIGHT)
            {
                if(bar.getStage()==0||bar.getStage()==1)
                {
                    if(sx==bar.getWidth())
                    {
                        if(sy+1<bar.getLength())
                        {
                            sy++;
                        }
                        sx=0;
                    }
                    else
                    {
                        sx++;
                    }
                }
                else if(bar.getStage()==2)
                {
                    if(type<3)
                    {
                        type++;
                    }
                    else
                    {
                        type=1;
                    }
                    bar.setSelected("border"+type+".png");
                }
            }
            else if(key==KeyEvent.VK_P)
            {
                lee.showMessageDialog(TestFrame.getPanel(),"Click ok to fix problems.\nDO NOT hit enter to confirm.","Problem Fixer",0);
            }
            else if(key==KeyEvent.VK_R)
            {
                if(rotate==3)
                {
                    rotate=0;
                }
                else
                {
                    rotate++;
                }
            }
            else if(key==KeyEvent.VK_F)
            {
                f=true;
            }
            else if(key==KeyEvent.VK_CONTROL)
            {
                control=true;
            }
            else if(key==KeyEvent.VK_ALT)
            {
                alt=true;
            }
            else if(key==45)
            {

            }
            else if(key==61)
            {

            }
            bar.setSelected(sx,sy);
        }

        public void keyTyped(KeyEvent e)
        {

        }
    }
}
