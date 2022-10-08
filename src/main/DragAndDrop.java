import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.nio.charset.StandardCharsets;
import javax.swing.*;



class DragAndDrop extends TransferHandler {
    @Serial
    private static final long serialVersionUID = 1L;
    @Override
    public boolean importData(JComponent comp, Transferable t) {
        try {
            Object o = t.getTransferData(DataFlavor.javaFileListFlavor);

            String filepath = o.toString();
            if (filepath.startsWith("[")) {
                filepath = filepath.substring(1);
            }
            if (filepath.endsWith("]")) {
                filepath = filepath.substring(0, filepath.length() - 1);
            }


            File file = new File(filepath);
            String fileName = file.getName();

            notepad.input.setText("");




            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(filepath), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();




            if (file.getName().endsWith(".odt")){
                String content = miniFunctions.readODTContents(file.getPath());
            }else if (file.getName().endsWith(".rtf")){
                String content = miniFunctions.ReadRtf(file.getPath());

            }else if (file.getName().endsWith(".py") || file.getName().endsWith(".java") || file.getName().endsWith(".cpp")){

                String content = miniFunctions.read(file);

                notepad.mainFrame.setTitle(fileName + " - Notepad--");
                notepad.input.setText(content);

            }else if(file.getName().endsWith(".txt") ){

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String content = sb.toString();

                notepad.mainFrame.setTitle(fileName + " - Notepad--");
                notepad.input.setText(content);

            }else{
                JOptionPane.showMessageDialog(null, "This file may not open correctly as its format is not supported.");
                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String content = sb.toString();

                notepad.mainFrame.setTitle(fileName + " - Notepad--");
                notepad.input.setText(content);
            }




        }  catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    @Override
    public boolean canImport(JComponent comp, DataFlavor[] flavors) {
        for (int i = 0; i < flavors.length; i++) {
            if (DataFlavor.javaFileListFlavor.equals(flavors[i])) {
                return true;
            }
        }
        return false;
    }


}





//public class CopyPathToTextField extends JFrame
//{
//    private static final long serialVersionUID = 1L;
//    private JTextPane field;
//    public CopyPathToTextField(){
//
//        this.setTitle("拖拽文件至文本框显示文件路径");
//        this.setSize(500, 300);
//        this.setLocationRelativeTo(null);
//        this.setLayout(null);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        field = new JTextPane();
//        field.setBounds(50, 50, 300, 30);
//
//        field.setTransferHandler(new TransferHandler()
//        {
//            private static final long serialVersionUID = 1L;
//            @Override
//            public boolean importData(JComponent comp, Transferable t) {
//                try {
//                    Object o = t.getTransferData(DataFlavor.javaFileListFlavor);
//
//                    String filepath = o.toString();
//                    if (filepath.startsWith("[")) {
//                        filepath = filepath.substring(1);
//                    }
//                    if (filepath.endsWith("]")) {
//                        filepath = filepath.substring(0, filepath.length() - 1);
//                    }
//
//                    try {
//                        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(filepath), StandardCharsets.UTF_8);
//                        BufferedReader br = new BufferedReader(inputStreamReader);
//                        StringBuilder sb = new StringBuilder();
//                        String line = br.readLine();
//
//                        while (line != null) {
//                            sb.append(line);
//                            sb.append(System.lineSeparator());
//                            line = br.readLine();
//                        }
//                        String content = sb.toString();
////                        notepad.input.setText(content);
//                        field.setText(content);
//                    } catch (FileNotFoundException e){
//                        JOptionPane.showMessageDialog(null, "Not a valid file.", "Error",
//                                JOptionPane.INFORMATION_MESSAGE);
//                    }
//                    return true;
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return false;
//            }
//            @Override
//            public boolean canImport(JComponent comp, DataFlavor[] flavors) {
//                for (int i = 0; i < flavors.length; i++) {
//                    if (DataFlavor.javaFileListFlavor.equals(flavors[i])) {
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
//
//        this.add(field);
//        this.setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        new CopyPathToTextField();
//    }
//}