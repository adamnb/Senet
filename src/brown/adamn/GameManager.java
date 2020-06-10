package brown.adamn;

import java.util.Scanner;

/**
 * This class helps clean up code in Main class and should handle miscellaneous stuff that shouldn't be in the Senet class
 * This class governs some of the game's rules and behaviors
 */
public class GameManager {
    private Senet game;

    public GameManager (Senet game) { this.game = game; }

    // Game loop
    public void run () {
        Scanner scanner = new Scanner (System.in);

        boolean retry = false; // If the player attempts to preform an invalid move.
        int lastRoll = 1;
        while (game.getScore()[0] <= 5 || game.getScore()[1] <= 5) { // Breaks on win
            drawBoard();

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
                System.out.println("Additional throw gained.");
            else System.out.println();

            // Obtain player input to move piece
            System.out.print(AnsiColors.player("PLAYER "+game.getTurn()+" \""+game.getPlayerSymbols()[game.getTurn()-1]
                    +"\" MOVE: ", game.getTurn()));

            String movInp = scanner.next(); // nextInt() sucks
            int mov;

            try {
                mov = Integer.parseInt(movInp)-1;
            } catch (NumberFormatException e) {
                String lc = movInp.toLowerCase();
                if (lc.equals("exit") || lc.equals("stop")) {
                    System.out.println("Stopping game...");
                    return;
                } else if (lc.equals("score") || lc.equals("scores")) {
                    System.out.println(displayScores());
                } else if (lc.equals("skip") || lc.equals("pass")) {
                    System.out.println("Turn skipped.");
                    game.nextTurn();
                    continue;
                } else {
                    System.out.println(AnsiColors.err("Try using a number"));
                }
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
                        errMsg = "You can't move that piece there";
                        break;
                    case 4:
                        errMsg = "You can't attack yourself.";
                        break;
                    case 5:
                        errMsg = "You are attempting to attack a piece that is guarded.";
                        break;
                    case 6:
                        errMsg = "You must move the piece on #10 at the beginning of the game.";
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

    /**
     * @param print If set to true, prints the board directly into the console
     * @return The board represented in text
     */
    // TODO: Okay this just kinda sucks
    public String drawBoard (boolean print) {

        StringBuilder ret = new StringBuilder(9*game.getBoard().length);

        for (int j = 0; j < game.getBoard().length; j+=10) {
            // Display board pieces
            if (j >= 10)
                ret.append("\n");

            for (int i = j; i < j+10; i++) {
                int player = game.getBoard()[i];
                ret.append(" ").append(game.isGuarded(i) ? AnsiColors.UNDERL : "")
                        .append(
                            i == game.getAnkh() && player == 0 ?
                                "\u2625" : // Show ankh or show player or space
                                AnsiColors.player(Character.toString(game.getPlayerSymbol(i)), player)
                        ).append("\u001B[0m ");
            }
            ret.append("\n");
            // Write numbers below
            for (int i = 0; i < 10; i++) {
                // Handle formatting for single & double digit numbers
                String digit = "" + (j + i + 1);
                if (j + i + 1 < 10)
                    digit = (j + i + 1) + " ";
                ret.append(" ").append(digit);
            }
            ret.append("\n------------------------------"); // Row separator
        }

        if (print)
            System.out.println(ret);

        return ret.toString();
    }
    public String drawBoard () { return drawBoard(true); }

    public String displayScore (int player) {
        StringBuilder ret = new StringBuilder(112);
        int score = game.getScore()[player-1];
        ret.append(AnsiColors.player("Player "+player, player)).append(" [")
                .append(repeatStr(AnsiColors.player("█", player), score))
                .append(repeatStr(" ",5-score)).append("] ").append(score).append("/5");
        return ret.toString();
    }
    public String displayScores () { return displayScore(1)+"\n"+displayScore(2); }

    public void setGame (Senet game) { this.game = game; }
    public Senet getGame () { return game; }

    String repeatStr (String str, int n) { // I need this because I don't want to link to java 11 :(
        StringBuilder sb = new StringBuilder(n*str.length());
        for (int i=0; i<Math.abs(n); i++)
            sb.append(str);
        return sb.toString();
    }

}
