/**
 * The Card interface.
 *
 * @author Osas Iyamu
 */
public interface Card {

    public enum type {REGULAR, SPECIAL}
    public enum SPECIALCARDS {SKIP, REVERSE, FLIP, SKIP_EVERYONE, WILD, WILD_DRAW_TWO}

    public Card.type getType();

    void setLightColor(Colors.LIGHTCOLORS lightcolors);

    void setDarkColor(Colors.DARKCOLORS darkcolors);

    public String getDarkCharacteristics();

    public String getLightCharacteristics();

}
