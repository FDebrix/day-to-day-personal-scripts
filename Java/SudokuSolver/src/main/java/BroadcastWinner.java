package main.java;

/**
 * When a square found its winner value, the square will call a BroadcastWinner to broadcast the information.
 */
public interface BroadcastWinner {
    public void broadcastWinner(SudokuSquare square);
}
