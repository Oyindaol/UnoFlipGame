import java.util.EventObject;

public class UNOEvent {
    public boolean valid;
    public UNOModel model;

    public UNOEvent(boolean valid, UNOModel model) {
        this.valid = valid;
        this.model = model;
    }

    public UNOModel getModel(){
        return this.model;
    }
}
