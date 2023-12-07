import java.util.ArrayList;
import java.util.HashMap;

public class GameState {
    public ArrayList<Card> playingDeck;
    public ArrayList<Card> playerCards;
    public HashMap<Player, Integer> scores;
    public Card topCard;
    public UNOModel.mode mode;

    public GameState(ArrayList<Card> playingDeck, ArrayList<Card> playerCards, HashMap<Player, Integer> scores, Card topCard, UNOModel.mode mode){
        this.playingDeck = playingDeck;
        this.playerCards = playerCards;
        this.scores = scores;
        this.topCard = topCard;
        this.mode = mode;
    }
}
