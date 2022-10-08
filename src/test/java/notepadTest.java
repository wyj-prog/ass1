
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class notepadTest {

    @Test
    public void testOpen() throws Exception {
        notepad testing_notepad = new notepad();
        String readIn = miniFunctions.Open_func(new File("src\\resources\\Opentest.txt"));
        assertTrue(readIn.contains("HTML5"));
        String readInODT = miniFunctions.Open_func(new File("src\\resources\\Opentest-TED.odt"));
        assertTrue(readInODT.contains("University of Pittsburgh"));
        String readInRTF = miniFunctions.Open_func(new File("src\\resources\\Opentest-TED.rtf"));
        assertTrue(readInRTF.contains("Gartner-Schmidt."));
    }



    @Test
    public void testSave() throws Exception {
        notepad notepad = new notepad();
        notepad.input.setText("Huawei Mate 50 Pro");
        miniFunctions.save_func(new File("src\\resources\\Savetest.txt"));
        String readIn = miniFunctions.Open_func(new File("src\\resources\\Savetest.txt"));
        assertTrue(readIn.contains("Huawei Mate 50 Pro"));
    }
}
