/**
 * The Number Card class.
 * Implements the Card interface.
 *
 * @author Oyindamola Taiwo-Olupeka
 * @author Osas Iyamu
 */
public class NumberCard implements Card {
    private final Card.type cardType;
    private final int lightNumber;
    private final Colors.LIGHTCOLORS lightColor;
    private final int darkNumber;
    private final Colors.DARKCOLORS darkColor;

    /**
     * Constructor of the card class
     * @param lightNumber the light number
     * @param lightColor the light color
     * @param darkNumber the dark number
     * @param darkColor the dark color
     */
    public NumberCard(Card.type cardType, int lightNumber, Colors.LIGHTCOLORS lightColor, int darkNumber, Colors.DARKCOLORS darkColor){
        this.cardType = cardType;
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
        return this.lightNumber + " " + this.lightColor.toString();
    }

    /**
     * Get the dark number and color of the card
     * @return the dark characteristics
     */
    public String getDarkCharacteristics(){
        return this.darkNumber + " " + this.darkColor.toString();
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

    /**
     * Get the type of the card
     * @return the type of the card
     */
    @Override
    public type getType() {
        return cardType;
    }

    /**
     * Set the light color for the card
     * @param lightcolors
     */
    @Override
    public void setLightColor(Colors.LIGHTCOLORS lightcolors) {
        System.out.println("You are not allowed to set the light color for a number card");
    }

    /**
     * Set the dark color of the card
     * @param darkcolors
     */
    @Override
    public void setDarkColor(Colors.DARKCOLORS darkcolors) {
        System.out.println("You are not allowed to set the dark color for a number card");
    }

}
