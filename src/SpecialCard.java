/**
 * The Special Card class
 */
public class SpecialCard implements Card {

    private Card.type cardType;
    private SPECIALCARDS specialDark;
    private SPECIALCARDS specialLight;

    private Colors.LIGHTCOLORS lightColor;
    private Colors.DARKCOLORS darkColor;

    /**
     * Constructor for the Special Card class
     * @param cardType the card type
     * @param specialLight the special card for light mode
     * @param lightColor the color for the light mode
     * @param specialDark the special card for the dark mode
     * @param darkColor the color for the dark mode
     */
    public SpecialCard(type cardType, SPECIALCARDS specialLight, Colors.LIGHTCOLORS lightColor, SPECIALCARDS specialDark, Colors.DARKCOLORS darkColor) {
        this.cardType = cardType;
        this.specialLight = specialLight;
        this.specialDark = specialDark;
        this.lightColor = lightColor;
        this.darkColor = darkColor;
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
     * Get the dark characteristics of the card
     * @return the dark characteristics
     */
    @Override
    public String getDarkCharacteristics() {
        return specialDark.toString() + " " + darkColor;
    }

    /**
     * Get the light characteristics of the card
     * @return the light characteristics
     */
    @Override
    public String getLightCharacteristics() {
        return specialLight.toString() + " " + lightColor;
    }
}
