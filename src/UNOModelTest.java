import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UNOModelTest {
    private UNOModel model;

    @Before
    public void setUp() {
        model = new UNOModel();
    }

    @Test
    public void testInitialization() {
        assertNotNull(model.getPlayers());
        assertTrue(model.getPlayers().isEmpty());

        assertNotNull(model.getScores());
        assertTrue(model.getScores().isEmpty());

        assertNotNull(model.getCardDeck());
        assertEquals(0, model.getCardDeck().size());

        assertNotNull(model.getPlayingDeck());
        assertEquals(0, model.getPlayingDeck().size());

        /*assertNull(model.getCurrentPlayer());
        assertEquals(0, model.getPosition());*/

        assertTrue(model.getCurrentMode() == UNOModel.mode.LIGHT);

        assertFalse(model.isWinner());
    }

    @Test
    public void testPlayerManagement() {
        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");

        model.addPlayer(player1);
        model.addPlayer(player2);

        assertEquals(2, model.getPlayers().size());

        model.removePlayer(player1);

        assertEquals(1, model.getPlayers().size());
    }

    @Test
    public void testDistributeCards() {
        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");

        model.addPlayer(player1);
        model.addPlayer(player2);

        model.init();

        assertEquals(7, player1.getCards().size());
        assertEquals(7, player2.getCards().size());

        assertEquals(1, model.getPlayingDeck().size());
    }

    @Test
    public void testScoreUpdate() {
        Player player1 = new Player("Alice");

        model.addPlayer(player1);
        model.init();

        int initialScore = model.getScores().get(player1);

        model.updateScore(player1, 10);

        assertEquals(initialScore + 10, (int) model.getScores().get(player1));
    }

    @Test
    public void testPlayTurn() {
        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");

        model.addPlayer(player1);
        model.addPlayer(player2);
        model.init();

        model.setCurrentPlayer(player1);
        //model.implementCurrentPlayerTurn();

        // Add assertions based on expected behavior of player1's turn
    }
}