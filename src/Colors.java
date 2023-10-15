import java.util.ArrayList;
import java.util.Random;

public class Colors {
    public enum LIGHTCOLORS {BLUE, GREEN, YELLOW, RED}
    public enum DARKCOLORS {PINK, TEAL, PURPLE, ORANGE}

    public enum SPECIALCARDS {DRAW_PLUS_ONE, SKIP, REVERSE, DRAW_PLUS_FIVE, FLIP, SKIP_EVERYONE, WILD, WILD_DRAW_TWO, WILD_DRAW_COLOR}

    public static void main(String[] args) {
        System.out.println(Colors.LIGHTCOLORS.values()[0]);
        ArrayList<Card> playingDeck = new ArrayList<>();
        Random rand = new Random();
        for (int i=0; i<112; i++){
            int lightInt = rand.nextInt(10);
            int lightColor = rand.nextInt(4);
            int darkInt = rand.nextInt(10);
            int darkColour = rand.nextInt(4);

            playingDeck.add(new Card(lightInt, LIGHTCOLORS.values()[lightColor], darkInt, DARKCOLORS.values()[darkColour]));

        }

//        for(Card card: playingDeck){
//            System.out.print("{");
//            System.out.print(card.getLightCharacteristics() + ", ");
//            System.out.print(card.getDarkCharacteristics());
//            System.out.print("} ");
//        }

        Player player = new Player("Osas");
        for (int i=0; i<7; i++){
            int lightInt = rand.nextInt(10);
            int lightColor = rand.nextInt(4);
            int darkInt = rand.nextInt(10);
            int darkColour = rand.nextInt(4);
            player.addCard(new Card(lightInt, LIGHTCOLORS.values()[lightColor], darkInt, DARKCOLORS.values()[darkColour]));
        }
        ArrayList<Card> playedCards = new ArrayList<>();
        System.out.println(playingDeck.get(playingDeck.size()-1).getLightCharacteristics());
        System.out.println(player.showCards());

    }
}
