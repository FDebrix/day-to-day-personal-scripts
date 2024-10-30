package test.java;

import main.java.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static test.java.SudokuTestHelper.*;

public class SudokuBuilderTest {

    @Test
    public void test_constructor_private () throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<SudokuBuilder> constructor = SudokuBuilder.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void test_getInstance_NotNull() {
        SudokuBuilder sudokuBuilder = SudokuBuilder.getInstance();
        assertNotNull(sudokuBuilder);
    }

    @Test
    public void test_buildSudoku_lengthFirstRowDifferentOfRowSize() {
        SudokuBuilder sudokuBuilder = SudokuBuilder.getInstance();

        int[][] sudokuToResolve = {
                {0, 1, 7, 0, 4, 5, 0, 0, 0},
                {0, 9, 4, 0, 2, 0, 8}};

        assertThrows(IllegalArgumentException.class,
                () -> sudokuBuilder.buildSudoku(sudokuToResolve, 1, 7, 3, 4));
    }

    @Test
    public void test_buildSudoku_rowsDonTHaveSameSize() {
        SudokuBuilder sudokuBuilder = SudokuBuilder.getInstance();

        int[][] sudokuToResolve = {
                {0, 1, 7, 0, 4, 5, 0, 0, 0},
                {0, 9, 4, 0, 2, 0, 8}};

        assertThrows(IllegalArgumentException.class,
                () -> sudokuBuilder.buildSudoku(sudokuToResolve, 2, 9, 3, 4));
    }

    @Test
    public void test_buildSudoku_failedOnRegionSideRow() {
        SudokuBuilder sudokuBuilder = SudokuBuilder.getInstance();

        assertThrows(IllegalArgumentException.class,
                () -> sudokuBuilder.buildSudoku(SUDOKU_id2_9x9_SIMPLE_INPUT, 9, 9, 4, 3));
    }

    @Test
    public void test_buildSudoku_failedOnRegionSideCol() {
        SudokuBuilder sudokuBuilder = SudokuBuilder.getInstance();

        assertThrows(IllegalArgumentException.class,
                () -> sudokuBuilder.buildSudoku(SUDOKU_id2_9x9_SIMPLE_INPUT, 9, 9, 3, 4));
    }

    @Test
    public void test_buildSudoku_returnIsNotNull() {
        assertNotNull(SudokuBuilder.getInstance().buildSudoku(SUDOKU_id2_9x9_SIMPLE_INPUT));
    }

    // The "simple" Sudoku is so easy that there is no need to run an algorithm.
    // Just the fact to convert the input int[][] into SudokuSquare[][] is enough to resolve it.
    @Test
    public void test_buildSudoku() {
        int nbRows = SUDOKU_id2_9x9_SIMPLE_INPUT.length;
        int nbColumns = SUDOKU_id2_9x9_SIMPLE_INPUT[0].length;

        SudokuSquare[][] sudokuSquares = SudokuBuilder.getInstance().buildSudoku(SUDOKU_id2_9x9_SIMPLE_INPUT);

        assertEquals(nbRows, sudokuSquares.length);

        for (int i = 0; i < SUDOKU_id2_9x9_SIMPLE_INPUT.length ; i++) {
            assertEquals(nbColumns, sudokuSquares[i].length);
        }

        for(int i = 0 ; i < nbRows ; i++) {
            for (int j = 0; j < nbColumns; j++) {
                assertEquals(i, sudokuSquares[i][j].getRowId());
                assertEquals(j, sudokuSquares[i][j].getColId());

                assertTrue(sudokuSquares[i][j].isWinnerValueFound());
                assertEquals(SUDOKU_id2_9x9_SIMPLE_EXPECTED[i][j], sudokuSquares[i][j].getWinnerValue());
            }
        }
        SudokuHelper.printlnFoundValues(sudokuSquares);
    }


    @Test
    public void test_buildSudoku_createRegions() {
        int nbRows = SUDOKU_id1_2x2_INPUT.length;
        int nbColumns = SUDOKU_id1_2x2_INPUT[0].length;

        SudokuSquare[][] sudokuSquares =
                SudokuBuilder.getInstance().buildSudoku(SUDOKU_id1_2x2_INPUT);


        for(int i = 0 ; i < nbRows ; i++) {
            for (int j = 0; j < nbColumns; j++) {
                BroadcastWinner winner = sudokuSquares[i][j].getBroadcastWinner();
                assertInstanceOf(SudokuRegions.class, winner);

            }
        }
    }
}
