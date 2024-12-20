package main.java.algorithms;

import main.java.SudokuRegion;
import main.java.SudokuSquare;

import java.util.List;

import static main.java.SudokuHelper.SOP;

/**
 * Algorithm to find the values which are available only in one square of a region.
 * <br/>
 * Let assume we have a 9 x 9 sudoku. On one row, we have the follow possible values:
 *                      8,9 | 3 | 7 | 4 | 5 | 1 | 8,9 | 2,8 | 6
 * <br/>
 * Only 3 squares are not resolved yet. In the position 8, we can see the possibles values 2 and 8.
 * 2 is not possible in other squares. Then even if there are 2 possibles values in the position 8,
 * we know for sure that 2 is the winner value in the position 8. We can update this line to
 *                      8,9 | 3 | 7 | 4 | 5 | 1 | 8,9 | 2 | 6
 */
public class OneValueInOneSquareOfTheRegion extends SudokuAbstractAlgorithm {

    @Override
    protected boolean internalRunAlgorithm(SudokuRegion region) {
        boolean findAWinner = false;

        List<List<SudokuSquare>> squaresPerPossibleValue = getSquaresPerPossibleValues(region);

        for(int i = 1 ; i < squaresPerPossibleValue.size() ; i++) {
            List<SudokuSquare> squaresForOnePossibleValue = squaresPerPossibleValue.get(i);

            if(valuePossibleOnlyInOneSquareWithoutWinnerValue(squaresForOnePossibleValue)){
                if(SOP) System.out.println("The value " + i + " was found in the region.");
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
}
