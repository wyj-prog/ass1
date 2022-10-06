import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

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
                    read(f);

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

    public static void read(File file){
        try {
            notepad.input.setText("");
            String contentL;
            StringBuilder content = new StringBuilder();

            InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(streamReader);

            while ((contentL = bufferedReader.readLine()) != null)
                content.append(contentL).append("\n");

            String fileName = file.getName();


            if (file.getName().endsWith(".java")||fileName.toLowerCase().endsWith(".py")||fileName.toLowerCase().endsWith(".cpp")) {
                sourceCodeEnhance(content);
            } else {
                addWord(content.toString(), Color.BLACK);
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // output error message
        }
    }

    public static void addWord(String str, Color col) {
        SimpleAttributeSet attrSet = new SimpleAttributeSet();
        StyleConstants.setForeground(attrSet, col);

        Document doc = notepad.input.getDocument();
        try {
            doc.insertString(doc.getLength(), str, attrSet);
        } catch (BadLocationException e) {
            System.out.println("BadLocationException: " + e);
        }
    }

    public static void sourceCodeEnhance (StringBuilder content){

        String[] lines = content.toString().split("\n");

        for (String line : lines) {
            String[] words = line.split(" ");
            for (String word : words) {

                if (word.contains("(")) {
                    Color color;
                    String[] specWord = word.split("\\(");
                    for (int m = 0; m < specWord.length; m++) {
                        if(specWord[m].endsWith(",")){
                            specWord[m] = specWord[m].replace(",","");
                            if(specWord[m].startsWith("\"") && specWord[m].endsWith("\"")){
                                color = Color.green;
                                addWord(specWord[m], color);
                            }else {
                                color = SCread.keyword(specWord[m]);
                                addWord(specWord[m], color);
                            }
                            addWord("," , Color.BLACK);
                        }else {
                            if(specWord[m].startsWith("\"") && specWord[m].endsWith("\"")){
                                color = Color.green;
                                addWord(specWord[m], color);
                            }else {
                                color = SCread.keyword(specWord[m]);
                                addWord(specWord[m], color);
                            }
                        }

                        if(m < specWord.length-1){
                            addWord("(", Color.BLACK);
                        }
                    }

                } else {

                    if(word.endsWith(",")){
                        word = word.replace(",","");
                        if(word.startsWith("\"") && word.endsWith("\"")){
                            Color color = Color.green;
                            addWord(word, color);
                        }else {
                            Color color = SCread.keyword(word);
                            addWord(word, color);
                        }
                        addWord("," , Color.BLACK);
                    }else{
                        if(word.startsWith("\"") && word.endsWith("\"")){
                            Color color = Color.green;
                            addWord(word, color);
                        }else {
                            Color color = SCread.keyword(word);
                            addWord(word, color);
                        }
                    }


// -The unfinished swan
//
//                String[] wordAs = word.split("\\(");
//                for (String wordA : wordAs){
//                    String[] wordBs = wordA.split("\\)");
//                    for (String wordB : wordBs){
//                        String[] wordCs = wordB.split("\\{");
//                        for (String wordC : wordCs){
//                            String[] wordDs = wordC.split("}");
//                            for (String wordD : wordDs){
//                                String[] wordEs = wordD.split(",");
//                                for (String wordE : wordEs){
//
//                                    Color color = SCread.keyword(wordE);
//                                    addWord(word, color);
//
//                                    addWord(",", Color.BLACK);
//                                }
//                                addWord("}", Color.BLACK);
//                            }
//                            addWord("{", Color.BLACK);
//                        }
//                        addWord("\\)", Color.BLACK);
//                    }
//                    addWord("\\(", Color.BLACK);
//                }

                }
                addWord(" ", Color.BLACK);
            }
            addWord("\n", Color.BLACK);
        }
    }
}


