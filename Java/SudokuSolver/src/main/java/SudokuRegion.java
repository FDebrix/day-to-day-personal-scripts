package main.java;

import java.util.List;

public class SudokuRegion implements BroadcastWinner {

    private List<SudokuSquare> squares;

    /**
     * Instantiate a region using a list of squares.
     * @param squares The list of squares of the region. Cannot be null or empty.
     */
    public SudokuRegion (List<SudokuSquare> squares) {
        validateListNotNull(squares) ;

        this.squares = squares;
    }

    public List<SudokuSquare> getSudokuSquares() {
        return squares;
    }

    /**
     * Function call by a square that found its winner value.
     * All the others squares of the region need to be notified.
     * @param winnerSquare The square which has a winner value.
     */
    @Override
    public void broadcastWinner(SudokuSquare winnerSquare) {
        validateInputIsNotNullAndHasWinnerValue(winnerSquare);

        int winnerValue = winnerSquare.getWinnerValue();

        for (SudokuSquare aSquare : squares) {
            if(aSquare != winnerSquare) {
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

    private void validateListNotNull(List<SudokuSquare> squares) {
        if(squares == null)
            throw new IllegalArgumentException("The list provided to the constructor SudokuRegion cannot be null.");
    }
}
