/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author UlTMATE
 */
public class MainWindowListener extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent winEve) {
        if (areWindowsSaved()) {
            winEve.getWindow().setVisible(false);
            winEve.getWindow().dispose();
        }
    }

    public static boolean areWindowsSaved() {
        int tabNum = Home.jtp.getTabCount();
//        int tabsToSave = tabNum;
        ArrayList tabsToSave = new ArrayList();
        ArrayList savedTabsToSave = new ArrayList();
        for (int i = 0; i < tabNum; i++) {
            if((Home.jtp.getTitleAt(i)).charAt(0) == '^'){
                savedTabsToSave.add(i);
            }
        }
//        if (tabsToSave.isEmpty()) {
//            if(saveWindows(savedTabsToSave)){
//                return true;
//            } else{
//                return false;
//            }
//        } else if(savedTabsToSave.isEmpty()){
//            if (askToSaveWindows(tabsToSave)) {
//                    return true;
//                } else {
//                    return false;
//                }
//        } else {
            if (saveWindows(savedTabsToSave)) {
                for (int i = 0; i < Home.jtp.getTabCount(); i++) {
                    if ((Home.jtp.getTitleAt(i)).charAt(0) == '*') {
                        tabsToSave.add(i);
                    }
                }
                return askToSaveWindows(tabsToSave);
            } else{
                return false;
            }
//        }
    }

    public static boolean saveWindows(ArrayList savedTabsToSave){
        int i=0;
        for (i = savedTabsToSave.size() - 1; i >= 0; i--) {
            int index = (int) savedTabsToSave.get(i);
            String tabName = Home.jtp.getTitleAt(index);
            String title = tabName.replace('^', ' ');
            title = title.trim();
            int option = JOptionPane.showConfirmDialog(null, "Save Changes To File " + title + " before closing?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
            if (option == JOptionPane.YES_OPTION) {
                String path = Home.map.get(title)+"";
                    File file = new File(path);
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
                    Home.jtp.remove(index);
            } else if (option == JOptionPane.NO_OPTION) {
                Home.jtp.remove(index);
//                tabsToSave.remove((Object)i);
            } else if (option == JOptionPane.CANCEL_OPTION) {
                break;
            }
        }
        if (i<=0) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean askToSaveWindows(ArrayList tabsToSave) {
        for (int i = tabsToSave.size() - 1; i >= 0; i--) {
            int index = (int) tabsToSave.get(i);
            String tabName = Home.jtp.getTitleAt(index);
            int option = JOptionPane.showConfirmDialog(null, "Save Changes To File " + tabName + " before closing?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
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
                    Home.jtp.remove(index);
                }
            } else if (option == JOptionPane.NO_OPTION) {
                Home.jtp.remove(index);
//                tabsToSave.remove((Object)i);
            } else if (option == JOptionPane.CANCEL_OPTION) {
                break;
            }
        }
        for (int i = Home.jtp.getTabCount() - 1; i >= 0; i--) {
            if ((Home.jtp.getTitleAt(i)).charAt(0) != '*') {
                Home.jtp.remove(i);
            } else {

            }
        }
        if (Home.jtp.getTabCount() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
