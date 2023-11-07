import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UNOController implements ActionListener {
    private UNOView view;
    private UNOModel model;

    public UNOController(UNOModel model, UNOView view) {
        this.model = model;
        this.view = view;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Play")){
            view.handlePlay();
        }

        else if(e.getActionCommand().equals("Next Player")){
            model.changeTurn();
        }

        else if(e.getActionCommand().equals("Draw Card")){
            model.drawCard();
        }

        else if(e.getActionCommand().equals("card")){
            JButton button = (JButton) e.getSource();
        }

        else if(e.getActionCommand().equals("Draw From Market")){
            JButton button = (JButton) e.getSource();
        }
    }
}
