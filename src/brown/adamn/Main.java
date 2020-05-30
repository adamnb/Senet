package brown.adamn;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Senet game = new Senet ('@', '#', 1);
        Scanner scanner = new Scanner(System.in);

        while (game.getScore()[0] <= 5 || game.getScore()[1] <= 5) {
            game.drawBoard();
            System.out.println("\nPlayer " + game.getTurn() + " rolled a " + game.toss());
            System.out.print("PLAYER " + game.getTurn() +" \""+game.getPlayerSymbol()[game.getTurn()-1]+"\" MOVE: ");

            int move = scanner.nextInt()-1;

            int moveError = game.move(move);
            System.out.println("Move resulted in "+moveError);
        }

    }
}
