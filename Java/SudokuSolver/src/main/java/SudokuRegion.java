package main.java;

import java.util.List;

public class SudokuRegion implements BroadcastWinner {

    private List<SudokuSquare> squares;

    private SudokuRegion () {}

    public SudokuRegion (List<SudokuSquare> theSquares) {
        validateListNotNullAndNotEmpty(theSquares) ;

        this.squares = theSquares;
    }

    public List<SudokuSquare> getSudokuSquares() {
        return this.squares;
    }

    @Override
    public void broadcastWinner(SudokuSquare square) {
        validateInputIsNotNullAndHasWinnerValue(square);

        int winnerValue = square.getWinnerValue();

        for (SudokuSquare aSquare : squares) {
            if(aSquare != square) {
                aSquare.setLoserValue(winnerValue);
            }
        }
    }

    private void validateInputIsNotNullAndHasWinnerValue(SudokuSquare square) {
        if(square == null)
            throw new IllegalArgumentException("The input square cannot be null");
        if(! square.isWinnerValueFound())
            throw new IllegalArgumentException("The winner value was not found for the input square");
    }

    private void validateListNotNullAndNotEmpty(List<SudokuSquare> theSquares) {
        if(theSquares == null || theSquares .isEmpty()) {
            throw new IllegalArgumentException("The list provided to the constructor SudokuRegion cannot be null");
        }
    }
}
