package test.java;

import main.java.SudokuHelper;
import main.java.SudokuSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SudokuHelperTest {

    private SudokuHelper helper;

    @Mock
    private SudokuSquare square1;

    @Mock
    private SudokuSquare square2;

    @BeforeEach
    public void beforeAll() {
        square1 = mock(SudokuSquare.class);
        square2 = mock(SudokuSquare.class);
        helper = SudokuHelper.getInstance();
    }

    @Test
    public void test_getInstance_returnANotNullInstance() {
        assertNotNull(SudokuHelper.getInstance());
    }

    @Test
    public void test_allWinnerFound_inputNullReturnFalse() {
        assertFalse(helper.allWinnerFound(null));
    }

    @Test
    public void test_allWinnerFound_inputEmptyReturnFalse() {
        SudokuSquare[][] sudokuSquares = new SudokuSquare[0][0];
        assertFalse(helper.allWinnerFound(sudokuSquares));
    }

    @Test
    public void test_allWinnerFound_allSquaresWinnerReturnTrue() {
        when(square1.isWinnerValueFound()).thenReturn(true);
        when(square2.isWinnerValueFound()).thenReturn(true);

        SudokuSquare[][] sudokuSquares = new SudokuSquare[1][2];
        sudokuSquares[0] = new SudokuSquare[]{square1, square2};

        assertTrue(helper.allWinnerFound(sudokuSquares));
    }


    @Test
    public void test_allWinnerFound_oneSquareNotWinnerWinnerReturnFalse() {
        when(square1.isWinnerValueFound()).thenReturn(true);
        when(square2.isWinnerValueFound()).thenReturn(false);

        SudokuSquare[][] sudokuSquares = new SudokuSquare[1][2];
        sudokuSquares[0] = new SudokuSquare[]{square1, square2};

        assertFalse(helper.allWinnerFound(sudokuSquares));
    }
}
