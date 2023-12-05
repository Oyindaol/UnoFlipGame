import java.util.ArrayList;
import java.util.HashMap;

public class GameState {

    private UNOModel model;

    // Constructor to capture the game state
    public GameState(UNOModel model) {
        this.model = model;
    }

    public UNOModel getModel() {
        return model;
    }

}
