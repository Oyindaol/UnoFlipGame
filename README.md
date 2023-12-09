# UnoFlipGame
A simplified version of the classic card UNOModel with a flip twist, Uno Flip, built using Java and Java.util packages.

## Milestone 4 Deliverables
A GUI-based version of Uno Flip where players use mouse and keyboard input to play the game. Players have the capability to:
1. View and select any of the cards in their deck.
2. Place cards using the official notation detailed in the Wikipedia link (By colour or number/type).
3. Execute actions associated with special cards, including Reverse, Skip, Wild, and Wild Draw Two cards.
4. Flip the game mode when the Flip card is played.
5. Play the game with AI player(s).
6. Observe the resultant state of the cards, presented in GUI format.
7. Draw a card from the market if they want/need to.
8. End their turn by selecting the Next Player button.
9. Undo/Redo a move during their turn.
10. Save/Load a Game.
11. Restart the game.
12. Declare UNO!
13. Win by playing all the cards in their deck before any other player.

## Authors
#### Osas Iyamu 
- Refactored the code to fit the MVC model.
- Worked on the implementation of the UNOModel, UNOFrame, UNOController, UNOView, UNOEvent, Player, Colors, NumberCard, SpecialCard and Card classes and interfaces.
- Worked on the AI player implementation and Flip card integration.
- Worked on Save/Load implementation.
- Worked on Replay implementation.
- Worked on the Game flow logic.
- Refactored the code to remove code smells.
- Added Java Doc comments to classes and methods.
- Updated the Design Decision section to reflect the refined design in the Documentation. 

#### Oyindamola Taiwo-Olupeka
- Refactored the code to fit the MVC model.
- Worked on the implementation of the UNOModel, UNOFrame, UNOController, UNOView classes and interfaces.
- Worked on Undo/Redo implementation.
- Refactored the code to remove code smells.
- Added Java Doc comments to classes and methods.
- Updated the User Manual for the GUI implementation in the Documentation.

#### Daniel Esenwa
- Worked on the UML class diagram and sequence diagram.
- Worked on the UNOModelTest testing class.
- Updated the Diagrams section in the Documentation.

#### Forewa Aboderin
- Worked on the AI player implementation.
- Worked on the UML class diagram.
- Updated the Design Decisions section in the Documentation.

## Known Issue(s)
- Occasional Inconsistencies with the load feature.

## Change Log
- Refactored the code to fit the MVC pattern by making our Game class our Model, adding a View interface to handle the views in our game, and adding a Controller class to control the game.
- Added many new methods to the UNOModel class to handle the GUI functionalities.
- Added a Frame class to build the GUI.
- Uno Flip card implementation into the UNOModel, considering their specific rules and scoring mechanisms.
- Added AI player capability.
- Added Undo/Redo features.
- Added Save/load features.
- Added Replay feature.
- Added more JUnit tests for the new methods.
- Refactored the project to remove code smells.

## Milestone Roadmap
### M1 - October 22, 2023
- Text-based version of the UNOModel played with the keyboard.
- Implementation of the gameplay logic and the primary challenge of ensuring the correctness of card placement, validating cards and adding to the score after each card placement.

### M2 - November 12, 2023
- GUI-based version of the UNOModel.
- Unit Testing for the Model.

### M3 - November 23, 2023
- Uno Flip card implementation into the UNOModel, considering their specific rules and scoring mechanisms.
- Adding AI player capability.
- Unit Testing for the UNOModel.

### M4 - December 8, 2023
- Addition of undo and redo features.
- Addition of Save and Load features: enable players to save their current UNOModel progress and later resume it
- Addition of Replay feature: Players will now have the ability to replay the UNOModel, allowing them to restart and enjoy multiple rounds
- Unit Testing for the UNOModel.
