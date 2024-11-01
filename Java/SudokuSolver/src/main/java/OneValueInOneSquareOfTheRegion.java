package main.java;

import java.util.ArrayList;
import java.util.List;

import static main.java.SudokuHelper.SOP;

/**
 * Algorithm to find the value only available in one place.
 *
 * Let assume we have a 9 x 9 sudoku. On one row, we have the follow possible values:
 *                      8,9 | 3 | 7 | 4 | 5 | 1 | 8,9 | 2,8 | 6
 *
 * Only 3 squares are not resolved yet. In the position 8, we can see the possibles values 2 and 8.
 * 2 is not possible in other squares. Then even if there are 2 possibles values in the position 8,
 * we know for sure that 2 is the winner value in the position 8. We can update this line to
 *                      8,9 | 3 | 7 | 4 | 5 | 1 | 8,9 | 2 | 6
 */
public class OneValueInOneSquareOfTheRegion implements SudokuAlgorithm {

    @Override
    public boolean runAlgorithm(List<SudokuRegion> regions) {
        if(regions == null || regions.isEmpty())
            return false;

        if(SOP) System.out.println("OneValueInOneSquareOfTheRegion was call with " + regions.size() + " regions.");

        boolean findAWinner = true;

        for(SudokuRegion region : regions) {
            boolean findAWinnerOneRun = runAlgorithm(region);
            findAWinner &= findAWinnerOneRun;
        }

        return findAWinner;
    }

    @Override
    public boolean runAlgorithm(SudokuRegion region) {
        if(region == null)
            return false;

        if(SudokuHelper.getInstance().allWinnerFound(region))
            return false;

        boolean findAWinner = false;

        List<List<SudokuSquare>> squaresPerPossibleValue = getSquaresPerPossibleValues(region);

        for(int i = 0 ; i < squaresPerPossibleValue.size() ; i++) {
            List<SudokuSquare> squaresForOnePossibleValue = squaresPerPossibleValue.get(i);

            if(valuePossibleOnlyInOneSquareWithoutWinnerValue(squaresForOnePossibleValue)){
                squaresForOnePossibleValue.getFirst().setWinnerValue(i);
                findAWinner = true;
            }
        }

        return findAWinner;
    }

    // Test if:
    // - There is one unique square in the list. That means that the value is only possible in one square for the entire region.
    // - The square did not find yet its winner value. Then, in theory, this square has multiple possible values.
    private boolean valuePossibleOnlyInOneSquareWithoutWinnerValue(List<SudokuSquare> squaresForOnePossibleValue) {
        return squaresForOnePossibleValue.size() == 1
                && ! squaresForOnePossibleValue.getFirst().isWinnerValueFound();
    }

    // The index i of the first list contains the list of all the squares who have for possible value i.
    private List<List<SudokuSquare>> getSquaresPerPossibleValues(SudokuRegion region) {
        List<List<SudokuSquare>> squaresPerPossibleValues = buildEmptySquaresPerPossibleValues(region);

        for(SudokuSquare aSquare : region.getSudokuSquares()) {
            int[] values = aSquare.getWinnerValueOrPossibleValues();

            for(int value : values) {
                squaresPerPossibleValues.get(value).add(aSquare);
            }
        }

        return squaresPerPossibleValues;
    }

    private List<List<SudokuSquare>> buildEmptySquaresPerPossibleValues(SudokuRegion region) {
        int nbPossibleValues = region.getSudokuSquares().size();

        List<List<SudokuSquare>> squaresPerPossibleValues = new ArrayList<>();

        for (int i = 0 ; i < nbPossibleValues + 1 ; i++) {
            squaresPerPossibleValues.add(i, new ArrayList<>());
        }

        return squaresPerPossibleValues;
    }
}
