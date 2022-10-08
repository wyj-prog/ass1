
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class notepadTest {

    @Test
    public void testOpen() throws Exception {
        notepad testing_notepad = new notepad(false);
        String readIn = miniFunctions.Open_func(new File("src\\main\\resources\\Opentest.txt"));
        assertTrue(readIn.contains("HTML5"));
        String readInODT = miniFunctions.Open_func(new File("src\\main\\resources\\Opentest-TED.odt"));
        assertTrue(readInODT.contains("University of Pittsburgh"));
        String readInRTF = miniFunctions.Open_func(new File("src\\main\\resources\\Opentest-TED.rtf"));
        assertTrue(readInRTF.contains("Gartner-Schmidt."));
    }

    @Test
    public void testSave() throws Exception {
        notepad notepad = new notepad(false);
        notepad.input.setText("Huawei Mate 50 Pro");
        miniFunctions.save_func(new File("src\\main\\resources\\Savetest.txt"));
        String readIn = miniFunctions.Open_func(new File("src\\main\\resources\\Savetest.txt"));
        assertTrue(readIn.contains("Huawei Mate 50 Pro"));
    }

    @Test
    public void testSearch() {
        notepad notepadT = new notepad(false);
        search searchT = new search(false);

        notepadT.input.setText("Beneath this mask there is more than flesh." +
                "Beneath this mask there is an idea, " +
                "Mr. Creedy, and ideas are bulletproof.");
        searchT.inputS.setText("is");

        assertEquals(10, search.searchMethod());
        assertEquals(24, search.searchMethod());
        assertEquals(53, search.searchMethod());
        assertEquals(67, search.searchMethod());
        assertEquals(10, search.searchMethod());
    }

    @Test
    public void testTime() throws Exception {
        notepad notepadT = new notepad(false);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Thread.sleep(2000);
        String frameTime = notepadT.timeAndDate.getText();
        notepadT.file.setText(formatter.format(new Date()));
        String generateTime = notepadT.file.getText();

        assertEquals(generateTime, frameTime);
    }

//    @Test
//    public void testCount() throws Exception {
//        notepad notepadT = new notepad(false);
//
//        notepadT.input.setText("I Really!! Want to \n" +
//                "Stay, At Your\n" +
//                "House.");
//
//        Robot robot = new Robot();
//        robot.keyPress(' ');
//
//
//        Thread.sleep(500);
//
//        String countFrame = notepadT.leftPart.getText();
//
//        assertEquals("Word Count: 8", countFrame);
//    }



}
