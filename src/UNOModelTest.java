import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UNOModelTest {
    private UNOModel model;

    @Before
    public void setUp() {
        model = new UNOModel();

    }

   /* @Test
    public void testInitialization() {
        assertNotNull(model.getPlayers());
        assertTrue(model.getPlayers().isEmpty());

        assertNotNull(model.getScores());
        assertTrue(model.getScores().isEmpty());

        assertNotNull(model.getCardDeck());
        assertEquals(0, model.getCardDeck().size());

        assertNotNull(model.getPlayingDeck());
        assertEquals(0, model.getPlayingDeck().size());

        assertNull(model.getCurrentPlayer());
        assertEquals(0, model.position());

        assertTrue(model.getCurrentMode() == UNOModel.mode.LIGHT);

        assertFalse(model.isWinner());
    }*/

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

    @Test
    public void testAddPlayer() {
        Player player = new Player("TestPlayer");
        model.addPlayer(player);

        // Assert that the player was added to the list of players
        assertTrue(model.getPlayers().contains(player));

        // Assert that the number of players increased after adding a player
        assertEquals(1, model.getPlayers().size());

        // Assert that a player with the same name can be added
        model.addPlayer(player);
        assertEquals(2, model.getPlayers().size()); // Size should remain the same

    }

    @Test
    public void testDrawFromBank() {
        Player currentPlayer = model.getPlayers().get(0); // Assuming the first player

        int initialDeckSize = model.getCardDeck().size();
        int initialPlayerHandSize = currentPlayer.getCards().size();

        model.drawFromBank(); // Draw a card from the bank for the current player

        int finalDeckSize = model.getCardDeck().size();
        int finalPlayerHandSize = currentPlayer.getCards().size();

        // Check if the player's hand size increased by 1
        assertEquals(initialPlayerHandSize + 1, finalPlayerHandSize);

        // Check if the deck size reduced by 1
        assertEquals(initialDeckSize - 1, finalDeckSize);


    }

    @Test
    public void testValidatePlacement() {
        Player currentPlayer = new Player("TestPlayer"); // Assuming the current player is a test player
        model.addPlayer(currentPlayer);
        model.setCurrentPlayer(currentPlayer);

        // Adding a card to the playing deck
        model.getPlayingDeck().add(new NumberCard(Card.type.REGULAR, 5, Colors.LIGHTCOLORS.RED, 5, Colors.DARKCOLORS.PURPLE));

        // Adding a card to the player's hand
        currentPlayer.addCard(new NumberCard(Card.type.REGULAR, 5, Colors.LIGHTCOLORS.RED, 5, Colors.DARKCOLORS.PURPLE));

        int initialPlayerHandSize = currentPlayer.getCards().size();

        // Simulating the placement of a card with matching characteristics and color
        model.validatePlacement("5", "RED");

        int finalPlayerHandSize = currentPlayer.getCards().size();

        // Check if the player's hand size reduced by 1 after a valid placement
        assertEquals(initialPlayerHandSize - 1, finalPlayerHandSize);
    }

}