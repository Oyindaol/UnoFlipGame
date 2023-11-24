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
    private JButton AI_Button;

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

        String AI = JOptionPane.showInputDialog("Do you wish to add an AI player? y/n");

        if(AI.toLowerCase().equals("y")){
            model.addPlayer(new AI());
        }

        model.init();
        model.setCurrentPlayer(model.getPlayers().get(0));
        updateCurrentPlayerInfo(model.getCurrentPlayer());

        this.updateCurrentPlayerCards(model.getCurrentPlayer(), model);
        southPanel.add(cardsScrollPane);
        this.pack();

        nextButton = new JButton("Next Player");
        nextButton.addActionListener(controller);
        nextButton.setEnabled(false);
        southPanel.add(nextButton, FlowLayout.LEFT);

        AI_Button = new JButton("Play AI");
        AI_Button.addActionListener(controller);
        AI_Button.setVisible(false);
        southPanel.add(AI_Button);

        southPanel.setBackground(Color.GRAY);

        Border raisedEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

        UNOButton = new JButton("UNO");
        UNOButton.addActionListener(controller);
        UNOButton.setBackground(Color.GRAY);
        UNOButton.setEnabled(false); // Disable the button initially

        //North Panel
        JLabel UNOFLIPLabel = new JLabel(new ImageIcon(getClass().getResource("UNO Flip Logo.png")));
        UNOFLIPLabel.setMaximumSize(new Dimension(5000, 100));
        UNOFLIPLabel.setMinimumSize(new Dimension(5000, 100));
        UNOFLIPLabel.setPreferredSize(new Dimension(5000, 100));
        UNOFLIPLabel.setHorizontalAlignment(JLabel.CENTER);
        northPanel.add(UNOFLIPLabel);
        northPanel.setBackground(Color.lightGray);

        //South Panel
        southPanel.add(UNOButton);

        //Center Panel
        updateTopCard(model);
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setBorder(raisedEtched);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        centerPanel.setSize(new Dimension(500, 500));

        //East Panel
        printAllPlayersInfo(model.getPlayers(), model);
        eastPanel.setBorder(raisedEtched);
        eastPanel.setBackground(Color.lightGray);

        //West Panel
        currentPlayerInfo.setBackground(Color.WHITE);
        currentPlayerInfo.setBorder(raisedEtched);
        JPanel cardPickedFromMarket = new JPanel();
        cardPickedFromMarket.setLayout(new BoxLayout(cardPickedFromMarket, BoxLayout.PAGE_AXIS));
        westPanel.setBackground(Color.lightGray);

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
        JPanel topCard;
        if(model.getCurrentMode().equals(UNOModel.mode.LIGHT)) {
            topCard = createCard(model.getTopCard())[1];
        }else{
            topCard = createCard(model.getTopCard())[0];
        }
        centerPanel.add(topCard);
    }

    /**
     * Method to update the current player's info.
     * @param player
     */
    private void updateCurrentPlayerInfo(Player player) {
        currentPlayerInfo.setText("<html>Current Mode: " + model.getCurrentMode()+ "<br/><hr><br/>Current Player: " + player.getName() + "<br/>Score: " +
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
    public void updateCurrentPlayerCards(Player currentPlayer, UNOModel unoModel){
        cardsPanel.removeAll();
        if (unoModel.getCurrentMode().equals(UNOModel.mode.LIGHT)) {
            for (Card card : currentPlayer.getCards()) {
                cardsPanel.add(createCard(card)[1]);
            }
        }else{
            for (Card card : currentPlayer.getCards()) {
                cardsPanel.add(createCard(card)[0]);
            }
        }
    }

    @Override
    public void handleWildCard(UNOModel unoModel){
        String wildColor = "";
        if (unoModel.getCurrentMode().equals(UNOModel.mode.LIGHT)) {
            wildColor = JOptionPane.showInputDialog("Choose a color (RED, GREEN, BLUE, YELLOW): ").toUpperCase();
            while (!Arrays.toString(Colors.LIGHTCOLORS.values()).contains(wildColor)) {
                wildColor = JOptionPane.showInputDialog("Color must be one of these (RED, GREEN, BLUE, YELLOW): ").toUpperCase();
            }
            unoModel.getTopCard().setLightColor(Colors.LIGHTCOLORS.valueOf(wildColor));
        }else if (unoModel.getCurrentMode().equals(UNOModel.mode.DARK)) {
            wildColor = JOptionPane.showInputDialog("Choose a color (PINK, TEAL, PURPLE, ORANGE): ").toUpperCase();
            while (!Arrays.toString(Colors.DARKCOLORS.values()).contains(wildColor)) {
                wildColor = JOptionPane.showInputDialog("Color must be one of these (PINK, TEAL, PURPLE, ORANGE): ").toUpperCase();
            }
            unoModel.getTopCard().setDarkColor(Colors.DARKCOLORS.valueOf(wildColor));
        }
        centerPanel.updateUI();
        updateCurrentPlayerCards(unoModel.getCurrentPlayer(), unoModel);
        updateCurrentPlayerInfo(unoModel.getCurrentPlayer());
        updateTopCard(unoModel);
        for(Component component : cardsPanel.getComponents()){
            JPanel panel = (JPanel) component;
            panel.getComponents()[1].setEnabled(false);
        }
        if(!unoModel.isWinner()) {
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
    public void handleAITurn(UNOEvent e) {
        updateCurrentPlayerCards(e.getModel().getCurrentPlayer(), e.getModel());
        updateCurrentPlayerInfo(e.getModel().getCurrentPlayer());
        updateTopCard(e.getModel());
        this.AI_Button.setEnabled(false);
        for (Component component : cardsPanel.getComponents()){
            JPanel panel = (JPanel) component;
            panel.getComponents()[1].setEnabled(false);
        }
        southPanel.updateUI();
        nextButton.setEnabled(true);
        drawButton.setEnabled(false);
        westPanel.updateUI();
        southPanel.updateUI();

        if (e.getModel().isWinner()){
            JLabel winner = new JLabel(e.getModel().getCurrentPlayer().getName() + " has won the game! Reload game to play again");
            eastPanel.add(winner);
            eastPanel.updateUI();
            nextButton.setEnabled(false);
            drawButton.setEnabled(false);
            southPanel.updateUI();
        }
    }

    @Override
    public void handleNextPlayer(UNOModel unoModel) {
        if(unoModel.getCurrentPlayer() instanceof AI){
            updateCurrentPlayerCards(unoModel.getCurrentPlayer(), unoModel);
            updateCurrentPlayerInfo(unoModel.getCurrentPlayer());
            printAllPlayersInfo(unoModel.getPlayers(), unoModel);
            for (Component component : cardsPanel.getComponents()){
                JPanel panel = (JPanel) component;
                panel.getComponents()[1].setEnabled(false);
            }
            AI_Button.setVisible(true);
            AI_Button.setEnabled(true);
            nextButton.setEnabled(false);
            drawButton.setEnabled(false);
            southPanel.updateUI();
            westPanel.updateUI();
        }else{
            AI_Button.setVisible(false);
            updateCurrentPlayerCards(unoModel.getCurrentPlayer(), unoModel);
            updateCurrentPlayerInfo(unoModel.getCurrentPlayer());
            printAllPlayersInfo(unoModel.getPlayers(), unoModel);
            nextButton.setEnabled(false);
            drawButton.setEnabled(true);
            westPanel.updateUI();
            eastPanel.updateUI();
            southPanel.updateUI();
        }
        if (unoModel.isWinner()){
            JLabel winner = new JLabel(unoModel.getCurrentPlayer().getName() + " has won the game! Reload game to play again");
            eastPanel.add(winner);
            eastPanel.updateUI();
            nextButton.setEnabled(false);
            drawButton.setEnabled(false);
            southPanel.updateUI();
        }
    }

    @Override
    public void handleDrawCard(UNOModel unoModel) {
        updateCurrentPlayerCards(unoModel.getCurrentPlayer(), unoModel);
        for (Component component : cardsPanel.getComponents()){
            component.getComponentAt(new Point(0,0)).setEnabled(false);

        }
        nextButton.setEnabled(true);
        drawButton.setEnabled(false);
        southPanel.updateUI();
    }

    @Override
    public void handlePlacement(UNOEvent e) {
        UNOButton.setEnabled(false);
        southPanel.updateUI();
        if (!e.isValid()){
            JLabel errorPanel = new JLabel("Invalid move, pick another card");
            eastPanel.add(errorPanel, BorderLayout.CENTER);
            eastPanel.updateUI();
        }
        else {
            updateCurrentPlayerCards(e.getModel().getCurrentPlayer(), e.getModel());
            updateCurrentPlayerInfo(e.getModel().getCurrentPlayer());
            updateTopCard(e.getModel());
            for (Component component : cardsPanel.getComponents()){
                JPanel panel = (JPanel) component;
                panel.getComponents()[1].setEnabled(false);
            }
            System.out.println(e.getModel().getTopCard().getLightCharacteristics());
            southPanel.updateUI();
            nextButton.setEnabled(true);
            drawButton.setEnabled(false);

            // A condition to enable the UNO button when the player has only one card left
            if (e.getModel().getCurrentPlayer().getCards().size() == 1) {
                UNOButton.setEnabled(true);
                UNOButton.setBackground(Color.GREEN); // Change the button color to green when enabled
            }

            if (e.getModel().isWinner()){
                JLabel winner = new JLabel(e.getModel().getCurrentPlayer().getName() + " has won the game! Reload game to play again");
                eastPanel.add(winner);
                eastPanel.updateUI();
                nextButton.setEnabled(false);
                drawButton.setEnabled(false);
                southPanel.updateUI();
            }
        }
    }

    public void handleUNO(){
        JLabel errorPanel = new JLabel(model.getCurrentPlayer().getName() + " called UNO!");
        eastPanel.add(errorPanel, BorderLayout.CENTER);
        eastPanel.updateUI();
    }
}
