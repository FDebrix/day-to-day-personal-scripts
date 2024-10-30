package main.java;

import java.util.List;

public class SudokuRegion implements BroadcastWinner {

    private List<SudokuSquare> squares;

    /**
     * Singleton class
     */
    private SudokuRegion () {}

    /**
     * Instantiate a region using a list of squares.
     * @param theSquares The list of squares of the region. Cannot be null or empty.
     */
    public SudokuRegion (List<SudokuSquare> theSquares) {
        validateListNotNullAndNotEmpty(theSquares) ;

        this.squares = theSquares;
    }

    public List<SudokuSquare> getSudokuSquares() {
        return this.squares;
    }

    /**
     * Function call by a square that just found its winner value.
     * All the others squares of the region need to be notified.
     * @param square
     */
    @Override
    public void broadcastWinner(SudokuSquare square) {
        validateInputIsNotNullAndHasWinnerValue(square);

        int winnerValue = square.getWinnerValue();

        for (SudokuSquare aSquare : this.squares) {
            if(aSquare != square) {
                aSquare.setLoserValue(winnerValue);
            }
        }
    }

    private void validateInputIsNotNullAndHasWinnerValue(SudokuSquare square) {
        if(square == null)
            throw new IllegalArgumentException("The input square cannot be null.");
        if(! square.isWinnerValueFound())
            throw new IllegalArgumentException("The input square does not have a winner value yet.");
    }

    private void validateListNotNullAndNotEmpty(List<SudokuSquare> theSquares) {
        if(theSquares == null)
            throw new IllegalArgumentException("The list provided to the constructor SudokuRegion cannot be null.");
        if(theSquares.isEmpty())
            throw new IllegalArgumentException("The list provided to the constructor SudokuRegion cannot be empty.");
    }
}
