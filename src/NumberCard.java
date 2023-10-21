/**
 * The Card class
 */
public class NumberCard implements Card{
    private Card.type cardType;
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
}
