public class SkipCard extends Card {

    private boolean isSkip;

    /**
     * Constructor of the card class
     *
     * @param lightColor  the light color
     * @param darkColor   the dark color
     */
    public SkipCard(Colors.LIGHTCOLORS lightColor, Colors.DARKCOLORS darkColor) {
        super(0, lightColor, 0, darkColor);
    }

    public boolean isSkip() {
        return isSkip;
    }
}

