public interface Card {
    public enum type {REGULAR, SPECIAL}
    public enum SPECIALCARDS {DRAW_PLUS_ONE, SKIP, REVERSE, DRAW_PLUS_FIVE, FLIP, SKIP_EVERYONE, WILD, WILD_DRAW_TWO, WILD_DRAW_COLOR}

    public Card.type getType();
    public String getDarkCharacteristics();

    public String getLightCharacteristics();
}
