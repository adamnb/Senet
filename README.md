# Senet
Senet is an ancient Egyptian board game. I thought it would be interesting to make a version of it for my AP CS-A final. You can run the current version of the game online [here][2].

## Run Instructions
The best way to play this game is with [repl.it][2]. Powershell and CMD will produce weird gibberish because of how they handle ANSI escape sequences without a proper terminal emulator. This is something I need to fix later. There is currently no way to opt out of the colors. 

## Rules
Due to information being lost over thousands of years, there is no certain ruleset for Senet. However, the rules that I have implemented so far are very limited and are straightforward. I'm basing these rules off of [this document][1] (PDF).

### Setup
The game is played on a board with three rows of ten. The number of rows can be changed to range from 2 to 10 rows. When this board is drawn onto the screen, each space is numbered.

Both players gets their own set of 5 pawns that occupy a single space. These are represented by a specific character (`@` and `#`by default). At the start of the game they occupy the first row and alternate from space 1-10.

At the start of each turn, a roll (or throw) is made that ranges from 0-4. This determines the number of spaces the active player may move one of their pieces. On a roll of 0, 1, or 4, the player may have another turn directly after the current one. A turn may be skipped by entering `skip`.

A player moves a piece by entering the number of the space occupied by the piece that they want to move. This piece must belong to the active player, must not run into another one of the active player's pieces, run into a _protected_ piece, or or overshoot the end of the board.

The first player to move is player 1, who must move the piece on space 10 on the first turn.

A player gains points to win by moving their pieces off the board. This must be a movevement that moves it exactly one space beyond the last space. A player wins if they successfully move all of their pieces off the board. The current score can be displayed by entering `score`, but will otherwise appear when a player is moved off the board.

An _attack_ is made when a piece is moved on to another piece belonging to its opponent. This will swap their spaces. An attack cannot be made when the the piece being attacked is _protected_.

A piece is _protected_ when it is adjacent to a piece of its own or when it's on the _ankh_.

The _ankh_ is a piece halfway through the board that protects any piece on top of it. This is normally depicted with a `☥` but is a question mark on fonts that don't support this character. 

[1]:https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&ved=2ahUKEwieioWJ-unpAhWSHzQIHZQaCH0QFjAHegQICxAF&url=https%3A%2F%2Fwww.cs.brandeis.edu%2F~storer%2FJimPuzzles%2FGAMES%2FSenet%2FSenet.pdf&usg=AOvVaw3yHIpUPXjTDe_oUweiZgbE
[2]:https://repl.it/@adamnb/Senetnet
