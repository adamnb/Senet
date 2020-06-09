package brown.adamn;

import java.util.Random;

public class Senet {
    private final int[] board;   // Stores position of player pieces
    private final char[] playerSymbol = new char[2]; // On-board representation of character pieces
    private final int[] score         = new int[2];

    private int turn  = 0; // Current player's turn. 1: player 1 (server), 2: player 2 (client), 0: no player
    private int roll  = 1; // What the current roll amount is for the current turn
    private int moves = 0; // How many moves have been executed

    private int ankh = -1;

    /**
     * @param player1 The symbol representing player 1
     * @param player2 The symbol representing player 2
     * @param initiative Which player (1, 2, or 0) goes first
     * @param rows The number of rows in the generated board
     */
    public Senet (char player1, char player2, int initiative, int rows) {
        if (player1 == player2) {
            player1 = 'A';
            player2 = 'B';
        }

        playerSymbol[0] = player1;
        playerSymbol[1] = player2;

        // Keep number of rows within a reasonable range
        if (rows < 1)
            rows = 3;
        if (rows > 10)
            rows = 10;
        board = new int[10*rows];

        // Place Ankh in middle of the board if applicable
        if (rows > 1)
            ankh = board.length/2-1;

        // Keep first player within range
        if (initiative > 2)
            turn = 2;
        else if (initiative < 0)
            turn = 1;
        else
            turn = initiative;

        // Populate board (Alternate players for first 10 spaces)
        for (int i = 0; i < 10; i++) {
            if (i%2 == 0) {
                board[i] = oppTurn();
            } else {
                board[i] = turn;
            }
        }
    }
    public Senet (char player1, char player2, int initiative) { this (player1, player2, initiative, 3); }
    public Senet (char player1, char player2) { this (player1, player2, 1, 3); }
    public Senet (int rows) { this('A', 'B', 1, rows); }
    public Senet () { this ('A', 'B', 1, 3);}

    /**
     * @param pos The position of the target piece to be moved. This is the array index, so it ranges from 0 to board.length-1.
     * @return The error code of the move. 0=valid, -n=player n scored, 1=No piece at position, 2=not your piece, 3=friendly fire, 4=guarded opponent
     */
    public int move (int pos) {
        // Determining move distance and handling extra turns
        int spaces = rollSpaces();

        final int destPos = pos+spaces; // Board index of destination slot

        // Invalid Moves
        if ((pos > board.length-1 || pos < 0) || board[pos] == 0)
            return 1; // Illegal move: There is no piece at this position or out of bounds
        if (board[pos] != turn)
            return 2; // Illegal move: Attempt to move opponent piece

        if (destPos == board.length) {
            System.out.println("Score!");
            moves++;
            board[pos] = 0;
            score[turn-1]++;
            nextTurn();
            return -oppTurn(); // Player has moved a piece off the board. Congrats!
        }
        else if (destPos < 0 || destPos > board.length)
            return 3; // Player cannot move there

        final int dest = board[destPos]; // The boardpiece where the selected piece is going

        if (dest == turn)
            return 4; // Illegal move: Attempt to attack friendly piece
        if (dest == oppTurn() && isGuarded(destPos))
            return 5; // Illegal move: This piece is guarded
        if (moves == 0 && pos != 9)
            return 6;

        if (dest == oppTurn()) { // Swap places with opponent
            board[pos+spaces] = turn;
            board[pos] = oppTurn();
        }

        if (dest == 0) { // Move piece to empty square
            board[pos+spaces] = turn;
            board[pos] = 0;
        }

        if (!hasExtraTurn()) {
            nextTurn();
        }

        moves++;
        return 0;

    }

    /**
     * Simulates dice-stick throwing and updates the roll attribute accordingly
     * @return The result of throwing the sticks
     */
    public int toss () {
        int result = 0;

        Random rng = new Random();
        for (int i = 0; i < 4; i++) { // Throw four sticks (flip four coins)
            double r = rng.nextDouble();
            if (r < 0.5)
                result++;
        }

        roll = result;
        return result;
    }

    /**
     * @return The number of spaces the player must move given the current roll value.
     */
    public int rollSpaces () {
        if (roll == 0) // No white sides up
            return 6;
        return roll;
    }

    /**
     * @return If the player has an extra turn given the current roll.
     */
    public boolean hasExtraTurn () { return roll == 0 || roll == 1 || roll == 4; }

    /**
     * @return The current opponents turn number. 1 for player 1. 2 for player 2.
     */
    public int oppTurn () {
        if (turn == 0)
            return 0;
        if (turn == 1)
            return 2;
        else
            return 1;
    }

    /**
     * @param pos The index of the piece to be guarded
     * @return Whether the piece at index "pos" is guarded. A piece is guarded when there is another friendly piece in an adjacent spot
     */
    public boolean isGuarded (int pos) {
        // Out of bounds protection
        if (pos < 0)
            pos = 0;
        if (pos >= board.length)
            pos = board.length-1;

        int team = board[pos];

        if (team > 0 && pos == ankh) // Ankh protection
            return true;

        return team > 0 &&
                ((pos > 0 && board[pos-1] == team) || // Check behind
                (pos < board.length - 1 && board[pos + 1] == team)); // Check in front
    }

    /**
     * This method switches the turn number to the opposite player
     */
    public void nextTurn () {
        turn = oppTurn();;
    }

    // General Set-Get methods
    public int getTurn() { return turn; }

    public int[] getBoard() { return board; }

    public char[] getPlayerSymbol() { return playerSymbol; }

    public int getMoves () { return moves; }

    public int[] getScore () { return score; }

    public int getRoll () { return roll; }

}
