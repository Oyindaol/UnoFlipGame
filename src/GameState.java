import java.util.ArrayList;
import java.util.HashMap;

public class GameState {
    public ArrayList<Card> playingDeck;
    public ArrayList<Card> playerCards;
    public HashMap<Player, Integer> scores;
    public Card topCard;

    public GameState(ArrayList<Card> playingDeck, ArrayList<Card> playerCards, HashMap<Player, Integer> scores, Card topCard){
        this.playingDeck = playingDeck;
        this.playerCards = playerCards;
        this.scores = scores;
        this.topCard = topCard;
    }
}
