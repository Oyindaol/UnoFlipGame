import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * UNOController class.
 * Controller part of the implementation of the MVC pattern.
 */
public class UNOController implements ActionListener, Serializable {
    private UNOFrame view;
    private UNOModel model;

    /**
     * UNOController Constructor.
     * @param model
     * @param view
     */
    public UNOController(UNOModel model, UNOFrame view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Action Listeners for the button in the GUI.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Next Player")) {
            model.changeTurn();
            model.resetUndoRedo();
        }

        else if (e.getActionCommand().equals("Draw Card")) {
            model.drawCard();
        }

        else if (e.getActionCommand().equals("card")) {
            JButton button = (JButton) e.getSource();
            String characteristics = button.getText().split(" ")[0];
            if (!characteristics.equals("WILD")) {
                String color = button.getText().split(" ")[1];
                System.out.println(characteristics + " " + color);
                try {
                    model.validatePlacement(characteristics, color);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else {
                try {
                    model.validatePlacement("WILD", "unassigned");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        else if (e.getActionCommand().equals("Draw From Bank")) {
            model.drawFromBank();
        }

        else if (e.getActionCommand().equals("UNO")) {
            this.view.handleUNO();

        }
        else if (e.getActionCommand().equals("Play AI")) {
            model.implementAITurn();
        }
        else if (e.getActionCommand().equals("Undo")) {
            model.implementUndo();
        }
        else if (e.getActionCommand().equals("Redo")){
            model.implementRedo();
        }
        else if (e.getActionCommand().equals("Restart")) {
            model.restartGame();
            JOptionPane.showMessageDialog(view,
                    "The game has been restarted",
                    "Restart successful",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }
}
