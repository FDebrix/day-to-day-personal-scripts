package test.java;

import main.java.SudokuHelper;
import main.java.SudokuRegion;
import main.java.SudokuSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        SudokuRegion region = new SudokuRegion(sudokuSquares, SudokuRegion.SudokuRegionType.HORIZONTAL);
        assertFalse(helper.allWinnerFound(region));
    }

    @Test
    public void test_allWinnerFoundList_allSquaresWinner_returnTrue() {
        when(square1.isWinnerValueFound()).thenReturn(true);
        when(square2.isWinnerValueFound()).thenReturn(true);
        List<SudokuSquare> sudokuSquares = Arrays.asList(square1, square2);
        SudokuRegion region = new SudokuRegion(sudokuSquares, SudokuRegion.SudokuRegionType.HORIZONTAL);

        assertTrue(helper.allWinnerFound(region));
    }

    @Test
    public void test_allWinnerFoundList_oneSquareNotWinnerWinner_returnFalse() {
        when(square1.isWinnerValueFound()).thenReturn(true);
        when(square2.isWinnerValueFound()).thenReturn(false);
        List<SudokuSquare> sudokuSquares = Arrays.asList(square1, square2);
        SudokuRegion region = new SudokuRegion(sudokuSquares, SudokuRegion.SudokuRegionType.HORIZONTAL);

        assertFalse(helper.allWinnerFound(region));
    }
}
