package game;

import java.util.ArrayList;
import java.util.Scanner;

import gametable.GameCheckersTable;
import player.Player;
/**
 * 
 * <<Entity>>
 * Restituisce i giocatori Bianco e Nero e il giocatore corrente.
 * Contiene i metodi get e set per l'attributo inGame che controlla
 * la presenza di una partita in corso.
 * Infine, gestisce i turni.
 *
 */
public class Game {
	private static final char WHITE_COLOR = 'w';
	private static final char BLACK_COLOR = 'b';

	private Player player1;
	private Player player2;
	private Player currentPlayer;
	private boolean inGame;

	public Game() {
		this.currentPlayer = null;
		this.player1 = new Player(WHITE_COLOR);
		this.player2 = new Player(BLACK_COLOR);
		inGame = false;
	}
	public static void startNewGame(final Game currentGame) {
		if (!currentGame.getInGame()) {
			currentGame.setInGame();
			System.out.println("Partita iniziata!");
			System.out.println("Il Bianco muove per primo");
			} else {
			System.out.println("Sei gia in partita!");
		}

	}
	public static void endGame(final Game currentGame, final GameCheckersTable currentTable,
			final Scanner scan, final String comando) {

		if (currentGame.getInGame()) {
			System.out.println("Sei sicuro di voler abbandonare? [S/N]");
			String command = comando;
			command = scan.nextLine();
            command = command.toLowerCase();

    		if (command.equals("s") || command.equals("si") || command.equals("y") || command.equals("yes")) {
    			if (currentGame.getCurrentPlayer().getColor() == WHITE_COLOR) {
    				System.out.println("Vittoria del Nero per abbandono!");
        			System.out.println("Partita abbandonata!");
    			} else {
    				System.out.println("Vittoria del Bianco per abbandono!");
        			System.out.println("Partita abbandonata!");
    			}
    			currentGame.getPlayer1().getMoves().clear();
    			currentGame.getPlayer2().getMoves().clear();
                currentGame.setInGame();
                currentTable.resetTable();
    		} else {
    			System.out.println("Non mollare!");
    			return;
    		}
		} else {
				System.out.println("Nessuna partita in corso!");
				System.out.println("Digita 'gioca' per iniziare una nuova partita.");
			}
	}


	public final boolean getInGame() {
		return inGame;
	}

	public final void setInGame() {
		inGame = !inGame;

		if (inGame) {
			startPlayer1Turn();
		}
	}

	public final Player getPlayer1() {

		return player1;
	}

	public final Player getPlayer2() {

		return player2;
	}

    public final Player getCurrentPlayer() {

        return currentPlayer;
    }

	public final void startPlayer1Turn() {

		if (currentPlayer != null) {
			player2.pauseTimer();
        }
        player1.startTimer();

		currentPlayer = player1;
	}

	public final void startPlayer2Turn() {

		player1.pauseTimer();
        player2.startTimer();

		currentPlayer = player2;
	}
	public final void switchTurn() {

		if (currentPlayer == null) {

			startPlayer1Turn();
            System.out.println("Turno del bianco");
        } else if (currentPlayer == player1) {

			startPlayer2Turn();
			player2.reStartTimer();
            System.out.println("Turno del nero");
		} else {

			startPlayer1Turn();
			player1.reStartTimer();
            System.out.println("Turno del bianco");
		}
    }

	public final char getCurrentPlayerColor() {
		return currentPlayer.getColor();
	}
	public static void showMovements(final Player white, final Player black) {
	    ArrayList<String> whiteMoves = new ArrayList<String>();
	    whiteMoves = white.getMoves();
	    ArrayList<String> blackMoves = 	new ArrayList<String>();
	    blackMoves = black.getMoves();
    	int arrayLength = whiteMoves.size() + blackMoves.size();

    	try {
	   	for (int i = 0; i < arrayLength; i++)	{
	   	System.out.println("B: " + whiteMoves.get(i));
	   	System.out.println("N: " + blackMoves.get(i));
	   	}
		} catch (IndexOutOfBoundsException exception) {
			System.out.println("");
		}
	}
	public static char getBlackColor() {
		return BLACK_COLOR;
	}
	public static char getWhiteColor() {
		return WHITE_COLOR;
	}
}
