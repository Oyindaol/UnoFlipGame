import javax.swing.*;

public interface UNOView {
    public void handlePlay();

    public void handleNextPlayer(UNOModel unoModel);

    public void handleDrawCard(UNOModel unoModel);

    void handlePlacement(UNOEvent e);

    void handleWildCard(UNOModel unoModel);
}
