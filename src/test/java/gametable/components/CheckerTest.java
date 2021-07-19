package gametable.components;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 *  Casi di test per la pedina
 */
public class CheckerTest {

    @Test
    @DisplayName("Test su metodi della pedina")
    void mainCheckerTest() {

        final char symbol = 'b';
        final char color = 'b';
        final boolean big = false;
        Checker ctest = new Checker(symbol, color, big);

        assertEquals(ctest.getColor(), color);
        assertEquals(ctest.getSymbol(), symbol);
        assertEquals(ctest.isBigChecker(), big);
    }
}
