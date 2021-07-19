package game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Casi di test su classe Game
 */
public class TestGame {

	private Game gioco = new Game();
    @Test
	@DisplayName("Test sul cambio del turno")
	void testSwitchTurn() {
		gioco.switchTurn();
		assertEquals('w', gioco.getCurrentPlayerColor());
		gioco.switchTurn();
		assertEquals('b', gioco.getCurrentPlayerColor());
	}
	@Test
	@DisplayName("Test sul tempo trascorso")
	void testTimer() {
		gioco.switchTurn();
		gioco.switchTurn();
		gioco.switchTurn();
		assertNotEquals(0, gioco.getCurrentPlayer().getElapsed());
	}
	@Test
	@DisplayName("Test sull'inizio di una nuova partita")
	void testStartNewGame() {
	    assertFalse(gioco.getInGame());
		Game.startNewGame(gioco);
		assertTrue(gioco.getInGame());
	}
}
