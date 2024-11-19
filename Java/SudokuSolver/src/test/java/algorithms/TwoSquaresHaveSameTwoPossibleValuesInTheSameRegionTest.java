package test.java.algorithms;

import main.java.SudokuHelper;
import main.java.SudokuRegion;
import main.java.SudokuSquare;
import main.java.algorithms.TwoSquaresHaveSameTwoPossibleValuesInTheSameRegion;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TwoSquaresHaveSameTwoPossibleValuesInTheSameRegionTest {

    private static final SudokuHelper HELPER = SudokuHelper.getInstance();

    /**
     * Below the two columns and the two rows of a subgrid of a sudoku 9 X 9
     * [1,2,4]      [1,3,5,6]   [1,3,5,6]
     * [1,3,5,6]    [2,4]       [1,3,5,6]
     * [1,3,7,8,9]  [1,3,7,8,9] [1,3,7,8,9]
     *
     * The values 2 and 4 are possible in the same time in 2 squares : in the top left square and the bottom right square.
     * That means those two squares can just be 2 or 4. In the top left corner, the value 1 is not possible
     *
     * [2,4]   [1,3]
     * [1,3]   [2,4]
     */
    @Test
    public void test_runAlgorithm_2SamePossibleValuesWithOtherValueIn2Squares  () {
        SudokuSquare squareAA = HELPER.buildSquare(9, 1,2,4);
        SudokuSquare squareAB = HELPER.buildSquare(9, 1,3,5,6);
        SudokuSquare squareAC = HELPER.buildSquare(9, 1,3,5,6);
        SudokuSquare squareBA = HELPER.buildSquare(9, 1,3,5,6);
        SudokuSquare squareBB = HELPER.buildSquare(9, 2,4);
        SudokuSquare squareBC = HELPER.buildSquare(9, 1,3,5,6);
        SudokuSquare squareCA = HELPER.buildSquare(9, 1,3,7,8,9);
        SudokuSquare squareCB = HELPER.buildSquare(9, 1,3,7,8,9);
        SudokuSquare squareCC = HELPER.buildSquare(9, 1,3,7,8,9);

        List<SudokuSquare> subgridSquares = Arrays.asList(squareAA, squareAB, squareAC, squareBA, squareBB, squareBC,
                squareCA, squareCB, squareCC);
        SudokuRegion subgridRegion = new SudokuRegion(subgridSquares, SudokuRegion.SudokuRegionType.SUB_GRID);
        List<SudokuRegion> regionsToRunAlgo = Arrays.asList(subgridRegion);

        TwoSquaresHaveSameTwoPossibleValuesInTheSameRegion algorithm = new TwoSquaresHaveSameTwoPossibleValuesInTheSameRegion();
        algorithm.runAlgorithm(regionsToRunAlgo);

        assertArrayEquals(new int[]{2,4}, squareAA.getPossibleValues());
        assertArrayEquals(new int[]{2,4}, squareBB.getPossibleValues());
    }
}
