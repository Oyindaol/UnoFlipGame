import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * UNOController class.
 * Controller part of the implementation of the MVC pattern.
 */
public class UNOController implements ActionListener {
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

        else if (e.getActionCommand().equals("Undo")) {
            model.undo();
        }

        else if (e.getActionCommand().equals("Redo")) {
            model.redo();
        }

        else if (e.getActionCommand().equals("Save This Game")) {
            //model.save("saved_game.txt");

            String saveFileName = JOptionPane.showInputDialog(view, "Enter file name to save:");
            if (saveFileName != null && !saveFileName.trim().isEmpty()) {
                model.save(saveFileName);
            }
        }

        else if (e.getActionCommand().equals("Load Previous Game")) {
//            UNOModel loadedGame = model.load("saved_game.txt");
//            if (loadedGame != null) {
//                model = loadedGame;
//                System.out.println("Game loaded successfully.");
//            }


            String loadFileName = JOptionPane.showInputDialog(view, "Enter file name to load:");
            if (loadFileName != null && !loadFileName.trim().isEmpty()) {
                UNOModel loadedGame = model.load(loadFileName);
                if (loadedGame != null) {
                    model = loadedGame;
                    System.out.println("Game loaded successfully.");
                }
                else {
                    System.out.println("Error loading the game. Could not load saved data.");
                }
            }
            else {
                System.out.println("Invalid file name. Please provide a valid file name.");
            }
        }
    }

}
