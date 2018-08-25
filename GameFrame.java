import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
public class GameFrame extends JFrame
{
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    GraphicsDevice myDevice;
    static double width = screenSize.getWidth(), height = screenSize.getHeight();
    private boolean isFullScreen = false;
    static boolean test=false;
    static GamePanel pan;
    public GameFrame(GraphicsDevice device)
    {
        super(device.getDefaultConfiguration());
        noCursor();
        this.myDevice = device;
        pan=new GamePanel();
        getContentPane().add(pan);
        setSize((int)width,(int)height);
        setUndecorated(true);
        setVisible(true);
        try{
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            //System.setProperty("apple.awt.fullscreenhidecursor","true");
        }
        catch(Exception e)
        {
            System.out.println("jeez");
        }
        setBackground(Color.black);
    }

    public void trueFullscreen() {
        isFullScreen = myDevice.isFullScreenSupported();
        setResizable(!isFullScreen); 
        if (isFullScreen) {
            // Full-screen mode
            myDevice.setFullScreenWindow(this);
            validate();
        }
    }

    public void noCursor()
    {
        try{
            //BufferedImage cursorImg = ImageIO.read(new File("images/other/filler.png"));
            //Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
            //frame.getContentPane().setCursor(blankCursor);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static double getW()
    {
        return width;
    }

    public static double getH()
    {
        return height;
    }

    public static int getZoom()
    {
        //width = this.getWidth();
        //height = this.getHeight();
        if(test)
        {
            return Math.min((int)width/256,(int)(height)/224)-5;
        }
        else
        {
            return Math.min((int)width/256,(int)height/224);
        }
    }

    public static int extra()
    {
        return ((int)width-(getZoom()*256))/2;
    }

    public static int extraH()
    {
        return ((int)height-(getZoom()*224))/2;
    }

    public static GamePanel getPanel()
    {
        return pan;
    }

    public static void setTest()
    {
        test=true;
    }
}
