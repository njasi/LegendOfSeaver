//comment test
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
public class DontRunMe
{
    
    static GameFrame test;
    public static void main(String[] args)
    {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = env.getScreenDevices();
        for (int i = 0; i < 1 ; i++) {
            test = new GameFrame(devices[i]);
            //test.trueFullscreen();
        }
    }
    public static GameFrame getFrame()
    {
        return test;
    }
}
