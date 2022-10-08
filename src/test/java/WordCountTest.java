import org.junit.jupiter.api.Test;

import java.awt.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class WordCountTest {

    @Test
    void WordCountTest() throws InterruptedException, AWTException {
        notepad notepadT = new notepad();

        notepadT.input.setText("I Really!! Want to \n" +
                "Stay, At Your\n" +
                "House.");

        Robot robot = new Robot();
        robot.keyPress(' ');


        Thread.sleep(500);

        String countFrame = notepadT.leftPart.getText();

        assertEquals("Word Count: 8", countFrame);
    }
}