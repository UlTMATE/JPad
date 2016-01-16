package controller;

import view.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
 *
 * @author UlTMATE
 */
public class MenuItemListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent axnEve) {
        Object obj = axnEve.getSource();
        if (obj == Home.newMI || obj == TabCreator.newBut) {
            if (Home.jtp.getTitleAt(0).equals("Home")) {
                Home.jtp.remove(0);
            }
            int tabNum = Home.jtp.getTabCount();
            Home.jtp.add("Untitled" + Home.count, new TabCreator(tabNum, null, "Untitled"+ Home.count));
            Home.map.put("Untitled" + Home.count, "");
            Home.jtp.setTabComponentAt(tabNum, new TabCreator().getCloseButton(tabNum, Home.jtp.getTitleAt(tabNum)));
            Home.jtp.setSelectedIndex(tabNum);
            Home.jmb.setVisible(true);
            Home.count++;
        } else if (obj == Home.openMI || obj == TabCreator.openBut) {
            JFileChooser jfc = new JFileChooser(".");
            jfc.setMultiSelectionEnabled(false);
            int value = jfc.showOpenDialog(Home.homeFrame);
            if (value == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile();
                String fileName = file.getName();
                String path = file.getAbsolutePath();
                if (Home.map.containsValue(path)) {
                    int index = Home.jtp.indexOfTab(fileName);
                    if(index==-1){
                        fileName = "^" +fileName;
                        index = Home.jtp.indexOfTab(fileName);
                    }
                    Home.jtp.setSelectedIndex(Home.jtp.indexOfTab(fileName));
                } else {
                    if (Home.jtp.getTitleAt(0).equals("Home")) {
                        Home.jtp.remove(0);
                    }
                    String text = "";
                    try {
                        FileReader reader = new FileReader(file);
                        BufferedReader bufReader = new BufferedReader(reader);
                        String line = "";
                        while ((line = bufReader.readLine()) != null) {
                            text += line + "\n";
                        }
                        bufReader.close();
                    } catch (IOException ioExc) {
                        ioExc.printStackTrace();
                    }
                    int tabNum = Home.jtp.getTabCount();
                    Home.jtp.add(fileName, new TabCreator(tabNum, text, fileName));
            Home.jtp.setTabComponentAt(tabNum, new TabCreator().getCloseButton(tabNum, Home.jtp.getTitleAt(tabNum)));
                    Home.jtp.setSelectedIndex(tabNum);
                    Home.jmb.setVisible(true);
                    Home.map.put(fileName, path);
                }
            }
        } else if (obj == Home.saveMI) {
            int index = Home.jtp.getSelectedIndex();
            String title = Home.jtp.getTitleAt(index);
            if (title.charAt(0) == '*') {
                String temp;
                temp = title.replace('*', ' ');
                temp = temp.trim();
                JFileChooser jfc = new JFileChooser(".");
                jfc.setMultiSelectionEnabled(false);
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

                    title = file.getName();
                    Home.map.remove(temp, "");
                    Home.map.put(title, file.getAbsolutePath());
                    Home.jtp.setTitleAt(index, title);
                    Home.jtp.setTabComponentAt(index, new TabCreator().getCloseButton(index, title));

                }
            } else if (title.charAt(0) == '^') {
                String temp;
                temp = title.replace('^', ' ');
                temp = temp.trim();
                String path = Home.map.get(temp) + "";
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
                title = file.getName();
                Home.jtp.setTitleAt(index, title);
                Home.jtp.setTabComponentAt(index, new TabCreator().getCloseButton(index, title));
            }
        } else if (obj == Home.saveasMI) {
            int index = Home.jtp.getSelectedIndex();
            String title = Home.jtp.getTitleAt(index);
            JFileChooser jfc = new JFileChooser(".");
            jfc.setMultiSelectionEnabled(false);
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
                String temp = title.replace('^', ' ');
                temp = title.replace('*', ' ');
                temp = temp.trim();
                Home.map.remove(temp, Home.map.get(temp));
                title = file.getName();
                Home.map.put(title, file.getAbsolutePath());
                Home.jtp.setTitleAt(index, title);
                Home.jtp.setTabComponentAt(index, new TabCreator().getCloseButton(index, title));
            }
        } else if (obj == Home.exitMI) {
            if (MainWindowListener.areWindowsSaved()) {
                Home.homeFrame.setVisible(false);
                Home.homeFrame.dispose();
            }
        } else if (obj == Home.cutMI) {
            JTextArea jta = new TabCreator().getJTextArea(Home.jtp.getSelectedIndex());
            jta.cut();
        } else if (obj == Home.copyMI) {
            JTextArea jta = new TabCreator().getJTextArea(Home.jtp.getSelectedIndex());
            jta.copy();
        } else if (obj == Home.pasteMI) {
            JTextArea jta = new TabCreator().getJTextArea(Home.jtp.getSelectedIndex());
            jta.paste();
        } else if (obj == Home.findMI) {
            new FindFrame();
        } else if (obj == Home.replaceMI) {
            new FindFrame(1);
        } else if (obj == Home.compileMI) {
            int index = Home.jtp.getSelectedIndex();
            saveAndCompile(index);
        } else if (obj == Home.runMI) {
            int index = Home.jtp.getSelectedIndex();
            if (saveAndCompile(index)) {
                run(index);
            }
        }
    }

    public static boolean saveAndCompile(int index) {
        String title = Home.jtp.getTitleAt(index);
        String temp = title.replace('*', ' ');
        temp = temp.replace('^', ' ');
        temp = temp.trim();
        if (title.charAt(0) == '^') {
            String path = Home.map.get(temp) + "";
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
            title = file.getName();
            Home.jtp.setTitleAt(index, title);
            Home.jtp.setTabComponentAt(index, new TabCreator().getCloseButton(index, title));
            return compile(file);
        } else if (title.charAt(0) == '*') {
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
                title = file.getName();
                Home.map.remove(temp, "");
                Home.map.put(title, file.getAbsolutePath());
                Home.jtp.setTitleAt(index, title);
                Home.jtp.setTabComponentAt(index, new TabCreator().getCloseButton(index, title));
                return compile(file);
            }
        } else {
            String path = Home.map.get(temp) + "";
            File file = new File(path);
            return compile(file);
        }
        return false;
    }

    public static boolean compile(File file) {
        String path = file.getAbsolutePath();
        String directory = path.substring(0, 2);
//        path = path.substring(3, path.length() - 5);
        String fileName = path.substring(path.lastIndexOf("\\")+1);
        path = path.substring(0,path.lastIndexOf("\\"));
        try {
            Process p = Runtime.getRuntime().exec("cmd /c " +directory+"&cd \"" +path+ "\"&javac \"" + fileName + "\"");
//            System.out.println("cmd /c " +directory+"&cd \"" +path+ "\"&javac \"" + fileName + "\"");
            p.waitFor();
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line = error.readLine();
            String result = "";
            while (line != null) {
                result += line + "\n";
                line = error.readLine();
            }
            p.destroy();
            if (result.equals("") || result == null) {
                Home.botTA.setText("Process Completed");
                return true;
            } else {
                Home.botTA.setText(result);
                Home.botTA.setCaretPosition(0);
                return false;
            }
        } catch (IOException ioExc) {
            ioExc.printStackTrace();
        } catch (InterruptedException interExc) {
            interExc.printStackTrace();
        } finally {
            Home.botPan.setVisible(true);
            Home.homeFrame.setSize(Home.homeFrame.getWidth(), Home.homeFrame.getHeight() + 1);
        }
        return false;
    }

    public static void run(int index) {
        String title = Home.jtp.getTitleAt(index);
        String temp = title.replace('*', ' ');
        temp = temp.replace('^', ' ');
        temp = temp.trim();
        String path = Home.map.get(temp) + "";
        String directory = path.substring(0, 2);
        path = path.substring(3, path.length() - 5);
        String fileName = path.substring(path.lastIndexOf("\\")+1);
        path = path.substring(0,path.lastIndexOf("\\"));
        try {
//                // Execute command
//                String command = "cmd /c start cmd.exe";
////            String command = "cmd /c echo java -cp \"" +directory+ "\\" +path+ "\" "+fileName+"| cmd.exe&pause&exit";
//                System.out.println(command);
//                Process p = Runtime.getRuntime().exec(command);
//                p.waitFor();

                // Get output stream to write from it
//                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
//                OutputStream out = p.getOutputStream();
//                out.write(("echo dir").getBytes());
//                out.flush();
//                out.write(("java "+fileName+"\n pause\n exit").getBytes());
//                out.flush();
//                out.write(("cd " + path));
//                out.flush();
//                out.write(("java " +fileName+ "\n"));
//                out.flush();
//                out.write("pause exit");
//                out.flush();
//                out.close();
            String command = "cmd /c java \"" + fileName + "\"";
            Process p = Runtime.getRuntime().exec(command);
            System.out.println(command);
//            Process p = Runtime.getRuntime().exec("cmd /c " +directory+"&cd \"" +path+ "\"&java \"" + fileName + "\"");
//            System.out.println("cmd /c " +directory+"&cd \"" +path+ "\"&java \"" + fileName + "\"");
            p.waitFor();
            String line = "";
            String result = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            line = reader.readLine();
            while (line != null) {
                result += line + "\n";
                line = reader.readLine();
            }
            reader.close();
//            if (result.equals("") || result == null) {
                BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                line = error.readLine();
//                result = "";
                while (line != null) {
                    result += line + "\n";
                    line = error.readLine();
                }
                error.close();
//            } else {
                Home.botTA.setText("Run: \n" +result + "\n\nProcess Completed");
                Home.botTA.setCaretPosition(0);
//            }
//            p.destroy();
        } catch (IOException ioExc) {
            ioExc.printStackTrace();
        }
        catch (InterruptedException interExc){
        } 
            finally {
            Home.botPan.setVisible(true);
            Home.homeFrame.setSize(Home.homeFrame.getWidth(), Home.homeFrame.getHeight() + 1);
        }
    }
}
