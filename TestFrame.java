import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import java.lang.reflect.Method;
public class TestFrame extends JFrame
{
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    GraphicsDevice myDevice;
    static double width = screenSize.getWidth();
    static double height = screenSize.getHeight();
    static private boolean isFullScreen = false;
    static boolean test=false;
    static TestPanel hello= new TestPanel();
    static int c=0;
    EditorBar eb;
    public TestFrame(GraphicsDevice device)
    {
        super("Legend of Seaver Level Editor",device.getDefaultConfiguration());
        //enableOSXFullscreen(this);
        this.myDevice = device;
        getContentPane().add(hello);
        setSize((int)width,(int)height);
        setBackground(Color.black);
        //menubar
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        eb=new EditorBar(this);

        SwingUtilities.invokeLater(eb);
        setMinimumSize(new Dimension(417,262));
        setVisible(true);
    }
    
    public EditorBar getBar()
    {
        return eb;
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
        if(test)
        {
            return (int)Math.ceil(Math.min(width/497,height/244))-c;
        }
        else
        {
            return Math.min((int)width/256,(int)height/244);
        }
    }

    public static int extra()
    {
        return ((int)width-(getZoom()*256))/2;
    }

    public static TestPanel getPanel()
    {
        return hello;
    }

    public static void setTest()
    {
        test=true;
    }

    public void changeZ(int i)
    {
        c+=i;
    }
    
    public static void changeW(int i)
    {
        width=i;
    }

    public static void changeH(int i)
    {
        height=i;
    }
}
