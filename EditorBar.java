import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EditorBar implements ActionListener,Runnable
{
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu stageMenu;
    private JMenu viewMenu;
    private JMenu triggerMenu;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem nameMenuItem;
    private JMenuItem decoMenuItem;
    private JMenuItem tilesMenuItem;
    private JMenuItem obsMenuItem;
    private JMenuItem doorsMenuItem;
    private JMenuItem indMenuItem;
    private JMenuItem monMenuItem;
    private JMenuItem specMenuItem;
    private JMenuItem msMenuItem;
    private JMenuItem leMenuItem;
    private JMenuItem trigMenuItem;
    private JMenuItem ssMenuItem;
    public EditorBar(JFrame frame)
    {
        menuBar = new JMenuBar();
        // build the File menu
        fileMenu = new JMenu("File");
        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save");
        nameMenuItem = new JMenuItem("Rename");
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        nameMenuItem.addActionListener(this);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(nameMenuItem);

        // build the Stage menu
        stageMenu = new JMenu("Stages");
        tilesMenuItem = new JMenuItem("Tiles");
        decoMenuItem = new JMenuItem("Decorations");
        obsMenuItem = new JMenuItem("Obstructions");
        doorsMenuItem = new JMenuItem("Doors");
        specMenuItem = new JMenuItem("Special");
        monMenuItem = new JMenuItem("Monsters");
        tilesMenuItem.addActionListener(this);
        decoMenuItem.addActionListener(this);
        obsMenuItem.addActionListener(this);
        doorsMenuItem.addActionListener(this);
        monMenuItem.addActionListener(this);
        specMenuItem.addActionListener(this);
        stageMenu.add(tilesMenuItem);
        stageMenu.add(decoMenuItem);
        stageMenu.add(obsMenuItem);
        stageMenu.add(doorsMenuItem);
        stageMenu.add(monMenuItem);
        stageMenu.add(specMenuItem);

        // build the View menu
        viewMenu = new JMenu("View");
        indMenuItem = new JMenuItem("Hide Indicators");
        msMenuItem = new JMenuItem("Show Monster Selector");
        leMenuItem = new JMenuItem("Show Level Edges");
        ssMenuItem = new JMenuItem("Show Special Selector");
        indMenuItem.addActionListener(this);
        msMenuItem.addActionListener(this);
        leMenuItem.addActionListener(this);
        ssMenuItem.addActionListener(this);
        viewMenu.add(indMenuItem);
        viewMenu.add(msMenuItem);
        viewMenu.add(leMenuItem);
        viewMenu.add(ssMenuItem);
        
        //trigger menu
        triggerMenu = new JMenu("Triggers");
        trigMenuItem = new JMenuItem("Show Trigger Options");
        triggerMenu.add(trigMenuItem);

        // add menus to menubar
        menuBar.add(fileMenu);
        menuBar.add(stageMenu);
        menuBar.add(viewMenu);
        menuBar.add(triggerMenu);

        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        nameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        tilesMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,ActionEvent.SHIFT_MASK));
        obsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.SHIFT_MASK));
        doorsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,ActionEvent.SHIFT_MASK));
        decoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.SHIFT_MASK));
        monMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,ActionEvent.SHIFT_MASK));
        specMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.SHIFT_MASK));
        indMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        // put the menubar on the frame
        frame.setJMenuBar(menuBar);
    }

    public void run()
    {
    }

    public void actionPerformed(ActionEvent event)
    {
        JOptionPane lee= new JOptionPane();
        String action=event.getActionCommand();
        TestPanel boi=CreatorDriver.getFrame().getPanel();
        if(action.equals("Open")){
            boi.loadFromMenu();
        }
        else if(action.equals("Save")){
            boi.saveFromMenu();
        }
        else if(action.equals("Rename")){
            boi.renameFromMenu(lee.showInputDialog(TestFrame.getPanel(),"What would you like to call it?","Rename Level",0));
        }
        else if(action.equals("Tiles")){
            boi.changeStage(0);
        }
        else if(action.equals("Decorations")){
            boi.changeStage(1);
        }
        else if(action.equals("Obstructions")){
            boi.changeStage(2);
        }
        else if(action.equals("Doors")){
            boi.changeStage(3);
        }
        else if(action.equals("Monsters")){
            boi.changeStage(4);
            msMenuItem.setText("Hide Monster Selector");
            //CreatorDriver.getFrame().getPanel().getMonsterSelector().setVisible(true);
        }
        else if(action.equals("Hide Indicators")){
            boi.toggleInd();
            indMenuItem.setText("Show Indicators");
        }
        else if(action.equals("Show Indicators")){
            boi.toggleInd();
            indMenuItem.setText("Hide Indicators");
        }
        else if(action.equals("Show Monster Selector")){
            msMenuItem.setText("Hide Monster Selector");
            CreatorDriver.getFrame().getPanel().getMonsterSelector().setVisible(true);
        }
        else if(action.equals("Hide Monster Selector")){
            msMenuItem.setText("Show Monster Selector");
            CreatorDriver.getFrame().getPanel().getMonsterSelector().setVisible(false);
        }
        else if(action.equals("Hide Level Edges")){
            leMenuItem.setText("Show Level Edges");
            CreatorDriver.getFrame().getPanel().getLevelEdges().setVisible(false);
        }
        else if(action.equals("Show Level Edges")){
            leMenuItem.setText("Hide Level Edges");
            CreatorDriver.getFrame().getPanel().getLevelEdges().setVisible(true);
        }
        else if(action.equals("Hide Special Selector")){
            ssMenuItem.setText("Show Special Selector");
            CreatorDriver.getFrame().getPanel().getSS().setVisible(false);
        }
        else if(action.equals("Show Special Selector")){
            ssMenuItem.setText("Hide Special Selector");
            CreatorDriver.getFrame().getPanel().getSS().setVisible(true);
        }
    }

    public void toggleMonsel(boolean j)
    {
        if(j){
            msMenuItem.setText("Hide Monster Selector");
            CreatorDriver.getFrame().getPanel().getMonsterSelector().setVisible(true);
        }else{
            msMenuItem.setText("Show Monster Selector");
            CreatorDriver.getFrame().getPanel().getMonsterSelector().setVisible(false);
        }
    }
    
    public void toggleEdges(boolean f)
    {
        if(f){
            leMenuItem.setText("Hide Level Edges");
            CreatorDriver.getFrame().getPanel().getLevelEdges().setVisible(true);
        }else{
            leMenuItem.setText("Show Level Edges");
            CreatorDriver.getFrame().getPanel().getLevelEdges().setVisible(true);
        }
    }
}
