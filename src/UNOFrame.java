import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * UNOFrame Class.
 * GUI representation of the UNO flip game.
 */
public class UNOFrame extends JFrame implements UNOView {

    private UNOModel model;
    private UNOController controller;
    private JButton nextButton;
    JButton drawButton;
    private JButton UNOButton;

    JPanel northPanel;
    JPanel centerPanel;
    JPanel eastPanel;
    JPanel westPanel;
    JPanel southPanel;
    JPanel cardsPanel;
    JScrollPane cardsScrollPane;
    JLabel currentPlayerInfo;


    /**
     * UNOFrame Constructor.
     * @param model
     */
    public UNOFrame(UNOModel model) {
        super("UNO Flip");
        this.setLayout(new BorderLayout(5, 2));
        this.model = model;
        this.northPanel = new JPanel(new FlowLayout());
        centerPanel = new JPanel(new GridBagLayout());
        eastPanel = new JPanel(new BorderLayout());

        eastPanel.setMaximumSize(new Dimension(30,30));
        westPanel = new JPanel(new BorderLayout());
        southPanel = new JPanel(new GridBagLayout());


        cardsPanel = new JPanel(new GridLayout(2, 0));
        cardsScrollPane = new JScrollPane(cardsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        model.addUNOView(this);
        controller = new UNOController(model, this);

        currentPlayerInfo = new JLabel();

        init();
        this.setMinimumSize(new Dimension(1250, 700));
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    /**
     * A method to initialize the game frame
     */
    public void init(){
        //For components that will go into the South Panel
        int numPlayers = Integer.parseInt(JOptionPane.showInputDialog("Enter number of Players (2-12)"));
        while(numPlayers < 2 || numPlayers > 12){
            numPlayers = Integer.parseInt(JOptionPane.showInputDialog("Number not in range. Enter number of Players (2-12)"));
        }
        int count = 1;
        for(int i = 0; i < numPlayers; i++){
            String player = JOptionPane.showInputDialog("Enter Player " + count + "'s name");
            model.addPlayer(new Player(player));
            count++;
        }

        model.init();
        model.setCurrentPlayer(model.getPlayers().get(0));
        updateCurrentPlayerInfo(model.getCurrentPlayer());

        //Items that go in the South Panel
//      southPanel.setBorder(raisedEtched);

        this.updateCurrentPlayerCards(model.getCurrentPlayer());
        southPanel.add(cardsScrollPane);
        this.pack();

        nextButton = new JButton("Next Player");
        nextButton.addActionListener(controller);
        nextButton.setEnabled(false);
        southPanel.add(nextButton, FlowLayout.LEFT);

        southPanel.setBackground(Color.GRAY);

        Border raisedEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

        UNOButton = new JButton("UNO");
        UNOButton.addActionListener(controller);
        UNOButton.setBackground(Color.GRAY);
        UNOButton.setEnabled(false); // Disable the button initially

        // Add the UNO button to the South Panel
        southPanel.add(UNOButton);

        //Items that go in the North Panel
        JLabel UNOFLIPLabel = new JLabel(new ImageIcon(getClass().getResource("UNO Flip Logo.png")));
        UNOFLIPLabel.setMaximumSize(new Dimension(5000, 100));
        UNOFLIPLabel.setMinimumSize(new Dimension(5000, 100));
        UNOFLIPLabel.setPreferredSize(new Dimension(5000, 100));
        UNOFLIPLabel.setHorizontalAlignment(JLabel.CENTER);
        northPanel.add(UNOFLIPLabel);

        //Items that go in the Center Panel
        JLabel Game = new JLabel("This is where the Game will be played");
        updateTopCard(model);
        Game.setSize(new Dimension(1000,1000));
        centerPanel.add(Game);
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setBorder(raisedEtched);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        centerPanel.setSize(new Dimension(500, 500));


        //Items that go in the East Panel

        StringBuilder players = new StringBuilder();
        for (Player player : model.getPlayers()){
            players.append(player.getName()).append(" \n");
        }
        System.out.println(players);


        printAllPlayersInfo(model.getPlayers(), model);
        eastPanel.setBorder(raisedEtched);
//        JLabel playersInfo = new JLabel("Players Names & Scores: " + players);
//        playersInfo.setBorder(raisedEtched);
//        eastPanel.add(playersInfo, BorderLayout.NORTH);


        //Items that go in the west Panel
        currentPlayerInfo.setBorder(raisedEtched);
        JPanel cardPickedFromMarket = new JPanel();
        cardPickedFromMarket.setLayout(new BoxLayout(cardPickedFromMarket, BoxLayout.PAGE_AXIS));

        drawButton = new JButton("Draw From Bank");
        drawButton.addActionListener(controller);

        cardPickedFromMarket.add(drawButton);

        westPanel.add(cardPickedFromMarket, BorderLayout.SOUTH);
        westPanel.add(currentPlayerInfo, BorderLayout.NORTH);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(westPanel, BorderLayout.WEST);
        this.add(southPanel, BorderLayout.SOUTH);
    }

    /**
     * A method to create the card
     * @param card, the card to create
     * @return the array of dark side, light side
     */
    private JPanel[] createCard(Card card){
        JPanel[] cardArray = new JPanel[2];
        JPanel lightPanel = makeCardSide("dark", card);
        lightPanel.setSize(500,500);
        JPanel darkPanel = makeCardSide("light", card);
        darkPanel.setSize(50,50);
        cardArray[0] = lightPanel;
        cardArray[1] = darkPanel;
        return cardArray;
    }

    /**
     * A method to create a side of a card (i.e. dark side or light side)
     * @param mode dark mode or light mode?
     * @param card the card to evaluate
     * @return the equivalent card side
     */
    private JPanel makeCardSide(String mode, Card card){
        JPanel panel = new JPanel(new BorderLayout());
        JButton button = new JButton();
        button.setActionCommand("card");
        button.addActionListener(controller);

        Border blackLine = BorderFactory.createLineBorder(Color.black);
        panel.setBorder(blackLine);
        JLabel topRight = new JLabel();
        JLabel center = new JLabel();
        String cardLightCharacteristics = card.getLightCharacteristics().split(" ")[0];;
        String cardLightColor = card.getLightCharacteristics().split(" ")[1];
        String cardDarkCharacteristics = card.getDarkCharacteristics().split(" ")[0];
        String cardDarkColor = card.getDarkCharacteristics().split(" ")[1];

        if (mode.equals("light")) {
            if (card.getType().equals(Card.type.REGULAR)) {
                topRight.setText(cardLightCharacteristics);

                button.setText(cardLightCharacteristics + " " + cardLightColor);
                Color color = getColorEquivalence(cardLightColor);
                panel.setBackground(color);
                panel.add(topRight, BorderLayout.NORTH);
                panel.add(button, BorderLayout.EAST);
            } else if (card.getType().equals(Card.type.SPECIAL)) {
                if (cardLightCharacteristics.equals("WILD")) {
                    button.setText(cardLightCharacteristics);
                    if (!cardLightColor.isEmpty()){
                        Color color = getColorEquivalence(cardLightColor);
                        panel.setBackground(color);
                    }
                    panel.add(topRight, BorderLayout.NORTH);
                    panel.add(button, BorderLayout.EAST);

                } else {
                    //other card types
                    button.setText(cardLightCharacteristics  + " " + cardLightColor);
                    Color color = getColorEquivalence(cardLightColor);
                    panel.setBackground(color);
                    panel.add(topRight, BorderLayout.NORTH);
                    panel.add(button, BorderLayout.EAST);
                }
            }
        } else if (mode.equals("dark")){
            if (card.getType().equals(Card.type.REGULAR)) {
                topRight.setText(cardDarkCharacteristics);
                center.setText(cardDarkCharacteristics);

                button.setText(cardDarkCharacteristics  + " " + cardDarkColor);
                Color color = getColorEquivalence(cardDarkColor);
                panel.setBackground(color);
                panel.add(topRight, BorderLayout.NORTH);
                panel.add(button, BorderLayout.EAST);
//                panel.add(center, BorderLayout.CENTER);
            } else if (card.getType().equals(Card.type.SPECIAL)) {
                if (cardDarkCharacteristics.equals("WILD")) {
                    //if card characteristics is 'WILD'
                    button.setText(cardDarkCharacteristics);
                    if (!cardLightColor.isEmpty()){
                        Color color = getColorEquivalence(cardDarkColor);
                        panel.setBackground(color);
                    }
                    panel.add(topRight, BorderLayout.NORTH);
                    panel.add(button, BorderLayout.EAST);
                } else {
                    //other card types
                    button.setText(cardDarkCharacteristics  + " " + cardDarkColor);
                    Color color = getColorEquivalence(cardDarkColor);
                    panel.setBackground(color);
                    panel.add(topRight, BorderLayout.NORTH);
                    panel.add(button, BorderLayout.EAST);
                }
            }
        }
        return panel;
    }

    /**
     * A method to get the color from the 'Color' class based on the colors from LIGHTCOLORS of DARKCOLORS from the Colors class
     * @param cardColor, the LIGHTCOLOR or DARKCOLOR
     * @return the Color equivalent
     */
    private Color getColorEquivalence(String cardColor) {
        switch(cardColor.toUpperCase()){
            case "BLUE":
                return Color.blue;
            case "GREEN":
                return Color.green;
            case "YELLOW":
                return Color.yellow;
            case "RED":
                return Color.red;
            case "PINK":
                return Color.pink;
            case "TEAL":
                return new Color(0,128,128);
            case "PURPLE":
                return new Color(128, 0, 128);
            case "ORANGE":
                return Color.orange;
            default:
                return Color.white;
        }
    }

    /**
     * Method to update the top card when a new card is played.
     * @param model
     */
    private void updateTopCard(UNOModel model){
        centerPanel.removeAll();
        JPanel topCard = createCard(model.topCard)[1];
        centerPanel.add(topCard);
    }

    /**
     * Method to update the current player's info.
     * @param player
     */
    private void updateCurrentPlayerInfo(Player player) {
        currentPlayerInfo.setText("<html>Current Player: " + player.getName() + "<br/>Score: " +
                model.getScores().get(player) + "</html>");
    }

    /**
     * Method the print all the players' info for the score board.
     * @param players
     * @param model
     */
    private void printAllPlayersInfo(ArrayList<Player> players, UNOModel model){
        eastPanel.removeAll();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("Players and Scores: ");
        jPanel.add(label);
        for (Player p : players){
            JLabel playerInfo = new JLabel(p.getName() + " -> " + model.getScores().get(p));
            jPanel.add(playerInfo);
        }
        eastPanel.add(jPanel, BorderLayout.NORTH);

    }

    /**
     * Method to update the players card deck after specific action(s).
     * @param currentPlayer
     */
    public void updateCurrentPlayerCards(Player currentPlayer){
        cardsPanel.removeAll();
        for(Card card : currentPlayer.getCards()){
            cardsPanel.add(createCard(card)[1]);
        }
    }

    @Override
    public void handleWildCard(UNOModel unoModel){
        String wildColor = JOptionPane.showInputDialog("Choose a color (RED, GREEN, BLUE, YELLOW): ").toUpperCase();
        while (!Arrays.toString(Colors.LIGHTCOLORS.values()).contains(wildColor)){
            wildColor = JOptionPane.showInputDialog("Color must be one of these (RED, GREEN, BLUE, YELLOW): ").toUpperCase();
        }
        unoModel.topCard.setLightColor(Colors.LIGHTCOLORS.valueOf(wildColor));
        centerPanel.updateUI();
        updateCurrentPlayerCards(unoModel.getCurrentPlayer());
        updateCurrentPlayerInfo(unoModel.getCurrentPlayer());
        updateTopCard(unoModel);
        for(Component component : cardsPanel.getComponents()){
            JPanel panel = (JPanel) component;
            panel.getComponents()[1].setEnabled(false);
        }
        if(!unoModel.winner) {
            centerPanel.updateUI();
            southPanel.updateUI();
            nextButton.setEnabled(true);
            drawButton.setEnabled(false);
        }else{
            JLabel winner = new JLabel(unoModel.getCurrentPlayer() + " has won the game! Reload game to play again");
            eastPanel.add(winner);
            eastPanel.updateUI();
        }

    }

    @Override
    public void handleNextPlayer(UNOModel unoModel) {
        updateCurrentPlayerCards(unoModel.getCurrentPlayer());
        updateCurrentPlayerInfo(unoModel.getCurrentPlayer());
        printAllPlayersInfo(unoModel.getPlayers(), unoModel);
        nextButton.setEnabled(false);
        drawButton.setEnabled(true);
        westPanel.updateUI();
        eastPanel.updateUI();
        southPanel.updateUI();
    }

    @Override
    public void handleDrawCard(UNOModel unoModel) {
        updateCurrentPlayerCards(unoModel.getCurrentPlayer());
        for (Component component : cardsPanel.getComponents()){
            component.getComponentAt(new Point(0,0)).setEnabled(false);

        }
        nextButton.setEnabled(true);
        drawButton.setEnabled(false);
        southPanel.updateUI();
    }

    @Override
    public void handlePlacement(UNOEvent e) {
        if (!e.isValid()){
            JLabel errorPanel = new JLabel("Invalid move, pick another card");
            eastPanel.add(errorPanel, BorderLayout.CENTER);
            eastPanel.updateUI();
        }
        else {
            updateCurrentPlayerCards(e.getModel().getCurrentPlayer());
            updateCurrentPlayerInfo(e.getModel().getCurrentPlayer());
            updateTopCard(e.getModel());
            for (Component component : cardsPanel.getComponents()){
                JPanel panel = (JPanel) component;
                panel.getComponents()[1].setEnabled(false);
            }
            System.out.println(e.getModel().topCard.getLightCharacteristics());
            southPanel.updateUI();
            nextButton.setEnabled(true);
            drawButton.setEnabled(false);

            // A condition to enable the UNO button when the player has only one card left
            if (e.getModel().getCurrentPlayer().getCards().size() == 1) {
                UNOButton.setEnabled(true);
                UNOButton.setBackground(Color.GREEN); // Change the button color to green when enabled
            }

            if (e.getModel().winner){
                JLabel winner = new JLabel(e.getModel().getCurrentPlayer().getName() + " has won the game! Reload game to play again");
                eastPanel.add(winner);
                eastPanel.updateUI();
                nextButton.setEnabled(false);
                drawButton.setEnabled(false);
                southPanel.updateUI();
            }
        }
    }

    public static void main(String[] args) {
        UNOModel model = new UNOModel();
        new UNOFrame(model);
    }
}
