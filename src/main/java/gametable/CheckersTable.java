package gametable;

import gametable.components.CheckersComponent;
import game.Game;
import gametable.components.Checker;

/**
 * <<Entity>> Si occupa della struttura base della damiera
 */
public class CheckersTable {

    protected static final int LAST_CELL_NUMBER = 32;

    protected static final int CHECKERS_NUMBER = 12;
    // Number of row and tableColumns of created table
    protected static final int TABLE_COLUMNS = 8;
    protected static final int TABLE_ROWS = 8;

    // number in the white cells
    protected static final int WHITE_CELLS_NUMBER = -1;

    // Displayed symbols of checkers
    protected static final char SMALL_WHITE_CHECKER = 'b';
    protected static final char BIG_WHITE_CHECKER = 'B';
    protected static final char SMALL_BLACK_CHECKER = 'n';
    protected static final char BIG_BLACK_CHECKER = 'N';

    // table including components cells and game cells
    private CheckersComponent[][] table;

    public CheckersTable() {

        // memory allocation for the whole table
        table = new CheckersComponent[TABLE_ROWS][TABLE_COLUMNS];

        for (int i = 0; i < TABLE_ROWS; i++) {

            for (int j = 0; j < TABLE_COLUMNS; j++) {

                table[i][j] = new CheckersComponent(Game.getWhiteColor(), null, WHITE_CELLS_NUMBER);
            }
        }

        setBlackCells();
        setCheckers(Game.getWhiteColor());
        setCheckers(Game.getBlackColor());

    }

    protected CheckersTable(final boolean empty) {

        // memory allocation for the whole table
        table = new CheckersComponent[TABLE_ROWS][TABLE_COLUMNS];

        for (int i = 0; i < TABLE_ROWS; i++) {

            for (int j = 0; j < TABLE_COLUMNS; j++) {

                table[i][j] = new CheckersComponent(Game.getWhiteColor(), null, WHITE_CELLS_NUMBER);
            }
        }

        setBlackCells();

        if (!empty) {
            setCheckers(Game.getWhiteColor());
            setCheckers(Game.getBlackColor());
        }
    }

    public final CheckersComponent getCellTable(final int row, final int column) {
        return table[row][column];
    }

    public final void setCellTable(final int row, final int column, final CheckersComponent cell) {
        table[row][column] = cell;
    }

    // sets black cells in checkersTable
    private void setBlackCells() {

        int k;
        k = 1;

        for (int i = 0; i < TABLE_ROWS; i++) {

            int j;

            if (i % 2 == 0) {
                j = 0;
            } else {
                j = 1;
            }

            while (j < TABLE_COLUMNS) {

                table[i][j].setColor(Game.getBlackColor());
                table[i][j].setNumber(k);

                k++;
                j = j + 2;
            }
        }
    }

    // sets checkers int the table
    protected final void setCheckers(final char checkersColor) {

        int startRow;
        int endRow;
        char enteredSymbol;

        final int startRowWhite = 5;
        final int endRowWhite = 7;
        final int blackStartRow = 0;
        final int blackEndRow = 2;

        if (checkersColor == Game.getWhiteColor()) {

            startRow = startRowWhite;
            endRow = endRowWhite;
            enteredSymbol = SMALL_WHITE_CHECKER;

        } else if (checkersColor == Game.getBlackColor()) {

            startRow = blackStartRow;
            endRow = blackEndRow;
            enteredSymbol = SMALL_BLACK_CHECKER;

        } else {
            return;
        }

        for (int i = startRow; i <= endRow; i++) {
            int j;

            if (i % 2 == 0) {
                j = 0;
            } else {
                j = 1;
            }

            while (j < TABLE_COLUMNS) {

                Checker enteredChecker = new Checker(enteredSymbol, checkersColor, false);
                table[i][j].setOccupierChecker(enteredChecker);

                j = j + 2;
            }
        }

    }

    // prints the table
    public final void printTable() {

        for (int i = 0; i <= TABLE_ROWS * 2; i++) {

            for (int j = 0; j < TABLE_COLUMNS; j++) {

                // Print separators between cells
                if (i % 2 == 0) {

                    if (j == 0) {
                        System.out.print("+ - +");
                    } else {
                        System.out.print(" - +");
                    }

                } else {

                    Checker printedChecker = table[(i - 1) / 2][j].getOccupierChecker();
                    char printedSymbol = ' ';

                    if (printedChecker != null) {

                        printedSymbol = printedChecker.getSymbol();
                    }

                    if (table[(i - 1) / 2][j].getColor() == Game.getBlackColor() && printedChecker == null) {
                        printedSymbol = '.';
                    }

                    if (j == 0) {

                        System.out.print("| " + printedSymbol + " |");

                    } else {
                        System.out.print(" " + printedSymbol + " |");
                    }

                }
            }
            System.out.print("\n");
        }
        System.out.print('\n');
    }

    // prints number in black cells of checkerstable
    public final void printNumbersBlackCells() {

        for (int i = 0; i <= TABLE_ROWS * 2; i++) {

            for (int j = 0; j < TABLE_COLUMNS; j++) {

                if (i % 2 == 0) {

                    if (j == 0) {
                        System.out.print("+ -  +");

                    } else {
                        System.out.print("  -  +");
                    }

                } else {

                    final int doubleSpaceNumber = 10;
                    int displayedNumber;
                    displayedNumber = table[(i - 1) / 2][j].getNumber();

                    if (j == 0) {

                        if (displayedNumber != WHITE_CELLS_NUMBER) {

                            if (displayedNumber < doubleSpaceNumber) {

                                System.out.print("| " + displayedNumber + "  |");

                            } else {
                                System.out.print("| " + displayedNumber + " |");
                            }

                        } else {
                            System.out.print("|    |");
                        }
                    } else {

                        if (displayedNumber != WHITE_CELLS_NUMBER) {

                            if (displayedNumber < doubleSpaceNumber) {

                                System.out.print(" " + displayedNumber + "   |");

                            } else {
                                System.out.print("  " + displayedNumber + " |");
                            }

                        } else {
                            System.out.print("     |");
                        }
                    }
                }
            }
            System.out.print('\n');
        }
        System.out.print('\n');
    }

    public final void reset() {

        setCheckers(Game.getWhiteColor());
        setCheckers(Game.getBlackColor());

        final int indexFirstRow = 3;
        final int indexSecondRow = 4;

        for (int i = 0; i < TABLE_COLUMNS; i++) {
            table[indexFirstRow][i].setOccupierChecker(null);
            table[indexSecondRow][i].setOccupierChecker(null);
        }
    }
}
