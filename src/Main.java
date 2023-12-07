import java.io.IOException;

/**
 * Main Method.
 * Run to play the game.
 */
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        UNOModel model = new UNOModel();
        new UNOFrame(model);

    }


}
