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
            String characteristics = button.getText().split(" ")[0];
            if(!characteristics.equals("WILD")) {
                String color = button.getText().split(" ")[1];
                System.out.println(characteristics + " " + color);
                model.validatePlacement(characteristics, color);
            }else{
                model.validatePlacement("WILD", "unassigned");
            }
        }

        else if(e.getActionCommand().equals("Draw From Bank")){
            model.drawFromBank();
        }
    }
}
