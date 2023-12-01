import java.util.ArrayList;
import java.util.HashMap;

public class GameState {
    private ArrayList<Player> players;
    private HashMap<Player, Integer> scores;
    private ArrayList<Card> cardDeck;
    private ArrayList<Card> playingDeck;
    private Player currentPlayer;
    private boolean clockwise;
    private UNOModel.mode currentMode;
    private Card topCard;
    private boolean winner;



    private UNOModel model;

    // Constructor to capture the game state
    public GameState(UNOModel model) {
//        this.players = players;
//        this.scores = scores;
//        this.cardDeck = cardDeck;
//        this.playingDeck = playingDeck;
//        this.currentPlayer = currentPlayer;
//        this.clockwise = clockwise;
//        this.currentMode = currentMode;
//        this.topCard = topCard;
//        this.winner = winner;
        this.model = model;
    }

    public UNOModel getModel() {
        return model;
    }

    // Getters for the stored game state
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public HashMap<Player, Integer> getScores() {
        return scores;
    }

    public ArrayList<Card> getCardDeck() {
        return cardDeck;
    }

    public ArrayList<Card> getPlayingDeck() {
        return playingDeck;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isClockwise() {
        return clockwise;
    }

    public UNOModel.mode getCurrentMode() {
        return currentMode;
    }

    public Card getTopCard() {
        return topCard;
    }

    public boolean isWinner() {
        return winner;
    }


    //public GameState(ArrayList<Player> players, HashMap<Player, Integer> scores, ArrayList<Card> cardDeck,
    //                     ArrayList<Card> playingDeck, Player currentPlayer, boolean clockwise,
    //                     UNOModel.mode currentMode, Card topCard, boolean winner) {

}
