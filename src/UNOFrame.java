import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class UNOFrame extends JFrame implements UNOView {

    private UNOModel model;
    private UNOController controller;
    private JButton nextButton;

    JPanel northPanel;
    JPanel centerPanel;
    JPanel eastPanel;
    JPanel westPanel;
    JPanel southPanel;
    JPanel cardsPanel;
    JScrollPane cardsScrollPane;
    JLabel currentPlayerInfo;
    JButton drawButton;

    public UNOFrame(UNOModel model) {
        super("UNO Flip");
        this.setLayout(new BorderLayout(5, 2));
        this.model = model;
        this.northPanel = new JPanel(new FlowLayout());
        centerPanel = new JPanel(new GridBagLayout());
        eastPanel = new JPanel(new BorderLayout());
        eastPanel.setMaximumSize(new Dimension(30,30));
        westPanel = new JPanel(new BorderLayout());
        GridBagConstraints c = new GridBagConstraints();
        southPanel = new JPanel(new GridBagLayout());

        currentPlayerInfo = new JLabel("Current Player: " + model.getCurrentPlayer() + "<html><br/>Score: </html>");


        cardsPanel = new JPanel(new GridLayout(2, 0));
        cardsScrollPane = new JScrollPane(cardsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        model.addUNOView(this);
        controller = new UNOController(model, this);


        nextButton = new JButton("Next Player");
        nextButton.addActionListener(controller);

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
        int numPlayers = Integer.parseInt(JOptionPane.showInputDialog("Enter number of Players"));
        int count = 1;
        for(int i=0; i<numPlayers; i++){
            String player = JOptionPane.showInputDialog("Enter Player " + count + "'s name");
            model.addPlayer(new Player(player));
            count++;
        }

        model.init();
        model.setCurrentPlayer(model.getPlayers().get(0));

        for(Card card : model.getCurrentPlayer().getCards()){
            cardsPanel.add(createCard(card)[1]);
        }
        southPanel.add(cardsScrollPane);
        this.pack();
        southPanel.add(nextButton, FlowLayout.LEFT);

        southPanel.setBackground(Color.GRAY);

        Border raisedEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

        //Items that go in the North Panel
        JLabel UNOFLIPLabel = new JLabel(new ImageIcon(getClass().getResource("UNO Flip Logo.png")));
        UNOFLIPLabel.setMaximumSize(new Dimension(5000, 100));
        UNOFLIPLabel.setMinimumSize(new Dimension(5000, 100));
        UNOFLIPLabel.setPreferredSize(new Dimension(5000, 100));
        UNOFLIPLabel.setHorizontalAlignment(JLabel.CENTER);
        northPanel.add(UNOFLIPLabel);

        //Items that go in the Center Panel
        JLabel Game = new JLabel("This is where the Game will be played");
        JPanel temp = createCard(new NumberCard(Card.type.REGULAR, 3, Colors.LIGHTCOLORS.BLUE, 6, Colors.DARKCOLORS.PURPLE))[0];
        temp.setMinimumSize(new Dimension(100, 100));
        temp.setMaximumSize(new Dimension(100, 100));
        temp.setPreferredSize(new Dimension(100, 100));
        Game.setSize(new Dimension(1000,1000));
        centerPanel.add(Game);
        centerPanel.add(temp);
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setBorder(raisedEtched);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        centerPanel.setSize(new Dimension(500, 500));

        //Items that go in the South Panel
//        southPanel.setBorder(raisedEtched);

        //Items that go in the East Panel

        String players = "<html>";
        for (Player player : model.getPlayers()){
            players += player.getName() + " <br/>";
        }
        players = players + "</html>";


        JLabel playersInfo = new JLabel("Players Names & Scores: " + players);
        playersInfo.setBorder(raisedEtched);
        eastPanel.add(playersInfo, BorderLayout.NORTH);


        //Items that go in the west Panel
        currentPlayerInfo.setBorder(raisedEtched);
        JPanel cardPickedFromMarket = new JPanel();
        cardPickedFromMarket.setLayout(new BoxLayout(cardPickedFromMarket, BoxLayout.PAGE_AXIS));

        drawButton = new JButton("Draw From Market");
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

                button.setText(cardLightCharacteristics);
                Color color = getColorEquivalence(cardLightColor);
                panel.setBackground(color);
                panel.add(topRight, BorderLayout.NORTH);
                panel.add(button, BorderLayout.EAST);
            } else if (card.getType().equals(Card.type.SPECIAL)) {
                if (cardLightCharacteristics.equals("WILD")) {
                    button.setText(cardLightCharacteristics);
                    panel.add(topRight, BorderLayout.NORTH);
                    panel.add(button, BorderLayout.EAST);

                } else {
                    //other card types
                    button.setText(cardLightCharacteristics);
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

                button.setText(cardDarkCharacteristics);
                Color color = getColorEquivalence(cardDarkColor);
                panel.setBackground(color);
                panel.add(topRight, BorderLayout.NORTH);
                panel.add(button, BorderLayout.EAST);
//                panel.add(center, BorderLayout.CENTER);
            } else if (card.getType().equals(Card.type.SPECIAL)) {
                if (cardDarkCharacteristics.equals("WILD")) {
                    //if card characteristics is 'WILD'
                    button.setText(cardDarkCharacteristics);
                    panel.add(topRight, BorderLayout.NORTH);
                    panel.add(button, BorderLayout.EAST);
                } else {
                    //other card types
                    button.setText(cardDarkCharacteristics);
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

    @Override
    public void handlePlay() {

    }

    @Override
    public void handleNextPlayer() {

    }

    @Override
    public void handleDrawCard() {

    }

    @Override
    public void handlePlacement(UNOEvent e) {

        nextButton.setVisible(true);
    }

    public static void main(String[] args) {
        UNOModel model = new UNOModel();
        new UNOFrame(model);
    }
}
