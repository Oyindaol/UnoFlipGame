import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Player class.
 *
 * @author Osas Iyamu
 */
public class Player implements Serializable {

    private String name;
    private ArrayList<Card> cards;

    /**
     * The constructor of the game class
     * @param name the name of the Player
     */
    public Player(String name){
        this.name = name;
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
     * A method to get the cards of a player
     * @return the cards of the player
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * A method to set the cards of a player
     */
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    /**
     * A method to show the light cards of a Player
     * @return the current light cards of a player
     */
    public String showCardsLight(){
        String cardsToReturn = "";
        for (Card card: cards){
            cardsToReturn += card.getLightCharacteristics() + " ";
        }
        return cardsToReturn;
    }

    /**
     * A method to show the dark cards of a Player
     * @return the current dark cards of a player
     */
    public String showCardsDark(){
        String cardsToReturn = "";
        for (Card card: cards){
            cardsToReturn += card.getDarkCharacteristics() + " ";
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
