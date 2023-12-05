import java.util.ArrayList;
import java.util.HashMap;

public class GameState {

//    private UNOModel model;

    private ArrayList<Card> playerCard;
    private HashMap<Player, Integer> playerScore;
    private ArrayList<Card> playingDeck;

    // Constructor to capture the game state
    public GameState(ArrayList<Card> playerCards, HashMap<Player, Integer> playerScore, ArrayList<Card> playingDeck) {
//        this.model = model;
        this.playerCard = playerCards;
        this.playerScore = playerScore;
        this.playingDeck = playingDeck;
    }

    public ArrayList<Card> getPlayerCard() {
        return playerCard;
    }

    public HashMap<Player, Integer> getPlayerScore() {
        return playerScore;
    }

    public ArrayList<Card> getPlayingDeck() {
        return playingDeck;
    }
//    public UNOModel getModel() {
//        return model;
//    }

}
