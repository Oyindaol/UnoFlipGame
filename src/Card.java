import java.util.HashMap;

public class Card {
    private int lightNumber;
    private Colors.LIGHTCOLORS lightColor;

    private int darkNumber;

    private Colors.DARKCOLORS darkColor;

    public Card(int lightNumber, Colors.LIGHTCOLORS lightColor, int darkNumber, Colors.DARKCOLORS darkColor){
        this.lightNumber = lightNumber;
        this.lightColor = lightColor;
        this.darkNumber = darkNumber;
        this.darkColor = darkColor;
    }

    public int getLightNumber() {
        return lightNumber;
    }

    public Colors.LIGHTCOLORS getLightColor() {
        return lightColor;
    }

    public String getLightCharacteristics(){
        return this.lightNumber + "" + this.lightColor.toString().charAt(0);
    }

    public String getDarkCharacteristics(){
        return this.darkNumber + "" + this.darkColor.toString().charAt(0);
    }

    public int getDarkNumber() {
        return darkNumber;
    }

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
