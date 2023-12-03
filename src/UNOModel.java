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

    /**
     * Constructor for the UNOModel (Model) class
     * Initializes the game
     */
    public UNOModel(){
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
        for (Player p : players){
            scores.put(p, 0);
        }
        distributeToAll(7);
        playingDeck.add(cardDeck.get(cardDeck.size()-1));
        topCard = playingDeck.get(playingDeck.size()-1);

        //Ensure that the top card isn't a wild card
        while(topCard.getLightCharacteristics().split(" ")[0].equals("WILD")){
            Collections.shuffle(cardDeck);
            topCard = playingDeck.get(playingDeck.size()-1);
        }
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
     * A method to get all the players in the game
     * @return players in the game
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

    /**
     * A method to get the top cad of the deck
     * @return the top card of the playing deck
     */
    public Card getTopCard() {
        return topCard;
    }

    /**
     * Return true if there's a winner
     * @return boolean, isWinner?
     */
    public boolean isWinner() {
        return winner;
    }

    /**
     * A method to remove a player from the list
     * @param p, the player to remove
     */
    public void removePlayer(Player p){
        this.players.remove(p);
    }

    /**
     * A method to get the card deck
     * @return the card deck
     */
    public ArrayList<Card> getCardDeck() {
        return cardDeck;
    }

    /**
     * A method to add a card to the card deck
     * @param card, the card to add
     */
    public void addToDeck(NumberCard card) {
        cardDeck.add(card);
    }

    /**
     * A method to remove from the card deck
     * @param card, the card to remove
     */
    public void removeFromDeck(NumberCard card) {
        this.cardDeck.remove(card);
    }

    /**
     * A method to get the scores
     * @return the list of scores
     */
    public HashMap<Player, Integer> getScores() {
        return scores;
    }

    /**
     * A method to get the playing deck
     * @return the playing deck
     */
    public ArrayList<Card> getPlayingDeck() {
        return playingDeck;
    }

    /**
     * A method to set the current player
     * @param p, the player to set
     */
    public void setCurrentPlayer(Player p) {
        this.currentPlayer = p;
    }

    /**
     * A method to get the current player
     * @return the current player
     */
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
    public void drawFromBank(){
        currentPlayer.getCards().add(cardDeck.get(cardDeck.size() - 1));
        cardDeck.remove(cardDeck.size() - 1);
        for (UNOView view : views){
            view.handleDrawCard(this);
        }
        System.out.println(currentPlayer.getName() + " picked a card");
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
     *  A method to check if the card characteristics is special, and if yes, executes a block of code
     * @param characteristics, the characteristics to be checked
     */
    private void checkSpecial(String characteristics){
        if(currentMode.equals(mode.LIGHT)){
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
        }else {
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
     * @param characteristics, the characteristics of the card
     * @param color, the color
     */
    private void checkWild(String characteristics, String color){
        if(currentMode.equals(mode.LIGHT)) {
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
        }else{
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
     * @param characteristics, the characteristics of the card
     * @param color, the color of the card
     */
    public void validatePlacement(String characteristics, String color) {
        if(currentMode.equals(mode.LIGHT)){

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
            if(characteristics.equals(topCard.getDarkCharacteristics().split(" ")[0]) ||
                    color.equals(topCard.getDarkCharacteristics().split(" ")[1])){
                checkSpecial(characteristics);

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

    public void save(String fileName) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName + ".txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.flush();
        objectOutputStream.close();

        for(UNOView view : views){
            view.handleSaveGame();
        }
    }

    public static UNOModel load(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(fileName + ".txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        UNOModel unoModel = (UNOModel) objectInputStream.readObject();
        objectInputStream.close();
        return unoModel;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        UNOModel model = UNOModel.load("NewModel");
        System.out.println(model.getPlayers());
        System.out.println(model.currentMode);
        System.out.println(model.topCard);
        System.out.println(model.getCurrentPlayer().getCards());
    }
}
