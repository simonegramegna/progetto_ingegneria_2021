package gametable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import game.Game;
import gametable.components.Checker;
import gametable.components.CheckersComponent;
import player.Player;

/**
 * <<Control>>
 * 
 * Contiene i metodi necessari alle mosse
 *
 */
public class GameCheckersTable extends CheckersTable {
	private static final char MOVE_SEPARATOR = '-';
    private static final char TAKE_SEPARATOR = 'x';

    private static final int NOT_VALID_CELL = -1;

    private int blackCheckers;
    private int whiteCheckers;

    private static final int UPPER_LEFT_CELL = 0;
    private static final int UPPER_RIGHT_CELL = 1;
    private static final int LOWER_LEFT_CELL = 2;
    private static final int LOWER_RIGHT_CELL = 3;

    private static final int MOVE_TYPE = 1;
    private static final int SIMPLE_TAKE_TYPE = 2;
    private static final int DOUBLE_TAKE_TYPE = 3;

    public GameCheckersTable() {
        super();
        blackCheckers = CHECKERS_NUMBER;
        whiteCheckers = CHECKERS_NUMBER;
    }

    protected  GameCheckersTable(final boolean empty) {
        super(empty);
        blackCheckers = 0;
        whiteCheckers = 0;
    }

    public static int getNotValidCell() {
    	return NOT_VALID_CELL;
    }

    public final int getBlackCheckers() {
        return blackCheckers;
    }

    public final void setBlackCheckers(final int value) {
    	this.blackCheckers = value;
    }

    public final int getWhiteCheckers() {
        return whiteCheckers;
    }

    public final void setWhiteCheckers(final int value) {
    	this.whiteCheckers = value;
    }

    public final char getMoveSeparator() {
        return MOVE_SEPARATOR;
    }
    public final char getTakeSeparator() {
        return TAKE_SEPARATOR;
    }

    public static final int getMoveType() {
        return MOVE_TYPE;
    }

    public static final int getSimpleTakeType() {
        return SIMPLE_TAKE_TYPE;
    }

    public static final int getDoubleTakeType() {
        return DOUBLE_TAKE_TYPE;
    }

    protected final int[] getNumbersCells(final String userInput, final char symbol) {

        String[] nums = userInput.split(Character.toString(symbol));
        int[] numbers = new int[nums.length];

        int i = 0;

        for (String s : nums) {
            numbers[i] = Integer.parseInt(s);
            i++;
        }

        return numbers;

    }

    private boolean validInputNumbers(final int[] numbers) {

        boolean valid = true;
        int i = 0;

        while (i < numbers.length && valid) {

            if (numbers[i] < 1 || numbers[i] > LAST_CELL_NUMBER) {
                valid = false;
            }

            if (numbers[i] == 0) {
                continue;
            }

            i++;
        }
        return valid;
    }

    private int[] getCoordinates(final int blackCellNumber) {

        int[] xy = new int[2];
        boolean found = false;

        int i = 0;

        while (i < TABLE_ROWS && !found) {
            int j = 0;

            while (j < TABLE_COLUMNS && !found) {

                if (super.getCellTable(i, j).getNumber() == blackCellNumber) {
                    xy[0] = i;
                    xy[1] = j;

                    found = true;
                }

                j++;
            }
            i++;
        }
        return xy;
    }

    private boolean validInputNumber(final int cellNumber) {

        boolean valid = false;

        if (cellNumber >= 1 && cellNumber <= LAST_CELL_NUMBER) {

            valid = true;
        }
        return valid;
    }

    private boolean diagonalControlChecker(final int start, final int end, final Player mover) {

        boolean control = false;

        int[] startCoord = getCoordinates(start);
        int[] endCoord = getCoordinates(end);
        Checker startChecker = super.getCellTable(startCoord[0], startCoord[1]).getOccupierChecker();

        if (startChecker != null) {

            int nextRow = 1;
            int nextColumn = 1;

            if (mover.getColor() == Game.getWhiteColor() && !startChecker.isBigChecker()) {

                nextRow = -1;
            }

            /*
             * controllo che la colonna della cella in cui mi muovo sia a destra o a
             * sinistra della colonna della cella selezionata
             */
            if (endCoord[1] == startCoord[1] + nextColumn || endCoord[1] == startCoord[1] - nextColumn) {

                if (!startChecker.isBigChecker()) {

                    if (endCoord[0] == startCoord[0] + nextRow) {
                        control = true;
                    }

                } else {

                    if (endCoord[0] == startCoord[0] + nextRow || endCoord[0] == startCoord[0] - nextRow) {

                        control = true;
                    }
                }
            }
        }
        return control;
    }

    private CheckersComponent getNextDiagonalCell(final int startCell, final int direction) {

        CheckersComponent diagonalCell = null;

        int[] startCoord = getCoordinates(startCell);

        if (validInputNumber(startCell)) {

            int rowIndex = startCoord[0];
            int numberDiagonalCell = -1;

            final int[] directions = {-5, -4, 3, 4, 5, -3};
            final int index3 = 3;
            final int index4 = 4;
            final int index5 = 5;

            int getUpperLeftCell = directions[0];
            int getUpperRightCell = directions[1];
            int getLowerLeftCell = directions[2];
            int getLowerRightCell = directions[index3];

            // l'indice della riga e' pari
            if (rowIndex % 2 != 0) {

                getUpperLeftCell = directions[1];
                getUpperRightCell = directions[index5];
                getLowerLeftCell = directions[index3];
                getLowerRightCell = directions[index4];
            }

            if (direction == UPPER_LEFT_CELL) {
                numberDiagonalCell = startCell + getUpperLeftCell;
            }

            if (direction == UPPER_RIGHT_CELL) {
                numberDiagonalCell = startCell + getUpperRightCell;
            }

            if (direction == LOWER_LEFT_CELL) {
                numberDiagonalCell = startCell + getLowerLeftCell;
            }

            if (direction == LOWER_RIGHT_CELL) {
                numberDiagonalCell = startCell + getLowerRightCell;
            }

            if (validInputNumber(numberDiagonalCell)) {

                diagonalCell = getCell(numberDiagonalCell);
            }
        }
        return diagonalCell;
    }

    protected final CheckersComponent getCell(final int cellNumber) {

        CheckersComponent selectedCell;
        int[] coordCell = getCoordinates(cellNumber);

        selectedCell = super.getCellTable(coordCell[0], coordCell[1]);
        return selectedCell;
    }

    private boolean moveCheckersControl(final int start, final int end, final Player mover) {

        boolean control = false;

        CheckersComponent startCell = getCell(start);
        CheckersComponent endCell = getCell(end);

        if (diagonalControlChecker(start, end, mover)) {

            if (startCell.getOccupierChecker() != null && endCell.getOccupierChecker() == null) {

                Checker movedChecker = startCell.getOccupierChecker();

                if (mover.getColor() == movedChecker.getColor()) {
                    control = true;
                }
            }
        }
        return control;
    }

    private void setBigCheckerTable(final int endCell, final Checker movedChecker) {

        if (validInputNumber(endCell) && movedChecker != null) {

            if (!movedChecker.isBigChecker()) {

                char bigSymbol = BIG_BLACK_CHECKER;

                if (movedChecker.getColor() == Game.getWhiteColor()) {
                    bigSymbol = BIG_WHITE_CHECKER;
                }

                final int validNum1 = 1;
                final int validNum2 = 4;
                final int validNum3 = 29;
                final int validNum4 = LAST_CELL_NUMBER;

                if ((endCell >= validNum1 && endCell <= validNum2) || (endCell >= validNum3 && endCell <= validNum4)) {

                    movedChecker.setBigChecker(true);
                    movedChecker.setSymbol(bigSymbol);
                }
            }
        }
    }

    private int moveCheckers(final int start, final int end, final Player mover) {

        int endCellNumber = NOT_VALID_CELL;

        CheckersComponent startCell = getCell(start);
        CheckersComponent endCell = getCell(end);

        if (moveCheckersControl(start, end, mover)) {

            Checker movedChecker = startCell.getOccupierChecker();
            endCell.setOccupierChecker(movedChecker);
            startCell.setOccupierChecker(null);

            setBigCheckerTable(end, movedChecker);
            endCellNumber = end;
        }
        String movement = start + "-" + end;
        mover.setMoves(movement);

        return endCellNumber;
    }

    private boolean moveBigCheckerControl(final int start, final int end, final Player mover) {

        boolean control = false;

        CheckersComponent startCell = getCell(start);
        CheckersComponent endCell = getCell(end);

        if (diagonalControlChecker(start, end, mover)) {

            Checker movedChecker = startCell.getOccupierChecker();

            if (movedChecker != null && endCell.getOccupierChecker() == null) {

                if (movedChecker.isBigChecker() && movedChecker.getColor() == mover.getColor()) {
                    control = true;
                }

            }
        }
        return control;
    }

    public final int moveCheckers(final String input, final Player mover) {

        int endCellNumber = NOT_VALID_CELL;
        int[] cellNumbers = getNumbersCells(input, MOVE_SEPARATOR);

        if (validInputNumbers(cellNumbers)) {

            if (moveCheckersControl(cellNumbers[0], cellNumbers[1], mover)) {

                Checker movedChecker = getCell(cellNumbers[0]).getOccupierChecker();

                if (!movedChecker.isBigChecker()) {

                    endCellNumber = moveCheckers(cellNumbers[0], cellNumbers[1], mover);
                } else {
                    endCellNumber = moveBigCheckers(cellNumbers[0], cellNumbers[1], mover);
                }

            }
        }
        return endCellNumber;
    }

    private int moveBigCheckers(final int start, final int end, final Player mover) {

        int endCellNumber = NOT_VALID_CELL;

        CheckersComponent startCell = getCell(start);
        CheckersComponent endCell = getCell(end);

        if (moveBigCheckerControl(start, end, mover)) {

            Checker movedChecker = startCell.getOccupierChecker();

            endCell.setOccupierChecker(movedChecker);
            startCell.setOccupierChecker(null);
            endCellNumber = end;
        }
        String movement = start + "-" + end;
        mover.setMoves(movement);

        return endCellNumber;
    }

    private int checkerSimpleTakeControl(final int start, final int end, final Player current) {

        int numberEatenCheckerCell = NOT_VALID_CELL;

        CheckersComponent startCell = getCell(start);
        CheckersComponent endCell = getCell(end);
        Checker movedChecker = startCell.getOccupierChecker();

        if (movedChecker != null && endCell.getOccupierChecker() == null) {

            if (movedChecker.getColor() == current.getColor()) {

                CheckersComponent eatenCheckerCell = null;
                int leftDirection = LOWER_LEFT_CELL;
                int rightDirection = LOWER_RIGHT_CELL;

                if (current.getColor() == Game.getWhiteColor()) {

                    leftDirection = UPPER_LEFT_CELL;
                    rightDirection = UPPER_RIGHT_CELL;
                }

                final int numberPossibleCells = 2;

                CheckersComponent[] possibleCells = new CheckersComponent[numberPossibleCells];
                CheckersComponent[] possibleNextCells = new CheckersComponent[numberPossibleCells];

                possibleCells[0] = getNextDiagonalCell(start, leftDirection);
                possibleCells[1] = getNextDiagonalCell(start, rightDirection);

                for (int i = 0; i < possibleCells.length; i++) {

                    if (possibleCells[i] != null) {

                        if (i == 0) {

                            possibleNextCells[i] = getNextDiagonalCell(possibleCells[i].getNumber(), leftDirection);
                        }

                        if (i == 1) {
                            possibleNextCells[i] = getNextDiagonalCell(possibleCells[i].getNumber(), rightDirection);
                        }
                    }
                }

                for (int j = 0; j < possibleNextCells.length; j++) {

                    if (possibleNextCells[j] != null) {

                        if (possibleNextCells[j].getNumber() == end) {

                            eatenCheckerCell = possibleCells[j];
                        }
                    }
                }

                // ho trovato la cella in cui mangiare la pedina
                if (eatenCheckerCell != null) {

                    Checker eatenChecker = eatenCheckerCell.getOccupierChecker();

                    if (eatenChecker != null) {

                        if (eatenChecker.getColor() != current.getColor() && !eatenChecker.isBigChecker()) {

                            numberEatenCheckerCell = eatenCheckerCell.getNumber();
                        }
                    }
                }
            }
        }
        return numberEatenCheckerCell;
    }

    private int checkerSimpleTake(final int start, final int end, final Player current) {

        int endCellNumber = NOT_VALID_CELL;
        int eatCellNumber = checkerSimpleTakeControl(start, end, current);

        // il controllo e' ok
        if (eatCellNumber != NOT_VALID_CELL) {

            CheckersComponent startCell = getCell(start);
            CheckersComponent endCell = getCell(end);
            CheckersComponent eatCell = getCell(eatCellNumber);
            Checker movedChecker = startCell.getOccupierChecker();

            eatCell.setOccupierChecker(null);
            endCell.setOccupierChecker(movedChecker);
            startCell.setOccupierChecker(null);

            if (current.getColor() == Game.getWhiteColor()) {
                blackCheckers--;
            } else {
                whiteCheckers--;
            }
            endCellNumber = end;
            setBigCheckerTable(end, movedChecker);
        }
        String movement = start + "x" + end;
        current.setMoves(movement);
        return endCellNumber;
    }

    public final int simpleTake(final String input, final Player current) {

        int endCellNumber = NOT_VALID_CELL;
        int[] cellNumbers = getNumbersCells(input, TAKE_SEPARATOR);

        if (validInputNumbers(cellNumbers)) {

            Checker movedChecker = getCell(cellNumbers[0]).getOccupierChecker();

            if (movedChecker != null) {

                if (!movedChecker.isBigChecker()) {

                    endCellNumber = checkerSimpleTake(cellNumbers[0], cellNumbers[1], current);
                } else {
                    endCellNumber = bigCheckerSimpleTake(cellNumbers[0], cellNumbers[1], current);
                }
            }
        }
        return endCellNumber;
    }

    private int bigCheckerSimpleTakeControl(final int start, final int end, final Player current) {

        int numberEatenCheckerCell = NOT_VALID_CELL;

        CheckersComponent startCell = getCell(start);
        CheckersComponent endCell = getCell(end);
        Checker movedChecker = startCell.getOccupierChecker();

        if (movedChecker != null && endCell.getOccupierChecker() == null) {

            if (movedChecker.getColor() == current.getColor()) {

                CheckersComponent eatenCheckerCell = null;
                final int numberPossibleCells = 4;

                CheckersComponent[] possibleCells = new CheckersComponent[numberPossibleCells];
                CheckersComponent[] possibleNextCells = new CheckersComponent[numberPossibleCells];

                final int cell0 = 0;
                final int cell1 = 1;
                final int cell2 = 2;
                final int cell3 = 3;

                // devo contollare quattro celle ognuna con la cella successiva in diagonale
                possibleCells[cell0] = getNextDiagonalCell(start, UPPER_LEFT_CELL);
                possibleCells[cell1] = getNextDiagonalCell(start, UPPER_RIGHT_CELL);
                possibleCells[cell2] = getNextDiagonalCell(start, LOWER_LEFT_CELL);
                possibleCells[cell3] = getNextDiagonalCell(start, LOWER_RIGHT_CELL);

                for (int i = 0; i < possibleCells.length; i++) {

                    if (possibleCells[i] != null) {

                        if (i == cell0) {
                            possibleNextCells[i] = getNextDiagonalCell(possibleCells[i].getNumber(), UPPER_LEFT_CELL);
                        }

                        if (i == cell1) {
                            possibleNextCells[i] = getNextDiagonalCell(possibleCells[i].getNumber(), UPPER_RIGHT_CELL);
                        }

                        if (i == cell2) {
                            possibleNextCells[i] = getNextDiagonalCell(possibleCells[i].getNumber(), LOWER_LEFT_CELL);
                        }

                        if (i == cell3) {
                            possibleNextCells[i] = getNextDiagonalCell(possibleCells[i].getNumber(), LOWER_RIGHT_CELL);
                        }
                    }
                }

                for (int j = 0; j < possibleNextCells.length; j++) {

                    if (possibleNextCells[j] != null) {

                        if (possibleNextCells[j].getNumber() == end) {

                            eatenCheckerCell = possibleCells[j];
                        }
                    }
                }

                if (eatenCheckerCell != null) {

                    Checker eatenChecker = eatenCheckerCell.getOccupierChecker();

                    if (eatenChecker != null) {

                        if (eatenChecker.getColor() != current.getColor()) {

                            numberEatenCheckerCell = eatenCheckerCell.getNumber();
                        }
                    }
                }
            }
        }
        return numberEatenCheckerCell;
    }

    private int bigCheckerSimpleTake(final int start, final int end, final Player current) {

        int endCellNumber = NOT_VALID_CELL;

        int eatCellNumber = bigCheckerSimpleTakeControl(start, end, current);

        // il controllo e' ok
        if (eatCellNumber != NOT_VALID_CELL) {

            CheckersComponent startCell = getCell(start);
            CheckersComponent endCell = getCell(end);
            CheckersComponent eatCell = getCell(eatCellNumber);
            Checker movedChecker = startCell.getOccupierChecker();

            eatCell.setOccupierChecker(null);
            endCell.setOccupierChecker(movedChecker);
            startCell.setOccupierChecker(null);

            if (current.getColor() == Game.getWhiteColor()) {
                blackCheckers--;
            } else {
                whiteCheckers--;
            }
            endCellNumber = end;

        }
        String movement = start + "x" + end;
        current.setMoves(movement);
        return endCellNumber;
    }

    private String[] getDoubleTakeInputs(final String input, final char separator) {

        String[] inputTake1 = new String[2];

        final int doubleTakeNum = 3;

        if (moveType(input) == doubleTakeNum) {

            int i = 0;
            int xcount = 0;

            int startFirstImput = 0;
            int startSecondInput = -1;

            while (i < input.length()) {
                if (input.charAt(i) == TAKE_SEPARATOR) {
                    xcount++;
                }

                if (xcount == 1 && startSecondInput == -1) {
                    startSecondInput = i + 1;
                }

                if (xcount == 2) {

                    inputTake1[0] = input.substring(startFirstImput, i);
                    inputTake1[1] = input.substring(startSecondInput, input.length());
                    break;
                }

                i++;
            }

        }
        return inputTake1;
    }

    private int doubleTakeChecker(final String input, final Player current) {

        int endCellNumber = NOT_VALID_CELL;

        String[] moves = getDoubleTakeInputs(input, TAKE_SEPARATOR);
        int[] numbersMoveOne = getNumbersCells(moves[0], TAKE_SEPARATOR);

        if (validInputNumbers(numbersMoveOne)) {

            // il controllo sulla prima presa e' ok
            if (checkerSimpleTakeControl(numbersMoveOne[0], numbersMoveOne[1], current) != NOT_VALID_CELL) {

                // inserisco la pedina fittizia per simulare la seconda presa
                Checker controlChecker = getCell(numbersMoveOne[0]).getOccupierChecker();
                CheckersComponent controlCell = getCell(numbersMoveOne[1]);
                controlCell.setOccupierChecker(controlChecker);

                int[] numbersMoveTwo = getNumbersCells(moves[1], TAKE_SEPARATOR);

                if (validInputNumbers(numbersMoveTwo)
                        && checkerSimpleTakeControl(numbersMoveTwo[0], numbersMoveTwo[1], current) != NOT_VALID_CELL) {

                    controlCell.setOccupierChecker(null);
                	current.setMoves(input);
                    checkerSimpleTake(numbersMoveOne[0], numbersMoveOne[1], current);
                    endCellNumber = checkerSimpleTake(numbersMoveTwo[0], numbersMoveTwo[1], current);


                } else {
                    controlCell.setOccupierChecker(null);
                }
            }
        }

        return endCellNumber;
    }

    private int doubleTakeBigChecker(final String input, final Player current) {

        int endCellNumber = NOT_VALID_CELL;

        String[] moves = getDoubleTakeInputs(input, TAKE_SEPARATOR);
        int[] numbersMoveOne = getNumbersCells(moves[0], TAKE_SEPARATOR);

        if (validInputNumbers(numbersMoveOne)) {

            if (bigCheckerSimpleTakeControl(numbersMoveOne[0], numbersMoveOne[1], current) != NOT_VALID_CELL) {

                // inserisco la pedina fittizia per simulare la seconda presa
                Checker controlChecker = getCell(numbersMoveOne[0]).getOccupierChecker();
                CheckersComponent controlCell = getCell(numbersMoveOne[1]);
                controlCell.setOccupierChecker(controlChecker);

                int[] numbersMoveTwo = getNumbersCells(moves[1], TAKE_SEPARATOR);

                if (validInputNumbers(numbersMoveTwo)) {

                    final int nmt0 = numbersMoveTwo[0];
                    final int nmt1 = numbersMoveTwo[1];

                    if (bigCheckerSimpleTakeControl(nmt0, nmt1, current) != NOT_VALID_CELL) {

                        controlCell.setOccupierChecker(null);

                        bigCheckerSimpleTake(numbersMoveOne[0], numbersMoveOne[1], current);
                        endCellNumber = bigCheckerSimpleTake(numbersMoveTwo[0], numbersMoveTwo[1], current);
                    }
                    current.setMoves(input);
                } else {
                    controlCell.setOccupierChecker(null);
                }
            }
        }

        return endCellNumber;
    }

    public final int doubleTake(final String input, final Player current) {

        int endCellNumber = NOT_VALID_CELL;

        int[] numbersDoubleTake = getNumbersCells(input, TAKE_SEPARATOR);
        Checker movedChecker = getCell(numbersDoubleTake[0]).getOccupierChecker();

        if (!movedChecker.isBigChecker()) {

            endCellNumber = doubleTakeChecker(input, current);
        } else {
            endCellNumber = doubleTakeBigChecker(input, current);
        }

        return endCellNumber;
    }

    public final void resetTable() {
        super.reset();
        whiteCheckers = CHECKERS_NUMBER;
        blackCheckers = CHECKERS_NUMBER;
   }
    private static int charCount(final String s, final char c) {

        return (int) s.chars().filter(ch -> ch == c).count();
    }

    public static int moveType(final String userInput) {

        int type = 0;

        if (charCount(userInput, GameCheckersTable.MOVE_SEPARATOR) == 1
        		&& charCount(userInput, GameCheckersTable.TAKE_SEPARATOR) == 0) {

            type = MOVE_TYPE;
        }

        if (charCount(userInput, GameCheckersTable.TAKE_SEPARATOR) == 1
        		&& charCount(userInput, GameCheckersTable.MOVE_SEPARATOR) == 0) {

            type = SIMPLE_TAKE_TYPE;
        }

        if (charCount(userInput, GameCheckersTable.TAKE_SEPARATOR) == 2
        		&& charCount(userInput, GameCheckersTable.MOVE_SEPARATOR) == 0) {

            type = DOUBLE_TAKE_TYPE;
        }
        return type;
    }

    public static void showTakes(final GameCheckersTable currentTable) {

    	final int whiteTakes = 12 - currentTable.getWhiteCheckers();
    	final int blackTakes = 12 - currentTable.getBlackCheckers();

    	System.out.print("Nero: ");

    	for (int i = 0; i < whiteTakes; i++) {

    		System.out.print('\u26c0');
    	}

       	System.out.println(" ");
    	System.out.print("Bianco: ");

    	for (int i = 0; i < blackTakes; i++) {

    		System.out.print('\u26c2');
    	}
     	System.out.println(" ");
    }

	public static boolean validInputForm(final String input) {

        boolean valid;
        valid = false;

        Pattern checkInput = Pattern.compile("^[0-9]+[-x]{1}[0-9]+[[-x]{1}[0-9]+]*$");
        Matcher inputMatch = checkInput.matcher(input);

        valid = inputMatch.matches();

        return valid;

    }
}
