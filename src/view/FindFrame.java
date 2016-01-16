package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.regex.*;

/**
 *
 * @author UlTMATE
 */
public class FindFrame implements ActionListener {
    
    JDialog findFrame, replaceFrame;
    JTextField findTf, findTfofFR, replaceTf;
    JTextArea ta;
    String filestr = "", str;
    JButton findButton, cancelFind, findButofFR, replaceBut, replaceAllBut, cancelReplace;
    int findplace, start_pt = 0, end_pt = 0, count = 0;
    
    public FindFrame() {
        Home.jtp.setEnabled(false);
        Home.findMI.setEnabled(false);
        Home.replaceMI.setEnabled(false);
        createFindGUI();
    }
    
    public FindFrame(int a) {
        Home.jtp.setEnabled(false);
        Home.findMI.setEnabled(false);
        Home.replaceMI.setEnabled(false);
        createFindReplaceGUI();
    }
    
    public void createFindGUI() {
        findFrame = new JDialog(Home.homeFrame, false);
        findFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        findFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent winEve) {
                Home.jtp.setEnabled(true);
                Home.findMI.setEnabled(true);
                Home.replaceMI.setEnabled(true);
                findFrame.setVisible(false);
                findFrame.dispose();
            }
        });
        findFrame.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent winEve) {
                winEve.getWindow().requestFocus();
            }
        });
        findFrame.setResizable(false);
        findFrame.setSize(270, 130);
        findFrame.setLocationRelativeTo(Home.homeFrame);
        JLabel findlabel = new JLabel("Find What:");
        findTf = new JTextField(20);
        findButton = new JButton("Find Next");
        findButton.addActionListener(this);
        cancelFind = new JButton("Cancel");
        cancelFind.addActionListener(this);
        JPanel findpanel = new JPanel();
        findpanel.add(findlabel);
        findpanel.add(findTf);
        findFrame.add(findpanel, "Center");
        JPanel buttonpanel = new JPanel();
        buttonpanel.add(findButton);
        buttonpanel.add(cancelFind);
        findFrame.add(buttonpanel, "South");
        findFrame.setVisible(true);
    }
    
    public void createFindReplaceGUI() {
        replaceFrame = new JDialog(Home.homeFrame, false);
        replaceFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent winEve) {
                Home.jtp.setEnabled(true);
                Home.findMI.setEnabled(true);
                Home.replaceMI.setEnabled(true);
                replaceFrame.setVisible(false);
                replaceFrame.dispose();
            }
        });
        replaceFrame.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent winEve) {
                winEve.getWindow().requestFocus();
            }
        });
        replaceFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        replaceFrame.setSize(340, 150);
        replaceFrame.setLocationRelativeTo(null);
        replaceFrame.setResizable(false);
        JLabel f_label = new JLabel("Find What:\t\t");
        findTfofFR = new JTextField(20);
        JLabel r_label = new JLabel("Replace With:");
        replaceTf = new JTextField(20);
        JPanel center_fr = new JPanel();
        center_fr.add(f_label);
        center_fr.add(findTfofFR);
        center_fr.add(r_label);
        center_fr.add(replaceTf);
        findButofFR = new JButton("Find Next");
        //	findButofFR.setEnabled(false);
        findButofFR.addActionListener(this);
        replaceBut = new JButton("Replace");
        //	replaceBut.setEnabled(false);
        replaceBut.addActionListener(this);
        replaceAllBut = new JButton("Replace All");
        replaceAllBut.addActionListener(this);
        cancelReplace = new JButton("Cancel");
        cancelReplace.addActionListener(this);
        JPanel bottom = new JPanel();
        bottom.add(findButofFR);
        bottom.add(replaceBut);
        bottom.add(replaceAllBut);
        bottom.add(cancelReplace);
        replaceFrame.add(center_fr, "Center");
        replaceFrame.add(bottom, "South");
        replaceFrame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent axnEve) {
        Object o = axnEve.getSource();
        if (o == cancelFind) {
            findFrame.setVisible(false);
            findFrame.dispose();
            Home.jtp.setEnabled(true);
            Home.findMI.setEnabled(true);
            Home.replaceMI.setEnabled(true);
        } else if (axnEve.getActionCommand().equals("Find Next")) {
            String text = "";
            if (o == findButton) {
                text = findTf.getText();
            } else {
                text = findTfofFR.getText();
            }
            ta = new TabCreator().getJTextArea(Home.jtp.getSelectedIndex());
            str = ta.getText();
            filestr = new String(str);
            String reptext = "";
            Pattern p = Pattern.compile("\r");
            Matcher m = p.matcher(str);
            while (m.find()) {
//                System.out.println("file is = "+filestr);
                filestr = m.replaceAll(reptext);
            }
//System.out.println(filestr +" pos=" +ta.getCaretPosition()+ " text=" +text);
//            prevtext = text;
            Pattern pat = Pattern.compile(text);
            Matcher mat = pat.matcher(filestr);
            try {
                if (count == 1) {
                    if (ta.getCaretPosition() == start_pt) {
                        ta.setCaretPosition(end_pt);
                    }
                }
                mat.find(ta.getCaretPosition());
                ta.select(mat.start(), mat.end());
//                Home.homeFrame.requestFocus();
                start_pt = mat.start();
                end_pt = mat.end();
                count = 1;
            } catch (IllegalStateException | IndexOutOfBoundsException exc1) {
//                exc1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Reached EOF but no Match Found", "Whoops", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (o == cancelReplace) {
            replaceFrame.setVisible(false);
            replaceFrame.dispose();
            Home.jtp.setEnabled(true);
            Home.findMI.setEnabled(true);
            Home.replaceMI.setEnabled(true);
        } else if (o == replaceBut) {
            ta = new TabCreator().getJTextArea(Home.jtp.getSelectedIndex());
            String reptext = replaceTf.getText();
            String text;
//            if (!reptext.equals("") && !findTfofFR.getText().equals("")){
            if (!findTfofFR.getText().equals("")){
                if (start_pt != end_pt) {
                    int index = Home.jtp.getSelectedIndex();
                    String title = Home.jtp.getTitleAt(index);
                    String temp = title.replace('^', ' ');
                    temp = temp.replace('*', ' ');
                    temp = temp.trim();
                    String path = Home.map.get(temp) + "";
                    if ((title.charAt(0) != '*') && path.equals("")) {
                        temp = "*" + Home.jtp.getTitleAt(index);
                        Home.jtp.setTitleAt(index, temp);
                        Home.jtp.setTabComponentAt(index, new TabCreator().getCloseButton(index, temp));
                    } else if ((title.charAt(0) != '^') && !(path.equals(""))) {
                        temp = "^" + Home.jtp.getTitleAt(index);
                        Home.jtp.setTitleAt(index, temp);
                        Home.jtp.setTabComponentAt(index, new TabCreator().getCloseButton(index, temp));
                    }
                    ta.replaceRange(reptext, start_pt, end_pt);
//                    ta.setCaretPosition(start_pt + reptext.length());
                }
                
                text = findTfofFR.getText();
                str = ta.getText();
                
                String txt = "";
                Pattern p = Pattern.compile("\r");
                Matcher m = p.matcher(str);
                while (m.find()) {
                    filestr = m.replaceAll(txt);
                }
                
//                prevtext = text;
                Pattern pat = Pattern.compile(text);
                Matcher mat = pat.matcher(filestr);
                try {
                    if (count == 1) {
                        if (ta.getCaretPosition() == start_pt) {
                            ta.setCaretPosition(start_pt+reptext.length());
                        }
                    }
                    mat.find(ta.getCaretPosition());
                    ta.select(mat.start(), mat.end());
//                    Home.homeFrame.requestFocus();
                    start_pt = mat.start();
                    end_pt = mat.end();
                    count = 1;
                } catch (IllegalStateException | IndexOutOfBoundsException exc1) {
                    JOptionPane.showMessageDialog(null, "No Match Found", "Whoops", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else if (o == replaceAllBut) {
            String text = findTfofFR.getText();
            if (!(text.equals(""))) {
                String reptext = replaceTf.getText();
                Pattern p = Pattern.compile(text);
                Matcher m = p.matcher(filestr = ta.getText());
                while (m.find()) {
                    ta.setText(m.replaceAll(reptext));
                }
            }
        }
    }
}
