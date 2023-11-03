import java.util.*;

/**
 * The GameModel class.
 * The Model of the game.
 * Run the Main method to play the game.
 *
 * @author Osas Iyamu
 * @author Oyindamola Taiwo-Olupeka
 */
public class GameModel {

    enum mode {LIGHT, DARK}
    private mode currentMode;
    private ArrayList<Player> players;
    private int position;
    private HashMap<Player, Integer> scores;
    private ArrayList<Card> cardDeck;
    private ArrayList<Card> playingDeck;
    private Player currentPlayer;
    private boolean clockwise;
    private boolean quit;


    /**
     * Constructor for the GameModel (Model) class
     * Initializes the game
     */
    public GameModel(){
        this. players = new ArrayList<>();
        this.scores = new HashMap<Player, Integer>();
        this.cardDeck = new ArrayList<>();
        this.playingDeck = new ArrayList<>();
        this.currentPlayer = null;
        this.position = 0;
        this.clockwise = true;
        this.quit = false;
        currentMode = mode.LIGHT;
        this.init();
    }

    /**
     * A method to initialize the card deck
     */
    private void init() {
        //Initialize card deck
        Random rand = new Random();

        //Initialize the number cards
        for (int i = 0; i < 72; i++) {
            int lightInt = rand.nextInt(9) + 1;
            int lightColor = rand.nextInt(4);
            int darkInt = rand.nextInt(9) + 1;
            int darkColour = rand.nextInt(4);

            cardDeck.add(new NumberCard(Card.type.REGULAR, lightInt, Colors.LIGHTCOLORS.values()[lightColor], darkInt, Colors.DARKCOLORS.values()[darkColour]));
        }

        //Initialize the special cards
        for (int i = 0; i < 12; i++) {
            SpecialCard skip = new SpecialCard(Card.type.SPECIAL, Card.SPECIALCARDS.SKIP, Colors.LIGHTCOLORS.values()[rand.nextInt(4)], Card.SPECIALCARDS.WILD, Colors.DARKCOLORS.values()[rand.nextInt(4)]);
            SpecialCard wild_draw_two = new SpecialCard(Card.type.SPECIAL, Card.SPECIALCARDS.WILD_DRAW_TWO, Colors.LIGHTCOLORS.values()[rand.nextInt(4)], Card.SPECIALCARDS.FLIP, Colors.DARKCOLORS.values()[rand.nextInt(4)]);
            SpecialCard reverse = new SpecialCard(Card.type.SPECIAL, Card.SPECIALCARDS.REVERSE, Colors.LIGHTCOLORS.values()[rand.nextInt(4)], Card.SPECIALCARDS.SKIP_EVERYONE, Colors.DARKCOLORS.values()[rand.nextInt(4)]);
            SpecialCard wild = new SpecialCard(Card.type.SPECIAL, Card.SPECIALCARDS.WILD, Colors.LIGHTCOLORS.UNASSIGNED, Card.SPECIALCARDS.WILD, Colors.DARKCOLORS.UNASSIGNED);

            cardDeck.add(reverse);
            cardDeck.add(wild_draw_two);
            cardDeck.add(skip);
            cardDeck.add(wild);
        }

        Collections.shuffle(cardDeck);
        playingDeck.add(cardDeck.get(cardDeck.size()-1));
        cardDeck.remove(cardDeck.get(cardDeck.size()-1));

    }

    /**
     * All methods unused will be used in the further milestones
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public void addPlayer(Player p) {
        this.players.add(p);
    }

    public void removePlayer(Player p){
        this.players.remove(p);
    }

    public ArrayList<Card> getCardDeck() {
        return cardDeck;
    }

    public void addToDeck(NumberCard card) {
        cardDeck.add(card);
    }

    public void removeFromDeck(NumberCard card) {
        this.cardDeck.remove(card);
    }

    public HashMap<Player, Integer> calculateScore(){
        return null;
    }
    public HashMap<Player, Integer> getScores() {
        return scores;
    }

    public ArrayList<Card> getPlayingDeck() {
        return playingDeck;
    }

    public void setCurrentPlayer(Player p) {
        this.currentPlayer = p;
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * A method to get the mode of the game
     * @return the mode of the game
     */
    public mode getCurrentMode() {
        return currentMode;
    }

    /**
     * A method to distribute cards to everyone
     * @param numberOfCards number of cards to distribute
     */
    private void distributeToAll(int numberOfCards){
        for (Player p : players){
            for (int i=0; i<numberOfCards; i++){
                int lastCardInDeck = cardDeck.size()-1;
                p.addCard(cardDeck.get(lastCardInDeck));
                cardDeck.remove(cardDeck.get(lastCardInDeck));
            }
        }
    }

    /** A method to update the score of each player
     * @param player the current player
     * @param score the score to add
     */
    public void updateScore(Player player, int score){
        if(!(scores.containsKey(player))){
            scores.put(player, score);
        }else{
            scores.replace(player, scores.get(player)+score);
        }
    }

    /**
     * A method to get a card from the bank
     */
    public void getCardFromBank(){
        currentPlayer.getCards().add(cardDeck.get(cardDeck.size() - 1));
        cardDeck.remove(cardDeck.size() - 1);
        System.out.println(currentPlayer.getName() + " picked a card");
    }

    /**
     * A method to implement the current player's turn
     */
    private void implementCurrentPlayerTurn() {
        System.out.println("Your available cards:");
        ArrayList<Card> currentPlayerCards = currentPlayer.getCards();
        if (currentMode == mode.LIGHT) {
            for (int i = 0; i < currentPlayer.getCards().size(); i++) {
                if (currentPlayerCards.get(i).getLightCharacteristics().split(" ")[0].equals("WILD")) {
                    System.out.println(i + 1 + ": " + currentPlayerCards.get(i).getLightCharacteristics().split(" ")[0]);
                } else {
                    System.out.println(i + 1 + ": " + currentPlayerCards.get(i).getLightCharacteristics());
                }
            }
        }
        if (currentMode == mode.DARK) {
            for (int i = 0; i < currentPlayer.getCards().size(); i++) {
                System.out.println(i + 1 + ": " + currentPlayerCards.get(i).getDarkCharacteristics());
            }
        }
        System.out.println("Current Mode : " + currentMode.toString());
        if (this.getCurrentMode() == mode.LIGHT) {
            System.out.println("Top Card: " + playingDeck.get(playingDeck.size() - 1).getLightCharacteristics());
            System.out.println("");
        } else {
            System.out.println("Top Card: " + playingDeck.get(playingDeck.size() - 1).getDarkCharacteristics());
            System.out.println("");
        }

        System.out.print("Pick an index of the card to place or 0 to draw from the bank: ");
        Scanner input = new Scanner(System.in);
        int chosen = input.nextInt();

        if (chosen == 0) {
            getCardFromBank();
        } else {
            while ((chosen - 1) > currentPlayerCards.size() - 1) {
                System.out.print("You don't have this card in your deck, pick something in your deck: ");
                chosen = input.nextInt();
            }

            String currentPlayerLightCharacter = currentPlayerCards.get(chosen - 1).getLightCharacteristics().split(" ")[0];
            String currentPlayerLightColor = currentPlayerCards.get(chosen - 1).getLightCharacteristics().split(" ")[1];
            String playingDeckLightCharacter = playingDeck.get(playingDeck.size() - 1).getLightCharacteristics().split(" ")[0];
            String playingDeckLightColor = playingDeck.get(playingDeck.size() - 1).getLightCharacteristics().split(" ")[1];

            while (((!currentPlayerLightCharacter.equals(playingDeckLightCharacter) && !(currentPlayerLightCharacter.equals("WILD"))) && !(currentPlayerLightColor).equals(playingDeckLightColor))) {
                System.out.print("Invalid choice; Color or numbers don't match\nPick a different option: ");
                chosen = input.nextInt();
                if (chosen == 0) {
                    getCardFromBank();
                    break;
                }
                currentPlayerLightCharacter = currentPlayerCards.get(chosen - 1).getLightCharacteristics().split(" ")[0];
                currentPlayerLightColor = currentPlayerCards.get(chosen - 1).getLightCharacteristics().split(" ")[1];
            }

            if (chosen == 0) {
            } else {
                if (currentPlayerCards.get(chosen - 1).getType() == Card.type.REGULAR) {
                    if (chosen == 0) {
                    } else {
                        playingDeck.add(currentPlayerCards.get(chosen - 1));
                        if (currentMode == mode.LIGHT) {
                            System.out.println("Played: " + currentPlayerCards.get(chosen - 1).getLightCharacteristics());
                            updateScore(currentPlayer, Integer.parseInt(currentPlayerLightCharacter));
                        } else {
                            System.out.println("Played: " + currentPlayerCards.get(chosen - 1).getDarkCharacteristics());
                            updateScore(currentPlayer, Integer.parseInt(currentPlayerLightCharacter));
                        }
                        currentPlayerCards.remove(currentPlayerCards.get(chosen - 1));
                        System.out.print("Current player score: ");
                        System.out.println(scores.get(currentPlayer));
                    }
                } else if (currentPlayerCards.get(chosen - 1).getType() == Card.type.SPECIAL) {
                    String specialCard = "";
                    if (currentMode == mode.LIGHT) {
                        specialCard = currentPlayerCards.get(chosen - 1).getLightCharacteristics();
                    } else if (currentMode == mode.DARK) {
                        specialCard = currentPlayerCards.get(chosen - 1).getDarkCharacteristics();
                    }

                    switch (specialCard.split(" ")[0]) {
                        case "SKIP":
                            this.position = (clockwise ? (this.position + 1) : (this.position - 1)) % players.size();
                            playingDeck.add(currentPlayerCards.get(chosen - 1));
                            currentPlayerCards.remove(currentPlayerCards.get(chosen - 1));
                            System.out.println(players.get((players.indexOf(currentPlayer) + 1) % players.size()).getName() + " has been skipped");
                            break;

                        case "REVERSE":
                            this.clockwise = !this.clockwise;
                            playingDeck.add(currentPlayerCards.get(chosen - 1));
                            currentPlayerCards.remove(currentPlayerCards.get(chosen - 1));
                            System.out.println("\nGameModel order has been reversed");
                            break;

                        case "WILD":
                            if (currentMode == mode.LIGHT) {
                                System.out.print("Choose a color (RED, GREEN, BLUE, YELLOW): ");
                                String chosenColor = input.next().toUpperCase();

                                // Validate the chosen color
                                boolean validColor = false;
                                for (Colors.LIGHTCOLORS color : Colors.LIGHTCOLORS.values()) {
                                    if (color.toString().equals(chosenColor)) {
                                        validColor = true;
                                        break;
                                    }
                                }

                                if (!validColor) {
                                    System.out.println("Invalid color choice. Please choose a valid color.");
                                } else {
                                    // Set the chosen color for the Wild card

                                    currentPlayerCards.get(chosen - 1).setLightColor(Colors.LIGHTCOLORS.valueOf(chosenColor));
                                    //currentPlayerCards.get(chosen - 1).setDarkColor(Colors.DARKCOLORS.valueOf(chosenColor));
                                    playingDeck.add(currentPlayerCards.get(chosen - 1));
                                    System.out.println("Played: " + currentPlayerCards.get(chosen - 1).getLightCharacteristics());
                                    currentPlayerCards.remove(currentPlayerCards.get(chosen - 1));
                                }
                            }

                            if (currentMode == mode.DARK) {
                                System.out.print("Choose a color (PINK, TEAL, PURPLE, ORANGE): ");
                                String chosenColor = input.next().toUpperCase();

                                // Validate the chosen color
                                boolean validColor = false;
                                for (Colors.DARKCOLORS color : Colors.DARKCOLORS.values()) {
                                    if (color.toString().equals(chosenColor)) {
                                        validColor = true;
                                        break;
                                    }
                                }

                                if (!validColor) {
                                    System.out.println("Invalid color choice. Please choose a valid color.");
                                } else {
                                    // Set the chosen color for the Wild card

                                    //currentPlayerCards.get(chosen - 1).setLightColor(Colors.LIGHTCOLORS.valueOf(chosenColor));
                                    currentPlayerCards.get(chosen - 1).setDarkColor(Colors.DARKCOLORS.valueOf(chosenColor));
                                    playingDeck.add(currentPlayerCards.get(chosen - 1));
                                    currentPlayerCards.remove(currentPlayerCards.get(chosen - 1));
                                    System.out.println("Played: " + currentPlayerCards.get(chosen - 1).getDarkCharacteristics());
                                }
                            }
                            break;

                        case "WILD_DRAW_TWO":
                            for (int i = 0; i < 2; i++) {
                                players.get(position + 1 % (players.size() - 1)).addCard(cardDeck.get(cardDeck.size() - 1));
                            }
                            this.position = (clockwise ? (this.position + 1) : (this.position - 1)) % players.size();
                            playingDeck.add(currentPlayerCards.get(chosen - 1));
                            currentPlayerCards.remove(currentPlayerCards.get(chosen - 1));
                            System.out.println(players.get((players.indexOf(currentPlayer) + 1)).getName() + " picked 2 and will be skipped");

                    }
                }
            }
        }
        if (currentPlayer.getCards().isEmpty()) {
            System.out.println("Winner: " + currentPlayer.getName());
            System.out.println("In order of scores: ");
            scores.entrySet().stream()
                    .sorted(Map.Entry.<Player, Integer>comparingByValue())
                    .forEach((player) -> System.out.println(player.getKey() + ": " + player.getValue()));
        }
    }

    /**
     * Play the game
     */
    public void play(){
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the game of UNO Flip!");
        System.out.print("Enter number of Players(minimum 2 players): ");
        int numberOfPlayers = input.nextInt();

        while(numberOfPlayers < 2){
            System.out.println("You need at least 2 players to play this game");
            System.out.print("Enter number of Players(minimum 2 players): ");
            numberOfPlayers = input.nextInt();
        }

        int count = 1;
        Scanner newInput = new Scanner(System.in);

        for (int i=0; i<numberOfPlayers; i++){
            System.out.print("Enter Player name " + count + ": ");
            String player = newInput.nextLine();
            this.players.add(new Player(player));
            count++;
        }

        distributeToAll(7);

        //GameModel starts
        while(!quit){
            currentPlayer = players.get(this.position);
            System.out.println("\n---------------------------------------------------------");
            System.out.println(currentPlayer.getName() + "'s turn");

            implementCurrentPlayerTurn();
            if(clockwise) {
                this.position = this.position + 1;
            } else {
                this.position = this.position - 1;
            }
            this.position = ((this.position%(numberOfPlayers)) + numberOfPlayers) % numberOfPlayers; //position of the next player
        }

    }

    public static void main(String[] args) {
        GameModel gameModel = new GameModel();
        gameModel.play();
    }

}