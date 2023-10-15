import java.util.ArrayList;

public class Player {

    private String name;
    private int score;
    private ArrayList<Card> cards;

    public Player(String name){
        this.name = name;
        this.score = 0;
        this.cards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public String showCards(){
        String cardsToReturn = "";
        for (Card card: cards){
            cardsToReturn += card.getLightCharacteristics() + " ";
        }
        return cardsToReturn;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }
}
