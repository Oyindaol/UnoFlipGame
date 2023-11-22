import java.util.*;

/**
 * The UNOModel class.
 * Model part of the implementation of the MVC pattern.
 *
 * @author Osas Iyamu
 * @author Oyindamola Taiwo-Olupeka
 */
public class UNOModel {

    enum mode {LIGHT, DARK}
    private mode currentMode;
    private ArrayList<Player> players;
    private int position;
    private HashMap<Player, Integer> scores;
    private HashMap<String, Integer> scoreGuide;
    private ArrayList<Card> cardDeck;
    private ArrayList<Card> playingDeck;
    private Player currentPlayer;
    private boolean clockwise;
    private boolean quit;
    private List<UNOView> views;

    private Card topCard;
    private boolean winner;



    /**
     * Constructor for the UNOModel (Model) class
     * Initializes the game
     */
    public UNOModel(){
        this. players = new ArrayList<>();
        this.scores = new HashMap<Player, Integer>();
        this.scoreGuide = new HashMap<>();
        this.cardDeck = new ArrayList<>();
        this.playingDeck = new ArrayList<>();
        this.currentPlayer = null;
        this.position = 0;
        this.clockwise = true;
        this.quit = false;
        this.currentMode = mode.LIGHT;
        this.views = new ArrayList<>();
    }

    /**
     * A method to initialize the card deck
     */
    public void init() {
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
            SpecialCard flip = new SpecialCard(Card.type.SPECIAL, Card.SPECIALCARDS.FLIP, Colors.LIGHTCOLORS.values()[rand.nextInt(4)], Card.SPECIALCARDS.FLIP, Colors.DARKCOLORS.values()[rand.nextInt(4)]);

            cardDeck.add(reverse);
            cardDeck.add(wild_draw_two);
            cardDeck.add(skip);
            cardDeck.add(wild);
            cardDeck.add(flip);
        }

        Collections.shuffle(cardDeck);


        //Initialize the scores
        for (Player p : players){
            scores.put(p, 0);
        }
        distributeToAll(7);
        playingDeck.add(cardDeck.get(cardDeck.size()-1));
        topCard = playingDeck.get(playingDeck.size()-1);
        cardDeck.remove(cardDeck.get(cardDeck.size()-1));

        this.scoreGuide.put("1", 1);
        this.scoreGuide.put("2", 2);
        this.scoreGuide.put("3", 3);
        this.scoreGuide.put("4", 4);
        this.scoreGuide.put("5", 5);
        this.scoreGuide.put("6", 6);
        this.scoreGuide.put("7", 7);
        this.scoreGuide.put("8", 8);
        this.scoreGuide.put("9", 9);
        this.scoreGuide.put("SKIP", 20);
        this.scoreGuide.put("FLIP", 20);
        this.scoreGuide.put("REVERSE", 20);
        this.scoreGuide.put("WILD_DRAW_TWO", 50);
        this.scoreGuide.put("WILD", 60);

    }

    /**
     * All methods unused will be used in the further milestones
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }


    /**
     * Adds a view from the Uno game
     * @param v, the view
     */
    public void addUNOView(UNOView v) {
        views.add(v);
    }

    /**
     * Removes a view from the Uno game
     * @param v, the view
     */
    public void removeUNOView(UNOView v) {
        views.remove(v);
    }

    /**
     * A method to add player to the player list
     * @param p the player to add
     */
    public void addPlayer(Player p) {
        this.players.add(p);
    }
    public Card getTopCard() {
        return topCard;
    }

    public boolean isWinner() {
        return winner;
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

    /**
     * A method to get the scores
     * @return the list of scores
     */
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
     * A method to set the current position of the game
     * @param position
     */
    public void setPosition(int position){
        this.position=position;
    }

    /**
     * A method to get the current position of the game
     * @return int
     */
    public int getPosition(){
        return position;
    }
    /**
     * A method to get the current direction of the game
     * @return boolean
     */
    public boolean getDirection(){
        return clockwise;
    }
    /**
     * A method to set the current direction of the game
     *
     */
    public void setDirection(boolean direction){
        this.clockwise=direction;
    }


    /**
     * A method to get a card from the bank
     */
    public void drawFromBank(){
        currentPlayer.getCards().add(cardDeck.get(cardDeck.size() - 1));
        cardDeck.remove(cardDeck.size() - 1);
        for (UNOView view : views){
            view.handleDrawCard(this);
        }
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
            drawFromBank();
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
                    drawFromBank();
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
                            System.out.println("\nUNOModel order has been reversed");
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


    //-------------------For the terminal version-------------------//
    /**
     * Play the game
     */
    public void play(){
        init();
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the game of UNO Flip!");
        System.out.print("Enter number of Players(minimum 2 players): ");
        int numberOfPlayers = input.nextInt();

        while(numberOfPlayers < 2){
            System.out.println("You need at least 2 players to play this game");
            System.out.print("Enter number of Players(minimum 2 players): ");
            numberOfPlayers = input.nextInt();
        }

        System.out.print("Enter number of human Players: ");
        int numberOfHumanPlayers = input.nextInt();

        System.out.print("Enter number of AI Players: ");
        int numberOfAIPlayers = input.nextInt();

        int totalPlayers = numberOfHumanPlayers + numberOfAIPlayers;
        while (totalPlayers != numberOfPlayers) {
            System.out.println("The players don't add up to Total players");
            System.out.print("Enter number of human Players: ");
            numberOfHumanPlayers = input.nextInt();

            System.out.print("Enter number of AI Players: ");
            numberOfAIPlayers = input.nextInt();
            totalPlayers = numberOfHumanPlayers + numberOfAIPlayers; // Update totalPlayers
        }


        int count = 1;
        Scanner newInput = new Scanner(System.in);


        for (int i = 0; i < numberOfHumanPlayers; i++){
            System.out.print("Enter Player name " + count + ": ");
            String player = newInput.nextLine();
            this.players.add(new Player(player));
            count++;
        }
        for (int i = 0; i < numberOfAIPlayers; i++) {
            String playerName = "AI " + (i + 1);
            this.players.add(new AIPlayer(playerName));
        }


        distributeToAll(7);

        //UNOModel starts
        while(!quit){
            currentPlayer = players.get(this.position);
            System.out.println("\n---------------------------------------------------------");
            System.out.println(currentPlayer.getName() + "'s turn");

            // Call the appropriate method based on player type
            if (currentPlayer instanceof AIPlayer) {
                ((AIPlayer) currentPlayer).implementCurrentAIPlayerTurn(this, currentMode);
            } else {
                implementCurrentPlayerTurn();
            }
            if(clockwise) {
                this.position = this.position + 1;
            } else {
                this.position = this.position - 1;
            }
            this.position = ((this.position%(numberOfPlayers)) + numberOfPlayers) % numberOfPlayers; //position of the next player
        }

    }


    //-------------------For the UI version-------------------//

    /**
     * A method to draw card
     */
    public void drawCard(){
        currentPlayer.getCards().add(cardDeck.get(cardDeck.size() - 1));
        cardDeck.remove(cardDeck.size() - 1);
    }

    /**
     * A method to change the turns
     */
    public void changeTurn(){
        if(clockwise) {
            this.position = this.position + 1;
        } else {
            this.position = this.position - 1;
        }
        this.position = ((this.position%(players.size())) + players.size()) % players.size(); //position of the next player
        currentPlayer = players.get(position);
        for (UNOView view : views){
            view.handleNextPlayer(this);
        }
    }

    /**
     * A method to validate the placement of cards
     * @param characteristics, the characteristics of the card
     * @param color, the color of the card
     */
    public void validatePlacement(String characteristics, String color) {
        if (color.equals("unassigned")) {
            for (int i = 0; i < this.currentPlayer.getCards().size(); i++) {
                if (this.currentPlayer.getCards().get(i).getLightCharacteristics().split(" ")[0].equals(characteristics)) {
                    playingDeck.add(this.currentPlayer.getCards().get(i));
                    currentPlayer.getCards().remove(this.currentPlayer.getCards().get(i));
                    this.topCard = this.playingDeck.get(this.playingDeck.size() - 1);
                    updateScore(currentPlayer, this.scoreGuide.get(characteristics));

                    if (currentPlayer.getCards().isEmpty()) {
                        winner = true;
                    }
                    break;
                }
            }
            for (UNOView view : views) {
                view.handleWildCard(this);
            }
        }
        if(currentMode.equals(mode.LIGHT)){
            if (characteristics.equals(topCard.getLightCharacteristics().split(" ")[0]) || color.equals(topCard.getLightCharacteristics().split(" ")[1])) {
                if (characteristics.equals("SKIP")) {
                    this.position = (clockwise ? (this.position + 1) : (this.position - 1)) % players.size();
                } else if (characteristics.equals("WILD_DRAW_TWO")) {
                    for (int i = 0; i < 2; i++) {
                        players.get(((this.position % (players.size())) + players.size()) % players.size()).addCard(cardDeck.get(cardDeck.size() - 1));
                    }
                    this.position = (clockwise ? (this.position + 1) : (this.position - 1)) % players.size();
                } else if (characteristics.equals("REVERSE")) {
                    this.clockwise = !this.clockwise;
                } else if (characteristics.equals("FLIP")) {
                        this.currentMode = mode.DARK;
                }

                for (int i = 0; i < this.currentPlayer.getCards().size(); i++) {
                    if (this.currentPlayer.getCards().get(i).getLightCharacteristics().equals(characteristics + " " + color)) {
                        playingDeck.add(this.currentPlayer.getCards().get(i));
                        currentPlayer.getCards().remove(this.currentPlayer.getCards().get(i));
                        this.topCard = this.playingDeck.get(this.playingDeck.size() - 1);
                        updateScore(currentPlayer, this.scoreGuide.get(characteristics));

                        if (currentPlayer.getCards().isEmpty()) {
                            winner = true;
                        }
                        break;
                    }
                }
                UNOEvent e = new UNOEvent(true, this);
                for (UNOView views : this.views) {
                    views.handlePlacement(e);
                }
            } else {
                UNOEvent e = new UNOEvent(false, this);
                for (UNOView views : views) {
                    views.handlePlacement(e);
                }
            }
        } else if (currentMode.equals(mode.DARK)) {
            if(characteristics.equals(topCard.getDarkCharacteristics().split(" ")[0]) || color.equals(topCard.getDarkCharacteristics().split(" ")[1])){
                if (characteristics.equals("SKIP")){
                    this.position = (clockwise ? (this.position + 1) : (this.position - 1)) % players.size();
                } else if (characteristics.equals("WILD_DRAW_TWO")) {
                    for (int i = 0; i < 2; i++) {
                        players.get(((this.position%(players.size())) + players.size()) % players.size()).addCard(cardDeck.get(cardDeck.size() - 1));
                    }
                    this.position = (clockwise ? (this.position + 1) : (this.position - 1)) % players.size();
                } else if (characteristics.equals("REVERSE")) {
                    this.clockwise = !this.clockwise;
                } else if (characteristics.equals("FLIP")){
                    this.currentMode = mode.LIGHT;
                }

                for (int i = 0; i < this.currentPlayer.getCards().size(); i++) {
                    if (this.currentPlayer.getCards().get(i).getDarkCharacteristics().equals(characteristics + " " + color)) {
                        playingDeck.add(this.currentPlayer.getCards().get(i));
                        currentPlayer.getCards().remove(this.currentPlayer.getCards().get(i));
                        this.topCard = this.playingDeck.get(this.playingDeck.size() - 1);
                        updateScore(currentPlayer, this.scoreGuide.get(characteristics));

                        if(currentPlayer.getCards().isEmpty()){
                            winner = true;
                        }
                        break;
                    }
                }
                UNOEvent e = new UNOEvent(true, this);
                for (UNOView views : this.views) {
                    views.handlePlacement(e);
                }
            }
            else {
                UNOEvent e = new UNOEvent(false, this);
                for (UNOView views: views){
                    views.handlePlacement(e);
                }
            }
        }

    }



    public void implementAITurn(){
        //Check the top card
        //Checks the cards the AI has and if there's a card with the same color or the same characteristics, then place that card
        //Update the view
    }
    /**public static void main(String[] args) {
            UNOModel model = new UNOModel();
            model.play();
            //new UNOFrame(model);

    }*/





}
