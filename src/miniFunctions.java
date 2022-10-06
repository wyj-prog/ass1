import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class miniFunctions {
    //Dynamic time display
    static class TimeActionListener implements ActionListener {
        public TimeActionListener(){
            Timer t=new Timer(1000, this);
            t.start();
        }
        @Override
        public void actionPerformed(ActionEvent ae){
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            notepad.timeAndDate.setText(formatter.format(new Date()));
        }
    }

    //Status bar invisibility
    static class statusBarInvisibility implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(notepad.statusBarInvisibility.getText().equals("Status Bar             √")){
                notepad.statusBar.setVisible(false);
                notepad.statusBarInvisibility.setText("Status Bar           ");
            }else{
                notepad.statusBar.setVisible(true);
                notepad.statusBarInvisibility.setText("Status Bar             √");
            }
        }
    }

    static class scale extends MouseAdapter {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e){
            if(e.isControlDown()){
                Font f = notepad.input.getFont();
                if(e.getWheelRotation()<0){
                    notepad.input.setFont(new Font(f.getFamily(),f.getStyle(),f.getSize()+1));
                }else if(e.getWheelRotation()>0){
                    notepad.input.setFont(new Font(f.getFamily(),f.getStyle(),f.getSize()-1));
                }
            }else{
                notepad.scroller.addMouseWheelListener(notepad.sysWheel);
                notepad.sysWheel.mouseWheelMoved(e);
                notepad.scroller.removeMouseWheelListener(notepad.sysWheel);
            }
        }
    }

    static class scaleUp implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Font f = notepad.input.getFont();
            notepad.input.setFont(new Font(f.getFamily(),f.getStyle(),f.getSize()+1));
        }
    }

    static class scaleDown implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Font f = notepad.input.getFont();
            notepad.input.setFont(new Font(f.getFamily(),f.getStyle(),f.getSize()-1));
        }
    }



    static class PrintButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Print();
        }
    }

    static class SelectAllButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { notepad.input.selectAll();}
    }

    static class CopyButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { notepad.input.copy();}
    }

    static class CutButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { notepad.input.cut();}
    }

    static class PasteButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { notepad.input.paste();}
    }

    static class ExportButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e1) {
            try {
                new Export();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static int getScreenWidth(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        return screenSize.width;
    }

    public static int getScreenHeight(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        return screenSize.height;
    }

    public static String getClipboardString() {

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        Transferable trans = clipboard.getContents(null);
        if (trans != null) {

            if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    return (String) trans.getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


    static class OpenButton extends JFrame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e1) {
            try {
                JFileChooser fileChooser = new JFileChooser();
                int i = fileChooser.showOpenDialog(notepad.open); // Show the open file dialog
                if (i == JFileChooser.APPROVE_OPTION) { // Click on the dialog box to open the option
                    File f = fileChooser.getSelectedFile(); // get selected file
                    notepad.mainFrame.setTitle(f.getName() + " - Notepad--");
                    if (f.getName().endsWith(".odt")){
                        readODTContents(f.getPath());
                        System.out.println(f.getPath());
                    }else read(f);

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    static class NewButton extends JFrame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                if (!notepad.input.getText().equals("")){
                    int choice = JOptionPane.showConfirmDialog(null,"This file has been changed.\nDo you want to save this file? ");
                    switch (choice){
                        case 0:
                            // Yes option
                            JOptionPane.showMessageDialog(null, "Saved");
                            ;
                            notepad.input.setText("");
                            notepad.mainFrame.setTitle("untitled - Notepad--");
                            break;
                        case 1:
                            // No option
                            notepad.input.setText("");
                            notepad.mainFrame.setTitle("untitled - Notepad--");
                            break;
                        case -1:
                            // Close option
                            break;
                        case 2:
                            // Cancel option
                            break;
                    }
                } else {
                    notepad.input.setText("");
                    notepad.mainFrame.setTitle("untitled - Notepad--");
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }

    static class SaveButton extends JFrame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            // Open the save dialog
            JFileChooser choose = new JFileChooser();
            // Choose the file
            int result = choose.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION){
                // Get the selected files
                File file = choose.getSelectedFile();
                FileWriter fw = null;
                // Save
                try {
                    fw = new FileWriter(file);
                    fw.write(notepad.input.getText());
                    String currentFileName = file.getName();
                    String currentPath = file.getAbsolutePath();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }finally {
                    try {
                        if (fw != null) fw.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        }
    }

    static class emptyCheck extends JFrame implements CaretListener {

        @Override
        public void caretUpdate(CaretEvent e) {
            if(Objects.equals(notepad.mainFrame.getTitle(), "untitled - Notepad--")){
                if(!notepad.input.getText().isEmpty()){
                    notepad.mainFrame.setTitle("*untitled - Notepad--");
                }
            }
        }
    }

    public static void read(File f){
        try {
            notepad.input.setText("");
            String contentL;
            StringBuilder content = new StringBuilder();

            InputStreamReader streamReader = new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(streamReader);

            while ((contentL = bufferedReader.readLine()) != null)
                content.append(contentL).append("\n");

            String fileName = f.getName();
            if (f.getName().toLowerCase().endsWith  (".java")) {


                String[] strs = content.toString().split("\n");

                for (String s : strs) {
                    String[] keys = s.split(" ");
                    for (String s1 : keys) {
                        if (s1.contains("(")) {
                            String[] keys1 = s1.split("\\(");
                            for (int m = 0; m < keys1.length; m++) {
                                Color color = SCread.isJavaKey(keys1[m]);

                                setDocs(keys1[m], color);
                                if (m < keys1.length - 1) setDocs("(", color);
                            }
                        } else {
                            Color color = SCread.isJavaKey(s1);
                            setDocs(s1, color);
                        }
                        setDocs(" ", Color.PINK);
                    }
                    setDocs("\n", Color.RED);
                }
            } else if (fileName.toLowerCase().endsWith(".py")) {
                String[] strs = content.toString().split("\n");
                int count = 0;
                for (String s : strs) {
                    String[] keys = s.split(" ");
                    for (String s1 : keys) {
                        Color color = SCread.isPyKey(s1);
                        setDocs(s1, color);
                        setDocs(" ", Color.BLACK);
                    }
                    ++count;
                    if (count != strs.length) setDocs("\n", Color.BLACK);
                }
            } else if (fileName.toLowerCase().endsWith(".cpp")) {
                String[] strs = content.toString().split("\n");
                int count = 0;
                for (String s : strs) {
                    String[] keys = s.split(" ");
                    for (String s1 : keys) {
                        Color color = SCread.isCppKey(s1);
                        setDocs(s1, color);
                        setDocs(" ", Color.BLACK);
                    }
                    ++count;
                    if (count != strs.length) setDocs("\n", Color.BLACK);
                }
            } else {
                setDocs(content.toString(), Color.BLACK);
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // output error message
        }
    }

    public static void setDocs(String str, Color col) {

        SimpleAttributeSet attrSet = new SimpleAttributeSet();
        StyleConstants.setForeground(attrSet, col);
        insert(str, attrSet);
    }

    public static void insert(String str, AttributeSet attrSet) {
        Document doc = notepad.input.getDocument();
        try {
            doc.insertString(doc.getLength(), str, attrSet);
        } catch (BadLocationException e) {
            System.out.println("BadLocationException: " + e);
        }
    }


    public static String str = "";
    public static void readODTContents(String srcFile) throws Exception {
        ZipFile zipFile = new ZipFile(srcFile);
        System.out.println(srcFile);
        Enumeration entries = zipFile.entries();
        ZipEntry entry;
        org.w3c.dom.Document doc = null;
        while (entries.hasMoreElements()) {
            entry = (ZipEntry) entries.nextElement();
            // only handel XML file
            if (entry.getName().equals("content.xml")) {
                // generate the document
                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true);
                DocumentBuilder docBuilder = domFactory.newDocumentBuilder();
                doc = docBuilder.parse(zipFile.getInputStream(entry));

                // generate the node
                NodeList list = doc.getElementsByTagName("text:p");

                for (int a = 0; a < list.getLength(); a++) {
                    Node node = list.item(a);
                    // Recursive fetching of label contents
                    getText(node);
                    // Empty the data and record the content of the next tab
                }
            }
        }
        notepad.input.setText(str);
    }


    private static int count = 0;
    public static void getText(org.w3c.dom.Node node) {
        if (node.getChildNodes().getLength() > 1) {
            NodeList childNodes = node.getChildNodes();
            for (int a = 0; a < childNodes.getLength(); a++) {
                getText(node.getChildNodes().item(a));
            }
        } else {
            System.out.println(node.getNodeName());
            if (node.getNodeName().equals("text:p")) {
                if (count == 0){
                    count ++;
                }else str += "\n";
            }
            if (node.getNodeValue() != null) {
                // str is used to link the content of the tags
                str = str + node.getNodeValue();
            }
            if (node.getFirstChild() != null) {
                str = str + node.getFirstChild().getNodeValue() + '\n';
            }
            System.out.println(str);
        }
    }
}
