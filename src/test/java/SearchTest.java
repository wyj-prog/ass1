import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {
    @Test
    void searchMethod() {
        notepad notepadT = new notepad();
        search searchT = new search();

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
}