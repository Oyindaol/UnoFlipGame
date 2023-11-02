import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameFrame extends JFrame {

    private final GameModel model;
    public GameFrame(GameModel model) {
        super("UNO Flip");
        this.setLayout(new BorderLayout(10, 10));
        this.model = model;
        init();
        this.setMinimumSize(new Dimension(1000, 700));
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void init(){
        JPanel northPanel = new JPanel(new FlowLayout());
        JPanel centerPanel = new JPanel(new GridBagLayout());
        JPanel eastPanel = new JPanel(new GridBagLayout());
//        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.X_AXIS));
        eastPanel.setMaximumSize(new Dimension(30,30));
        JPanel westPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
//        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.PAGE_AXIS));
        JPanel southPanel = new JPanel(new FlowLayout());
        JButton nextButton = new JButton("Next");
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            int lightInt = rand.nextInt(9) + 1;
            int lightColor = rand.nextInt(4);
            int darkInt = rand.nextInt(9) + 1;
            int darkColour = rand.nextInt(4);
            Card card = new NumberCard(Card.type.REGULAR, lightInt, Colors.LIGHTCOLORS.values()[lightColor], darkInt, Colors.DARKCOLORS.values()[darkColour]);
            southPanel.add(createCard(card)[0], BorderLayout.SOUTH);
        }
        this.pack();
        southPanel.add(nextButton, FlowLayout.LEFT);

        southPanel.setBackground(Color.GRAY);

        Border raisedEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

        //Items that go in the North Panel
        JLabel UNOFLIPLabel = new JLabel(new ImageIcon(getClass().getResource("UNO Flip.jpeg")));
        UNOFLIPLabel.setHorizontalAlignment(JLabel.CENTER);
//        northPanel.add(UNOFLIPLabel);

        //Items that go in the Center Panel
        JLabel Game = new JLabel("This is where the Game will be played");
        JButton temp = new JButton("Temporary Label");
        Game.setSize(new Dimension(1000,1000));
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 2;
        c.gridy = 0;
        centerPanel.add(Game, c);
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 2;
        c.gridy = 2;
        centerPanel.add(temp, c);
        centerPanel.setBackground(Color.DARK_GRAY);
        centerPanel.setBorder(raisedEtched);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        centerPanel.setSize(new Dimension(500, 500));



        //Items that go in the South Panel
//        southPanel.setBorder(raisedEtched);

        //Items that go in the East Panel
        JLabel playersInfo = new JLabel("<html>Players Names & Scores: <br/>Osas Iyamu<br/>Oyinda<br/>Vishesh<br/>Jay</html>\"");
        playersInfo.setBorder(raisedEtched);
//        eastPanel.setBorder(raisedEtched);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        eastPanel.add(playersInfo, c);


        //Items that go in the East Panel
        JLabel currentPlayerInfo = new JLabel("<html>Current Player: Osas Iyamu                 <br/>Score: 80</html>");
        currentPlayerInfo.setBorder(raisedEtched);
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        westPanel.add(currentPlayerInfo, c);
//        westPanel.setBorder(raisedEtched);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(westPanel, BorderLayout.WEST);
        this.add(southPanel, BorderLayout.SOUTH);
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
