import java.io.Serializable;

/**
 * UNOEvent class.
 */
public class UNOEvent implements Serializable {
    public boolean valid;
    public UNOModel model;

    /**
     * UNOEvent Constructor.
     * @param valid
     * @param model
     */
    public UNOEvent(boolean valid, UNOModel model) {
        this.valid = valid;
        this.model = model;
    }

    /**
     * Getter method for the model instance.
     * @return this.model
     */
    public UNOModel getModel(){
        return this.model;
    }

    /**
     * isValid Method to check if placement is valid.
     * @return true if valid
     */
    public boolean isValid() {
        return valid;
    }
}
