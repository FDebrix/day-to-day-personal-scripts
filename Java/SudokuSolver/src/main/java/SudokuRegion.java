package main.java;

import java.util.List;

public class SudokuRegion implements BroadcastWinner {

    private List<SudokuSquare> squares;
    private SudokuRegionType regionType;


    public enum SudokuRegionType {HORIZONTAL, VERTICAL, SUB_GRID}

    /**
     * Instantiate a region using a list of squares.
     *
     * @param squares The list of squares of the region. Cannot be null or empty.
     * @param regionType The type of the region.
     */
    public SudokuRegion (List<SudokuSquare> squares, SudokuRegionType regionType) {
        validateListNotNull(squares) ;
        validateRegionTypeNotNull(regionType);

        this.regionType = regionType;
        this.squares = squares;
    }

    public List<SudokuSquare> getSudokuSquares() {
        return squares;
    }

    public SudokuRegionType getRegionType() {
        return regionType;
    }

    /**
     * Function call by a square that found its winner value.
     * All the others squares of the region need to be notified.
     * @param winnerSquare The square which has a winner value.
     */
    @Override
    public void broadcastWinner(SudokuSquare winnerSquare) {
        validateInputIsNotNullAndHasNoWinnerValue(winnerSquare);

        int winnerValue = winnerSquare.getWinnerValue();

        for (SudokuSquare aSquare : squares) {
            if(aSquare != winnerSquare) {
                aSquare.setLoserValue(winnerValue);
            }
        }
    }

    @Override
    public List<SudokuRegion> getRegions() {
        return List.of(this);
    }

    private void validateInputIsNotNullAndHasNoWinnerValue(SudokuSquare square) {
        if(square == null)
            throw new IllegalArgumentException("The input square cannot be null.");
        if(! square.isWinnerValueFound())
            throw new IllegalArgumentException("The input square does not have a winner value yet.");
    }

    private void validateListNotNull(List<SudokuSquare> squares) {
        if(squares == null)
            throw new IllegalArgumentException("The list provided to the constructor SudokuRegion cannot be null.");
    }

    private void validateRegionTypeNotNull(SudokuRegionType regionType) {
        if(regionType == null)
            throw new IllegalArgumentException("The region type provided to the constructor cannot be null.");
    }
}
