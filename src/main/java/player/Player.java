package player;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 *<<entity>>
 *
 *  Classe contenente i metodi per la gestione dei dati
 * degli oggetti Player.
 * 
 * Gli oggetti Player rappresentano i giocatori (Bianco e Nero) della partita.
 *
 */
public class Player {

	private boolean inTurn;
	private char color;
	private Timer time;
	private ArrayList<String> moves;

	private long start;
	private long end;
	private long elapsed; //tempo trascorso

	public Player(final char tint) {
		this.inTurn = false;
		this.start = 0;
		this.moves = new ArrayList<String>();
		this.end = 0;
		this.elapsed = 0;
		this.color = tint;
		this.time = null;
	}
	public final ArrayList<String> getMoves() {
		 return moves;
	}
	public final void setMoves(final String movement) {
		this.moves.add(movement);
	}
	public final void startTime() {

		TimerTask task = new TimerTask() {
			public void run() {
				long finalTime;
				long total;
				finalTime = System.currentTimeMillis();
				long last = finalTime - start;
				total = last + elapsed;
			}
		};
		this.time = new Timer();
		final long millis = 1000;
		this.time.schedule(task, 0, millis);
	}

	public final long getStart() {
		return start;
	}

	public final char getColor() {
		return color;
	}

	public final void pauseTimer() {
		inTurn = false;
		this.end = System.currentTimeMillis();
		long last = end - start;
			this.elapsed = last + elapsed;
	}
	public final void reStartTimer() {
		this.start = System.currentTimeMillis();
		inTurn = true;
	}

	public final void startTimer() {
		inTurn = true;
		startTime();
		this.start = System.currentTimeMillis();
	}

	public final String getElapsed() {
		long min = 0;
		long sec = 0;
		final int minute = 60;
		final int millis = 1000;
		if  (inTurn) {
			if (this.start != 0) {
				long finalTime;
				long total;
				finalTime = System.currentTimeMillis();
				long last = finalTime - start;
					total = last + elapsed;
				min = total / (millis * minute);
				sec = total / (millis) % minute;

			} else {

				return "turno non iniziato";
			}
		} else {
			if (this.elapsed != 0) {
				min = elapsed / (millis * minute);
				sec = elapsed / (millis) % minute;
			}
		}
		String first = "" + min + " minut";
		String second;
		if (min == 1) {
			second = "o" + " e " + sec + " secondi";
		} else {
			second = "i" + " e " + sec + " secondi";
		}
		return first + second;
	}

	public static void printHelp() {
	    System.out.println("Questo e' il gioco della dama.\r\n"
				+ "\r\n"
				+ "Ecco una lista dei comandi disponibili:\r\n"
				+ "\r\n"
				+ "- help (apre il menu di aiuto)\r\n"
				+ "\r\n"
				+ "- gioca (inizia una nuova partita)\r\n"
				+ "\r\n"
				+ "- abbandona (termina la partita in corso)\r\n"
				+ "\r\n"
				+ "- damiera (mostra la damiera)\r\n"
				+ "\r\n"
				+ "- numeri (mostra i numeri sulle caselle nere)\r\n"
				+ "\r\n"
				+ "- prese (mostra le pedine mangiate)\r\n"
				+ "\r\n"
				+ "- mosse (mostra le mosse effettuate dai giocatori)\r\n"
				+ "\r\n"
				+ "- tempo (mostra il tempo trascorso nel turno dai giocatori)\r\n"
				+ "\r\n"
				+ "- esci (esci dal programma)\r\n"
				+ "\r\n"
				+ "Buon divertimento!\r\n"
				+ "\r\n"
				+ "		-team Stroustup uniba ");
		System.out.println("Digita un comando o help per una lista dei comandi");
	}

}
