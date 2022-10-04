import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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
//                notepad.scroller.addMouseWheelListener(notepad.sysWheel);
//                notepad.sysWheel.mouseWheelMoved(e);
//                notepad.scroller.removeMouseWheelListener(notepad.sysWheel);
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
        int screenWidth = screenSize.width;
        return screenWidth;
    }

    public static int getScreenHeight(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        return screenHeight;
    }

    public static String getClipboardString() {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 获取剪贴板中的内容
        Transferable trans = clipboard.getContents(null);
        if (trans != null) {
            // 判断剪贴板中的内容是否支持文本
            if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    // 获取剪贴板中的文本内容
                    String text = (String) trans.getTransferData(DataFlavor.stringFlavor);
                    return text;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


    private static JFileChooser fileChooser;
    static class OpenButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e1) {

            try {
                fileChooser = new JFileChooser();
                int i = fileChooser.showOpenDialog(notepad.open); // Show the open file dialog
                if (i == JFileChooser.APPROVE_OPTION) { // Click on the dialog box to open the option
                    File f = fileChooser.getSelectedFile(); // get selected file
                    try {
                        FileReader is = new FileReader(f); // get file input stream
                        notepad.input.read(is, f); // read file into text pane
                    } catch (Exception ex) {
                        ex.printStackTrace(); // output error message
                    }
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
                            break;
                        case 1:
                            // No option
                            notepad.input.setText("");
                            break;
                        case -1:
                            // Close option
                            break;
                        case 2:
                            // Cancel option
                            break;
                    }
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }
}
