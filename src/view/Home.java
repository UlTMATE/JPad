package view;

import controller.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * @author UlTMATE
 */
public class Home {

    static public JFrame homeFrame;
    static public JMenuBar jmb;
    static JMenu file, edit, build;
    static public JMenuItem newMI, openMI, saveMI, saveasMI, exitMI, cutMI, copyMI, pasteMI, findMI, replaceMI, compileMI, runMI;
    static public JTabbedPane jtp;
    static public JSplitPane jsp;
    static public JPanel botPan;
    static public JTextArea botTA;
    public static HashMap map;
    public static int count=0;
    public Home() {
        createGUI();
    }

    public void createGUI() {
        map = new HashMap();
        homeFrame = new JFrame("JPad");
        Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
        homeFrame.setSize(scrDim.width - 70, scrDim.height - 70);
        homeFrame.setLocationRelativeTo(null);
        homeFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        homeFrame.addWindowListener(new MainWindowListener());

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception exc1) {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception exc2) {
                JOptionPane.showMessageDialog(null, exc2, "EXCEPTION", JOptionPane.ERROR_MESSAGE);
            }
        }

        jmb = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        build = new JMenu("Build");
        newMI = new JMenuItem("New");
        newMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        newMI.addActionListener(new MenuItemListener());
        openMI = new JMenuItem("Open");
        openMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        openMI.addActionListener(new MenuItemListener());
        saveMI = new JMenuItem("Save");
        saveMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveMI.addActionListener(new MenuItemListener());
        saveasMI = new JMenuItem("Save As");
        saveasMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
        saveasMI.addActionListener(new MenuItemListener());
        exitMI = new JMenuItem("Exit");
        exitMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.CTRL_DOWN_MASK));
        exitMI.addActionListener(new MenuItemListener());
        cutMI = new JMenuItem("Cut");
        cutMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        cutMI.addActionListener(new MenuItemListener());
        copyMI = new JMenuItem("Copy");
        copyMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        copyMI.addActionListener(new MenuItemListener());
        pasteMI = new JMenuItem("Paste");
        pasteMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        pasteMI.addActionListener(new MenuItemListener());
        findMI = new JMenuItem("Find");
        findMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        findMI.addActionListener(new MenuItemListener());
        replaceMI = new JMenuItem("Find & Replace");
        replaceMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
        replaceMI.addActionListener(new MenuItemListener());
        compileMI = new JMenuItem("Compile");
        compileMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        compileMI.addActionListener(new MenuItemListener());
        runMI = new JMenuItem("Run");
        runMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
        runMI.addActionListener(new MenuItemListener());
        file.add(newMI);
        file.add(openMI);
        file.add(saveMI);
        file.add(saveasMI);
        file.add(new JSeparator());
        file.add(exitMI);
        edit.add(cutMI);
        edit.add(copyMI);
        edit.add(pasteMI);
        edit.add(findMI);
        edit.add(replaceMI);
        build.add(compileMI);
        build.add(runMI);
        jmb.add(file);
        jmb.add(edit);
        jmb.add(build);
        jmb.setVisible(false);
        homeFrame.setJMenuBar(jmb);

        jtp = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        jtp.add("Home", TabCreator.getHomeTab());
        
        botTA = new JTextArea();
        botTA.setSize(new Dimension(homeFrame.getWidth(), 100));
        JLabel botLab = new JLabel("Build Output");
        botLab.setFont(new Font("serif", Font.BOLD, 14));
        botPan = new JPanel(new BorderLayout());
        botPan.setMinimumSize(new Dimension(100, 50));
        botPan.setMaximumSize(new Dimension(homeFrame.getWidth(), 100));
        botPan.add(botLab, "North");
        botPan.add(new JScrollPane(botTA), "Center");
        botPan.setVisible(false);
        jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, jtp, botPan);
        homeFrame.add(jsp, "Center");
        homeFrame.setVisible(true);
        homeFrame.setMinimumSize(new Dimension(200, 200));
    }

    public static void main(String args[]) {
        new Home();
    }
}
