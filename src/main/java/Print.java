
import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import java.awt.*;
import java.awt.print.*;

public class Print extends JFrame {
    public Print(){
        try{
            PageFormat pf = new PageFormat();
            PrinterJob.getPrinterJob().pageDialog(pf);
            PrintJob p = getToolkit().getPrintJob(this,"ok",null);
            Graphics g = p.getGraphics();//p Get a Graphics object for printing
            g.translate(120,200);// Change the position of the object
            notepad.input.printAll(g);
            p.end();// Release the object g
        }
        catch(Exception a){
            a.printStackTrace();
            new JOptionPane("This operation did not complete successfully");
        }
    }
}
