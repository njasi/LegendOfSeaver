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
public class Testing
{
    public static void main(String[] args)
    {
        StringTokenizer token = new StringTokenizer("hello.ser","#");
            System.out.println(token.countTokens());
        while(token.hasMoreTokens())
        {
            System.out.println(token.nextToken());
        }
    }
}
