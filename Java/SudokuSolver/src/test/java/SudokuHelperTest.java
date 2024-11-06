package test.java;

import main.java.SudokuHelper;
import main.java.SudokuRegion;
import main.java.SudokuRegions;
import main.java.SudokuSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.java.SudokuRegion.SudokuRegionType.HORIZONTAL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SudokuHelperTest {

    private SudokuHelper helper;

    @Mock
    private SudokuSquare square1;

    @Mock
    private SudokuSquare square2;

    @Mock
    private SudokuRegion region1;

    @BeforeEach
    public void beforeAll() {
        square1 = mock(SudokuSquare.class);
        square2 = mock(SudokuSquare.class);
        region1 = mock(SudokuRegion.class);
        helper = SudokuHelper.getInstance();
    }

    @Test
    public void test_getInstance_returnANotNullInstance() {
        assertNotNull(SudokuHelper.getInstance());
    }

    @Test
    public void test_allWinnerFound_inputNull_returnFalse() {
        assertFalse(helper.allWinnerFound((SudokuSquare[][])null));
    }

    @Test
    public void test_allWinnerFound_inputEmpty_returnFalse() {
        SudokuSquare[][] sudokuSquares = new SudokuSquare[0][0];
        assertFalse(helper.allWinnerFound(sudokuSquares));
    }

    @Test
    public void test_allWinnerFound_allSquaresWinner_returnTrue() {
        when(square1.isWinnerValueFound()).thenReturn(true);
        when(square2.isWinnerValueFound()).thenReturn(true);

        SudokuSquare[][] sudokuSquares = new SudokuSquare[1][2];
        sudokuSquares[0] = new SudokuSquare[]{square1, square2};

        assertTrue(helper.allWinnerFound(sudokuSquares));
    }


    @Test
    public void test_allWinnerFound_oneSquareNotWinnerWinner_returnFalse() {
        when(square1.isWinnerValueFound()).thenReturn(true);
        when(square2.isWinnerValueFound()).thenReturn(false);

        SudokuSquare[][] sudokuSquares = new SudokuSquare[1][2];
        sudokuSquares[0] = new SudokuSquare[]{square1, square2};

        assertFalse(helper.allWinnerFound(sudokuSquares));
    }


    @Test
    public void test_allWinnerFoundList_inputSudokuRegionNull_returnFalse() {
        assertFalse(helper.allWinnerFound((SudokuRegion)null));
    }

    @Test
    public void test_allWinnerFoundList_inputNull_returnFalse() {
        when(region1.getSudokuSquares()).thenReturn(null);
        assertFalse(helper.allWinnerFound(region1));
    }

    @Test
    public void test_allWinnerFoundList_inputEmpty_returnFalse() {
        List<SudokuSquare> sudokuSquares = new ArrayList<>();
        SudokuRegion region = new SudokuRegion(sudokuSquares, HORIZONTAL);
        assertFalse(helper.allWinnerFound(region));
    }

    @Test
    public void test_allWinnerFoundList_allSquaresWinner_returnTrue() {
        when(square1.isWinnerValueFound()).thenReturn(true);
        when(square2.isWinnerValueFound()).thenReturn(true);
        List<SudokuSquare> sudokuSquares = Arrays.asList(square1, square2);
        SudokuRegion region = new SudokuRegion(sudokuSquares, HORIZONTAL);

        assertTrue(helper.allWinnerFound(region));
    }

    @Test
    public void test_allWinnerFoundList_oneSquareNotWinnerWinner_returnFalse() {
        when(square1.isWinnerValueFound()).thenReturn(true);
        when(square2.isWinnerValueFound()).thenReturn(false);
        List<SudokuSquare> sudokuSquares = Arrays.asList(square1, square2);
        SudokuRegion region = new SudokuRegion(sudokuSquares, HORIZONTAL);

        assertFalse(helper.allWinnerFound(region));
    }

    @Test
    public void test_getSquaresPerPossibleValues_nullRegion_returnEmptyList() {
        List<List<SudokuSquare>> squaresPerPossibleValues =
                helper.getSquaresPerPossibleValues(null);
        assertNotNull(squaresPerPossibleValues);
        assertTrue(squaresPerPossibleValues.isEmpty());
    }

    @Test
    public void test_getSquaresPerPossibleValues_expectedBehavior () {
        SudokuSquare square01 = new SudokuSquare(4);
        SudokuSquare square02 = new SudokuSquare(4);
        SudokuSquare square03 = new SudokuSquare(4);
        SudokuSquare square04 = new SudokuSquare(4);

        SudokuRegion region = new SudokuRegion(
                List.of(square01, square02, square03, square04), HORIZONTAL);
        SudokuRegions regions = new SudokuRegions();
        regions.addRegion(region);

        square01.setRegions(regions);
        square02.setRegions(regions);
        square03.setRegions(regions);
        square04.setRegions(regions);

        square01.setWinnerValue(1);
        square02.setWinnerValue(2);

        List<List<SudokuSquare>> squaresPerPossibleValues =
                helper.getSquaresPerPossibleValues(region);

        SudokuSquare[] squaresWithPossibleValue1 = {square01};
        SudokuSquare[] squaresWithPossibleValue2 = {square02};
        SudokuSquare[] squaresWithPossibleValue3 = {square03, square04};
        SudokuSquare[] squaresWithPossibleValue4 = {square03, square04};

        assertNotNull(squaresPerPossibleValues);
        assertEquals(5, squaresPerPossibleValues.size());
        assertArrayEquals(squaresWithPossibleValue1, squaresPerPossibleValues.get(1).toArray());
        assertArrayEquals(squaresWithPossibleValue2, squaresPerPossibleValues.get(2).toArray());
        assertArrayEquals(squaresWithPossibleValue3, squaresPerPossibleValues.get(3).toArray());
        assertArrayEquals(squaresWithPossibleValue4, squaresPerPossibleValues.get(4).toArray());
    }
}
