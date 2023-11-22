/**
 * UNOView interface.
 * View part of the implementation of the MVC pattern.
 */
public interface UNOView {
    public void handleNextPlayer(UNOModel unoModel);

    public void handleDrawCard(UNOModel unoModel);
    void handlePlacement(UNOEvent e);

    void handleWildCard(UNOModel unoModel);
    void handleAITurn(UNOModel unoModel);
}
