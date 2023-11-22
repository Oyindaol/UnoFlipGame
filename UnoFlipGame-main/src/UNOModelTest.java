import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UNOModelTest {
    private UNOModel unoModel;

    @Before
    public void setUp() {
        unoModel = new UNOModel();
    }
    @Test
    public void init() {
        unoModel.init();
        assertNotNull(unoModel.getCardDeck());
        assertNotNull(unoModel.getPlayingDeck());
    }

    @Test
    public void addPlayer() {
        Player player1 = new Player("Alice");
        assertEquals(unoModel.getPlayers().size(), 0);
        unoModel.addPlayer(player1);
        assertEquals(1, unoModel.getPlayers().size());
    }

    @Test
    public void removePlayer() {
        Player player = new Player("TestPlayer");
        assertEquals(unoModel.getPlayers().size(), 0);
        unoModel.addPlayer(player);
        assertEquals(unoModel.getPlayers().size(), 1);
        unoModel.removePlayer(player);
        assertEquals(unoModel.getPlayers().size(), 0);

        //assertFalse(unoModel.getPlayers().contains(player));
    }

    @Test
    public void distributeCards() {
        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");

        unoModel.addPlayer(player1);
        unoModel.addPlayer(player2);
        unoModel.init();

        assertEquals(7, player1.getCards().size());
        assertEquals(7, player2.getCards().size());

        assertEquals(1, unoModel.getPlayingDeck().size());
    }

    @Test
    public void addToDeck() {
        NumberCard card = new NumberCard(Card.type.REGULAR, 5, Colors.LIGHTCOLORS.RED,
                5, Colors.DARKCOLORS.PINK);

        assertEquals(0, unoModel.getCardDeck().size());
        unoModel.addToDeck(card);
        assertEquals(1, unoModel.getCardDeck().size());
    }

    @Test
    public void removeFromDeck() {
        NumberCard card = new NumberCard(Card.type.REGULAR, 5, Colors.LIGHTCOLORS.RED,
                5, Colors.DARKCOLORS.PINK);

        assertEquals(0, unoModel.getCardDeck().size());
        unoModel.addToDeck(card);
        assertEquals(1, unoModel.getCardDeck().size());
        unoModel.removeFromDeck(card);
        assertEquals(0, unoModel.getCardDeck().size());
    }

    @Test
    public void updateScore() {
        Player player1 = new Player("Alice");
        unoModel.addPlayer(player1);
        unoModel.init();

        int initialScore = unoModel.getScores().get(player1);
        unoModel.updateScore(player1, 10);
        assertEquals(initialScore + 10, (int) unoModel.getScores().get(player1));
    }

    @Test
    public void drawFromBank() {
        Player player = new Player("TestPlayer");
        unoModel.addPlayer(player);
        unoModel.init();

        if (unoModel.getPlayers().equals(player)) {
            int initialSize = player.getCards().size();
            unoModel.drawFromBank();
            assertEquals(initialSize + 1, player.getCards().size());
        }
    }

    @Test
    public void drawCard() {
        Player player = new Player("TestPlayer");
        unoModel.addPlayer(player);
        unoModel.init();

        if (unoModel.getPlayers().equals(player)) {
            int initialSize = player.getCards().size();
            unoModel.drawCard();
            assertEquals(initialSize - 1, player.getCards().size());
        }
    }

    @Test
    public void changeTurn() {
        Player player1 = new Player("TestPlayer1");
        Player player2 = new Player("TestPlayer2");
        unoModel.addPlayer(player1);
        unoModel.addPlayer(player2);
        unoModel.init();
        unoModel.changeTurn();
        assertEquals(player2, unoModel.getCurrentPlayer());
    }

}