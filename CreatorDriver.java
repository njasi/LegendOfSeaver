import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
/**
 * @SuppressWarnings("unchecked")
 */
public class CreatorDriver
{
    static private boolean fullScreen=false;
    static TestFrame test;
    public static void main(String[] args)
    { 
        if(!fullScreen)
        {
            TestFrame.setTest();
        }
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = env.getScreenDevices();
        for (int i = 0; i < 1 ; i++) {
            test = new TestFrame(devices[i]);
            //test.requestToggleFullScreen(test);
            if(fullScreen)
                test.trueFullscreen();
        }
    }

    public static TestFrame getFrame()
    {
        return test;
    }
}
