/**
 * UNOView interface.
 * View part of the implementation of the MVC pattern.
 */
public interface UNOView {
    void handleNextPlayer(UNOModel unoModel);

    void handleDrawCard(UNOModel unoModel);

    void handlePlacement(UNOEvent e);

    void handleWildCard(UNOModel unoModel);

    void handleAITurn(UNOEvent e);

    void handleSaveGame();
}
