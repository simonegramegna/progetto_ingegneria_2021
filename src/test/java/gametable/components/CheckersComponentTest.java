package gametable.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *  Test sulla cella della damiera
 */
public class CheckersComponentTest {

    @Test
    @DisplayName("Test per la cella della damiera")
    void mainCheckesComponentTest() {

        final char symbol = 'b';
        final char color = 'b';
        final char cellColor = 'n';
        final int cellNumber = 16;
        final boolean big = false;

        Checker occupier = new Checker(symbol, color, big);
        CheckersComponent cellTest = new CheckersComponent(cellColor, occupier, cellNumber);

        assertEquals(cellTest.getColor(), cellColor);
        assertEquals(cellTest.getNumber(), cellNumber);
        assertNotNull(cellTest.getOccupierChecker());

    }

}
