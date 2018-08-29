import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EditorBar implements ActionListener,Runnable
{
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu stageMenu;
    private JMenu viewMenu;
    private JMenu toolMenu;
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
    private JMenuItem leMenuItem;
    private JMenuItem trigMenuItem;
    private JMenuItem ssMenuItem;
    private JMenuItem zzMenuItem;
    private JMenuItem seMenuItem;
    private JMenuItem mstMenuItem;
    private JMenuItem tsoMenuItem;

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
        leMenuItem = new JMenuItem("Show Level Edges");
        ssMenuItem = new JMenuItem("Show Special Selector");
        zzMenuItem = new JMenuItem("Snap to zoom");
        indMenuItem.addActionListener(this);
        leMenuItem.addActionListener(this);
        ssMenuItem.addActionListener(this);
        zzMenuItem.addActionListener(this);
        viewMenu.add(indMenuItem);
        viewMenu.add(leMenuItem);
        viewMenu.add(ssMenuItem);
        viewMenu.add(zzMenuItem);

        // build the Tools menu
        toolMenu = new JMenu("Tools");
        mstMenuItem = new JMenuItem("Monster Search");
        tsoMenuItem = new JMenuItem("Break Down Tileset");
        mstMenuItem.addActionListener(this);
        tsoMenuItem.addActionListener(this);
        toolMenu.add(mstMenuItem);
        toolMenu.add(tsoMenuItem);

        //trigger menu
        triggerMenu = new JMenu("Triggers");
        trigMenuItem = new JMenuItem("Show Trigger Options");
        triggerMenu.add(trigMenuItem);

        // add menus to menubar
        menuBar.add(fileMenu);
        menuBar.add(stageMenu);
        menuBar.add(viewMenu);
        menuBar.add(toolMenu);
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
        mstMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        zzMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK));
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
        }
        else if(action.equals("Hide Indicators")){
            boi.toggleInd();
            indMenuItem.setText("Show Indicators");
        }
        else if(action.equals("Show Indicators")){
            boi.toggleInd();
            indMenuItem.setText("Hide Indicators");
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
        else if(action.equals("Monster Search")){
            MonsterOptionPane chris= new MonsterOptionPane();
            CreatorDriver.getFrame().getPanel().setMonsterSelection(chris.search());
        }
        else if(action.equals("Break Down Tileset")){
            TilesetStuff.TilesetToTiles();
        }
        else if(action.equals("Snap to zoom")){
            CreatorDriver.getFrame().getPanel().snapToZoom();
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
