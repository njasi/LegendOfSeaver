import java.awt.*;
import java.io.IOException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;
import javax.imageio.ImageIO;

public class ImprovedJOptionPane extends JOptionPane{
    public ImprovedJOptionPane()
    {
        super();
    }

    public int showConfirmDialog(String s)
    { 
        return super.showConfirmDialog(
            null,
            getPanel(s),
            "Put the door(s) on this screen?",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);
    }

    private JPanel getPanel(String s) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        ImageIcon image = null;
        try {
            image = new ImageIcon(ImageIO.read(new File("Images/Screens/"+s+".png")));
        } catch(Exception e) {
            e.printStackTrace();
        }

        label.setIcon(image);
        panel.add(label);

        return panel;
    }
}