import java.io.*;
import java.util.*;

/**
 * The UNOModel class.
 * Model part of the implementation of the MVC pattern.
 *
 * @author Osas Iyamu
 * @author Oyindamola Taiwo-Olupeka
 */
public class UNOModel implements Serializable {

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
    private List<UNOView> views;

    private Card topCard;
    private boolean winner;

    //private List<GameState> gameStates;
    //private int currentStateIndex;
    //private List<GameState> history; // Store game state history for undo

    //private Stack<GameState> redoStack;
    //private Stack<GameState> undoStack;

    private GameState undo, redo;


    /**
     * Constructor for the UNOModel (Model) class
     * Initializes the game
     */
    public UNOModel() {
        this.players = new ArrayList<>();
        this.scores = new HashMap<Player, Integer>();
        this.scoreGuide = new HashMap<>();
        this.cardDeck = new ArrayList<>();
        this.playingDeck = new ArrayList<>();
        this.currentPlayer = null;
        this.position = 0;
        this.clockwise = true;
        this.currentMode = mode.LIGHT;
        this.views = new ArrayList<>();

        //this.undoStack = new Stack<>();
        //this.redoStack = new Stack<>();

        //this.history = new ArrayList<>();
        //this.currentStateIndex = -1; // No game state initially

        undo = new GameState(this);
        redo = new GameState(this);
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
            SpecialCard reverse = new SpecialCard(Card.type.SPECIAL, Card.SPECIALCARDS.REVERSE, Colors.LIGHTCOLORS.values()[rand.nextInt(4)], Card.SPECIALCARDS.REVERSE, Colors.DARKCOLORS.values()[rand.nextInt(4)]);
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
        for (Player p : players) {
            scores.put(p, 0);
        }
        distributeToAll(7);
        playingDeck.add(cardDeck.get(cardDeck.size() - 1));
        topCard = playingDeck.get(playingDeck.size() - 1);

        //Ensure that the top card isn't a wild card
        while (topCard.getLightCharacteristics().split(" ")[0].equals("WILD")) {
            Collections.shuffle(cardDeck);
            topCard = playingDeck.get(playingDeck.size() - 1);
        }
        cardDeck.remove(cardDeck.get(cardDeck.size() - 1));

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
     * A method to get all the players in the game
     *
     * @return players in the game
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }


    /**
     * Adds a view from the Uno game
     *
     * @param v, the view
     */
    public void addUNOView(UNOView v) {
        views.add(v);
    }

    /**
     * Removes a view from the Uno game
     *
     * @param v, the view
     */
    public void removeUNOView(UNOView v) {
        views.remove(v);
    }

    /**
     * A method to add player to the player list
     *
     * @param p the player to add
     */
    public void addPlayer(Player p) {
        this.players.add(p);
    }

    /**
     * A method to get the top cad of the deck
     *
     * @return the top card of the playing deck
     */
    public Card getTopCard() {
        return topCard;
    }

    /**
     * Return true if there's a winner
     *
     * @return boolean, isWinner?
     */
    public boolean isWinner() {
        return winner;
    }

    /**
     * Return true if game direction is clockwise
     *
     * @return boolean, isClockwise?
     */
    public boolean isClockwise() {
        return clockwise;
    }

    /**
     * A method to remove a player from the list
     *
     * @param p, the player to remove
     */
    public void removePlayer(Player p) {
        this.players.remove(p);
    }

    /**
     * A method to get the card deck
     *
     * @return the card deck
     */
    public ArrayList<Card> getCardDeck() {
        return cardDeck;
    }

    /**
     * A method to add a card to the card deck
     *
     * @param card, the card to add
     */
    public void addToDeck(NumberCard card) {
        cardDeck.add(card);
    }

    /**
     * A method to remove from the card deck
     *
     * @param card, the card to remove
     */
    public void removeFromDeck(NumberCard card) {
        this.cardDeck.remove(card);
    }

    /**
     * A method to get the scores
     *
     * @return the list of scores
     */
    public HashMap<Player, Integer> getScores() {
        return scores;
    }

    /**
     * A method to get the playing deck
     *
     * @return the playing deck
     */
    public ArrayList<Card> getPlayingDeck() {
        return playingDeck;
    }

    /**
     * A method to set the current player
     *
     * @param p, the player to set
     */
    public void setCurrentPlayer(Player p) {
        this.currentPlayer = p;
    }

    /**
     * A method to get the current player
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * A method to get the mode of the game
     *
     * @return the mode of the game
     */
    public mode getCurrentMode() {
        return currentMode;
    }

    /**
     * A method to distribute cards to everyone
     *
     * @param numberOfCards number of cards to distribute
     */
    private void distributeToAll(int numberOfCards) {
        for (Player p : players) {
            for (int i = 0; i < numberOfCards; i++) {
                int lastCardInDeck = cardDeck.size() - 1;
                p.addCard(cardDeck.get(lastCardInDeck));
                cardDeck.remove(cardDeck.get(lastCardInDeck));
            }
        }
    }

    /**
     * A method to update the score of each player
     *
     * @param player the current player
     * @param score  the score to add
     */
    public void updateScore(Player player, int score) {
        if (!(scores.containsKey(player))) {
            scores.put(player, score);
        } else {
            scores.replace(player, scores.get(player) + score);
        }
    }

    /**
     * A method to get a card from the bank
     */
    public void drawFromBank() {
        currentPlayer.getCards().add(cardDeck.get(cardDeck.size() - 1));
        cardDeck.remove(cardDeck.size() - 1);
        for (UNOView view : views) {
            view.handleDrawCard(this);
        }
        System.out.println(currentPlayer.getName() + " picked a card");
    }


    //-------------------For the UI version-------------------//

    /**
     * A method to draw card
     */
    public void drawCard() {
        currentPlayer.getCards().add(cardDeck.get(cardDeck.size() - 1));
        cardDeck.remove(cardDeck.size() - 1);
    }

    /**
     * A method to change the turns
     */
    public void changeTurn() {
        if (clockwise) {
            this.position = this.position + 1;
        } else {
            this.position = this.position - 1;
        }
        this.position = ((this.position % (players.size())) + players.size()) % players.size(); //position of the next player
        currentPlayer = players.get(position);
        for (UNOView view : views) {
            view.handleNextPlayer(this);
        }
    }

    /**
     * A method to check if the card characteristics is special, and if yes, executes a block of code
     *
     * @param characteristics, the characteristics to be checked
     */
    private void checkSpecial(String characteristics) {
        if (currentMode.equals(mode.LIGHT)) {
            if (characteristics.equals("SKIP")) {
                this.position = (clockwise ? (this.position + 1) : (this.position - 1)) % players.size();
            } else if (characteristics.equals("WILD_DRAW_TWO")) {
                for (int i = 0; i < 2; i++) {
                    players.get(((this.position + 1 % (players.size())) + players.size()) % players.size()).addCard(cardDeck.get(cardDeck.size() - 1));
                    cardDeck.remove(cardDeck.size() - 1);
                }
                this.position = (clockwise ? (this.position + 1) : (this.position - 1)) % players.size();
            } else if (characteristics.equals("REVERSE")) {
                this.clockwise = !this.clockwise;
            } else if (characteristics.equals("FLIP")) {
                this.currentMode = mode.DARK;
            }
        } else {
            if (characteristics.equals("SKIP")) {
                this.position = (clockwise ? (this.position + 1) : (this.position - 1)) % players.size();
            } else if (characteristics.equals("WILD_DRAW_TWO")) {
                for (int i = 0; i < 2; i++) {
                    players.get(((this.position + 1 % (players.size())) + players.size()) % players.size()).addCard(cardDeck.get(cardDeck.size() - 1));
                }
                this.position = (clockwise ? (this.position + 1) : (this.position - 1)) % players.size();
            } else if (characteristics.equals("REVERSE")) {
                this.clockwise = !this.clockwise;
            } else if (characteristics.equals("FLIP")) {
                this.currentMode = mode.LIGHT;
            }
        }
    }

    /**
     * A method to check a wild card and perform the operation based on the result
     *
     * @param characteristics, the characteristics of the card
     * @param color,           the color
     */
    private void checkWild(String characteristics, String color) {
        if (currentMode.equals(mode.LIGHT)) {
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
        } else {
            if (color.equals("unassigned")) {
                for (int i = 0; i < this.currentPlayer.getCards().size(); i++) {
                    if (this.currentPlayer.getCards().get(i).getDarkCharacteristics().split(" ")[0].equals(characteristics)) {
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
        }
    }


    /**
     * A method to validate the placement of cards
     *
     * @param characteristics, the characteristics of the card
     * @param color,           the color of the card
     */
    public void validatePlacement(String characteristics, String color) {
        undo = new GameState(this);
        if (currentMode.equals(mode.LIGHT)) {

            checkWild(characteristics, color);

            if (characteristics.equals(topCard.getLightCharacteristics().split(" ")[0]) ||
                    color.equals(topCard.getLightCharacteristics().split(" ")[1])) {

                checkSpecial(characteristics);

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

            checkWild(characteristics, color);
            if (characteristics.equals(topCard.getDarkCharacteristics().split(" ")[0]) ||
                    color.equals(topCard.getDarkCharacteristics().split(" ")[1])) {
                checkSpecial(characteristics);

                for (int i = 0; i < this.currentPlayer.getCards().size(); i++) {
                    if (this.currentPlayer.getCards().get(i).getDarkCharacteristics().equals(characteristics + " " + color)) {
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
        }
        redo = new GameState(this);
    }

    public void undo() {
        if (undo == null) {
            this.players = undo.getModel().getPlayers();
            this.scores = undo.getModel().getScores();
            this.cardDeck = undo.getModel().getCardDeck();
            this.playingDeck = undo.getModel().getPlayingDeck();
            this.currentPlayer = undo.getModel().getCurrentPlayer();
            this.clockwise = undo.getModel().isClockwise();
            this.currentMode = undo.getModel().getCurrentMode();
            this.topCard = undo.getModel().getTopCard();
            this.winner = undo.getModel().isWinner();
            //this.scoreGuide = undo.getModel().scoreGuide;
            //this.position = undo.getModel().position;

            for (UNOView view : views) {
                view.handleUndo(this);
            }

        }

    }

    public void redo() {
        if (redo == null) {
            this.players = redo.getModel().getPlayers();
            this.scores = redo.getModel().getScores();
            this.cardDeck = redo.getModel().getCardDeck();
            this.playingDeck = redo.getModel().getPlayingDeck();
            this.currentPlayer = redo.getModel().getCurrentPlayer();
            this.clockwise = redo.getModel().isClockwise();
            this.currentMode = redo.getModel().getCurrentMode();
            this.topCard = redo.getModel().getTopCard();
            this.winner = redo.getModel().isWinner();
            //this.scoreGuide = redo.getModel().scoreGuide;
            //this.position = redo.getModel().position;


            for (UNOView view : views) {
                view.handleRedo(this);
            }
        }
    }

    /**
     * A method to place AI card
     * @param card, the card to place
     */
    private void placeAICard(Card card){
        if(currentMode.equals(mode.LIGHT)) {
            playingDeck.add(card);
            currentPlayer.getCards().remove(card);
            this.topCard = this.playingDeck.get(this.playingDeck.size() - 1);
            updateScore(currentPlayer, this.scoreGuide.get(card.getLightCharacteristics().split(" ")[0]));
        }else{
            playingDeck.add(card);
            currentPlayer.getCards().remove(card);
            this.topCard = this.playingDeck.get(this.playingDeck.size() - 1);
            updateScore(currentPlayer, this.scoreGuide.get(card.getDarkCharacteristics().split(" ")[0]));
        }
    }

    /**
     * A method to implement the AI's turn
     */
    public void implementAITurn(){
        int cardSize = currentPlayer.getCards().size();
        Random rand = new Random();
        int colorIndex;
        for(Card card : currentPlayer.getCards()){
            if(currentMode.equals(mode.LIGHT)){
                if(card.getLightCharacteristics().split(" ")[0].equals(topCard.getLightCharacteristics().split(" ")[0]) ||
                        card.getLightCharacteristics().split(" ")[1].equals(topCard.getLightCharacteristics().split(" ")[1])){
                    checkSpecial(card.getLightCharacteristics().split(" ")[0]);
                    placeAICard(card);
                    break;
                }
                if (card.getLightCharacteristics().split(" ")[1].equals("UNASSIGNED")) {
                    colorIndex = rand.nextInt(4);
                    card.setLightColor(Colors.LIGHTCOLORS.values()[colorIndex]);
                    placeAICard(card);
                    break;
                }
            } else if(currentMode.equals(mode.DARK)){
                if(card.getDarkCharacteristics().split(" ")[0].equals(topCard.getDarkCharacteristics().split(" ")[0]) ||
                        card.getDarkCharacteristics().split(" ")[1].equals(topCard.getDarkCharacteristics().split(" ")[1])){
                    checkSpecial(card.getDarkCharacteristics().split(" ")[0]);
                    placeAICard(card);
                    break;
                }
                if (card.getDarkCharacteristics().split(" ")[1].equals("UNASSIGNED")) {
                    colorIndex = rand.nextInt(4);
                    card.setDarkColor(Colors.DARKCOLORS.values()[colorIndex]);

                    placeAICard(card);
                }
            }
        }

        if(cardSize == currentPlayer.getCards().size()){
            drawFromBank();
        }
        if(currentPlayer.getCards().isEmpty()){
            winner = true;
        }
        UNOEvent e = new UNOEvent(true, this);
        for (UNOView views : this.views) {
            views.handleAITurn(e);
        }

    }


/* STACK VERSION

    public void saveGameState() {
        GameState currentState = new GameState(players, scores, cardDeck, playingDeck, currentPlayer,clockwise,
                currentMode, topCard, winner);
        undoStack.push(currentState);

        redoStack.clear(); // Clear redo stack as a new action is performed after undo
    }

    public void undo() {

        GameState currentState = new GameState(players, scores, cardDeck, playingDeck, currentPlayer,clockwise,
                currentMode, topCard, winner);
        undoStack.push(currentState);

        redoStack.clear(); // Clear redo stack as a new action is performed after undo


        //GameState previousState = undoStack.peek(); // Get the previous state
        if (!undoStack.isEmpty()) {
//            redoStack.push(undoStack.pop()); // Move current state to redo stack
//            GameState previousState = undoStack.peek(); // Get the previous state
//            System.out.println(previousState);
//            restoreGameState(previousState);



            redoStack.push(new GameState(players, scores, cardDeck, playingDeck, currentPlayer,clockwise,
                    currentMode, topCard, winner));
            GameState previousState = undoStack.peek();
            System.out.println(previousState);
            restoreGameState(previousState); // Restore the game state to the previous state

            for (UNOView view : views) {
                view.handleUndo(this);
            }
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
//            undoStack.push(redoStack.pop()); // Move current state to undo stack
//            GameState nextState = undoStack.peek(); // Get the next state
//            // Restore the game state to the next state
//            restoreGameState(nextState);



            undoStack.push(new GameState(players, scores, cardDeck, playingDeck, currentPlayer,clockwise,
                    currentMode, topCard, winner));
            GameState nextState = redoStack.pop();
            restoreGameState(nextState);

            for (UNOView view : views){
                view.handleRedo(this);
            }
        }
    }

    private void restoreGameState(GameState state) {
        this.players.clear();
        for (Player savedPlayer : state.getPlayers()) {
            Player player = new Player(savedPlayer.getName()); // Create new player with same name
            player.setCards(new ArrayList<>(savedPlayer.getCards())); // Restore player's hand
            this.players.add(player);
        }

        // Restore scores
        this.scores = new HashMap<>(state.getScores());

        // Restore card decks
        this.cardDeck = new ArrayList<>(state.getCardDeck());
        this.playingDeck = new ArrayList<>(state.getPlayingDeck());

        // Restore current player, mode, top card, and other game state attributes
        this.currentPlayer = findPlayer(state.getCurrentPlayer()); // Assuming findEquivalentPlayer() finds the player object reference
        this.clockwise = state.isClockwise();
        this.currentMode = state.getCurrentMode();
        this.topCard = state.getTopCard();
        this.winner = state.isWinner();
    }

    private Player findPlayer(Player p1) {
        for (Player player : this.players) {
            if (player.getName().equals(p1.getName())) {
                return player;
            }
        }
        return null; // Handle if the player isn't found (might need additional logic)
    }

 */



 /*  ARRAYLIST VERSION

    public void saveGameState() {
        GameState currentState = new GameState(players, scores, cardDeck, playingDeck, currentPlayer,clockwise,
                currentMode, topCard, winner);
        history.add(currentState);
        currentStateIndex++;
    }

    public void undo() {

        if (currentStateIndex > 0) {
            currentStateIndex--;
            restoreGameState(history.get(currentStateIndex));

            for (UNOView view : views) {
                view.handleUndo(this);
            }
        }
    }

    public void redo() {

        if (currentStateIndex < history.size() - 1) {
            currentStateIndex++;
            restoreGameState(history.get(currentStateIndex));

            for (UNOView view : views) {
                view.handleRedo(this);
            }
        }

    }

    private void restoreGameState(GameState gameState) {
        this.players = new ArrayList<>(gameState.getPlayers());
        this.scores = new HashMap<>(gameState.getScores());
        this.cardDeck = new ArrayList<>(gameState.getCardDeck());
        this.playingDeck = new ArrayList<>(gameState.getPlayingDeck());
        this.currentPlayer = gameState.getCurrentPlayer();
        this.clockwise = gameState.isClockwise();
        this.currentMode = gameState.getCurrentMode();
        this.topCard = gameState.getTopCard();
        this.winner = gameState.isWinner();
    }

*/



    // SAVE / LOAD
    public void save(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(this);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error while saving the game: " + e.getMessage());
        }
    }

    public static UNOModel load(String fileName) {
        UNOModel loadedGame = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            loadedGame = (UNOModel) inputStream.readObject();
            System.out.println("Game loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while loading the game: " + e.getMessage());
        }
        return loadedGame;
    }
}
