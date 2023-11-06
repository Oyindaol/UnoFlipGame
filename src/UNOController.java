import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UNOController implements ActionListener {
    private UNOView view;
    private UNOModel model;

    public UNOController(UNOModel model, UNOView view) {
        this.view = view;
        this.model = model;

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "Play"){
            view.handlePlay();

        }

        if(e.getActionCommand() == "Next Player"){
            view.handleNextPlayer();

        }

        if(e.getActionCommand() == "Draw Card"){
            view.handleDrawCard();

        }
    }
}
