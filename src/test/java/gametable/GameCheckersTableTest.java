package gametable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import gametable.components.Checker;
import gametable.components.CheckersComponent;
import player.Player;
import game.Game;

/**
 *  Casi di test oer la damiera di gioco
 */
public class GameCheckersTableTest {

    private void moveTestTemplate(TestTable t1, Player mover, final String input) {

        final int startCellNumber = t1.getNumbersCells(input, t1.getMoveSeparator())[0];
        final int endCellNumber = t1.getNumbersCells(input, t1.getMoveSeparator())[1];

        CheckersComponent startCell = t1.getCell(startCellNumber);
        CheckersComponent endCell = t1.getCell(endCellNumber);

        // prima mossa del bianco
        assertNotNull(startCell.getOccupierChecker());
        assertNull(endCell.getOccupierChecker());

        assertEquals(startCell.getOccupierChecker().getColor(), mover.getColor());
        t1.moveCheckers(input, mover);

        assertNull(startCell.getOccupierChecker());
        assertNotNull(endCell.getOccupierChecker());
        assertEquals(endCell.getOccupierChecker().getColor(), mover.getColor());
    }

    private void takeTestTemplate(TestTable t1, Player taker, String input, final int start, final int taken,
            final int end, final boolean size1, final boolean size2) {

        // imposto la prima pedina
        CheckersComponent startCell = t1.getCell(start);
        startCell.setOccupierChecker(new Checker(taker.getColor(), taker.getColor(), size1));
        t1.setWhiteCheckers(1);
        t1.setBlackCheckers(1);

        char opponentColor = Game.getBlackColor();

        if (taker.getColor() == Game.getBlackColor()) {
            opponentColor = Game.getWhiteColor();
        }

        // imposto la seconda pedina
        CheckersComponent takenCell = t1.getCell(taken);
        takenCell.setOccupierChecker(new Checker(opponentColor, opponentColor, size2));
        t1.setBlackCheckers(1);

        Checker takerChecker = startCell.getOccupierChecker();
        Checker takenChecker = takenCell.getOccupierChecker();

        assertNotNull(takerChecker);
        assertNotNull(takenChecker);

        assertNull(t1.getCell(end).getOccupierChecker());
        assertEquals(takerChecker.getColor(), taker.getColor());
        assertNotEquals(takerChecker.getColor(), takenChecker.getColor());

        t1.simpleTake(input, taker);
        assertNull(startCell.getOccupierChecker());
        assertNull(takenCell.getOccupierChecker());

        Checker takerMoved = t1.getCell(end).getOccupierChecker();

        assertNotNull(takerMoved);
        assertEquals(takerMoved.getColor(), taker.getColor());
    }

    private void dbTakeTemplate(TestTable t1, Player current, String input, final int start, final int taken1,
            final int taken2, final int end, final boolean size1, final boolean size2, final boolean size3) {

        char currentColor = current.getColor();
        char opponentColor = Game.getBlackColor();

        t1.setBlackCheckers(2);
        t1.setWhiteCheckers(1);

        if (currentColor == Game.getBlackColor()) {

            opponentColor = Game.getWhiteColor();
            t1.setBlackCheckers(1);
            t1.setWhiteCheckers(2);
        }

        CheckersComponent startCell = t1.getCell(start);
        startCell.setOccupierChecker(new Checker(currentColor, currentColor, size1));

        CheckersComponent take1Cell = t1.getCell(taken1);
        take1Cell.setOccupierChecker(new Checker(opponentColor, opponentColor, size2));

        CheckersComponent take2Cell = t1.getCell(taken2);
        take2Cell.setOccupierChecker(new Checker(opponentColor, opponentColor, size3));

        CheckersComponent endCell = t1.getCell(end);

        assertEquals(t1.doubleTake(input, current), end);
        assertNull(take1Cell.getOccupierChecker());
        assertNull(take2Cell.getOccupierChecker());
        assertNotNull(endCell.getOccupierChecker());
        assertEquals(endCell.getOccupierChecker().getColor(), current.getColor());

    }

    @Test
    @DisplayName("Test di spostamento semplice per la pedina")
    void moveTestChecker() {

        TestTable t1 = new TestTable();
        Player p1white = new Player(Game.getWhiteColor());
        Player p2black = new Player(Game.getBlackColor());

        moveTestTemplate(t1, p1white, "21-18");
        moveTestTemplate(t1, p1white, "24-20");
        moveTestTemplate(t1, p2black, "9-13");
        moveTestTemplate(t1, p2black, "11-14");
    }

    @Test
    @DisplayName("Test di spostamento semplice per la Dama")
    void moveTestBigChecker() {

        TestTable t1 = new TestTable(true);
        Player p1white = new Player(Game.getWhiteColor());
        Player p1black = new Player(Game.getBlackColor());

        final int startCellNumber = 17;

        // imposto il pezzo da spostare
        CheckersComponent start = t1.getCell(startCellNumber);
        start.setOccupierChecker(new Checker(Game.getWhiteColor(), Game.getWhiteColor(), true));
        t1.setWhiteCheckers(1);

        // imposto il secondo pezzo da spostare
        CheckersComponent start2 = t1.getCell(1);
        start2.setOccupierChecker(new Checker(Game.getBlackColor(), Game.getBlackColor(), true));
        t1.setBlackCheckers(1);

        moveTestTemplate(t1, p1white, "17-13");
        moveTestTemplate(t1, p1white, "13-18");
        moveTestTemplate(t1, p1white, "18-14");
        moveTestTemplate(t1, p1white, "14-19");

        moveTestTemplate(t1, p1black, "1-5");
        moveTestTemplate(t1, p1black, "5-2");
        moveTestTemplate(t1, p1black, "2-6");
        moveTestTemplate(t1, p1black, "6-3");

    }

    @Test
    @DisplayName("Test di presa semplice")
    void takeTest() {

        TestTable t1 = new TestTable(true);
        Player p1white = new Player(Game.getWhiteColor());
        Player p2black = new Player(Game.getBlackColor());

        final int[] take1 = {18, 14, 11};
        final int[] take2 = {2, 5, 9};
        final int[] take3 = {30, 27, 23};
        final int[] take4 = {22, 26, 29};

        takeTestTemplate(t1, p1white, "18x11", take1[0], take1[1], take1[2], false, false);
        takeTestTemplate(t1, p2black, "2x9", take2[0], take2[1], take2[2], false, false);
        takeTestTemplate(t1, p1white, "30x23", take3[0], take3[1], take3[2], true, false);
        takeTestTemplate(t1, p2black, "22x29", take4[0], take4[1], take4[2], true, true);
    }

    @Test
    @DisplayName("Test di presa doppia")
    void doubleTakeTest() {

        TestTable t1 = new TestTable(true);
        Player p1white = new Player(Game.getWhiteColor());
        Player p1black = new Player(Game.getBlackColor());

        final int[] take1 = {29, 26, 19, 15};
        final int[] take2 = {1, 5, 13, 17 };
        final int[] take3 = {29, 26, 27, 31};
        final int[] take4 = {4, 7, 6, 2};
        final int[] take5 = {32, 28, 20, 16};
        final int lastindex = 3;

        dbTakeTemplate(t1, p1white, "29x22x15", take1[0], take1[1], take1[2], take1[lastindex], false, false, false);
        dbTakeTemplate(t1, p1black, "1x10x17", take2[0], take2[1], take2[2], take2[lastindex], false, false, false);
        dbTakeTemplate(t1, p1white, "29x22x31", take3[0], take3[1], take3[2], take3[lastindex], true, true, false);
        dbTakeTemplate(t1, p1black, "4x11x2", take4[0], take4[1], take4[2], take4[lastindex], true, false, true);
        dbTakeTemplate(t1, p1white, "32x23x16", take5[0], take5[1], take5[2], take5[lastindex], false, false, false);
    }

    @Test
    @DisplayName("Test per il tipo di mossa")
    void testTypeMove() {

        assertEquals(GameCheckersTable.moveType("26-22"), GameCheckersTable.getMoveType());
        assertEquals(GameCheckersTable.moveType("26x19"), GameCheckersTable.getSimpleTakeType());
        assertEquals(GameCheckersTable.moveType("26x19x12"), GameCheckersTable.getDoubleTakeType());
    }

}

