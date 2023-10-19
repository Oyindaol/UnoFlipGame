import java.util.ArrayList;

/**
 * The Player class
 */
public class Player {

    private String name;
    private int score;
    private ArrayList<Card> cards;

    /**
     * The constructor of the game class
     * @param name the name of the Player
     */
    public Player(String name){
        this.name = name;
        this.score = 0;
        this.cards = new ArrayList<>();
    }

    /**
     * A method to get the name of the player
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * A method to get the score of the player
     * @return the score of the Player
     */
    public int getScore() {
        return score;
    }

    /**
     * A method to get the cards of a player
     * @return the cards of the player
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * A method to show the cards of a Player
     * @return the current cards of a player
     */
    public String showCards(){
        String cardsToReturn = "";
        for (Card card: cards){
            cardsToReturn += card.getLightCharacteristics() + " ";
        }
        return cardsToReturn;
    }

    /**
     * A method to add a card to the player's deck
     * @param card
     */
    public void addCard(Card card) {
        this.cards.add(card);
    }
}
