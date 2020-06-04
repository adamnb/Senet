package brown.adamn;

import java.util.Scanner;

/**
 * This class helps clean up code in Main class and should handle miscellaneous stuff that shouldn't be in the Senet class
 */
public class GameManager {
    private Senet game;

    public GameManager (Senet game) {
        this.game = game;
    }

    // Game loop
    public void run () {
        Scanner scanner = new Scanner (System.in);

        boolean retry = false; // If the player attempts to preform an invalid move.
        int lastRoll = 1;
        while (game.getScore()[0] <= 5 || game.getScore()[1] <= 5) { // Breaks on win
            game.drawBoard();

            // Throw dice sticks
            int roll;
            if (retry) { // Ensure that the player doesn't re-roll if they get to retry turn
                roll = lastRoll;
                retry = false;
            } else {
                roll = game.toss();
            }

            // Report roll
            System.out.print("\nPlayer " + game.getTurn() + " threw a " + roll +
                    ". A piece must move " + AnsiColors.format(AnsiColors.NUMBER,game.rollSpaces() + " spaces. "));
            if (game.hasExtraTurn())
                System.out.println(AnsiColors.ul("Additional throw gained."));
            else System.out.println();

            // Obtain player input to move piece
            System.out.print(AnsiColors.inp("PLAYER "+game.getTurn()+" \""+game.getPlayerSymbol()[game.getTurn()-1]
                    +"\" MOVE: "));

            String movInp = scanner.next(); // nextInt() sucks
            int mov;

            try {
                mov = Integer.parseInt(movInp)-1;
            } catch (NumberFormatException e) {
                System.out.println(AnsiColors.err("Try using a number"));
                retry = true;
                lastRoll = roll;
                continue;
            }

            // Error messages for invalid moves
            int moveError = game.move(mov);
            if (moveError > 0) {
                retry = true;
                lastRoll = roll;

                String errMsg = "";
                switch (moveError) {
                    case 1:
                        errMsg = "There is no piece here.";
                        break;
                    case 2:
                        errMsg = "That's not your piece.";
                        break;
                    case 3:
                        errMsg = "You can't attack yourself.";
                        break;
                    case 4:
                        errMsg = "You are attempting to attack a piece that is guarded.";
                        break;
                    default:
                        errMsg = "Unexpected violation. "+moveError;
                        break;
                }
                System.out.println(AnsiColors.err("Invalid move: " + errMsg) + "\nPlease try again.");
            }
        }
        scanner.close();
    }

    public void setGame (Senet game) { this.game = game; }
    public Senet getGame () { return game; }

}
