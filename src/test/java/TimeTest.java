import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TimeTest {

    @Test
    void timeTest() throws InterruptedException {
        notepad notepadT = new notepad();

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

        Thread.sleep(2000);

        String frameTime = notepadT.timeAndDate.getText();

        notepadT.file.setText(formatter.format(new Date()));

        String generateTime = notepadT.file.getText();



        assertEquals(generateTime, frameTime);
    }
}