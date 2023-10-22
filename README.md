# UnoFlipGame
A simplified version of the classic card game with a flip twist, Uno Flip, built using Java and Java.util packages.

## Milestone 1 Deliverables
A Text-based version of Uno Flip where players use a mouse and keyboard input to play the game. Players have the capability to:
1. View their drawn cards.
2. Place cards using the official notation detailed in the Wikipedia link.
3. Draw one card.
4. Execute actions associated with special cards, including Reverse, Skip, Wild, and Wild Draw Two cards.
5. Observe the resultant state of the cards, presented in text format.

## Authors
#### Osas Iyamu 
- Worked on the implementation of the game model, player, and card class.
- Refactored the card interface and special card classes and separated  it into two classes (NumberCard and SpecialCard).
- Added Java Doc comments to classes and methods.

#### Oyindamola Taiwo-Olupeka
- Worked on the implementation of the special cards (Skip, Reverse, Wild).
- Updated the design decision document to reflect the refined design. 
- Added Java Doc comments to classes and methods.

#### Daniel Esenwa
- Worked on the UML class diagram and sequence diagram.
- Tested the UNO Flip game.

## Known Issues
- Issues with tallying the scores after playing any special card.
- The wild special card still depends on colour for the logic to work.
- Occasional Out-of-bounds Error when using the special cards after multiple rounds.

## Change Log

## Milestone Roadmap
### M1 - October 22, 2023
- Text-based version of the game played with the keyboard.
- Implementation of the gameplay logic and the primary challenge of ensuring the correctness of card placement, validating cards and adding to the score after each card placement.

### M2 - November 12, 2023
- GUI-based version of the game.
- Unit Testing for the Model.

### M3 - November 23, 2023
- Uno Flip card implementation into the game, considering their specific rules and scoring mechanisms.
- Adding AI player capability.
- Unit Testing for the Game.

### M4 - December 8, 2023
- Addition of undo and redo features.
- Addition of Save and Load features: enable players to save their current game progress and later resume it
- Addition of Replay feature: Players will now have the ability to replay the game, allowing them to restart and enjoy multiple rounds
- Unit Testing for the Game.
