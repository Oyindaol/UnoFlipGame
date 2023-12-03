import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
                model.validatePlacement(characteristics, color);
            }
            else {
                model.validatePlacement("WILD", "unassigned");
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
        else if (e.getActionCommand().equals("Save")) {
            String fileNameToSave = JOptionPane.showInputDialog("Enter file name to save to");
            try {
                model.save(fileNameToSave);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
