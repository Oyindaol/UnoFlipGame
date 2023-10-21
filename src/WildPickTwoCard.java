public class WildPickTwoCard extends Card{

    private boolean isWildPickTwo;

    /**
     * Constructor of the card class
     *
     * @param lightColor  the light color
     * @param darkColor   the dark color
     */
    public WildPickTwoCard(Colors.LIGHTCOLORS lightColor, Colors.DARKCOLORS darkColor) {
        super(11, lightColor, 11, darkColor);
        isWildPickTwo = true;
    }

    public boolean isWildPickTwo(){
        return isWildPickTwo;
    }
}
