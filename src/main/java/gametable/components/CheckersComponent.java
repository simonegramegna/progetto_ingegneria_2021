package gametable.components;

/**
 * <<entity>>
 * 
 * Classe contenente i metodi per la gestione dei dati
 * degli oggetti CheckerComponent.
 * 
 * Ogni oggetto CheckerComponent rappresenta una cella della damiera.
 */
public class CheckersComponent {

    // cell color
    private char color;

    // checker on the cell
    private Checker occupierChecker;

    // cell number
    private int number;

    public CheckersComponent(final char colorSet, final Checker occupierCheckerSet, final int numberSet) {

        this.color = colorSet;
        this.occupierChecker = occupierCheckerSet;
        this.number = numberSet;
    }

    public final char getColor() {
        return color;
    }

    public final void setColor(final char colorSet) {
        this.color = colorSet;
    }

    public final Checker getOccupierChecker() {
        return occupierChecker;
    }

    public final void setOccupierChecker(final Checker occupierCheckerSet) {
        this.occupierChecker = occupierCheckerSet;
    }

    public final int getNumber() {
        return number;
    }

    public final void setNumber(final int numberSet) {
        this.number = numberSet;
    }
}
