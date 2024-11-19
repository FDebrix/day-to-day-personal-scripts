package test.java.algorithms;

import main.java.SudokuHelper;
import main.java.SudokuRegion;
import main.java.SudokuSquare;
import main.java.algorithms.TwoValuesPossibleInTwoSquaresInTheSameRegion;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TwoValuesPossibleInTwoSquaresInTheSameRegionTest {

    private static final SudokuHelper HELPER = SudokuHelper.getInstance();

    @Test
    public void test_constructor_regionsIsNull_returnFalse () {
        TwoValuesPossibleInTwoSquaresInTheSameRegion algo = new TwoValuesPossibleInTwoSquaresInTheSameRegion();
        assertFalse(algo.runAlgorithm((SudokuRegion) null));
    }

    @Test
    public void test_constructor_regionsAreNull_returnFalse () {
        TwoValuesPossibleInTwoSquaresInTheSameRegion algo = new TwoValuesPossibleInTwoSquaresInTheSameRegion();
        assertFalse(algo.runAlgorithm((List)null));
    }

    /**
     * Below the two columns and the two rows of a subgrid of a sudoku 4 x 4
     * [2,4]     [1,2,3,4]
     * [1,2,3]   [2,4]
     *
     * The values 2 and 4 are possible in the same time in 2 squares : in the top left square and the bottom right square.
     * That means the values 2 and 4 cannot be in the other squares of the region.
     *
     * [2,4]   [1,3]
     * [1,3]   [2,4]
     */
    @Test
    public void test_runAlgorithm_2SamePossibleValuesIn2Squares () {
        SudokuSquare squareAA = HELPER.buildSquare(4, 2, 4);
        SudokuSquare squareAB = HELPER.buildSquare(4, 1, 2, 3);
        SudokuSquare squareBA = HELPER.buildSquare(4, 1, 2, 3, 4);
        SudokuSquare squareBB = HELPER.buildSquare(4, 2, 4);

        List<SudokuSquare> subgridSquares = Arrays.asList(squareAA, squareAB, squareBA, squareBB);
        SudokuRegion subgridRegion = new SudokuRegion(subgridSquares, SudokuRegion.SudokuRegionType.SUB_GRID);
        List<SudokuRegion> regionsToRunAlgo = Arrays.asList(subgridRegion);

        TwoValuesPossibleInTwoSquaresInTheSameRegion algorithm = new TwoValuesPossibleInTwoSquaresInTheSameRegion();
        algorithm.runAlgorithm(regionsToRunAlgo);

        assertArrayEquals(new int[]{2,4}, squareAA.getPossibleValues());
        assertArrayEquals(new int[]{1,3}, squareAB.getPossibleValues());
        assertArrayEquals(new int[]{1,3}, squareBA.getPossibleValues());
        assertArrayEquals(new int[]{2,4}, squareBB.getPossibleValues());
    }
}
