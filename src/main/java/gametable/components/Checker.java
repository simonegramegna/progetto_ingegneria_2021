package gametable.components;

/**
 * <<entity>>
 * 
 * Classe contenente i metodi per la gestione dei dati
 * degli oggetti checker.
 * Gli oggetti checker rappresentano le singole pedine che vengono usate dai giocatori nel corso della partita.
 * 
 */
public class Checker {

    // displayed symbol of the checker in the checkerstable
    private char symbol;

    // checker color
    private char color;

    // checker dimension
    private boolean bigChecker;

    public Checker(final char symbolSet, final char colorSet, final boolean bigCheckerSet) {
        this.symbol = symbolSet;
        this.color = colorSet;
        this.bigChecker = bigCheckerSet;
    }

    public final char getSymbol() {
        return symbol;
    }

    public final void setSymbol(final char symbolSet) {
        this.symbol = symbolSet;
    }

    public final char getColor() {
        return color;
    }

    public final void setColor(final char colorSet) {
        this.color = colorSet;
    }

    public final boolean isBigChecker() {
        return bigChecker;
    }

    public final void setBigChecker(final boolean bigCheckerSet) {
        this.bigChecker = bigCheckerSet;
    }
}
