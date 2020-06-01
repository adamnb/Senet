package brown.adamn;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Senet game = new Senet ('@', '#', 1, 2);
        Scanner scanner = new Scanner(System.in);

        boolean retry = false; // If the player attempts to preform an invalid move.
        int lastRoll = 1;
        while (game.getScore()[0] <= 5 || game.getScore()[1] <= 5) { // Breaks on win

            game.drawBoard();

            // Throw dice sticks
            int roll = game.toss();
            if (retry)
                roll = lastRoll;

            // Report roll
            System.out.print("\nPlayer " + game.getTurn() + " threw a " + roll +
                    ". Player may move " + game.rollSpaces() + " spaces. ");
            if (game.hasExtraTurn())
                System.out.println("Additional throw gained.");
             else System.out.println("");

            // Obtain player input to move piece
            System.out.print("PLAYER " + game.getTurn() +" \""+game.getPlayerSymbol()[game.getTurn()-1]+"\" MOVE: ");

            int movInp = scanner.nextInt()-1;
            int moveError = game.move(movInp);
            if (moveError > 0) {
                retry = true;
                lastRoll = roll;
            } else
                retry = false;

            System.out.println("Move resulted in "+moveError);
        }
    }
}
