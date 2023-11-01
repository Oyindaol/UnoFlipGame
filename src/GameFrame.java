import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Random;

public class GameFrame extends JFrame {

    private final GameModel model;
    public GameFrame(GameModel model) {
        super("UNO Flip");
        this.model = model;
        init();
//        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void init(){
        this.setLayout(new BorderLayout());
        this.setSize(500, 500);
        JPanel northPanel = new JPanel(new FlowLayout());
        JPanel centerPanel = new JPanel(new FlowLayout());
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.PAGE_AXIS));
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.PAGE_AXIS));
        JPanel southPanel = new JPanel(new FlowLayout());
        JButton nextButton = new JButton("Next");
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            int lightInt = rand.nextInt(9) + 1;
            int lightColor = rand.nextInt(4);
            int darkInt = rand.nextInt(9) + 1;
            int darkColour = rand.nextInt(4);
            Card card = new NumberCard(Card.type.REGULAR, lightInt, Colors.LIGHTCOLORS.values()[lightColor], darkInt, Colors.DARKCOLORS.values()[darkColour]);
            southPanel.add(createCard(card)[0], BorderLayout.SOUTH);
        }
        southPanel.add(nextButton);
        this.add(northPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(westPanel, BorderLayout.WEST);
        this.add(southPanel, BorderLayout.SOUTH);

        Border blackLine = BorderFactory.createLineBorder(Color.black);

        //Items that go in the North Panel
        JLabel UNOFLIPLabel = new JLabel("UNO FLIP");
        northPanel.add(UNOFLIPLabel);

        //Items that go in the Center Panel
        JTextField Game = new JTextField("This is where the Game will be played");
        Game.setBorder(blackLine);
        Game.setSize(1000,1000);
        centerPanel.add(Game);
        centerPanel.setBackground(Color.RED);
        centerPanel.setBorder(blackLine);



        //Items that go in the South Panel
//        southPanel.setBorder(blackLine);

        //Items that go in the East Panel
        JTextField textFieldEast = new JTextField("Temp for List of players");
        eastPanel.add(textFieldEast);

        //Items that go in the East Panel
        JTextField textFieldWest = new JTextField("Temp for Current Player Info");
        westPanel.add(textFieldWest);
    }

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

    private JPanel makeCardSide(String mode, Card card){
        JPanel panel = new JPanel(new BorderLayout());
        JButton button = new JButton();
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
                center.setText(cardLightCharacteristics);

                button.setText(cardLightCharacteristics);
                Color color = getColorEquivalence(cardLightColor);
                panel.setBackground(color);
                panel.add(topRight, BorderLayout.NORTH);
                panel.add(button, BorderLayout.EAST);
//                panel.add(button, BorderLayout.WEST);
//                panel.add(center, BorderLayout.CENTER);
            } else if (card.getType().equals(Card.type.SPECIAL)) {
                if (cardLightCharacteristics.equals("WILD")) {
                    //if card characteristics is 'WILD'

                } else {
                    //other card types
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

                } else {
                    //other card types
                }
            }
        }
        return panel;
    }

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

    public static void main(String[] args) {
        GameFrame frame = new GameFrame(new GameModel());
    }
}
