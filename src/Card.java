import java.util.Collections;
import java.util.HashMap;

/**
 * The Card class
 */
public class Card {
    private int lightNumber;
    private Colors.LIGHTCOLORS lightColor;
    private int darkNumber;

    private Colors.DARKCOLORS darkColor;

    /**
     * Constructor of the card class
     * @param lightNumber the light number
     * @param lightColor the light color
     * @param darkNumber the dark number
     * @param darkColor the dark color
     */
    public Card(int lightNumber, Colors.LIGHTCOLORS lightColor, int darkNumber, Colors.DARKCOLORS darkColor){
        this.lightNumber = lightNumber;
        this.lightColor = lightColor;
        this.darkNumber = darkNumber;
        this.darkColor = darkColor;
    }

    /**
     * Get the light number of the card
     * @return the light number
     */
    public int getLightNumber() {
        return lightNumber;
    }

    /**
     * Get the light color of the card
     * @return the light color
     */
    public Colors.LIGHTCOLORS getLightColor() {
        return lightColor;
    }

    /**
     * Get the light number and color of the card
     * @return the light characteristics
     */
    public String getLightCharacteristics(){
        if (lightNumber == 0) {
            return "SKIP " + this.lightColor.toString().charAt(0);
        }

        if (lightNumber == 10) {
            return "REVERSE " + this.lightColor.toString().charAt(0);
        }

        if (lightNumber == 11) {
            return "WILD DRAW TWO " + this.lightColor.toString().charAt(0);
        }

        return this.lightNumber + "" + this.lightColor.toString().charAt(0);
    }

    /**
     * Get the dark number and color of the card
     * @return the dark characteristics
     */
    public String getDarkCharacteristics(){
        if(darkNumber == 0) {
            return "SKIP " + this.darkColor.toString().charAt(0);
        }

        if(darkNumber == 10) {
            return "REVERSE " + this.darkColor.toString().charAt(0);
        }

        if (darkNumber == 11) {
            return "WILD DRAW TWO " + this.lightColor.toString().charAt(0);
        }

        return this.darkNumber + "" + this.darkColor.toString().charAt(0);
    }

    /**
     * Get the dark number of the card
     * @return the dark number
     */
    public int getDarkNumber() {
        return darkNumber;
    }

    /**
     * Get the dark color of the card
     * @return the dark color
     */
    public Colors.DARKCOLORS getDarkColor() {
        return darkColor;
    }

    public static void main(String[] args) {
        Card card = new Card(1, Colors.LIGHTCOLORS.GREEN, 2, Colors.DARKCOLORS.PURPLE);
        Card card1 = new Card(3, Colors.LIGHTCOLORS.GREEN, 4, Colors.DARKCOLORS.PURPLE);
        Card card2 = new Card(5, Colors.LIGHTCOLORS.GREEN, 6, Colors.DARKCOLORS.PURPLE);
        Card card3 = new Card(7, Colors.LIGHTCOLORS.GREEN, 8, Colors.DARKCOLORS.PURPLE);

        System.out.println("light: " + card.getLightNumber() + ", " + card.getLightColor() + "\ndark:  " + card.getDarkNumber() + ", " + card.getDarkColor());
        System.out.println("light: " + card1.getLightNumber() + ", " + card1.getLightColor() + "\ndark:  " + card1.getDarkNumber() + ", " + card1.getDarkColor());
        System.out.println("light: " + card2.getLightNumber() + ", " + card2.getLightColor() + "\ndark:  " + card2.getDarkNumber() + ", " + card2.getDarkColor());
        System.out.println("light: " + card3.getLightNumber() + ", " + card3.getLightColor() + "\ndark:  " + card3.getDarkNumber() + ", " + card3.getDarkColor());
    }
}
