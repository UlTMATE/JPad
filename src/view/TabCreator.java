package view;

import controller.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author UlTMATE 
 */
public class TabCreator extends JPanel {

    static public JButton openBut, newBut;
    static public JLabel titleLab;
    private String tabName;
    public JTextArea tabTA;

    public TabCreator(String tabName) {
        this.tabName = tabName;
    }

    public String getTabName() {
        return tabName;
    }

    public TabCreator() {

    }

    public TabCreator(int index, String text, String tabName) {
        this.tabName = tabName;
        setLayout(new BorderLayout());
        tabTA = new JTextArea(text);
        tabTA.setMinimumSize(new Dimension(50, 50));
//        tabTA.setLineWrap(true);
        tabTA.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
//        tabTA.setCaretPosition(0);
        tabTA.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent keyEve) {
                String title = Home.jtp.getTitleAt(index);
                String temp = title.replace('^', ' ');
                temp = temp.replace('*', ' ');
                temp = temp.trim();
                String path = Home.map.get(temp) + "";
                if ((title.charAt(0) != '*') && path.equals("")) {
                    temp = "*" + Home.jtp.getTitleAt(index);
                    Home.jtp.setTitleAt(index, temp);
                    Home.jtp.setTabComponentAt(index, getCloseButton(index, temp));
                } else if ((title.charAt(0) != '^') && !(path.equals(""))) {
                    temp = "^" + Home.jtp.getTitleAt(index);
                    Home.jtp.setTitleAt(index, temp);
                    Home.jtp.setTabComponentAt(index, getCloseButton(index, temp));
                }
            }
        });
        add(new JScrollPane(tabTA), BorderLayout.CENTER);
    }

    public static JPanel getHomeTab() {
        JPanel homePan = new JPanel(new GridBagLayout());

        openBut = new JButton(new ImageIcon("src//images//openFileIcon.png"));
        openBut.setToolTipText("Open an existing file");
        openBut.addActionListener(new MenuItemListener());
        newBut = new JButton(new ImageIcon("src.//images//newFileIcon.png"));
        newBut.setToolTipText("Create a new file for editing");
        newBut.addActionListener(new MenuItemListener());

        homePan.add(openBut);
        homePan.add(newBut);

        return homePan;
    }

    public JPanel getCloseButton(int index, String title) {
        tabName = title;
        JPanel closingPan = new JPanel();
        titleLab = new JLabel(title);
        titleLab.setFont(new Font("arial", Font.BOLD, 12));
        JButton closeBut = new JButton("X");
        closeBut.setFont(new Font("arial", Font.BOLD, 10));
        closeBut.setContentAreaFilled(false);
        closeBut.setOpaque(false);
//        closeBut.setBorder(BorderFactory.createLineBorder(Color.black));
        closeBut.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent mouEve) {
                JButton but = (JButton) mouEve.getSource();
                but.setForeground(Color.red);
//                closeBut.setContentAreaFilled(true);
            }

            @Override
            public void mouseExited(MouseEvent mouEve) {
                JButton but = (JButton) mouEve.getSource();
                but.setForeground(Color.BLACK);
//                closeBut.setContentAreaFilled(false);
            }
        });
        closeBut.setMnemonic('w');
        closeBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent axnEve) {
//                String title = Home.jtp.getTitleAt(Home.jtp.getSelectedIndex());
                int index = Home.jtp.indexOfTab(getTabName());
                String title = getTabName();
                int tabNum = Home.jtp.getTabCount();
                if (tabNum == 1) {
                    if (title.charAt(0) == '*') {
                        askToSaveWindow(index);
                    } else if (title.charAt(0) == '^') {
                        saveWindow(index);
                    } else {
//                        Home.map.remove(Home.jtp.getTitleAt(index));
//                        String tabName = getTabName();
//                        if (index == -1) {
//                            String temp = "*" + tabName;
//                            index = Home.jtp.indexOfTab(temp);
//                        }
//                        if (index == -1) {
//                            String temp = "^" + tabName;
//                            index = Home.jtp.indexOfTab(temp);
//                        }
                        if (index >= 0) {
//                            String temp = Home.jtp.getTitleAt(index);
                            Home.map.remove(Home.jtp.getTitleAt(index));
                            Home.jtp.removeTabAt(index);

                        }
                    }
                    Home.jtp.add("Home", TabCreator.getHomeTab());
                    Home.jtp.setTabComponentAt(0, new TabCreator().getCloseButton(0, Home.jtp.getTitleAt(0)));
                    Home.jmb.setVisible(false);
                } else {
                    if (title.charAt(0) == '*') {
                        askToSaveWindow(index);
//                            Home.jtp.remove(Home.jtp.getSelectedIndex());
                    } else if (title.charAt(0) == '^') {
                        saveWindow(index);
                    } else {
//                        String tabName = getTabName();
//                        if (index == -1) {
//                            String temp = "*" + tabName;
//                            index = Home.jtp.indexOfTab(temp);
//                        }
//                        if (index == -1) {
//                            String temp = "^" + tabName;
//                            index = Home.jtp.indexOfTab(temp);
//                        }
                        if (index >= 0) {
                            Home.map.remove(Home.jtp.getTitleAt(index));
                            Home.jtp.removeTabAt(index);

                        }
                    }
                }
            }
        });
        closingPan.add(titleLab);
        closingPan.add(closeBut);
        closingPan.setOpaque(false);

        return closingPan;
    }

    public JTextArea getJTextArea(int index) {
//        JPanel pan = (JPanel) Home.jtp.getComponentAt(index);
//        JTextArea jta = (JTextArea) pan.getComponentAt(5, 5);
//        return jta;
//        JScrollPane pan = (JScrollPane) Home.jtp.getComponentAt(index);
        TabCreator tc = (TabCreator) Home.jtp.getComponentAt(index);
        JTextArea jta = tc.tabTA;
//        JTextArea jta = (JTextArea)pan.getComponent(0);
        return jta;
    }

    public void saveWindow(int index) {
        int option = JOptionPane.showConfirmDialog(null, "Save Changes To File " + Home.jtp.getTitleAt(index) + " before closing?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
        if (option == JOptionPane.YES_OPTION) {
            File file = new File(Home.map.get(Home.jtp.getTitleAt(index)) + "");
            JTextArea jta = new TabCreator().getJTextArea(index);
            String text = jta.getText();
            try {
                FileWriter writer = new FileWriter(file);
                BufferedWriter bufWriter = new BufferedWriter(writer);
                bufWriter.write(text);
                bufWriter.close();
            } catch (IOException ioExc) {
                ioExc.printStackTrace();
            }
//            String tabName = getTabName();
//            int ind = Home.jtp.indexOfTab(tabName);
//            if (ind == -1) {
//                String temp = "*" + tabName;
//                ind = Home.jtp.indexOfTab(temp);
//            }
//            if (ind == -1) {
//                String temp = "^" + tabName;
//                ind = Home.jtp.indexOfTab(temp);
//            }
            if (index >= 0) {
                String temp = Home.jtp.getTitleAt(index);
                if(temp.charAt(0)=='^'){
                    temp = temp.substring(1);
                } else if(temp.charAt(0)=='*'){
                    temp = temp.substring(1);
                }
                Home.map.remove(temp);
                Home.jtp.removeTabAt(index);

            }
        } else if (option == JOptionPane.NO_OPTION) {
//            String tabName = getTabName();
//            int ind = Home.jtp.indexOfTab(tabName);
//            if (ind == -1) {
//                String temp = "*" + tabName;
//                ind = Home.jtp.indexOfTab(temp);
//            }
//            if (ind == -1) {
//                String temp = "^" + tabName;
//                ind = Home.jtp.indexOfTab(temp);
//            }
            if (index >= 0) {
                String temp = Home.jtp.getTitleAt(index);
                if(temp.charAt(0)=='^'){
                    temp = temp.substring(1);
                } else if(temp.charAt(0)=='*'){
                    temp = temp.substring(1);
                }
                Home.map.remove(temp);
                Home.map.remove(Home.jtp.getTitleAt(index));
                Home.jtp.removeTabAt(index);

            }
        } else if (option == JOptionPane.CANCEL_OPTION) {
        }
    }

    public void askToSaveWindow(int index) {
        String tabNam = Home.jtp.getTitleAt(index);
        int option = JOptionPane.showConfirmDialog(null, "Save Changes To File " + tabNam + " before closing?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
        if (option == JOptionPane.YES_OPTION) {
            JFileChooser jfc = new JFileChooser(".");
            int value = jfc.showSaveDialog(Home.homeFrame);
            if (value == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile();
                JTextArea jta = new TabCreator().getJTextArea(index);
                String text = jta.getText();
                try {
                    FileWriter writer = new FileWriter(file);
                    BufferedWriter bufWriter = new BufferedWriter(writer);
                    bufWriter.write(text);
                    bufWriter.close();
                } catch (IOException ioExc) {
                    ioExc.printStackTrace();
                }
                if (index >= 0) {
                    String temp = Home.jtp.getTitleAt(index);
                if(temp.charAt(0)=='^'){
                    temp = temp.substring(1);
                } else if(temp.charAt(0)=='*'){
                    temp = temp.substring(1);
                }
                Home.map.remove(temp);
                    Home.jtp.removeTabAt(index);

                }
            }
        } else if (option == JOptionPane.NO_OPTION) {
            if (index >= 0) {
                String temp = Home.jtp.getTitleAt(index);
                if(temp.charAt(0)=='^'){
                    temp = temp.substring(1);
                } else if(temp.charAt(0)=='*'){
                    temp = temp.substring(1);
                }
                Home.map.remove(temp);
                Home.jtp.removeTabAt(index);

            }
        } else if (option == JOptionPane.CANCEL_OPTION) {
        }
    }
}
