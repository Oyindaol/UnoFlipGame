import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private ArrayList<Player> players;
    private HashMap<Player, Integer> scores;

    private ArrayList<Card> cardDeck;
    private ArrayList<Card> playingDeck;

    public Game(){
        this. players = new ArrayList<>();
        this.scores = new HashMap<Player, Integer>();
        this.cardDeck = new ArrayList<>();
        this.playingDeck = new ArrayList<>();
        this.init();
    }

    private void init() {
        //initialize card deck

    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    public void addPlayer(Player p){
        this.players.add(p);
    }

    public void removePlayer(Player p){
        this.players.remove(p);
    }

    public ArrayList<Card> getCardDeck() {
        return cardDeck;
    }

    public void addToDeck(Card card) {
        cardDeck.add(card);
    }

    public void removeFromDeck(Card card) {
        this.cardDeck.remove(card);
    }

    public HashMap<Player, Integer> calculateScore(){
        return null;
    }

    private void distributeToAll(int numberOfCards){
        for (Player p : players){
            for (int i=0; i<numberOfCards; i++){
                int lastCardInDeck = cardDeck.size()-1;
                p.addCard(cardDeck.get(lastCardInDeck));
                cardDeck.remove(cardDeck.get(lastCardInDeck));
            }
        }
    }

    public void play(){
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the game of UNO Flip!");
        System.out.println("How many players will be playing this round? Enter an integer, for example: 2");
        int numberOfPlayers = input.nextInt();

        while(numberOfPlayers < 2){
            System.out.println("You need at least 2 players to play this game");
            numberOfPlayers = input.nextInt();
        }

        int count = 1;
        Scanner newInput = new Scanner(System.in);

        for (int i=0; i<numberOfPlayers; i++){
            System.out.println("Enter your name Player " + count + ": ");
            String player = newInput.nextLine();
            this.players.add(new Player(player));
            count++;
        }

        distributeToAll(6);

    }


    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
