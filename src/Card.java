import java.util.HashMap;

public class Card {
    public enum LIGHTCOLORS {BLUE, GREEN, YELLOW, RED}
    public enum DARKCOLORS {PINK, TEAL, PURPLE, ORANGE}

    public enum SPECIALCARDS {DRAW_PLUS_ONE, SKIP, REVERSE, DRAW_PLUS_FIVE, FLIP, SKIP_EVERYONE, WILD, WILD_DRAW_TWO, WILD_DRAW_COLOR}

    private HashMap<Integer, LIGHTCOLORS> lightSide;
    private HashMap<Integer, DARKCOLORS> darkSide;

    public Card(HashMap<Integer,LIGHTCOLORS> light, HashMap<Integer,DARKCOLORS> dark) {
        this.lightSide = light;
        this.darkSide = dark;
    }

    public HashMap<Integer, LIGHTCOLORS> getLightSide() {
        return lightSide;
    }

    public HashMap<Integer, DARKCOLORS> getDarkSide() {
        return darkSide;
    }
}
