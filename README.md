# Senet
Senet is an ancient Egyptian board game. I thought it would be interesting to make a version of it for my AP CS-A final. You can run the current version of the game online [here][2].

## Run Instructions
The best way to play this game is with [repl.it][2]. Powershell and CMD will produce weird gibberish because of how they handle ANSI escape sequences. This is something I need to fix later. There is currently no way to opt out of the colors. 

## Rules
Due to information being lost over thousands of years, there is no certain ruleset for Senet. However, the rules that I have implemented are straightforward and are as follows. I'm basing these rules off of [this document][1] (PDF download)
### Setup
The game is played on a board with three rows of ten. The number of rows can be changed to range from 2 to 10 rows. When this board is drawn onto the screen, each slot is numbered.  

Both player gets their own set of 5 pawns that occupy a single slot. These are represented by a specific character (`@` and `#`by default). At the start of the game they occupy the first row and alternate from left to right.

[1]:https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&ved=2ahUKEwieioWJ-unpAhWSHzQIHZQaCH0QFjAHegQICxAF&url=https%3A%2F%2Fwww.cs.brandeis.edu%2F~storer%2FJimPuzzles%2FGAMES%2FSenet%2FSenet.pdf&usg=AOvVaw3yHIpUPXjTDe_oUweiZgbE
[2]:(http://repl.it/@adamnb/Senetnet)
