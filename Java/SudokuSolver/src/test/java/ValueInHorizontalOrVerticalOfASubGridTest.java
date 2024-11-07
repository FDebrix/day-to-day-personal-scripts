package test.java;

import main.java.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ValueInHorizontalOrVerticalOfASubGridTest {

    private static final SudokuHelper HELPER = SudokuHelper.getInstance();

    @Test
    public void test_constructor_regionIsNull_returnFalse () {
        ValueInHorizontalOrVerticalOfASubGrid algo = new ValueInHorizontalOrVerticalOfASubGrid();
        assertFalse(algo.runAlgorithm((SudokuRegion)null));
    }

    @Test
    public void test_constructor_regionsAreNull_returnFalse () {
        ValueInHorizontalOrVerticalOfASubGrid algo = new ValueInHorizontalOrVerticalOfASubGrid();
        assertFalse(algo.runAlgorithm((List)null));
    }

    @Test
    public void test_runAlgorithm_aSquareDoesNotHaveSubgridRegion_throwException () {
        SudokuSquare squareAA = HELPER.buildSquare(4, 1, 2, 3);
        SudokuSquare squareAB = HELPER.buildSquare(4, 1, 2, 3);
        SudokuSquare squareAC = HELPER.buildSquare(4, 1, 4);
        SudokuSquare squareAD = HELPER.buildSquare(4, 2, 4);

        List<SudokuSquare> verticalSquares = Arrays.asList(squareAA, squareAB, squareAC, squareAD);
        SudokuRegion verticalRegion = new SudokuRegion(verticalSquares, SudokuRegion.SudokuRegionType.VERTICAL);
        List<SudokuRegion> regionsToRunAlgo = Arrays.asList(verticalRegion);

        ValueInHorizontalOrVerticalOfASubGrid algorithm = new ValueInHorizontalOrVerticalOfASubGrid();
        assertThrows(IllegalStateException.class,
                () -> algorithm.runAlgorithm(regionsToRunAlgo));
    }

    /**
     * Below the two columns of a sudoku 4 x 4
     * [1,2,3]   [1,2,3,4]  |
     * [1,2,3]   [1,2,3,4]  |
     * ----------------------
     * [1,4]     [1,2,3,4]  |
     * [2,4]     [1,2,3,4]  |
     *
     * While the algorithm will run on the left column:
     * - We can see that the value 3 is only in the two squares in the top subgrid of the left column.
     *   Then the value 3 cannot be in the other squares of the top subgrid.
     * - We can see that the value 4 is only in the two squares in the bottom subgrid of the left column.
     *   Then the value 4 cannot be in the other squares of the bottom subgrid.
     *
     * [1,2,3]   [1,2,4]  |
     * [1,2,3]   [1,2,4]  |
     * --------------------
     * [1,4]     [1,2,3]  |
     * [2,4]     [1,2,3]  |
     */
    @Test
    public void test_runAlgorithm_expectedBehavior () {
        SudokuSquare squareAA = HELPER.buildSquare(4, 1, 2, 3);
        SudokuSquare squareAB = HELPER.buildSquare(4, 1, 2, 3);
        SudokuSquare squareAC = HELPER.buildSquare(4, 1, 4);
        SudokuSquare squareAD = HELPER.buildSquare(4, 2, 4);
        SudokuSquare squareBA = new SudokuSquare(4);
        SudokuSquare squareBB = new SudokuSquare(4);
        SudokuSquare squareBC = new SudokuSquare(4);
        SudokuSquare squareBD = new SudokuSquare(4);

        List<SudokuSquare> verticalSquares = Arrays.asList(squareAA, squareAB, squareAC, squareAD);
        SudokuRegion verticalRegion = new SudokuRegion(verticalSquares, SudokuRegion.SudokuRegionType.VERTICAL);

        List<SudokuSquare> topSubgridSquares = Arrays.asList(squareAA, squareAB, squareBA, squareBB);
        SudokuRegion topSubgridRegion = new SudokuRegion(topSubgridSquares, SudokuRegion.SudokuRegionType.SUB_GRID);

        List<SudokuSquare> bottomSubgridSquares = Arrays.asList(squareAC, squareAD, squareBC, squareBD);
        SudokuRegion bottomSubgridRegion = new SudokuRegion(bottomSubgridSquares, SudokuRegion.SudokuRegionType.SUB_GRID);

        List<SudokuRegion> regionsToRunAlgo = Arrays.asList(verticalRegion, topSubgridRegion, bottomSubgridRegion);

        ValueInHorizontalOrVerticalOfASubGrid algorithm = new ValueInHorizontalOrVerticalOfASubGrid();
        algorithm.runAlgorithm(regionsToRunAlgo);

        assertArrayEquals(new int[]{1,2,3}, squareAA.getPossibleValues());
        assertArrayEquals(new int[]{1,2,3}, squareAB.getPossibleValues());
        assertArrayEquals(new int[]{1,4}, squareAC.getPossibleValues());
        assertArrayEquals(new int[]{2,4}, squareAD.getPossibleValues());
        assertArrayEquals(new int[]{1,2,4}, squareBA.getPossibleValues());
        assertArrayEquals(new int[]{1,2,4}, squareBB.getPossibleValues());
        assertArrayEquals(new int[]{1,2,3}, squareBC.getPossibleValues());
        assertArrayEquals(new int[]{1,2,3}, squareBD.getPossibleValues());
    }
}
