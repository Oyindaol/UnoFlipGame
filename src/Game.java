import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private enum mode {LIGHT, DARK}

    public mode getCurrentMode() {
        return currentMode;
    }

    private mode currentMode;
    private ArrayList<Player> players;

    private int position;
    private HashMap<Player, Integer> scores;

    private ArrayList<Card> cardDeck;
    private Card topCard;
    private ArrayList<Card> playingDeck;
    private Player currentPlayer;
    private boolean quit;

    public Game(){
        this. players = new ArrayList<>();
        this.scores = new HashMap<Player, Integer>();
        this.cardDeck = new ArrayList<>();
        this.playingDeck = new ArrayList<>();
        this.currentPlayer = null;
        this.position = 0;
        this.quit = false;
        currentMode = mode.LIGHT;
        this.init();
    }

    private void init() {
        //initialize card deck
        Random rand = new Random();
        for (int i=0; i<100; i++){
            int lightInt = rand.nextInt(10);
            int lightColor = rand.nextInt(4);
            int darkInt = rand.nextInt(10);
            int darkColour = rand.nextInt(4);

            cardDeck.add(new Card(lightInt, Colors.LIGHTCOLORS.values()[lightColor], darkInt, Colors.DARKCOLORS.values()[darkColour]));
        }
        playingDeck.add(cardDeck.get(cardDeck.size()-1));
        cardDeck.remove(cardDeck.get(cardDeck.size()-1));
        System.out.println(cardDeck.size());

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
        System.out.print("Enter number of Players(for example 2): ");
        int numberOfPlayers = input.nextInt();

        while(numberOfPlayers < 2){
            System.out.println("You need at least 2 players to play this game");
            System.out.print("Enter number of Players(for example 2): ");
            numberOfPlayers = input.nextInt();
        }

        int count = 1;
        Scanner newInput = new Scanner(System.in);

        for (int i=0; i<numberOfPlayers; i++){
            System.out.print("Enter name for Player " + count + ": ");
            String player = newInput.nextLine();
            this.players.add(new Player(player));
            count++;
        }

        distributeToAll(7);

        //Game starts
        while(!quit){
            currentPlayer = players.get(this.position);
            System.out.println("\n---------------------------------------------------------");
            System.out.println(currentPlayer.getName() + "'s turn");

            implementCurrentPlayerTurn();
            this.position = this.position + 1;
            this.position = this.position%(numberOfPlayers); //position of the next player
        }

    }

    private void implementCurrentPlayerTurn() {
        System.out.println("Your available cards:");
        ArrayList<Card> currentPlayerCards = currentPlayer.getCards();
        if (currentMode == mode.LIGHT){
            for(int i=0; i<currentPlayer.getCards().size(); i++){
                System.out.println(i+1 + ": " + currentPlayerCards.get(i).getLightCharacteristics());
            }
        }
        if (currentMode == mode.DARK){
            for(int i=0; i<currentPlayer.getCards().size(); i++){
                System.out.println(i+1 + ": " + currentPlayerCards.get(i).getDarkCharacteristics());
            }
        }
        System.out.println("Current Mode : " + currentMode.toString());
        if (this.getCurrentMode() == mode.LIGHT) {
            System.out.println("Top Card: " + playingDeck.get(playingDeck.size() - 1).getLightCharacteristics());
        }
        else {
            System.out.println("Top Card: " + playingDeck.get(playingDeck.size() - 1).getDarkCharacteristics());
        }

        System.out.print("Pick an index of the card to place or 0 to draw from the bank: ");
        Scanner input = new Scanner(System.in);
        int chosen = input.nextInt();

        if(chosen == 0){
            currentPlayer.getCards().add(cardDeck.get(cardDeck.size() - 1));
            cardDeck.remove(cardDeck.size() - 1);
            System.out.println(currentPlayer.getName() + "picked a card");
        }else{
            while ((chosen-1) > currentPlayerCards.size()) {
                System.out.println("You dont have this card in your deck, pick something in your deck");
                chosen = input.nextInt();
            }
            while (currentPlayerCards.get(chosen-1).getLightNumber() != playingDeck.get(playingDeck.size() - 1).getLightNumber() && !(currentPlayerCards.get(chosen-1).getLightColor().toString()).equals(playingDeck.get(playingDeck.size() - 1).getLightColor().toString())) {
                System.out.print("Invalid choice; Color or numbers don't match\nPick a different option: ");
                chosen = input.nextInt();
            }
            playingDeck.add(currentPlayerCards.get(chosen-1));
            if (currentMode == mode.LIGHT) {
                System.out.println("Played: " + currentPlayerCards.get(chosen-1).getLightCharacteristics());
            } else {
                System.out.println("Played: " + currentPlayerCards.get(chosen-1).getDarkCharacteristics());
            }
            currentPlayerCards.remove(currentPlayerCards.get(chosen-1));
        }
    }


    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
