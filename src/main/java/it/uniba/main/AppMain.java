package it.uniba.main;

import game.Game;

import java.util.Scanner;

import gametable.GameCheckersTable;
import player.Player;

/**
 * <<Boundary>>
 * Nel Main vengono richiamati tutti i comandi che consentono di usare l'applicazione e gestire l'interfaccia.
 */

/**
 * The main class for the project. It must be customized to meet the project
 * assignment specifications.
 *
 * <b>DO NOT RENAME</b>
 */
public final class AppMain {

    /**
     * Private constructor. Change if needed.
     */
    private AppMain() {
    }

    /**
     * * This is the main entry of the application.
     *
     * @param args The command-line arguments.
     */
    public static void main(final String[] params) {
        if (params.length == 1) {
            if (params[0].equals("-h") || params[0].equals("--help")) {
                Player.printHelp();
            }
        }

        GameCheckersTable currentTable = new GameCheckersTable();
        Game currentGame = new Game();

        Scanner scan = new Scanner(System.in);

        System.out.println("Digita un comando o help per una lista dei comandi");

        while (true) {
            String comando = scan.nextLine();

            switch (comando) {
                case "help":
                	Player.printHelp();
                    break;
                case "damiera":
                    if (currentGame.getInGame()) {
                        currentTable.printTable();
                    } else {
                        System.out.println("Nessuna partita in corso!");
                        System.out.println("Digita 'gioca' per iniziare una nuova partita.");
                    }
                    break;
                case "numeri":
                    currentTable.printNumbersBlackCells();
                    break;
                case "gioca":
                    if (!currentGame.getInGame()) {
                        currentGame = new Game();
                    }
                    Game.startNewGame(currentGame);

                    break;
                case "tempo":
                    if (currentGame.getInGame()) {
                        System.out.println("Bianco: " + currentGame.getPlayer1().getElapsed());
                        System.out.println("Nero: " + currentGame.getPlayer2().getElapsed());
                    } else {
                        System.out.println("Nessuna partita in corso!");
                        System.out.println("Digita 'gioca' per iniziare una nuova partita.");
                    }
                    break;

                case "prese":
                    GameCheckersTable.showTakes(currentTable);
                    break;
                case "mosse":
                    Game.showMovements(currentGame.getPlayer1(), currentGame.getPlayer2());
                    break;
                case "abbandona":
                    Game.endGame(currentGame, currentTable, scan, comando);
                    break;
                case "esci":
                    System.out.println("Sei sicuro di voler uscire? [S/N]");
                    comando = scan.nextLine();
                    comando = comando.toLowerCase();

                    if (comando.equals("s") || comando.equals("si") || comando.equals("y") || comando.equals("yes")) {
                    	scan.close();
                		System.out.println("Uscita...");
                		System.exit(0);

                    } else {
                        System.out.println("Digita un comando o help per una lista dei comandi");
                    }
                    break;

                default:

                    int movType = GameCheckersTable.moveType(comando);

                    if (GameCheckersTable.validInputForm(comando) && movType != 0) {
                    	if (currentGame.getInGame()) {
                        int result = currentTable.getNotValidCell();

                        if (movType == currentTable.getMoveType()) {

                            result = currentTable.moveCheckers(comando, currentGame.getCurrentPlayer());
                        } else if (movType == currentTable.getSimpleTakeType()) {

                            result = currentTable.simpleTake(comando, currentGame.getCurrentPlayer());
                        } else {
                            result = currentTable.doubleTake(comando, currentGame.getCurrentPlayer());
                        }

                        if (result == currentTable.getNotValidCell()) {

                            System.out.println("Mossa non valida");

                        } else {
                            currentTable.printTable();
                            currentGame.switchTurn();
                        }
                    	} else {
                    		System.out.println("Devi essere in partita per eseguire una mossa!");
                    	}
                    } else {
                        System.out.println("Comando non riconosciuto. Digita help per aprire il menu di aiuto.");
                    }

            }
        }
    }

}
