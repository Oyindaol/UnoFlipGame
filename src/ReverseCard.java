public class ReverseCard  extends Card{

    private boolean isReverse;

    /**
     * Constructor of the card class
     *
     * @param lightColor  the light color
     * @param darkColor   the dark color
     */
    public ReverseCard(Colors.LIGHTCOLORS lightColor, Colors.DARKCOLORS darkColor) {
        super(10, lightColor, 10, darkColor);
        isReverse = true;

    }

    public boolean isReverse(){
        return isReverse;
    }
}
