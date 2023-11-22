import java.util.*;
/**
 * The AIPlayer class.
 *This class handles the actions of the AI player of the UNO Flip game.
 *
 * @author Forewa Aboderin
 */
public class AIPlayer extends Player{


    /**
     * The constructor of the AIPlayer class
     *
     * @param name the name of the Player
     */
    public AIPlayer(String name) {

        super(name);

    }

    /**
     * A method to implement the current AI's turn.
     * @param model
     * @param mode
     */
    public void implementCurrentAIPlayerTurn(UNOModel model,UNOModel.mode mode) {

        System.out.println("Your available cards are: ");
        for (Card card : model.getCurrentPlayer().getCards()) {
            if (mode == UNOModel.mode.LIGHT) {

                System.out.println(card.getLightCharacteristics());
            }
            if (mode == UNOModel.mode.DARK) {
                System.out.println(card.getDarkCharacteristics());
            }

        }
        if (mode == UNOModel.mode.LIGHT) {
            System.out.println();
            System.out.println("Top Card: " + model.getPlayingDeck().get(model.getPlayingDeck().size() - 1).getLightCharacteristics());

        }
        if (mode == UNOModel.mode.DARK) {
            System.out.println("Top Card: " + model.getPlayingDeck().get(model.getPlayingDeck().size() - 1).getDarkCharacteristics());

        }

        playAI(model, mode);

    }
    /**
     * Simulates an AI player's turn in the UNO game by determining and playing a valid card from the player's hand.
     *
     * @param model The UNOModel representing the current state of the UNO game.
     * @param mode The game mode (LIGHT or DARK) in which the AI player is playing.
     */

    public void playAI(UNOModel model,UNOModel.mode mode) {
        ArrayList<Card> playableCards = isCardPlayable(model.getCurrentPlayer().getCards(),mode,model);

        if (!playableCards.isEmpty()) {
            // Play the first valid card
            Card cardToPlay = playableCards.get(0);
            System.out.println("Playable cards are: ");
            for (Card card : playableCards) {
                if (mode == UNOModel.mode.LIGHT) {

                    System.out.println(card.getLightCharacteristics());
                }
                if (mode == UNOModel.mode.DARK) {
                    System.out.println(card.getDarkCharacteristics());
                }
            }
            if(mode== UNOModel.mode.LIGHT){
                if(cardToPlay.getLightCharacteristics().split(" ")[0].equals("WILD")){
                    Colors.LIGHTCOLORS chosenColor = getRandomLightColor();
                    System.out.println(model.getCurrentPlayer().getName() + " wants "+ chosenColor);
                    cardToPlay.setLightColor(chosenColor);

                }


            }
            if(mode== UNOModel.mode.DARK){
                if(cardToPlay.getDarkCharacteristics().split(" ")[0].equals("WILD")){
                    Colors.DARKCOLORS chosenColor = getRandomDarkColor();
                    System.out.println(model.getCurrentPlayer().getName() + " wants "+ chosenColor);
                    cardToPlay.setDarkColor(chosenColor);

                }
            }

            // Find the index of the card to play in the AI player's hand
            //Looks for the card played in the players hands and gets the index.
            int index = model.getCurrentPlayer().getCards().indexOf(cardToPlay);


            if (index != -1) {
                // Remove the played card from the AI player's hand
                model.getCurrentPlayer().getCards().remove(index);
                if(mode== UNOModel.mode.LIGHT){
                    System.out.println(model.getCurrentPlayer().getName()+" Played " + cardToPlay.getLightCharacteristics());
                    if(cardToPlay.getType() == Card.type.REGULAR){
                        model.updateScore(model.getCurrentPlayer(), Integer.parseInt(cardToPlay.getLightCharacteristics().split(" ")[0]));
                        System.out.print("Current player score: ");
                        System.out.println(model.getScores().get(model.getCurrentPlayer()));
                    }

                }
                if(mode== UNOModel.mode.DARK){
                    System.out.println(model.getCurrentPlayer().getName()+" Played " + cardToPlay.getDarkCharacteristics());
                    if(cardToPlay.getType() == Card.type.REGULAR) {
                        model.updateScore(model.getCurrentPlayer(), Integer.parseInt(cardToPlay.getDarkCharacteristics().split(" ")[0]));
                        System.out.print("Current player score: ");
                        System.out.println(model.getScores().get(model.getCurrentPlayer()));
                    }

                }
                if(cardToPlay.getLightCharacteristics().split(" ")[0].equals("SKIP")){
                    int position = (model.getDirection() ? (model.getPosition() + 1) : (model.getPosition() - 1)) % model.getPlayers().size();
                    model.setPosition(position);
                    System.out.println(model.getPlayers().get((model.getPlayers().indexOf(model.getCurrentPlayer()) + 1) % model.getPlayers().size()).getName() + " has been skipped");


                }
                if(cardToPlay.getLightCharacteristics().split(" ")[0].equals("REVERSE")){
                    boolean clockwise = model.getDirection();
                    model.setDirection(!clockwise);
                    System.out.println("\nUNOModel order has been reversed");

                }
                if(cardToPlay.getLightCharacteristics().split(" ")[0].equals("WILD_DRAW_TWO")){
                    for (int i = 0; i < 2; i++) {
                        model.getPlayers().get(model.getPosition() + 1 % (model.getPlayers().size() - 1)).addCard(model.getCardDeck().get(model.getCardDeck().size() - 1));
                    }
                    int position = (model.getDirection() ? (model.getPosition() + 1) : (model.getPosition() - 1)) % model.getPlayers().size();
                    model.setPosition(position);
                    System.out.println(model.getPlayers().get((model.getPlayers().indexOf(model.getCurrentPlayer()) + 1)).getName() + " picked 2 and will be skipped");


                }

                // Add the card to the playing deck or perform other necessary actions
                model.getPlayingDeck().add(cardToPlay);


            } else {
                // Handle the case where the card is not found in the AI player's hand
                System.out.println("Error: Played card not found in AI player's hand");
            }
        } else {
            // If no valid cards are found, draw from the bank
            model.drawFromBank();

        }
    }

    /**
     * A method to give a list of cards that are playable by the AI player.
     * @param cards
     * @param mode
     * @param model
     * @return ArrayList<Card>
     */

    public ArrayList<Card>  isCardPlayable( ArrayList<Card> cards,UNOModel.mode mode, UNOModel model) {

        ArrayList<Card> playableCards = new ArrayList<>();
        String topCardcharacter= model.getPlayingDeck().get(model.getPlayingDeck().size() - 1).getLightCharacteristics().split(" ")[0];
        String topCardcolor= model.getPlayingDeck().get(model.getPlayingDeck().size() - 1).getLightCharacteristics().split(" ")[1];

        String topCardDarkcharacter= model.getPlayingDeck().get(model.getPlayingDeck().size() - 1).getDarkCharacteristics().split(" ")[0];
        String topCardDarkcolor= model.getPlayingDeck().get(model.getPlayingDeck().size() - 1).getDarkCharacteristics().split(" ")[1];


        for (Card card : cards) {
            if (mode == UNOModel.mode.LIGHT) {
                if (card.getLightCharacteristics().split(" ")[0].equals("WILD")) {
                    // Wild card is always playable
                    playableCards.add(card);
                }  if (card.getLightCharacteristics().split(" ")[0].equals(topCardcharacter) ||
                        card.getLightCharacteristics().split(" ")[1].equals(topCardcolor)) {
                    // Regular card matches either character or color
                    playableCards.add(card);
                }
            } else if (mode == UNOModel.mode.DARK) {
                if (card.getDarkCharacteristics().split(" ")[0].equals("WILD")) {
                    // Wild card is always playable
                    playableCards.add(card);
                } if (card.getDarkCharacteristics().split(" ")[0].equals(topCardDarkcharacter) ||
                        card.getDarkCharacteristics().split(" ")[1].equals(topCardDarkcolor)) {
                    // Regular card matches either character or color
                    playableCards.add(card);
                }
            }
        }
        return playableCards;

    }


    /**
     * A method which randomly selects between valid colours, which colour to ask for.
     * This is for the light mode.
     * @return Colors.LIGHTCOLORS
     */

    public Colors.LIGHTCOLORS getRandomLightColor() {
        Colors.LIGHTCOLORS[] lightColors = Colors.LIGHTCOLORS.values();
        Random random = new Random();
        int randomIndex = random.nextInt(lightColors.length-1);
        return lightColors[randomIndex];
    }
    /**
     * A method which randomly selects between valid colours, which colour to ask for.
     * This is for the dark mode.
     * @return Colors.DARKCOLORS
     */
    public Colors.DARKCOLORS getRandomDarkColor() {
        Colors.DARKCOLORS[] darkColors = Colors.DARKCOLORS.values();
        Random random = new Random();
        int randomIndex = random.nextInt(darkColors.length-1);
        return darkColors[randomIndex];
    }



}

